package com.managetransfer.client;
// Generated Feb 19, 2015 4:10:49 AM by Hibernate Tools 4.0.0



/**
 *          This class contains the connection detail. 
 *       
 */
public class ConnectionDetails  implements java.io.Serializable {


     private String connectionName;
     private String connectionType;
     private String userName;
     private String userPsw;
     private String dataSource;
     private String domain;
     private String port;
     private String url;

    public ConnectionDetails() {
    }

	
    public ConnectionDetails(String connectionName) {
        this.connectionName = connectionName;
    }
    public ConnectionDetails(String connectionName, String connectionType, String userName, String userPsw, String dataSource, String domain, String port, String url) {
       this.connectionName = connectionName;
       this.connectionType = connectionType;
       this.userName = userName;
       this.userPsw = userPsw;
       this.dataSource = dataSource;
       this.domain = domain;
       this.port = port;
       this.url = url;
    }
   
    public String getConnectionName() {
        return this.connectionName;
    }
    
    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }
    public String getConnectionType() {
        return this.connectionType;
    }
    
    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }
    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPsw() {
        return this.userPsw;
    }
    
    public void setUserPsw(String userPsw) {
        this.userPsw = userPsw;
    }
    public String getDataSource() {
        return this.dataSource;
    }
    
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
    public String getDomain() {
        return this.domain;
    }
    
    public void setDomain(String domain) {
        this.domain = domain;
    }
    public String getPort() {
        return this.port;
    }
    
    public void setPort(String port) {
        this.port = port;
    }
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }




}


