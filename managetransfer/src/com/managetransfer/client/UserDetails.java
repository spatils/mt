package com.managetransfer.client;
// Generated Nov 8, 2013 11:46:53 AM by Hibernate Tools 4.0.0



/**
 *          This class contains the connection detail. 
 *       
 */
public class UserDetails  implements java.io.Serializable {
 
	private static final long serialVersionUID = 1350463079721021562L;
	private String userId;
     private String psw;
     private String repository;
     private String url;

    public UserDetails() {
    }

	
    public UserDetails(String userId) {
        this.userId = userId;
    }
    public UserDetails(String userId, String psw, String repository, String url) {
       this.userId = userId;
       this.psw = psw;
       this.repository = repository;
       this.url = url;
    }
   
    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getPsw() {
        return this.psw;
    }
    
    public void setPsw(String psw) {
        this.psw = psw;
    }
    public String getRepository() {
        return this.repository;
    }
    
    public void setRepository(String repository) {
        this.repository = repository;
    }
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }




}


