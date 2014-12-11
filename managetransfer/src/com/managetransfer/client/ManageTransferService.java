package com.managetransfer.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.managetransfer.client.ConnectionDetails;
import com.managetransfer.client.SequenceDetails;
import com.managetransfer.client.UserDetails;
import com.managetransfer.client.MappingDetails;
 
/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("managetransfer")
public interface ManageTransferService extends RemoteService {
	
	/*** Common****/
	String greetServer(String name) throws IllegalArgumentException;
	UserDetails userLoginServiceCall(UserDetails userDetails);
	Boolean userAuthentication(UserDetails userDetails);
	ArrayList<String> getSourceObjectList();
	ArrayList<String> getTargetObjectList();
	/*** Connection****/
	ArrayList<ConnectionDetails> getConnectionResult(String input);
	ArrayList<ConnectionDetails> searchConnectionResult(String input);
	Boolean deleteConnection(String input);
	boolean isAuthenticated();
	boolean addConnection(ConnectionDetails input);
	boolean editConnection(ConnectionDetails connectionDetail);
	ArrayList<String> getConnectionTypeList();
	ArrayList<String> getConnectionList();
	/*** Sequence****/
	ArrayList<SequenceDetails> getSequenceResult(String input);
	ArrayList<SequenceDetails> searchSequenceResult(String input);
	Boolean deleteSequence(String input);
	boolean addSequence(SequenceDetails input);
	boolean editSequence(SequenceDetails sequenceDetail);
	/*** Expression****/
	ArrayList<ExpressionsDetails> getExpressionsResult(String input);
	ArrayList<ExpressionsDetails> searchExpressions(String input);
	Boolean deleteExpression(String input);
	ArrayList<String> getExpressionTypeList();
	ArrayList<String> getExpressionList();
	boolean addExpression(ExpressionsDetails input);
	boolean editExpression(ExpressionsDetails expressionDetail);
	/*** Schedular****/
	ArrayList<JobDetails> getJobResult(String input) throws Exception;
	ArrayList<JobDetails> searchJob(String input) throws Exception ;
	Boolean deleteJob(JobDetails jobDetail) throws Exception ;
	ArrayList<String> getJobTypeList() throws Exception ;
	boolean addJob(JobDetails input) throws Exception;
	boolean editJob(JobDetails jobDetail)throws Exception;
	boolean runJob(JobDetails jobDetail)throws Exception;
	boolean changeStateJob(JobDetails jobDetail)throws Exception;
	boolean interruptJob(JobDetails selectedJobName)throws Exception;
	SequenceDetailsMap getSequenceResultMap(String sequenceName,int seqNumber);
	/*** Phases****/
	ArrayList<PhasesDetails> getPhaseResult(String input);
	ArrayList<PhasesDetails> searchPhase(String input);
	Boolean deletePhase(String input);
	ArrayList<String> getPhaseTypeList();
	ArrayList<String> getPhaseList();
	boolean addPhase(PhasesDetails input);
	boolean editPhase(PhasesDetails input);
	/*** Mapping****/
	ArrayList<MappingDetails> getMappingResult(String input);
	ArrayList<MappingDetails> getMappingResultObject(String input);
	ArrayList<MappingDetails> searchMappingResult(String input);
	Boolean deleteMapping(String input);
	boolean addMapping(MappingDetails input);
	boolean editMapping(MappingDetails mappingDetail);
	ArrayList<String> getMappingTypeList();
	ArrayList<String> getSourceAttributeList(String[] input);
	ArrayList<String> getTargetAttributeList(String input);
	ArrayList<String> getMachineList();
	ArrayList<String> getObjectLevelMappingNames();
	
	List<BatchDetails> getBatchDetails();
	List<SequenceDetails> getSequencesList();
	List<String> getObjectTypes();
	List<String> getAttributes(String objectType);
	List<LogsDetails> getLogsDetails();
	List<LogsDetails> getLogsDetails(String objectType, String attribute,
			String value);
	List<SequenceDetailsMap> getSequenceDetailsMapList(String seqName);
	List<BatchDetails> getBatchDetailsBySequence(String sequenceName,
			int sequenceNumber);
	 String logOut();
	
	
	
}
