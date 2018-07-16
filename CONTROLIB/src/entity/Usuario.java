package entity;

public class Usuario {
	private String _id;
	private String usuario;
	private String cpf;
	private String senha;
	private Boolean lembraSenha;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean getLembraSenha() {
		return lembraSenha;
	}

	public void setLembraSenha(Boolean lembraSenha) {
		this.lembraSenha = lembraSenha;
	}

	public Usuario(String _id, String usuario, String cpf, String senha, Boolean lembraSenha) {
		super();
		this._id = _id;
		this.usuario = usuario;
		this.cpf = cpf;
		this.senha = senha;
		this.lembraSenha = lembraSenha;
	}
}
