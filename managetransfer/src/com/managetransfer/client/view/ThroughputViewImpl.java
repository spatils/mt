package com.managetransfer.client.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.BarChart;
import com.google.gwt.visualization.client.visualizations.LineChart;
import com.managetransfer.client.SequenceDetails;
import com.managetransfer.client.SequenceDetailsMap;
import com.managetransfer.client.BatchDetails;

public class ThroughputViewImpl extends Composite implements
		ThroughputView<SequenceDetails> {

	private static ThroughputViewUiBinder uiBinder = GWT
			.create(ThroughputViewUiBinder.class);

	private static final String THREAD_COUNT = "Thread Count";
	private static final String IMPORT_RATE = "Import Count";;
	private static final String START_DATE = "Start Date";

	@UiTemplate("ThroughputView.ui.xml")
	interface ThroughputViewUiBinder extends
			UiBinder<Widget, ThroughputViewImpl> {
	}

	public ThroughputViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	VerticalPanel throughputViewPanel = new VerticalPanel();
	@UiField(provided = true)
	CellTable<SequenceDetails> sequenceViewCellTable = new CellTable<SequenceDetails>();
	@UiField(provided = true)
	CellTable<SequenceDetailsMap> sequenceDetailsViewCellTable = new CellTable<SequenceDetailsMap>();
	@UiField
	VerticalPanel sequenceThroughputDisplayPanel = new VerticalPanel();

	private Presenter<SequenceDetails> presenter;

	@Override
	public void setPresenter(final Presenter presenter) {
		this.presenter = presenter;
		sequenceViewCellTable
				.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		final SingleSelectionModel<SequenceDetails> selectionModel = new SingleSelectionModel<SequenceDetails>();
		sequenceViewCellTable.setSelectionModel(selectionModel);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					@Override
					public void onSelectionChange(SelectionChangeEvent event) {
						sequenceThroughputDisplayPanel.clear();
						SequenceDetails sequenceDetails = selectionModel
								.getSelectedObject();
						getSequenceDetailsMapList(sequenceDetails
								.getSequenceName());
					}
				});

		sequenceDetailsViewCellTable
				.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		final SingleSelectionModel<SequenceDetailsMap> selectionModelSequenceDetails = new SingleSelectionModel<SequenceDetailsMap>();
		sequenceDetailsViewCellTable
				.setSelectionModel(selectionModelSequenceDetails);
		selectionModelSequenceDetails
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					@Override
					public void onSelectionChange(SelectionChangeEvent event) {
						SequenceDetailsMap seqDetailsMap = selectionModelSequenceDetails
								.getSelectedObject();
						presenter.getBatchDetails(seqDetailsMap.getSequenceName(),seqDetailsMap.getSequenceNumber());
					}
				});

	}

	private void getSequenceDetailsMapList(String seqName) {
		presenter.getSequenceDetailsMapList(seqName);
	}

	@Override
	public void showSequenceDetailsAsBarChart(
			final List<BatchDetails> batchDetails) {
		VisualizationUtils.loadVisualizationApi(new Runnable() {
			@Override
			public void run() {
				LineChart lineChart = new LineChart();
				sequenceThroughputDisplayPanel.clear();
				sequenceThroughputDisplayPanel.add(lineChart);
				lineChart.draw(createDataTable(batchDetails),
						getLineChartOptions());

			}
		}, BarChart.PACKAGE);
	}

	private DataTable createDataTable(List<BatchDetails> batchDetails) {
		DataTable dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.DATE, START_DATE);
		dataTable.addColumn(ColumnType.NUMBER, THREAD_COUNT);
		dataTable.addColumn(ColumnType.NUMBER, IMPORT_RATE);
		for(int i=0 ; i <batchDetails.size();i++){
			dataTable.addRows(1);
			dataTable.setValue(i, 0, batchDetails.get(i).getStartTime());
			dataTable.setValue(i, 1, batchDetails.get(i).getSuccessCount()+batchDetails.get(i).getFailureCount());
			dataTable.setValue(i, 2, 20);
		}
		return dataTable;
	}

	private LineChart.Options getLineChartOptions() {
		LineChart.Options options = LineChart.Options.create();
		options.setBackgroundColor("#f0f0f0");
		options.setWidth(400);
		options.setHeight(240);
		options.setTitle("Throughput Information");
		return options;
	}

	@Override
	public void setRowData(List<SequenceDetails> rowData) {

		ListDataProvider<SequenceDetails> dataProvider = new ListDataProvider<SequenceDetails>();

		if (sequenceViewCellTable.getColumnCount() == 0) {
			sequenceViewCellTable.addColumn(sequenceName, "Sequence Name");
			sequenceViewCellTable.addColumn(numberOfPhases, "Number Of Phases");
		}
		dataProvider.addDataDisplay(sequenceViewCellTable);

		List<SequenceDetails> dataList = dataProvider.getList();
		dataList.clear();
		if (rowData != null && !rowData.isEmpty()) {
			for (SequenceDetails ld : rowData) {
				dataList.add(ld);
			}
		}
	}

	@Override
	public void setRowDataWithSequenceDetails(List<SequenceDetailsMap> rowData) {

		ListDataProvider<SequenceDetailsMap> dataProvider = new ListDataProvider<SequenceDetailsMap>();

		if (sequenceDetailsViewCellTable.getColumnCount() == 0) {
			sequenceDetailsViewCellTable.addColumn(sequenceName1,
					"Sequence Name");
			sequenceDetailsViewCellTable.addColumn(sequenceNumber,
					"Sequence Number");
			sequenceDetailsViewCellTable.addColumn(cronFrquency,
					"Cron Frequency");
			sequenceDetailsViewCellTable.addColumn(machineName, "Machine Name");
			sequenceDetailsViewCellTable.addColumn(threadCount, "Thread Count");
			sequenceDetailsViewCellTable.addColumn(phaseName, "Phase Name");

		}
		dataProvider.addDataDisplay(sequenceDetailsViewCellTable);

		List<SequenceDetailsMap> dataList = dataProvider.getList();
		dataList.clear();
		if (rowData != null && !rowData.isEmpty()) {
			for (SequenceDetailsMap sdm : rowData) {
				dataList.add(sdm);
			}
		}
	}

	TextColumn<SequenceDetails> sequenceName = new TextColumn<SequenceDetails>() {
		public String getValue(SequenceDetails sd) {
			return sd.getSequenceName();
		}
	};
	TextColumn<SequenceDetails> numberOfPhases = new TextColumn<SequenceDetails>() {
		public String getValue(SequenceDetails sd) {
			return sd.getNumberOfPhases() + "";
		}
	};
	TextColumn<SequenceDetailsMap> cronFrquency = new TextColumn<SequenceDetailsMap>() {
		public String getValue(SequenceDetailsMap sdm) {
			return sdm.getCronFrequency();
		}
	};
	TextColumn<SequenceDetailsMap> machineName = new TextColumn<SequenceDetailsMap>() {
		public String getValue(SequenceDetailsMap sdm) {
			return sdm.getMachineName();
		}
	};
	TextColumn<SequenceDetailsMap> sequenceName1 = new TextColumn<SequenceDetailsMap>() {
		public String getValue(SequenceDetailsMap sdm) {
			return sdm.getSequenceName();
		}
	};
	TextColumn<SequenceDetailsMap> sequenceNumber = new TextColumn<SequenceDetailsMap>() {
		public String getValue(SequenceDetailsMap sdm) {
			return sdm.getSequenceNumber() + "";
		}
	};
	TextColumn<SequenceDetailsMap> threadCount = new TextColumn<SequenceDetailsMap>() {
		public String getValue(SequenceDetailsMap sdm) {
			return sdm.getThreadCount() + "";
		}
	};
	TextColumn<SequenceDetailsMap> phaseName = new TextColumn<SequenceDetailsMap>() {
		public String getValue(SequenceDetailsMap sdm) {
			return sdm.getPhaseName();
		}
	};
}
