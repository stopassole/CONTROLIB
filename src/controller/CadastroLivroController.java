package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.LivroDAO;
import entity.Livro;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import resource.Inicio;
import util.DateUtil;

public class CadastroLivroController extends DashboardController implements Initializable {
	@FXML
	private TextFlow idTextFlow;
	@FXML
	private TextField idNome;
	@FXML
	private TextField idCodigo;
	@FXML
	private TextField idAutor;
	@FXML
	private TextField idPublicacao;
	@FXML
	private TextField idGenero;
	@FXML
	private TextField idEditora;
	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnFechar;
	@FXML
	private Button btnSalvar;

	LivroDAO dao = new LivroDAO();

	DateUtil date = new DateUtil();

	@FXML
	public void fechar() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/ListLivros.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}

	@SuppressWarnings("static-access")
	@FXML
	public void salvarLivro() throws Exception {
		Livro livro = new Livro(null, null, null, null, null, null, null, null, null, null);
		Boolean ok = true;

		if (!idPublicacao.getText().isEmpty() && !validaPublicacao()) {
			ok = false;
		}
		if (ok) {
			if (!verificaVazio()) {
				livro.setNome(idNome.getText());
				livro.setCodigo(idCodigo.getText());
				livro.setAutor(idAutor.getText());
				livro.setPublicacao(idPublicacao.getText());
				livro.setEditora(idEditora.getText());
				livro.setGenero(idGenero.getText());

				if (InfoLivroController.idLivroEditar == null) {
					int cont = dao.validaLivro(livro);
					if (cont == 0) {
						dao.salvarLivro(livro);
						fechar();
						AlertSucesso sucesso = new AlertSucesso();
						sucesso.text = "Salvo com sucesso";
						sucesso.clicado = idTextFlow;
						sucesso.start(new Stage());
					} else {
						AlertFalha falha = new AlertFalha();
						falha.text = "Livro com este c�digo j� cadastrado";
						falha.clicado = idTextFlow;
						falha.start(new Stage());
					}
				} else {
					dao.editarLivro(InfoLivroController.idLivroEditar, livro);
					fechar();
					AlertSucesso sucesso = new AlertSucesso();
					sucesso.text = "Editado com sucesso";
					sucesso.clicado = idTextFlow;
					sucesso.start(new Stage());
				}
			} else {
				AlertFalha falha = new AlertFalha();
				falha.text = "Campos obrigat�rios n�o informados";
				falha.clicado = idTextFlow;
				falha.start(new Stage());
			}
		}
	}

	@SuppressWarnings("static-access")
	private boolean validaPublicacao() {
		if (date.isValidDate(idPublicacao.getText())) {
			return true;
		} else {
			AlertFalha falha = new AlertFalha();
			falha.text = "Infome uma data de nascimento v�lida";
			falha.clicado = idTextFlow;
			falha.start(new Stage());
			return false;
		}
	}

	private boolean verificaVazio() {
		return idNome.getText().isEmpty() || idAutor.getText().isEmpty();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (InfoLivroController.idLivroEditar != null) {
			try {
				Livro livro = dao.getByIdLivro(InfoLivroController.idLivroEditar);
				populaEdicao(livro);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("static-access")
	private void populaEdicao(Livro livro) throws Exception {
		DateUtil dateUtil = new DateUtil();
		if (!livro.getPublicacao().equals("null")) {
			idPublicacao.setText(dateUtil.dataFormatoYYYYMMDD(livro.getPublicacao()));
		}
		idNome.setText(livro.getNome());
		idCodigo.setText(livro.getCodigo());
		idAutor.setText(livro.getAutor());
		idGenero.setText(livro.getGenero());
		idEditora.setText(livro.getEditora());
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
			salvarLivro();
		}
	}

	@SuppressWarnings("static-access")
	@FXML
	public void tbData(KeyEvent event) {
		date.mascaraData(event, idPublicacao);
	}
}
