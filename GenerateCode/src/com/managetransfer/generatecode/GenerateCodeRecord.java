package com.managetransfer.generatecode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
 

import java.util.List;

import com.managetransfer.client.ObjectDetails;
import com.managetransfer.common.ReadProperty;
import com.managetransfer.hibernate.GetRecordDetails;
import com.managetransfer.hibernate.HibernateConnection;
 
 

public class GenerateCodeRecord {

	private GetRecordDetails grd = new GetRecordDetails();
	private HibernateConnection hc = new HibernateConnection();
	private String packageName = new String ("com.managetransfer.businessobject.");
	private File javaClassFile = null;
	public static void main(String[] args) throws Exception {
		GenerateCodeRecord gc = new GenerateCodeRecord();
		gc.initOperation();
		gc.createStartClassFile();
		gc.getInstantiateObjectMethod(); 
		gc.getExtractKeyMethod();
		gc.createEndClassFile();
		
	}
	public void initOperation() throws Exception{
		hc.initOperation();
		grd.initOperation(hc);
		ReadProperty.PropertyLoad();
	}
	
	public void getInstantiateObjectMethod() throws Exception{
		//Print declaration
		String startString = new String("public Object instantiateObject( String className ,	HashMap<String, String> hmString, HashMap<String,Integer> hmInteger,HashMap<String,Date> hmDate,HashMap<String,Long> hmLong){");
		String objectLevelHandling = new String("if (className.equals(\"$CLASS_NAME$\")){ \n"+
			" $CLASS_NAME$ $OBJECT_NAME$ = new $CLASS_NAME$(); \n"+
			" $OBJECT_INIT$ \n"+
			" return $OBJECT_NAME$;\n"+
		"}");
		String endString = new String(" return null;\n" +
				"}");
		appendToFile(startString);
		ArrayList<String> classNames =  getClassList();
		for(int i=0 ; i < classNames.size();i++){
			System.out.println(objectLevelHandling);
			
			//Conditional handling
			String objectName = new String(classNames.get(i)+"Object");
			String className  = new String(classNames.get(i));
			System.out.println(className);
			String columnNamesPK= grd.getColumnNamesPK(packageName+className);
			String methodFinal  = new String("");
			String setMethods = new String("");
			if(null != columnNamesPK && !columnNamesPK.equals("") ){
				
				 //System.out.println("columnNames Name:: " + columnNamesPK + " columnNames type:: "+grd.getColumnType(packageName+"Claims",columnNamesPK) + " columnNames:: "+grd.getDatabaseColumnName(packageName+"Claims",columnNamesPK) );
				String method =  objectName+".set"+columnNamesPK.substring(0, 1).toUpperCase()+columnNamesPK.substring(1, columnNamesPK.length())+"(" ;
				String mapName = new String("");
				if (grd.getColumnType(packageName+className,columnNamesPK).equals("string")){
					mapName = "hmString";
				}
				else if(grd.getColumnType(packageName+className,columnNamesPK).equals("integer")){
					mapName = "hmInteger";
				}
				else if(grd.getColumnType(packageName+className,columnNamesPK).equals("date")){
					mapName = "hmDate";
				}else if(grd.getColumnType(packageName+className,columnNamesPK).equals("long")){
					mapName = "hmLong";
				}
				method = method + mapName+".get(\""+grd.getDatabaseColumnName(packageName+className,columnNamesPK)+"\"));";
				System.out.println(method);
				setMethods =method;
			}else{
				System.out.println("Composite key");
				String columnNamesCK[] = grd.getColumnNamesCK(packageName+className);
				
				for (String str : columnNamesCK) {
					//System.out.println("columnNames Name:: " +str+ "columnNames type:: " +grd.getColumnType(packageName+"Claims",str)+ " columnNames:: " +grd.getDatabaseColumnName(packageName+"Claims",str) );
					System.out.println( "set"+str.substring(0, 1).toUpperCase()+str.substring(1, str.length()));
					String method =  objectName+".set"+str.substring(0, 1).toUpperCase()+str.substring(1, str.length())+"(" ;
					String mapName = new String("");
					if (grd.getColumnType(packageName+className,str).equals("string")){
						mapName = "hmString";
					}
					else if(grd.getColumnType(packageName+className,str).equals("integer")){
						mapName = "hmInteger";
					}
					else if(grd.getColumnType(packageName+className,str).equals("date")){
						mapName = "hmDate";
					}else if(grd.getColumnType(packageName+className,str).equals("long")){
						mapName = "hmLong";
					}
					method = method + mapName+".get(\""+grd.getDatabaseColumnName(packageName+className,str)+"\"));";
					System.out.println(method);
					setMethods = setMethods + method + "\n";
				}
			 }
			methodFinal = objectLevelHandling.replace("$CLASS_NAME$", className );
			methodFinal = methodFinal.replace("$OBJECT_NAME$",objectName  );
			methodFinal = methodFinal.replace("$OBJECT_INIT$", setMethods  );
			appendToFile(methodFinal);
		}
		appendToFile(endString);
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
		String startString = new String("package com.managetransfer.dynamiccode.keyhandling;\n"+
										"import java.util.Date;\n"+
										"import java.util.HashMap;\n"+
										"import com.managetransfer.businessobject.*;\n"+
										"public class KeyHandling {\n" +
										"public HashMap<String,String> ohmString = new HashMap() ; \n" +
										"public HashMap<String,Integer> ohmInteger = new HashMap() ; \n" +
										"public HashMap<String,Date> ohmDate = new HashMap() ; \n" +
										"public HashMap<String,Long> ohmLong = new HashMap() ; \n");
		javaClassFile = new File(ReadProperty.getJavaClassPath());
		 
		// if file doesnt exists, then create it
		System.out.println("javaClassFile"+javaClassFile.getPath());
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
	public void getExtractKeyMethod() throws Exception{
		String startString = new String("public void extractKey(Object object,String className){ \n");
		String endString = new String("\n}");
		String objectLevelHandling = new String("if (className.equals(\"$CLASS_NAME$\")){ \n"+
				" $CLASS_NAME$ $OBJECT_NAME$ =  ($CLASS_NAME$) object; \n"+
				" $OBJECT_INIT$ \n"+
			"}");
			
		appendToFile(startString);
		
		ArrayList<String> classNames =  getClassList();
		 
		for(int i=0 ; i < classNames.size();i++){
			//Conditional handling
			String objectName = new String(classNames.get(i)+"Object");
			String className  = new String(classNames.get(i));
			System.out.println(className);
			String columnNamesPK= grd.getColumnNamesPK(packageName+className);
			String methodFinal  = new String("");
			String setMethods = new String("");
			if(null != columnNamesPK && !columnNamesPK.equals("") ){
				
				 //System.out.println("columnNames Name:: " + columnNamesPK + " columnNames type:: "+grd.getColumnType(packageName+"Claims",columnNamesPK) + " columnNames:: "+grd.getDatabaseColumnName(packageName+"Claims",columnNamesPK) );
				String method = objectName+ ".get"+columnNamesPK.substring(0, 1).toUpperCase()+columnNamesPK.substring(1, columnNamesPK.length())+"(" ;
				String mapName = new String("");
				if (grd.getColumnType(packageName+className,columnNamesPK).equals("string")){
					mapName = "ohmString.put(";
				}
				else if(grd.getColumnType(packageName+className,columnNamesPK).equals("integer")){
					mapName = "ohmInteger.put(";
				}
				else if(grd.getColumnType(packageName+className,columnNamesPK).equals("date")){
					mapName = "ohmDate.put(";
				}else if(grd.getColumnType(packageName+className,columnNamesPK).equals("long")){
					mapName = "ohmLong.put(";
				}
				mapName=mapName+"\""+grd.getDatabaseColumnName(packageName+className,columnNamesPK)+"\",";
				method = mapName+method;
				method = method + "));";
				System.out.println(method);
				setMethods =method;
			}else{
				System.out.println("Composite key");
				String columnNamesCK[] = grd.getColumnNamesCK(packageName+className);
				
				for (String str : columnNamesCK) {
					//System.out.println("columnNames Name:: " +str+ "columnNames type:: " +grd.getColumnType(packageName+"Claims",str)+ " columnNames:: " +grd.getDatabaseColumnName(packageName+"Claims",str) );
					String method =  objectName+".get"+str.substring(0, 1).toUpperCase()+str.substring(1, str.length())+"(" ;
					String mapName = new String("");
					if (grd.getColumnType(packageName+className,str).equals("string")){
						mapName = "ohmString.put(";
					}
					else if(grd.getColumnType(packageName+className,str).equals("integer")){
						mapName = "ohmInteger.put(";
					}
					else if(grd.getColumnType(packageName+className,str).equals("date")){
						mapName = "ohmDate.put(";
					}else if(grd.getColumnType(packageName+className,str).equals("long")){
						mapName = "ohmLong.put(";
					}
					mapName=mapName+"\""+grd.getDatabaseColumnName(packageName+className,str)+"\",";
					method = mapName + method;
					method = method + "));";
					System.out.println(method);
					setMethods = setMethods + method + "\n";
				}
			 }
			methodFinal = objectLevelHandling.replace("$CLASS_NAME$", className );
			methodFinal = methodFinal.replace("$OBJECT_NAME$",objectName  );
			methodFinal = methodFinal.replace("$OBJECT_INIT$", setMethods  );
			appendToFile(methodFinal);
	
			
		}
		appendToFile(endString);
	
		
	
	}
	public ArrayList<String> getClassList(){
		ArrayList<String> classList = new ArrayList<String>();
		List  objectList = hc.getObject("from ObjectDetails") ;
		for (int i=0; i< objectList.size();i++ ){
			ObjectDetails od = ( ObjectDetails ) objectList.get(i);
			classList.add(od.getObjectName());
		}
		
		return classList;
	}

}
