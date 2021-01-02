package captainsly.tut;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Calculator extends Application {

	// UI Nodes

	private BorderPane root;
	private GridPane grid;

	private Button[] numBtns = new Button[10];
	private Button multiplyBtn, divideBtn, additionBtn, subtractionBtn, equalsBtn, backBtn, clearBtn, clearMemBtn;

	private TextField answerField;
	private TextArea calcMem;

	// Iterator
	private enum State {
		NONE, ADD, SUB, MUL, DIV
	}

	// Calculator Variables
	private int intTemp;
	private float floatTemp;
	private State currentState;

	public static void main(String[] args) {
		Calculator app = new Calculator();
		Application.launch(app.getClass(), args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		currentState = State.NONE; // Set Current State to NONE
		root = new BorderPane();
		grid = new GridPane();

		// Create the Answer Field, make sure it's not editable and set it to clear when backspace is pressed when it's focused
		answerField = new TextField("" + 0);
		answerField.setEditable(false);
		answerField.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.BACK_SPACE) {
				answerField.clear();
			}
		});

		// Create the Calculator Memory, make it not editable, and set it to clear when backspace is pressed when it's focused
		calcMem = new TextArea();
		calcMem.setEditable(false);
		calcMem.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.BACK_SPACE) {
				calcMem.clear();
			}
		});

		setupButtons();

		root.setTop(answerField);
		root.setCenter(grid);
		root.setRight(calcMem);

		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("Test");
		primaryStage.show();

	}

	private void setupButtons() {

		// Setup The numerical buttons
		for (int i = 0; i < numBtns.length; i++) {
			numBtns[i] = new Button("" + i);

			int temp = i;
			numBtns[i].setOnAction(e -> {
				if (answerField.getText().equals("") || answerField.getText().equals("0")) answerField.setText("");
				answerField.appendText("" + temp);
				calcMem.appendText("" + temp);
			});

		}

		// Setup the Action buttons
		clearMemBtn = new Button("CM");
		clearMemBtn.setOnAction(e -> {
			calcMem.clear();
		});

		clearBtn = new Button("C");
		clearBtn.setOnAction(e -> {
			currentState = State.NONE; // Clear state and set to none
			floatTemp = 0; // Set float to 0
			intTemp = 0; // Set int to 0
			answerField.setText("0"); // set answerfield back to 0
			addNewLine(); // Add new line to calc memory
			calcMem.appendText("CLEAR STATE AND TEMP VARIABLES\n");
		});

		backBtn = new Button("⌫");
		backBtn.setOnAction(e -> {
			String tempField = answerField.getText(0, answerField.getLength() - 1);
			answerField.setText(tempField);

			if (answerField.getLength() == 0) answerField.setText("0");
			calcMem.appendText("BACKSPACE ANSWER FIELD\n");
		});

		additionBtn = new Button("+");
		additionBtn.setOnAction(e -> {
			setIntTemp(State.ADD);
			addNewLine();
			calcMem.appendText("ADD\n");
		});

		subtractionBtn = new Button("-");
		subtractionBtn.setOnAction(e -> {
			setIntTemp(State.SUB);
			addNewLine();
			calcMem.appendText("SUB\n");
		});

		multiplyBtn = new Button("X");
		multiplyBtn.setOnAction(e -> {
			setIntTemp(State.MUL);
			addNewLine();
			calcMem.appendText("MUL\n");
		});

		divideBtn = new Button("÷");
		divideBtn.setOnAction(e -> {
			setFloatTemp(State.DIV);
			addNewLine();
			calcMem.appendText("DIV\n");
		});

		equalsBtn = new Button("=");
		equalsBtn.setOnAction(e -> {

			// Get the current State of the calculator
			switch (currentState) {
			case ADD: // The addition button was added, clear the screen and prepare for the next number
				int addTemp = Integer.parseInt(answerField.getText());
				int addAnswer = intTemp + addTemp;
				answerField.setText("" + addAnswer);
				addNewLine();
				calcMem.appendText("intTemp[" + intTemp + "] + addTemp[" + addTemp + "] = " + addAnswer + "\n");
				break;
			case DIV: // Same as addition but division instead, does not allow for division by 0, like it should
				float divTemp = Float.parseFloat(answerField.getText());
				float divAnswer;

				if (floatTemp != 0) {
					divAnswer = floatTemp / divTemp;
					answerField.setText("" + divAnswer);
					addNewLine();
					calcMem.appendText("intTemp[" + intTemp + "] / divTemp[" + divTemp + "] = " + divAnswer + "\n");
				} else answerField.setText("ERROR: CAN NOT DIVIDE BY ZERO");
				break;
			case MUL: // Same as addition, but multiplication instead
				int mulTemp = Integer.parseInt(answerField.getText());
				int mulAnswer = intTemp * mulTemp;
				answerField.setText("" + mulAnswer);
				addNewLine();
				calcMem.appendText("intTemp[" + intTemp + "] * mulTemp[" + mulTemp + "] = " + mulAnswer + "\n");
				break;
			case SUB: // Same as addition, but subtraction instead
				int subTemp = Integer.parseInt(answerField.getText());
				int subAnswer = intTemp - subTemp;
				answerField.setText("" + subAnswer);
				addNewLine();
				calcMem.appendText("intTemp[" + intTemp + "] * subTemp[" + subTemp + "] = " + subAnswer + "\n");
				break;
			default:
				break;

			}

		});

		// Align the buttons in a specific order to get the old Windows 98 calculator
		// feel
		grid.add(numBtns[7], 0, 0);
		grid.add(numBtns[8], 1, 0);
		grid.add(numBtns[9], 2, 0);
		grid.add(multiplyBtn, 3, 0);
		grid.add(clearBtn, 4, 0);

		grid.add(numBtns[4], 0, 1);
		grid.add(numBtns[5], 1, 1);
		grid.add(numBtns[6], 2, 1);
		grid.add(subtractionBtn, 3, 1);
		grid.add(backBtn, 4, 1);

		grid.add(numBtns[1], 0, 2);
		grid.add(numBtns[2], 1, 2);
		grid.add(numBtns[3], 2, 2);
		grid.add(additionBtn, 3, 2);

		grid.add(divideBtn, 0, 3);
		grid.add(numBtns[0], 1, 3);
		grid.add(clearMemBtn, 2, 3);
		grid.add(equalsBtn, 3, 3);

	}

	
	//-----------------------------------------------------------------------------
	// Helper methods
	
	private void addNewLine() {
		if (calcMem.getText() != "\n") calcMem.appendText("\n");
	}

	private void setIntTemp(State state) {
		currentState = state;
		intTemp = Integer.parseInt(answerField.getText());
		answerField.setText("" + 0);
	}

	private void setFloatTemp(State state) {
		currentState = state;
		floatTemp = Float.parseFloat(answerField.getText());
		answerField.setText("" + 0);
	}

}
