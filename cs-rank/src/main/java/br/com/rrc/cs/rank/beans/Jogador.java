package br.com.rrc.cs.rank.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class Jogador implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String nome;
	private List<String> bonus = new ArrayList<>(); 
	private EstatisticaJogador estatisticaJogador = new EstatisticaJogador();
	
	public Jogador(String nome) {
		super();
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	
	public List<String> getBonus() {
		return bonus;
	}

	public void setBonus(List<String> bonus) {
		this.bonus = bonus;
	}

	public EstatisticaJogador getEstatisticaJogador() {
		return estatisticaJogador;
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
