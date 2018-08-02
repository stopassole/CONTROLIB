package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dao.UsuarioDAO;
import entity.Tipo;
import entity.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import resource.Inicio;
import util.DateUtil;
import util.ValidatorCPF;
import util.ValidatorTelefone;

public class CadastroUsuarioController extends DashboardController implements Initializable {
	@FXML
	private TextField idDataNasc;
	@FXML
	private TextField idNome;
	@FXML
	private TextField idSobrenome;
	@FXML
	private TextField idEndereco;
	@FXML
	private TextField idEmail;
	@FXML
	private TextField idTelefone;
	@FXML
	private TextField idCPF;
	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnSalvar;
	@FXML
	private Button bntCancelar;
	@FXML
	private ComboBox<Tipo> idTipo;

	UsuarioDAO dao = new UsuarioDAO();

	DateUtil date = new DateUtil();

	@FXML
	public void fechar() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/ListUsuario.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}

	@SuppressWarnings("static-access")
	@FXML
	public void salvar() throws Exception {
		Usuario usuario = new Usuario(null, null, null, null, null, null, null, null, null, null, null);

		if (!verificaVazio()) {
			if (validaObrigatorios()) {
				usuario.setNome(idNome.getText());
				usuario.setSobrenome(idSobrenome.getText());
				usuario.setEndereco(idEndereco.getText());
				usuario.setEmail(idEmail.getText());
				usuario.setTelefone(idTelefone.getText());
				usuario.setCPF(idCPF.getText());
				usuario.setDataNascimento(idDataNasc.getText());
				usuario.setIdTipo(idTipo.getValue().get_id());

				dao.salvarUsuario(usuario);
				fechar();
				AlertSucesso sucesso = new AlertSucesso();
				sucesso.text = "Salvo com sucesso";
				sucesso.btnClicado = btnSalvar;
				sucesso.start(new Stage());
			}
		} else {
			AlertFalha falha = new AlertFalha();
			falha.text = "Campos obrigatórios não informados";
			falha.btnClicado = btnSalvar;
			falha.start(new Stage());
		}
	}

	@SuppressWarnings("static-access")
	private boolean validaObrigatorios() {
		Boolean retornoCPF = false;
		Boolean retornoData = false;
		Boolean retorno = false;

		if (idCPF.getText().length() > 0) {
			if (new ValidatorCPF(idCPF).isValidCPF(idCPF.getText())) {
				retornoCPF = true;
			} else {
				AlertFalha falha = new AlertFalha();
				falha.text = "Infome um CPF válido";
				falha.btnClicado = btnSalvar;
				falha.start(new Stage());
				return false;
			}
		} else {
			retornoCPF = true;
		}

		if (date.isValidDate(idDataNasc.getText())) {
			retornoData = true;
		} else {
			AlertFalha falha = new AlertFalha();
			falha.text = "Infome uma data de nascimento válida";
			falha.btnClicado = btnSalvar;
			falha.start(new Stage());
			return false;
		}

		if (retornoCPF && retornoData) {
			retorno = true;
		}
		return retorno;
	}

	private boolean verificaVazio() {
		return idNome.getText().isEmpty() || idSobrenome.getText().isEmpty() || idDataNasc.getText().isEmpty()
				|| idTelefone.getText().isEmpty() || idEmail.getText().isEmpty()
				|| idTipo.getValue().get_id().equals(null);
	}

	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {
		getTipos();
	}

	private void getTipos() {
		List<Tipo> tipos = new ArrayList<>();
		Tipo t1 = new Tipo(null, null);
		t1.set_id("1");
		t1.setDescricao("Aluno");
		tipos.add(t1);
		Tipo t2 = new Tipo(null, null);
		t2.set_id("2");
		t2.setDescricao("Funcionário");
		tipos.add(t2);
		Tipo t3 = new Tipo(null, null);
		t3.set_id("3");
		t3.setDescricao("Outros");
		tipos.add(t3);

		idTipo.getItems().addAll(tipos);
		idTipo.setValue(tipos.get(0));
	}
	
	public String validaTipoUsuario(String idTipo){
		if(idTipo.equals("1")) {
			return "Aluno";
		}else if(idTipo.equals("2")) {
			return "Funcionário";
		}else {
			return "Outros";
		}
	}

	@SuppressWarnings("static-access")
	@FXML
	public void tbData(KeyEvent event) {
		date.mascaraData(event, idDataNasc);
	}

	@FXML
	private void tbCpf() {
		new ValidatorCPF(idCPF);
	}

	@FXML
	private void tbTelefone() {
		new ValidatorTelefone(idTelefone);
	}

}
