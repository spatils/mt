package com.managetransfer.client;

import java.io.Serializable;
import java.util.Date;

public class JobDetails implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6938550317765485308L;
	/**
	 * 
	 */
	private   String jobName ;
	private   String cronSchedule ;
	private   String serverName ;
	private   String lastExecutionDate ;
	private   String nextExectuionDate;
	private   String jobState;
	
	public JobDetails(  String jobName ,   String cronSchedule, String parameters,String lastExecutionDate,String nextExectuionDate,String jobState){
		this.setJobName(jobName);
		this.setCronSchedule(cronSchedule);
		this.setServerName(parameters);
		this.setLastExecutionDate(lastExecutionDate);
		this.setNextExectuionDate(nextExectuionDate);
		this.setJobState(jobState);
	}
	public JobDetails(){
		
	}
	 
	 
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getCronSchedule() {
		return cronSchedule;
	}
	public void setCronSchedule(String cronSchedule) {
		this.cronSchedule = cronSchedule;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getLastExecutionDate() {
		return lastExecutionDate;
	}
	public void setLastExecutionDate(String lastExecutionDate) {
		this.lastExecutionDate = lastExecutionDate;
	}
	public String getNextExectuionDate() {
		return nextExectuionDate;
	}
	public void setNextExectuionDate(String nextExectuionDate) {
		this.nextExectuionDate = nextExectuionDate;
	}
	public String getJobState() {
		return jobState;
	}
	public void setJobState(String jobState) {
		this.jobState = jobState;
	}
}
