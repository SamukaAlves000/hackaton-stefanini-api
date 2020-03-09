package com.stefanini.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
/**
 * @author samukaAlves00
 *
 */
@Entity
@Table(name = "TB_PERFIL")
public class Perfil implements Serializable {
   
    /**
	 * Serializacao da Classe
	 */
	private static final long serialVersionUID = 1L;
	/**
     * ID da Tabela
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CO_SEQ_PERFIL")
    private Long id;
    /**
     *
     */
    @NotNull
    @Column(name = "NO_PERFIL")
    private String nome;
    /**
     *
     */
    @NotNull
    @Column(name = "DS_PERFIL")
    private String descricao;
    /**
     *
     */
    @Column(name = "DT_HORA_INCLUSAO")
    @NotNull
    private LocalDateTime dataHoraInclusao;
    /**
     *
     */
    @Column(name = "DT_HORA_ALTERACAO")
    private LocalDateTime dataHoraAlteracao;
    
    @JsonIgnore
    @OneToMany(mappedBy = "perfil",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private Set<PessoaPerfil> pessoaPerfils;

    public Perfil() {
    }

    public Perfil(@NotNull String nome, @NotNull String descricao, @NotNull LocalDateTime dataHoraInclusao, LocalDateTime dataHoraAlteracao) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataHoraInclusao = dataHoraInclusao;
        this.dataHoraAlteracao = dataHoraAlteracao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataHoraInclusao() {
        return dataHoraInclusao;
    }

    public void setDataHoraInclusao(LocalDateTime dataHoraInclusao) {
        this.dataHoraInclusao = dataHoraInclusao;
    }

    public LocalDateTime getDataHoraAlteracao() {
        return dataHoraAlteracao;
    }

    public void setDataHoraAlteracao(LocalDateTime dataHoraAlteracao) {
        this.dataHoraAlteracao = dataHoraAlteracao;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Perfil other = (Perfil) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "Perfil{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataHoraInclusao=" + dataHoraInclusao +
                ", dataHoraAlteracao=" + dataHoraAlteracao +
                '}';
    }
}
