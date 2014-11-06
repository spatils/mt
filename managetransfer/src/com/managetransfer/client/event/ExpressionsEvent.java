package com.managetransfer.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ExpressionsEvent extends GwtEvent<ExpressionsEventHandler> {
  public static Type<ExpressionsEventHandler> TYPE = new Type<ExpressionsEventHandler>();
  
  @Override
  public Type<ExpressionsEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ExpressionsEventHandler handler) {
    handler.onExpressions(this);
  }
}
