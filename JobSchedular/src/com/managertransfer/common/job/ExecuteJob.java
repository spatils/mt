package com.managertransfer.common.job;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
public class ExecuteJob implements Job{
	private static org.apache.log4j.Logger logger = Logger.getLogger(ExecuteJob.class);
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("Executing Execute Job");
		
		
	}
}
