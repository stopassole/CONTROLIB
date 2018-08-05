package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dao.LivroDAO;
import entity.Livro;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import resource.Inicio;

public class ListLivrosController extends DashboardController implements Initializable {

	@FXML
	private Button btnNovoLivro;
	@FXML
	private Button btnPesquisar;
	@FXML
	private TextField idPesquisar;
	@FXML
	private TableView<Livro> tbLivros;
	@FXML
	private TableColumn<Livro, String> columnTitulo;
	@FXML
	private TableColumn<Livro, String> columnAutor;
	@FXML
	private TableColumn<Livro, String> columnGenero;
	@FXML
	private TableColumn<Livro, ImageView> columnDisponivel;
	@FXML
	private TableColumn<Livro, ImageView> columnImage;

	private List<Livro> livros = new ArrayList<>();

	public static String idLivro = null;

	private static Integer disponivel = null;

	CadastroLivroController cadastroLivro = new CadastroLivroController();

	@FXML
	private void novoLivro() throws Exception {
		InfoLivroController.idLivroEditar = null;
		Parent root = FXMLLoader.load(getClass().getResource("/view/CadastroLivro.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		populaTabela();
		confTabela();
	}

	public void confTabela() {
		columnTitulo.setCellValueFactory(celula -> new SimpleStringProperty(celula.getValue().getNome()));
		columnAutor.setCellValueFactory(celula -> {
			idLivro = celula.getValue().get_id();
			disponivel = celula.getValue().getQuantidadeDisponivel();
			return new SimpleStringProperty(celula.getValue().getAutor());
		});
		columnGenero.setCellValueFactory(celula -> new SimpleStringProperty(celula.getValue().getGenero()));

		columnDisponivel.setCellFactory(new Callback<TableColumn<Livro, ImageView>, TableCell<Livro, ImageView>>() {
			@Override
			public TableCell<Livro, ImageView> call(TableColumn<Livro, ImageView> columnImage) {
				return new TableCell<Livro, ImageView>() {
					ImageView img1 = new ImageView("./images/Group 138.png");
					ImageView img2 = new ImageView("./images/Group 140.png");
					Button button = new Button();
					{
						button.setMaxSize(50, 50);
						button.setStyle("-fx-background-color:transparent;");
					}

					public void updateItem(ImageView img, boolean empty) {
						super.updateItem(img, empty);
						if (!empty) {
							if (disponivel > 0) {
								button.setGraphic(img1);
							} else {
								button.setGraphic(img2);
							}
							button.setId(idLivro);
							setGraphic(button);
							setAlignment(Pos.CENTER);
						}
					}
				};
			}
		});

		columnImage.setCellFactory(new Callback<TableColumn<Livro, ImageView>, TableCell<Livro, ImageView>>() {
			@Override
			public TableCell<Livro, ImageView> call(TableColumn<Livro, ImageView> columnImage) {
				return new TableCell<Livro, ImageView>() {
					ImageView img = new ImageView("./images/Group 131.png");
					Button button = new Button();
					{
						button.setGraphic(img);
						button.setMaxSize(50, 50);
						button.setStyle("-fx-background-color:transparent; -fx-cursor:hand");
						button.setOnMouseClicked(visualizar(button));
					}

					public void updateItem(ImageView img, boolean empty) {
						super.updateItem(img, empty);
						if (!empty) {
							button.setId(idLivro);
							setGraphic(button);
							setAlignment(Pos.CENTER);
						}
					}
				};
			}
		});
	}

	private EventHandler<? super MouseEvent> visualizar(Button button) {
		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Parent root = null;
				try {
					idLivro = button.getId();
					root = FXMLLoader.load(getClass().getResource("/view/InfoLivro.fxml"));
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				Scene scene = new Scene(root);
				Inicio.myStage.setScene(scene);
			}
		});
		return null;
	}

	public void populaTabela() {
		LivroDAO dao = new LivroDAO();
		try {
			livros = dao.buscarLivros();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!livros.isEmpty()) {
			tbLivros.getItems().addAll(livros);
		}
	}

	public void pesquisar() throws Exception {
		LivroDAO dao = new LivroDAO();
		if (!idPesquisar.getText().isEmpty()) {
			livros = dao.buscarLivrosFiltro(idPesquisar.getText());
		} else {
			livros = dao.buscarLivros();
		}
		tbLivros.getItems().setAll(livros);
		confTabela();
	}

	public void enterPressedNovoLivro(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			novoLivro();
		}
	}

	public void enterPressedPesquisar(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			pesquisar();
		}
	}
}
