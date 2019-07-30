package application;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class AddEventController implements Initializable {

	@FXML
	private Label dayLabel;
	@FXML
	private Label monthLabel;
	@FXML
	private Label yearLabel;
	@FXML
	private TextField descriptionField;
	@FXML
	private Spinner<Integer> startHourSpinner;
	@FXML
	private Spinner<Integer> startMinuteSpinner;
	@FXML
	private Spinner<Integer> endHourSpinner;
	@FXML
	private Spinner<Integer> endMinuteSpinner;
	@FXML
	private Button goBackButton;
	@FXML
	private Button saveButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	private void addEvent(Event event) {

		DayEvent dayEvent = new DayEvent(descriptionField.getText(), 
				(startHourSpinner.getValue().toString().length() == 1 ? "0" + startHourSpinner.getValue().toString() : startHourSpinner.getValue().toString()),
				(startMinuteSpinner.getValue().toString().length() == 1 ? "0" + startMinuteSpinner.getValue().toString() : startMinuteSpinner.getValue().toString()), 
				(endHourSpinner.getValue().toString().length() == 1 ? "0" + endHourSpinner.getValue().toString() : endHourSpinner.getValue().toString()),
				(endMinuteSpinner.getValue().toString().length() == 1 ? "0" + endMinuteSpinner.getValue().toString() : endMinuteSpinner.getValue().toString()));
		Day chosenDay = new Day(new Date(Integer.valueOf(yearLabel.getText()), Integer.valueOf(monthLabel.getText()),
				Integer.valueOf(dayLabel.getText())));

		if (Model.getInstance().dayList.contains(chosenDay)) {
			//System.out.println("lololol1");
			Model.getInstance().dayList.forEach(item -> {
				if (chosenDay.equals(item)) {
					item.getDayEventsList().add(dayEvent);
					item.getDayEventsList().sort(DayEvent.dayEventComparator);
				}
			});
		} else {
			//System.out.println("lololol2");
			chosenDay.getDayEventsList().add(dayEvent);
			chosenDay.getDayEventsList().sort(DayEvent.dayEventComparator);
			Model.getInstance().dayList.add(chosenDay);
			Model.getInstance().dayList.sort(Day.dayComparator);
		}
		//System.out.println("lololol3");

		ViewManager.getInstance().displayDays();
		
		DiskManager.getInstance().writeEventsToFile();
		goBack(new Event(null));
	}

	@FXML
	private void goBack(Event event) {
		try {
			ViewManager.getInstance().displayPreviousView();
		} catch (IOException e) {
		}
	}
}

