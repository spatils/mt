package com.managetransfer.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class LogReportEvent extends GwtEvent<LogReportEventHandler> {
  public static Type<LogReportEventHandler> TYPE = new Type<LogReportEventHandler>();
  
  @Override
  public Type<LogReportEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(LogReportEventHandler handler) {
    handler.onLogReport(this);
  }
}
