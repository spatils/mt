package com.admin.shared.session;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;	
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.managetransfer.client.JobDetails;
import com.managetransfer.client.JobServerDetails;
import com.managetransfer.client.PhasesDetailsH;
import com.managetransfer.client.SequenceDetails;
import com.managetransfer.client.SequenceDetailsH;
import com.managetransfer.client.SequenceDetailsMap;
import com.managetransfer.client.SequenceDetailsMapH;
import com.managetransfer.hibernate.HibernateConnection;

public class SequencesDetailsServer {
	List<SequenceDetails> sequenceResult = null;
	ArrayList<SequenceDetails> arraySequence = new ArrayList<SequenceDetails>();
	private  List<SequenceDetailsMap> sequenceDetailsMap = new ArrayList<SequenceDetailsMap>();
	HibernateConnection hc = new HibernateConnection();
	List<JobDetails> jobResult = null;
	ArrayList<JobDetails> arrayJob = new ArrayList<JobDetails>();
	List<JobServerDetails> jobServerDetails = new ArrayList<JobServerDetails>();
	String ACTION = "?Action=GetJobDetails";  //
	final Logger logger = Logger.getLogger(SequencesDetailsServer.class.getName()) ;
	public HibernateConnection getHc() {
		return hc;
	}
	public void setHc(HibernateConnection hc) {
		sequenceResult = arraySequence;
		String methodName= new String("SequencesDetailsServer.setHc");
		logger.info("Inside"+methodName);
		try{
			this.hc = hc;
			List list = hc.getObject("from SequenceDetailsH ");
			for (int i=0 ; i < list.size();i++){
				SequenceDetailsH sequenceDetailsH= (SequenceDetailsH)list.get(i);
				SequenceDetails sequenceDetails = convertDB2FrontEnd(sequenceDetailsH);
				sequenceResult.add(sequenceDetails);
			}
			List listJob = hc.getObject("from 	JobServerDetails ");
			jobResult = arrayJob;
			for (int i=0 ; i < listJob.size();i++){
				JobServerDetails jsd = (JobServerDetails) listJob.get(i);
				jobServerDetails.add(jsd);
				jobResult.addAll(getActiveJobDetails(jsd));
			}
			if (sequenceResult.size()==0) {
				sequenceDetailsMap.add(new SequenceDetailsMap("ImportClaimDocument",0,"cron fequ","machine",10,"ExportFromDocumentum",100,1000,0));
				sequenceDetailsMap.add(new SequenceDetailsMap("ImportClaimDocument",1,"cron fequ","machine",10,"ExportFromDocumentum",100,1000,0));
				sequenceDetailsMap.add(new SequenceDetailsMap("ImportClaimDocument",2,"cron fequ","machine",10,"ExportFromDocumentum",100,1000,0));
				sequenceDetailsMap.add(new SequenceDetailsMap("ImportClaimDocument",3,"cron fequ","machine",10,"ExportFromDocumentum",100,1000,0));
				sequenceResult.add(new SequenceDetails("ImportClaimDocument",4,"dev",sequenceDetailsMap));
			}
		} catch(Exception e){
			logger.severe("Exception"+e);
		}
		logger.info("Existing"+methodName);
	}
	public SequencesDetailsServer(){
		
	}
	public ArrayList<SequenceDetails> getSequenceResult(String input){
		return arraySequence;
			
	}
	public boolean addSequence(SequenceDetails input) {
		String methodName= new String("SequencesDetailsServer.addSequence");
		logger.info("Inside"+methodName);
		try {
			SequenceDetailsH sequenceDetailsH = convertFrontEnd2DB(input);
			hc.clearObjectCache();
			hc.startBatchLevelTransaction();
			hc.saveOperation(sequenceDetailsH);
			hc.commitBatchLevelTransaction();
			sequenceResult.add(input);
		}catch(Exception e){
			System.out.println("Error"+e);
			logger.severe("Error"+e);
			hc.abortBatchLevelTransaction();
		}
		logger.info("Existing"+methodName);
		return true;
	}
	public ArrayList<SequenceDetails> searchSequenceResult(String input) {
		// TODO Auto-generated method stub
		List<SequenceDetails> searchSequenceResult = null;
		ArrayList<SequenceDetails> searchArraySequence = new ArrayList<SequenceDetails>();
		searchSequenceResult =searchArraySequence;
		for(SequenceDetails sr : sequenceResult){
			if (sr.getSequenceName().contains(input)){
				searchSequenceResult.add(sr);
			}
		}
		return (ArrayList<SequenceDetails>) searchSequenceResult;
	}
	public Boolean deleteSequence(String input) {
		// TODO Auto-generated method stub
		int i=0;
		for( SequenceDetails sr : sequenceResult){
			if (sr.getSequenceName().equals(input)){
				try{
					SequenceDetailsH sdh = convertFrontEnd2DB(sr);
					deleteJobDetails(sdh);
					cascadeDelete(sdh);
					break;
				}
				catch(Exception e){
					System.out.println("Error"+e);
				}
			}
			i = i+1;
		}
		sequenceResult.remove(i);
		return true;
	}
	public boolean editSequence(SequenceDetails sequenceDetail) {
		String methodName= new String("PhasesDetailsServer.editPhase");
		logger.info("Inside"+methodName);
		SequenceDetailsH sequenceDetailsH = null;
		SequenceDetailsH sequenceDetailsHSR = null;
		int i=0;
		for( SequenceDetails sr : sequenceResult){
			if (sr.getSequenceName().equals(sequenceDetail.getSequenceName())){
				try{
					logger.info("Found maching Sequence");
					sequenceDetailsHSR = convertFrontEnd2DB(sr);
					logger.info("Converted Old Sequence");
					deleteJobDetails(sequenceDetailsHSR);
					logger.info("Deleted Job Details for old Sequence");
					break;
			 	}
				catch(Exception e){
					logger.severe("Error"+e);
				}
			}
			i = i+1;
		}
		try{
			cascadeDelete(sequenceDetailsHSR);
			logger.info("Deleted old Sequence");
			sequenceDetailsH = convertFrontEnd2DB(sequenceDetail);
			logger.info("Converted new Sequence");
			hc.startBatchLevelTransaction();
			hc.clearObjectCache();
			createJobEntries(sequenceDetailsH);
			logger.info("Created new job entries");
			hc.saveOperation(sequenceDetailsH);
			logger.info("Saved New sequence details");
			sequenceResult.remove(i);
			sequenceResult.add(i, sequenceDetail);
			hc.commitBatchLevelTransaction();
		} catch(Exception e ){
			System.out.println("Exception::"+e);
			hc.abortBatchLevelTransaction();
		}
		
		return true;
	}
	
	 
	public  SequenceDetails convertDB2FrontEnd(SequenceDetailsH sequenceDetailsH){
		String methodName= new String("SequencesDetailsServer.convertDB2FrontEnd");
		logger.info("Inside"+methodName);
		SequenceDetails sequenceDetails = new SequenceDetails();
		try{
			sequenceDetails.setNumberOfPhases(sequenceDetailsH.getNumberOfPhases());
			sequenceDetails.setSequenceName(sequenceDetailsH.getSequenceName());
			logger.info("sequenceDetailsH.getSequenceName()"+sequenceDetailsH.getSequenceName());
			Map<Integer,SequenceDetailsMapH> sdmh = sequenceDetailsH.getSequenceDetailsMap();
			List<SequenceDetailsMap> sequenceDetailsMap = new ArrayList<SequenceDetailsMap>();
			Iterator iterator = sdmh.entrySet().iterator();
			int i = 0;
			while(iterator.hasNext()){
				Map.Entry mapEntry = (Map.Entry) iterator.next();
				SequenceDetailsMapH sdmp =(SequenceDetailsMapH)mapEntry.getValue();
				sequenceDetailsMap.add(new SequenceDetailsMap(sdmp.getSequenceName(),i,sdmp.getCronFrequency(),sdmp.getMachineName(),sdmp.getThreadCount(),sdmp.getPhaseName(),sdmp.getCommitCount(),sdmp.getBatchSize(),sdmp.getDailySplit()));
				i = i + 1;
			}
			sequenceDetails.setSequenceDetails(sequenceDetailsMap);
		}catch(Exception e){
			logger.severe("Exception e"+e);
		}
		logger.info("Exiting"+methodName);
		return sequenceDetails;
	}
	public  SequenceDetailsH convertFrontEnd2DB(SequenceDetails sequenceDetails){
		 String methodName= new String("SequencesDetailsServer.convertFrontEnd2DB");
		 logger.info("Inside"+methodName);
		 SequenceDetailsH sequenceDetailsH = new SequenceDetailsH ();
		 Map<Integer,SequenceDetailsMapH> sdmp = new HashMap(0);
		 try{
			 sequenceDetailsH.setNumberOfPhases(sequenceDetails.getNumberOfPhases());
			 sequenceDetailsH.setSequenceName(sequenceDetails.getSequenceName());
			 List<SequenceDetailsMap>  sequenceDetailsMap= sequenceDetails.getSequenceDetails();
			 if(sequenceDetailsMap!=null){
				 logger.info("sze of list is "+ sequenceDetailsMap.size());
			 }else{
				 logger.info("List is null");
			 }
			 logger.info("sequenceDetails.getSequenceName()"+sequenceDetails.getSequenceName());
		    if( sequenceDetails.getSequenceDetails() !=null &&  sequenceDetails.getSequenceDetails().size()>0){
				 for(int i=0;i<sequenceDetails.getNumberOfPhases(); i++){
					 SequenceDetailsMap sdm = sequenceDetails.getSequenceDetails().get(i);
					 sdmp.put(i,new SequenceDetailsMapH(sequenceDetails.getSequenceName(),sdm.getSequenceNumber(),sdm.getCronFrequency(),sdm.getMachineName(),sdm.getThreadCount(),sdm.getPhaseName(),sdm.getCommitCount(),sdm.getBatchSize(),sdm.getDailySplit()));
				 }
				 
			 }
		     logger.info("size of sdmp"+sdmp.size());
			 sequenceDetailsH.setSequenceDetailsMap(sdmp);
		 } catch(Exception e){
			 logger.severe("Exception e"+e); 
		 }
		 logger.info("Exiting"+methodName);
		 return sequenceDetailsH;
	  }
	public void cascadeDelete(SequenceDetailsH sequenceDetailsH){
		 String methodName= new String("SequencesDetailsServer.cascadeDelete");
		 logger.info("Inside"+methodName);
		
		 try{
			 hc.clearObjectCache();
			 logger.info("getNumberOfPhases"+sequenceDetailsH.getNumberOfPhases());
			 logger.info("getSequenceDetailsMap"+sequenceDetailsH.getSequenceDetailsMap());
			 Map<Integer,SequenceDetailsMapH> sdmh = sequenceDetailsH.getSequenceDetailsMap();
			 
			 if(sdmh != null){
				 logger.info("getSequenceDetailsMap"+sdmh.size());
			 }else{
				 logger.info("Map is null" );
			 }
			 
			 Iterator iterator = sdmh.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry mapEntry = (Map.Entry) iterator.next();
					SequenceDetailsMapH sdmp =(SequenceDetailsMapH)mapEntry.getValue();
					logger.info("Inside Iterator"+sdmp.getSequenceName());
					hc.startBatchLevelTransaction();
					hc.deleteOperation(sdmp);
					hc.commitBatchLevelTransaction();
				}
			 hc.startBatchLevelTransaction();
			 hc.deleteOperation(sequenceDetailsH);
			 hc.commitBatchLevelTransaction();
			  
		 }
		 catch(Exception e){
		 logger.severe("Exception e"+e); 
		 hc.abortBatchLevelTransaction();
		 }
		 logger.info("Exiting"+methodName);
	}
	private void createJobEntries(SequenceDetailsH sequenceDetailsH) throws Exception{
		 String methodName= new String("SequencesDetailsServer.CreateJobEntries");
		 logger.info("Inside"+methodName);
		 Map<Integer,SequenceDetailsMapH> sdmp = sequenceDetailsH.getSequenceDetailsMap();
			for (int i=0; i<sequenceDetailsH.getNumberOfPhases();i++){
				SequenceDetailsMapH sdm = sdmp.get(i);
				PhasesDetailsH pds= new PhasesDetailsH();
				List listPhases = hc.getObject(" from PhasesDetailsH where phaseName = '"+sdm.getPhaseName()+"'");
				for(int l=0;l< listPhases.size();l++){
					pds = (PhasesDetailsH) listPhases.get(l);
				}
				for (int l=0; l< sdm.getThreadCount();l++){
					String jobName = sequenceDetailsH.getSequenceName()+"_"+i+"_"+l;
					for(JobServerDetails jds : jobServerDetails ){
						if(jds.getJobServerName().equals(sdm.getMachineName())){
							System.out.println("Stop Action");
							ACTION = "?Action=StartJob"; 
							String PARAMETER = "&JobId="+jobName;
							String CRON_SCH  = "&CronSch=";
							String CRON_SCH_VAL =sdm.getCronFrequency();
							CRON_SCH_VAL=  URLEncoder.encode(CRON_SCH_VAL, "UTF-8") ;
							//Get Phase Type
							String PHASE_TYPE = "&PhaseType="+pds.getPhaseType();
					        //Choose class name according to Phase type
						    URL JobServer = new URL(jds.getServer_url()+ACTION+PARAMETER+CRON_SCH+CRON_SCH_VAL+PHASE_TYPE);
							URLConnection yc =  JobServer.openConnection();
					        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
					        
					        String inputLine;
					        while ((inputLine = in.readLine()) != null) {
					        	logger.info(inputLine);
					        }
					        in.close();
					    }
					}
				}
			 }
		 
		 
		 logger.info("Exiting"+methodName);
	}
	private void deleteJobDetails(SequenceDetailsH sequenceDetailsH) throws Exception{
		String methodName= new String("SequencesDetailsServer.CreateJobEntries");
		logger.info("Inside"+methodName);
		Map<Integer,SequenceDetailsMapH> sdmp = sequenceDetailsH.getSequenceDetailsMap();
		for (int i=0; i<sequenceDetailsH.getNumberOfPhases();i++){
			SequenceDetailsMapH sdm = sdmp.get(i);
			for (int l=0; l< sdm.getThreadCount();l++){
				String jobName = sequenceDetailsH.getSequenceName()+"_"+i+"_"+l;
				for(JobServerDetails jds : jobServerDetails ){
					if(jds.getJobServerName().equals(sdm.getMachineName())){
						System.out.println("Stop Action");
						ACTION = "?Action=StopJob"; 
						String PARAMETER = "&JobId="+jobName;
						String url = jds.getServer_url()+ACTION+PARAMETER;
						URL JobServer = new URL(url);
				        URLConnection yc =  JobServer.openConnection();
				        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
				        String inputLine;
				        while ((inputLine = in.readLine()) != null) {
				        	logger.info(inputLine);
				        }
				        in.close();
				    }
				}
			}
		 }
		logger.info("Exiting"+methodName);
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
	public List<SequenceDetailsH> getSequencesList(){
		return hc.getObject(" from SequenceDetailsH ");
	}
	
	public List<SequenceDetailsMapH> getSequenceDetailsMapList(String seqName){
		//hard coding the sequence name temperarily
		return hc.getObject(" from SequenceDetailsMapH where sequence_name = '"+seqName+"'" );
	}
	public SequenceDetailsMap getSequenceMap(String sequenceName, int seqNumber) {
		String methodName= new String("SequencesDetailsServer.getSequenceMap");
		logger.info("Inside"+methodName);
		SequenceDetailsMap sqdmap = new SequenceDetailsMap();
		for (int i=0; i< sequenceResult.size();i++){
			logger.info("Selected sequenceName"+sequenceName);
			logger.info("Selected seqNumber"+seqNumber);
			if(sequenceResult.get(i).getSequenceName().equals(sequenceName)){
				sqdmap = sequenceResult.get(i).getSequenceDetails().get(seqNumber);
			}
		}
		logger.info("Selected Seq"+sqdmap.getPhaseName());
		logger.info("Exiting"+methodName);
		return sqdmap;
	}
}
