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
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import resource.Inicio;
import util.DateUtil;

public class InfoLivroController extends DashboardController implements Initializable {
	@FXML
	private TextFlow idTextFlow;
	@FXML
	private Label idNome;
	@FXML
	private Label idAutor;
	@FXML
	private Label idPublicacao;
	@FXML
	private Label idGenero;
	@FXML
	private Label idEditora;
	@FXML
	private Label idQuantidadeTotal;
	@FXML
	private Label idQuantidadeDisponivel;
	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnEditar;
	@FXML
	private Button btnExcluir;

	public static String idLivroEditar = null;

	LivroDAO dao = new LivroDAO();

	@FXML
	public void fechar() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/ListLivros.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}

	@FXML
	public void editar() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/CadastroLivro.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}

	@SuppressWarnings("static-access")
	@FXML
	public void excluir() throws Exception {
		Deletar deletar = new Deletar();
		deletar.clicado = idTextFlow;
		deletar.classe = "Livro";
		deletar.start(new Stage());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String idLivro = ListLivrosController.idLivro;
		try {
			Livro livro = dao.getByIdLivro(idLivro);
			popularInformacao(livro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	private void popularInformacao(Livro livro) throws Exception {
		if (livro != null) {
			DateUtil dateUtil = new DateUtil();
			idLivroEditar = livro.get_id();
			if (livro.getCodigo() != null && !livro.getCodigo().equals("")) {
				idNome.setText(livro.getNome() + " - " + livro.getCodigo());
			} else {
				idNome.setText(livro.getNome() + " - " + livro.get_id());
			}
			idAutor.setText(livro.getAutor());
			idGenero.setText(livro.getGenero());
			idEditora.setText(livro.getEditora());
			if (!livro.getPublicacao().equals("null")) {
				idPublicacao.setText(dateUtil.dataFormatoYYYYMMDD(livro.getPublicacao()));
			}
			idQuantidadeTotal.setText(String.valueOf(livro.getQuantidadeTotal()));
			idQuantidadeDisponivel.setText(String.valueOf(livro.getQuantidadeDisponivel()));
		}
	}

	@FXML
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

	public void enterPressedExcluir(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			excluir();
		}
	}
}
