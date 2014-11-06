package com.managertransfer.common.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloWorld  implements Job{
	 private static org.apache.log4j.Logger logger = Logger.getLogger(HelloWorld.class);
	 
	public static void main(String[] args) {
		 

	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("Inside execute");
		
	}

}
