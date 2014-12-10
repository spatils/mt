package com.managetransfer.businessobject;
// Generated Dec 10, 2014 3:00:52 AM by Hibernate Tools 4.0.0



/**
 *          Annotation relation information is stored in this table 
 *       
 */
public class Annotation  implements java.io.Serializable {


     private String objectId;
     private String repositoryPath;
     private String filePath;

    public Annotation() {
    }

	
    public Annotation(String objectId) {
        this.objectId = objectId;
    }
    public Annotation(String objectId, String repositoryPath, String filePath) {
       this.objectId = objectId;
       this.repositoryPath = repositoryPath;
       this.filePath = filePath;
    }
   
    public String getObjectId() {
        return this.objectId;
    }
    
    public void setObjectId(String objectId) {
        this.objectId = objectId;
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




}


