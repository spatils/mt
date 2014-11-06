package com.managetransfer.client;

import java.io.Serializable;
import java.util.Date;

public class LogsDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String errorType;
	private int numberOfErrors;
	
	private String mtSequenceName;
	private int mtSequenceNumber;
    private Date mtCreateDate;
    private Date mtModifyDate;
    private String mtErrorDesc;
    private String mtStatus;
    private int mtProcessId;
    private String mtRepositoryPath;
	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public int getNumberOfErrors() {
		return numberOfErrors;
	}

	public void setNumberOfErrors(int numberOfErrors) {
		this.numberOfErrors = numberOfErrors;
	}
	public int getMtSequenceNumber() {
		return mtSequenceNumber;
	}

	public void setMtSequenceNumber(int mtSequenceNumber) {
		this.mtSequenceNumber = mtSequenceNumber;
	}

	public Date getMtCreateDate() {
		return mtCreateDate;
	}

	public void setMtCreateDate(Date mtCreateDate) {
		this.mtCreateDate = mtCreateDate;
	}

	public Date getMtModifyDate() {
		return mtModifyDate;
	}

	public void setMtModifyDate(Date mtModifyDate) {
		this.mtModifyDate = mtModifyDate;
	}

	public String getMtErrorDesc() {
		return mtErrorDesc;
	}

	public void setMtErrorDesc(String mtErrorDesc) {
		this.mtErrorDesc = mtErrorDesc;
	}

	public String getMtStatus() {
		return mtStatus;
	}

	public void setMtStatus(String mtStatus) {
		this.mtStatus = mtStatus;
	}

	public int getMtProcessId() {
		return mtProcessId;
	}

	public void setMtProcessId(int mtProcessId) {
		this.mtProcessId = mtProcessId;
	}

	public String getMtRepositoryPath() {
		return mtRepositoryPath;
	}

	public void setMtRepositoryPath(String mtRepositoryPath) {
		this.mtRepositoryPath = mtRepositoryPath;
	}
 

	public String getMtSequenceName() {
		return mtSequenceName;
	}

	public void setMtSequenceName(String mtSequenceName) {
		this.mtSequenceName = mtSequenceName;
	}
}
