package application;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class EditEventController implements Initializable {

	@FXML
    private Label dayLabel; 
	@FXML
    private Label monthLabel;
	@FXML
    private Label yearLabel;
	@FXML
    private Label dayEventNumberLabel; 
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
	@FXML
    private Button removeButton; 
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	
	@FXML        
	private void deleteEvent(Event event) {
		Day chosenDay = new Day(new Date(Integer.valueOf(yearLabel.getText()), Integer.valueOf(monthLabel.getText()),
				Integer.valueOf(dayLabel.getText())));
		
		int dayEventIndex = Integer.valueOf(dayEventNumberLabel.getText()) - 1;

		for(int i = 0; i < Model.getInstance().dayList.size(); i++) {
			if (chosenDay.equals(Model.getInstance().dayList.get(i))) {
				Model.getInstance().dayList.get(i).dayEventsList.remove(dayEventIndex);
			}
		}
		
		ViewManager.getInstance().displayDays();
		
		DiskManager.getInstance().writeEventsToFile();
		goBack(new Event(null));
	}
	
	@FXML        
	private void updateEvent(Event event) {
		DayEvent dayEvent = new DayEvent(descriptionField.getText(), 
				(startHourSpinner.getValue().toString().length() == 1 ? "0" + startHourSpinner.getValue().toString() : startHourSpinner.getValue().toString()),
				(startMinuteSpinner.getValue().toString().length() == 1 ? "0" + startMinuteSpinner.getValue().toString() : startMinuteSpinner.getValue().toString()), 
				(endHourSpinner.getValue().toString().length() == 1 ? "0" + endHourSpinner.getValue().toString() : endHourSpinner.getValue().toString()),
				(endMinuteSpinner.getValue().toString().length() == 1 ? "0" + endMinuteSpinner.getValue().toString() : endMinuteSpinner.getValue().toString()));
		Day chosenDay = new Day(new Date(Integer.valueOf(yearLabel.getText()), Integer.valueOf(monthLabel.getText()),
				Integer.valueOf(dayLabel.getText())));
		
		int dayEventIndex = Integer.valueOf(dayEventNumberLabel.getText()) - 1;
		
		for(int i = 0; i < Model.getInstance().dayList.size(); i++) {
			if (chosenDay.equals(Model.getInstance().dayList.get(i))) {
				Model.getInstance().dayList.get(i).dayEventsList.get(dayEventIndex).setDescription(dayEvent.getDescription());
				Model.getInstance().dayList.get(i).dayEventsList.get(dayEventIndex).setStartHour(dayEvent.getStartHour());
				Model.getInstance().dayList.get(i).dayEventsList.get(dayEventIndex).setStartMinute(dayEvent.getStartMinute());
				Model.getInstance().dayList.get(i).dayEventsList.get(dayEventIndex).setEndHour(dayEvent.getEndHour());
				Model.getInstance().dayList.get(i).dayEventsList.get(dayEventIndex).setEndMinute(dayEvent.getEndMinute());
			}
		}
		
		ViewManager.getInstance().displayDays();
		
		DiskManager.getInstance().writeEventsToFile();
		goBack(new Event(null));
	}
	
	@FXML        
	private void goBack(Event event) {
                try {
                        ViewManager.getInstance().displayPreviousView();
                }
                catch (IOException e) {}
	}
}
