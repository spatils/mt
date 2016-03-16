package com.managetransfer.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.managetransfer.client.UserDetails;

public class LogOutSuccessEvent extends GwtEvent<LogOutSuccessEventHandler> {
	UserDetails userDetails ;
  public static com.google.gwt.event.shared.GwtEvent.Type<LogOutSuccessEventHandler> TYPE = new Type<LogOutSuccessEventHandler>();
  
  public LogOutSuccessEvent(UserDetails result) {
	// TODO Auto-generated constructor stub
	  userDetails = result;
}

@Override
  public Type<LogOutSuccessEventHandler> getAssociatedType() {
    return TYPE;
  }


@Override
protected void dispatch(LogOutSuccessEventHandler handler) {
	// TODO Auto-generated method stub
	handler.onLogOutSuccess(this);
}
}
