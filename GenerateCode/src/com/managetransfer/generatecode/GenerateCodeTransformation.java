package com.managetransfer.generatecode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
 





import com.managetransfer.businessobject.Claims;
import com.managetransfer.client.ExpressionsDetails;
import com.managetransfer.client.MappingDetailsH;
import com.managetransfer.client.MappingDetailsMapH;
import com.managetransfer.common.ReadProperty;
import com.managetransfer.hibernate.GetRecordDetails;
import com.managetransfer.hibernate.HibernateConnection;
 
 /**
    get List of Transformation
       for each transformation
         Generate SetSource ObjetMethod
         	
         Generate SetTarget Object Method
         Generate exeucteTranformation method
             Get list of expression
             
          
   *
   */

public class GenerateCodeTransformation {

	private GetRecordDetails grd = new GetRecordDetails();
	private HibernateConnection hc = new HibernateConnection();
	private String packageName = new String ("com.managetransfer.businessobject.");
	private File javaClassFile = null;
	public static void main(String[] args) throws Exception {
		
		
		GenerateCodeTransformation gc = new GenerateCodeTransformation();
		 
		 
		//Initailize session
		gc.initOperation();
		//First part of class file is created
		gc.createStartClassFile();
		//Getter setter methods for important variables
		gc.createGetterSetter();
		//Populate all expressions
		gc.createExpression();
		//Generate transformation
		gc.createTransformation1();//This method called getTransformationBegining -> getAttributeLevelDetails
		//Close class file
	    gc.createEndClassFile();
		 
	}
	public String getTransformationBegining(String nameOfTransformation){
		String transformationBegin =" if(transformationName.equals(\""+nameOfTransformation+"\")){ \n";
		transformationBegin = transformationBegin +" for(int i=0 ; i < getSourceObject().size(); i++ ) { " ;
		//This loop picksup each object associated with the transformation
		List mappingListObject = hc.getObject("from MappingDetailsMapH  where mappingType ='Object' and mappingName='"+nameOfTransformation+"'");
		for(int i=0; i <mappingListObject.size();i++ ){
			MappingDetailsMapH mappingObject = (MappingDetailsMapH)mappingListObject.get(i);
			transformationBegin = transformationBegin +"\n if( getSourceObject().get(i).equals(\""+packageName+mappingObject.getSourceObject()+"\")) { \n";
			transformationBegin = transformationBegin +"\n "+ mappingObject.getTargetObject()+" "+mappingObject.getTargetObject()+"Object = new "+mappingObject.getTargetObject()+"();" ;
			transformationBegin = transformationBegin +"\n "+ mappingObject.getSourceObject()+" "+mappingObject.getSourceObject()+"Object = ( "+mappingObject.getSourceObject()+") getSourceObject().get(i) ; ";
			transformationBegin = transformationBegin +"\n"+ getAttributeLevelDetails(nameOfTransformation,mappingObject.getSourceObject(),mappingObject.getTargetObject());
			transformationBegin = transformationBegin +"\n getTargetobject().add(" +mappingObject.getTargetObject()+"Object);";
			transformationBegin = transformationBegin +"\n }";
		}
		transformationBegin = transformationBegin +"\n } \n }";
		return transformationBegin;
	}

	private String getAttributeLevelDetails(String mappingName,
			String sourceObject, String targetObject) {

		String attributeLevel = new String("");
		List mappingListAttribute = hc
				.getObject("from MappingDetailsH  where mappingType ='"
						+ mappingName + "'");
		for (int k = 0; k < mappingListAttribute.size(); k++) {
			boolean differentObject= false;
			MappingDetailsH mappingAttribute = (MappingDetailsH) mappingListAttribute
					.get(k);
			Map<Integer, MappingDetailsMapH> sdmhMattribute = mappingAttribute
					.getMappingDetailsMap();
			if (sdmhMattribute != null ) {
				Iterator iterator = sdmhMattribute.entrySet().iterator();
				int l = 0;
				differentObject= false;
				String attributeLogic = new String("");
				while (iterator.hasNext()) {
					Map.Entry mapEntry = (Map.Entry) iterator.next();
					MappingDetailsMapH sdmp1 = (MappingDetailsMapH) mapEntry
							.getValue();
					    if(sdmp1.getTargetObject().startsWith(targetObject+".")){
						if (l == 0) {
							if (mappingAttribute.getExpressionName() != null
									&& !mappingAttribute.getExpressionName()
											.trim().equals("=")) {
								attributeLogic = getSplitString(
										sdmp1.getTargetObject(), "set")
										+ "("
										+ mappingAttribute.getExpressionName()
										+ "("
										+ getSplitString(
												sdmp1.getSourceObject(), "get")
										+ "()";
							} else {
								attributeLogic = getSplitString(
										sdmp1.getTargetObject(), "set")
										+ "("
										+ getSplitString(
												sdmp1.getSourceObject(), "get")
										+ "()";
							}
						} else {
							attributeLogic = attributeLogic
									+ ","
									+ getSplitString(sdmp1.getSourceObject(),
											"get") + "()";
						}
						l = l + 1;
					}else{
						differentObject=true;
					}
					}
				if(!differentObject){ 
					if (mappingAttribute.getExpressionName() != null
							&& !mappingAttribute.getExpressionName().trim()
									.equals("=")) {
						attributeLogic = attributeLogic + "));\n";
					} else {
						attributeLogic = attributeLogic + ");\n";
					}
	
					attributeLevel = attributeLevel + attributeLogic;
				}
			}
		}
		return attributeLevel;
	}
	public String getTransformationEnd(String nameOfTransformation) throws Exception{
		appendToFile("}\n");
		return "";
	}
	private void createTransformation1() throws Exception {
		List mappingListObject = hc.getObject("from MappingDetailsH  where mappingType ='Object'");
		appendToFile("\n public void executeTransformation(){\n");
		//This loop picks up each transformation from defined in the system and process it 
		for(int i=0; i <mappingListObject.size();i++ ){
			MappingDetailsH mappinObject = (MappingDetailsH)mappingListObject.get(i);
			appendToFile(" \n "+getTransformationBegining(mappinObject.getMappingName()));
			
		}
		appendToFile("\n}");
		
	}
	
	private void createExpression() throws Exception {
		List objectList = hc.getObject("from ExpressionsDetails");
		for( int i=0; i < objectList.size(); i++){
			ExpressionsDetails ed = (ExpressionsDetails) objectList.get(i); 
			appendToFile(" \n "+ed.getExpression());
		}
		
	}
	private void createGetterSetter() throws Exception {
 		String startString = new String(" public ArrayList<Object> getTargetobject() { \n" +
				" 	return targetObject; \n" +
				" } \n" +
				" public ArrayList<Object> getSourceObject(){  \n" +
				" return sourceObject;  \n" +
				" }  \n" +
				" public void setTargetObject(ArrayList<Object> targetObject) { \n" + 
				" this.targetObject = targetObject;  \n" +
				" } \n" +
				" public String getTransformationName() { \n" +
				" return transformationName; \n" +
				" } \n" +
				" public void setTransformationName(String transformationName) { \n" +
				" this.transformationName = transformationName; \n" +
				" }   \n" + 
				" public void setSourceObjects(Object[] objects){  \n" + 
				" for(int i=0;i< objects.length;i++){  \n" + 
				" sourceObject.add( objects[i]);  \n" + 
				" }  \n" + 
				" }");
		appendToFile(startString);
	}
	public void initOperation() throws Exception{
		hc.initOperation();
		grd.initOperation(hc);
		ReadProperty.PropertyLoad();
	}
	
	 
	public void getGetterMethods(){
		
	}
	public GetRecordDetails getGrd() {
		return grd;
	}
	public void setGrd(GetRecordDetails grd) {
		this.grd = grd;
	}
	 
	public void createStartClassFile() throws Exception{
		String packageName = new String (" package com.managetransfer.dynamiccode.transformation; ");
		String importPackages = new String ("\n "+ReadProperty.getImportpackages());
		String classDeclaration = new String("\n public class Transformation { ");
		String globalVariables = new String ("\n ArrayList<Object> sourceObject = new ArrayList<>();   \n" +
										"ArrayList<Object> targetObject = new ArrayList<>();  \n" +
										"private String transformationName = new String (\"\"); \n" +
										"  \n");
		String startString =  packageName + importPackages +classDeclaration +globalVariables;
		javaClassFile = new File(ReadProperty.getJavaclasspathTransformation());
		 
		// if file doesnt exists, then create it
		if (!javaClassFile.exists()) {
			javaClassFile.createNewFile();
		}

		FileWriter fw = new FileWriter(javaClassFile.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(startString);
		bw.close();
	}
	public void createEndClassFile() throws Exception{
		String endString = new String("\n}");
		appendToFile(endString);
    }
	public void appendToFile(String data) throws Exception{
		FileWriter fileWritter = new FileWriter(javaClassFile,true);
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
        bufferWritter.append(data);
        bufferWritter.close();
	}
	 
	public String getSplitString(String input1,String input2){
		String ClassName = input1.substring(0, input1.indexOf("."));
		String AttributeName = input1.substring(input1.indexOf(".")+1,input1.length() );
		String a = AttributeName.substring(0, 1);
		AttributeName = a.toUpperCase()+AttributeName.substring(1,AttributeName.length());
		return ClassName+"Object."+input2+ AttributeName;
	}
}
