package com.managetransfer.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.managetransfer.client.event.*;
import com.managetransfer.client.presenter.BatchPresenter;
import com.managetransfer.client.presenter.HomePagePresenter;
import com.managetransfer.client.presenter.ConnectionsPresenter;
import com.managetransfer.client.presenter.LoginPresenter;
import com.managetransfer.client.presenter.LogsPresenter;
import com.managetransfer.client.presenter.MappingPresenter;
import com.managetransfer.client.presenter.PhasesPresenter;
import com.managetransfer.client.presenter.Presenter;
import com.managetransfer.client.presenter.SchedularPresenter;
import com.managetransfer.client.presenter.SequencesPresenter;
import com.managetransfer.client.presenter.ExpressionsPresenter;
import com.managetransfer.client.presenter.ThroughputPresenter;
import com.managetransfer.client.view.BatchesViewImpl;
import com.managetransfer.client.view.ConnectionsViewImpl;
import com.managetransfer.client.view.ExpressionsViewImpl;
import com.managetransfer.client.view.HomePageViewImpl;
import com.managetransfer.client.view.LoginViewImpl;
import com.managetransfer.client.view.LogsViewImpl;
import com.managetransfer.client.view.MappingViewImpl;
import com.managetransfer.client.view.PhasesViewImpl;
import com.managetransfer.client.view.SchedularViewImpl;
import com.managetransfer.client.view.SequencesViewImpl;
import com.managetransfer.client.view.ThroughputViewImpl;
 

public class AppController implements Presenter, ValueChangeHandler<String> {
	private final EventBus eventBus;
	private final ManageTransferServiceAsync rpcService;
	private HasWidgets container;
	private LoginViewImpl loginView = null;
	private HomePageViewImpl homePageView = null;
	private ConnectionsViewImpl connectionsView = null; 
	private SequencesViewImpl sequencesView= null;
	private ExpressionsViewImpl expressionsView = null;
	private SchedularViewImpl schedularView = null;
	private PhasesViewImpl phasesView = null;
	private MappingViewImpl mappingView = null;
	private BatchesViewImpl batchView;
	private ThroughputViewImpl throughputView;
	private LogsViewImpl logsView;
	public AppController(ManageTransferServiceAsync rpcService,
			EventBus eventBus) {
		this.eventBus = eventBus;
		this.rpcService = rpcService;
		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);

		eventBus.addHandler(LoginEvent.TYPE,
				new LoginEventHandler() {
					@Override
					public void onLogin(LoginEvent event) {
						doLogin();
					}
				});
		 
		eventBus.addHandler(LoginSuccessEvent.TYPE,
				new LoginSuccessEventHandler() {
					@Override
					public void onLoginSuccess(LoginSuccessEvent event) {
						doLoginSuccess();
					}
				});
		eventBus.addHandler(ConnectionsEvent.TYPE,
				new ConnectionsEventHandler() {
					@Override
					public void onConnections(ConnectionsEvent event) {
						doConnections();
					}
				});
		eventBus.addHandler(ExpressionsEvent.TYPE,
				new ExpressionsEventHandler() {
					@Override
					public void onExpressions(ExpressionsEvent event) {
						doExpressions();
					}
				});
		eventBus.addHandler(SchedularEvent.TYPE,
				new SchedularEventHandler() {
					@Override
					public void onSchedular(SchedularEvent event) {
						doSchedularEvent();
					}
				});
		eventBus.addHandler(SequenceEvent.TYPE,
				new SequenceEventHandler() {
					@Override
					public void onSequence(SequenceEvent event) {
						doSequenceEvent();
					}
				});
		eventBus.addHandler(ReportsEvent.TYPE,
				new ReportsEventHandler() {
					@Override
					public void onReports(ReportsEvent event) {
						doReportsEvent();
					}
				});
		eventBus.addHandler(ReportsEvent.TYPE,
				new ReportsEventHandler() {
					@Override
					public void onReports(ReportsEvent event) {
						doReportsEvent();
					}
				});
		eventBus.addHandler(PhasesEvent.TYPE,
				new PhasesEventHandler() {
					@Override
					public void onPhases(PhasesEvent event) {
						doPhasesEvent();
					}
				});
		eventBus.addHandler(ImportDefinitionEvent.TYPE,
				new ImportDefinitionEventHandler() {
					@Override
					public void onImportDefinition(ImportDefinitionEvent event) {
						doImportDefinitionEvent();
					}
				});
		eventBus.addHandler(MapDataEvent.TYPE,
				new MapDataEventHandler() {
					@Override
					public void onMapData(MapDataEvent event) {
						doMapDataEvent();
					}
				});
		eventBus.addHandler(LogReportEvent.TYPE,
				new LogReportEventHandler() {
					@Override
					public void onLogReport(LogReportEvent event) {
						doLogReportEvent();
					}
				});
		eventBus.addHandler(BatchReportEvent.TYPE,
				new BatchReportEventHandler() {
					@Override
					public void onBatchReport(BatchReportEvent event) {
						doBatchReportEvent();
					}
				});
		eventBus.addHandler(ThroughputReportEvent.TYPE,
				new ThroughputReportEventHandler() {
					@Override
					public void onThroughputReport(ThroughputReportEvent event) {
						doThroughputReportEvent();
					}
				});
	}

	protected void doLoginSuccess() {
		// TODO Auto-generated method stub
		History.newItem("loginSuccess");
	}

	private void doLogin() {
		History.newItem("login");
	}
	private void doConnections() {
		History.newItem("connections");
	}
	private void doExpressions() {
		History.newItem("expressions");
	}
	private void doSchedularEvent() {
		History.newItem("schedular");
	}
	private void doSequenceEvent() {
		History.newItem("sequence");
	}
	private void doReportsEvent() {
		History.newItem("reports");
	}
	private void doPhasesEvent() {
		History.newItem("phases");
	}
	private void doImportDefinitionEvent() {
		History.newItem("importdefinition");
	}
	private void doMapDataEvent() {
		History.newItem("mapdata");
	}
	private void doBatchReportEvent() {
		History.newItem("batchreport");
	}

	private void doLogReportEvent() {
		History.newItem("logreport");
	}

	private void doThroughputReportEvent() {
		History.newItem("throughputreport");
	}
	@Override
	public void go(final HasWidgets container) {
		this.container = container;
		 
		//If ticket is not passed or login is not successful then get login screen
		 rpcService.isAuthenticated( new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {
				// Show the RPC error message to the user
				Window.alert("Server Not Available");
			}

			public void onSuccess(Boolean srFromServer) {
				if(!srFromServer){
					 
					History.newItem("login");
				}else{
					if ("".equals(History.getToken())) {
						History.newItem("login");
					} else {
						History.fireCurrentHistoryState();
					}
				}
			}
		}); 
		
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		final String token = event.getValue();
       if (token != null) {
			if (token.equals("login")) {
				GWT.runAsync(new RunAsyncCallback() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					@Override
					public void onSuccess() {
						new LoginPresenter(rpcService, eventBus, getLoginView()).go(container)	;
					}
				});
			}
			if (token.equals("loginSuccess")) {
				GWT.runAsync(new RunAsyncCallback() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					@Override
					public void onSuccess() {
						new HomePagePresenter(rpcService, eventBus, getHomePageView()).go(container)	;
					}
				});
			}
			if (token.equals("connections")) {
				GWT.runAsync(new RunAsyncCallback() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					@Override
					public void onSuccess() {
						new ConnectionsPresenter(rpcService, eventBus, getConnectionsView()).go(container)	;
					}
				});
			}
			if (token.equals("sequence")) {
				GWT.runAsync(new RunAsyncCallback() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					@Override
					public void onSuccess() {
						new SequencesPresenter(rpcService, eventBus, getSequencesView()).go(container)	;
					}
				});
			}
			if (token.equals("expressions")) {
				GWT.runAsync(new RunAsyncCallback() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					@Override
					public void onSuccess() {
						new ExpressionsPresenter(rpcService, eventBus, getExpressionsView()).go(container)	;
					}
				});
			}
			if (token.equals("schedular")) {
				GWT.runAsync(new RunAsyncCallback() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					@Override
					public void onSuccess() {
						new SchedularPresenter(rpcService, eventBus, getSchedularView()).go(container)	;
					}
				});
			}
			if (token.equals("phases")) {
				GWT.runAsync(new RunAsyncCallback() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					@Override
					public void onSuccess() {
						new PhasesPresenter(rpcService, eventBus, getPhasesView()).go(container)	;
					}
				});
			}
			if (token.equals("mapdata")) {
				GWT.runAsync(new RunAsyncCallback() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					@Override
					public void onSuccess() {
						new MappingPresenter(rpcService, eventBus, getMappingView()).go(container)	;
					}
				});
			}
			if (token.equals("batchreport")) {
				GWT.runAsync(new RunAsyncCallback() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					@Override
					public void onSuccess() {
						new BatchPresenter(rpcService, eventBus, getBatchView())
								.go(container);
					}
				});
			}
			if (token.equals("logreport")) {
				GWT.runAsync(new RunAsyncCallback() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					@Override
					public void onSuccess() {
						new LogsPresenter(rpcService, eventBus, getLogsView())
								.go(container);
					}
				});
			}

			if (token.equals("throughputreport")) {
				GWT.runAsync(new RunAsyncCallback() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					@Override
					public void onSuccess() {
						new ThroughputPresenter(rpcService, eventBus,
								getThroughputView()).go(container);
					}
				});
			}

		}
		
	}

	private LoginViewImpl getLoginView() {
		if (loginView == null) {
			loginView = new LoginViewImpl();
		}
		return loginView;
	}
	private HomePageViewImpl getHomePageView() {
		if (homePageView == null) {
			homePageView = new HomePageViewImpl();
		}
		return homePageView;
	}
	private ConnectionsViewImpl getConnectionsView() {
		if (connectionsView == null) {
			connectionsView = new ConnectionsViewImpl();
		}
		return connectionsView;
	}

	private SequencesViewImpl getSequencesView() {
		if (sequencesView == null) {
			sequencesView = new SequencesViewImpl();
		}
		return sequencesView;
	}
	private ExpressionsViewImpl getExpressionsView() {
		if (expressionsView == null) {
			expressionsView = new ExpressionsViewImpl();
		}
		return expressionsView;
	}
	private SchedularViewImpl getSchedularView() {
		if (schedularView == null) {
			schedularView = new SchedularViewImpl();
		}
		return schedularView;
	}
	private PhasesViewImpl getPhasesView() {
		if (phasesView == null) {
			phasesView = new PhasesViewImpl();
		}
		return phasesView;
	}
	private MappingViewImpl getMappingView() {
		if (mappingView == null) {
			mappingView = new MappingViewImpl();
		}
		return mappingView;
	}
	 
	 
	private BatchesViewImpl getBatchView() {
		if (batchView == null) {
			batchView = new BatchesViewImpl();
		}
		return batchView;
	}

	private ThroughputViewImpl getThroughputView() {
		if (throughputView == null) {
			throughputView = new ThroughputViewImpl();
		}
		return throughputView;
	}

	private LogsViewImpl getLogsView() {
		if (logsView == null) {
			logsView = new LogsViewImpl();
		}
		return logsView;
	}
}
