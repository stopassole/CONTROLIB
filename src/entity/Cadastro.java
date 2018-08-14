package entity;

public class Cadastro {
	private String _id;
	private String email;
	private String empresa;
	private String cpf;
	private String senha;
	private Boolean lembraSenha;
	private String dataCadastro;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
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

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Cadastro(String _id, String email, String empresa, String cpf, String senha, Boolean lembraSenha,
			String dataCadastro) {
		super();
		this._id = _id;
		this.email = email;
		this.empresa = empresa;
		this.cpf = cpf;
		this.senha = senha;
		this.lembraSenha = lembraSenha;
		this.dataCadastro = dataCadastro;
	}
}
