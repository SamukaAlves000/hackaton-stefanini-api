package com.stefanini.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;

@Entity
@Table(name = "tb_pessoa_perfil")
public class PessoaPerfil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "co_seq_pessoal_perfil")
	private Long id;

	@JsonIgnore
	@Column(name = "co_seq_perfil", insertable = false, updatable = false)
	private Long idPerfil;
	@JsonIgnore
	@Column(name = "co_seq_pessoa", insertable = false, updatable = false)
	private Long idPessoa;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "co_seq_perfil", referencedColumnName = "co_seq_perfil", nullable = false)
	private Perfil perfil;
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "co_seq_pessoa", referencedColumnName = "co_seq_pessoa", nullable = false)
	private Pessoa pessoa;

	public PessoaPerfil() {
		// TODO Auto-generated constructor stub
	}

	public PessoaPerfil(Perfil perfil, Pessoa pessoa) {

		this.perfil = perfil;
		this.pessoa = pessoa;
	}

	public Long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}