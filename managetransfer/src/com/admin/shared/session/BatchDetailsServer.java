package com.admin.shared.session;

import java.util.List;

 
import com.managetransfer.client.BatchDetails;
import com.managetransfer.hibernate.HibernateConnection;
 

public class BatchDetailsServer {
	private HibernateConnection hc;

	@SuppressWarnings("unchecked")
	public List<BatchDetails> getBatchDetails() {
		return hc.getObject("from BatchDetails  order by modifyTime DESC");
	}

	public void setHc(HibernateConnection hc) {
		this.hc = hc;
	}

	@SuppressWarnings("unchecked")
	public List<BatchDetails> getBatchDetailsBySequence(String sequenceName,
			int sequenceNumber) {
		return hc.getObject("from BatchDetails where sequenceNumber="+sequenceNumber+" and sequenceName='"+sequenceName+"' order by modifyTime desc ");
		 
	}

}
