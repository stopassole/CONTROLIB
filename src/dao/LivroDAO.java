package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import entity.Livro;

public class LivroDAO {

	public static final String END_POINT = "LivroDAO";

	private static final Logger log = Logger.getLogger(LivroDAO.END_POINT);

	DAO dao = new DAO();

	public void salvarLivro(Livro livro) throws Exception {
		log.info(END_POINT + "/salvarlivro -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement(
				"INSERT INTO livro(nome,autor,genero,editora,quantidadeTotal,quantidadeDisponivel) VALUES (?, ?, ?, ?, ?, ?)");
		stmt.setString(1, livro.getNome());
		stmt.setString(2, livro.getAutor());
		stmt.setString(3, livro.getGenero());
		stmt.setString(4, livro.getEditora());
		stmt.setInt(5, livro.getQuantidadeTotal());
		// A quantidade dispon�vel Inicial � a mesma da quantidade total;
		stmt.setInt(6, livro.getQuantidadeTotal());

		stmt.executeUpdate();

		log.info(END_POINT + "/salvarlivro -> Fim");

	}

	public void editarLivro(String idLivroEditar, Livro livro) throws Exception {
		log.info(END_POINT + "/editarlivro -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement(
				"UPDATE livro SET nome = ?, autor = ?, genero = ?, editora = ?, quantidadeTotal = ?, quantidadeDisponivel = ? WHERE _id = \'"
						+ idLivroEditar + "\';");
		stmt.setString(1, livro.getNome());
		stmt.setString(2, livro.getAutor());
		stmt.setString(3, livro.getGenero());
		stmt.setString(4, livro.getEditora());
		stmt.setInt(5, livro.getQuantidadeTotal());

		stmt.executeUpdate();

		log.info(END_POINT + "/editarlivro -> Fim");
	}

	public void editarQuantidadeDisponivel(String idLivroEditar, Integer quantidade) throws Exception {
		log.info(END_POINT + "/editarquatidadedisponivel -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao
				.prepareStatement("UPDATE livro SET quantidadeDisponivel = ? WHERE _id = \'" + idLivroEditar + "\';");
		stmt.setInt(1, quantidade);

		stmt.executeUpdate();

		log.info(END_POINT + "/editarquatidadedisponivel -> Fim");
	}

	public int validaLivro(Livro livro) throws Exception {
		log.info(END_POINT + "/validalivro -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		String sql = "SELECT count(*) FROM livro WHERE livro.nome= \'" + livro.getNome() + "\'AND livro.autor =\'"
				+ livro.getAutor() + "\'AND livro.quantidadetotal =\'" + livro.getQuantidadeTotal()
				+ "\'AND livro.deletado =\'" + false + "\';";
		PreparedStatement stmt = conexao.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet resultSet = stmt.executeQuery();

		int cont = 0;
		if (resultSet.next()) {
			cont = resultSet.getInt(1);
		}

		log.info(END_POINT + "/validalivro -> Fim");

		return cont;
	}

	public List<Livro> buscarLivros() throws Exception {
		log.info(END_POINT + "/buscarlivros -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM livro WHERE deletado = false;");
		ResultSet rs = stmt.executeQuery();

		List<Livro> list = new ArrayList<>();

		while (rs.next()) {
			Livro livro = new Livro(null, null, null, null, null, null, null, null, null);

			livro.set_id(rs.getString("_id"));
			livro.setNome(rs.getString("nome"));
			livro.setAutor(rs.getString("autor"));
			livro.setGenero(rs.getString("genero"));
			livro.setEditora(rs.getString("editora"));
			livro.setQuantidadeTotal(rs.getInt("quantidadetotal"));
			livro.setQuantidadeDisponivel(rs.getInt("quantidadedisponivel"));
			livro.setDataCadastro(String.valueOf(rs.getDate("datacadastro")));
			livro.setDeletado(rs.getBoolean("deletado"));

			list.add(livro);
		}

		log.info(END_POINT + "/buscarlivros -> Fim");

		return list;
	}

	public List<Livro> buscarLivrosFiltro(String text) throws Exception {
		log.info(END_POINT + "/buscarlivrosfiltro -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao
				.prepareStatement("SELECT * FROM livro WHERE deletado = false and livro.nome LIKE '%" + text
						+ "%' or livro.autor LIKE '%" + text + "%' or livro.genero LIKE  '%" + text + ";");
		ResultSet rs = stmt.executeQuery();

		List<Livro> list = new ArrayList<>();

		while (rs.next()) {
			Livro livro = new Livro(null, null, null, null, null, null, null, null, null);

			livro.set_id(rs.getString("_id"));
			livro.setNome(rs.getString("nome"));
			livro.setAutor(rs.getString("autor"));
			livro.setGenero(rs.getString("genero"));
			livro.setEditora(rs.getString("editora"));
			livro.setQuantidadeTotal(rs.getInt("quantidadetotal"));
			livro.setQuantidadeDisponivel(rs.getInt("quantidadedisponivel"));
			livro.setDataCadastro(String.valueOf(rs.getDate("datacadastro")));
			livro.setDeletado(rs.getBoolean("deletado"));

			list.add(livro);
		}
		log.info(END_POINT + "/buscarlivrosfiltro -> Fim");

		return list;
	}

	public void excluirLivro(String idLivro) throws Exception {
		log.info(END_POINT + "/excluirlivro -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao
				.prepareStatement("UPDATE livro SET deletado = true WHERE _id = \'" + idLivro + "\';");

		stmt.executeUpdate();

		log.info(END_POINT + "/excluirlivro -> Fim");
	}

	public Livro getByIdLivro(String idLivro) throws Exception {
		log.info(END_POINT + "/buscarlivrobyid -> Inicio");

		Connection conexao = dao.conexaoUsuario();
		PreparedStatement stmt = conexao
				.prepareStatement("SELECT * FROM livro WHERE deletado = false and _id = \'" + idLivro + "\';");
		ResultSet rs = stmt.executeQuery();

		Livro livro = new Livro(null, null, null, null, null, null, null, null, null);

		if (rs.next()) {

			livro.set_id(rs.getString("_id"));
			livro.setNome(rs.getString("nome"));
			livro.setAutor(rs.getString("autor"));
			livro.setGenero(rs.getString("genero"));
			livro.setEditora(rs.getString("editora"));
			livro.setQuantidadeTotal(rs.getInt("quantidadetotal"));
			livro.setQuantidadeDisponivel(rs.getInt("quantidadedisponivel"));
			livro.setDataCadastro(String.valueOf(rs.getDate("datacadastro")));
			livro.setDeletado(rs.getBoolean("deletado"));
		}

		log.info(END_POINT + "/buscarlivrobyid  -> Fim");
		return livro;
	}

}
