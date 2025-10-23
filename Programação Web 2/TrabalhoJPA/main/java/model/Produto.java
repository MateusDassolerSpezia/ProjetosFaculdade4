package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "produtos")
public class Produto {

	@Id
	@Column(name = "cd_produto")
	private int id;
	
	@Column(name = "nm_produto")
	private String nome;
	
	@Column(name = "ds_produto")
	private String descricao;
	
	@Column(name = "vl_venda")
	private float valorVenda;
	
	@Column(name = "vl_custo")
	private float valorCusto;
	
	@Column(name = "qt_estoque")
	private int estoque;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id <= 0 ) {
			throw new IllegalArgumentException("O id deve ser maior do que zero!");
		}
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (nome.isBlank()) {
			throw new IllegalArgumentException("Nome não pode ser vazio!");
		}
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		if (descricao.isBlank()) {
			throw new IllegalArgumentException("A descrição não pode ser vazio!");
		}
		this.descricao = descricao;
	}

	public float getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(float valorVenda) {
		if (valorVenda < 0) {
			throw new IllegalArgumentException("O preço não pode ser menor que zero!");
		}
		this.valorVenda = valorVenda;
	}

	public float getValorCusto() {
		return valorCusto;
	}

	public void setValorCusto(float valorCusto) {
		if (valorCusto < 0) {
			throw new IllegalArgumentException("O custo não pode ser menor que zero!");
		}
		this.valorCusto = valorCusto;
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		if (estoque < 0) {
			throw new IllegalArgumentException("A quantidade não pode ser menor que zero!");
		}
		this.estoque = estoque;
	}
}
