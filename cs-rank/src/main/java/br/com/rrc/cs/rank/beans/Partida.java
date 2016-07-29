package br.com.rrc.cs.rank.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import br.com.rrc.cs.rank.exceptions.PartidaInvalidaException;

public class Partida implements Serializable{

	private static final String ASSASSINO_BLACK_LIST_WORLD = "<WORLD>";
	private static final String NAO_FOI_POSSIVEL_INCLUIR_A_VITIMA = "Nao foi possivel incluir a vitima: %s";
	private static final String NAO_FOI_POSSIVEL_FINALIZAR_A_PARTIDA_POIS_A_MESMA_NAO_FOI_INICIADA = "Não foi possível finalizar a partida %s, pois a mesma não foi iniciada";

	private static final long serialVersionUID = 1L;
	private static final String NAO_FOI_POSSIVEL_INCLUIR_O_ASSASSINO = null;
	
	private Long numeroPartida; 
	private LocalDateTime dataInicio;
	private LocalDateTime dataFim;
	private Map<String, Jogador> jogadores = new HashMap<String, Jogador>() ;
	
	public Partida(Long numeroPartida, LocalDateTime dataInicio) {
		super();
		this.dataInicio = dataInicio;
		this.numeroPartida = numeroPartida;
	}
	
	public Partida(Long numeroPartida, LocalDateTime dataInicio, Map<String, Jogador> jogadores, LocalDateTime dataFim) {
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

	public Map<String, Jogador> getJogadores() {
		return jogadores;
	}

	public LocalDateTime getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDateTime dataFim) {
		this.dataFim = dataFim;
	}
	
	/**
	 * Metodo que visa realizar a finalizacao da partida
	 * 
	 * Para execucao correta deste metedo todos os campos sao obrigatorios e
	 * Para a partida se considera finalizada, o parametro numero de Partida
	 * deve ser iqual ao do inicio da partida.
	 * Se os numeros nao forem iguais ser lancado uma excecao de negocio.
	 * 
	 * @param numeroPartida numero da Partida
	 * @param dataFim data quando a partida finalizada
	 * 
	 * @throws {@link PartidaInvalidaException} Excecao de negocio.
	 */
	public void finalizarPartida(Long numeroPartida, LocalDateTime dataFim) {
		
		if (this.numeroPartida.equals(numeroPartida)) {
			this.dataFim = dataFim;
		} else {
			throw new PartidaInvalidaException(String.format(NAO_FOI_POSSIVEL_FINALIZAR_A_PARTIDA_POIS_A_MESMA_NAO_FOI_INICIADA, numeroPartida));
		}
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

	/**
	 * Metodo que visa registra o evento killer na partida.
	 * 
	 * Para execucao correta deste metedo todos os campos sao obrigatorios.
	 * e caso o assassino se <WORLD> deve ser desconsiderado o assassinato, 
	 * no entanto, a morte causada pelo <WORLD> deve ser considerada para vitima.
	 * 
	 * @param assassino nome do assassino 
	 * @param vitima nome da vitima
	 */
	public void killer(Jogador assassino, Jogador vitima) {
		
		adicionaAssassino(assassino);
		adicionaVitima(vitima);
	}
	
	private void adicionaVitima(Jogador vitima) {
		
		if (vitima == null || StringUtils.isBlank(vitima.getNome())) {
			throw new PartidaInvalidaException(String.format(NAO_FOI_POSSIVEL_INCLUIR_A_VITIMA, vitima));
		}
		
		Jogador jogador = jogadores.get(vitima.getNome());
		
		if (jogador == null) {
			vitima.adicionadaMortes();
			jogadores.put(vitima.getNome(), vitima);			
		} else {
			jogadores.put(vitima.getNome(), jogador);
		}
	}

	private void adicionaAssassino(Jogador assassino) {
		
		if (assassino == null || StringUtils.isBlank(assassino.getNome())) {
			throw new PartidaInvalidaException(String.format(NAO_FOI_POSSIVEL_INCLUIR_O_ASSASSINO, assassino));
		}
		
		if(assassino.getNome().equals(ASSASSINO_BLACK_LIST_WORLD)) {
			return;
		}
		
		Jogador jogador = jogadores.get(assassino.getNome());
		
		if (jogador == null) {
			assassino.adicionadaAssassinatos();
			jogadores.put(assassino.getNome(), assassino);			
		} else {
			jogador.adicionadaAssassinatos();
			jogadores.put(assassino.getNome(), jogador);
		}
	}
}
