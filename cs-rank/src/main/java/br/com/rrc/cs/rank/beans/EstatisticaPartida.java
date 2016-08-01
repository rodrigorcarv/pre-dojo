package br.com.rrc.cs.rank.beans;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import br.com.rrc.cs.rank.service.utils.LogUtil;

public class EstatisticaPartida implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1L;

	private static final LogUtil LOG = LogUtil.getLog(EstatisticaPartida.class);

	private static final String NAO_FOI_POSSIVEL_CALCULAR_A_DURACAO_DA_PARTIDA_DATA_INICIO = "Nao foi possivel calcular a duracao da partida, pois a data incial: %s esta invalida";
	private static final String NAO_FOI_POSSIVEL_CALCULAR_A_DURACAO_DA_PARTIDA_DATA_FIM = "Nao foi possivel calcular a duracao da partida, pois a data incial: %s esta invalida";

	private Duration duracaoPartida;
	private int qtdJogores;
	
	public Duration getDuracaoPartida() {
		return duracaoPartida;
	}

	public int getQtdJogores() {
		return qtdJogores;
	}

	public void calculaDuracaoPartida(LocalDateTime dataInicio, LocalDateTime dataFim) {

		if (dataInicio == null ) {
			LOG.error(NAO_FOI_POSSIVEL_CALCULAR_A_DURACAO_DA_PARTIDA_DATA_INICIO, dataInicio);
			throw new IllegalArgumentException(String.format(NAO_FOI_POSSIVEL_CALCULAR_A_DURACAO_DA_PARTIDA_DATA_INICIO, dataInicio));
		}

		if (dataFim == null ) {
			LOG.error(NAO_FOI_POSSIVEL_CALCULAR_A_DURACAO_DA_PARTIDA_DATA_FIM, dataFim);
			throw new IllegalArgumentException(String.format(NAO_FOI_POSSIVEL_CALCULAR_A_DURACAO_DA_PARTIDA_DATA_FIM, dataFim));
		}

		this.duracaoPartida = Duration.between(dataInicio.toLocalTime(), dataFim.toLocalTime());
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}


}