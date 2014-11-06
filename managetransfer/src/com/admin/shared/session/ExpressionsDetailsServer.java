package com.admin.shared.session;

import java.util.ArrayList;
import java.util.List;

import com.managetransfer.client.ConnectionDetails;
import com.managetransfer.client.ExpressionsDetails;
import com.managetransfer.hibernate.HibernateConnection;

public class ExpressionsDetailsServer {
	List<ExpressionsDetails> expressionsResult = null;
	ArrayList<ExpressionsDetails> arrayExpressions = new ArrayList<ExpressionsDetails>();
	ArrayList<String> experssionsTypeList = new ArrayList<String>();
	HibernateConnection hc = new HibernateConnection();
	public HibernateConnection getHc() {
		return hc;
	}
	public void setHc(HibernateConnection hc) {
		try{
			this.hc = hc;
			expressionsResult = arrayExpressions;
			List list = hc.getObject("from ExpressionsDetails ");
			for (int i=0 ; i < list.size();i++){
				expressionsResult.add((ExpressionsDetails)list.get(i));
			}
			if (expressionsResult.size()==0) {
				expressionsResult.add(new ExpressionsDetails("DummyExpression","ReturnString","'{$user_name}'_test"));
			}
			experssionsTypeList.clear();
			experssionsTypeList.add("ReturnString");
			experssionsTypeList.add("ReturnInt");
			experssionsTypeList.add("ReturnDate");
			experssionsTypeList.add("Validate");
			experssionsTypeList.add("Repeating");
			experssionsTypeList.add("ReturnString");
		}catch(Exception e){
			System.out.println("Error"+e);
		}
	}
	
	public ExpressionsDetailsServer(){
		
	}
	public ArrayList<ExpressionsDetails> getExpressionResult(String input){
		return arrayExpressions;
			
	}
	public boolean addExpression(ExpressionsDetails input) {
		try{
			hc.startBatchLevelTransaction();
			hc.saveOperation(input);
			hc.commitBatchLevelTransaction();
			expressionsResult.add(new ExpressionsDetails(input.getExpressionsName(),input.getExpressionsType(),input.getExpression()));
		}
		catch(Exception e){
			System.out.println("Error"+e);
			hc.abortBatchLevelTransaction();
		}
		return true;
	}
	public ArrayList<ExpressionsDetails> searchExpressionResult(String input) {
		// TODO Auto-generated method stub
		List<ExpressionsDetails> searchExpressionsResult = null;
		ArrayList<ExpressionsDetails> searchArrayConnection = new ArrayList<ExpressionsDetails>();
		searchExpressionsResult =searchArrayConnection;
		for(ExpressionsDetails sr : expressionsResult){
			if (sr.getExpressionsName().contains(input)){
				searchExpressionsResult.add(sr);
			}
		}
		
		return (ArrayList<ExpressionsDetails>) searchExpressionsResult;
	}
	public Boolean deleteExpression(String input) {
		// TODO Auto-generated method stub
		int i=0;
		for( ExpressionsDetails sr : expressionsResult){
			if (sr.getExpressionsName().equals(input)){
				try{
				 
					hc.startBatchLevelTransaction();
					hc.deleteOperation(sr);
					hc.commitBatchLevelTransaction();
				 	break; 
				}
				catch(Exception e){
					System.out.println("Error"+e);
					hc.abortBatchLevelTransaction();
				}
			}
			i = i+1;
		}
		expressionsResult.remove(i);
		return true;
	}
	public boolean editExpression(ExpressionsDetails expressionsDetail) {
		// TODO Auto-generated method stub
		int i=0;
		for( ExpressionsDetails sr : expressionsResult){
			if (sr.getExpressionsName().equals(expressionsDetail.getExpressionsName())){
				try{
					expressionsResult.get(i).setExpressionsType(expressionsDetail.getExpressionsType());
					expressionsResult.get(i).setExpression(expressionsDetail.getExpression());
					hc.startBatchLevelTransaction();
					hc.updateOperation(sr);
					hc.commitBatchLevelTransaction();
				}
				catch(Exception e){
					System.out.println("Error"+e);
					hc.abortBatchLevelTransaction();
				}
			}
			i = i+1;
		}
		return true;
	}
	public ArrayList<String> getExpressionTypeList() {
		return experssionsTypeList;
	}
	public ArrayList<String> getExpressionList() {
		ArrayList<String> expressionList = new ArrayList<String>();
		expressionList.add("=");
		for (int i=0; i<expressionsResult.size();i++){
			expressionList.add(expressionsResult.get(i).getExpressionsName());
		}
		return expressionList;
	}
	
	

}
