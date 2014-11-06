package com.managetransfer.sharepoint;

import java.io.File;
import java.io.PrintStream;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.namespace.QName;
 

import org.apache.axiom.om.OMAbstractFactory;
 
 
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNode;

import org.apache.commons.httpclient.auth.AuthPolicy;

  
import org.apache.axis2.transport.http.HttpTransportProperties;
import org.apache.axis2.transport.http.HttpTransportProperties.Authenticator;

 

import com.microsoft.schemas.sharepoint.soap.ListsStub;
import com.microsoft.schemas.sharepoint.soap.ListsStub.GetList;
import com.microsoft.schemas.sharepoint.soap.ListsStub.GetListItems;

import com.microsoft.schemas.sharepoint.soap.CopyStub;
import com.microsoft.schemas.sharepoint.soap.CopyStub.CopyIntoItems;
import com.microsoft.schemas.sharepoint.soap.CopyStub.CopyIntoItemsResponse;
import com.microsoft.schemas.sharepoint.soap.CopyStub.DestinationUrlCollection;
import com.microsoft.schemas.sharepoint.soap.CopyStub.FieldInformation;
import com.microsoft.schemas.sharepoint.soap.CopyStub.FieldInformationCollection;
import com.microsoft.schemas.sharepoint.soap.CopyStub.FieldType;
import com.microsoft.schemas.sharepoint.soap.CopyStub.Guid;
import com.microsoft.schemas.sharepoint.soap.ListsStub.GetListItemsResponse;
 
import com.microsoft.schemas.sharepoint.soap.ListsStub.UpdateListItemsResponse;
import com.microsoft.schemas.sharepoint.soap.ListsStub.Updates_type1;
import com.microsoft.schemas.sharepoint.soap.ListsStub.UpdateListItems;

import com.microsoft.schemas.sharepoint.soap.dws.DwsStub;
 
/*
 *  Input Parameter
 *  User Name
 *  Password
 *  Domain
 *  HostName
 *  Port Name
 *  WebserviceURL
 *  Base Path
 */
public class SharePointConnection {
	private String siteName = new String("");
	// This information should come from connection
	private String userName = new String("131268");
	private String psw = new String("Below2@2014");
	private String myDomain = new String("INDIA");
	private String myHost = new String("inblrabhvteg");
	private int myPort = 12345;
	private String webserviceURLBase= new String("http://inblrabhvteg:12345/_vti_bin/");
	private String webserviceURL = webserviceURLBase  + "Lists.asmx"; 
	private String webserviceURLCOPY = webserviceURLBase +"Copy.asmx";
	private String webserviceURLDWS =  webserviceURLBase+"Dws.asmx";
	
	private String dateFormatString = new String("yyyy-MM-dd'T'HH:mm:ssZ");
	private SimpleDateFormat  dateFormatter = new SimpleDateFormat(dateFormatString);
	
	//This information should set for each record
	//private String docUriSharePointDest = new String("http://inblrabhvteg:12345/Information_library");
	private String docUriSharePointDest = new String("http://inchnsirisuap01:6622/sites/site/Information_library");
	private String docUriLocal = new String("D:\\hello.pdf");
	private String repositoryPath = new String("");
	

	private String newFolderPath = new String("Information_library/Test1/Test");
	
	private HashMap<String,String> stringAttributes = new HashMap<String,String>();
	private HashMap<String,Integer> intAttributes = new HashMap<String,Integer>();
	private HashMap<String,Date> dateAttributes = new HashMap<String,Date>();
	private HashMap<String,Long> longAttributes = new HashMap<String,Long>();
	private ArrayList<String> listOfAttributes = new ArrayList<String>();
	private String recordType  = new String("Information");
	final Logger logger = Logger.getLogger(SharePointConnection.class.getName());
	public String getMyDomain() {
		return myDomain;
	}

	public void setMyDomain(String myDomain) {
		this.myDomain = myDomain;
	}

	public String getMyHost() {
		return myHost;
	}

	public void setMyHost(String myHost) {
		this.myHost = myHost;
	}

	public int getMyPort() {
		return myPort;
	}

	public void setMyPort(int myPortInput) {
		this.myPort = myPortInput;
	}

	
	
	public static void main(String[] args) throws Exception {
		SharePointConnection spc = new SharePointConnection();
		 
		//spc.UpdateDocumentMetadata("");
		//spc.GetListItems("ListDel");
		//spc.GetListItems("Information_library"); // Will this work?
		//spc.ImportDocument();
		//spc.CreateFolder(spc.newFolderPath);
		
	}
 
	 
	public void GetListItems(String listName) throws Exception {
		ListsStub stub = new ListsStub(webserviceURL);
		stub._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.AUTHENTICATE,getAuthenticator());

		com.microsoft.schemas.sharepoint.soap.ListsStub.GetListItems getListItems0 = new com.microsoft.schemas.sharepoint.soap.ListsStub.GetListItems();
		getListItems0.setListName(listName);
		GetListItemsResponse getreponse = stub.getListItems(getListItems0);
	 }
 
 
 
	public void initOperation() {
		// Set
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public String getWebserviceURL() {
		return webserviceURL;
	}

	public void setWebserviceURL(String webserviceURL) {
		this.webserviceURL = webserviceURL;
	}

	 

	public String ImportDocument() throws Exception {
		String methodName="ImportDocument";
		logger.info("Inside Method"+methodName);
		
		//This method imports document in the repository
		//It expects source URL and destination url of the document
		CopyStub stub = new CopyStub(webserviceURLCOPY);		
		stub._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.AUTHENTICATE,getAuthenticator());

		CopyStub.CopyIntoItems copyIntoItemsRequest = new CopyIntoItems();

		DestinationUrlCollection destUrlCollection = new DestinationUrlCollection();
		// destinationUrl should be an absolute URL, for ex:
		// http://domain/site/Shared Documents/some.doc
		// Otherwise, you could see a 'Success' return message but the document
		// in SharePoint hasn't been updated.
		destUrlCollection.addString(docUriSharePointDest+"/"+getRepositoryPath()+stringAttributes.get("object_name"));
		logger.info("object path ::"+docUriSharePointDest+"/"+getRepositoryPath()+stringAttributes.get("object_name"));
		copyIntoItemsRequest.setDestinationUrls(destUrlCollection);
		// SourceUrl is not used in the copy operation but is stored with the
		// document on the server as the CopySource property.
		// Note: This value should NOT match the destinationUrl otherwise you'll
		// encounter the error:
		// ErrorCode='DestinationInvalid'
		// ErrorMessage='Cannot create an item at the requested destination.
		// Verify that the folder exists and that you have permission to edit in
		// it.'
		copyIntoItemsRequest.setSourceUrl(stringAttributes.get("folder_path"));

		File file = new File(stringAttributes.get("folder_path"));
		Date lastModifiedDate = new Date();
		lastModifiedDate.setTime(file.lastModified());

		FileDataSource fileDataSource = new FileDataSource(file);
		DataHandler dataHandler = new DataHandler(fileDataSource);

		copyIntoItemsRequest.setStream(dataHandler);

		// An empty array needs to be created in order to prevent the error:
		// ErrorCode='Unknown', ErrorMessage='Object reference not set to an
		// instance of an object.'

		FieldInformationCollection fic = new FieldInformationCollection();

		copyIntoItemsRequest.setFields(fic);

		CopyStub.CopyIntoItemsResponse copyIntoItemsResponse = stub
				.copyIntoItems(copyIntoItemsRequest);
		// assertNotNull(copyIntoItemsResponse);
		// Get the root element of the response
		OMElement root = copyIntoItemsResponse.getOMElement(
				copyIntoItemsResponse.MY_QNAME,
				OMAbstractFactory.getOMFactory());
		String responseString = ""+root;
		logger.info("Inside Method"+methodName);
		return getResponseString("Import",responseString);
	}

	 

	public OMElement XMLdocumentBuilder() throws Exception {
		//This methods build xml element 
		// It has all the attribute and matadata details
		org.apache.axiom.om.OMElement rootElement = null;
		OMFactory factory = OMAbstractFactory.getOMFactory();
		// Creates the root element
		QName rootQName = new QName("Batch");
		rootElement = factory.createOMElement(rootQName);
		// set attributes of root element
		rootElement.addAttribute("OnError", "Continue", null);
		rootElement.addAttribute("ListVersion", "0", null);
		
		
		 
		// Create method node
		QName methodQName = new QName("Method");
		rootElement.addChild(factory.createOMElement(methodQName));
		// Set attributes of method node
		OMElement methodElement = rootElement.getFirstElement();
		methodElement.addAttribute("ID", "1", null);
		methodElement.addAttribute("Cmd", "Update", null);

		 
		// Create field node
		QName fieldQName = new QName("Field");
		OMNode idfieldNode = factory.createOMElement(fieldQName);
		methodElement.addChild(idfieldNode);
		// Set attributes of field node
		OMElement fieldElement = methodElement.getFirstElement();
		fieldElement.addAttribute("Name", "ID", null);

		 
		// Create field node
		QName fieldQNameContentType = new QName("Field");
		OMNode contentTypeNode = factory.createOMElement(fieldQNameContentType);
		idfieldNode.insertSiblingBefore(contentTypeNode);
		
		// Set attributes of field node
		OMElement fieldElementContentType = methodElement.getFirstElement();
		fieldElementContentType.addAttribute("Name", "ContentType", null);
		fieldElementContentType.setText(getRecordType().substring(getRecordType().lastIndexOf(".")+1,getRecordType().length()));

		// Create field node
		QName fieldFileQName = new QName("Field");
		OMNode prvFieldNode = factory.createOMElement(fieldFileQName);
		contentTypeNode.insertSiblingBefore(prvFieldNode);
		// Set attributes of field node
		OMElement fieldElementFile = methodElement.getFirstElement();
		fieldElementFile.addAttribute("Name", "FileRef", null);
		fieldElementFile.setText(docUriSharePointDest+"/"+repositoryPath+stringAttributes.get("object_name"));
	 
			
		//Populating remaining attribute
		for(int i=0; i < listOfAttributes.size();i++){
			 
			QName currentQName = new QName("Field");
			OMNode currentFieldNode = factory.createOMElement(currentQName);
			prvFieldNode.insertSiblingBefore(currentFieldNode);
			// Set attributes of field node
			OMElement fieldElementCurrent = methodElement.getFirstElement();
			
			fieldElementCurrent.addAttribute("Name",listOfAttributes.get(i)  , null);
			if(stringAttributes.containsKey(listOfAttributes.get(i)) && stringAttributes.get(listOfAttributes.get(i)) != null ){
				fieldElementCurrent.setText(stringAttributes.get(listOfAttributes.get(i)));
			}
			if(intAttributes.containsKey(listOfAttributes.get(i)) && intAttributes.get(listOfAttributes.get(i)) != null ){
				fieldElementCurrent.setText(""+intAttributes.get(listOfAttributes.get(i)));
			}
			if(dateAttributes.containsKey(listOfAttributes.get(i)) && dateAttributes.get(listOfAttributes.get(i)) != null ){
				fieldElementCurrent.setText(dateFormatter.format(dateAttributes.get(listOfAttributes.get(i))));
			}
			if(longAttributes.containsKey(listOfAttributes.get(i)) && longAttributes.get(listOfAttributes.get(i))  != null ){
				fieldElementCurrent.setText(""+longAttributes.get(listOfAttributes.get(i)));
			}
			prvFieldNode = currentFieldNode;
		}
		 
		logger.info("Final Value"+rootElement);
		 
	 	return rootElement;

	}


	private Authenticator getAuthenticator() {
		AuthPolicy.registerAuthScheme(AuthPolicy.NTLM, JCIFS_NTLMScheme.class);
		HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
		auth.setUsername(userName);
		auth.setPassword(psw);
		auth.setDomain(myDomain);
		auth.setHost(myHost);
		auth.setPort(myPort);

		List<String> authPrefs = new ArrayList<String>();
		authPrefs.add(AuthPolicy.NTLM);
		auth.setAuthSchemes(authPrefs);
		return auth;
	}
	
	private String getResponseString(String methodName, String responseString){
		if(methodName.equals("Update")){
			if(responseString.contains("<ErrorCode>0x00000000</ErrorCode>")) {
				return "Success";
			}else{
				return responseString.substring(responseString.indexOf("<ErrorText>")+11, responseString.indexOf("</ErrorText>"));
			}
		}else if(methodName.equals("Import")){
			if( responseString.contains("ErrorCode=\"Success\"")) {
				return "Success";
			}else{
				return responseString.substring(responseString.indexOf("ErrorMessage=\"") +14, responseString.indexOf("\"", responseString.indexOf("ErrorMessage=\"") +14) );
			}
		}else if(methodName.equals("CreateFolder")){
			if(! responseString.contains("Error ID")) {
				return "Success";
			}else{
				return responseString.substring(responseString.indexOf("Error") +5, responseString.indexOf("/Error")  );
			}
		}
		else {
			return "";
		}
		 
	}
	
	public HashMap<String, String> getStringAttributes() {
		return stringAttributes;
	}

	public void setStringAttributes(HashMap<String, String> stringAttributes) {
		//These columns used for internal processing . Hence need to be removed 
		 
		this.stringAttributes = stringAttributes;
	}

	public HashMap<String, Integer> getIntAttributes() {
		return intAttributes;
	}

	public void setIntAttributes(HashMap<String, Integer> intAttributes) {
		//These columns used for internal processing . Hence need to be removed 
		this.intAttributes = intAttributes;
	}

	public HashMap<String, Date> getDateAttributes() {
		return dateAttributes;
	}

	public void setDateAttributes(HashMap<String, Date> dateAttributes) {
		//These columns used for internal processing . Hence need to be removed 
		
		this.dateAttributes = dateAttributes;
	}

	public HashMap<String, Long> getLongAttributes() {
		return longAttributes;
	}

	public void setLongAttributes(HashMap<String, Long> longAttributes) {
		
		this.longAttributes = longAttributes;
	}

	public ArrayList<String> getListOfAttributes() {
		return listOfAttributes;
	}

	public void setListOfAttributes(ArrayList<String> listOfAttributes) {
		this.listOfAttributes = listOfAttributes;
		//Removing processing specific attributes;
		this.listOfAttributes.remove("mt_sequence_name");
		this.listOfAttributes.remove("mt_error_description");
		this.listOfAttributes.remove("mt_status");
		this.listOfAttributes.remove("mt_create_date");
		this.listOfAttributes.remove("mt_modify_date");
		this.listOfAttributes.remove("mt_process_id");
		this.listOfAttributes.remove("mt_sequence_number");
		this.listOfAttributes.remove("folder_path");
		this.listOfAttributes.remove("object_name");
		this.listOfAttributes.remove("mt_repo_path");
	}
	public String getDocUriSharePointDest() {
		return docUriSharePointDest;
	}

	public void setDocUriSharePointDest(String docUriSharePointDest) {
		this.docUriSharePointDest = docUriSharePointDest;
	}

	public String getDocUriLocal() {
		return docUriLocal;
	}

	public void setDocUriLocal(String docUriLocal) {
		this.docUriLocal = docUriLocal;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getWebserviceURLBase() {
		return webserviceURLBase;
	}

	public void setWebserviceURLBase(String webserviceURLBaseInput) {
		this.webserviceURL   = webserviceURLBaseInput  + "Lists.asmx"; 
		this.webserviceURLCOPY = webserviceURLBaseInput +"Copy.asmx";
		this.webserviceURLDWS =  webserviceURLBaseInput+"Dws.asmx";
	}

	public void createFolderStructure(String folderPath) throws Exception{
		String methodName="createFolderStructure";
		logger.info("Inside Method"+methodName);
		String[] folderNames = folderPath.split("/");
		logger.info("docUriSharePointDest"+docUriSharePointDest);
		String baseFolderPath  = docUriSharePointDest.substring(docUriSharePointDest.lastIndexOf("/")+1);
		for(int i=0 ;i< folderNames.length; i++){
			baseFolderPath = baseFolderPath +"/" +folderNames[i];
			logger.info("baseFolderPath"+baseFolderPath);
			CreateFolder(baseFolderPath);
		}
		logger.info("Exist Method"+methodName);
	}

	public String getRepositoryPath() {
		return repositoryPath;
	}

	public void setRepositoryPath(String repositoryPath) {
		this.repositoryPath = repositoryPath;
	}
	 
	public void CreateFolder(String folderPath) throws Exception{
		try{
		DwsStub stub = new DwsStub(webserviceURLDWS);
		logger.info("Webservice URL:"+webserviceURLDWS);
		stub._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.AUTHENTICATE,getAuthenticator());
		com.microsoft.schemas.sharepoint.soap.dws.DwsStub.CreateFolder createFolder8  = new com.microsoft.schemas.sharepoint.soap.dws.DwsStub.CreateFolder();
		createFolder8.setUrl(folderPath);
		logger.info("folderPath::"+folderPath);
		com.microsoft.schemas.sharepoint.soap.dws.DwsStub.CreateFolderResponse createFolderResponse = stub.createFolder(createFolder8  );
		OMElement root =  createFolderResponse.getOMElement( createFolderResponse.MY_QNAME, OMAbstractFactory.getOMFactory());
		String responseString = ""+root;
		logger.info("Root::"+root);
		logger.info(getResponseString("CreateFolder",responseString)); 
		}
		catch(Exception e){
			logger.severe("Error in CreateFolder:: "+e);
			throw e;
		}
				
	}
	
	
	public String UpdateDocumentMetadata(String listName) throws Exception {
		try {
		String methodName="UpdateDocumentMetadata";
		logger.info("Inside Method"+methodName);
		//* This method is used to update document metadata . It updates metadata of the document based on document URL
		ListsStub stub = new ListsStub(webserviceURL);
		logger.info("webserviceURL"+webserviceURL);
		stub._getServiceClient().getOptions().setProperty(org.apache.axis2.transport.http.HTTPConstants.AUTHENTICATE,getAuthenticator());
		//stub._getServiceClient().getOptions().setSoapVersionURI(org.apache.axiom.soap.SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
		stub._getServiceClient().getOptions().setExceptionToBeThrownOnSOAPFault(false);
		 
		com.microsoft.schemas.sharepoint.soap.ListsStub.UpdateListItems updateListItems48 = new com.microsoft.schemas.sharepoint.soap.ListsStub.UpdateListItems();
		com.microsoft.schemas.sharepoint.soap.ListsStub.Updates_type1 param = new com.microsoft.schemas.sharepoint.soap.ListsStub.Updates_type1();
 		//updateListItems48.setListName("Information_library/TestData");
		updateListItems48.setListName("Information_library");
		param.setExtraElement(  XMLdocumentBuilder());
		updateListItems48.setUpdates(param);
		com.microsoft.schemas.sharepoint.soap.ListsStub.UpdateListItemsResponse getreponse = stub.updateListItems(updateListItems48);
		OMElement responseElement = getreponse.getOMElement( getreponse.MY_QNAME, OMAbstractFactory.getOMFactory());
		String responseString =  ""+responseElement; 
		logger.info("responseString from update"+responseString);
		logger.info("Existing Method"+methodName);
		return getResponseString("Update",responseString) ;
		}catch(Exception e) {
			logger.severe(" Error in Update metadata"+e);
			e.printStackTrace(System.out);
			return e.getMessage();
		}
	}
	 
}
