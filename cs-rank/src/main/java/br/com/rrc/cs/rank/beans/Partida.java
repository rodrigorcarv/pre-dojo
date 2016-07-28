package br.com.rrc.cs.rank.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Partida implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long numeroPartida; 
	private LocalDateTime dataInicio;
	private LocalDateTime dataFim;
	private Set<Jogador> jogadores = new HashSet<Jogador>() ;
	
	public Partida(Long numeroPartida, LocalDateTime dataInicio) {
		super();
		this.dataInicio = dataInicio;
		this.numeroPartida = numeroPartida;
	}
	
	public Partida(Long numeroPartida, LocalDateTime dataInicio, Set<Jogador> jogadores, LocalDateTime dataFim) {
		super();
		this.dataInicio = dataInicio;
		this.numeroPartida = numeroPartida;
		this.jogadores = jogadores;
		this.dataFim = dataFim;
	}

	public Long getNumeroPartida() {
		return numeroPartida;
	}

	public LocalDateTime getDataInicio() {
		return dataInicio;
	}

	public Set<Jogador> getJogadores() {
		return jogadores;
	}

	public LocalDateTime getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDateTime dataFim) {
		this.dataFim = dataFim;
	}
	
	public void adicionaJogar(Jogador jogador) {
		jogadores.add(jogador);
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
