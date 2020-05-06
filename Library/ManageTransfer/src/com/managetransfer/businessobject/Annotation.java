package com.managetransfer.businessobject;
// Generated 2 May, 2020 2:29:40 PM by Hibernate Tools 4.0.0



/**
 *          Annotation relation information is stored in this table 
 *       
 */
public class Annotation  implements java.io.Serializable {


     private String objectId;
     private int seqNumber;
     private String repositoryPath;
     private String filePath;
     private String ownerName;
     private String objectName;
     private String aclName;

    public Annotation() {
    }

	
    public Annotation(String objectId, int seqNumber) {
        this.objectId = objectId;
        this.seqNumber = seqNumber;
    }
    public Annotation(String objectId, int seqNumber, String repositoryPath, String filePath, String ownerName, String objectName, String aclName) {
       this.objectId = objectId;
       this.seqNumber = seqNumber;
       this.repositoryPath = repositoryPath;
       this.filePath = filePath;
       this.ownerName = ownerName;
       this.objectName = objectName;
       this.aclName = aclName;
    }
   
    public String getObjectId() {
        return this.objectId;
    }
    
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    public int getSeqNumber() {
        return this.seqNumber;
    }
    
    public void setSeqNumber(int seqNumber) {
        this.seqNumber = seqNumber;
    }
    public String getRepositoryPath() {
        return this.repositoryPath;
    }
    
    public void setRepositoryPath(String repositoryPath) {
        this.repositoryPath = repositoryPath;
    }
    public String getFilePath() {
        return this.filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public String getOwnerName() {
        return this.ownerName;
    }
    
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    public String getObjectName() {
        return this.objectName;
    }
    
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
    public String getAclName() {
        return this.aclName;
    }
    
    public void setAclName(String aclName) {
        this.aclName = aclName;
    }




}


