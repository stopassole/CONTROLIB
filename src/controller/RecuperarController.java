package controller;

import dao.UsuarioDAO;
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
import util.ValidatorCPF;

public class RecuperarController {
	@FXML
	private Button btnVoltarLogin;
	@FXML
	private TextField idEmail;
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
	private void voltar() throws Exception {
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
	public void alterarSenha() throws Exception {
		if (verificaVazio()) {
			AlertFalha falha = new AlertFalha();
			falha.text = "Algum campo est� vazio";
			falha.btnClicado = btnCadastrar;
			falha.start(new Stage());
		} else {
			if (senhaValida()) {
				if (new ValidatorCPF(idCPF).isValidCPF(idCPF.getText())) {

					String retorno = dao.getValida(idEmail.getText(), idCPF.getText());

					if (retorno != null) {
						
						dao.alterarSenha(idEmail.getText(), idCPF.getText(),cripto.getSenhaCriptografada(idSenha.getText()));

						Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
						Scene scene = new Scene(root);
						Inicio.myStage.setScene(scene);

						AlertSucesso sucesso = new AlertSucesso();
						sucesso.text = "Salvo com sucesso";
						sucesso.btnClicado = btnCadastrar;
						sucesso.start(new Stage());
						
					} else {
						AlertFalha falha = new AlertFalha();
						falha.text = "Usu�rio n�o encontrado";
						falha.btnClicado = btnCadastrar;
						falha.start(new Stage());
					}

				} else {
					AlertFalha falha = new AlertFalha();
					falha.text = "Infome um CPF v�lido";
					falha.btnClicado = btnCadastrar;
					falha.start(new Stage());
				}
			} else {
				AlertFalha falha = new AlertFalha();
				falha.text = "As senhas est�o diferentes";
				falha.btnClicado = btnCadastrar;
				falha.start(new Stage());
			}
		}
	}

	private boolean verificaVazio() {
		return idEmail.getText().isEmpty() || idConfirmaSenha.getText().isEmpty() || idCPF.getText().isEmpty()
				|| idSenha.getText().isEmpty();
	}

	private boolean senhaValida() {
		if (!idSenha.getText().equals(idConfirmaSenha.getText())) {
			return false;
		} else {
			return true;
		}
	}
	
	public void enterPressedVoltar(KeyEvent e) throws Exception {
        if (e.getCode().toString().equals("ENTER")) {
        	voltar();
        }
    }
	
	public void enterPressedAlterar(KeyEvent e) throws Exception {
        if (e.getCode().toString().equals("ENTER")) {
        	alterarSenha();
        }
    }
}
