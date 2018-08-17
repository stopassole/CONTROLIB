package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.LivroDAO;
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
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextFlow;
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
	
	static TextFlow clicado;
	
	static String classe;

	public void start(Stage stage) {

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				clicado.setDisable(false);
				t.consume();
				stage.close();
			}
		});

		try {
			Deletar.stage = stage;
			Parent root = FXMLLoader.load(getClass().getResource("/view/Deletar.fxml"));
			stage.getIcons().add(new  Image(getClass().getResourceAsStream("/images/logo.png")));
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
		clicado.setDisable(true);
	}

	public static void fechar() {
		clicado.setDisable(false);
		Deletar.stage.close();
		Deletar.stage = null;
	}
	
	@FXML
	public void deletar() throws Exception {
		if(classe.equals("Usuario")) {
			deletarUsuario();
		}else {
			deletarLivro();
		}
	}

	@FXML
	private void done() {
		fechar();
	}

	@FXML
	public void enterPressedDeletar(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			if(classe.equals("Usuario")) {
				deletarUsuario();
			}else {
				deletarLivro();
			}

		}
	}

	@FXML
	public void enterPressedCancelar(KeyEvent e) {
		if (e.getCode().toString().equals("ENTER")) {
			fechar();
		}
	}

	@SuppressWarnings("static-access")
	private void deletarLivro() throws Exception {
		LivroDAO dao = new LivroDAO();
		InfoLivroController info = new InfoLivroController();
		try {
			dao.excluirLivro(ListLivrosController.idLivro);
			fechar();
			info.fechar();
			AlertSucesso sucesso = new AlertSucesso();
			sucesso.text = "Excluido com sucesso";
			sucesso.clicado = clicado;
			sucesso.start(new Stage());
		} catch (Exception e) {
			fechar();
			info.fechar();
			AlertFalha sucesso = new AlertFalha();
			sucesso.text = "Falha ao excluir";
			sucesso.clicado = clicado;
			sucesso.start(new Stage());
		}
	}
	
	@SuppressWarnings("static-access")
	@FXML
	public void deletarUsuario() throws Exception {
		UsuarioDAO dao = new UsuarioDAO();
		InfoUsuarioController info = new InfoUsuarioController();
		try {
			dao.excluirUsuario(ListUsuariosController.idUsuario);
			fechar();
			info.fechar();
			AlertSucesso sucesso = new AlertSucesso();
			sucesso.text = "Excluido com sucesso";
			sucesso.clicado = clicado;
			sucesso.start(new Stage());
		} catch (Exception e) {
			fechar();
			info.fechar();
			AlertFalha sucesso = new AlertFalha();
			sucesso.text = "Falha ao excluir";
			sucesso.clicado = clicado;
			sucesso.start(new Stage());
		}
	}
}
