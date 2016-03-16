package com.managetransfer.businessobject;
// Generated Mar 15, 2016 1:49:44 PM by Hibernate Tools 4.0.0



/**
 * 			This class contains bill related process
 * 			variables
 * 		
 */
public class ProcessVariable2  implements java.io.Serializable {


     private String taskId;
     private String form_benefit_state;
     private int dqlupdateobj;
     private String dcn;
     private String contenttype;
     private String adjuster_assigned;
     private boolean diff_department;
     private String checkcomments;
     private String mitchellcomments;
     private String start_updatemetadata_flag;
     private String updatemetadata_comments;
     private boolean is_duplicate;
     private String CommentsTemp;

    public ProcessVariable2() {
    }

	
    public ProcessVariable2(String taskId) {
        this.taskId = taskId;
    }
    public ProcessVariable2(String taskId, String form_benefit_state, int dqlupdateobj, String dcn, String contenttype, String adjuster_assigned, boolean diff_department, String checkcomments, String mitchellcomments, String start_updatemetadata_flag, String updatemetadata_comments, boolean is_duplicate, String CommentsTemp) {
       this.taskId = taskId;
       this.form_benefit_state = form_benefit_state;
       this.dqlupdateobj = dqlupdateobj;
       this.dcn = dcn;
       this.contenttype = contenttype;
       this.adjuster_assigned = adjuster_assigned;
       this.diff_department = diff_department;
       this.checkcomments = checkcomments;
       this.mitchellcomments = mitchellcomments;
       this.start_updatemetadata_flag = start_updatemetadata_flag;
       this.updatemetadata_comments = updatemetadata_comments;
       this.is_duplicate = is_duplicate;
       this.CommentsTemp = CommentsTemp;
    }
   
    public String getTaskId() {
        return this.taskId;
    }
    
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public String getForm_benefit_state() {
        return this.form_benefit_state;
    }
    
    public void setForm_benefit_state(String form_benefit_state) {
        this.form_benefit_state = form_benefit_state;
    }
    public int getDqlupdateobj() {
        return this.dqlupdateobj;
    }
    
    public void setDqlupdateobj(int dqlupdateobj) {
        this.dqlupdateobj = dqlupdateobj;
    }
    public String getDcn() {
        return this.dcn;
    }
    
    public void setDcn(String dcn) {
        this.dcn = dcn;
    }
    public String getContenttype() {
        return this.contenttype;
    }
    
    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
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
    public String getCheckcomments() {
        return this.checkcomments;
    }
    
    public void setCheckcomments(String checkcomments) {
        this.checkcomments = checkcomments;
    }
    public String getMitchellcomments() {
        return this.mitchellcomments;
    }
    
    public void setMitchellcomments(String mitchellcomments) {
        this.mitchellcomments = mitchellcomments;
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
    public String getCommentsTemp() {
        return this.CommentsTemp;
    }
    
    public void setCommentsTemp(String CommentsTemp) {
        this.CommentsTemp = CommentsTemp;
    }




}


