package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import resource.Inicio;

public class ListEmprestimosController extends DashboardController {
	@FXML
	private TextField idPesquisar;
	@FXML
	private Button btnPesquisar;
	@FXML
	private Button btnNovoEmprestimo;
	@FXML
	private TextField idDataInicio;
	@FXML
	private TextField idDataFim;
	
	@FXML
	public void novoEmprestimo() throws Exception {
		//InfoUsuarioController.idUsuarioEditar = null;
		Parent root = FXMLLoader.load(getClass().getResource("/view/CadastroEmprestimo.fxml"));
		Scene scene = new Scene(root);
		Inicio.myStage.setScene(scene);
	}
	
	public void enterPressedNovoEmprestimo(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			novoEmprestimo();
		}
	}

	public void enterPressedPesquisar(KeyEvent e) throws Exception {
		if (e.getCode().toString().equals("ENTER")) {
			//pesquisar();
		}
	}
}
