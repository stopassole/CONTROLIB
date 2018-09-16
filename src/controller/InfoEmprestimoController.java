package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.EmprestimoDAO;
import entity.EmprestimoDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import resource.Inicio;
import util.DateUtil;

public class InfoEmprestimoController extends DashboardController implements Initializable {
	@FXML
	private TextFlow idTextFlow;
	@FXML
	private Button btnEmprestimos;
	@FXML
	private Button idDevolver;
	@FXML
	private Button idEditar;
	@FXML
	private Button btnLivros;
	@FXML
	private Button btnUsuarios;
	@FXML
	private Label idUsuario;
	@FXML
	private Button bntCancelar;
	@FXML
	private Label idEmail;
	@FXML
	private Label idTelefone;
	@FXML
	private Label idDataNasc;
	@FXML
	private Label idCpf;
	@FXML
	private Label idEndereco;
	@FXML
	private Label idTipo;
	@FXML
	private Label idDataEmprestimo;
	@FXML
	private Label idDataVencimento;
	@FXML
	private Label idLivro;
	@FXML
	private Label idAutor;
	@FXML
	private Label idGenero;
	@FXML
	private Label idEditora;
	@FXML
	private Label idPublicacao;

	public static String idEmprestimo = null;

	public static String idLivroDevolver = null;

	EmprestimoDAO dao = new EmprestimoDAO();

	@FXML
	public void fechar() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/ListEmprestimos.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
		Inicio.myStage.centerOnScreen();
	}

	@FXML
	public void editar() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/CadastroEmprestimo.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
		Inicio.myStage.centerOnScreen();
	}

	@SuppressWarnings("static-access")
	@FXML
	public void devolver() throws Exception {
		Devolver devolver = new Devolver();
		devolver.clicado = idTextFlow;
		devolver.classe = "Usuario";
		devolver.start(new Stage());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String idEmprestimo = ListEmprestimosController.idEmprestimo;
		try {
			EmprestimoDTO emprestimo = dao.getByIdEmprestimo(idEmprestimo);
			popularInformacao(emprestimo);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("static-access")
	private void popularInformacao(EmprestimoDTO emprestimo) throws Exception {
		idEmprestimo = emprestimo.get_id();
		idLivroDevolver = emprestimo.getIdLivro();
		DateUtil date = new DateUtil();
		idUsuario.setText(
				emprestimo.getNomeUsuario() + " " + emprestimo.getSobrenomeUsuario() + " " + emprestimo.getIdUsuario());
		idEmail.setText(emprestimo.getEmailUsuario());
		idTelefone.setText(emprestimo.getTelefoneUsuario());
		idDataNasc.setText(date.dataFormatoYYYYMMDD(emprestimo.getDataNascimentoUsuario()));
		idCpf.setText(emprestimo.getCPFUsuario());
		idEndereco.setText(emprestimo.getEnderecoUsuario());
		idTipo.setText(emprestimo.getTipoUsuario());
		idDataEmprestimo.setText(date.dataFormatoYYYYMMDD(emprestimo.getDataEmprestimo()));
		idDataVencimento.setText(date.dataFormatoYYYYMMDD(emprestimo.getDataVencimento()));
		idLivro.setText(emprestimo.getNomeLivro());
		idAutor.setText(emprestimo.getAutorLivro());
		idGenero.setText(emprestimo.getGeneroLivro());

		idEditora.setText(emprestimo.getEditoraLivro());

		if (!emprestimo.getPublicacaoLivro().equals("null")) {
			idPublicacao.setText(emprestimo.getPublicacaoLivro());
		}
	}

	@FXML
	public void enterPressedFechar(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			fechar();
		}
	}

	@FXML
	public void enterPressedEditar(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			editar();
		}
	}

	@FXML
	public void enterPressedDevolver(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			devolver();
		}
	}
}
