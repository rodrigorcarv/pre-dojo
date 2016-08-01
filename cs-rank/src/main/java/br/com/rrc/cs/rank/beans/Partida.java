package br.com.rrc.cs.rank.beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import br.com.rrc.cs.rank.exceptions.PartidaInvalidaException;
import br.com.rrc.cs.rank.service.utils.LogUtil;

public class Partida implements Serializable{

	private static final long serialVersionUID = 1L;

	private static final LogUtil LOG = LogUtil.getLog(Partida.class);

	private static final String ASSASSINO_BLACK_LIST_WORLD = "<WORLD>";
	private static final String NAO_FOI_POSSIVEL_INCLUIR_A_VITIMA = "Nao foi possivel incluir a vitima: %s, pois a mesma nao esta preenchida corretamente";
	private static final String NAO_FOI_POSSIVEL_FINALIZAR_A_PARTIDA_POIS_A_MESMA_NAO_FOI_INICIADA = "Não foi possível finalizar a partida %s, pois a mesma não foi iniciada";
	private static final String NAO_FOI_POSSIVEL_INCLUIR_O_ASSASSINO = "Nao foi possivel incluir o assassino: %s, pois o mesmo nao esta preenchida corretamente";
	private static final String NAO_FOI_POSSIVEL_PRECISAR_O_VENCENDOR_POIS_LISTA_DE_JOGADORES_ESTA_VAZIA = "Nao foi possivel precisar o vencendor, pois a lista de jogadores esta vazia";

	private Long numeroPartida; 
	private LocalDateTime dataInicio;
	private LocalDateTime dataFim;
	private Map<String, Jogador> jogadores = new HashMap<String, Jogador>() ;
	private EstatisticaPartida estatisticaPartida = new EstatisticaPartida();
	private Jogador vencedor;

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

	public EstatisticaPartida getEstatisticaPartida() {
		return estatisticaPartida;
	}

	public Jogador getVencedor() {
		return vencedor;
	}

	public void setVencedor(Jogador vencedor) {
		this.vencedor = vencedor;
	}

	public void analisaVencedor() {

		if (jogadores.size() <= 0) {
			LOG.error(NAO_FOI_POSSIVEL_PRECISAR_O_VENCENDOR_POIS_LISTA_DE_JOGADORES_ESTA_VAZIA);
			throw new IllegalArgumentException(NAO_FOI_POSSIVEL_PRECISAR_O_VENCENDOR_POIS_LISTA_DE_JOGADORES_ESTA_VAZIA);
		}

		Jogador jogador = jogadores.values()
				.stream()
				.max((s1, s2) -> 
				Integer.compare(s1.getEstatisticaJogador().getQtdAssinatos(), 
						s2.getEstatisticaJogador().getQtdAssinatos()))
				.orElse(null);

		List<Jogador> jodagoresQtdAssinatosIguais = jogadores.values()
			.stream()
			.filter(p -> p.getEstatisticaJogador().getQtdAssinatos() == jogador.getEstatisticaJogador().getQtdAssinatos())
			.collect(Collectors.toList());	

		//Ocorreu um empate
		if  (jodagoresQtdAssinatosIguais.size() > 1) {
			vencedor = null;
		} else {
			vencedor = jogador;
		}
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

		LOG.debug("Dados de entrada");
		LOG.debug("numeroPartida: ", numeroPartida);
		LOG.debug("dataFim: ", dataFim);

		if (this.numeroPartida.equals(numeroPartida)) {
			this.dataFim = dataFim;
		} else {

			LOG.error(NAO_FOI_POSSIVEL_FINALIZAR_A_PARTIDA_POIS_A_MESMA_NAO_FOI_INICIADA, numeroPartida);
			throw new PartidaInvalidaException(String.format(NAO_FOI_POSSIVEL_FINALIZAR_A_PARTIDA_POIS_A_MESMA_NAO_FOI_INICIADA, numeroPartida));
		}

		estatisticaPartida.calculaDuracaoPartida(this.dataInicio, dataFim);
		analisaVencedor();

		if (vencedor != null) {

			EstatisticaJogador estatisticaJogador = this.vencedor.getEstatisticaJogador();

			if (estatisticaJogador.getQtdMortes() == 0) {
				this.vencedor.adicionaBonus("Jogador venceu a partida sem nenhuma morte");
			}
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
	 * @param nomeArma nome da arma
	 */
	public void killer(Jogador assassino, Jogador vitima, String nomeArma) {

		LOG.debug("Dados de entrada killer");
		LOG.debug("assassino: %s", assassino);
		LOG.debug("vitima: %s", vitima);
		LOG.debug("nomeArma: %s", nomeArma);

		adicionaAssassino(assassino, nomeArma);
		adicionaVitima(vitima);
	}

	private void adicionaVitima(Jogador vitima) {

		LOG.debug("Dados de entrada adicionaVitima");
		LOG.debug("vitima: %s", vitima);

		if (vitima == null || StringUtils.isBlank(vitima.getNome())) {
			LOG.error(NAO_FOI_POSSIVEL_INCLUIR_A_VITIMA, vitima);
			throw new PartidaInvalidaException(String.format(NAO_FOI_POSSIVEL_INCLUIR_A_VITIMA, vitima));
		}

		Jogador jogador = jogadores.get(vitima.getNome());

		if (jogador == null) {
			vitima.getEstatisticaJogador().adicionadaMortes();
			jogadores.put(vitima.getNome(), vitima);			
		} else {
			jogador.getEstatisticaJogador().adicionadaMortes();
			jogadores.put(vitima.getNome(), jogador);
		}
	}

	private void adicionaAssassino(Jogador assassino, String nomeArma) {

		LOG.debug("Dados de entrada adicionaVitima");
		LOG.debug("assassino: %s", assassino);

		if (assassino == null || StringUtils.isBlank(assassino.getNome())) {
			LOG.error(NAO_FOI_POSSIVEL_INCLUIR_O_ASSASSINO, assassino);
			throw new PartidaInvalidaException(String.format(NAO_FOI_POSSIVEL_INCLUIR_O_ASSASSINO, assassino));
		}

		if(assassino.getNome().equals(ASSASSINO_BLACK_LIST_WORLD)) {
			return;
		}

		Jogador jogador = jogadores.get(assassino.getNome());

		if (jogador == null) {

			EstatisticaJogador estatisticaJogador = assassino.getEstatisticaJogador();
			estatisticaJogador.adicionadaAssassinatos();
			estatisticaJogador.adicionarArmaUtilizada(nomeArma);
			jogadores.put(assassino.getNome(), assassino);			
		} else {
			EstatisticaJogador estatisticaJogador = jogador.getEstatisticaJogador();
			estatisticaJogador.adicionadaAssassinatos();
			estatisticaJogador.adicionarArmaUtilizada(nomeArma);
			jogadores.put(assassino.getNome(), jogador);
		}
	}
}
