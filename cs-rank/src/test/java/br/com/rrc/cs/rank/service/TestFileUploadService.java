package br.com.rrc.cs.rank.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.com.rrc.cs.rank.CsRankApplication;
import br.com.rrc.cs.rank.beans.EstatisticaPartida;
import br.com.rrc.cs.rank.beans.Evento;
import br.com.rrc.cs.rank.beans.Jogador;
import br.com.rrc.cs.rank.beans.Partida;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CsRankApplication.class)
@WebAppConfiguration
public class TestFileUploadService {

	@Autowired
	FileUploadService fileUploadService;

	@Mock
	public EventoService eventoService;

	@Mock
	public PartidaService partidaService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testProcessamentoDoArquivo() {

		List<String> linhas = new ArrayList<>();
		linhas.add("23/04/2013 15:34:22 - New match 11348965 has started");
		linhas.add("23/04/2013 15:36:04 - Roman killed Nick using M16");
		linhas.add("23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN");
		linhas.add("23/04/2013 15:39:22 - Match 11348965 has ended");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dataInicio = LocalDateTime.parse("23/04/2013 15:34:22", formatter);
		LocalDateTime dataFim = LocalDateTime.parse("23/04/2013 15:39:22", formatter);

		Jogador jogadorRoman = new Jogador("Roman");
		jogadorRoman.getEstatisticaJogador().adicionadaAssassinatos();
		Jogador jogadorNick = new Jogador("Nick");
		jogadorNick.getEstatisticaJogador().adicionadaMortes();
		jogadorNick.getEstatisticaJogador().adicionadaMortes();

		Map<String, Jogador> jogadores = new HashMap<>();
		jogadores.put("Roman", jogadorRoman);
		jogadores.put("Nick", jogadorNick);

		Partida partida = new Partida(new Long("11348965"), dataInicio, jogadores, dataFim);

		List<Partida> partidas = new ArrayList<>();
		partidas.add(partida);

		List<Evento> eventos = new ArrayList<>();

		Mockito.when(eventoService.linha2EventoPartida(Mockito.anyString())).thenReturn(Mockito.any());
		Mockito.when(partidaService.extrairPartidas(eventos)).thenReturn(partidas);
		List<Partida> processarArquivo = fileUploadService.processarArquivo(linhas);

		Assert.assertEquals(processarArquivo.size(), 1);
		
		Partida partidaObtida = processarArquivo.get(0);
		
		Assert.assertEquals(partidaObtida.getDataInicio(), dataInicio);
		Assert.assertEquals(partidaObtida.getDataFim(), dataFim);
		Assert.assertEquals(partidaObtida.getNumeroPartida(), new Long("11348965"));
		
		EstatisticaPartida estatisticaPartida = partidaObtida.getEstatisticaPartida();
		
		Assert.assertEquals(estatisticaPartida.getDuracaoPartida(), Duration.ofMinutes(5));
	}
}
