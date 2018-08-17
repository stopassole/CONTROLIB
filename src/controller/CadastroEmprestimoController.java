package controller;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dao.EmprestimoDAO;
import dao.LivroDAO;
import dao.UsuarioDAO;
import entity.Emprestimo;
import entity.EmprestimoDTO;
import entity.Livro;
import entity.Tipo;
import entity.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import resource.Inicio;
import util.ComboBoxAutoComplete;
import util.DateUtil;

public class CadastroEmprestimoController extends DashboardController implements Initializable {
	@FXML
	private TextFlow idTextFlow;
	@FXML
	private Label idTela;
	@FXML
	private Button btnEmprestimos;
	@FXML
	private Button btnLivros;
	@FXML
	private Button btnUsuarios;
	@FXML
	private Button bntCancelar;
	@FXML
	private Button btnSalvar;
	@FXML
	private ComboBox<Usuario> idUsuarioSelecionar;
	@FXML
	private ComboBox<Livro> idLivroSelecionar;
	@FXML
	private TextField idNomeUsuario;
	@FXML
	private TextField idNomeLivro;
	@FXML
	private TextField idCPF;
	@FXML
	private TextField idAutor;
	@FXML
	private ComboBox<Tipo> idTipo;
	@FXML
	private TextField idCodigo;
	@FXML
	private TextField idDataEmprestimo;
	@FXML
	private TextField idDataVencimento;
	@FXML
	private Button btnCancelar;

	EmprestimoDAO dao = new EmprestimoDAO();

	List<Usuario> usuarios = new ArrayList<>();
	List<Livro> livros = new ArrayList<>();

	UsuarioDAO usuarioDAO = new UsuarioDAO();
	LivroDAO livroDAO = new LivroDAO();

	DateUtil date = new DateUtil();

	@FXML
	public void fechar() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/ListEmprestimos.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}

	@SuppressWarnings("static-access")
	@FXML
	public void salvarEmprestimo() throws Exception {
		Emprestimo emprestimo = new Emprestimo(null, null, null, null, null, null, null);

		if (!verificaVazio()) {
			if (date.isValidDate(idDataEmprestimo.getText()) && date.isValidDate(idDataVencimento.getText())) {
				if (date.verificaMaiorData(idDataEmprestimo.getText(), idDataVencimento.getText())) {
					emprestimo.setIdUsuario(idUsuarioSelecionar.getValue().get_id());
					emprestimo.setIdLivro(idLivroSelecionar.getValue().get_id());
					emprestimo.setDataEmprestimo(idDataEmprestimo.getText());
					emprestimo.setDataVencimento(idDataVencimento.getText());

					if (InfoEmprestimoController.idEmprestimo == null) {
						dao.salvarEmprestimo(emprestimo);
						livroDAO.editarDisponivel(emprestimo.getIdLivro(), false);
						fechar();
						AlertSucesso sucesso = new AlertSucesso();
						sucesso.text = "Salvo com sucesso";
						sucesso.clicado = idTextFlow;
						sucesso.start(new Stage());
					} else {
						EmprestimoDTO emprestimoAntesEditar = dao.getByIdEmprestimo(InfoEmprestimoController.idEmprestimo);
						dao.editarEmprestimo(InfoEmprestimoController.idEmprestimo, emprestimo);
						if (!emprestimoAntesEditar.getIdLivro().equals(emprestimo.getIdLivro())) {
							livroDAO.editarDisponivel(emprestimoAntesEditar.getIdLivro(), true);
							livroDAO.editarDisponivel(emprestimo.getIdLivro(), false);
						}
						fechar();
						AlertSucesso sucesso = new AlertSucesso(); 
						sucesso.text = "Editado com sucesso";
						sucesso.clicado = idTextFlow;
						sucesso.start(new Stage());
					}
				} else {
					AlertFalha falha = new AlertFalha();
					falha.text = "A data de Vencimento deve ser maior que a data de Emprestimo";
					falha.clicado = idTextFlow;
					falha.start(new Stage());
				}
			} else {
				AlertFalha falha = new AlertFalha();
				falha.text = "Informe datas válidas";
				falha.clicado = idTextFlow;
				falha.start(new Stage());
			}
		} else {
			AlertFalha falha = new AlertFalha();
			falha.text = "Campos obrigatórios não informados";
			falha.clicado = idTextFlow;
			falha.start(new Stage());
		}
	}

	private boolean verificaVazio() {
		return idUsuarioSelecionar.getSelectionModel().getSelectedItem() == null
				|| idLivroSelecionar.getSelectionModel().getSelectedItem() == null
				|| idDataEmprestimo.getText().isEmpty() || idDataVencimento.getText().isEmpty();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			livros = livroDAO.buscarLivrosDisponiveis();
			usuarios = usuarioDAO.buscarUsuarios();
		} catch (Exception e) {
			e.printStackTrace();
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String hoje = sdf.format(new Date(System.currentTimeMillis()));
		idDataEmprestimo.setText(hoje);

		if (InfoEmprestimoController.idEmprestimo != null) {
			Emprestimo emprestimo = new Emprestimo(null, null, null, null, null, null, null);
			Livro livro = new Livro(null, null, null, null, null, null, null, null, null, null);
			Usuario usuario = new Usuario(null, null, null, null, null, null, null, null, null, null, null);
			try {
				emprestimo = dao.getEmprestimoById(InfoEmprestimoController.idEmprestimo);
				usuario = usuarioDAO.getByIdUsuario(emprestimo.getIdUsuario());
				livro = livroDAO.getByIdLivro(emprestimo.getIdLivro());
				if (emprestimo != null) {
					populaEdicao(emprestimo, usuario, livro);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		idUsuarioSelecionar.getItems().addAll(usuarios);
		idLivroSelecionar.getItems().addAll(livros);

		new ComboBoxAutoComplete<Usuario>(idUsuarioSelecionar);
		new ComboBoxAutoComplete<Livro>(idLivroSelecionar);
	}

	@SuppressWarnings("static-access")
	private void populaEdicao(Emprestimo emprestimo, Usuario usuario, Livro livro) throws Exception {
		idUsuarioSelecionar.setValue(usuario);
		idLivroSelecionar.setValue(livro);
		idDataEmprestimo.setText(date.dataFormatoYYYYMMDD(emprestimo.getDataEmprestimo()));
		idDataVencimento.setText(date.dataFormatoYYYYMMDD(emprestimo.getDataVencimento()));
		btnSalvar.setText("ATUALIZAR");
		idTela.setText("EDITAR EMPRÉSTIMO");
		livros.add(livro);
		preencherUsuario();
		preencherLivro();
	}

	@SuppressWarnings("static-access")
	@FXML
	public void tbDataEmprestimo(KeyEvent event) {
		date.mascaraData(event, idDataEmprestimo);
	}

	@SuppressWarnings("static-access")
	@FXML
	public void tbDataVencimento(KeyEvent event) {
		date.mascaraData(event, idDataVencimento);
	}

	@FXML
	public void preencherUsuario() throws Exception {
		if (idUsuarioSelecionar.getSelectionModel().getSelectedItem() != null) {
			Usuario usuario = usuarioDAO.getByIdUsuario(idUsuarioSelecionar.getValue().get_id());

			idNomeUsuario.setText(usuario.getNome());
			idCPF.setText(usuario.getCPF());
			Tipo tipo = new Tipo(usuario.getTipo());
			idTipo.setValue(tipo);
		}
	}

	@FXML
	public void preencherLivro() throws Exception {
		if (idLivroSelecionar.getSelectionModel().getSelectedItem() != null) {
			Livro livro = livroDAO.getByIdLivro(idLivroSelecionar.getValue().get_id());

			idNomeLivro.setText(livro.getNome());
			idCodigo.setText(livro.getCodigo());
			idAutor.setText(livro.getAutor());
		}
	}

	@FXML
	public void enterPressedFechar(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			fechar();
		}
	}

	@FXML
	public void enterPressedSalvar(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			salvarEmprestimo();
		}
	}
}
