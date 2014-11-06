package com.managetransfer.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class SequenceEvent extends GwtEvent<SequenceEventHandler> {
  public static Type<SequenceEventHandler> TYPE = new Type<SequenceEventHandler>();
  
  @Override
  public Type<SequenceEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(SequenceEventHandler handler) {
    handler.onSequence(this);
  }
}
