package com.managetransfer.d7;

public class Test {
	public static void main(String[] arg) throws Exception{
		Test test = new Test();
		//test.testing();
		test.formatterTesting(); 
	}
	public void testing() throws Exception{
		DocumentumConnection dc = new  DocumentumConnection();
		XCPWorkflowMethods xcpWM = new XCPWorkflowMethods();
		
		dc.connectRepository();
		xcpWM.setDc(dc);
		xcpWM.InitiateWorkflow();
		
	}
	public void formatterTesting(){
		String sampleData = " This is %1$s %2$s %3$s %4$B";
		System.out.println(String.format(sampleData, "one","two","three",true));
	}
}    
