package com.managetransfer.businessobject;
// Generated Mar 15, 2016 1:49:44 PM by Hibernate Tools 4.0.0



/**
 * 			This class contains bill related process
 * 			variables
 * 		
 */
public class ProcessVariable3  implements java.io.Serializable {


     private String taskId;
     private String irs_name;
     private String irs_number;

    public ProcessVariable3() {
    }

	
    public ProcessVariable3(String taskId) {
        this.taskId = taskId;
    }
    public ProcessVariable3(String taskId, String irs_name, String irs_number) {
       this.taskId = taskId;
       this.irs_name = irs_name;
       this.irs_number = irs_number;
    }
   
    public String getTaskId() {
        return this.taskId;
    }
    
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public String getIrs_name() {
        return this.irs_name;
    }
    
    public void setIrs_name(String irs_name) {
        this.irs_name = irs_name;
    }
    public String getIrs_number() {
        return this.irs_number;
    }
    
    public void setIrs_number(String irs_number) {
        this.irs_number = irs_number;
    }




}


