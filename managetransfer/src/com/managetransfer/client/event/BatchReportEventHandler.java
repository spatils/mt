package com.managetransfer.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface BatchReportEventHandler extends EventHandler {
  void onBatchReport(BatchReportEvent event);
}
