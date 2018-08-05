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
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import resource.Inicio;

public class CadastroLivroController extends DashboardController implements Initializable {
	@FXML
	private TextFlow idTextFlow;
	@FXML
	private TextField idNome;
	@FXML
	private TextField idAutor;
	@FXML
	private TextField idGenero;
	@FXML
	private TextField idEditora;
	@FXML
	private Spinner<Integer> idQuantidade;
	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnFechar;
	@FXML
	private Button btnSalvar;

	LivroDAO dao = new LivroDAO();

	@FXML
	public void fechar() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/ListLivros.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}

	@SuppressWarnings("static-access")
	@FXML
	public void salvarLivro() throws Exception {
		Livro livro = new Livro(null, null, null, null, null, null, null, null, null);

		if (!verificaVazio()) {

			livro.setNome(idNome.getText());
			livro.setAutor(idAutor.getText());
			livro.setEditora(idEditora.getText());
			livro.setGenero(idGenero.getText());
			livro.setQuantidadeTotal(idQuantidade.getValue());

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
					falha.text = "Livro com estes dados já cadastrado";
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
			falha.text = "Campos obrigatórios não informados";
			falha.clicado = idTextFlow;
			falha.start(new Stage());
		}
	}

	private boolean verificaVazio() {
		return idNome.getText().isEmpty() || idAutor.getText().isEmpty() || idQuantidade.getValue() == null;
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

	private void populaEdicao(Livro livro) {
		idNome.setText(livro.getNome());
		idAutor.setText(livro.getAutor());
		idGenero.setText(livro.getGenero());
		idEditora.setText(livro.getEditora());
		idQuantidade.getValueFactory().setValue(livro.getQuantidadeTotal());
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
}
