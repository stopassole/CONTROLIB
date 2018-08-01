package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dao.UsuarioDAO;
import entity.Usuario;
import javafx.beans.property.SimpleStringProperty;
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
		columnNome.setCellValueFactory(celula -> new SimpleStringProperty(celula.getValue().getNome() + " " + celula.getValue().getSobrenome()));
		columnEmail.setCellValueFactory(celula -> new SimpleStringProperty(celula.getValue().getEmail()));
		columnTipo.setCellValueFactory(celula -> new SimpleStringProperty(celula.getValue().getIdTipo()));

		columnImage.setCellFactory(new Callback<TableColumn<Usuario, ImageView>, TableCell<Usuario, ImageView>>() {

			@Override
			public TableCell<Usuario, ImageView> call(TableColumn<Usuario, ImageView> p) {
				ImageView img = new ImageView("./images/Group 131.png");
				img.setFitHeight(50);
				img.setFitWidth(50);

				TableCell<Usuario, ImageView> cell = new TableCell<Usuario, ImageView>() {
					
				};
				cell.setGraphic(img);
				cell.setAlignment(Pos.CENTER);
				return cell;
			}
		});
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
