package banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import dao.DAO;
import javafx.concurrent.Task;
import javafx.scene.control.Button;

public class CreateDatabase extends Task<Void> {
	
	public static final String END_POINT = "CreateDatabase";

	private static final Logger log = Logger.getLogger(CreateDatabase.END_POINT);

	DAO dao = new DAO();
	Button button;

	public CreateDatabase(Button button) {
		this.button = button;
	}

	public void createTableUsuario() {
		try {
			
			log.info(END_POINT + "/criartabelausuario -> Inicio");
			
			Connection conexao = dao.conexaoUsuario();
			String sql = "CREATE TABLE usuario(_id serial NOT NULL,\r\n" + 
					"					email character varying(50) not null,\r\n" + 
					"					empresa character varying(50) not null,\r\n" + 
					"					cpf character varying(50) not null,\r\n" + 
					"					senha character varying(50) not null,\r\n" + 
					"					lembrarSenha boolean default false,\r\n" + 
					"					CONSTRAINT usuario_pkey PRIMARY KEY (_id)) ";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.executeQuery();
			
			log.info(END_POINT + "/criartabelausuario -> Fim");
			
		} catch (Exception e) {

		} finally {
			this.updateProgress(100, 100);
		}
	}

	@Override
	protected Void call() throws Exception {
		try {
			
			log.info(END_POINT + "/criardatabase -> Inicio");
			
			this.updateProgress(10, 100);
			String url = "jdbc:postgresql://localhost:5432/";
			String usuario = "postgres";
			String senha = "postgres";

			Class.forName("org.postgresql.Driver");
			Connection conexao = DriverManager.getConnection(url, usuario, senha);
			PreparedStatement stmt = conexao.prepareStatement("CREATE DATABASE controlib");
			stmt.execute();
			this.updateProgress(20, 100);
			
			log.info(END_POINT + "/criardatabase -> Fim");

		} catch (Exception e) {

		} finally {
			this.updateProgress(10, 100);
			createTableUsuario();
			button.setVisible(true);
		}

		return null;
	}
}
