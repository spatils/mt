package com.managetransfer.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.managetransfer.client.ConnectionDetails;
import com.managetransfer.client.SequenceDetails;
import com.managetransfer.client.UserDetails;
import com.managetransfer.client.BatchDetails;
 

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface ManageTransferServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void userLoginServiceCall(UserDetails userDetails,
			AsyncCallback<UserDetails> asyncCallback);

	void userAuthentication(UserDetails userDetails,
			AsyncCallback<Boolean> callback);

	void isAuthenticated(AsyncCallback<Boolean> callback);

	void getConnectionResult(String input,
			AsyncCallback<ArrayList<ConnectionDetails>> asyncCallback);

	void addConnection(ConnectionDetails input,
			AsyncCallback<Boolean> asyncCallback);

	void searchConnectionResult(String input,
			AsyncCallback<ArrayList<ConnectionDetails>> callback);

	void deleteConnection(String input, AsyncCallback<Boolean> callback);
	
	void editConnection(ConnectionDetails connectionDetail,
			AsyncCallback<Boolean> callback);

	
	void getSequenceResult(String input,
			AsyncCallback<ArrayList<SequenceDetails>> callback);
	
	void getSequenceResultMap(String sequenceName,int seqNumber,
			AsyncCallback<SequenceDetailsMap> callback);

	void addSequence(SequenceDetails input, AsyncCallback<Boolean> callback);

	void deleteSequence(String input, AsyncCallback<Boolean> callback);

	void editSequence(SequenceDetails sequenceDetail,
			AsyncCallback<Boolean> callback);

	void searchSequenceResult(String input,
			AsyncCallback<ArrayList<SequenceDetails>> callback);

	void getConnectionTypeList(AsyncCallback<ArrayList<String>> callback);
	
	
	void getExpressionsResult(String input,
			AsyncCallback<ArrayList<ExpressionsDetails>> asyncCallback);

	void addExpression(ExpressionsDetails input,
			AsyncCallback<Boolean> asyncCallback);

	void searchExpressions(String input,
			AsyncCallback<ArrayList<ExpressionsDetails>> callback);

	void deleteExpression(String input, AsyncCallback<Boolean> callback);
	
	void editExpression(ExpressionsDetails expressionDetail,
			AsyncCallback<Boolean> callback);
	
	void getExpressionTypeList(AsyncCallback<ArrayList<String>> callback);
	
	void addJob(JobDetails input, AsyncCallback<Boolean> callback);
	void deleteJob(JobDetails jobDetail, AsyncCallback<Boolean> callback);
	void editJob(JobDetails jobDetail, AsyncCallback<Boolean> callback);
	void getJobResult(String input,
			AsyncCallback<ArrayList<JobDetails>> callback);
	void getJobTypeList(AsyncCallback<ArrayList<String>> callback);
	void getMachineList(AsyncCallback<ArrayList<String>> callback);
	void searchJob(String input, AsyncCallback<ArrayList<JobDetails>> callback);
	void addPhase(PhasesDetails input, AsyncCallback<Boolean> callback);
	void deletePhase(String input, AsyncCallback<Boolean> callback);
	void editPhase(PhasesDetails input, AsyncCallback<Boolean> callback);
	void getPhaseResult(String input,
			AsyncCallback<ArrayList<PhasesDetails>> callback);
	void getPhaseTypeList(AsyncCallback<ArrayList<String>> callback);
	void getPhaseList(AsyncCallback<ArrayList<String>> callback);
	void searchPhase(String input,
			AsyncCallback<ArrayList<PhasesDetails>> callback);
	void getMappingResult(String input,
			AsyncCallback<ArrayList<MappingDetails>> callback);
	void addMapping(MappingDetails input, AsyncCallback<Boolean> callback);
	void deleteMapping(String input, AsyncCallback<Boolean> callback);
	void editMapping(MappingDetails mappingDetail,
			AsyncCallback<Boolean> callback);
	void getMappingTypeList(AsyncCallback<ArrayList<String>> callback);
	void getObjectLevelMappingNames(AsyncCallback<ArrayList<String>> callback);
	void searchMappingResult(String input,
			AsyncCallback<ArrayList<MappingDetails>> callback);
	void getSourceObjectList(AsyncCallback<ArrayList<String>> callback);
	void getTargetObjectList(AsyncCallback<ArrayList<String>> callback);
	void getMappingResultObject(String input,
			AsyncCallback<ArrayList<MappingDetails>> callback);
	void getSourceAttributeList(String[] input,AsyncCallback<ArrayList<String>> callback);
	void getTargetAttributeList(String  input,AsyncCallback<ArrayList<String>> callback);
	void runJob(JobDetails jobDetail, AsyncCallback<Boolean> asyncCallback);
	void changeStateJob(JobDetails jobDetail, AsyncCallback<Boolean> asyncCallback);
	void getConnectionList(AsyncCallback<ArrayList<String>> callback);
	void getExpressionList(AsyncCallback<ArrayList<String>> callback);
	
	void getBatchDetails(AsyncCallback<List<BatchDetails>> callback);

	void getSequencesList(
			AsyncCallback<List<SequenceDetails>> asyncCallback);
	
	void getSequenceDetailsMapList(String seqName,
			AsyncCallback<List<SequenceDetailsMap>> asyncCallback);

	void getObjectTypes(AsyncCallback<List<String>> asyncCallback);

	void getAttributes(String objectType,
			AsyncCallback<List<String>> asyncCallback);

	void getLogsDetails(AsyncCallback<List<LogsDetails>> asyncCallback);

	void getLogsDetails(String objectType, String attribute, String value,
			AsyncCallback<List<LogsDetails>> asyncCallback);
	
	void getBatchDetailsBySequence(String sequenceName, int sequenceNumber,
			AsyncCallback<List<BatchDetails>> asyncCallback);
	void logOut(AsyncCallback<String> asyncCallback);
	void interruptJob(JobDetails selectedJobName,
			AsyncCallback<Boolean> asyncCallback);
	
	
}
