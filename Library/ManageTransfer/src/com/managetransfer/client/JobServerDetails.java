package com.managetransfer.client;
// Generated Nov 7, 2014 7:09:07 AM by Hibernate Tools 4.0.0



/**
 *          This class contains the connection detail. 
 *       
 */
public class JobServerDetails  implements java.io.Serializable {


     private String JobServerName;
     private String Server_url;

    public JobServerDetails() {
    }

	
    public JobServerDetails(String JobServerName) {
        this.JobServerName = JobServerName;
    }
    public JobServerDetails(String JobServerName, String Server_url) {
       this.JobServerName = JobServerName;
       this.Server_url = Server_url;
    }
   
    public String getJobServerName() {
        return this.JobServerName;
    }
    
    public void setJobServerName(String JobServerName) {
        this.JobServerName = JobServerName;
    }
    public String getServer_url() {
        return this.Server_url;
    }
    
    public void setServer_url(String Server_url) {
        this.Server_url = Server_url;
    }




}

