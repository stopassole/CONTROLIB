package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import resource.Inicio;

public class DashboardController {
	@FXML
	private Button btnEmprestimos;
	@FXML
	private Button btnLivros;
	@FXML
	private Button btnUsuarios;
	
	@FXML
	private void menuUsuarios() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/ListUsuario.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}
	
	@FXML
	private void menuEmprestimos() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/ListEmprestimos.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}
	
	@FXML
	private void menuLivros() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/ListLivros.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}

}
