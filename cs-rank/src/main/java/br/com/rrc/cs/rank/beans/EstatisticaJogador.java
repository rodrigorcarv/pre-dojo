package br.com.rrc.cs.rank.beans;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import br.com.rrc.cs.rank.exceptions.PartidaInvalidaException;
import br.com.rrc.cs.rank.service.utils.LogUtil;

public class EstatisticaJogador {
	
	private static final LogUtil LOG = LogUtil.getLog(EstatisticaJogador.class);
	
	private static final String NAO_FOI_POSSIVEL_INCLUIR_A_ARMA_UTILIZADA = "Nao foi possivel incluir a arma: %s, pois a mesma nao esta preenchida corretamente";;
	private Map<String, Integer> armasUtilizadas = new HashMap<>();
	private int qtdMortes;
	private int qtdAssinatos;
	
	public int getQtdMortes() {
		return qtdMortes;
	}

	public int getQtdAssinatos() {
		return qtdAssinatos;
	}

	public void adicionadaMortes() {
		qtdMortes++;
	}
	
	public void adicionadaAssassinatos() {
		qtdAssinatos++;
	}
	
	public Map<String, Integer> getArmasUtilizadas() {
		return armasUtilizadas;
	}

	public void setArmasUtilizadas(Map<String, Integer> armasUtilizadas) {
		this.armasUtilizadas = armasUtilizadas;
	}

	public void adicionarArmaUtilizada(String arma) {

		if (StringUtils.isBlank(arma)) {
			LOG.error(NAO_FOI_POSSIVEL_INCLUIR_A_ARMA_UTILIZADA, arma);
			throw new PartidaInvalidaException(String.format(NAO_FOI_POSSIVEL_INCLUIR_A_ARMA_UTILIZADA, arma));
		}
		
		if (armasUtilizadas.containsKey(arma)) {
			Integer qtdMortesComEstaArma = armasUtilizadas.get(arma);
			armasUtilizadas.put(arma, ++qtdMortesComEstaArma);
		} else {
			Integer qtdMortesComEstaArma = new Integer(1); 
			armasUtilizadas.put(arma, qtdMortesComEstaArma);
		}
	}
}
