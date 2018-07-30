package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import resource.Inicio;

public class ListUsuariosController extends DashboardController{

	@FXML
	private Button btnNovoUsuario;
	
	@FXML
	private void novoUsuario() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/CadastroUsuario.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}
}
