package com.managetransfer.hibernate;
 
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.Session;
public class HibernateConnection {
	private   SessionFactory sessionFactory = null;
	private   ServiceRegistry serviceRegistry = null;
	private   Session hibernateSession = null;
	private   Transaction batchLevelTransaction = null;
	private   Transaction recordLevelTransaction = null;
	final Logger logger = Logger.getLogger(HibernateConnection.class.getName()) ;
	public static void main(String[] args) {
		
		 
	}
	public  void initOperation() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
        	Configuration configuration = new Configuration();
            configuration.configure();
            if (serviceRegistry==null ){
            	serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            }
            if(sessionFactory==null){
            	sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            }
            if(hibernateSession==null){
            	hibernateSession = sessionFactory.openSession();
            }
        }
        catch (Throwable ex) {
            // Make sure you log the exception
            System.err.println("initOperation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
	public SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	public Session getHibernateSession() {
		return hibernateSession;
	}
	public void setHibernateSession(Session hibernateSession) {
		this.hibernateSession = hibernateSession;
	}
	public void startRecordLevelTransaction(){
		recordLevelTransaction = hibernateSession.beginTransaction();
	}
	 
	public void startBatchLevelTransaction(){
		batchLevelTransaction = hibernateSession.beginTransaction();
	}
	public void commitBatchLevelTransaction(){
		if (batchLevelTransaction!= null && batchLevelTransaction.isActive()){
			batchLevelTransaction.commit();
		}
	}
	public void abortBatchLevelTransaction(){
		if (batchLevelTransaction!= null && batchLevelTransaction.isActive()){
			batchLevelTransaction.rollback();
		}
	}
	public void abortRecordLevelTransaction(){
		if (recordLevelTransaction!= null && recordLevelTransaction.isActive()){
			recordLevelTransaction.rollback();
		}
	}
	public void saveOperation(Object persisterObject){
		hibernateSession.save(persisterObject);
	}
	public void deleteOperation(Object persisterObject){
		hibernateSession.delete(persisterObject);
	}
	public void updateOperation(Object persisterObject){
		hibernateSession.update(persisterObject);
	}
    public  List  getObject(String queryString){
    	Query query = hibernateSession.createQuery(queryString);
    	 List list = query.list();
    	 return list;
    }
    public void finalCommitOperation(){
    	if(batchLevelTransaction!= null && batchLevelTransaction.isActive()){
    		batchLevelTransaction.commit();
    	}
    	if(recordLevelTransaction!= null && recordLevelTransaction.isActive()){
    		recordLevelTransaction.commit();
    	}
    }
    public void closeConnection(){
    	if(hibernateSession != null  ){
    		hibernateSession.close();
    	}
    	if(sessionFactory!=null){
    		sessionFactory.close();
    	}
    }
	public void clearObjectCache(){
		hibernateSession.clear();
	}
}
