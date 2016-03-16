package com.managetransfer.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.managetransfer.client.common.SelectionModel;
import com.managetransfer.client.event.LoginEvent;
import com.managetransfer.client.event.LoginSuccessEvent;
import com.managetransfer.client.ManageTransferServiceAsync;
import com.managetransfer.client.UserDetails;
import com.managetransfer.client.view.LoginView;
 

public class LoginPresenter implements Presenter, LoginView.Presenter<UserDetails> {

	private UserDetails userDetails;
	private final ManageTransferServiceAsync rpcService;
	private final EventBus eventBus;
	private final LoginView<UserDetails> display;
	private final SelectionModel<UserDetails> selectionModel;

	public LoginPresenter(ManageTransferServiceAsync rpcService, EventBus eventBus, LoginView<UserDetails> display) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.userDetails = new UserDetails();
		this.display = display;
		this.selectionModel = new SelectionModel<UserDetails>();
		display.setPresenter(this);
	}
	@Override
	public void go(final HasWidgets container) {
		container.clear();
		/*Rendering login screen in the middle of the page */
		VerticalPanel vp = new VerticalPanel();
		vp.setPixelSize(Window.getClientWidth(), Window.getClientHeight());
		vp.setHorizontalAlignment(	HasHorizontalAlignment.ALIGN_CENTER);
		vp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		vp.add(display.asWidget()); 
		container.add(vp);
	}
	@Override
	public void onLoginButtonClicked() {
		 
		userDetails.setUserId(display.getUserName().getValue() );
		userDetails.setPsw(display.getUserPsw().getValue() );
		rpcService.userAuthentication(userDetails, new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean isAuthenticated) {
				eventBus.fireEvent(new LoginSuccessEvent(userDetails));
			}

			@Override
			public void onFailure(Throwable caught) {
				 
					Window.alert("Login Failed : Error From Server"+caught.getMessage());
				 
			}
		});
		
	}
		 
}
