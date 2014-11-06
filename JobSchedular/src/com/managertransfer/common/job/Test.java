package com.managertransfer.common.job;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Test {
	String JSPURL0 = "http://localhost:8080/JobSchedular2/JobSchedular.jsp";
	String JSPURL = "http://localhost:8080/JobSchedular2/JobSchedular.jsp?Action=PauseJob&JobId=hello1";
	String JSPURL1 = "http://localhost:8080/JobSchedular/JobSchedular.jsp?Action=GetJobDetails";
	String JSPURL2 = "http://localhost:8080/JobSchedular2/JobSchedular.jsp?Action=RunJob&JobId=hello1";
	
	String addedeVariable = new String("");
	String BASE_URL = "http://localhost:8080/JobSchedular2/JobSchedular.jsp";
	String ACTION = "?Action=StartJob";
	String PARAMETER = "&JobId=hello2";
	String CRON_SCH  = "&CronSch=";
	String CRON_SCH_VAL ="* 0/5 * * * ?";
	String actionName = "StartJob"; //StopJob , PauseJob, ResumeJob, RunJob , GetJobDetails
	public static void main(String[] args) throws Exception {
		Test ts = new Test();
		//ts.callJSP();
		ts.processString();
	}
 public void callJSP() throws Exception {
	 	String encodedString =  URLEncoder.encode(CRON_SCH_VAL, "UTF-8") ;
	    //URL JobServer = new URL(BASE_URL+ACTION+PARAMETER+CRON_SCH+encodedString);
	 	URL JobServer = new URL(JSPURL1);
        URLConnection yc =  JobServer.openConnection();
        
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) 
            System.out.println(inputLine);
        in.close();
	}
 public void processString(){
	 String jobName = new String("Seq1_1_2");
	 int processId =  Integer.parseInt(jobName.substring(jobName.lastIndexOf("_")+1,jobName.length()));
	 System.out.println(processId);
	 jobName = jobName.substring(0,jobName.lastIndexOf("_") );
	 int seqNumber =   Integer.parseInt(jobName.substring(jobName.lastIndexOf("_")+1,jobName.length()));
	 System.out.println(seqNumber);
	 String seqName = jobName.substring(0,jobName.lastIndexOf("_") );
	 System.out.println(seqName);
 }
}
