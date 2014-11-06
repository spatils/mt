package com.admin.shared.session;

import java.util.ArrayList;
import java.util.List;
import com.managetransfer.client.ConnectionDetails;
import com.managetransfer.hibernate.HibernateConnection;


public class ConnectionsDetailsServer {
	List<ConnectionDetails> connectionResult = null;
	ArrayList<ConnectionDetails> arrayConnection = new ArrayList<ConnectionDetails>();
	ArrayList<String> connectionType = new ArrayList<String>();
	HibernateConnection hc = new HibernateConnection();
    	 
	public ConnectionsDetailsServer(){
		
	}
	public ArrayList<ConnectionDetails> getConnectionResult(String input){
		return arrayConnection;
			
	}
	public boolean addConnection(ConnectionDetails input) {
		try{
			hc.startBatchLevelTransaction();
			hc.saveOperation(input);
			hc.commitBatchLevelTransaction();
			
			connectionResult.add(new ConnectionDetails(input.getConnectionName(),input.getConnectionType(),input.getUserName(),input.getUserPsw(),input.getDataSource(),input.getDomain(),input.getPort(),input.getPort()));
		}
		catch(Exception e){
			hc.abortBatchLevelTransaction();
		}
		return true;
	}
	public ArrayList<ConnectionDetails> searchConnectionResult(String input) {
		// TODO Auto-generated method stub
		List<ConnectionDetails> searchConnectionResult = null;
		ArrayList<ConnectionDetails> searchArrayConnection = new ArrayList<ConnectionDetails>();
		searchConnectionResult =searchArrayConnection;
		for(ConnectionDetails sr : connectionResult){
			if (sr.getConnectionName().contains(input)){
				searchConnectionResult.add(sr);
			}
		}
		
		return (ArrayList<ConnectionDetails>) searchConnectionResult;
	}
	public Boolean deleteConnection(String input) {
		// TODO Auto-generated method stub
		int i=0;
		for( ConnectionDetails sr : connectionResult){
			if (sr.getConnectionName().equals(input)){
				try{
					hc.startBatchLevelTransaction();
					hc.deleteOperation(sr);
					hc.commitBatchLevelTransaction();
				 	break; 
				}
				catch(Exception e){
					hc.abortBatchLevelTransaction();
					System.out.println("Error in deleteConnection"+e);
					
				}
			}
			i = i+1;
		}
		connectionResult.remove(i);
		return true;
	}
	public boolean editConnection(ConnectionDetails connectionDetail) {
		// TODO Auto-generated method stub
		int i=0;
		for( ConnectionDetails sr : connectionResult){
			if (sr.getConnectionName().equals(connectionDetail.getConnectionName())){
				try{
					System.out.println("before update");
					connectionResult.get(i).setConnectionType(connectionDetail.getConnectionType());
					connectionResult.get(i).setDataSource(connectionDetail.getDataSource());
					connectionResult.get(i).setUserName(connectionDetail.getUserName());
					connectionResult.get(i).setUserPsw(connectionDetail.getUserPsw());
					connectionResult.get(i).setDomain(connectionDetail.getDomain());
					connectionResult.get(i).setPort(connectionDetail.getPort());
					connectionResult.get(i).setUrl(connectionDetail.getUrl());
				 	System.out.println("after update");
				 	hc.startBatchLevelTransaction();
					hc.saveOperation(connectionResult.get(i));
					System.out.println(connectionDetail.getUrl());
					hc.commitBatchLevelTransaction();
					
				}
				catch(Exception e){
					System.out.println("Error in edit connection"+e);
					hc.abortBatchLevelTransaction();
				}
			}
			i = i+1;
		}
		return true;
	}
	public ArrayList<String> getConnectionTypeList() {
		// TODO Auto-generated method stub
		System.out.println("Size"+connectionType.size());
		System.out.println("getConnectionType");
		return connectionType;
	}
	public HibernateConnection getHc() {
		return hc;
	}
	public void setHc(HibernateConnection hc) {
		this.hc = hc;
		List list = hc.getObject("from ConnectionDetails ");
		connectionResult = arrayConnection;
		for (int i=0 ; i < list.size();i++){
			connectionResult.add((ConnectionDetails)list.get(i));
		}
		if (connectionResult.size()==0) {
			connectionResult.add(new ConnectionDetails("Oracle","userName","userpsw","repo","dummy"));
			
			
		 }
		connectionType.clear();
		connectionType.add("Documentum");
		connectionType.add("SharePoint");
 	}
	public ArrayList<String> getConnectionList() {
		ArrayList<String> connectionList = new ArrayList<String>();
		for(int i=0;i<connectionResult.size();i++){
			connectionList.add(connectionResult.get(i).getConnectionName());
		}
		return connectionList;
	}
	

}
