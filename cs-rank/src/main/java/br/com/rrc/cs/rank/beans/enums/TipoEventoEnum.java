package br.com.rrc.cs.rank.beans.enums;

import org.apache.commons.lang.StringUtils;

import br.com.rrc.cs.rank.beans.Evento;
import br.com.rrc.cs.rank.beans.EventoEnd;
import br.com.rrc.cs.rank.beans.EventoKiller;
import br.com.rrc.cs.rank.beans.EventoStart;
import br.com.rrc.cs.rank.service.utils.LogUtil;

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
	
	private static final String NAO_FOI_ENCONTRADO_UM_TIPO_DE_EVENTO_PARA_A_LINHA_INFORMADA = "Nao foi encontrado um tipo de evento para a linha informada: %s";
	private static final String A_LINHHA_INFORMADA_ESTA_INVALIDA = "A linhha informada esta invalida: %s";

	private static final LogUtil LOG = LogUtil.getLog(TipoEventoEnum.class);

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
		
		LOG.debug("linha: ", linha);
		
		if (StringUtils.isBlank(linha)) {
			LOG.error(A_LINHHA_INFORMADA_ESTA_INVALIDA, linha);
			throw new IllegalArgumentException(String.format(A_LINHHA_INFORMADA_ESTA_INVALIDA, linha));
		}

		for (TipoEventoEnum tipoEvento : TipoEventoEnum.values()) {

			if (StringUtils.contains(linha, tipoEvento.descricao)){
				return tipoEvento;
			}
		}
		LOG.error(NAO_FOI_ENCONTRADO_UM_TIPO_DE_EVENTO_PARA_A_LINHA_INFORMADA, linha);
		throw new IllegalArgumentException(String.format(NAO_FOI_ENCONTRADO_UM_TIPO_DE_EVENTO_PARA_A_LINHA_INFORMADA, linha));
	}
	
	public abstract Evento criarEvento(String linha);

}