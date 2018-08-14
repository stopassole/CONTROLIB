package entity;

public class Emprestimo {
	private String _id;
	private String idUsuario;
	private String idLivro;
	private String dataEmprestimo;
	private String dataVencimento;
	private String dataCadastro;
	private Boolean deletado;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getIdLivro() {
		return idLivro;
	}

	public void setIdLivro(String idLivro) {
		this.idLivro = idLivro;
	}

	public String getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(String dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public String getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Boolean getDeletado() {
		return deletado;
	}

	public void setDeletado(Boolean deletado) {
		this.deletado = deletado;
	}

	public Emprestimo(String _id, String idUsuario, String idLivro, String dataEmprestimo, String dataVencimento,
			String dataCadastro, Boolean deletado) {
		this._id = _id;
		this.idUsuario = idUsuario;
		this.idLivro = idLivro;
		this.dataEmprestimo = dataEmprestimo;
		this.dataVencimento = dataVencimento;
		this.dataCadastro = dataCadastro;
		this.deletado = deletado;
	}
}
