package application;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class FxCalendarController implements Initializable {
	
	private short clickCount = 0;
	
	@FXML
	  public void Click(ActionEvent event) {
	      //System.out.println(((Button)event.getSource()).getText());
	      
	      if(0 <= Model.getInstance().currentDayOffset && Model.getInstance().currentDayOffset < 28) {
	    	  Model.getInstance().dayLabels[Model.getInstance().currentDayOffset].getStylesheets().clear();
	    	  Model.getInstance().dayLabels[Model.getInstance().currentDayOffset].getStylesheets().add(getClass().getResource("label2.css").toExternalForm());
	      }
	      
	      if(((Button)event.getSource()).getText().equals("prev")) {
	    	  Model.getInstance().calendar.add(Calendar.DAY_OF_MONTH, -7);
	    	  Model.getInstance().currentDayOffset += 7;
	      }
	      
	      else if(((Button)event.getSource()).getText().equals("next")) {
	    	  Model.getInstance().calendar.add(Calendar.DAY_OF_MONTH, 7);
	    	  Model.getInstance().currentDayOffset -= 7;
	      }
	      if(0 <= Model.getInstance().currentDayOffset && Model.getInstance().currentDayOffset < 28) {
	    	  Model.getInstance().dayLabels[Model.getInstance().currentDayOffset].getStylesheets().clear();
	      	  Model.getInstance().dayLabels[Model.getInstance().currentDayOffset].getStylesheets().add(getClass().getResource("label3.css").toExternalForm());
	      }
	      
	      ViewManager.getInstance().displayDays();
	  }
	
	  @FXML
	  public void EventClick(MouseEvent event) {
	      
	      if(clickCount == 0) {
	    	  clickCount++;
	    	  return;
	      }
	      else
	    	  clickCount = 0;
	      
	      Date firstDayOfCalendar = Model.getInstance().calendar.getTime();
	      int dayNumber = Integer.parseInt(((Label)event.getSource()).getId()) / 5;
	      int eventNumber = Integer.parseInt(((Label)event.getSource()).getId()) % 5;
	      Date chosenDayOfCalendar = (Date) firstDayOfCalendar.clone();
	      chosenDayOfCalendar.setDate(firstDayOfCalendar.getDate() + dayNumber);
	      //System.out.println(chosenDayOfCalendar);
	      
	      if(((Label)event.getSource()).getText().equals("")) { // puste pole - nowe wydarzenie
	    	  try {
	              ViewManager.getInstance().displayAddEventWindow(chosenDayOfCalendar);
	  	    } catch(Exception e) {
	  	      e.printStackTrace();
	  	    }   
	      }
	      else { // niepuste pole - edycja wydarzenia 
	    	  try {
	              ViewManager.getInstance().displayEditEventWindow(chosenDayOfCalendar, eventNumber);
	  	    } catch(Exception e) {
	  	      e.printStackTrace();
	  	    }   
	      } 	      
	  }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
