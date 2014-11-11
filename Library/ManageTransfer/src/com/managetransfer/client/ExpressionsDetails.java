package com.managetransfer.client;
// Generated Nov 10, 2014 9:52:48 AM by Hibernate Tools 4.0.0



/**
 *          This class contains the connection detail. 
 *       
 */
public class ExpressionsDetails  implements java.io.Serializable {


     private String expressionsName;
     private String expressionsType;
     private String expression;

    public ExpressionsDetails() {
    }

	
    public ExpressionsDetails(String expressionsName) {
        this.expressionsName = expressionsName;
    }
    public ExpressionsDetails(String expressionsName, String expressionsType, String expression) {
       this.expressionsName = expressionsName;
       this.expressionsType = expressionsType;
       this.expression = expression;
    }
   
    public String getExpressionsName() {
        return this.expressionsName;
    }
    
    public void setExpressionsName(String expressionsName) {
        this.expressionsName = expressionsName;
    }
    public String getExpressionsType() {
        return this.expressionsType;
    }
    
    public void setExpressionsType(String expressionsType) {
        this.expressionsType = expressionsType;
    }
    public String getExpression() {
        return this.expression;
    }
    
    public void setExpression(String expression) {
        this.expression = expression;
    }




}


