package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import entity.Usuario;

public class UsuarioDAO {

	public static final String END_POINT = "UsuarioDAO";

	private static final Logger log = Logger.getLogger(UsuarioDAO.END_POINT);

	DAO dao = new DAO();

	public void salvarUsuario(Usuario usuario) throws Exception {
		log.info(END_POINT + "/salvarusuairo -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement("INSERT INTO usuario(nome,sobrenome,endereco,email,telefone,cpf,dataNascimento, idTipo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		stmt.setString(1, usuario.getNome());
		stmt.setString(2, usuario.getSobrenome());
		stmt.setString(3, usuario.getEndereco());
		stmt.setString(4, usuario.getEmail());
		stmt.setString(5, usuario.getTelefone());
		stmt.setString(6, usuario.getCPF());
		SimpleDateFormat dataOriginal = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date date = dataOriginal.parse(usuario.getDataNascimento());
		SimpleDateFormat novaData = new SimpleDateFormat("yyyy-MM-dd");
		stmt.setDate(7, Date.valueOf(novaData.format(date)));
		stmt.setInt(8, Integer.valueOf(usuario.getIdTipo()));

		stmt.executeUpdate();

		log.info(END_POINT + "/salvarusuairo -> Fim");
	}
}