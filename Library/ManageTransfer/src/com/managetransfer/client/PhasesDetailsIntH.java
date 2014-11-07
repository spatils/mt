package com.managetransfer.client;
// Generated Nov 7, 2014 7:09:07 AM by Hibernate Tools 4.0.0



/**
 *          This class contains the connection detail. 
 *       
 */
public class PhasesDetailsIntH  implements java.io.Serializable {


     private String phaseName;
     private String parameterName;
     private int parameterValue;

    public PhasesDetailsIntH() {
    }

	
    public PhasesDetailsIntH(String phaseName, String parameterName) {
        this.phaseName = phaseName;
        this.parameterName = parameterName;
    }
    public PhasesDetailsIntH(String phaseName, String parameterName, int parameterValue) {
       this.phaseName = phaseName;
       this.parameterName = parameterName;
       this.parameterValue = parameterValue;
    }
   
    public String getPhaseName() {
        return this.phaseName;
    }
    
    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }
    public String getParameterName() {
        return this.parameterName;
    }
    
    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }
    public int getParameterValue() {
        return this.parameterValue;
    }
    
    public void setParameterValue(int parameterValue) {
        this.parameterValue = parameterValue;
    }




}


