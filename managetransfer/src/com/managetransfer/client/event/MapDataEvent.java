package com.managetransfer.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class MapDataEvent extends GwtEvent<MapDataEventHandler> {
  public static Type<MapDataEventHandler> TYPE = new Type<MapDataEventHandler>();
  
  @Override
  public Type<MapDataEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(MapDataEventHandler handler) {
    handler.onMapData(this);
  }
}
