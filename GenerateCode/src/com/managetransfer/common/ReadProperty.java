package com.managetransfer.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
 
import java.util.ResourceBundle;
 

import org.apache.log4j.Logger;
 


public class ReadProperty  {
	private static org.apache.log4j.Logger logger = Logger.getLogger(ReadProperty.class);
	private static String PROPERTY_FILE ="GenerateCodeProp";
	private static ArrayList<String> sourceclassname = new ArrayList<String> () ; 
	private static ArrayList<String>  targetclassname = new ArrayList<String> () ; 
	private static List<String> sourceclassnamel=new ArrayList<String> ();
	private static List<String> targetclassnamel=new ArrayList<String> ();
	private static String javaClassPath = new String("");
	private static String  javaclasspathTransformation = new String("");
	private static String importpackages = new String("");
	
	public static void PropertyLoad(){
		try{
			String methodName="PropertyLoad";
			logger.info("Inside"+methodName);
			ResourceBundle bundle = ResourceBundle.getBundle(PROPERTY_FILE);
			setSourceclassnamel(  Arrays.asList( bundle.getString("sourceclassname").split(",")) );
			logger.info("sourceclassname"+getSourceclassname());
			setTargetclassnamel(  Arrays.asList( bundle.getString("targetclassname").split(",")) );
			logger.info("targetclassname"+getTargetclassname());
			setJavaClassPath(bundle.getString("javaclasspath"));
			setJavaclasspathTransformation(bundle.getString("javaclasspathTransformation"));
			logger.info("classpath"+getJavaClassPath());
			setJavaClassPath(bundle.getString("javaclasspath"));
			setImportpackages(bundle.getString("importpackages"));
			logger.info("classpath"+getImportpackages());
			
			}
		catch(Exception e){
			logger.error("Exception e"+e);
		}
	}
	 
	public static ArrayList<String> getSourceclassname() {
		return sourceclassname;
	}
	public static void setSourceclassname(ArrayList<String> sourceclassname) {
		ReadProperty.sourceclassname = sourceclassname;
	}
	public static ArrayList<String> getTargetclassname() {
		return targetclassname;
	}
	public static void setTargetclassname(ArrayList<String> targetclassname) {
		ReadProperty.targetclassname = targetclassname;
	}
	
	
	public static List<String> getSourceclassnamel() {
		
		return sourceclassnamel;
	}
	public static void setSourceclassnamel(List<String> sourceclassnamel) {
		ReadProperty.sourceclassnamel = sourceclassnamel;
		for(int i=0; i < sourceclassnamel.size();i++){
			sourceclassname.add(sourceclassnamel.get(i));
		}
	}
	public static List<String> getTargetclassnamel() {
		
		return targetclassnamel;
	}
	public static void setTargetclassnamel(List<String> targetclassnamel) {
		ReadProperty.targetclassnamel = targetclassnamel;
		for(int i=0; i < targetclassnamel.size();i++){
			targetclassname.add(targetclassnamel.get(i));
		}
		
	}

	public static String getJavaClassPath() {
		return javaClassPath;
	}

	public static void setJavaClassPath(String classPath) {
		ReadProperty.javaClassPath = classPath;
	}

	public static String getJavaclasspathTransformation() {
		return javaclasspathTransformation;
	}

	public static void setJavaclasspathTransformation(
			String javaclasspathTransformation) {
		ReadProperty.javaclasspathTransformation = javaclasspathTransformation;
	}

	public static String getImportpackages() {
		return importpackages;
	}

	public static void setImportpackages(String importpackages) {
		ReadProperty.importpackages = importpackages;
	}

	 

}
