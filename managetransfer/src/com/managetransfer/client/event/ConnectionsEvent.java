package com.managetransfer.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ConnectionsEvent extends GwtEvent<ConnectionsEventHandler> {
  public static Type<ConnectionsEventHandler> TYPE = new Type<ConnectionsEventHandler>();
  
  @Override
  public Type<ConnectionsEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ConnectionsEventHandler handler) {
    handler.onConnections(this);
  }
}
