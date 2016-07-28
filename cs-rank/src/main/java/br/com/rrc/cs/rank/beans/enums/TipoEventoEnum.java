package br.com.rrc.cs.rank.beans.enums;

import org.apache.commons.lang.StringUtils;

import br.com.rrc.cs.rank.beans.Evento;
import br.com.rrc.cs.rank.beans.EventoEnd;
import br.com.rrc.cs.rank.beans.EventoKiller;
import br.com.rrc.cs.rank.beans.EventoStart;

public enum TipoEventoEnum {

	START("started") {

		@Override
		public Evento criarEvento(String linha) {
			return new EventoStart(linha);
		}
	},
	END("ended"){

		@Override
		public Evento criarEvento(String linha) {
			return new EventoEnd(linha);
		}

	},
	KILL("killed"){

		@Override
		public Evento criarEvento(String linha) {
			return new EventoKiller(linha);
		}

	};

	private String descricao;

	TipoEventoEnum (String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	
	/**
	 * Realiza busca do {@link TipoEventoEnum} utilizando o parametro
	 * informacaoLinha.
	 * 
	 * Caso o evento informado em forma de {@link String} nao 
	 * constem na lista de enum seram considerados invalidos.
	 * 
	 * Para a instrucao ser valida ela nao pode ser nula, vazia
	 * 
	 * @param informacaoLinha
	 * @return
	 * @throws IllegalAccessException
	 */
	public static TipoEventoEnum buscaTipoEvento (String linha)  {
		
		if (StringUtils.isBlank(linha)) {
			throw new IllegalArgumentException(linha);
		}

		for (TipoEventoEnum tipoEvento : TipoEventoEnum.values()) {

			if (StringUtils.contains(linha, tipoEvento.descricao)){
				return tipoEvento;
			}
		}
		throw new IllegalArgumentException(linha);
	}
	
	public abstract Evento criarEvento(String linha);

}