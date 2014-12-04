package com.managertransfer.common.job;

import org.apache.log4j.Logger;
import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.UnableToInterruptJobException;

import com.managetransfer.documetum.ExportACL;
import com.managetransfer.documetum.ExportDocumentum;
import com.managetransfer.documetum.ImportACLD7;
import com.managetransfer.documetum.ImportDocumentumD7;
import com.managetransfer.documetum.InitSequence;
import com.managetransfer.record.TransformationHandler;
import com.managetransfer.sharepoint.SharePointImport;


public class DelegateExecution implements Job, InterruptableJob {
	Object phaseObject ;
	String phaseType = new String(""); 
	private static org.apache.log4j.Logger logger = Logger.getLogger(DelegateExecution.class);
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("Inside execute");
		logger.info("this"+this);
		JobKey jobKey = arg0.getJobDetail().getKey();
		logger.info("Job Name"+jobKey.getName());
		logger.info("Phase Type"+arg0.getMergedJobDataMap().getString("PHASE_TYPE"));
		phaseType  = arg0.getMergedJobDataMap().getString("PHASE_TYPE");
		String jobName =jobKey.getName();
		int processId =  Integer.parseInt(jobName.substring(jobName.lastIndexOf("_")+1,jobName.length()));
		logger.info(processId);
		jobName = jobName.substring(0,jobName.lastIndexOf("_") );
		int sequenceNumber =   Integer.parseInt(jobName.substring(jobName.lastIndexOf("_")+1,jobName.length()));
		logger.info(sequenceNumber);
		String sequenceName = jobName.substring(0,jobName.lastIndexOf("_") );
		logger.info(sequenceName);
		if(phaseType.equals("ExportFromDocumentum")){
			try{
				ExportDocumentum ed = new ExportDocumentum();
				ed.setSequenceName(sequenceName);
				ed.setSequenceNumber(sequenceNumber);
				ed.setProcessId(processId);
				ed.initOperation();
				phaseObject= ed;
				ed.executeOperation();
			} catch(Exception e){
				logger.error(e);
			}

		}else if(phaseType.equals("ImportToSharePoint")){
			try{
				SharePointImport si = new SharePointImport();
				si.setSequenceName(sequenceName);
				si.setSequenceNumber(sequenceNumber);
				si.setProcessId(processId);
				si.initOperation();
				phaseObject= si;
				si.executeOperation();
			} catch(Exception e){
				logger.error(e);
			}

		}else if(phaseType.equals("Transformation")){
			try{
				TransformationHandler  th = new TransformationHandler();
				th.setSequenceName(sequenceName);
				th.setSequenceNumber(sequenceNumber);
				th.setProcessId(processId);
				th.initOperation();
				phaseObject= th;
				th.executeOperation();
			} catch(Exception e){
				logger.error(e);
			}
		}else if(phaseType.equals("InitDocumentumBatch")){
			try{
				InitSequence  is = new InitSequence();
				is.setSequenceName(sequenceName);
				is.setSequenceNumber(sequenceNumber);
				is.setProcessId(processId);
				is.initOperation();
				phaseObject= is;
				is.executeOperation();
			} catch(Exception e){
				logger.error(e);
			}
		}else if(phaseType.equals("ExportACL")){
			try{
				ExportACL  ea = new ExportACL();
				ea.setSequenceName(sequenceName);
				ea.setSequenceNumber(sequenceNumber);
				ea.setProcessId(processId);
				ea.initOperation();
				phaseObject= ea;
				ea.executeOperation();
			} catch(Exception e){
				logger.error(e);
			}
		}else if(phaseType.equals("ImportACLD7")){
			try{
				ImportACLD7  ea = new ImportACLD7();
				ea.setSequenceName(sequenceName);
				ea.setSequenceNumber(sequenceNumber);
				ea.setProcessId(processId);
				ea.initOperation();
				phaseObject= ea;
				ea.executeOperation();
			} catch(Exception e){
				logger.error(e);
			}
		}else if(phaseType.equals("ImportToDocumentumD7")){
			try{
				ImportDocumentumD7  ea = new ImportDocumentumD7();
				ea.setSequenceName(sequenceName);
				ea.setSequenceNumber(sequenceNumber);
				ea.setProcessId(processId);
				ea.initOperation();
				phaseObject= ea;
				ea.executeOperation();
			} catch(Exception e){
				logger.error(e);
			}
		}

	}
	@Override
	public void interrupt() throws UnableToInterruptJobException {
		logger.info("Inside DelegateJobExecution.Interrupt");
		if(phaseType.equals("ExportFromDocumentum")){
			ExportDocumentum ed = (ExportDocumentum) phaseObject;
			ed.setInterruptFlag(true);
		}else if(phaseType.equals("InitDocumentumBatch")){
			InitSequence  is = (InitSequence) phaseObject;
			is.setInterruptFlag(true);
		}else if(phaseType.equals("Transformation")){
			TransformationHandler  th = (TransformationHandler) phaseObject;
			th.setInterruptFlag(true);
		}else if(phaseType.equals("ExportACL")){
			ExportACL ea = (ExportACL) phaseObject;
			ea.setInterruptFlag(true);
		}else if(phaseType.equals("ImportACLD7")){
			ImportACLD7 ea = (ImportACLD7) phaseObject;
			ea.setInterruptFlag(true);
		}else if(phaseType.equals("ImportToDocumentumD7")){
			ImportDocumentumD7 ea = (ImportDocumentumD7) phaseObject;
			ea.setInterruptFlag(true);
		}
	}


}
