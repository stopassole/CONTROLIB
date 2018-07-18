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

	public void alterarSenha(String usuario, String cpf, String senhaCriptografada) throws Exception {
		log.info(END_POINT + "/alterarsenha -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement("UPDATE usuario SET senha = ? WHERE cpf = ? AND usuario = ?");

		stmt.setString(1, senhaCriptografada);
		stmt.setString(2, cpf);
		stmt.setString(3, usuario);

		stmt.executeUpdate();

		log.info(END_POINT + "/alterarsenha -> Fim");
	}
	
	public String getValida(String usuario, String cpf) throws Exception {
		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement("SELECT _id FROM usuario WHERE usuario.usuario= ? AND usuario.cpf = ?;");
		stmt.setString(1, usuario);
		stmt.setString(2, cpf);

		ResultSet rs = stmt.executeQuery();

		String valor = null;
		if (rs.next()) {
			valor = rs.getString("_id");
		}
		return valor; 
	}

	public String getValidaUsuario(String usuario, String senha) throws Exception {
		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement("SELECT _id FROM usuario WHERE usuario.usuario= ? AND usuario.senha = ?;");
		stmt.setString(1, usuario);
		stmt.setString(2, senha);

		ResultSet rs = stmt.executeQuery();

		String valor = null;
		if (rs.next()) {
			valor = rs.getString("_id");
		}
		return valor; 
	}
}
