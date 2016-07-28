package br.com.rrc.cs.rank.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Partida implements Serializable{

	private static final String NÃO_FOI_POSSÍVEL_FINALIZAR_A_PARTIDA_POIS_A_MESMA_NÃO_FOI_INICIADA = "Não foi possível finalizar a partida %s, pois a mesma não foi iniciada";

	private static final long serialVersionUID = 1L;
	
	private Long numeroPartida; 
	private LocalDateTime dataInicio;
	private LocalDateTime dataFim;
	private Set<Jogador> jogadores = new HashSet<Jogador>() ;
	
	public Partida(Long numeroPartida, LocalDateTime dataInicio) {
		super();
		this.dataInicio = dataInicio;
		this.numeroPartida = numeroPartida;
	}
	
	public Partida(Long numeroPartida, LocalDateTime dataInicio, Set<Jogador> jogadores, LocalDateTime dataFim) {
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

	public Set<Jogador> getJogadores() {
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
	 * Para a partida se considera finalizada, o parametro numero de Partida
	 * deve ser iqual ao do inicio da partida.
	 * Se os numeros nao forem iguais ser lancado uma excecao de negocio.
	 * 
	 * @param numeroPartida numero da Partida
	 * @param dataFim data quando a partida finalizada
	 * 
	 * @throws {@link RuntimeException} Excecao de negocio.
	 */
	public void finalizarPartida(Long numeroPartida, LocalDateTime dataFim) {
		
		if (this.numeroPartida.equals(numeroPartida)) {
			this.dataFim = dataFim;
		} else {
			throw new RuntimeException(String.format(NÃO_FOI_POSSÍVEL_FINALIZAR_A_PARTIDA_POIS_A_MESMA_NÃO_FOI_INICIADA, numeroPartida));
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
	 * @param data data do evento
	 * @param assassino nome do assassino 
	 * @param vitima nome da vitima
	 * @param nomeArma nome da Arma utilizada no assassinato.
	 */
	public void killer(LocalDateTime data, Jogador assassino, Jogador vitima, String nomeArma) {
		
		adicionaAssassino(assassino);
		adicionaVitima(vitima);
	}
	
	private void adicionaVitima(Jogador vitima) {
		jogadores.add(vitima);
		
	}

	private void adicionaAssassino(Jogador assassino) {
		jogadores.add(assassino);
		
	}

	private void adicionaJogador(Jogador jogador) {
		jogadores.add(jogador);
	}

	
}
