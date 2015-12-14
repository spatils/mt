package com.managetransfer.businessobject;
// Generated Jun 29, 2015 8:28:44 AM by Hibernate Tools 4.0.0



/**
 *          This class contains Adjuster notification relatied information
 *       
 */
public class ProcessVariable1  implements java.io.Serializable {


     private String taskId;
     private String start_bill_review_flag;
     private String PerformerName;
     private String object_type;
     private String content_type;
     private String adjuster_assigned;
     private boolean diff_department;
     private String r_object_id;
     private String start_updatemetadata_flag;
     private String updatemetadata_comments;
     private boolean is_duplicate;

    public ProcessVariable1() {
    }

	
    public ProcessVariable1(String taskId) {
        this.taskId = taskId;
    }
    public ProcessVariable1(String taskId, String start_bill_review_flag, String PerformerName, String object_type, String content_type, String adjuster_assigned, boolean diff_department, String r_object_id, String start_updatemetadata_flag, String updatemetadata_comments, boolean is_duplicate) {
       this.taskId = taskId;
       this.start_bill_review_flag = start_bill_review_flag;
       this.PerformerName = PerformerName;
       this.object_type = object_type;
       this.content_type = content_type;
       this.adjuster_assigned = adjuster_assigned;
       this.diff_department = diff_department;
       this.r_object_id = r_object_id;
       this.start_updatemetadata_flag = start_updatemetadata_flag;
       this.updatemetadata_comments = updatemetadata_comments;
       this.is_duplicate = is_duplicate;
    }
   
    public String getTaskId() {
        return this.taskId;
    }
    
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public String getStart_bill_review_flag() {
        return this.start_bill_review_flag;
    }
    
    public void setStart_bill_review_flag(String start_bill_review_flag) {
        this.start_bill_review_flag = start_bill_review_flag;
    }
    public String getPerformerName() {
        return this.PerformerName;
    }
    
    public void setPerformerName(String PerformerName) {
        this.PerformerName = PerformerName;
    }
    public String getObject_type() {
        return this.object_type;
    }
    
    public void setObject_type(String object_type) {
        this.object_type = object_type;
    }
    public String getContent_type() {
        return this.content_type;
    }
    
    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }
    public String getAdjuster_assigned() {
        return this.adjuster_assigned;
    }
    
    public void setAdjuster_assigned(String adjuster_assigned) {
        this.adjuster_assigned = adjuster_assigned;
    }
    public boolean isDiff_department() {
        return this.diff_department;
    }
    
    public void setDiff_department(boolean diff_department) {
        this.diff_department = diff_department;
    }
    public String getR_object_id() {
        return this.r_object_id;
    }
    
    public void setR_object_id(String r_object_id) {
        this.r_object_id = r_object_id;
    }
    public String getStart_updatemetadata_flag() {
        return this.start_updatemetadata_flag;
    }
    
    public void setStart_updatemetadata_flag(String start_updatemetadata_flag) {
        this.start_updatemetadata_flag = start_updatemetadata_flag;
    }
    public String getUpdatemetadata_comments() {
        return this.updatemetadata_comments;
    }
    
    public void setUpdatemetadata_comments(String updatemetadata_comments) {
        this.updatemetadata_comments = updatemetadata_comments;
    }
    public boolean isIs_duplicate() {
        return this.is_duplicate;
    }
    
    public void setIs_duplicate(boolean is_duplicate) {
        this.is_duplicate = is_duplicate;
    }




}


