package com.managetransfer.record;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
 

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
	private String packageName = new String ("com.managetransfer.record.");
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
		gc.createTransformation();
		//Close class file
	    gc.createEndClassFile();
		 
	}
	private void createTransformation() throws Exception {
		List mappingListObject = hc.getObject("from MappingDetailsH  where mappingType ='Object'");
		appendToFile("public void executeTransformation(){\n");
		for(int i=0; i <mappingListObject.size();i++ ){
			MappingDetailsH mappinObject = (MappingDetailsH)mappingListObject.get(i);
			//System.out.println("mapping object"+mappinObject.getMappingName());
			String mappingLevel = new String("");
			String targetClassName = new String("");
			mappingLevel = mappingLevel+ "if(transformationName.equals(\""+mappinObject.getMappingName()+"\")){\n";
			//initailizing objects
			Map<Integer,MappingDetailsMapH> sdmhM = mappinObject.getMappingDetailsMap();
			if(sdmhM!=null){
				Iterator iterator = sdmhM.entrySet().iterator();
				int l = 0;
				while(iterator.hasNext()){
					Map.Entry mapEntry = (Map.Entry) iterator.next();
					MappingDetailsMapH sdmp =(MappingDetailsMapH)mapEntry.getValue();
					if(l==0){
						
						mappingLevel = mappingLevel +   sdmp.getTargetObject()+" "+sdmp.getTargetObject()+"Object = new "+sdmp.getTargetObject()+"(); \n";
						targetClassName = sdmp.getTargetObject();
					}
					mappingLevel = mappingLevel +   sdmp.getSourceObject()+" "+sdmp.getSourceObject()+"Object = ("+sdmp.getSourceObject() +") sourceObject.get( "+l+"); \n";
					l = l+1;
				}
			}
			
			//Attribute handling
			List mappingListAttribute = hc.getObject("from MappingDetailsH  where mappingType ='"+mappinObject.getMappingName()+"'");
			for(int k=0; k <mappingListAttribute.size();k++ ){
				MappingDetailsH mappingAttribute = (MappingDetailsH)mappingListAttribute.get(k);
				Map<Integer,MappingDetailsMapH> sdmhMattribute = mappingAttribute.getMappingDetailsMap();
				if(sdmhMattribute!=null){
					Iterator iterator = sdmhMattribute.entrySet().iterator();
					int l = 0;
					String attributeLogic = new String("");
					while(iterator.hasNext()){
						Map.Entry mapEntry = (Map.Entry) iterator.next();
						MappingDetailsMapH sdmp1 =(MappingDetailsMapH)mapEntry.getValue();
						if(l==0){
							 if (mappingAttribute.getExpressionName()!=null &&  !mappingAttribute.getExpressionName().trim().equals("=")){
								 attributeLogic =   getSplitString(sdmp1.getTargetObject(),"set")+"("+mappingAttribute.getExpressionName()+"(" +getSplitString( sdmp1.getSourceObject(),"get")+"()" ;
							 }else{
								 attributeLogic =   getSplitString(sdmp1.getTargetObject(),"set")+"(" +getSplitString( sdmp1.getSourceObject(),"get")+"()" ;
							 }
						}else{
							 attributeLogic = attributeLogic+"," +getSplitString( sdmp1.getSourceObject(),"get")+"()" ;
						}
						l = l+1;
					}
					 if (mappingAttribute.getExpressionName()!=null &&  !mappingAttribute.getExpressionName().trim().equals("=")){
						 attributeLogic= attributeLogic+"));\n";
					 }else{
						 attributeLogic= attributeLogic+");\n";
					 }
					
					 mappingLevel = mappingLevel+attributeLogic;
				}
			}
			mappingLevel = mappingLevel + " setTargetObject("+targetClassName+"Object"+"); \n ";
			mappingLevel = mappingLevel + "\n }";
			appendToFile(mappingLevel);
		}
		appendToFile("}\n");
	}
	private void createExpression() throws Exception {
		List objectList = hc.getObject("from ExpressionsDetails");
		for( int i=0; i < objectList.size(); i++){
			ExpressionsDetails ed = (ExpressionsDetails) objectList.get(i); 
			appendToFile(" \n "+ed.getExpression());
		}
		
	}
	private void createGetterSetter() throws Exception {
 		String startString = new String(" public Object getTargetobject() { \n" +
				" 	return targetObject; \n" +
				" } \n" +
				" public void setTargetObject(Object targetObject) { \n" + 
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
		String startString = new String("package com.managetransfer.record;\n"+
										"import java.util.Date;\n"+
										"import java.util.HashMap;\n" +
										"import java.util.ArrayList;\n"+
										"public class Transformation {\n" +
										"ArrayList<Object> sourceObject = new ArrayList<>();   \n" +
										"private Object targetObject = null;  ; \n" +
										"private String transformationName = new String (\"\"); \n" +
										"  \n");
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
