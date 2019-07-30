package application;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Day implements Comparable<Day> {

	ArrayList<DayEvent> dayEventsList;
	Date dayDate;

	public Day(Date date) {
		this.dayEventsList = new ArrayList<DayEvent>();
		dayDate = date;
	}

	@Override
	public boolean equals(Object object) {
		//System.out.println("equals");
		Day day = (Day) object;
		if ((this.dayDate.getDate() == day.getDayDate().getDate())
				&& (this.dayDate.getMonth() == day.getDayDate().getMonth())
				&& (this.dayDate.getYear() == day.getDayDate().getYear()))
			return true;
		else
			return false;
	}

	public ArrayList<DayEvent> getDayEventsList() {
		return dayEventsList;
	}

	public void setDayEventsList(ArrayList<DayEvent> dayEventsList) {
		this.dayEventsList = dayEventsList;
	}

	public Date getDayDate() {
		return dayDate;
	}

	public void setDayDate(Date dayDate) {
		this.dayDate = dayDate;
	}

	@Override
	public int compareTo(Day day) {
		//System.out.println("compareTo");
		return this.dayDate.compareTo(day.getDayDate());
	}

	public static Comparator<Day> dayComparator = new Comparator<Day>() {
		public int compare(Day day1, Day day2) {
			// ascending order
			//System.out.println("comparator");
			return day1.compareTo(day2);
		}
	};
}
