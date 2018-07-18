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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AlertSucesso extends Application implements Initializable {

	@FXML
	private Label idText;
	@FXML
	private Button idConfirma;
	
	private static Stage stage;
	
	public static String text;
	
	public static Button btnClicado;

	@Override
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
			AlertSucesso.stage = stage;
			Parent root = FXMLLoader.load(getClass().getResource("/view/AlertSucesso.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle("CONTROLIB - SUCESSO");
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void fechar() {
		btnClicado.setDisable(false);
		AlertSucesso.stage.close();
		AlertSucesso.stage = null;
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
}
