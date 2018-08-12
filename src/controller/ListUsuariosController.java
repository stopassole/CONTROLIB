package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dao.UsuarioDAO;
import entity.Usuario;
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

public class ListUsuariosController extends DashboardController implements Initializable {
	@FXML
	private Button idSair;
	@FXML
	private Button btnNovoUsuario;
	@FXML
	private Button btnPesquisar;
	@FXML
	private TextField idPesquisar;
	@FXML
	private TableView<Usuario> tbUsuarios;
	@FXML
	private TableColumn<Usuario, String> columnNome;
	@FXML
	private TableColumn<Usuario, String> columnEmail;
	@FXML
	private TableColumn<Usuario, String> columnTipo;
	@FXML
	private TableColumn<Usuario, ImageView> columnImage;

	private List<Usuario> usuarios = new ArrayList<>();

	public static String idUsuario = null;

	CadastroUsuarioController cadastroUsuario = new CadastroUsuarioController();

	@FXML
	private void novoUsuario() throws Exception {
		InfoUsuarioController.idUsuarioEditar = null;
		Parent root = FXMLLoader.load(getClass().getResource("/view/CadastroUsuario.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idSair.setVisible(false);
		populaTabela();
		confTabela();
	}

	public void confTabela() {
		columnNome.setCellValueFactory(celula -> new SimpleStringProperty(
				celula.getValue().getNome() + " " + celula.getValue().getSobrenome()));
		columnEmail.setCellValueFactory(celula -> {
			idUsuario = celula.getValue().get_id();
			return new SimpleStringProperty(celula.getValue().getEmail());
		});
		columnTipo.setCellValueFactory(celula -> new SimpleStringProperty(celula.getValue().getTipo()));

		columnImage.setCellFactory(new Callback<TableColumn<Usuario, ImageView>, TableCell<Usuario, ImageView>>() {
			@Override
			public TableCell<Usuario, ImageView> call(TableColumn<Usuario, ImageView> columnImage) {
				return new TableCell<Usuario, ImageView>() {
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
							button.setId(idUsuario);
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
					idUsuario = button.getId();
					root = FXMLLoader.load(getClass().getResource("/view/InfoUsuario.fxml"));
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
		UsuarioDAO dao = new UsuarioDAO();
		try {
			usuarios = dao.buscarUsuarios();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!usuarios.isEmpty()) {
			tbUsuarios.getItems().addAll(usuarios);
		}
	}

	public void pesquisar() throws Exception {
		UsuarioDAO dao = new UsuarioDAO();
		if (!idPesquisar.getText().isEmpty()) {
			usuarios = dao.buscarUsuariosFiltro(idPesquisar.getText());
		} else {
			usuarios = dao.buscarUsuarios();
		}
		tbUsuarios.getItems().setAll(usuarios);
		confTabela();
	}

	public void enterPressedNovoUsuario(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			novoUsuario();
		}
	}

	public void enterPressedPesquisar(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			pesquisar();
		}
	}
	
	@FXML
	public void change() {
		if(idSair.isVisible()) {
			idSair.setVisible(false);
		}else {
			idSair.setVisible(true);
		}

	}
}
