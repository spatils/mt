package com.managetransfer.client;
// Generated Nov 8, 2013 11:46:53 AM by Hibernate Tools 4.0.0



/**
 *          This class contains the connection detail. 
 *       
 */
public class PhasesDetailsInt  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = -3320478925477489622L;
	private String phaseName;
     private String parameterName;
     private Integer parameterValue;

    public PhasesDetailsInt() {
    }

	
    public PhasesDetailsInt(String phaseName, String parameterName) {
        this.phaseName = phaseName;
        this.parameterName = parameterName;
    }
    public PhasesDetailsInt(String phaseName, String parameterName, Integer parameterValue) {
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
    public Integer getParameterValue() {
        return this.parameterValue;
    }
    
    public void setParameterValue(Integer parameterValue) {
        this.parameterValue = parameterValue;
    }




}


