package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import entity.Usuario;

public class UsuarioDAO {
	
	public static final String END_POINT = "UsuarioDAO";

	private static final Logger log = Logger.getLogger(UsuarioDAO.END_POINT);

	DAO dao = new DAO();

	public int buscarUsuario() throws Exception {
		
		log.info(END_POINT + "/buscarusuario -> Inicio");
		
		Connection conexao = dao.conexaoUsuario();
		String sql = "SELECT count(*) FROM usuario";
		PreparedStatement stmt = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet resultSet = stmt.executeQuery();
		int cont = 0;
		if (resultSet.next()) {
			cont = resultSet.getInt(1);
		}
		log.info(END_POINT + "/buscarusuario -> Fim");
		
		return cont;
	}

	public void salvarUsuario(Usuario usuario) throws Exception {
		
		log.info(END_POINT + "/salvarusuario -> Inicio");
		
		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement("INSERT INTO Usuario(usuario,cpf,senha) VALUES (?, ?, ?)");
		stmt.setString(1, usuario.getUsuario());
		stmt.setString(2, usuario.getCpf());
		stmt.setString(3, usuario.getSenha());

		stmt.executeUpdate();
		
		log.info(END_POINT + "/salvarusuario -> Fim");
	}
}
