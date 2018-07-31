package controller;

import java.util.ResourceBundle;

import dao.UsuarioDAO;
import entity.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
	private ImageView bntCancelar;
	@FXML
	private ComboBox<String> idTipo;

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
		Usuario usuario = new Usuario(null, null, null, null, null, null, null, null, null, null);

		usuario.setNome(idNome.getText());
		usuario.setSobrenome(idSobrenome.getText());
		usuario.setEndereco(idEndereco.getText());
		usuario.setEmail(idEmail.getText());
		usuario.setTelefone(idTelefone.getText());
		usuario.setCPF(idCPF.getText());
		usuario.setDataNascimento(idDataNasc.getText());
		usuario.setIdTipo("1");

		dao.salvarUsuario(usuario);
		fechar();
		AlertSucesso sucesso = new AlertSucesso();
		sucesso.text = "Salvo com sucesso";
		sucesso.btnClicado = btnSalvar;
		sucesso.start(new Stage());
	}

	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {

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
