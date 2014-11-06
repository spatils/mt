package com.admin.shared.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.managetransfer.client.SequenceDetails;
import com.managetransfer.client.SequenceDetailsH;
import com.managetransfer.client.SequenceDetailsMap;
import com.managetransfer.client.SequenceDetailsMapH;
import com.managetransfer.hibernate.HibernateConnection;

public class ThroughputDetailsServer {

	private HibernateConnection hc;
	private SequencesDetailsServer seqDetailsServer = new SequencesDetailsServer();

	public List<SequenceDetails> getSequenceDetails() {
		seqDetailsServer.setHc(hc);
		List<SequenceDetailsH> seqDetails = seqDetailsServer.getSequencesList();
		List<SequenceDetails> seqDetailsList = null;
		if (seqDetails != null && !seqDetails.isEmpty()) {
			seqDetailsList = new ArrayList<SequenceDetails>();
			for (SequenceDetailsH seqDt : seqDetails) {
				SequenceDetails sq = new SequenceDetails();
				sq.setNumberOfPhases(seqDt.getNumberOfPhases());
				sq.setSequenceName(seqDt.getSequenceName());
				//sq.setSequenceDetails(seqDt.getSequenceDetailsMap());
				
				Map map =  seqDt.getSequenceDetailsMap();
				
				seqDetailsList.add(sq);
			}
		}
		return seqDetailsList;
	}

	public List<SequenceDetailsMap> getSequenceDetailsMapList(String seqName) {
		seqDetailsServer.setHc(hc);
		List<SequenceDetailsMapH> seqDetailsMap = seqDetailsServer
				.getSequenceDetailsMapList(seqName);
		List<SequenceDetailsMap> seqDetailsMapList = null;
		if (seqDetailsMap != null && !seqDetailsMap.isEmpty()) {
			seqDetailsMapList = new ArrayList<SequenceDetailsMap>();
			for (SequenceDetailsMapH seqDtm : seqDetailsMap) {
				SequenceDetailsMap sqm = new SequenceDetailsMap();
				sqm.setCronFrequency(seqDtm.getCronFrequency());
				sqm.setMachineName(seqDtm.getMachineName());
				sqm.setPhaseName(seqDtm.getPhaseName());
				sqm.setSequenceNumber(seqDtm.getSequenceNumber());
				sqm.setThreadCount(seqDtm.getThreadCount());
				sqm.setSequenceName(seqDtm.getSequenceName());
				seqDetailsMapList.add(sqm);
			}
		}
		return seqDetailsMapList;
	}

	private SequenceDetailsMap mapSequenceDetailsMap(SequenceDetailsMapH seqDtm) {
		SequenceDetailsMap sqm = null;
		if (seqDtm != null) {
			sqm = new SequenceDetailsMap();
			sqm.setCronFrequency(seqDtm.getCronFrequency());
			sqm.setMachineName(seqDtm.getMachineName());
			sqm.setPhaseName(seqDtm.getPhaseName());
			sqm.setSequenceNumber(seqDtm.getSequenceNumber());
			sqm.setThreadCount(seqDtm.getThreadCount());
			sqm.setSequenceName(seqDtm.getSequenceName());
		}
		return sqm;
	}

	public void setHc(HibernateConnection hc) {
		this.hc = hc;
	}

}
