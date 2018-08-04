package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.UsuarioDAO;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Deletar extends Application implements Initializable {

	@FXML
	private Label idText;
	@FXML
	private Button idConfirma;
	@FXML
	private Button idCancelar;

	private static Stage stage;

	public static Button btnClicado;

	public static ImageView img;

	UsuarioDAO dao = new UsuarioDAO();

	InfoUsuarioController info = new InfoUsuarioController();

	public void start(Stage stage) {

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				img.setDisable(false);
				btnClicado.setDisable(false);
				t.consume();
				stage.close();
			}
		});

		try {
			Deletar.stage = stage;
			Parent root = FXMLLoader.load(getClass().getResource("/view/Deletar.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle("CONTROLIB - DELETAR?");
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {

		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		img.setDisable(true);
		btnClicado.setDisable(true);
	}

	@SuppressWarnings("static-access")
	@FXML
	public void deletarUsuario() throws Exception {
		try {
			dao.excluirUsuario(ListUsuariosController.idUsuario);
			fechar();
			info.fechar();
			AlertSucesso sucesso = new AlertSucesso();
			sucesso.text = "Excluido com sucesso";
			sucesso.btnClicado = btnClicado;
			sucesso.start(new Stage());
		} catch (Exception e) {
			fechar();
			info.fechar();
			AlertFalha sucesso = new AlertFalha();
			sucesso.text = "Falha ao excluir";
			sucesso.btnClicado = btnClicado;
			sucesso.start(new Stage());
		}
	}

	public static void fechar() {
		img.setDisable(false);
		btnClicado.setDisable(false);
		Deletar.stage.close();
		Deletar.stage = null;
	}

	@FXML
	private void done() {
		fechar();
	}

	@FXML
	public void enterPressedDeletar(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			deletarUsuario();
		}
	}

	@FXML
	public void enterPressedCnacelar(KeyEvent e) {
		if (e.getCode().toString().equals("ENTER")) {
			fechar();
		}
	}
}
