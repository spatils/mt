package com.managetransfer.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class BatchReportEvent extends GwtEvent<BatchReportEventHandler> {
  public static Type<BatchReportEventHandler> TYPE = new Type<BatchReportEventHandler>();
  
  @Override
  public Type<BatchReportEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(BatchReportEventHandler handler) {
    handler.onBatchReport(this);
  }
}
