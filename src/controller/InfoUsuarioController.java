package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.UsuarioDAO;
import entity.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import resource.Inicio;

public class InfoUsuarioController extends DashboardController implements Initializable{
	
	@FXML
	private Button bntCancelar;
	UsuarioDAO dao = new UsuarioDAO();
	
	@FXML
	public void fechar() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/ListUsuario.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}
	
	@FXML
	public void editar() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/CadastroUsuario.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String idUsuario = ListUsuariosController.idUsuario;
		Usuario usuario = dao.getByIdUsuario(idUsuario);
	}
	
	
}
