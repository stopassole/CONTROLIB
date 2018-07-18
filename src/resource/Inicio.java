package resource;

import java.io.FileOutputStream;
import java.sql.Connection;

import org.apache.log4j.Logger;
import org.postgresql.util.PSQLException;

import banco.CreateDatabase;
import dao.DAO;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Inicio extends Application {

	public static Stage myStage = new Stage();

	public static final String END_POINT = "Inicio";

	private static final Logger log = Logger.getLogger(Inicio.END_POINT);

	@SuppressWarnings("unused")
	@Override
	public void start(Stage primaryStage) throws Exception {

		try {
			@SuppressWarnings({"resource" })
			FileOutputStream file = new FileOutputStream("C:\\Users\\Alisson Stopassole\\Desktop\\LogCONTROLIB.log");

			log.info(END_POINT + "/verificabanco -> Inicio");

			DAO dao = new DAO();
			Connection conexao = dao.conexaoUsuario();

			startLogin();

			log.info(END_POINT + "/verificabanco -> Fim");

		} catch (PSQLException e) {
			log.error(e);

			log.info(END_POINT + "/criarbanco -> Inicio");

			final ProgressBar progressBar = new ProgressBar(0);
			progressBar.setScaleX(5);
			progressBar.setScaleY(5);
			progressBar.setScaleZ(5);
			String css = this.getClass().getResource("/css/Progress.css").toExternalForm();
			progressBar.getStylesheets().add(css);
			progressBar.setTranslateX(650);
			progressBar.setTranslateY(300);

			Button startButton = new Button("Finalizar");

			startButton.setTranslateX(560);
			startButton.setTranslateY(500);
			startButton.setStyle("-fx-cursor:hand; -fx-background-radius: 5; -fx-text-fill: white; -fx-background-color: linear-gradient(to right, rgb(107, 193, 255) 20%, #00b3b8);");
			startButton.setScaleX(3);
			startButton.setScaleY(2);
			startButton.setVisible(false);

			startButton.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					try {
						startLogin();
					} catch (Exception e) {
						log.error(e);
					}
				}
			});

			startButton.setVisible(false);

			progressBar.setProgress(0);

			CreateDatabase createDatabase = new CreateDatabase(startButton);

			progressBar.progressProperty().unbind();

			progressBar.progressProperty().bind(createDatabase.progressProperty());

			Thread t1 = new Thread(createDatabase);
			t1.start();

			FlowPane root = new FlowPane();
			root.setPadding(new Insets(10));
			root.setHgap(10);

			root.getChildren().addAll(progressBar, startButton);

			root.setStyle("-fx-background-color: #1d2026");

			Scene scene = new Scene(root, 1024, 700);
			myStage.setTitle("Carregando");
			myStage.setScene(scene);
			myStage.centerOnScreen();
			myStage.show();

			log.info(END_POINT + "/criarbanco -> Fim");
		} 
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void startLogin() throws Exception {

		log.info(END_POINT + "/login -> Inicio");

		try {

			Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			myStage.setScene(new Scene(root));
			myStage.setTitle("CONTROLIB");
			myStage.centerOnScreen();
			myStage.show();

		} catch (Exception e) {
			log.error(e);
		}

		log.info(END_POINT + "/login -> Fim");

	}

}
