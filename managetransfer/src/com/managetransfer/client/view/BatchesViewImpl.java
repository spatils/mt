package com.managetransfer.client.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.PieChart;
 
import com.managetransfer.client.BatchDetails;
import com.managetransfer.client.ExpressionsDetails;
import com.managetransfer.client.MappingDetails;
import com.managetransfer.client.SequenceDetailsMap;
 
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;
import com.google.gwt.view.client.AbstractDataProvider;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel.AbstractSelectionModel;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.TreeViewModel.NodeInfo;
import com.google.gwt.view.client.TreeViewModel.DefaultNodeInfo;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.RowHoverEvent;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class BatchesViewImpl extends Composite implements
		BatchesView<BatchDetails> {
	@UiTemplate("BatchesView.ui.xml")
	interface BatchesViewUiBinder extends UiBinder<Widget, BatchesViewImpl> {
	}
	final DialogBox dialogBox = new DialogBox() ;
	@UiField Button closeButton;
	private static BatchesViewUiBinder uiBinder = GWT
			.create(BatchesViewUiBinder.class);
	@UiField
	VerticalPanel batchViewPanel = new VerticalPanel();
	@UiField
	VerticalPanel batchesGridViewPanel;
	@UiField(provided = true)
	CellTable<BatchDetails> batchDetailsCellTable = new CellTable<BatchDetails>();
	@UiField SimplePager simplePager;
	@UiField VerticalPanel displayPieChart;
	@UiField HorizontalPanel horizontalPanel;
	@UiField Grid labelGrid;
	@UiField  Label lblBatchSize;
	@UiField  Label lblThreadSize;
	@UiField  Label lblPhaseType;
	@UiField  Label lblMachineName;
	  
	private static final String COUNT = "Count";;
	private static final String SUCCESS_COUNT = "Sucess Count";;
	private static final String ERROR_COUNT = "Error Count";;
	private static final String VALUE = "Value";;
	
	private Presenter<BatchDetails> presenter;

	public BatchesViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		batchViewPanel.setVisible(false);
	}

	@Override
	public void setPresenter(
			com.managetransfer.client.view.BatchesView.Presenter<BatchDetails> presenter) {
		this.presenter = presenter;
		batchDetailsCellTable
				.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		final SingleSelectionModel<BatchDetails> selectionModel = new SingleSelectionModel<BatchDetails>();
		batchDetailsCellTable.setSelectionModel(selectionModel);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					@Override
					public void onSelectionChange(SelectionChangeEvent event) {
						BatchDetails batchDetails = selectionModel
								.getSelectedObject();
						showBatchDetailsAsPieChart(batchDetails);
						
					}
				});
	}

	private DataTable createDataTable(BatchDetails batchDetails) {
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, COUNT);
		dataTable.addColumn(ColumnType.NUMBER, VALUE);
		dataTable.addRows(2);
		dataTable.setValue(0, 0, SUCCESS_COUNT);
		dataTable.setValue(0, 1, batchDetails.getSuccessCount());
		dataTable.setValue(1, 0, ERROR_COUNT);
		dataTable.setValue(1, 1, batchDetails.getFailureCount());
		return dataTable;
	}

	private void showBatchDetailsAsPieChart(final  BatchDetails batchDetails) {
		
		batchViewPanel.setVisible(true); 
		closeButton.setVisible(true);
		VisualizationUtils.loadVisualizationApi(new Runnable() {
			@Override
			public void run() {
				PieChart pieChart = new PieChart();
				displayPieChart.add(pieChart);
				pieChart.draw(createDataTable(batchDetails),
						getPieChartOptions(batchDetails.getBatchName()));
			}
		}, PieChart.PACKAGE);
		batchViewPanel.add(displayPieChart);
		batchViewPanel.add(labelGrid);
		batchViewPanel.add(horizontalPanel);
		batchViewPanel.add(closeButton);
		dialogBox.clear();
		dialogBox.setText("Batch Details");
		dialogBox.add(batchViewPanel);
		dialogBox.show();
		dialogBox.showRelativeTo(simplePager);
		presenter.displayBatchDetails(batchDetails.getSequenceName(),batchDetails.getSequenceNumber());
		
	}

	private PieChart.Options getPieChartOptions(String batchName) {
		PieChart.Options options = PieChart.Options.create();
		options.setBackgroundColor("#f0f0f0");
		// options.setColors(colors);
		options.setWidth(350);
		options.setHeight(250);
		options.set3D(true);
		options.setTitle(batchName);
		return options;
	}

	@Override
	public void setRowData(List<BatchDetails> rowData) {
		if (batchDetailsCellTable.getColumnCount() == 0) {
			batchDetailsCellTable.addColumn(batchName, "Batch Name");
			batchDetailsCellTable.addColumn(successCount, "Success Count");
			batchDetailsCellTable.addColumn(failureCount, "Failure Count");
			batchDetailsCellTable.addColumn(modifyTime, "Modify Time");
			batchDetailsCellTable.addColumn(startTime, "Start Time");
			batchDetailsCellTable.addColumn(batchStatus, "Batch Status");
			batchDetailsCellTable.addColumn(errorDescription,
					"Error Description");
		}
		ListDataProvider<BatchDetails> dataProvider = new ListDataProvider<BatchDetails>();
		dataProvider.addDataDisplay(batchDetailsCellTable);
		List<BatchDetails> dataList = dataProvider.getList();
		dataList.clear();
		if (rowData != null && !rowData.isEmpty()) {
			for (BatchDetails bd : rowData) {
				dataList.add(bd);
			}
		}
		 
		simplePager.setDisplay(batchDetailsCellTable); 
		simplePager.setPageSize(10);
		 
	}

	TextColumn<BatchDetails> batchName = new TextColumn<BatchDetails>() {
		public String getValue(BatchDetails bt) {
			return bt.getBatchName();
		}
	};
	TextColumn<BatchDetails> successCount = new TextColumn<BatchDetails>() {
		public String getValue(BatchDetails bt) {
			return bt.getSuccessCount() + "";
		}
	};
	TextColumn<BatchDetails> failureCount = new TextColumn<BatchDetails>() {
		public String getValue(BatchDetails bt) {
			return bt.getFailureCount() + "";
		}
	};
	TextColumn<BatchDetails> modifyTime = new TextColumn<BatchDetails>() {
		public String getValue(BatchDetails bt) {
			return bt.getModifyTime() + "";
		}
	};

	TextColumn<BatchDetails> startTime = new TextColumn<BatchDetails>() {
		public String getValue(BatchDetails bt) {
			return bt.getStartTime() + "";
		}
	};

	TextColumn<BatchDetails> batchStatus = new TextColumn<BatchDetails>() {
		public String getValue(BatchDetails bt) {
			return bt.getBatchStatus();
		}
	};
	TextColumn<BatchDetails> errorDescription = new TextColumn<BatchDetails>() {
		public String getValue(BatchDetails bt) {
			return bt.getErrorDescription();
		}
	};
    
    
    
	@UiHandler("closeButton")
	void onCloseButtonClick(ClickEvent event) {
		batchViewPanel.clear();
		batchViewPanel.remove(closeButton);
		horizontalPanel.clear();
		displayPieChart.clear();
		batchViewPanel.setVisible(false);
		dialogBox.hide();
	}

	@Override
	public void displayBatchDetails(SequenceDetailsMap seqDetailsMap) {
		lblBatchSize.setText(""+seqDetailsMap.getBatchSize());
		lblThreadSize.setText(""+seqDetailsMap.getThreadCount());
		lblPhaseType.setText(""+seqDetailsMap.getPhaseName());
		lblMachineName.setText(""+seqDetailsMap.getMachineName());
	}
	
	
}
