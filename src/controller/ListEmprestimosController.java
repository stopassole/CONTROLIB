package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import resource.Inicio;

public class ListEmprestimosController extends DashboardController implements Initializable{
	@FXML
	private Button idSair;
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idSair.setVisible(false);
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

