package com.admin.shared.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.managetransfer.client.PhasesDetailsDateH;
import com.managetransfer.client.PhasesDetailsH;
import com.managetransfer.client.PhasesDetailsIntH;
import com.managetransfer.client.PhasesDetailsStringH;
import com.managetransfer.client.PhasesDetails;
import com.managetransfer.hibernate.HibernateConnection;

public class PhasesDetailsServer {
	private   List<PhasesDetails> phaseDetails = null;
	private   ArrayList<PhasesDetails> arrayPhase = new ArrayList<PhasesDetails>();
	private   ArrayList<String> phaseTypeList = new ArrayList<String>();
	HibernateConnection hc = new HibernateConnection();
	final Logger logger = Logger.getLogger(PhasesDetailsServer.class.getName()) ;
	public HibernateConnection getHc() {
		return hc;
	}
	public void setHc(HibernateConnection hc) {
		
		String methodName= new String("PhasesDetailsServer.setHc");
		logger.info("Inside"+methodName);
		
		this.hc = hc;
		if(hc==null){
			logger.severe("hc is null");
		}
		if(hc.getHibernateSession()==null){
			logger.severe("session is null");
			hc.initOperation();
		}
		try{
		phaseDetails = arrayPhase;
		phaseTypeList.clear();
		phaseTypeList.add("ExportFromDocumentum");
		phaseTypeList.add("ImportToSharePoint");
		phaseTypeList.add("InitDocumentumBatch");
		phaseTypeList.add("Transformation");
		phaseTypeList.add("ImportToDocumentumD7");
		phaseTypeList.add("ExportACL");
		phaseTypeList.add("ImportACLD7");
		phaseTypeList.add("ExportProcessD6");
		phaseTypeList.add("ImportProcessD7");
		List list = hc.getObject("from PhasesDetailsH ");
		for (int i=0 ; i < list.size();i++){
			PhasesDetailsH phasesDetailsH= (PhasesDetailsH)list.get(i);
			PhasesDetails phasesDetails = convertDB2FrontEnd(phasesDetailsH);
			phaseDetails.add(phasesDetails);
		}
		if(phaseDetails.size()==0){
			phaseDetails.add(new PhasesDetails("dummy","ExportFromDocumentum",null));
		}
		}catch(Exception e){
			logger.severe("error"+e);
		}
		logger.info("Existing"+methodName);
	}
	public PhasesDetailsServer(){
	}
	public ArrayList<PhasesDetails> getPhaseResult(String input){
		return arrayPhase;
			
	}
	public boolean addPhase(PhasesDetails input) {
		String methodName= new String("PhasesDetailsServer.addPhase");
		logger.info("Inside"+methodName);
		try{
			PhasesDetailsH phaseDetailsH = convertFrontEnd2DB(input);
			hc.startBatchLevelTransaction();
			hc.clearObjectCache();
			hc.saveOperation(phaseDetailsH);
			hc.commitBatchLevelTransaction();
			phaseDetails.add(input);
		}catch(Exception e){
			hc.abortBatchLevelTransaction();
			System.out.println("eerr"+e);
		}
		
		logger.info("Existing"+methodName);
		return true;
	}
	public ArrayList<PhasesDetails> searchPhaseResult(String input) {
		String methodName= new String("PhasesDetailsServer.searchPhaseResult");
		logger.info("Inside"+methodName);
		// TODO Auto-generated method stub
		List<PhasesDetails> searchPhaseResult = null;
		ArrayList<PhasesDetails> searchArrayPhase = new ArrayList<PhasesDetails>();
		searchPhaseResult =searchArrayPhase;
		for(PhasesDetails sr : phaseDetails){
			if (sr.getPhaseName().contains(input)){
				searchPhaseResult.add(sr);
			}
		}
		logger.info("Existing"+methodName);
		return (ArrayList<PhasesDetails>) searchPhaseResult;
	}
	public Boolean deletePhase(String input) {
		String methodName= new String("PhasesDetailsServer.deletePhase");
		logger.info("Inside"+methodName);
		int i=0;
		for( PhasesDetails sr : phaseDetails){
			if (sr.getPhaseName().equals(input)){
				try{
					PhasesDetailsH pdh = convertFrontEnd2DB(sr);
					cascadeDelete(pdh);
					break; 
				}
				catch(Exception e){
				  System.out.println("Error"+e);
				  hc.abortBatchLevelTransaction();
				 
				}
			}
			i = i+1;
		}
		phaseDetails.remove(i);
		 
		logger.info("Existing"+methodName);
		return true;
	}
	public boolean editPhase(PhasesDetails phaseDetail) {
		String methodName= new String("PhasesDetailsServer.editPhase");
		logger.info("Inside"+methodName);
		PhasesDetailsH phaseDetailsH = null;
		try{
			int i=0;
			for( PhasesDetails sr : phaseDetails){
				if (sr.getPhaseName().equals(phaseDetail.getPhaseName())){
					phaseDetailsH = convertFrontEnd2DB(phaseDetail);
					break;
				}
				i = i+1;
			}
			phaseDetails.remove(i);
			phaseDetails.add(i, phaseDetail);
			try{
				cascadeDelete(phaseDetailsH);
				hc.startBatchLevelTransaction();
				hc.clearObjectCache();
				hc.saveOperation(phaseDetailsH);
				hc.commitBatchLevelTransaction();
			} catch(Exception e ){
				System.out.println("Exception::"+e);
			}
			
		}catch(Exception e){
			hc.abortBatchLevelTransaction();
			System.out.println("eerr"+e);
		}
		logger.info("Existing"+methodName);
		return true;
	}
	public ArrayList<String> getPhaseTypeList() {
		return phaseTypeList;
	}
	
	public  PhasesDetails convertDB2FrontEnd(PhasesDetailsH phaseDetailsH){
		String methodName= new String("PhasesDetailsServer.convertDB2FrontEnd");
		logger.info("Inside"+methodName);
		PhasesDetails phasesDetails = new PhasesDetails();
		if(phaseDetailsH != null){
			phasesDetails.setPhaseName(phaseDetailsH.getPhaseName());
			phasesDetails.setPhaseType(phaseDetailsH.getPhaseType());
			Map<String,PhasesDetailsDateH> pdd = phaseDetailsH.getPhaseDetailsDate();
			Map<String,PhasesDetailsStringH> pds = phaseDetailsH.getPhaseDetailsString();
			Map<String,PhasesDetailsIntH> pdi = phaseDetailsH.getPhaseDetailsInt();
			HashMap<String,String> hmString = new HashMap<String,String>();
			HashMap<String,Date> hmDate = new HashMap<String,Date>();
			HashMap<String,Integer> hmInteger = new HashMap<String,Integer>();
			if(pdd!=null && pdd.size()!= 0){
				Iterator iterator = pdd.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry mapEntry = (Map.Entry) iterator.next();
					PhasesDetailsDateH phasesDetailsDate = (PhasesDetailsDateH)mapEntry.getValue();
					hmDate.put((String)mapEntry.getKey(), phasesDetailsDate.getParameterValue());
					
				}
			}
			
			if(pds!=null && pds.size()!= 0){
				Iterator iterator = pds.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry mapEntry = (Map.Entry) iterator.next();
					PhasesDetailsStringH phasesDetailsDate = (PhasesDetailsStringH)mapEntry.getValue();
					hmString.put((String)mapEntry.getKey(), phasesDetailsDate.getParameterValue());
					logger.info("hmString"+(String)mapEntry.getKey());
					logger.info("phasesDetails.getParameterName"+phasesDetails.getPhaseName());
				}
			}
			if(pdi!=null && pdi.size()!= 0){
				Iterator iterator = pdi.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry mapEntry = (Map.Entry) iterator.next();
					PhasesDetailsIntH phasesDetailsDate = (PhasesDetailsIntH)mapEntry.getValue();
					hmInteger.put((String)mapEntry.getKey(), phasesDetailsDate.getParameterValue());
					
				}
			}
			phasesDetails.setPhaseParametersDate(hmDate);
			phasesDetails.setPhaseParametersInteger(hmInteger);
			phasesDetails.setPhaseParametersString(hmString);
		} 
		logger.info("Existing"+methodName);
		return phasesDetails;
		 
	}
public  PhasesDetailsH convertFrontEnd2DB(PhasesDetails phaseDetails){
	String methodName= new String("PhasesDetailsServer.convertFrontEnd2DB");
	logger.info("Inside"+methodName);
		PhasesDetailsH phasesDetailsH = new PhasesDetailsH();
		if(phaseDetails!=null){
		    phasesDetailsH.setPhaseName(phaseDetails.getPhaseName());
			phasesDetailsH.setPhaseType(phaseDetails.getPhaseType());
			Map<String,PhasesDetailsDateH> pdd = new HashMap(0);
			Map<String,PhasesDetailsStringH> pds =new HashMap(0);
			Map<String,PhasesDetailsIntH> pdi =new HashMap(0);
			HashMap<String,String> hmString = phaseDetails.getPhaseParametersString();
			HashMap<String,Date> hmDate =phaseDetails.getPhaseParametersDate();
			HashMap<String,Integer> hmInteger = phaseDetails.getPhaseParametersInteger();
			if(hmString!=null && hmString.size()!=0){
				Iterator iterator = hmString.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry mapEntry = (Map.Entry) iterator.next();
				 	PhasesDetailsStringH phaseDetailsStringH = new PhasesDetailsStringH();
					phaseDetailsStringH.setParameterName((String)mapEntry.getKey());
					phaseDetailsStringH.setPhaseName(phaseDetails.getPhaseName());
					phaseDetailsStringH.setParameterValue((String)mapEntry.getValue());
					pds.put((String)mapEntry.getKey(), phaseDetailsStringH);
					logger.info("phaseDetailsStringH.getPhaseName"+phaseDetailsStringH.getPhaseName());
					logger.info("phaseDetailsStringH.getParameterName"+phaseDetailsStringH.getParameterName());
				}
			}
			if(hmDate!=null && hmDate.size()!=0){
				Iterator iterator = hmDate.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry mapEntry = (Map.Entry) iterator.next();
					PhasesDetailsDateH phaseDetailsStringH = new PhasesDetailsDateH();
					phaseDetailsStringH.setParameterName((String)mapEntry.getKey());
					phaseDetailsStringH.setPhaseName(phaseDetails.getPhaseName());
					phaseDetailsStringH.setParameterValue((Date)mapEntry.getValue());
					pdd.put((String)mapEntry.getKey(), phaseDetailsStringH);
				}
			}
			if(hmInteger!=null && hmInteger.size()!=0){
				Iterator iterator = hmInteger.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry mapEntry = (Map.Entry) iterator.next();
					PhasesDetailsIntH phaseDetailsStringH = new PhasesDetailsIntH();
					phaseDetailsStringH.setParameterName((String)mapEntry.getKey());
					phaseDetailsStringH.setPhaseName(phaseDetails.getPhaseName());
					phaseDetailsStringH.setParameterValue((Integer)mapEntry.getValue());
					pdi.put((String)mapEntry.getKey(), phaseDetailsStringH);
				}
			}
			
			phasesDetailsH.setPhaseDetailsDate(pdd);
			phasesDetailsH.setPhaseDetailsInt(pdi);
			phasesDetailsH.setPhaseDetailsString(pds);
		}
		logger.info("Existing"+methodName);
		return phasesDetailsH;
	}
	public void cascadeDelete(PhasesDetailsH pdh){
		String methodName= new String("PhasesDetailsServer.cascadeDelete");
		logger.info("Inside"+methodName);
		try{
			hc.clearObjectCache();
			
			Map<String,PhasesDetailsDateH> pdd = pdh.getPhaseDetailsDate();
			Map<String,PhasesDetailsStringH> pds = pdh.getPhaseDetailsString();
			Map<String,PhasesDetailsIntH> pdi = pdh.getPhaseDetailsInt();
			if(pdd!=null && pdd.size()!= 0){
				Iterator iterator = pdd.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry mapEntry = (Map.Entry) iterator.next();
					PhasesDetailsDateH phasesDetailsDate = (PhasesDetailsDateH)mapEntry.getValue();
					hc.startBatchLevelTransaction();
					hc.deleteOperation(phasesDetailsDate);
					hc.commitBatchLevelTransaction();
				}
			}
			
			if(pds!=null && pds.size()!= 0){
				Iterator iterator = pds.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry mapEntry = (Map.Entry) iterator.next();
					PhasesDetailsStringH phasesDetailsDate = (PhasesDetailsStringH)mapEntry.getValue();
					hc.startBatchLevelTransaction();
					hc.deleteOperation(phasesDetailsDate);
					hc.commitBatchLevelTransaction();
				}
			}
			if(pdi!=null && pdi.size()!= 0){
				Iterator iterator = pdi.entrySet().iterator();
				while(iterator.hasNext()){
					Map.Entry mapEntry = (Map.Entry) iterator.next();
					PhasesDetailsIntH phasesDetailsDate = (PhasesDetailsIntH)mapEntry.getValue();
					hc.startBatchLevelTransaction();
					hc.deleteOperation(phasesDetailsDate);
					hc.commitBatchLevelTransaction();
				}
			}
			hc.startBatchLevelTransaction();
			hc.deleteOperation(pdh);
			hc.commitBatchLevelTransaction();
			 
		}catch(Exception e){
			logger.severe("Error "+e);
		}
		logger.info("existing"+methodName);
	}
	public ArrayList<String> getPhaseList() {
		ArrayList<String> phaseList = new ArrayList<String>();
		for(int i=0; i < phaseDetails.size() ;i++){
			phaseList.add(phaseDetails.get(i).getPhaseName());
		}
		return phaseList;
	}

}
