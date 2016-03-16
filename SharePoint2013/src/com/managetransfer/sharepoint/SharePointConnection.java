package com.managetransfer.sharepoint;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
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
	
	private final String sts = "https://login.microsoftonline.com/extSTS.srf";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); //This format is compatible with DATA TIME format accepted by REST
	private final String login = "/_forms/default.aspx?wa=wsignin1.0";
	private final String reqXML = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:a=\"http://www.w3.org/2005/08/addressing\" xmlns:u=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"><s:Header><a:Action s:mustUnderstand=\"1\">http://schemas.xmlsoap.org/ws/2005/02/trust/RST/Issue</a:Action><a:ReplyTo><a:Address>http://www.w3.org/2005/08/addressing/anonymous</a:Address></a:ReplyTo><a:To s:mustUnderstand=\"1\">https://login.microsoftonline.com/extSTS.srf</a:To><o:Security s:mustUnderstand=\"1\" xmlns:o=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"><o:UsernameToken><o:Username>[username]</o:Username><o:Password>[password]</o:Password></o:UsernameToken></o:Security></s:Header><s:Body><t:RequestSecurityToken xmlns:t=\"http://schemas.xmlsoap.org/ws/2005/02/trust\"><wsp:AppliesTo xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"><a:EndpointReference><a:Address>[endpoint]</a:Address></a:EndpointReference></wsp:AppliesTo><t:KeyType>http://schemas.xmlsoap.org/ws/2005/05/identity/NoProofKey</t:KeyType><t:RequestType>http://schemas.xmlsoap.org/ws/2005/02/trust/Issue</t:RequestType><t:TokenType>urn:oasis:names:tc:SAML:1.0:assertion</t:TokenType></t:RequestSecurityToken></s:Body></s:Envelope>";
    private String rtFa = new String("");
    private String FedAuth = new String("");
    String token= new String("");
    String formDigestValue = new String("");
    String RpsContextCookie = new String("");
	// This information should come from connection
    String endPoint = new String ("https://spatils.sharepoint.com"); //Base sharePoint site path
   	String subSitePath= new String ("");//This path of sub site relative to base site
    String userName = new String("spatils@spatils.onmicrosoft.com");
    String psw = new String("Bus@9pm123");
    
	
	//This comes from phase object . This is the base path during folder creation
    String baseLibrayPath = new String("Shared Documents/ManageTransfer"); //path where document will get stored  
	//This information should set for each record
	//private String docUriSharePointDest = new String("http://inblrabhvteg:12345/Information_library");
	private String docUriSharePointDest = new String("https://tatacommunications.sharepoint.com/sites/ILLActivitiesSIT");
	private String docUriLocal = new String("D:\\hello.pdf");
	private String repositoryPath = new String("");
	
	private HashMap<String,String> stringAttributes = new HashMap<String,String>();
	private HashMap<String,Integer> intAttributes = new HashMap<String,Integer>();
	private HashMap<String,Date> dateAttributes = new HashMap<String,Date>();
	private HashMap<String,Long> longAttributes = new HashMap<String,Long>();
	private HashMap<String,Boolean> booleanAttributes = new HashMap<String,Boolean>();
	private ArrayList<String> listOfAttributes = new ArrayList<String>();
	 
	 String objectName = new String ("hello.pdf"); //Name of the document to be uploaded
	private String recordType  = new String("Information");
	private String updateMetadataString = new String("");
	final Logger logger = Logger.getLogger(SharePointConnection.class.getName());
	public String ImportDocument() throws Exception {
		logger.info(baseLibrayPath+"/"+stringAttributes.get("folder_path"));
		
		logger.info(stringAttributes.get("object_name"));
		upLoadDocument( stringAttributes.get("folder_path"), baseLibrayPath+"/"+repositoryPath+"/",  stringAttributes.get("object_name"));
		return "SUCCESS";
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
	public void createFolderStructure(String folderPath) throws Exception{
		String methodName="createFolderStructure";
		logger.info("Inside Method"+methodName);
		String[] folderNames = folderPath.split("/");
		for(int i=0 ;i< folderNames.length; i++){
			logger.info(baseLibrayPath+"/"+folderNames[i]);
			createFolder(baseLibrayPath+"/"+folderNames[i]);
		}
		logger.info("Exist Method"+methodName);
	}
	private String generateSAML() {
		 
		String saml = reqXML
				.replace("[username]", userName );
		saml = saml.replace("[password]",psw);
		saml = saml.replace("[endpoint]", endPoint);
		return saml;
		 
	}
	public String login() {
		
		try {
			token = requestToken();
			String cookie = submitToken(token);
			return cookie;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return "";
	}

	private String requestToken() throws XPathExpressionException, SAXException,
		ParserConfigurationException, IOException {
		String saml = generateSAML();
		URL u = new URL(sts);
		URLConnection uc = u.openConnection();
		HttpsURLConnection connection = (HttpsURLConnection) uc;
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.addRequestProperty("Content-Type",	"text/xml; charset=utf-8");
		connection.setInstanceFollowRedirects(false);
		connection.setAllowUserInteraction(false);
		OutputStream out = connection.getOutputStream();
		Writer wout = new OutputStreamWriter(out);
		wout.write(saml);
		wout.flush();
		wout.close();
		InputStream in = connection.getInputStream();
		int c;
		logger.info(""+connection.getResponseCode());
		StringBuilder sb = new StringBuilder("");
		while ((c = in.read()) != -1)
			sb.append((char) (c));
		in.close();
		String result = sb.toString();
		logger.info(result);
		String token = extractToken(result);
		logger.info(token);
		return token;
	}
	private String extractToken(String result) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document document = db.parse(new InputSource(new StringReader(result)));

        XPathFactory xpf = XPathFactory.newInstance();
        XPath xp = xpf.newXPath();
        String token = xp.evaluate("//BinarySecurityToken/text()", document.getDocumentElement());
        logger.info(token);
        return token;
	}
	private String submitToken(String token) throws  Exception {
		String url = endPoint + login;
		URL u = new URL(url);
		URLConnection uc = u.openConnection();
		HttpURLConnection connection = (HttpURLConnection) uc;
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.addRequestProperty("Accept", "application/x-www-form-urlencoded");
		connection.addRequestProperty("X-Forms_Based_Auth_Required","t");
		connection.addRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0)");
		connection.addRequestProperty("Content-Type","application/x-www-form-urlencoded");
		connection.setInstanceFollowRedirects(false);
		connection.setAllowUserInteraction(false);
		byte[] utf8TokenStringBytes = token.getBytes("UTF-8");
		connection.setRequestProperty("Content-Length", utf8TokenStringBytes.length + "");
		OutputStream out = connection.getOutputStream();
		Writer wout = new OutputStreamWriter(out);
		wout.write(token);
		wout.flush();
		wout.close();
		InputStream in = connection.getInputStream();
		for (int i=0; ; i++) {
	        String headerName = connection.getHeaderFieldKey(i);
	        String headerValue = connection.getHeaderField(i);
	        logger.info("header: " + headerName + " : " + headerValue);
	        if (headerName == null && headerValue == null) {
	            // No more headers
	            break;
	        }
	        if (headerName == null) {
	            // The header value contains the server's HTTP version
	        }
	        if (headerValue != null){
	        	if (headerValue.startsWith("rtFa"))  	rtFa = headerValue; 
	        	if (headerValue.startsWith("FedAuth"))  FedAuth = headerValue;
	        	if (headerValue.startsWith("RpsContextCookie"))  RpsContextCookie = headerValue;
	        	
	        }
	    }
		String headerName = connection.getHeaderField("set-cookie");
		logger.info("headerName");
		logger.info(headerName);
		int c;
		StringBuilder sb = new StringBuilder("");
		while ((c = in.read()) != -1)
			sb.append((char) (c));
		in.close();
		String result = sb.toString();
		logger.info(result);
		if(rtFa== null || rtFa.trim().equals("")||FedAuth== null || FedAuth.trim().equals("")){
			throw new Exception("Unable To FedAuth rtFa cokies ");
		}
		return headerName;
	}
	private String getRquestDigest() throws  Exception {
		String url = endPoint+"/_api/contextinfo" ;
		URL u = new URL(url);
		URLConnection uc = u.openConnection();
		HttpURLConnection connection = (HttpURLConnection) uc;
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.addRequestProperty("X-Forms_Based_Auth_Required","f");
		connection.addRequestProperty("Content-Type","application/x-www-form-urlencoded");
		connection.addRequestProperty("Cookie","" + rtFa + ";" + FedAuth);
		OutputStream out = connection.getOutputStream();
		Writer wout = new OutputStreamWriter(out);
		wout.write("");
		wout.flush();
		wout.close();
		InputStream in = connection.getInputStream();
		 
		int c;
		StringBuilder sb = new StringBuilder("");
		while ((c = in.read()) != -1)
			sb.append((char) (c));
		in.close();
		String result = sb.toString();
		logger.info(result);
		formDigestValue = extractDigestValue(result);
		if(formDigestValue== null || formDigestValue.trim().equals("")){
			throw new Exception("Unable To Get Digest Values ");
		}
		return " ";
	}
	 
	private String createFolder( String folderPath) throws  Exception {
		//For example folderPath   = Shared Documents/f2
		String url = endPoint+subSitePath+"/_api/Web/Folders" ;
		///Sample URL https://spatils.sharepoint.com/_api/Web/Folders" ;
		///Sample Data String createFolderString = new String("{ '__metadata':{ 'type': 'SP.Folder' }, 'ServerRelativeUrl':'Shared Documents/f2' }");
		URL u = new URL(url);
		URLConnection uc = u.openConnection();
		HttpURLConnection connection = (HttpURLConnection) uc;
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.addRequestProperty("Accept", "application/json; odata=verbose");
		connection.addRequestProperty("Content-Type","application/json; odata=verbose");
		connection.addRequestProperty("Cookie","" + rtFa + ";" + FedAuth);
		connection.addRequestProperty("X-RequestDigest",formDigestValue );
		connection.setInstanceFollowRedirects(false);
		connection.setAllowUserInteraction(false);
		String createFolderString = new String("{ '__metadata':{ 'type': 'SP.Folder' }, 'ServerRelativeUrl':'"+folderPath+"' }");
		OutputStream out = connection.getOutputStream();
		Writer wout = new OutputStreamWriter(out);
		wout.write(createFolderString);
		wout.flush();
		wout.close();
		InputStream in = connection.getInputStream();
		int c;
		StringBuilder sb = new StringBuilder("");
		while ((c = in.read()) != -1)
			sb.append((char) (c));
		in.close();
		String result = sb.toString();
		JSONObject jsonObject = new JSONObject(result);
		logger.info(result);
		return "SUCCESS";
	}
	private String upLoadDocument(String filePath, String repositoryPath,String objectName) throws  Exception {
		//For example filePath : d:\hellp.pdf  repositoryPath = /Shared Documents/f1/  objectName = hello.pdf
		String url =endPoint+subSitePath+"/_api/web/GetFolderByServerRelativeUrl('"+repositoryPath+"')/Files/add(url='"+objectName+"',overwrite=true)";
		///Sample URL https://spatils.sharepoint.com/_api/web/GetFolderByServerRelativeUrl('/Shared%20Documents/f1/')/Files/add(url='hello.pdf',overwrite=true)";
		
		URL u = new URL(getHttpCompatibleURL(url));
		URLConnection uc = u.openConnection();
		HttpURLConnection connection = (HttpURLConnection) uc;
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.addRequestProperty("Accept", "application/json; odata=verbose");
		connection.addRequestProperty("Content-Type","application/x-www-form-urlencoded");
		connection.addRequestProperty("Cookie","" + rtFa + ";" + FedAuth);
		connection.addRequestProperty("X-RequestDigest",formDigestValue );
		File newFile = new File(filePath);
		if(!newFile.exists()){
			 throw new Exception( "File Does Not Exist "+filePath);
		}
		OutputStream out = connection.getOutputStream();
		InputStream  inFile = new FileInputStream(filePath);
	    int c;
	    byte [] buffer = new byte[256];
        while ((c = inFile.read(buffer)) != -1) {
        	out.write(buffer,0,c);
        }
        inFile.close();
        InputStream in = connection.getInputStream();
		int l;
		StringBuilder sb = new StringBuilder("");
		while ((l = in.read()) != -1)
			sb.append((char) (l));
		in.close();
		String result = sb.toString();
		logger.info(result);
		return "SUCCESS";
	}
	private String getDocumentMetaData(String documentPath) throws  Exception {
		/***
		 * This method is used to get the attribute details of custom type
		 * SharePoint sets some custom name to each field 
		 * This method can be used to get that custom name
		 */
		
		String url = endPoint+subSitePath+"/_api/web/GetFileByServerRelativeUrl('/Shared Documents/f1/hello.pdf')/ListItemAllFields" ;
		///Sample URL https://spatils.sharepoint.com/_api/web/GetFileByServerRelativeUrl('/Shared%20Documents/f1/hello.pdf')/ListItemAllFields" ;
		URL u = new URL(getHttpCompatibleURL(url));
		URLConnection uc = u.openConnection();
		HttpURLConnection connection = (HttpURLConnection) uc;
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("GET");
		connection.addRequestProperty("Accept", "application/json; odata=verbose");
		connection.addRequestProperty("Content-Type","application/json; odata=verbose");
		connection.addRequestProperty("Cookie","" + rtFa + ";" + FedAuth);
		connection.addRequestProperty("X-RequestDigest",formDigestValue );
		connection.setInstanceFollowRedirects(false);
		connection.setAllowUserInteraction(false);
		String updateString = new String("");
		OutputStream out = connection.getOutputStream();
		Writer wout = new OutputStreamWriter(out);
		wout.write(updateString);
		wout.flush();
		wout.close();
		InputStream in = connection.getInputStream();
		int c;
		StringBuilder sb = new StringBuilder("");
		while ((c = in.read()) != -1)
			sb.append((char) (c));
		in.close();
		String result = sb.toString();
		logger.info(result);
		return "SUCCESS";
	}
	private String setDocumentMetaData(String documentPath,String updateMetadataValue) throws  Exception {
		//For example documentPath = /Shared Documents/f1/hello.pdf updateMetadataValue ={'__metadata': { 'type': 'SP.Data.Shared_x0020_DocumentsItem'}, 'Ref_x0020_Num': '123','Doc_x0020_Category': 'docdc123','Is_x0020_Active': false,'Doc_x0020_Date':'2016-02-10T00:00:00' }
		String url = endPoint+subSitePath+"/_api/web/GetFileByServerRelativeUrl('"+subSitePath+"/"+documentPath+"')/ListItemAllFields"  ;
		///Sample URL https://spatils.sharepoint.com/_api/web/GetFileByServerRelativeUrl('/Shared%20Documents/f1/hello.pdf')/ListItemAllFields"  ;
		///Sample Values String updateString = new String("{'__metadata': { 'type': 'SP.Data.Shared_x0020_DocumentsItem'}, 'Ref_x0020_Num': '123','Doc_x0020_Category': 'docdc123','Is_x0020_Active': false,'Doc_x0020_Date':'2016-02-10T00:00:00' }");
		
		URL u = new URL(getHttpCompatibleURL(url));
		URLConnection uc = u.openConnection();
		HttpURLConnection connection = (HttpURLConnection) uc;
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("GET");
		connection.addRequestProperty("Accept", "application/json; odata=verbose");
		connection.addRequestProperty("Content-Type","application/json; odata=verbose");
		connection.addRequestProperty("Cookie","" + rtFa + ";" + FedAuth);
		connection.addRequestProperty("X-RequestDigest",formDigestValue );
		connection.addRequestProperty("X-HTTP-Method", "MERGE");
		connection.addRequestProperty("IF-MATCH", "*");
		connection.setInstanceFollowRedirects(false);
		connection.setAllowUserInteraction(false);
		OutputStream out = connection.getOutputStream();
		Writer wout = new OutputStreamWriter(out);
		wout.write(updateMetadataValue);
		wout.flush();
		wout.close();
		InputStream in = connection.getInputStream();
		int c;
		StringBuilder sb = new StringBuilder("");
		while ((c = in.read()) != -1)
			sb.append((char) (c));
		in.close();
		String result = sb.toString();
		logger.info(result);
		return "SUCCESS";
	}
	private String extractDigestValue(String result) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document document = db.parse(new InputSource(new StringReader(result)));

        XPathFactory xpf = XPathFactory.newInstance();
        XPath xp = xpf.newXPath();
        String token = xp.evaluate("//FormDigestValue/text()", document.getDocumentElement());
        logger.info(token);
        return token;
	}
	public String UpdateDocumentMetadata(String listName) throws Exception {
		logger.info(baseLibrayPath+"/"+repositoryPath+"/"+  stringAttributes.get("object_name"));
		setDocumentMetaData(baseLibrayPath+"/"+repositoryPath+"/"+  stringAttributes.get("object_name"),buildUpdateMetadataString());
		return "SUCCESS";
	}
	public String buildUpdateMetadataString(){
		String updateString = this.getUpdateMetadataString();
		for(int i=0; i < listOfAttributes.size();i++){
			String attributeName = listOfAttributes.get(i);
			if(stringAttributes.containsKey(attributeName) && stringAttributes.get(attributeName) != null ){
				updateString=updateString.replace( getRESTAttributeString(attributeName), stringAttributes.get(attributeName) )  ;
			}
			if(intAttributes.containsKey(attributeName) && intAttributes.get(attributeName) != null ){
				updateString=updateString.replace( getRESTAttributeString(attributeName),""+intAttributes.get(attributeName) )  ;
			}
			if(dateAttributes.containsKey(attributeName) && dateAttributes.get(attributeName) != null ){
				updateString=updateString.replace( getRESTAttributeString(attributeName),sdf.format(dateAttributes.get(attributeName)))  ;
			}
			if(longAttributes.containsKey(attributeName) && longAttributes.get(attributeName)  != null ){
				updateString=updateString.replace( getRESTAttributeString(attributeName),""+longAttributes.get(attributeName))  ;
			}
			if(booleanAttributes.containsKey(attributeName) && booleanAttributes.get(attributeName)  != null ){
				updateString=updateString.replace( getRESTAttributeString(attributeName),""+booleanAttributes.get(attributeName))  ;
			}
			
			 
		}	
		logger.info(updateString);
		return	updateString;
	}
	public String getHttpCompatibleURL(String url){
		
		return url.replace(" ", "%20");
	}
	public String getRESTDate(Date inputDate){
		return sdf.format(inputDate);
	}
	public String getRESTAttributeString(String attributeName){
		return "["+attributeName+"]" ;
	}
	 
	public void connectSharePoint2013REST() throws Exception{
		login() ; 
		getRquestDigest();
	}
	 public String getEndPoint() {
			return endPoint;
		}

		public void setEndPoint(String endPoint) {
			this.endPoint = endPoint;
		}
		public void GetListItems(String listName) throws Exception {
			
		 }
	 
		public void initOperation() {
			 
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
		public String getRepositoryPath() {
			return repositoryPath;
		}

		public void setRepositoryPath(String repositoryPath) {
			this.repositoryPath = repositoryPath;
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
		public String getUpdateMetadataString() {
			return updateMetadataString;
		}
		public void setUpdateMetadataString(String updateMetadataString) {
			this.updateMetadataString = updateMetadataString;
		}
		public HashMap<String,Boolean> getBooleanAttributes() {
			return booleanAttributes;
		}
		public void setBooleanAttributes(HashMap<String,Boolean> booleanAttributes) {
			this.booleanAttributes = booleanAttributes;
		}
}
