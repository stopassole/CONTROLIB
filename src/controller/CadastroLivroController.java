package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dao.LivroDAO;
import entity.Livro;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	private Label idTela;
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
		Inicio.myStage.centerOnScreen();
	}

	@SuppressWarnings("static-access")
	@FXML
	public void salvarLivro() throws Exception {
		Livro livro = new Livro(null, null, null, null, null, null, null, null, null, null);
		Boolean ok = true;

		if (idPublicacao.getText().length() != 0 && idPublicacao.getText().length() != 4) {
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

				List<Livro> l = new ArrayList<>();
				if (!idCodigo.getText().isEmpty()) {
					l = dao.validaLivro(livro);
				}

				if (InfoLivroController.idLivroEditar == null) {
					if (l.isEmpty()) {
						dao.salvarLivro(livro);
						fechar();
						AlertSucesso sucesso = new AlertSucesso();
						sucesso.text = "Salvo com sucesso";
						sucesso.clicado = idTextFlow;
						sucesso.start(new Stage());
					} else {
						AlertFalha falha = new AlertFalha();
						falha.text = "Livro com este código já cadastrado";
						falha.clicado = idTextFlow;
						falha.start(new Stage());
					}
				} else {
					if (l.isEmpty() || l.size() == 1 && l.get(0).get_id().equals(InfoLivroController.idLivroEditar)) {
						dao.editarLivro(InfoLivroController.idLivroEditar, livro);
						fechar();
						AlertSucesso sucesso = new AlertSucesso();
						sucesso.text = "Editado com sucesso";
						sucesso.clicado = idTextFlow;
						sucesso.start(new Stage());
					} else {
						AlertFalha falha = new AlertFalha();
						falha.text = "Livro com este código já cadastrado";
						falha.clicado = idTextFlow;
						falha.start(new Stage());
					}
				}
			} else {
				AlertFalha falha = new AlertFalha();
				falha.text = "Campos obrigatórios não informados";
				falha.clicado = idTextFlow;
				falha.start(new Stage());
			}
		} else {
			AlertFalha falha = new AlertFalha();
			falha.text = "Data de publicação inválida";
			falha.clicado = idTextFlow;
			falha.start(new Stage());
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
				idTela.setText("EDITAR LIVRO");
				btnSalvar.setText("ATUALIZAR");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void populaEdicao(Livro livro) throws Exception {
		idPublicacao.setText(livro.getPublicacao());
		idNome.setText(livro.getNome());
		idCodigo.setText(livro.getCodigo());
		idAutor.setText(livro.getAutor());
		idGenero.setText(livro.getGenero());
		idEditora.setText(livro.getEditora());
	}

	@FXML
	private void tbNumber(KeyEvent event) {
		idPublicacao.lengthProperty().addListener(new javafx.beans.value.ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					char ch = idPublicacao.getText().charAt(oldValue.intValue());
					if (!(ch >= '0' && ch <= '9') && idPublicacao.getText().length() <= 4) {
						idPublicacao.setText(idPublicacao.getText().substring(0, idPublicacao.getText().length() - 1));
					} else {
						if (idPublicacao.getText().length() > 4) {
							idPublicacao
									.setText(idPublicacao.getText().substring(0, idPublicacao.getText().length() - 1));
						}
					}
				}
			}
		});
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
