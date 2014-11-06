package com.managetransfer.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ReportsEvent extends GwtEvent<ReportsEventHandler> {
  public static Type<ReportsEventHandler> TYPE = new Type<ReportsEventHandler>();
  
  @Override
  public Type<ReportsEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ReportsEventHandler handler) {
    handler.onReports(this);
  }
}
