package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.UsuarioDAO;
import entity.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import resource.Inicio;
import util.DateUtil;

public class InfoUsuarioController extends DashboardController implements Initializable {

	@FXML
	private Label idDataNasc;
	@FXML
	private Label idNomeCodigo;
	@FXML
	private Label idTipo;
	@FXML
	private Label idEndereco;
	@FXML
	private Label idEmail;
	@FXML
	private Label idTelefone;
	@FXML
	private Label idCPF;
	@FXML
	private Label idTotalEmprestimo;
	@FXML
	private Button bntCancelar;
	@FXML
	private Button btnEditar;
	@FXML
	private Button btnExcluir;

	UsuarioDAO dao = new UsuarioDAO();

	CadastroUsuarioController cadastroUsuario = new CadastroUsuarioController();

	public static String idUsuarioEditar = null;

	@FXML
	public void fechar() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/ListUsuario.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}

	@FXML
	public void editar() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/CadastroUsuario.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}

	@SuppressWarnings("static-access")
	@FXML
	public void deletarUsuario() throws Exception {
		try {
			dao.excluirUsuario(ListUsuariosController.idUsuario);
			fechar();
			AlertSucesso sucesso = new AlertSucesso();
			sucesso.text = "Excluido com sucesso";
			sucesso.btnClicado = btnExcluir;
			sucesso.start(new Stage());
		} catch (Exception e) {
			fechar();
			AlertFalha sucesso = new AlertFalha();
			sucesso.text = "Falha ao excluir";
			sucesso.btnClicado = btnExcluir;
			sucesso.start(new Stage());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String idUsuario = ListUsuariosController.idUsuario;
		try {
			Usuario usuario = dao.getByIdUsuario(idUsuario);
			popularInformacao(usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	private void popularInformacao(Usuario usuario) throws Exception {
		if (usuario != null) {
			DateUtil dateUtil = new DateUtil();
			idUsuarioEditar = usuario.get_id();
			idCPF.setText(usuario.getCPF());
			idDataNasc.setText(dateUtil.dataFormatoYYYYMMDD(usuario.getDataNascimento()));
			idEmail.setText(usuario.getEmail());
			idEndereco.setText(usuario.getEndereco());
			idNomeCodigo.setText(usuario.getNome() + " " + usuario.getSobrenome() + " " + usuario.get_id());
			idTelefone.setText(usuario.getTelefone());
			idTipo.setText(usuario.getTipo());
			idTotalEmprestimo.setText("0");
		}
	}

	public void enterPressedFechar(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			fechar();
		}
	}

	public void enterPressedEditar(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			editar();
		}
	}
}
