package com.managetransfer.client;
// Generated Dec 2, 2014 4:02:49 AM by Hibernate Tools 4.0.0



/**
 *          This class contains information related business object
 *       
 */
public class ObjectDetails  implements java.io.Serializable {


     private String objectName;
     private String objectType;
     private String repoObjectName;
     private String repeatingObject;
     private String relationshipColumns;

    public ObjectDetails() {
    }

	
    public ObjectDetails(String objectName) {
        this.objectName = objectName;
    }
    public ObjectDetails(String objectName, String objectType, String repoObjectName, String repeatingObject, String relationshipColumns) {
       this.objectName = objectName;
       this.objectType = objectType;
       this.repoObjectName = repoObjectName;
       this.repeatingObject = repeatingObject;
       this.relationshipColumns = relationshipColumns;
    }
   
    public String getObjectName() {
        return this.objectName;
    }
    
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
    public String getObjectType() {
        return this.objectType;
    }
    
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }
    public String getRepoObjectName() {
        return this.repoObjectName;
    }
    
    public void setRepoObjectName(String repoObjectName) {
        this.repoObjectName = repoObjectName;
    }
    public String getRepeatingObject() {
        return this.repeatingObject;
    }
    
    public void setRepeatingObject(String repeatingObject) {
        this.repeatingObject = repeatingObject;
    }
    public String getRelationshipColumns() {
        return this.relationshipColumns;
    }
    
    public void setRelationshipColumns(String relationshipColumns) {
        this.relationshipColumns = relationshipColumns;
    }




}


