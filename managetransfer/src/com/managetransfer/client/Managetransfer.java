package com.managetransfer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Managetransfer implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final ManageTransferServiceAsync greetingService = GWT
			.create(ManageTransferService.class);

	/**
	 * This is the entry point method.
	 */
	 
	public void onModuleLoad() {
		ManageTransferServiceAsync rpcService = GWT.create(ManageTransferService.class);
		SimpleEventBus eventBus = new SimpleEventBus();
		AppController appViewer = new AppController(rpcService, eventBus);
	    RootPanel rootPanel = RootPanel.get();
	    rootPanel.setStyleName("whiteBackground");
	    appViewer.go(rootPanel);
	 }

	public static String getServerError() {
		return SERVER_ERROR;
	}

	public ManageTransferServiceAsync getGreetingService() {
		return greetingService;
	}
}
 
