package com.managetransfer.client;
// Generated Mar 15, 2016 1:49:44 PM by Hibernate Tools 4.0.0


import java.util.HashMap;
import java.util.Map;

/**
 *          This class contains the connection detail. 
 *       
 */
public class PhasesDetailsH  implements java.io.Serializable {


     private String phaseName;
     private String phaseType;
     private Map phaseDetailsString = new HashMap(0);
     private Map phaseDetailsDate = new HashMap(0);
     private Map phaseDetailsInt = new HashMap(0);

    public PhasesDetailsH() {
    }

	
    public PhasesDetailsH(String phaseName) {
        this.phaseName = phaseName;
    }
    public PhasesDetailsH(String phaseName, String phaseType, Map phaseDetailsString, Map phaseDetailsDate, Map phaseDetailsInt) {
       this.phaseName = phaseName;
       this.phaseType = phaseType;
       this.phaseDetailsString = phaseDetailsString;
       this.phaseDetailsDate = phaseDetailsDate;
       this.phaseDetailsInt = phaseDetailsInt;
    }
   
    public String getPhaseName() {
        return this.phaseName;
    }
    
    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }
    public String getPhaseType() {
        return this.phaseType;
    }
    
    public void setPhaseType(String phaseType) {
        this.phaseType = phaseType;
    }
    public Map getPhaseDetailsString() {
        return this.phaseDetailsString;
    }
    
    public void setPhaseDetailsString(Map phaseDetailsString) {
        this.phaseDetailsString = phaseDetailsString;
    }
    public Map getPhaseDetailsDate() {
        return this.phaseDetailsDate;
    }
    
    public void setPhaseDetailsDate(Map phaseDetailsDate) {
        this.phaseDetailsDate = phaseDetailsDate;
    }
    public Map getPhaseDetailsInt() {
        return this.phaseDetailsInt;
    }
    
    public void setPhaseDetailsInt(Map phaseDetailsInt) {
        this.phaseDetailsInt = phaseDetailsInt;
    }




}


