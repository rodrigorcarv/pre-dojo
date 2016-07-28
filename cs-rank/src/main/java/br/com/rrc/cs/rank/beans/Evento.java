package br.com.rrc.cs.rank.beans;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import br.com.rrc.cs.rank.beans.enums.TipoEventoEnum;

public abstract class Evento {
	
	protected InformacaoLinha informacaoLinha;
	private String linha;
	private TipoEventoEnum tipoEventoEnum;

	public Evento(String linha, TipoEventoEnum tipoEventoEnum ) {
		this.linha = linha;
		this.tipoEventoEnum = tipoEventoEnum;
	}
	
	public String getLinha() {
		return linha;
	}

	public InformacaoLinha getInformacaoLinha() {
		return informacaoLinha;
	}
	
	public TipoEventoEnum getTipoEventoEnum() {
		return tipoEventoEnum;
	}

	public abstract InformacaoLinha analisaEvento();
	
	/**
	 * Obtem o {@link Matcher} com base nos paramentros informados
	 * 
	 * @param pattern {@link Pattern} 
	 * @param token texto a ser virificado se atendo o  {@link Pattern} 
	 * @return Retorna o {@link o Matcher} do token
	 * 
	 * @throws IllegalArgumentException Exececao sera disparada caso nao ocorra o {@link Matcher} 
	 *                                  do token.
	 */
	protected Matcher getMatcher(Pattern pattern, String token) {

		final Matcher matcher = pattern.matcher(token);
		
		if (!matcher.matches()) {
			throw new IllegalArgumentException(String.format("InformacaoLinha %s esta invalida", token));
		}
		
		return matcher;
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
