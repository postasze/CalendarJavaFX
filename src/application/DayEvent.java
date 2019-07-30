package application;

import java.util.Comparator;

public class DayEvent implements Comparable<DayEvent> {

	private String description;
	private String startHour;
	private String startMinute;
	private String endHour;
	private String endMinute;
	
	public DayEvent(String description,	String startHour, String startMinute, String endHour, String endMinute) {
		this.description = description;
		this.startHour = startHour;
		this.startMinute = startMinute;
		this.endHour = endHour;
		this.endMinute = endMinute;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartHour() {
		return startHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	public String getStartMinute() {
		return startMinute;
	}

	public void setStartMinute(String startMinute) {
		this.startMinute = startMinute;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	public String getEndMinute() {
		return endMinute;
	}

	public void setEndMinute(String endMinute) {
		this.endMinute = endMinute;
	}

	@Override
	public int compareTo(DayEvent dayEvent) {
		if(this.startHour.compareTo(dayEvent.getStartHour()) > 0)
			return 1;
		else if(this.startHour.compareTo(dayEvent.getStartHour()) < 0)
			return -1;
		else {
			if(this.startMinute.compareTo(dayEvent.getStartMinute()) > 0)
				return 1;
			else if(this.startMinute.compareTo(dayEvent.getStartMinute()) < 0)
				return -1;
			else
				return 0;
		}
	}
	
	public static Comparator<DayEvent> dayEventComparator = new Comparator<DayEvent>() {
		public int compare(DayEvent dayEvent1, DayEvent dayEvent2) {
			//ascending order
			return dayEvent1.compareTo(dayEvent2);
		}
	};
}
