package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import dao.CadastroDAO;
import entity.Cadastro;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import resource.Inicio;
import util.CriptoUtil;
import util.ValidarEmail;

public class LoginController implements Initializable {
	@FXML
	private TextFlow idTextFlow;
	@FXML
	private PasswordField idSenha;
	@FXML
	private TextField idEmail;
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

	CadastroDAO dao = new CadastroDAO();
	ValidarEmail validarEmail = new ValidarEmail();
	CriptoUtil cripto = new CriptoUtil();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		log.info(END_POINT + "/validalogin -> Inicio");

		CadastroDAO dao = new CadastroDAO();

		int cont = 5;
		try {
			cont = dao.contCadastro();
		} catch (Exception e) {
			log.error(e);
		}

		if (cont == 0) {
			idCadastro.setVisible(true);
			idRecuperarSenha.setVisible(false);
		} else {
			Cadastro cadastro = null;
			try {
				cadastro = dao.buscarCadastro();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (cadastro != null && cadastro.getLembraSenha() == true) {
				idEmail.setText(cadastro.getEmail());
				idSenha.setText(cadastro.getSenha());
				idLembrarSenha.setSelected(true);
			}
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

	@SuppressWarnings("static-access")
	@FXML
	private void entrar() throws Exception {
		if (verificaVazio()) {
			if (validarEmail.validar(idEmail.getText())) {
				String result = dao.getValidaCadastro(idEmail.getText(),cripto.getSenhaCriptografada(idSenha.getText()));
				if (result == null) {
					result = dao.getValidaCadastro(idEmail.getText(), idSenha.getText());
					if (result != null) {
						dao.alterarLembrarSenha(idEmail.getText(), idSenha.getText(), idLembrarSenha.isSelected());
					}
				} else {
					dao.alterarLembrarSenha(idEmail.getText(), cripto.getSenhaCriptografada(idSenha.getText()),idLembrarSenha.isSelected());
				}

				if (result != null) {
					Parent root = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
					Scene scene = new Scene(root);
					Inicio.myStage.setScene(scene);
				} else {
					AlertFalha falha = new AlertFalha();
					falha.text = "Usuário não encontrado";
					falha.clicado = idTextFlow;
					falha.start(new Stage());
				}

			} else {
				AlertFalha falha = new AlertFalha();
				falha.text = "Infome um email válido";
				falha.clicado = idTextFlow;
				falha.start(new Stage());
			}
		} 
	}

	@SuppressWarnings("static-access")
	private boolean verificaVazio() {
		if (idEmail.getText().isEmpty() && idSenha.getText().isEmpty()) {
			AlertFalha falha = new AlertFalha();
			falha.text = "Campos obrigatórios não informados";
			falha.clicado = idTextFlow;
			falha.start(new Stage());
			return false;
		} else if (idEmail.getText().isEmpty()) {
			AlertFalha falha1 = new AlertFalha();
			falha1.text = "usuário não informado";
			falha1.clicado = idTextFlow;
			falha1.start(new Stage());
			return false;
		} else if (idSenha.getText().isEmpty()) {
			AlertFalha falha2 = new AlertFalha();
			falha2.text = "Senha não informada";
			falha2.clicado = idTextFlow;
			falha2.start(new Stage());
			return false;
		} else {
			return true;
		}

	}

	public void enterPressedEntrar(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			entrar();
		}
	}

	public void enterPressedCadastar(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			cadastrar();
		}
	}

	public void enterPressedRecuperar(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			recuperar();
		}
	}
}
