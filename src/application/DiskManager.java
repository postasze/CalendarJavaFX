package application;

import java.io.*;
import java.util.Date;

public class DiskManager {

	private static DiskManager instance;
	FileInputStream in = null;
	FileOutputStream out = null;

	public DiskManager() {

		/*
		 * try { in = new FileInputStream("input.txt"); out = new
		 * FileOutputStream("output.txt");
		 * 
		 * } catch (FileNotFoundException e) { e.printStackTrace(); } finally { if (in
		 * != null) { try { in.close(); } catch (IOException e) { e.printStackTrace(); }
		 * } if (out != null) { try { out.close(); } catch (IOException e) {
		 * e.printStackTrace(); } } }
		 */
	}

	public static DiskManager getInstance() {
		if (instance == null)
			instance = new DiskManager();
		return instance;
	}

	public void writeEventsToFile() {

		String outputLine = null;

		try {
			out = new FileOutputStream("Zdarzenia.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int c;
		try {
			for (int i = 0; i < Model.getInstance().dayList.size(); i++) {
				outputLine = "__Data__";

				for (int j = 0; j < outputLine.length(); j++) {
					out.write(outputLine.charAt(j));
				}
				out.write(System.getProperty("line.separator").getBytes());

				outputLine = Integer.toString(Model.getInstance().dayList.get(i).dayDate.getDate());

				for (int j = 0; j < outputLine.length(); j++) {
					out.write(outputLine.charAt(j));
				}
				out.write(System.getProperty("line.separator").getBytes());
				
				outputLine = Integer.toString(Model.getInstance().dayList.get(i).dayDate.getMonth());

				for (int j = 0; j < outputLine.length(); j++) {
					out.write(outputLine.charAt(j));
				}
				out.write(System.getProperty("line.separator").getBytes());
				
				outputLine = Integer.toString(Model.getInstance().dayList.get(i).dayDate.getYear());

				for (int j = 0; j < outputLine.length(); j++) {
					out.write(outputLine.charAt(j));
				}
				out.write(System.getProperty("line.separator").getBytes());
				
				for (int j = 0; j < Model.getInstance().dayList.get(i).getDayEventsList().size(); j++) {
					outputLine = "__Zdarzenie__";

					for (int k = 0; k < outputLine.length(); k++) {
						out.write(outputLine.charAt(k));
					}
					out.write(System.getProperty("line.separator").getBytes());
					
					outputLine = Model.getInstance().dayList.get(i).getDayEventsList().get(j).getDescription();

					for (int k = 0; k < outputLine.length(); k++) {
						out.write(outputLine.charAt(k));
					}
					out.write(System.getProperty("line.separator").getBytes());
					
					outputLine = Model.getInstance().dayList.get(i).getDayEventsList().get(j).getStartHour();

					for (int k = 0; k < outputLine.length(); k++) {
						out.write(outputLine.charAt(k));
					}
					out.write(System.getProperty("line.separator").getBytes());
					
					outputLine = Model.getInstance().dayList.get(i).getDayEventsList().get(j).getStartMinute();

					for (int k = 0; k < outputLine.length(); k++) {
						out.write(outputLine.charAt(k));
					}
					out.write(System.getProperty("line.separator").getBytes());
					
					outputLine = Model.getInstance().dayList.get(i).getDayEventsList().get(j).getEndHour();

					for (int k = 0; k < outputLine.length(); k++) {
						out.write(outputLine.charAt(k));
					}
					out.write(System.getProperty("line.separator").getBytes());
					
					outputLine = Model.getInstance().dayList.get(i).getDayEventsList().get(j).getEndMinute();

					for (int k = 0; k < outputLine.length(); k++) {
						out.write(outputLine.charAt(k));
					}
					out.write(System.getProperty("line.separator").getBytes());
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void readEventsFromFile() {

		DayEvent dayEvent = null;
		Day dayObject = null;
		short counter = 0;
		String line = "";
		char c;
		int day = 0;
		int month = 0;
		int year = 0;
		String description = null;
		String startHour = "";
		String startMinute = "";
		String endHour = "";
		String endMinute = "";
		boolean readingDate = true;

		//System.out.println(System.getProperty("line.separator").getBytes());
		
		try {
			in = new FileInputStream("Zdarzenia.txt");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			while ((c = (char) in.read()) != 65535) {
				if ((int) c != 10) // c != '\n'
				{
					System.out.println("read" + c + "lolol");
					//System.out.println("read" + (int)c + "lolol");
					line += c;
				} else if ((int) c == 10) { // c == '\n'
					//in.read();
					if (line.equals("__Data__")) {
						System.out.println("lol");
						readingDate = true;
						line = "";
						counter = 0;
						continue;
					} else if (line.equals("__Zdarzenie__")) {
						System.out.println("lol1");
						readingDate = false;
						line = "";
						counter = 0;
						continue;
					} else {
						if (readingDate) { // czytanie nowej daty
							System.out.println("lol2");
							switch (counter) {
							case 0:
								day = Integer.parseInt(line);
								break;
							case 1:
								month = Integer.parseInt(line);
								break;
							case 2:
								year = Integer.parseInt(line);
								dayObject = new Day(new Date(year, month, day));
								Model.getInstance().dayList.add(dayObject);
								break;
							}
							line = "";
							counter = (short) ((counter + 1) % 3);
						} else { // readingEvents - czytanie zdarzen dla danej daty
									 System.out.println("lol3");
							switch (counter) {
							case 0:
								 System.out.println("lol30");
								description = line;
								break;
							case 1:
								 System.out.println("lol31");
								startHour = line;
								break;
							case 2:
								 System.out.println("lol32");
								startMinute = line;
								break;
							case 3:
								 System.out.println("lol33");
								endHour = line;
								break;
							case 4:
								 System.out.println("lol34");
								endMinute = line;
								dayEvent = new DayEvent(description, startHour, startMinute, endHour, endMinute);
								dayObject.dayEventsList.add(dayEvent);
								break;
							}
							line = "";
							counter = (short) ((counter + 1) % 5);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
