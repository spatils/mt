package com.admin.shared.session;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.managetransfer.client.ConnectionDetails;
import com.managetransfer.client.JobDetails;
import com.managetransfer.client.JobServerDetails;
import com.managetransfer.hibernate.HibernateConnection;

public class SchedularDetailsServer {
	 List<JobDetails> jobResult = null;
	 ArrayList<JobDetails> arrayJob = new ArrayList<JobDetails>();
	 List<JobServerDetails> jobServerDetails = new ArrayList<JobServerDetails>();
	 HibernateConnection hc = new HibernateConnection();
	 String ACTION = "?Action=StartJob";  //
	 /*
	  *  ACTION 
	  *  ?Action=StartJob
	  *  	PARAMETER
	  *  		&JobId=hello1
	  *  		&PhaseType=
	  *  		&CronSch=
	  *  ?Action=StopJob
	  *  	PARAMETER
	  *  		&JobId=hello1
	  *  ?Action=PauseJob
	  *  	PARAMETER
	  *  		&JobId=hello1
	  *  ?Action=ResumeJob
	  *  	PARAMETER
	  *  		&JobId=hello1
	  *  ?Action=RunJob
	  *  	PARAMETER
	  *  		&JobId=hello1
	  *  ?Action=GetJobDetails
	  *  		PARAMETER
	  *   
	  * 
	  * 
	  * 
	  */
	public SchedularDetailsServer(){
		 
		
		 
	}
	public ArrayList<JobDetails> getJobResult(String input){
		return arrayJob;
			
	}
	public boolean addJob(JobDetails input) {
		jobResult.add(new JobDetails(input.getJobName(),input.getCronSchedule(),input.getServerName(),input.getLastExecutionDate(),input.getNextExectuionDate(),input.getJobState()));
		return true;
	}
	public ArrayList<JobDetails> searchJobResult(String input) {
		// TODO Auto-generated method stub
		List<JobDetails> searchJobResult = null;
		ArrayList<JobDetails> searchArrayJob = new ArrayList<JobDetails>();
		searchJobResult =searchArrayJob;
		for(JobDetails sr : jobResult){
			if (sr.getJobName().contains(input)){
				searchJobResult.add(sr);
			}
		}
		
		return (ArrayList<JobDetails>) searchJobResult;
	}
	public Boolean deleteJob(String input) {
		// TODO Auto-generated method stub
		int i=0;
		for( JobDetails sr : jobResult){
			if (sr.getJobName().equals(input)){
				try{
				 	break; 
				}
				catch(Exception e){
					System.out.println("Error"+e);
				}
			}
			i = i+1;
		}
		jobResult.remove(i);
		return true;
	}
	public boolean editJob(JobDetails jobDetail) {
		// TODO Auto-generated method stub
		int i=0;
		for( JobDetails sr : jobResult){
			if (sr.getJobName().equals(jobDetail.getJobName())){
				try{
					jobResult.get(i).setCronSchedule(jobResult.get(i).getCronSchedule());
					jobResult.get(i).setServerName(jobResult.get(i).getServerName());
					jobResult.get(i).setNextExectuionDate(jobResult.get(i).getNextExectuionDate());
					jobResult.get(i).setLastExecutionDate(jobResult.get(i).getLastExecutionDate());
					jobResult.get(i).setJobState( jobResult.get(i).getJobState());
				}
				catch(Exception e){
					System.out.println("Error"+e);
				}
			}
			i = i+1;
		}
		return true;
	}
	public HibernateConnection getHc() {
		return hc;
	}
	public void setHc(HibernateConnection hc) throws Exception{
		this.hc = hc;
		List list = hc.getObject("from 	JobServerDetails ");
		jobResult = arrayJob;
		for (int i=0 ; i < list.size();i++){
			JobServerDetails jsd = (JobServerDetails) list.get(i);
			jobServerDetails.add(jsd);
			jobResult.addAll(getActiveJobDetails(jsd));
		}
		if (jobResult.size()==0) {
			jobResult.add(new JobDetails("Dummy","1 3 2003","-userName dmadmin -batchSize 100","","","" ));
		 }
		 
		
	}
	
	public boolean runJob(String jobName) {
		System.out.println("Inside runJob");
		for( JobDetails sr : jobResult){
			if (sr.getJobName().equals(jobName)){
				try{
					runJob(sr);
					return true;
				}
				catch(Exception e){
					System.out.println("Error"+e);
					
				}
			}
		}
		return false;
	}
	public boolean changeStateJob(String jobName) {
		for( JobDetails sr : jobResult){
			if (sr.getJobName().equals(jobName)){
				try{
					changeStateJob(sr);
					return true;
				}
				catch(Exception e){
					System.out.println("Error"+e);
					
				}
			}
		}
		return false;
	}
	public boolean changeStateJob(JobDetails jobDetails) throws Exception{
		for(JobServerDetails jds : jobServerDetails ){
			if(jds.getJobServerName().equals(jobDetails.getServerName())){
				if(jobDetails.getJobState().equals("NORMAL")){
					ACTION = "?Action=PauseJob"; 
				}else {
					ACTION = "?Action=ResumeJob"; 
				}
				String PARAMETER = "&JobId="+jobDetails.getJobName();
				String url = jds.getServer_url()+ACTION+PARAMETER;
				URL JobServer = new URL(url);
		        URLConnection yc =  JobServer.openConnection();
		        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		        String inputLine;
		        while ((inputLine = in.readLine()) != null) {
		            System.out.println(inputLine);
		        }
		        in.close();
		        return true;
			}
		}
		
		return false;
	}
	
	public boolean runJob(JobDetails jobDetails) throws Exception{
		System.out.println("Inside runJob");
		for(JobServerDetails jds : jobServerDetails ){
			if(jds.getJobServerName().equals(jobDetails.getServerName())){
				System.out.println("run Action");
				ACTION = "?Action=RunJob"; 
				String PARAMETER = "&JobId="+jobDetails.getJobName();
				String url = jds.getServer_url()+ACTION+PARAMETER;
				URL JobServer = new URL(url);
		        URLConnection yc =  JobServer.openConnection();
		        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		        String inputLine;
		        while ((inputLine = in.readLine()) != null) {
		            System.out.println(inputLine);
		        }
		        in.close();
		        return true;
			}
		}
		
		return false;
	}
	public boolean deleteJob(JobDetails jobDetails) throws Exception{
		System.out.println("Inside deleteJob");
		for(JobServerDetails jds : jobServerDetails ){
			if(jds.getJobServerName().equals(jobDetails.getServerName())){
				System.out.println("Stop Action");
				ACTION = "?Action=StopJob"; 
				String PARAMETER = "&JobId="+jobDetails.getJobName();
				String url = jds.getServer_url()+ACTION+PARAMETER;
				URL JobServer = new URL(url);
		        URLConnection yc =  JobServer.openConnection();
		        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		        String inputLine;
		        while ((inputLine = in.readLine()) != null) {
		            System.out.println(inputLine);
		        }
		        in.close();
		        return true;
			}
		}
		
		return false;
	}
	public ArrayList<JobDetails> getActiveJobDetails(JobServerDetails jsd) throws Exception{
		ArrayList<JobDetails> jdl = new ArrayList<JobDetails>();
		ACTION = "?Action=GetJobDetails";  
		String url = jsd.getServer_url()+ACTION;
		URL JobServer = new URL(url);
        URLConnection yc =  JobServer.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println(inputLine);
            String[] values = inputLine.split("~");
            jdl.add(new JobDetails(values[0],values[4], jsd.getJobServerName(),values[1],values[2],values[3]));
         }
        in.close();
		return jdl;
	}
	public ArrayList<String> machineList() {
		ArrayList<String> machineList = new ArrayList<String>();
		for(int i=0;i< jobServerDetails.size();i++){
			machineList.add(jobServerDetails.get(i).getJobServerName());
		}
		return machineList;
	}
}
	 
	
	

 
