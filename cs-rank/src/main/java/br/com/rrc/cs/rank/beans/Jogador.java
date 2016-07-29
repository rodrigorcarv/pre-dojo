package br.com.rrc.cs.rank.beans;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author fernandomacedo
 *
 */
public class Jogador implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String nome;
	private int qtdMortes;
	private int qtdAssinatos;
	
	public Jogador(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public int getQtdMortes() {
		return qtdMortes;
	}

	public int getQtdAssinatos() {
		return qtdAssinatos;
	}

	public void adicionadaMortes() {
		qtdMortes++;
	}
	
	public void adicionadaAssassinatos() {
		qtdAssinatos++;
	}
	
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
