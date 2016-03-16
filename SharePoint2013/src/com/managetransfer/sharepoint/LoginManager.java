package com.managetransfer.sharepoint;
/********
 * 
 * getHttpCompatibleURL - this method converts space into %20 . In future if required then other special characters need to be handled
 * 
 * 
 * 
 */
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.*;
import javax.xml.xpath.*;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.*;

public class LoginManager {
	private final String sts = "https://login.microsoftonline.com/extSTS.srf";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); //This format is compatible with DATA TIME format accepted by REST
	private final String login = "/_forms/default.aspx?wa=wsignin1.0";
	private final String reqXML = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:a=\"http://www.w3.org/2005/08/addressing\" xmlns:u=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"><s:Header><a:Action s:mustUnderstand=\"1\">http://schemas.xmlsoap.org/ws/2005/02/trust/RST/Issue</a:Action><a:ReplyTo><a:Address>http://www.w3.org/2005/08/addressing/anonymous</a:Address></a:ReplyTo><a:To s:mustUnderstand=\"1\">https://login.microsoftonline.com/extSTS.srf</a:To><o:Security s:mustUnderstand=\"1\" xmlns:o=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"><o:UsernameToken><o:Username>[username]</o:Username><o:Password>[password]</o:Password></o:UsernameToken></o:Security></s:Header><s:Body><t:RequestSecurityToken xmlns:t=\"http://schemas.xmlsoap.org/ws/2005/02/trust\"><wsp:AppliesTo xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"><a:EndpointReference><a:Address>[endpoint]</a:Address></a:EndpointReference></wsp:AppliesTo><t:KeyType>http://schemas.xmlsoap.org/ws/2005/05/identity/NoProofKey</t:KeyType><t:RequestType>http://schemas.xmlsoap.org/ws/2005/02/trust/Issue</t:RequestType><t:TokenType>urn:oasis:names:tc:SAML:1.0:assertion</t:TokenType></t:RequestSecurityToken></s:Body></s:Envelope>";
    private String rtFa = new String("");
    private String FedAuth = new String("");
    String token= new String("");
    String formDigestValue = new String("");
    String RpsContextCookie = new String("");
    String objectName = new String ("hello.pdf"); //Name of the document to be uploaded
    String endPoint = new String ("https://spatils.sharepoint.com"); //Base sharePoint site path
    String subSitePath= new String ("");//This path of sub site relative to base site
    String baseLibrayPath = new String("Shared Documents/"); //path where document will get stored OR folder will be created 

    String userName = new String("spatils@spatils.onmicrosoft.com");
    String psw = new String("Bus@9pm123");
    
    /*
    String endPoint = new String ("https://tatacommunications.sharepoint.com");
    String userName = new String("Hemant.Ala@tatacommunications.com");
    String psw = new String("Krishna123@");
      **/
 
	public static void main(String argv[]) throws Exception{
		LoginManager lm = new LoginManager();
		String cookieFinal =lm.login();
		System.out.println("rtFa**********"+lm.rtFa);
		System.out.println("FedAuth**********"+lm.FedAuth);
		System.out.println("RpsContextCookie**********"+lm.RpsContextCookie);
		 
		lm.getRquestDigest();
		 
		//lm.createFolder("ManageTransfer","Shared Documents");
		//lm.upLoadDocument("D:\\hello.pdf","/Shared Documents/ManageTransfer","hello.pdf");
		lm.getDocumentMetaData("");
	    //lm.setDocumentMetaData("/Shared Documents/ManageTransfer/hello.pdf");
	 
		Date today = new Date();
		System.out.println(lm.getRESTDate(today));
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
		System.out.println(connection.getResponseCode());
		StringBuilder sb = new StringBuilder("");
		while ((c = in.read()) != -1)
			sb.append((char) (c));
		in.close();
		String result = sb.toString();
		System.out.println(result);
		String token = extractToken(result);
		System.out.println(token);
		return token;
	}
	private String extractToken(String result) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document document = db.parse(new InputSource(new StringReader(result)));

        XPathFactory xpf = XPathFactory.newInstance();
        XPath xp = xpf.newXPath();
        String token = xp.evaluate("//BinarySecurityToken/text()", document.getDocumentElement());
        System.out.println(token);
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
	        System.out.println("header: " + headerName + " : " + headerValue);
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
		System.out.println("headerName");
		System.out.println(headerName);
		int c;
		StringBuilder sb = new StringBuilder("");
		while ((c = in.read()) != -1)
			sb.append((char) (c));
		in.close();
		String result = sb.toString();
		System.out.println(result);
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
		System.out.println(result);
		formDigestValue = extractDigestValue(result);
		if(formDigestValue== null || formDigestValue.trim().equals("")){
			throw new Exception("Unable To Get Digest Values ");
		}
		return " ";
	}
	 
	private String createFolder(String folderName,String baseFolderPath) throws  Exception {
		//For example folderPath   = Shared Documents  and folderName = f2
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
		String createFolderString = new String("{ '__metadata':{ 'type': 'SP.Folder' }, 'ServerRelativeUrl':'"+baseFolderPath+"/"+folderName+"' }");
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
		System.out.println(result);
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
		System.out.println(result);
		return "SUCCESS";
	}
	private String getDocumentMetaData(String documentPath) throws  Exception {
		/***
		 * This method is used to get the attribute details of custom type
		 * SharePoint sets some custom name to each field 
		 * This method can be used to get that custom name
		 */
		
		//String url = endPoint+subSitePath+"/_api/web/GetFileByServerRelativeUrl('/Shared Documents/f1/hello.pdf')/ListItemAllFields" ;
		String url ="https://spatils.sharepoint.com/mt/_api/web/GetFileByServerRelativeUrl('/mt/Shared%20Documents/ManageTransfer/f1/1.pdf')/ListItemAllFields" ;
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
		System.out.println(result);
		return "SUCCESS";
	}
	private String setDocumentMetaData(String documentPath) throws  Exception {
		//For example documentPath = /Shared Documents/f1/hello.pdf
		String url = endPoint+subSitePath+"/_api/web/GetFileByServerRelativeUrl('"+documentPath+"')/ListItemAllFields"  ;
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
		String updateString = new String("{'__metadata': { 'type': 'SP.Data.Shared_x0020_DocumentsItem'}, 'Ref_x0020_Num': '123','Doc_x0020_Category': 'docdc123','Is_x0020_Active': false,'Doc_x0020_Date':'2016-03-14T14:19:16' }");
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
		System.out.println(result);
		return "SUCCESS";
	}
	private String extractDigestValue(String result) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document document = db.parse(new InputSource(new StringReader(result)));

        XPathFactory xpf = XPathFactory.newInstance();
        XPath xp = xpf.newXPath();
        String token = xp.evaluate("//FormDigestValue/text()", document.getDocumentElement());
         System.out.println(token);
        return token;
	}
	public String getHttpCompatibleURL(String url){
		
		return url.replace(" ", "%20");
	}
	public String getRESTDate(Date inputDate){
		return sdf.format(inputDate);
	}
}