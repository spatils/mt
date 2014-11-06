package com.managetransfer.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class PhasesEvent extends GwtEvent<PhasesEventHandler> {
  public static Type<PhasesEventHandler> TYPE = new Type<PhasesEventHandler>();
  
  @Override
  public Type<PhasesEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(PhasesEventHandler handler) {
    handler.onPhases(this);
  }
}
