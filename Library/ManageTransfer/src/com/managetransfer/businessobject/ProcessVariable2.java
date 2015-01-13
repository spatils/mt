package com.managetransfer.businessobject;
// Generated Jan 13, 2015 12:37:55 AM by Hibernate Tools 4.0.0



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
     private String diff_department;
     private String checkcomments;
     private String michellcomments;
     private String stat_updatemetadata_flag;
     private String updatemetadata_comments;
     private String is_duplicate;
     private String CommentsTemp;

    public ProcessVariable2() {
    }

	
    public ProcessVariable2(String taskId) {
        this.taskId = taskId;
    }
    public ProcessVariable2(String taskId, String form_benefit_state, int dqlupdateobj, String dcn, String contenttype, String adjuster_assigned, String diff_department, String checkcomments, String michellcomments, String stat_updatemetadata_flag, String updatemetadata_comments, String is_duplicate, String CommentsTemp) {
       this.taskId = taskId;
       this.form_benefit_state = form_benefit_state;
       this.dqlupdateobj = dqlupdateobj;
       this.dcn = dcn;
       this.contenttype = contenttype;
       this.adjuster_assigned = adjuster_assigned;
       this.diff_department = diff_department;
       this.checkcomments = checkcomments;
       this.michellcomments = michellcomments;
       this.stat_updatemetadata_flag = stat_updatemetadata_flag;
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
    public String getDiff_department() {
        return this.diff_department;
    }
    
    public void setDiff_department(String diff_department) {
        this.diff_department = diff_department;
    }
    public String getCheckcomments() {
        return this.checkcomments;
    }
    
    public void setCheckcomments(String checkcomments) {
        this.checkcomments = checkcomments;
    }
    public String getMichellcomments() {
        return this.michellcomments;
    }
    
    public void setMichellcomments(String michellcomments) {
        this.michellcomments = michellcomments;
    }
    public String getStat_updatemetadata_flag() {
        return this.stat_updatemetadata_flag;
    }
    
    public void setStat_updatemetadata_flag(String stat_updatemetadata_flag) {
        this.stat_updatemetadata_flag = stat_updatemetadata_flag;
    }
    public String getUpdatemetadata_comments() {
        return this.updatemetadata_comments;
    }
    
    public void setUpdatemetadata_comments(String updatemetadata_comments) {
        this.updatemetadata_comments = updatemetadata_comments;
    }
    public String getIs_duplicate() {
        return this.is_duplicate;
    }
    
    public void setIs_duplicate(String is_duplicate) {
        this.is_duplicate = is_duplicate;
    }
    public String getCommentsTemp() {
        return this.CommentsTemp;
    }
    
    public void setCommentsTemp(String CommentsTemp) {
        this.CommentsTemp = CommentsTemp;
    }




}


