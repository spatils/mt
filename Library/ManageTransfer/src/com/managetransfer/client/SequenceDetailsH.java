package com.managetransfer.client;
// Generated Dec 2, 2014 4:02:49 AM by Hibernate Tools 4.0.0


import java.util.HashMap;
import java.util.Map;

/**
 *          This class contains the connection detail. 
 *       
 */
public class SequenceDetailsH  implements java.io.Serializable {


     private String sequenceName;
     private int numberOfPhases;
     private Map sequenceDetailsMap = new HashMap(0);

    public SequenceDetailsH() {
    }

	
    public SequenceDetailsH(String sequenceName) {
        this.sequenceName = sequenceName;
    }
    public SequenceDetailsH(String sequenceName, int numberOfPhases, Map sequenceDetailsMap) {
       this.sequenceName = sequenceName;
       this.numberOfPhases = numberOfPhases;
       this.sequenceDetailsMap = sequenceDetailsMap;
    }
   
    public String getSequenceName() {
        return this.sequenceName;
    }
    
    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }
    public int getNumberOfPhases() {
        return this.numberOfPhases;
    }
    
    public void setNumberOfPhases(int numberOfPhases) {
        this.numberOfPhases = numberOfPhases;
    }
    public Map getSequenceDetailsMap() {
        return this.sequenceDetailsMap;
    }
    
    public void setSequenceDetailsMap(Map sequenceDetailsMap) {
        this.sequenceDetailsMap = sequenceDetailsMap;
    }




}


