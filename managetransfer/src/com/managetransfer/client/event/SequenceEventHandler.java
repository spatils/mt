package com.managetransfer.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface SequenceEventHandler extends EventHandler {
  void onSequence(SequenceEvent event);
}
