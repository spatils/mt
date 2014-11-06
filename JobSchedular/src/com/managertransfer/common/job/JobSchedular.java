package com.managertransfer.common.job;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.CalendarIntervalScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.impl.matchers.GroupMatcher.*;
import static org.quartz.impl.matchers.EverythingMatcher.*;
import static org.quartz.impl.matchers.KeyMatcher.*;
import static org.quartz.TriggerKey.*;
import static org.quartz.impl.matchers.AndMatcher.*;
import static org.quartz.impl.matchers.OrMatcher.*;
import static org.quartz.JobKey.*;
import static org.quartz.DateBuilder.*;
import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.Trigger.*;

 
@WebServlet(
		urlPatterns = { "/JobSchedular" }, 
		initParams = { 
				@WebInitParam(name = "isStarted", value = "true")
		})
public class JobSchedular extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String defaultGroup =  new String("group1") ;
	private static org.apache.log4j.Logger logger = Logger.getLogger(DelegateExecution.class);
    public JobSchedular() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Inside doGet");
		PrintWriter out = response.getWriter();
		if(StartSchedular.getInstance()  == null){
			out.println("Schedular is not initialized");
		}else{
			
			if (request.getParameter("Action")==null) {
				out.println("Please provide appropriate action");
				
			}else if( request.getParameter("Action").equals("StartJob")) {
				try{
					logger.info(request.getParameter("Action"));
					logger.info(request.getParameter("JobId"));
					logger.info(request.getParameter("CronSch"));
					startJob(request.getParameter("JobId"),URLDecoder.decode(request.getParameter("CronSch"), "UTF-8"),request.getParameter("PhaseType"));
					out.println("Job Started");
				}catch(Exception e){
					logger.error("Error While starting a job"+e);
					out.println("Error While starting a job");
				}
			}else if (request.getParameter("Action").equals("StopJob")){
				try{ 
					logger.info(request.getParameter("Action"));
					logger.info(request.getParameter("JobId"));
					stopJob(request.getParameter("JobId"));
					out.println("Job Stopped");
				}catch(Exception e){
					logger.error("Error deleting Job"+e);
					out.println("Error deleting Job");
				}
			}else if (request.getParameter("Action").equals("ModifyJob")){
				
			}else if (request.getParameter("Action").equals("GetJobDetails")){
				logger.info("Returning Job details");
				try{
					for (String group : StartSchedular.scheduler.getJobGroupNames()) {
						// enumerate each job in group
						for (JobKey jobKey : StartSchedular.scheduler.getJobKeys(jobGroupEquals(  group))) {
							JobDetail jd = StartSchedular.scheduler.getJobDetail(jobKey) ;
							 
							CronTrigger  triggerKey = (CronTrigger) StartSchedular.scheduler.getTrigger(triggerKey(jobKey.getName(),defaultGroup)) ;
							out.println(jobKey.getName()+"~"+triggerKey.getPreviousFireTime()+"~"+triggerKey.getNextFireTime()+"~"+StartSchedular.scheduler.getTriggerState(triggerKey(jobKey.getName(),defaultGroup))+"~"+triggerKey.getCronExpression());
							 
						}
					}
				}catch(Exception e){
					out.println("Unable to process request");
					logger.error("ERROR"+e);
				}
			}else if (request.getParameter("Action").equals("RunJob")){
				try{ 
					logger.info(request.getParameter("Action"));
					logger.info(request.getParameter("JobId"));
					runJob(request.getParameter("JobId"));
					out.println("Running");
				}catch(Exception e){
					logger.error("Error Running Job"+e);
					out.println("Error Running Job");
				}
			}else if (request.getParameter("Action").equals("PauseJob")){
				try{ 
					logger.info(request.getParameter("Action"));
					logger.info(request.getParameter("JobId"));
					pauseJob(request.getParameter("JobId"));
					out.println("Job Pasued");
				}catch(Exception e){
					logger.error("Error Pausing Job"+e);
					out.println("Error Pausing Job");
				}
			}else if (request.getParameter("Action").equals("ResumeJob")){
				try{ 
					logger.info(request.getParameter("Action"));
					logger.info(request.getParameter("JobId"));
					resumeJob(request.getParameter("JobId"));
					out.println("Job Resumed");
				}catch(Exception e){
					logger.error("Error Resuming Job"+e);
					out.println("Error Resuming Job");
				}
			}
				
			
			
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}
	public void startJob(String jobId, String cronSch,String phaseType) throws Exception {
		    // define the job and tie it to our HelloJob class
			JobDetail job = newJob(DelegateExecution.class).withIdentity(jobId, defaultGroup).build();
			job.getJobDataMap().put("PHASE_TYPE", phaseType);
			// Trigger the job to run now, and then every 40 seconds
			Trigger trigger = newTrigger().withIdentity(jobId, defaultGroup).startNow().withSchedule(cronSchedule(cronSch)).build();
			// Tell quartz to schedule the job using our trigger
			StartSchedular.scheduler.scheduleJob(job, trigger);
		
		 
	}
	public void stopJob(String jobId) throws Exception{
		
			JobKey jobKey = new JobKey(jobId,defaultGroup);
			StartSchedular.scheduler.deleteJob(jobKey);
	 	
	}
	public void runJob(String jobId) throws Exception{
	 	StartSchedular.scheduler.triggerJob(jobKey(jobId,defaultGroup));
	}
	public void pauseJob(String jobId) throws Exception{
		 StartSchedular.scheduler.pauseJob(jobKey(jobId,defaultGroup));
	}
	public void resumeJob(String jobId) throws Exception{
		 StartSchedular.scheduler.resumeJob(jobKey(jobId,defaultGroup));
	}
	public void modifyJob(){
		
		
		
	}

}
