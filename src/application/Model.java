package application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

import javafx.scene.Scene;
import javafx.scene.control.Label;

public class Model {

	private static Model instance;
	
	public final String[] Month = new String[] {
	    	"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" 
	    };
		
	public ArrayList<Day> dayList;
	public Label[][] eventLabels = new Label[28][5];
	public Label[][] weekLabels = new Label[4][2];
	public Label[] dayLabels = new Label[28];
	public Calendar calendar;
	public int currentDayOffset;
	
	public Model() {
		dayList = new ArrayList<Day>(); 
	}
	
	public static Model getInstance() {
        if(instance == null)
                instance = new Model();
        return instance;
	}
	public void resetCalendar() {
		calendar.setTime(new Date());
		normalizeWeek();
	}
	
	public void normalizeWeek() {
		if(calendar.get(Calendar.DAY_OF_WEEK) == 2) { // obecny dzien to poniedzialek
			currentDayOffset = 0;
		}
		else { // obecny dzien nie poniedzialek - przesuwamy sie do tylu do poniedzialku
	        int difference = calendar.get(Calendar.DAY_OF_WEEK) - 2;
	        if(difference == -1) {// obecny dzien to niedziela
	      	    calendar.add(Calendar.DAY_OF_MONTH, -6);
	      	    currentDayOffset = 6;
	        }
	    	else {// inne dni
	    		calendar.add(Calendar.DAY_OF_MONTH, -difference);
	    		currentDayOffset = difference;
	    	}    
	    }
	}
}
