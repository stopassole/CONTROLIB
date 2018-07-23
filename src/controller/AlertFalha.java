package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AlertFalha extends Application implements Initializable {

	@FXML
	private Label idText;
	@FXML
	private Button idConfirma;

	private static Stage stage;
	
	public static String text;
	
	public static Button btnClicado;

	public void start(Stage stage) {
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                t.consume();
                btnClicado.setDisable(false);
                stage.close();

            }
        });
		
		try {
			AlertFalha.stage = stage;
			Parent root = FXMLLoader.load(getClass().getResource("/view/AlertFalha.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle("CONTROLIB - FALHA");
			stage.setResizable(false);
			stage.getStyle();
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {

		}
	}

	public static void fechar() {
		btnClicado.setDisable(false);
		AlertFalha.stage.close();
		AlertFalha.stage = null;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnClicado.setDisable(true);
		idText.setText(text);
	}

	@FXML
	private void done() {
		fechar();
	}
	
	public void enterPressed(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			fechar();
		}
	}

}
