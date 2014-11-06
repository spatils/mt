package com.managertransfer.common.job;

 
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

@WebListener
public class StartSchedular implements ServletContextListener {

    public static StartSchedular startSchedular = null;
    static Scheduler scheduler =null;
    static SchedulerFactory schedFact = null;  
    private static org.apache.log4j.Logger logger = Logger.getLogger(StartSchedular.class);
    public static StartSchedular getInstance(){
    	if(startSchedular==null){
    		startSchedular = new StartSchedular();
    	}
    	return startSchedular;
    }
    public void contextInitialized(ServletContextEvent arg0) {
    	try{
    		schedFact =  new org.quartz.impl.StdSchedulerFactory();
    		startSchedular = new StartSchedular();
    		scheduler =schedFact.getScheduler();
    		logger.info("Schedular started");
    		scheduler.start();
    		 
    	}catch(Exception e){
    		System.out.println("Exception"+e);
    		logger.error("Error while starting schdular"+e);
    	}
    }

	 
	 
    public void contextDestroyed(ServletContextEvent arg0) {
    	try{
    		scheduler.shutdown();
    		logger.info("Schedular stopped");
    	}
    	catch(Exception e){
    		 
    		
    	}
    }
	
}
