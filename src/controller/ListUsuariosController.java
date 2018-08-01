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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import resource.Inicio;

public class ListUsuariosController extends DashboardController implements Initializable {

	@FXML
	private Button btnNovoUsuario;

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

	@FXML
	private void novoUsuario() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/CadastroUsuario.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		populaTabela();
		confTabela();
	}

	public void confTabela() {
		columnNome.setCellValueFactory(celula -> new SimpleStringProperty(
				celula.getValue().getNome() + " " + celula.getValue().getSobrenome()));
		columnEmail.setCellValueFactory(celula -> new SimpleStringProperty(celula.getValue().getEmail()));
		columnTipo.setCellValueFactory(celula -> new SimpleStringProperty(celula.getValue().getIdTipo()));

		columnImage.setCellFactory(new Callback<TableColumn<Usuario, ImageView>, TableCell<Usuario, ImageView>>() {
			@Override
			public TableCell<Usuario, ImageView> call(TableColumn<Usuario, ImageView> columnImage) {
				return new TableCell<Usuario, ImageView>() {
					ImageView img = new ImageView("./images/Group 131.png");
					Button button = new Button();
					{
						img.setFitHeight(50);
						img.setFitWidth(50);
						button.setGraphic(img);
						button.setMaxSize(50, 50);
						button.setStyle("-fx-background-color:transparent; -fx-cursor:hand");
						button.setOnMouseClicked(visualizar(button));
					}

					public void updateItem(ImageView img, boolean empty) {
						super.updateItem(img, empty);
						if (!empty) {
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

					// String id = tbUsuarios.getSelectionModel().getSelectedItem().get_id();
					// System.out.println("AQui " + id);
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
}