package com.managetransfer.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ImportDefinitionEvent extends GwtEvent<ImportDefinitionEventHandler> {
  public static Type<ImportDefinitionEventHandler> TYPE = new Type<ImportDefinitionEventHandler>();
  
  @Override
  public Type<ImportDefinitionEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ImportDefinitionEventHandler handler) {
    handler.onImportDefinition(this);
  }
}
