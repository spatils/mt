package com.managetransfer.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface ThroughputReportEventHandler extends EventHandler {
  void onThroughputReport(ThroughputReportEvent event);
}
