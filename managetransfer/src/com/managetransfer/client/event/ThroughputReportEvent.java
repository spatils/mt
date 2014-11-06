package com.managetransfer.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ThroughputReportEvent extends GwtEvent<ThroughputReportEventHandler> {
  public static Type<ThroughputReportEventHandler> TYPE = new Type<ThroughputReportEventHandler>();
  
  @Override
  public Type<ThroughputReportEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ThroughputReportEventHandler handler) {
    handler.onThroughputReport(this);
  }
}
