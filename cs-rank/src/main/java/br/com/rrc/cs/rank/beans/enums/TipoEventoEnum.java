package br.com.rrc.cs.rank.beans.enums;

import org.apache.commons.lang.StringUtils;

public enum TipoEventoEnum {

	START("started"),
	END("ended"),
	KILL("killed");

	private String descricao;

	TipoEventoEnum (String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static TipoEventoEnum buscaTipoEvento (String linha) {

		for (TipoEventoEnum tipoEvento : TipoEventoEnum.values()) {

			if (StringUtils.contains(linha, tipoEvento.descricao)){
				return tipoEvento;
			}
		}
		throw new IllegalArgumentException(linha);
	}

}