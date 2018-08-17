package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.EmprestimoDAO;
import dao.LivroDAO;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Devolver extends Application implements Initializable{
	@FXML
	private Label idText;
	@FXML
	private Button idConfirma;
	@FXML
	private Button idCancelar;

	private static Stage stage;
	
	static TextFlow clicado;
	
	static String classe;

	public void start(Stage stage) {
		
		stage.initStyle(StageStyle.UNDECORATED);

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				clicado.setDisable(false);
				t.consume();
				stage.close();
			}
		});

		try {
			Devolver.stage = stage;
			Parent root = FXMLLoader.load(getClass().getResource("/view/Devolver.fxml"));
			stage.getIcons().add(new  Image(getClass().getResourceAsStream("../images/logo.png")));
			Scene scene = new Scene(root);
			stage.setTitle("CONTROLIB - DEVOLVER?");
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {

		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clicado.setDisable(true);
	}

	public static void fechar() {
		clicado.setDisable(false);
		Devolver.stage.close();
		Devolver.stage = null;
	}
	
	@FXML
	private void done() {
		fechar();
	}
	
	@SuppressWarnings("static-access")
	public void devolver() throws Exception {
		LivroDAO livroDAO = new LivroDAO();
		EmprestimoDAO dao = new EmprestimoDAO();
		InfoEmprestimoController info = new InfoEmprestimoController();
		try {
			livroDAO.editarDisponivel(InfoEmprestimoController.idLivroDevolver, true);
			dao.devolverEmprestimo(InfoEmprestimoController.idEmprestimo);
			fechar();
			info.fechar();
			AlertSucesso sucesso = new AlertSucesso();
			sucesso.text = "Devolvido com sucesso";
			sucesso.clicado = clicado;
			sucesso.start(new Stage());
		} catch (Exception e) {
			fechar();
			info.fechar();
			AlertFalha sucesso = new AlertFalha();
			sucesso.text = "Falha ao devolver";
			sucesso.clicado = clicado;
			sucesso.start(new Stage());
		}
	}

	@FXML
	public void enterPressedDevolver(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			devolver();
		}
	}
	
	@FXML
	public void enterPressedCancelar(KeyEvent e) {
		if (e.getCode().toString().equals("ENTER")) {
			fechar();
		}
	}

}
