package br.com.rrc.cs.rank.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Partida implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long numeroPartida; 
	private LocalDateTime dataInicio;
	private LocalDateTime dataFim;
	private List<Jogador> jogadores;
	
	public Partida(Long numeroPartida, LocalDateTime dataInicio) {
		super();
		this.dataInicio = dataInicio;
		this.numeroPartida = numeroPartida;
	}
	
	public Partida(Long numeroPartida, LocalDateTime dataInicio, List<Jogador> jogadores, LocalDateTime dataFim) {
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

	public List<Jogador> getJogadores() {
		return jogadores;
	}

	public LocalDateTime getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDateTime dataFim) {
		this.dataFim = dataFim;
	}

	public void setJogadores(List<Jogador> jogadores) {
		this.jogadores = jogadores;
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
