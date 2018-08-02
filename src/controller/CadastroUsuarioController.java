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
import util.ValidarEmail;
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
	
	ValidarEmail validarEmail = new ValidarEmail();
	
	List<Tipo> tipos = new ArrayList<>();

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
				usuario.setTipo(idTipo.getValue().getDescricao());

				if (InfoUsuarioController.idUsuarioEditar == null) {
					dao.salvarUsuario(usuario);
					fechar();
					AlertSucesso sucesso = new AlertSucesso();
					sucesso.text = "Salvo com sucesso";
					sucesso.btnClicado = btnSalvar;
					sucesso.start(new Stage());
				} else {
					dao.editarUsuario(InfoUsuarioController.idUsuarioEditar, usuario);
					fechar();
					AlertSucesso sucesso = new AlertSucesso();
					sucesso.text = "Editado com sucesso";
					sucesso.btnClicado = btnSalvar;
					sucesso.start(new Stage());
				}
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
		Boolean retornoEmail = false;
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
		
		if(validarEmail.validar(idEmail.getText())) {
			retornoEmail = true;
		}else {
			AlertFalha falha = new AlertFalha();
			falha.text = "Infome um email válido";
			falha.btnClicado = btnSalvar;
			falha.start(new Stage());
			return false;
		}

		if (retornoCPF && retornoData && retornoEmail) {
			retorno = true;
		}
		return retorno;
	}

	private boolean verificaVazio() {
		return idNome.getText().isEmpty() || idSobrenome.getText().isEmpty() || idDataNasc.getText().isEmpty()
				|| idTelefone.getText().isEmpty() || idEmail.getText().isEmpty()
				|| idTipo.getValue().getDescricao().equals(null);
	}

	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {
		getTipos();
		if (InfoUsuarioController.idUsuarioEditar != null) {
			try {
				Usuario usuario = dao.getByIdUsuario(InfoUsuarioController.idUsuarioEditar);
				populaEdicao(usuario);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("static-access")
	public void populaEdicao(Usuario usuario) throws Exception {
		if (usuario != null) {
			DateUtil dateUtil = new DateUtil();
			idDataNasc.setText(dateUtil.dataFormatoYYYYMMDD(usuario.getDataNascimento()));
			idNome.setText(usuario.getNome());
			idSobrenome.setText(usuario.getSobrenome());
			idEndereco.setText(usuario.getEndereco());
			idEmail.setText(usuario.getEmail());
			idTelefone.setText(usuario.getTelefone());
			idCPF.setText(usuario.getCPF());
			idTipo.setValue(validaComboBoxPosicao(usuario.getTipo()));
		}
	}

	public void getTipos() {
		Tipo t1 = new Tipo( null);
		t1.setDescricao("Aluno");
		tipos.add(t1);
		Tipo t2 = new Tipo(null);
		t2.setDescricao("Funcionário");
		tipos.add(t2);
		Tipo t3 = new Tipo(null);
		t3.setDescricao("Outros");
		tipos.add(t3);

		idTipo.getItems().addAll(tipos);
		idTipo.setValue(tipos.get(0));
	}


	public Tipo validaComboBoxPosicao(String idTipo) {
		if (idTipo.equals("Aluno")) {
			return tipos.get(0);
		} else if (idTipo.equals("Funcionário")) {
			return tipos.get(1);
		} else {
			return tipos.get(2);
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
	
	public void enterPressedSalvar(KeyEvent e) throws Exception {
        if (e.getCode().toString().equals("ENTER")) {
        	salvar();
        }
    }
	
	public void enterPressedFechar(KeyEvent e) throws Exception {
        if (e.getCode().toString().equals("ENTER")) {
        	fechar();
        }
    }

}
