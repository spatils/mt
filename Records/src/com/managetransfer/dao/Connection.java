package com.managetransfer.dao;
// Generated Oct 15, 2013 2:39:14 PM by Hibernate Tools 4.0.0



/**
 *          This class contains the connection detail. 
 *       
 */
public class Connection  implements java.io.Serializable {


     private String connectionName;
     private String connectionType;
     private String connectionUser;
     private String connectionPassword;
     private String connectionURL;

    public Connection() {
    }

	
    public Connection(String connectionName) {
        this.connectionName = connectionName;
    }
    public Connection(String connectionName, String connectionType, String connectionUser, String connectionPassword, String connectionURL) {
       this.connectionName = connectionName;
       this.connectionType = connectionType;
       this.connectionUser = connectionUser;
       this.connectionPassword = connectionPassword;
       this.connectionURL = connectionURL;
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
    public String getConnectionUser() {
        return this.connectionUser;
    }
    
    public void setConnectionUser(String connectionUser) {
        this.connectionUser = connectionUser;
    }
    public String getConnectionPassword() {
        return this.connectionPassword;
    }
    
    public void setConnectionPassword(String connectionPassword) {
        this.connectionPassword = connectionPassword;
    }
    public String getConnectionURL() {
        return this.connectionURL;
    }
    
    public void setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
    }




}


