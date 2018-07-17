package controller;

import dao.UsuarioDAO;
import entity.Usuario;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import resource.Inicio;
import util.CriptoUtil;
import util.ValidatorCPF;

public class CadastroController {
	@FXML
	private TextField idUsuario;
	@FXML
	private TextField idCPF;
	@FXML
	private PasswordField idSenha;
	@FXML
	private Button btnCadastrar;
	@FXML
	private PasswordField idConfirmaSenha;
	@FXML
	private Button btnVoltarLogin;
	
	CriptoUtil cripto = new CriptoUtil();
	UsuarioDAO dao = new UsuarioDAO();

	@FXML
	public void salvarUsuario(ActionEvent event) throws Exception {
		if (verificaVazio()) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "Algum campo pode estar vazio.");

			alert.showAndWait();
		} else {
			if (senhaValida()) {
				if (new ValidatorCPF(idCPF).isValidCPF(idCPF.getText())) {
					Usuario u = new Usuario(null, null, null, null, null);

					u.setUsuario(idUsuario.getText());
					u.setCpf(idCPF.getText());
					u.setSenha(cripto.getSenhaCriptografada(idSenha.getText()));

					dao.salvarUsuario(u);

					Alert alert = new Alert(Alert.AlertType.INFORMATION, "Salvo com sucesso.");
					alert.setGraphic(new ImageView(this.getClass().getResource("/images/sucess.jpg").toString()));
					alert.showAndWait();
				} else {
					Alert alert = new Alert(Alert.AlertType.WARNING, "Insira um CPF válido.");
					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(Alert.AlertType.WARNING, "As senhas estão diferentes.");
				alert.showAndWait();
			}
		}
	}
	
	@FXML
	private void voltar(Event event) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}

	private boolean verificaVazio() {
		return idUsuario.getText().isEmpty() || idConfirmaSenha.getText().isEmpty() || idCPF.getText().isEmpty()
				|| idSenha.getText().isEmpty();
	}

	private boolean senhaValida() {
		if (!idSenha.getText().equals(idConfirmaSenha.getText())) {
			return false;
		} else {
			return true;
		}
	}

	@FXML
	private void tbCpf() {
		new ValidatorCPF(idCPF);
	}

}
