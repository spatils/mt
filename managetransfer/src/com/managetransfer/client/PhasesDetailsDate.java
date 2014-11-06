package com.managetransfer.client;
// Generated Nov 8, 2013 11:46:53 AM by Hibernate Tools 4.0.0


import java.util.Date;

/**
 *          This class contains the connection detail. 
 *       
 */
public class PhasesDetailsDate  implements java.io.Serializable {


     /**
	 * 
	 */
	private static final long serialVersionUID = -8910291459231998674L;
	private String phaseName;
     private String parameterName;
     private Date parameterValue;

    public PhasesDetailsDate() {
    }

	
    public PhasesDetailsDate(String phaseName, String parameterName) {
        this.phaseName = phaseName;
        this.parameterName = parameterName;
    }
    public PhasesDetailsDate(String phaseName, String parameterName, Date parameterValue) {
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


