package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import dao.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import resource.Inicio;

public class LoginController implements Initializable {
	@FXML
	private PasswordField idSenha;
	@FXML
	private TextField idUsuario;
	@FXML
	private CheckBox idLembrarSenha;
	@FXML
	private Hyperlink idRecuperarSenha;
	@FXML
	private Hyperlink idCadastro;
	@FXML
	private Button btnEntrar;

	public static final String END_POINT = "LoginController";

	private static final Logger log = Logger.getLogger(Inicio.END_POINT);

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		log.info(END_POINT + "/validalogin -> Inicio");

		UsuarioDAO dao = new UsuarioDAO();

		int cont = 5;
		try {
			cont = dao.buscarUsuario();
		} catch (Exception e) {
			log.error(e);
		}

		if (cont == 0) {
			idCadastro.setVisible(true);
			idRecuperarSenha.setVisible(false);
		} else {
			idCadastro.setVisible(false);
			idRecuperarSenha.setVisible(true);
		}

		log.info(END_POINT + "/validalogin -> Fim");
	}

	@FXML
	public void cadastrar() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/Cadastro.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}

	@FXML
	public void recuperar() throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/view/Recuperar.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}

	@FXML
	private void entrar(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("This is a Custom Confirmation Dialog");

		ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
		buttonBar.getButtons().forEach(b -> b.setStyle(
				"-fx-font-size: 20px; -fx-text-fill: white; -fx-padding: 10px 0 0 180px; -fx-border-radius: 4px;"
						+ " -fx-cursor:hand;-fx-background-color: linear-gradient(to right, rgb(107, 193, 255) 20%, #00b3b8)"));
		buttonBar.setTranslateY(-20);
		buttonBar.setTranslateX(-65);
		alert.showAndWait();
	}

}
