package com.managetransfer.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
 
import java.util.ResourceBundle;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
 


public class ReadProperty implements ServletContextListener{
	private static org.apache.log4j.Logger logger = Logger.getLogger(ReadProperty.class);
	private static String PROPERTY_FILE ="ManageTransferProp";
	private static ArrayList<String> sourceclassname = new ArrayList<String> () ; 
	private static ArrayList<String>  targetclassname = new ArrayList<String> () ; 
	private static List<String> sourceclassnamel=new ArrayList<String> ();
	private static List<String> targetclassnamel=new ArrayList<String> ();
	public static void PropertyLoad(){
		try{
			String methodName="PropertyLoad";
			logger.info("Inside"+methodName);
			ResourceBundle bundle = ResourceBundle.getBundle(PROPERTY_FILE);
			setSourceclassnamel(  Arrays.asList( bundle.getString("sourceclassname").split(",")) );
			logger.info("sourceclassname"+getSourceclassname());
			setTargetclassnamel(  Arrays.asList( bundle.getString("targetclassname").split(",")) );
			logger.info("targetclassname"+getTargetclassname());
			}
		catch(Exception e){
			logger.error("Exception e"+e);
		}
	}
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		 
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		PropertyLoad();
		
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
	public static ArrayList<String>  getObjectTypes (){
		ArrayList<String> allObjectType = new ArrayList<String>();
		allObjectType.add("--select--");
		allObjectType.addAll(sourceclassname);
		allObjectType.addAll(targetclassname);
		return allObjectType;
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

	 

}
