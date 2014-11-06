package com.managetransfer.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ConnectionsEventHandler extends EventHandler {
  void onConnections(ConnectionsEvent event);
}
