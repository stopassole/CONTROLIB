package controller;

import dao.CadastroDAO;
import entity.Cadastro;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import resource.Inicio;
import util.CriptoUtil;
import util.ValidarEmail;
import util.ValidatorCPF;

public class CadastroController {
	@FXML
	private TextField idEmail;
	@FXML
	private TextField idCPF;
	@FXML
	private TextField idEmpresa;
	@FXML
	private PasswordField idSenha;
	@FXML
	private Button btnCadastrar;
	@FXML
	private PasswordField idConfirmaSenha;
	@FXML
	private Button btnVoltarLogin;

	CriptoUtil cripto = new CriptoUtil();
	ValidarEmail validarEmail = new ValidarEmail();
	CadastroDAO dao = new CadastroDAO();

	@SuppressWarnings("static-access")
	@FXML
	public void salvarUsuario() throws Exception {
		if (verificaVazio()) {
			AlertFalha falha = new AlertFalha();
			falha.text = "Algum campo está vazio";
			falha.btnClicado = btnCadastrar;
			falha.start(new Stage());
		} else {
			if (senhaValida()) {
				if (new ValidatorCPF(idCPF).isValidCPF(idCPF.getText())) {
					if (validarEmail.validar(idEmail.getText())) {
						Cadastro c = new Cadastro(null, null, null, null, null, null, null);

						c.setEmail(idEmail.getText());
						c.setEmpresa(idEmpresa.getText());
						c.setCpf(idCPF.getText());
						c.setSenha(cripto.getSenhaCriptografada(idSenha.getText()));

						dao.salvarCadastro(c);

						Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
						Scene scene = new Scene(root);
						Inicio.myStage.setScene(scene);

						AlertSucesso sucesso = new AlertSucesso();
						sucesso.text = "Salvo com sucesso";
						sucesso.btnClicado = btnCadastrar;
						sucesso.start(new Stage());
					} else {
						AlertFalha falha = new AlertFalha();
						falha.text = "Infome um email válido";
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

	@FXML
	private void voltar() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}

	private boolean verificaVazio() {
		return idEmail.getText().isEmpty() || idConfirmaSenha.getText().isEmpty() || idCPF.getText().isEmpty()
				|| idSenha.getText().isEmpty() || idEmpresa.getText().isEmpty();
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

	public void enterPressedSalvar(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			salvarUsuario();
		}
	}

	public void enterPressedVoltar(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			voltar();
		}
	}

}
