package com.managetransfer.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.managetransfer.client.UserDetails;

public class LoginSuccessEvent extends GwtEvent<LoginSuccessEventHandler> {
	UserDetails userDetails ;
  public static com.google.gwt.event.shared.GwtEvent.Type<LoginSuccessEventHandler> TYPE = new Type<LoginSuccessEventHandler>();
  
  public LoginSuccessEvent(UserDetails result) {
	// TODO Auto-generated constructor stub
	  userDetails = result;
}

@Override
  public Type<LoginSuccessEventHandler> getAssociatedType() {
    return TYPE;
  }


@Override
protected void dispatch(LoginSuccessEventHandler handler) {
	// TODO Auto-generated method stub
	handler.onLoginSuccess(this);
}
}
