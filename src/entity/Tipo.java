package entity;

public class Tipo {
	private String _id;
	private String descricao;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Tipo(String _id, String descricao) {
		this._id = _id;
		this.descricao = descricao;
	}	
	
	@Override
	public String toString() {
		return this.descricao;
	}	
}
