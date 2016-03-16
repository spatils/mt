package com.admin.shared.session; 


import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpSession; 

import com.google.gwt.event.shared.EventBus;
import com.managetransfer.hibernate.HibernateConnection;
import com.managetransfer.client.UserDetails;
import com.managetransfer.client.event.LoginEvent;

import javax.validation.ValidatorFactory;
 
 
  
  
public class AllSessions { 

    private static AllSessions allSessions = null; 
    private static final String USER_SESSION = "userSession"; 
    private HttpServletRequest request = null; 
    private HttpSession session = null; 
    private String sessionId = ""; 
    private String USER_DETAILS = "USER_DETAILS";
    private String IS_LOGGED_IN = "IS_LOGGED_IN";
    private boolean isLoggedIn = false ; 
    private static Logger log4j ;
    private HibernateConnection hc = new HibernateConnection();
        public static AllSessions getInstance(){ 
        	    if (allSessions ==null){ 
                        allSessions = new AllSessions();
                        System.out.println("Creating new allSessions");
                } else{
                	System.out.println("Already exist allSessions");
                }
        	    return allSessions; 
        } 
        protected AllSessions(){
        	
        }
       
        public synchronized void setUser(UserDetails userDetails){ 
        	  if(null == userDetails){
                  if(session!=null) session.removeAttribute(USER_DETAILS);
                  return;
              }
              
              if(null != request)
                  this.session = request.getSession(true);
               
              if(session!=null){
            	  try{
	            	  session.setAttribute(USER_DETAILS, userDetails);
	                  session.setAttribute(IS_LOGGED_IN, isLoggedIn);
	                  System.out.println("Initializing hibernate session");
	                  this.hc.initOperation();
	                  session.setAttribute("HIBERNATE_SESSION", this.hc);
	                  setSessionId(session.getId());
                  } catch(IllegalStateException  e){
                	  System.out.println("IllegateState of session");
                	  throw e; 
                  }
            	  
              }

        } 
        public  UserDetails getUser(){

            if(null == session) return null;

            return session.getAttribute(USER_SESSION) != null ? 
                    (UserDetails)session.getAttribute(USER_SESSION) : null;
        }
        public HttpSession getSession(){
            return session;
        }
        public  void invalidate(){
            if(request!=null) 
                if(request.getSession(false)!= null)
                     request.getSession(false).invalidate();
            if(null != session){    
                session.invalidate();
                session = null;
            }
            setSessionId(null);
            
        }
        public String getId(){
            return request.getSession(false).getId();
        }

        public HttpServletRequest getRequest() {
            return request;
        }

        public   synchronized void setRequest(HttpServletRequest request) {
            this.request = request;
        }

        public String getSessionId() {
            return sessionId;
        }


        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public synchronized boolean  isAuthenticated(){
        	
        	 if (null== session || null == session.getAttribute(IS_LOGGED_IN)       ){
        		 System.out.println("Inside isAuthenticated false");
        		 return false;
        	 }else{
        		 System.out.println("Inside isAuthenticated true");
        		 return true;
        	 }
        }
		public synchronized HibernateConnection getHc() {
			this.hc  = (HibernateConnection )session.getAttribute("HIBERNATE_SESSION" );
			if(! this.hc.getHibernateSession().isConnected()) {
				System.out.println("hc sesion is disconnected");
				this.hc.initOperation();
			}
			return hc;
		}
		public void setHc(HibernateConnection hc) {
			this.hc = hc;
		}
		public synchronized String logOut(){
			if (null != session && null != session.getAttribute(IS_LOGGED_IN)       ){
		    	this.hc.closeConnection();
		    	session.removeAttribute(IS_LOGGED_IN);
		    	invalidate();
	       	}
			return "Success";
		}


        
} 


