package com.managetransfer.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.managetransfer.client.ManageTransferService;
 
import com.managetransfer.shared.FieldVerifier;
 
import com.managetransfer.client.ConnectionDetails;
import com.managetransfer.client.ExpressionsDetails;
import com.managetransfer.client.JobDetails;
import com.managetransfer.client.MappingDetails;
import com.managetransfer.client.PhasesDetails;
import com.managetransfer.client.SequenceDetails;
import com.managetransfer.client.UserDetails;
import com.managetransfer.hibernate.GetRecordDetails;
import com.managetransfer.hibernate.HibernateConnection;
import com.admin.shared.session.AllSessions;
import com.admin.shared.session.BatchDetailsServer;
import com.admin.shared.session.ConnectionsDetailsServer;
import com.admin.shared.session.ExpressionsDetailsServer;
import com.admin.shared.session.MappingDetailsServer;
import com.admin.shared.session.PhasesDetailsServer;
import com.admin.shared.session.SchedularDetailsServer;
import com.admin.shared.session.SequencesDetailsServer;
import com.admin.shared.session.ThroughputDetailsServer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.admin.shared.session.AllSessions;
import com.admin.shared.session.BatchDetailsServer;
import com.admin.shared.session.ConnectionsDetailsServer;
import com.admin.shared.session.ExpressionsDetailsServer;
import com.admin.shared.session.LogsDetailsServer;
import com.admin.shared.session.MappingDetailsServer;
import com.admin.shared.session.PhasesDetailsServer;
import com.admin.shared.session.SchedularDetailsServer;
import com.admin.shared.session.SequencesDetailsServer;
import com.admin.shared.session.ThroughputDetailsServer;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
 
import com.managetransfer.client.BatchDetails;
import com.managetransfer.client.ConnectionDetails;
import com.managetransfer.client.ExpressionsDetails;
import com.managetransfer.client.JobDetails;
import com.managetransfer.client.LogsDetails;
import com.managetransfer.client.ManageTransferService;
import com.managetransfer.client.MappingDetails;
import com.managetransfer.client.PhasesDetails;
import com.managetransfer.client.SequenceDetails;
import com.managetransfer.client.SequenceDetailsMap;
import com.managetransfer.client.UserDetails;
import com.managetransfer.hibernate.GetRecordDetails;
import com.managetransfer.hibernate.HibernateConnection;
import com.managetransfer.shared.FieldVerifier;
/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ManageTransferServiceImpl extends RemoteServiceServlet implements
		ManageTransferService {
	private static org.apache.log4j.Logger logger = Logger.getLogger(ManageTransferServiceImpl.class);  
	String packageName = new String("com.managetransfer.businessobject.");
	 
	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
	@Override
	public UserDetails userLoginServiceCall(UserDetails userDetails) {
		// TODO Auto-generated method stub
		return userDetails;
	}

	@Override
	public Boolean userAuthentication(UserDetails userDetails) {
		// TODO Auto-generated method stub
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		as.setUser(userDetails);
		return true;
	}

	@Override
	public boolean isAuthenticated( ){
		System.out.println("Inside isAuthenticated ");
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		return as.isAuthenticated();
		 
	}
	@Override
	public ArrayList<String> getConnectionList() {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		System.out.println("hibernate connection class"+hc);
		ConnectionsDetailsServer  connectionsServer = new ConnectionsDetailsServer();
		connectionsServer.setHc(hc);
		return connectionsServer.getConnectionList();
	}

	@Override
	public ArrayList<ConnectionDetails> getConnectionResult(String input) {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		System.out.println("hibernate connection class"+hc);
		ConnectionsDetailsServer  connectionsServer = new ConnectionsDetailsServer();
		connectionsServer.setHc(hc);
		return connectionsServer.getConnectionResult("dev");
	}

	@Override
	public boolean addConnection(ConnectionDetails input) {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		ConnectionsDetailsServer  connectionsServer = new ConnectionsDetailsServer();
		connectionsServer.setHc(hc);
		connectionsServer.addConnection(input);
		return true;
	}

	@Override
	public ArrayList<ConnectionDetails> searchConnectionResult(String input) {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
	    ConnectionsDetailsServer  connectionsServer = new ConnectionsDetailsServer();
		connectionsServer.setHc(hc);
		return connectionsServer.searchConnectionResult(input);
		 
	}

	@Override
	public Boolean deleteConnection(String input) {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
	    ConnectionsDetailsServer  connectionsServer = new ConnectionsDetailsServer();
		connectionsServer.setHc(hc);
		return connectionsServer.deleteConnection(input);
	}

	@Override
	public boolean editConnection(ConnectionDetails connectionDetail) {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		ConnectionsDetailsServer  connectionsServer = new ConnectionsDetailsServer();
		connectionsServer.setHc(hc);
		return connectionsServer.editConnection(connectionDetail);
		 
	}

	@Override
	public ArrayList<SequenceDetails> getSequenceResult(String input) {
		// TODO Auto-generated method stub
		SequencesDetailsServer  sequencesServer = new SequencesDetailsServer();
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		sequencesServer.setHc(hc);
		return sequencesServer.getSequenceResult("dev");
		 
	}

	@Override
	public ArrayList<SequenceDetails> searchSequenceResult(String input) {
		// TODO Auto-generated method stub
		SequencesDetailsServer  sequencesServer = new SequencesDetailsServer();
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		sequencesServer.setHc(hc);
		return sequencesServer.searchSequenceResult(input);
	}

	@Override
	public Boolean deleteSequence(String input) {
		// TODO Auto-generated method stub
		SequencesDetailsServer  sequencesServer = new SequencesDetailsServer();
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		sequencesServer.setHc(hc);
		return sequencesServer.deleteSequence(input);
	}

	@Override
	public boolean addSequence(SequenceDetails input) {
		// TODO Auto-generated method stub
		SequencesDetailsServer  sequencesServer = new SequencesDetailsServer();
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		sequencesServer.setHc(hc);
		return sequencesServer.addSequence(input);
	}

	@Override
	public boolean editSequence(SequenceDetails sequenceDetail) {
		// TODO Auto-generated method stub
		SequencesDetailsServer  sequencesServer = new SequencesDetailsServer();
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		sequencesServer.setHc(hc);
		return sequencesServer.editSequence(sequenceDetail);
	}

	@Override
	public ArrayList<String> getConnectionTypeList() {
		// TODO Auto-generated method stub
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		ConnectionsDetailsServer  connectionsServer = new ConnectionsDetailsServer();
		connectionsServer.setHc(hc);
		return connectionsServer.getConnectionTypeList();
	}
	@Override
	public ArrayList<String> getExpressionList() {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		ExpressionsDetailsServer  expresionsServer = new ExpressionsDetailsServer();
		expresionsServer.setHc(hc);
		return expresionsServer.getExpressionList();
	}
	@Override
	public ArrayList<ExpressionsDetails> getExpressionsResult(String input) {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		ExpressionsDetailsServer  expresionsServer = new ExpressionsDetailsServer();
		expresionsServer.setHc(hc);
		return expresionsServer.getExpressionResult("dev");
	}

	@Override
	public ArrayList<ExpressionsDetails> searchExpressions(String input) {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		ExpressionsDetailsServer  expresionsServer = new ExpressionsDetailsServer();
		expresionsServer.setHc(hc);
		return expresionsServer.searchExpressionResult(input);
	}

	@Override
	public Boolean deleteExpression(String input) {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		ExpressionsDetailsServer  expresionsServer = new ExpressionsDetailsServer();
		expresionsServer.setHc(hc);
		return expresionsServer.deleteExpression(input);
	}

	@Override
	public ArrayList<String> getExpressionTypeList() {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		ExpressionsDetailsServer  expresionsServer = new ExpressionsDetailsServer();
		expresionsServer.setHc(hc);
		return expresionsServer.getExpressionTypeList();
	}

	@Override
	public boolean addExpression(ExpressionsDetails input) {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		ExpressionsDetailsServer  expresionsServer = new ExpressionsDetailsServer();
		expresionsServer.setHc(hc);
		return expresionsServer.addExpression(input);
	}

	@Override
	public boolean editExpression(ExpressionsDetails expressionDetail) {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		ExpressionsDetailsServer  expresionsServer = new ExpressionsDetailsServer();
		expresionsServer.setHc(hc);
		return expresionsServer.editExpression(expressionDetail);
	}
	@Override
	public ArrayList<String> getMachineList() {
		try{
			SchedularDetailsServer schedularDetailsServer= new SchedularDetailsServer();
			AllSessions as = AllSessions.getInstance();
			as.setRequest(getThreadLocalRequest());
			HibernateConnection hc = new HibernateConnection();
			hc = as.getHc();
			schedularDetailsServer.setHc(hc);
			return schedularDetailsServer.machineList();
		}
		catch(Exception e){
			System.out.println("Excepton"+e);
			return null;
		}
	}
	@Override
	public boolean runJob(JobDetails jobDetail) {
		try{
			SchedularDetailsServer schedularDetailsServer= new SchedularDetailsServer();
			AllSessions as = AllSessions.getInstance();
			as.setRequest(getThreadLocalRequest());
			HibernateConnection hc = new HibernateConnection();
			hc = as.getHc();
			schedularDetailsServer.setHc(hc);
			return schedularDetailsServer.runJob(jobDetail);
		}
		catch(Exception e){
			System.out.println("Excepton"+e);
			return false;
		}
	 
	}

	public boolean changeStateJob(JobDetails jobDetail) {
		try{
			SchedularDetailsServer schedularDetailsServer= new SchedularDetailsServer();
			AllSessions as = AllSessions.getInstance();
			as.setRequest(getThreadLocalRequest());
			HibernateConnection hc = new HibernateConnection();
			hc = as.getHc();
			schedularDetailsServer.setHc(hc);
			return schedularDetailsServer.changeStateJob(jobDetail);
		}
		catch(Exception e){
			System.out.println("Excepton"+e);
			return false;
		}
	}

	@Override
	public ArrayList<JobDetails> getJobResult(String input) {
	try{
			SchedularDetailsServer schedularDetailsServer= new SchedularDetailsServer();
			AllSessions as = AllSessions.getInstance();
			as.setRequest(getThreadLocalRequest());
			HibernateConnection hc = new HibernateConnection();
			hc = as.getHc();
			schedularDetailsServer.setHc(hc);
			return schedularDetailsServer.getJobResult(input);
		}
		catch(Exception e){
			System.out.println("Excepton"+e);
			return null;
		}
	}

	@Override
	public ArrayList<JobDetails> searchJob(String input) {
		try{
			SchedularDetailsServer schedularDetailsServer= new SchedularDetailsServer();
			AllSessions as = AllSessions.getInstance();
			as.setRequest(getThreadLocalRequest());
			HibernateConnection hc = new HibernateConnection();
			hc = as.getHc();
			schedularDetailsServer.setHc(hc);
			return schedularDetailsServer.searchJobResult( input);
		}
		catch(Exception e){
			System.out.println("Excepton"+e);
			return null;
		}
	}

	@Override
	public Boolean deleteJob(JobDetails jobDetail) {
		try{
			SchedularDetailsServer schedularDetailsServer= new SchedularDetailsServer();
			AllSessions as = AllSessions.getInstance();
			as.setRequest(getThreadLocalRequest());
			HibernateConnection hc = new HibernateConnection();
			hc = as.getHc();
			schedularDetailsServer.setHc(hc);
			return schedularDetailsServer.deleteJob(jobDetail);
		}
		catch(Exception e){
			System.out.println("Excepton"+e);
			return null;
		}
	}

	@Override
	public ArrayList<String> getJobTypeList() {
		
		
		return null;
	}

	@Override
	public boolean addJob(JobDetails input) {
		try{
			SchedularDetailsServer schedularDetailsServer= new SchedularDetailsServer();
			AllSessions as = AllSessions.getInstance();
			as.setRequest(getThreadLocalRequest());
			HibernateConnection hc = new HibernateConnection();
			hc = as.getHc();
			schedularDetailsServer.setHc(hc);
			return schedularDetailsServer.addJob(input);	
		}
		catch(Exception e){
			System.out.println("Excepton"+e);
			return false;
		}
	}

	@Override
	public boolean editJob(JobDetails jobDetail) {
		try{
			SchedularDetailsServer schedularDetailsServer= new SchedularDetailsServer();
			AllSessions as = AllSessions.getInstance();
			as.setRequest(getThreadLocalRequest());
			HibernateConnection hc = new HibernateConnection();
			hc = as.getHc();
			schedularDetailsServer.setHc(hc);
			return schedularDetailsServer.editJob(jobDetail);
		}
		catch(Exception e){
			System.out.println("Excepton"+e);
			return false;
		}
	}
	public ArrayList<String> getPhaseList() {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		PhasesDetailsServer phaseDetailsServer = new PhasesDetailsServer();
		phaseDetailsServer.setHc(hc);
		return phaseDetailsServer.getPhaseList();
	}
	@Override
	public ArrayList<PhasesDetails> getPhaseResult(String input) {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		PhasesDetailsServer phaseDetailsServer = new PhasesDetailsServer();
		phaseDetailsServer.setHc(hc);
		return phaseDetailsServer.getPhaseResult(input);
	}

	@Override
	public ArrayList<PhasesDetails> searchPhase(String input) {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		PhasesDetailsServer phaseDetailsServer = new PhasesDetailsServer();
		phaseDetailsServer.setHc(hc);
		return phaseDetailsServer.searchPhaseResult(input);
	}

	@Override
	public Boolean deletePhase(String input) {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		PhasesDetailsServer phaseDetailsServer = new PhasesDetailsServer();
		phaseDetailsServer.setHc(hc);
		return phaseDetailsServer.deletePhase(input);
	}

	@Override
	public ArrayList<String> getPhaseTypeList() {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		PhasesDetailsServer phaseDetailsServer = new PhasesDetailsServer();
		phaseDetailsServer.setHc(hc);
		return phaseDetailsServer.getPhaseTypeList();
	}

	@Override
	public boolean addPhase(PhasesDetails input) {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		PhasesDetailsServer phaseDetailsServer = new PhasesDetailsServer();
		phaseDetailsServer.setHc(hc);
		return phaseDetailsServer.addPhase(input);
	}

	@Override
	public boolean editPhase(PhasesDetails input) {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		PhasesDetailsServer phaseDetailsServer = new PhasesDetailsServer();
		phaseDetailsServer.setHc(hc);
		return phaseDetailsServer.editPhase(input);
	}

	@Override
	public ArrayList<MappingDetails> getMappingResult(String input) {
		MappingDetailsServer mappingDetailsServer = new MappingDetailsServer();
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		mappingDetailsServer.setHc(hc);
		return mappingDetailsServer.getMappingResult(input);
	}

	@Override
	public ArrayList<MappingDetails> searchMappingResult(String input) {
		MappingDetailsServer mappingDetailsServer = new MappingDetailsServer();
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		mappingDetailsServer.setHc(hc);
		return mappingDetailsServer.searchMappingResult(input);
	}

	@Override
	public Boolean deleteMapping(String input) {
		// TODO Auto-generated method stub
		MappingDetailsServer mappingDetailsServer = new MappingDetailsServer();
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		mappingDetailsServer.setHc(hc);
		return mappingDetailsServer.deleteMapping(input);
	}

	@Override
	public boolean addMapping(MappingDetails input) {
		// TODO Auto-generated method stub
		MappingDetailsServer mappingDetailsServer = new MappingDetailsServer();
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		mappingDetailsServer.setHc(hc);
		return mappingDetailsServer.addMapping(input);
	}

	@Override
	public boolean editMapping(MappingDetails mappingDetail) {
		// TODO Auto-generated method stub
		MappingDetailsServer mappingDetailsServer = new MappingDetailsServer();
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		mappingDetailsServer.setHc(hc);
		return mappingDetailsServer.editMapping(mappingDetail);
	}

	@Override
	public ArrayList<String> getMappingTypeList() {
		// TODO Auto-generated method stub
		MappingDetailsServer mappingDetailsServer = new MappingDetailsServer();
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		mappingDetailsServer.setHc(hc);
		return mappingDetailsServer.getMappingTypeList();
	}

	@Override
	public ArrayList<String> getSourceObjectList() {
		logger.info("Inside getSourceObjectList" );
		return ReadProperty.getSourceclassname();
	}

	@Override
	public ArrayList<String> getTargetObjectList() {
		 
		 
		return ReadProperty.getTargetclassname();
	}

	@Override
	public ArrayList<MappingDetails> getMappingResultObject(String input) {
		// TODO Auto-generated method stub
		MappingDetailsServer mappingDetailsServer = new MappingDetailsServer();
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		mappingDetailsServer.setHc(hc);
		return mappingDetailsServer.getMappingResult(input);
	}

	@Override
	public ArrayList<String> getSourceAttributeList(String[] input) {
		MappingDetailsServer mappingDetailsServer = new MappingDetailsServer();
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		mappingDetailsServer.setHc(hc);
		ArrayList<String> attributeList = new ArrayList<String>();
		for(int i=0 ; i <input.length;i++){
			attributeList.addAll( mappingDetailsServer.getAttributeList(input[i]));
		}
		return attributeList;
	}

	@Override
	public ArrayList<String> getTargetAttributeList(String input) {
		MappingDetailsServer mappingDetailsServer = new MappingDetailsServer();
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		mappingDetailsServer.setHc(hc);
		ArrayList<String> attributeList = new ArrayList<String>();
		attributeList.addAll( mappingDetailsServer.getAttributeList(input ));
		return attributeList;
		 
	}
	public ArrayList<String> getObjectLevelMappingNames( ) {
		MappingDetailsServer mappingDetailsServer = new MappingDetailsServer();
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		mappingDetailsServer.setHc(hc);
		return mappingDetailsServer.getObjectLevelMappingNames();
		 
	}
	@Override
	public List<BatchDetails> getBatchDetailsBySequence(String sequenceName,
			int sequenceNumber) {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		BatchDetailsServer batchDetailsServer = new BatchDetailsServer();
		batchDetailsServer.setHc(as.getHc());
		return batchDetailsServer.getBatchDetailsBySequence(sequenceName,sequenceNumber);
	}
	 

	@Override
	public List<BatchDetails> getBatchDetails() {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		BatchDetailsServer batchDetailsServer = new BatchDetailsServer();
		batchDetailsServer.setHc(as.getHc());
		return batchDetailsServer.getBatchDetails();
	}

	@Override
	public List<SequenceDetails> getSequencesList() {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		ThroughputDetailsServer throughputDetailsServer = new ThroughputDetailsServer();
		throughputDetailsServer.setHc(as.getHc());
		return throughputDetailsServer.getSequenceDetails();

	}
	@Override
	public SequenceDetailsMap getSequenceResultMap(String sequenceName,
			int seqNumber) {
		SequencesDetailsServer  sequencesServer = new SequencesDetailsServer();
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = new HibernateConnection();
		hc = as.getHc();
		sequencesServer.setHc(hc);
		return sequencesServer.getSequenceMap(  sequenceName,  seqNumber);
	}
	@Override
	public List<String> getObjectTypes() {
		return ReadProperty.getObjectTypes() ;
		 
	}

	@Override
	public List<String> getAttributes(String objectType) {
		String fullyQualifiedClassName = packageName+ objectType;
		GetRecordDetails grd = new GetRecordDetails();
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		HibernateConnection hc = as.getHc();
		grd.initOperation(hc);
		List<String> attrList = new ArrayList<String>();
    try {
			if (grd.getColumnNamesPK(fullyQualifiedClassName)!= null){
				attrList.add(grd.getColumnNamesPK(fullyQualifiedClassName));
			}else{
				for(int i=0 ; i < grd.getColumnNamesCK(fullyQualifiedClassName).length;i++)
				attrList.add(grd.getColumnNamesCK(fullyQualifiedClassName)[i]);
			}
			 
		} catch (Exception e) {
			// exception handling need to be defined
			e.printStackTrace();
		}

		 
		return attrList;
	}

	@Override
	public List<LogsDetails> getLogsDetails() {

		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		LogsDetailsServer logsDetailsServer = new LogsDetailsServer();
		logsDetailsServer.setHc(as.getHc());
		return logsDetailsServer.getLogsDetails();

	}

	@Override
	public List<LogsDetails> getLogsDetails(String objectType,
			String attribute, String value) {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		LogsDetailsServer logsDetailsServer = new LogsDetailsServer();
		logsDetailsServer.setHc(as.getHc());
		return logsDetailsServer.getLogsDetails(objectType, attribute, value);
	}

	@Override
	public List<SequenceDetailsMap> getSequenceDetailsMapList(String seqName) {
		AllSessions as = AllSessions.getInstance();
		as.setRequest(getThreadLocalRequest());
		ThroughputDetailsServer throughputDetailsServer = new ThroughputDetailsServer();
		throughputDetailsServer.setHc(as.getHc());
		return throughputDetailsServer.getSequenceDetailsMapList(seqName);

	}



	 

	


 
}
