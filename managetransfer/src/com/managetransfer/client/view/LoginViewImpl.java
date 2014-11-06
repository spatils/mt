package com.managetransfer.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
 
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
 
 
 
import com.managetransfer.client.UserDetails;

public class LoginViewImpl extends Composite implements LoginView<UserDetails>{

	@UiTemplate("LoginView.ui.xml")
	interface LoginViewUiBinder extends UiBinder<Widget, LoginViewImpl> {
	} 
	
	private static LoginViewUiBinder uiBinder =	GWT.create(LoginViewUiBinder.class);
	@UiField TextBox  userName ;
	@UiField TextBox  usrPsw ;
	@UiField Button loginButton;
	
	public LoginViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	private Presenter<UserDetails> presenter;
	
	
	@Override
	public void setPresenter(Presenter<UserDetails> presenter) {
		// TODO Auto-generated method stub
		this.presenter = presenter;
	}
	 
	@Override
	public HasValue<String> getUserName() {
		// TODO Auto-generated method stub
		return userName;
	}
	@Override
	public HasValue<String> getUserPsw() {
		// TODO Auto-generated method stub
		return usrPsw;
	}
  
	@UiHandler("loginButton")
	void onLoginButtonClicked(@SuppressWarnings("unused") ClickEvent event) {
		if (presenter != null) {
			presenter.onLoginButtonClicked();
		}
	}
	  
}
