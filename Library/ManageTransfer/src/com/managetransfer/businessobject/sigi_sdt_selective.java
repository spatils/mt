package com.managetransfer.businessobject;
// Generated Feb 19, 2015 4:10:49 AM by Hibernate Tools 4.0.0



/**
 *          This class contains the selective sdt  detail. 
 *       
 */
public class sigi_sdt_selective  implements java.io.Serializable {


     private String taskId;
     private String junk_id;
     private boolean mark_junk;
     private boolean is_junk;
     private int taxonomy_value;
     private String doc_control_number;
     private String department;
     private String document_source;

    public sigi_sdt_selective() {
    }

	
    public sigi_sdt_selective(String taskId) {
        this.taskId = taskId;
    }
    public sigi_sdt_selective(String taskId, String junk_id, boolean mark_junk, boolean is_junk, int taxonomy_value, String doc_control_number, String department, String document_source) {
       this.taskId = taskId;
       this.junk_id = junk_id;
       this.mark_junk = mark_junk;
       this.is_junk = is_junk;
       this.taxonomy_value = taxonomy_value;
       this.doc_control_number = doc_control_number;
       this.department = department;
       this.document_source = document_source;
    }
   
    public String getTaskId() {
        return this.taskId;
    }
    
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public String getJunk_id() {
        return this.junk_id;
    }
    
    public void setJunk_id(String junk_id) {
        this.junk_id = junk_id;
    }
    public boolean isMark_junk() {
        return this.mark_junk;
    }
    
    public void setMark_junk(boolean mark_junk) {
        this.mark_junk = mark_junk;
    }
    public boolean isIs_junk() {
        return this.is_junk;
    }
    
    public void setIs_junk(boolean is_junk) {
        this.is_junk = is_junk;
    }
    public int getTaxonomy_value() {
        return this.taxonomy_value;
    }
    
    public void setTaxonomy_value(int taxonomy_value) {
        this.taxonomy_value = taxonomy_value;
    }
    public String getDoc_control_number() {
        return this.doc_control_number;
    }
    
    public void setDoc_control_number(String doc_control_number) {
        this.doc_control_number = doc_control_number;
    }
    public String getDepartment() {
        return this.department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getDocument_source() {
        return this.document_source;
    }
    
    public void setDocument_source(String document_source) {
        this.document_source = document_source;
    }




}


