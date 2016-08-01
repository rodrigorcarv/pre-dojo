package br.com.rrc.cs.rank.beans;

import java.time.LocalDateTime;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class InformacaoLinha {
	
	private LocalDateTime data;
	private Long numeroPartida;
	private String jogadorUm;
	private String jogadorDois;
	private String nomeArma;

	public InformacaoLinha(Long numeroPartida, LocalDateTime data) {
		super();
		this.data = data;
		this.numeroPartida = numeroPartida;
	}

	public InformacaoLinha(LocalDateTime data, String jogadorUm, String jogadorDois, String nomeArma) {
		super();
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
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
