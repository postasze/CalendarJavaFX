package application;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Stack;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ViewManager {

	private static ViewManager instance;
	private Stage stage;
	private Stack<Scene> scenes;

	private Parent root, parent;
	private Scene scene;
	private Node node, leaf;
	private Label label;
	private TextField textField;
	private Spinner<Integer> spinner;

	public ViewManager() {
		scenes = new Stack<Scene>();
	}

	public static ViewManager getInstance() {
		if (instance == null)
			instance = new ViewManager();
		return instance;
	}

	public void setStage(Stage stage) {
		// begin-user-code
		this.stage = stage;
		// end-user-code
	}

	public void displayCalendar() throws IOException {
		int index1 = 0, index2 = 0, index3 = 0;

		root = FXMLLoader.load(getClass().getResource("fxcalendar.fxml"));
		scene = new Scene(root, 1000, 600);
		scene.getStylesheets().add(getClass().getResource("fxscene.css").toExternalForm());
		stage.setMinWidth(1000);
		stage.setMinHeight(600);
		stage.setScene(scene);
		stage.setTitle("Fx Calendar");
		stage.show();

		scenes.clear();
		scenes.push(scene);

		ObservableList<Node> itemList = root.getChildrenUnmodifiable();
		Iterator<Node> iterator = itemList.iterator();

		while (iterator.hasNext()) {
			node = iterator.next();

			if (node.getId() != null) {
				if (node.getId().equals("3")) {
					if (Model.getInstance().weekLabels[0][0] == null)
						Model.getInstance().weekLabels[0][0] = (Label) node;
					else
						Model.getInstance().weekLabels[0][1] = (Label) node;
				} else if (node.getId().equals("4")) {
					if (Model.getInstance().weekLabels[1][0] == null)
						Model.getInstance().weekLabels[1][0] = (Label) node;
					else
						Model.getInstance().weekLabels[1][1] = (Label) node;
				} else if (node.getId().equals("5")) {
					if (Model.getInstance().weekLabels[2][0] == null)
						Model.getInstance().weekLabels[2][0] = (Label) node;
					else
						Model.getInstance().weekLabels[2][1] = (Label) node;
				} else if (node.getId().equals("6")) {
					if (Model.getInstance().weekLabels[3][0] == null)
						Model.getInstance().weekLabels[3][0] = (Label) node;
					else
						Model.getInstance().weekLabels[3][1] = (Label) node;
				} else if (node.getId().equals("8")) {
					parent = (Parent) node;

					ObservableList<Node> nodeList = parent.getChildrenUnmodifiable();
					Iterator<Node> nodeIterator = nodeList.iterator();

					index2 = 0;
					while (nodeIterator.hasNext()) {
						leaf = nodeIterator.next();
						if (leaf.getId().equals("7")) {
							Model.getInstance().dayLabels[index3] = (Label) leaf;
							index3++;
						} else if (leaf.getId().equals("8")) {
							Model.getInstance().eventLabels[index1][index2] = (Label) leaf;
							Model.getInstance().eventLabels[index1][index2]
									.setId(Integer.toString(5 * index1 + index2));
							index2++;
						}
					}
					index1++;
				}
			}
		}

		for (int i = 0; i < 28; i++)
			for (int j = 0; j < 5; j++)
				Model.getInstance().eventLabels[i][j].setText("");

		// create a GregorianCalendar with the current date and time
		Model.getInstance().calendar = new GregorianCalendar();
		Model.getInstance().calendar.setTime(new Date());
		Model.getInstance().normalizeWeek();

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 2; j++)
				Model.getInstance().weekLabels[i][j]
						.setText("W" + (i + Model.getInstance().calendar.get(Calendar.WEEK_OF_MONTH)) + " "
								+ Model.getInstance().calendar.get(Calendar.YEAR));

		DiskManager.getInstance().readEventsFromFile();

		displayDays();

		Model.getInstance().dayLabels[Model.getInstance().currentDayOffset].getStylesheets().clear();
		Model.getInstance().dayLabels[Model.getInstance().currentDayOffset].getStylesheets()
				.add(getClass().getResource("label3.css").toExternalForm());
	}

	public void displayDays() {
		Date searchedDate = null;
		Day searchedDay = null;

		for (int i = 0; i < 28; i++)
			for (int j = 0; j < 5; j++)
				Model.getInstance().eventLabels[i][j].setText("");

		for (int i = 0; i < 28; i++) {
			Model.getInstance().dayLabels[i]
					.setText(Model.getInstance().Month[Model.getInstance().calendar.get(Calendar.MONTH)] + " "
							+ Model.getInstance().calendar.get(Calendar.DAY_OF_MONTH));

			searchedDate = new Date(Model.getInstance().calendar.get(Calendar.YEAR),
					Model.getInstance().calendar.get(Calendar.MONTH) + 1,
					Model.getInstance().calendar.get(Calendar.DAY_OF_MONTH));
			searchedDay = new Day(searchedDate);

			for (int j = 0; j < Model.getInstance().dayList.size(); j++) {
				if (searchedDay.equals(Model.getInstance().dayList.get(j))) {
					for (int k = 0; k < Model.getInstance().dayList.get(j).getDayEventsList().size(); k++) {
						Model.getInstance().eventLabels[i][k].setText(Model.getInstance().dayList.get(j)
								.getDayEventsList().get(k).getStartHour() + ":"
								+ Model.getInstance().dayList.get(j).getDayEventsList().get(k).getStartMinute() + "-"
								+ Model.getInstance().dayList.get(j).getDayEventsList().get(k).getEndHour() + ":"
								+ Model.getInstance().dayList.get(j).getDayEventsList().get(k).getEndMinute() + " "
								+ Model.getInstance().dayList.get(j).getDayEventsList().get(k).getDescription());
					}
				}
			}
			Model.getInstance().calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		Model.getInstance().calendar.add(Calendar.DAY_OF_MONTH, -28);

	}

	public void displayAddEventWindow(Date chosenDayOfCalendar) throws IOException {

		root = FXMLLoader.load(getClass().getResource("addEventWindow.fxml"));
		scene = new Scene(root, 1000, 600);
		stage.setScene(scene);
		// stage.setTitle("Add Event Window");
		stage.show();
		scenes.push(scene);

		ObservableList<Node> itemList = root.getChildrenUnmodifiable();
		Iterator<Node> iterator = itemList.iterator();

		while (iterator.hasNext()) {
			node = iterator.next();

			if (node.getId() != null) {
				if (node.getId().equals("day")) {
					label = (Label) node;
					label.setText(Integer.toString(chosenDayOfCalendar.getDate()));
				} else if (node.getId().equals("month")) {
					label = (Label) node;
					label.setText(Integer.toString(chosenDayOfCalendar.getMonth() + 1));
				} else if (node.getId().equals("year")) {
					label = (Label) node;
					label.setText(Integer.toString(chosenDayOfCalendar.getYear() + 1900));
				}
			}
		}
	}

	public void displayEditEventWindow(Date chosenDayOfCalendar, int chosenDayEventIndex) throws IOException {

		root = FXMLLoader.load(getClass().getResource("editEventWindow.fxml"));
		scene = new Scene(root, 1000, 600);
		stage.setScene(scene);
		// stage.setTitle("Edit Event Window");
		stage.show();
		scenes.push(scene);
		DayEvent chosenDayEvent = null;

		chosenDayOfCalendar.setMonth(chosenDayOfCalendar.getMonth() + 1);
		chosenDayOfCalendar.setYear(chosenDayOfCalendar.getYear() + 1900);

		for (int i = 0; i < Model.getInstance().dayList.size(); i++) {
			if (chosenDayOfCalendar.getDate() == Model.getInstance().dayList.get(i).dayDate.getDate()
					&& chosenDayOfCalendar.getMonth() == Model.getInstance().dayList.get(i).dayDate.getMonth()
					&& chosenDayOfCalendar.getYear() == Model.getInstance().dayList.get(i).dayDate.getYear()) {
				chosenDayEvent = Model.getInstance().dayList.get(i).dayEventsList.get(chosenDayEventIndex);
			}
		}

		ObservableList<Node> itemList = root.getChildrenUnmodifiable();
		Iterator<Node> iterator = itemList.iterator();

		while (iterator.hasNext()) {
			node = iterator.next();

			if (node.getId() != null) {
				if (node.getId().equals("day")) {
					label = (Label) node;
					label.setText(Integer.toString(chosenDayOfCalendar.getDate()));
				} else if (node.getId().equals("month")) {
					label = (Label) node;
					label.setText(Integer.toString(chosenDayOfCalendar.getMonth()));
				} else if (node.getId().equals("year")) {
					label = (Label) node;
					label.setText(Integer.toString(chosenDayOfCalendar.getYear()));
				} else if (node.getId().equals("description")) {
					textField = (TextField) node;
					textField.setText(chosenDayEvent.getDescription());
				} else if (node.getId().equals("startHour")) {
					spinner = (Spinner<Integer>) node;
					spinner.increment(Integer.parseInt(chosenDayEvent.getStartHour()));
				} else if (node.getId().equals("startMinute")) {
					spinner = (Spinner<Integer>) node;
					spinner.increment(Integer.parseInt(chosenDayEvent.getStartMinute()));
				} else if (node.getId().equals("endHour")) {
					spinner = (Spinner<Integer>) node;
					spinner.increment(Integer.parseInt(chosenDayEvent.getEndHour()));
				} else if (node.getId().equals("endMinute")) {
					spinner = (Spinner<Integer>) node;
					spinner.increment(Integer.parseInt(chosenDayEvent.getEndMinute()));
				} else if (node.getId().equals("dayEventNumber")) {
					label = (Label) node;
					label.setText(Integer.toString(chosenDayEventIndex + 1));
				}
			}
		}
	}

	public void displayPreviousView() throws IOException {
		scenes.pop();
		stage.setScene(scenes.peek());
		stage.show();
	}
}


