package controller;

import dao.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import resource.Inicio;
import util.CriptoUtil;
import util.ValidatorCPF;

public class RecuperarController {
	@FXML
	private Button btnVoltarLogin;
	@FXML
	private TextField idUsuario;
	@FXML
	private TextField idCPF;
	@FXML
	private PasswordField idSenha;
	@FXML
	private PasswordField idConfirmaSenha;
	@FXML
	private Button btnCadastrar;

	CriptoUtil cripto = new CriptoUtil();
	UsuarioDAO dao = new UsuarioDAO();

	@FXML
	private void voltar(Event event) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}

	@FXML
	private void tbCpf() {
		new ValidatorCPF(idCPF);
	}

	@SuppressWarnings("static-access")
	@FXML
	public void alterarSenha(ActionEvent event) throws Exception {
		if (verificaVazio()) {
			AlertFalha falha = new AlertFalha();
			falha.text = "Algum campo está vazio";
			falha.btnClicado = btnCadastrar;
			falha.start(new Stage());
		} else {
			if (senhaValida()) {
				if (new ValidatorCPF(idCPF).isValidCPF(idCPF.getText())) {

					String retorno = dao.getValida(idUsuario.getText(), idCPF.getText());

					if (retorno != null) {
						
						dao.alterarSenha(idUsuario.getText(), idCPF.getText(),cripto.getSenhaCriptografada(idSenha.getText()));

						Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
						Scene scene = new Scene(root);
						Inicio.myStage.setScene(scene);

						AlertSucesso sucesso = new AlertSucesso();
						sucesso.text = "Salvo com sucesso";
						sucesso.btnClicado = btnCadastrar;
						sucesso.start(new Stage());
						
					} else {
						AlertFalha falha = new AlertFalha();
						falha.text = "Usuário não encontrado";
						falha.btnClicado = btnCadastrar;
						falha.start(new Stage());
					}

				} else {
					AlertFalha falha = new AlertFalha();
					falha.text = "Infome um CPF válido";
					falha.btnClicado = btnCadastrar;
					falha.start(new Stage());
				}
			} else {
				AlertFalha falha = new AlertFalha();
				falha.text = "As senhas estão diferentes";
				falha.btnClicado = btnCadastrar;
				falha.start(new Stage());
			}
		}
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
}
