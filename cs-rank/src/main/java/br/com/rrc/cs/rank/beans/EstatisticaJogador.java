package br.com.rrc.cs.rank.beans;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import br.com.rrc.cs.rank.exceptions.PartidaInvalidaException;
import br.com.rrc.cs.rank.service.utils.LogUtil;

public class EstatisticaJogador {
	
	private static final LogUtil LOG = LogUtil.getLog(EstatisticaJogador.class);
	
	private static final String NAO_FOI_POSSIVEL_INCLUIR_A_ARMA_UTILIZADA = "Nao foi possivel incluir a arma: %s, pois a mesma nao esta preenchida corretamente";;
	
	private Map<String, Integer> armasUtilizadas = new HashMap<>();
	private String armaPredila;
	private int qtdMortes;
	private int qtdAssinatos;
	private int maiorSequenciaAssinatosSemMorte;
	private int qtdAssassinatosSemMorte;
	
	public static LogUtil getLog() {
		return LOG;
	}

	public String getArmaPredila() {
		
		if (armasUtilizadas != null && armasUtilizadas.values().size() > 0) {
			
			Entry<String, Integer> arma = armasUtilizadas
					.entrySet()
					.stream()
					.max((s1, s2) ->  s1.getValue().compareTo(s2.getValue()))
					.orElse(null);
			arma.getKey();
			armaPredila = arma.getKey();
		}
		
		return armaPredila;
	}

	public int getQtdMortes() {
		return qtdMortes;
	}

	public int getQtdAssinatos() {
		return qtdAssinatos;
	}

	public Map<String, Integer> getArmasUtilizadas() {
		return armasUtilizadas;
	}

	public void adicionadaMortes() {
		qtdAssassinatosSemMorte = 0;
		qtdMortes++;
	}
	
	public void adicionadaAssassinatos() {
		qtdAssinatos++;
		incrementaSequenciaAssinatosSemMorte();
	}
	
	public int getMaiorSequenciaAssinatosSemMorte() {
		return maiorSequenciaAssinatosSemMorte;
	}

	/**
	 * Metodo que adiciona todas as armas utilizadas pelo {@link Jogador} e incrementa a 
	 * quantidade de assinatos efetudo pelo {@link Jogador} com esta arma.
	 * 
	 * @param arma arma
	 */
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
	
	public void incrementaSequenciaAssinatosSemMorte() {
		
		if (qtdAssassinatosSemMorte >= maiorSequenciaAssinatosSemMorte) {
			maiorSequenciaAssinatosSemMorte++;
		} 
		
		qtdAssassinatosSemMorte++;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
}