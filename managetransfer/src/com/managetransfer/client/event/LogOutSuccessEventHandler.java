package com.managetransfer.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface LogOutSuccessEventHandler extends EventHandler {
  void onLogOutSuccess(LogOutSuccessEvent logOutSuccessEvent);
}
