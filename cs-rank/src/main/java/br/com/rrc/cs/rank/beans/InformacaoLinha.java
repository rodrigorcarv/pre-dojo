package br.com.rrc.cs.rank.beans;

import java.time.LocalDateTime;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class InformacaoLinha {
	
	private LocalDateTime data;
	private Long numeroPartida;
	private String jogadorUm;
	private String jogadorDois;
	private String nomeArma;
	
	public InformacaoLinha(LocalDateTime data, Long numeroPartida, String jogadorUm, String jogadorDois, String nomeArma) {
		super();
		this.data = data;
		this.numeroPartida = numeroPartida;
		this.jogadorUm = jogadorUm;
		this.jogadorDois = jogadorDois;
		this.nomeArma = nomeArma;
	}
	
	public InformacaoLinha(Long numeroPartida, LocalDateTime data) {
		super();
		this.data = data;
		this.numeroPartida = numeroPartida;
	}

	public InformacaoLinha(LocalDateTime data, String jogadorUm, String jogadorDois, String nomeArma) {
		this.data = data;
		this.jogadorUm = jogadorUm;
		this.jogadorDois = jogadorDois;
		this.nomeArma = nomeArma;
	}

	public LocalDateTime getData() {
		return data;
	}
	public Long getNumeroPartida() {
		return numeroPartida;
	}
	public String getJogadorUm() {
		return jogadorUm;
	}
	public String getJogadorDois() {
		return jogadorDois;
	}
	public String getNomeArma() {
		return nomeArma;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}