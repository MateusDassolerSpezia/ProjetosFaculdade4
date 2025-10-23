package model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "lojas")
public class Loja {

	@Id
	@Column(name = "cd_loja")
	private int id;
	
	@Column(name = "nm_loja")
	private String nome;
	
	@Column(name = "ds_loja")
	private String descricao;
	
	@ManyToOne
	private ArrayList<Produto> listaProdutos;

	public Loja() {
		super();
		listaProdutos = new ArrayList<Produto>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		if (id <= 0) {
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

	public ArrayList<Produto> getListaProdutos() {
		return listaProdutos;
	}
	
	public void create(Produto produto) {
		listaProdutos.add(produto);
	}
	
}
