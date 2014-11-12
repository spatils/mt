package com.managetransfer.hibernate;

import java.util.Date;

import com.managetransfer.businessobject.Claims;

public class Test {
	public static void main(String argc[]){
		Test test = new Test();
		test.testTransaction();
	}
	
	public void testTransaction(){
		HibernateConnection hc = new HibernateConnection();
		Claims obj = new Claims();
		hc.initOperation();
		Date tody = new Date();
		int proccessCount = 0;
		for (int i = 0 ; i < 100 ; i ++ ){
			try{
				if(proccessCount == 10){
					proccessCount = 0;
					hc.commitBatchLevelTransaction();
					System.out.println("Commit");
				}
				hc.startBatchLevelTransaction();
				proccessCount = proccessCount + 1;
				obj = new Claims();
				System.out.println("Started processing record----------------"+i);
				if(i !=5){
					obj.setObjectId(""+i);
				}
				obj.setMtSequenceNumber(1);
				obj.setMtErrorDesc("");
				obj.setMtProcessId(1);
				obj.setMtSequenceName("");
				obj.setMtCreateDate(tody);
				obj.setMtModifyDate(new Date());
				hc.saveOperation(obj);
				
				
			}catch(Exception e){
				System.out.println("Error Processing"+ e);
				try{
				obj.setObjectId(""+i);
				obj.setMtSequenceNumber(1);
				obj.setMtErrorDesc("");
				obj.setMtProcessId(1);
				obj.setMtSequenceName("");
				obj.setMtCreateDate(tody);
				obj.setMtModifyDate(new Date());
				hc.saveOperation(obj);
				}catch(Exception ex) {
					System.out.println("Error In Error"+ ex);
				}
			}
			hc.commitBatchLevelTransaction();
		
		}
	}
}
