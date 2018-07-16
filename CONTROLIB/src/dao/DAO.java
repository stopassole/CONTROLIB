package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
	
	public Connection conexaoUsuario() throws Exception  {
		String url ="jdbc:postgresql://localhost:5432/catalogolivros";
        String usuario="postgres";
        String senha = "postgres";

        Class.forName("org.postgresql.Driver");
        Connection conexao = DriverManager.getConnection(url,usuario,senha);

        return conexao;
	}

}
