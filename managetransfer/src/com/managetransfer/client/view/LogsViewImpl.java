package com.managetransfer.client.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.ColumnChart;
import com.google.gwt.visualization.client.visualizations.PieChart;
import com.managetransfer.client.LogsDetails;

public class LogsViewImpl extends Composite implements LogsView<LogsDetails> {

	private static LogsViewUiBinder uiBinder = GWT
			.create(LogsViewUiBinder.class);

	private Presenter<LogsDetails> presenter;

	private static final String ERROR_TYPE = "Error Type";
	private static final String NUMBER_OF_ERRORS = "Number of Errors";;

	@UiTemplate("LogsView.ui.xml")
	interface LogsViewUiBinder extends UiBinder<Widget, LogsViewImpl> {
	}

	public LogsViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));

	}

	@UiField
	VerticalPanel logsViewPanel = new VerticalPanel();
	@UiField
	ListBox objectTypeComboBox = new ListBox();
	@UiField
	ListBox attributesComboBox = new ListBox();
	@UiField
	TextBox attributeValueTextBox = new TextBox();
	@UiField
	Button searchButton = new Button();
	@UiField
	TabLayoutPanel logsViewTabLayoutPanel;
	@UiField(provided = true)
	CellTable<LogsDetails> logsDetailsCellTable = new CellTable<LogsDetails>();
	@UiField
	VerticalPanel columnChartPanel;
	@UiField
	VerticalPanel searchResultPanel;
	@UiField(provided = true)
	CellTable<LogsDetails> searchResultCellTable = new CellTable<LogsDetails>();

	@Override
	public void showLogsDetailsAsColumnChart(final List<LogsDetails> logsDetails) {

		VisualizationUtils.loadVisualizationApi(new Runnable() {
			@Override
			public void run() {
				ColumnChart columnChart = new ColumnChart();
				columnChartPanel.clear();
				columnChartPanel.add(columnChart);
				columnChart.draw(createDataTable(logsDetails),
						getColumnChartOptions());

			}
		}, PieChart.PACKAGE);

	}

	private DataTable createDataTable(List<LogsDetails> logsDetailsList) {
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, ERROR_TYPE);
		dataTable.addColumn(ColumnType.NUMBER, NUMBER_OF_ERRORS);

		if (logsDetailsList != null && !logsDetailsList.isEmpty()) {

			dataTable.addRows(logsDetailsList.size());

			int i = 0;
			for (LogsDetails logsDetails : logsDetailsList) {
				dataTable.setValue(i, 0, logsDetails.getErrorType());
				dataTable.setValue(i, 1, logsDetails.getNumberOfErrors());
				i++;
			}
		}

		return dataTable;

	}

	private ColumnChart.Options getColumnChartOptions() {
		ColumnChart.Options options = ColumnChart.Options.create();
		options.setBackgroundColor("#f0f0f0");

		// options.setColors(colors);
		options.setWidth(400);
		options.setHeight(300);
		options.set3D(true);
		options.setTitle("Logs Information");
		return options;
	}

	public void loadObjectTypeDropDownBox() {
		objectTypeComboBox.clear();
		List<String> objectTypeList = presenter.getObjectTypeList();
		if (objectTypeList != null && !objectTypeList.isEmpty()) {
			for (String objectType : objectTypeList) {
				objectTypeComboBox.addItem(objectType);
			}
		}
	}

	@UiHandler("objectTypeComboBox")
	void onObjectTypeComboBoxChange(ChangeEvent event) {
		attributesComboBox.clear();
		if (presenter != null) {
			String selectedValue = objectTypeComboBox
					.getValue(objectTypeComboBox.getSelectedIndex());
			if (!"--select--".equals(selectedValue)) {
				presenter.onObjectTypeComboBoxChange(selectedValue);
			}
		}
	}

	@UiHandler("searchButton")
	void onSearchButtonClick(ClickEvent event) {
		String objectType = objectTypeComboBox.getValue(objectTypeComboBox
				.getSelectedIndex());

		String attribute = attributesComboBox.getValue(attributesComboBox
				.getSelectedIndex());

		String value = attributeValueTextBox.getValue();

		if (presenter != null) {
			presenter.onSearchButtonClicked(objectType, attribute, value);
		}
	}

	@Override
	public void setPresenter(
			com.managetransfer.client.view.LogsView.Presenter<LogsDetails> presenter) {
		this.presenter = presenter;
		logsDetailsCellTable
				.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		final SingleSelectionModel<LogsDetails> selectionModel = new SingleSelectionModel<LogsDetails>();
		logsDetailsCellTable.setSelectionModel(selectionModel);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					@Override
					public void onSelectionChange(SelectionChangeEvent event) {
						LogsDetails logsDetails = selectionModel
								.getSelectedObject();
						// Action can be added
					}
				});
	}

	@Override
	public void loadAttributesDropDownBox() {
		attributesComboBox.clear();
		List<String> attributesList = presenter.getAttributesList();
		if (attributesList != null && !attributesList.isEmpty()) {
			for (String attribute : attributesList) {
				attributesComboBox.addItem(attribute);
			}
		}
	}

	@Override
	public void setRowData(List<LogsDetails> rowData, boolean isSearchGrid) {
		ListDataProvider<LogsDetails> dataProvider = new ListDataProvider<LogsDetails>();
		if (isSearchGrid) {
			if (searchResultCellTable.getColumnCount() == 0) {
				searchResultCellTable.addColumn(sequenceName, "Sequence Name");
				searchResultCellTable.addColumn(sequenceNumber, "Sequence Number"); 
				searchResultCellTable.addColumn(processId, "Process Id");
				searchResultCellTable.addColumn(recordStatus, "Records Status");
				searchResultCellTable.addColumn(createDate, "Create Date");
				searchResultCellTable.addColumn(modifyDate, "Modify Date");
				searchResultCellTable.addColumn(errorDescription, "Error");
			}
			dataProvider.addDataDisplay(searchResultCellTable);
		} else {
			if (logsDetailsCellTable.getColumnCount() == 0) {
				logsDetailsCellTable.addColumn(errorType, "Error Type");
				logsDetailsCellTable.addColumn(numberOfErrors,
						"Number Of Errors");
			}
			dataProvider.addDataDisplay(logsDetailsCellTable);
		}

		List<LogsDetails> dataList = dataProvider.getList();
		dataList.clear();
		if (rowData != null && !rowData.isEmpty()) {
			for (LogsDetails ld : rowData) {
				dataList.add(ld);
			}
		}
	}
	TextColumn<LogsDetails> numberOfErrors = new TextColumn<LogsDetails>() {
		public String getValue(LogsDetails ld) {
			return ld.getNumberOfErrors()+"";
		}
	};
	 
	TextColumn<LogsDetails> errorType = new TextColumn<LogsDetails>() {
		public String getValue(LogsDetails ld) {
			return ld.getErrorType();
		}
	};
	TextColumn<LogsDetails> sequenceName = new TextColumn<LogsDetails>() {
		public String getValue(LogsDetails ld) {
			return ld.getMtSequenceName();
		}
	};
	TextColumn<LogsDetails> sequenceNumber = new TextColumn<LogsDetails>() {
		public String getValue(LogsDetails ld) {
			return ld.getMtSequenceNumber() + "";
		}
	};
	TextColumn<LogsDetails> processId = new TextColumn<LogsDetails>() {
		public String getValue(LogsDetails ld) {
			return ld.getMtProcessId() + "";
		}
	};
	TextColumn<LogsDetails> recordStatus = new TextColumn<LogsDetails>() {
		public String getValue(LogsDetails ld) {
			return ld.getMtStatus() + "";
		}
	};
	TextColumn<LogsDetails> createDate = new TextColumn<LogsDetails>() {
		public String getValue(LogsDetails ld) {
			return ld.getMtCreateDate() + "";
		}
	};
	TextColumn<LogsDetails> modifyDate = new TextColumn<LogsDetails>() {
		public String getValue(LogsDetails ld) {
			return ld.getMtModifyDate() + "";
		}
	};
	TextColumn<LogsDetails> errorDescription = new TextColumn<LogsDetails>() {
		public String getValue(LogsDetails ld) {
			return ld.getMtErrorDesc() + "";
		}
	};
}
