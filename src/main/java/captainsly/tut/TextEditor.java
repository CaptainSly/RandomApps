package captainsly.tut;

import javafx.application.Application;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TextEditor extends Application {

	private BorderPane pane;
	
	private MenuBar menuBar;
	private TextArea textArea;
	
	public static void main(String[] args) {
		TextEditor app = new TextEditor();
		Application.launch(app.getClass(), args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	}

}
