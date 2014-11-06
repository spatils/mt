package com.managetransfer.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SchedularEvent extends GwtEvent<SchedularEventHandler> {
  public static Type<SchedularEventHandler> TYPE = new Type<SchedularEventHandler>();
  
  @Override
  public Type<SchedularEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(SchedularEventHandler handler) {
    handler.onSchedular(this);
  }
}
