package com.managetransfer.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class LogOutEvent extends GwtEvent<LogOutEventHandler> {
  public static Type<LogOutEventHandler> TYPE = new Type<LogOutEventHandler>();
  
  @Override
  public Type<LogOutEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(LogOutEventHandler handler) {
    handler.onLogOut(this);
  }
}
