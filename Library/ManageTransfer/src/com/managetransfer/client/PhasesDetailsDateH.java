package com.managetransfer.client;
// Generated Nov 11, 2014 6:02:54 AM by Hibernate Tools 4.0.0


import java.util.Date;

/**
 *          This class contains the connection detail. 
 *       
 */
public class PhasesDetailsDateH  implements java.io.Serializable {


     private String phaseName;
     private String parameterName;
     private Date parameterValue;

    public PhasesDetailsDateH() {
    }

	
    public PhasesDetailsDateH(String phaseName, String parameterName) {
        this.phaseName = phaseName;
        this.parameterName = parameterName;
    }
    public PhasesDetailsDateH(String phaseName, String parameterName, Date parameterValue) {
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
    public Date getParameterValue() {
        return this.parameterValue;
    }
    
    public void setParameterValue(Date parameterValue) {
        this.parameterValue = parameterValue;
    }




}


