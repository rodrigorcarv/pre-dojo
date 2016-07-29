package br.com.rrc.cs.rank.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.com.rrc.cs.rank.CsRankApplication;
import br.com.rrc.cs.rank.beans.Evento;
import br.com.rrc.cs.rank.beans.EventoEnd;
import br.com.rrc.cs.rank.beans.EventoKiller;
import br.com.rrc.cs.rank.beans.EventoStart;
import br.com.rrc.cs.rank.beans.Jogador;
import br.com.rrc.cs.rank.beans.Partida;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CsRankApplication.class)
@WebAppConfiguration
public class TestePartidaService {
	
	@Autowired
	PartidaService partidaService;
	
	@Test
	public void testExtrairPartidasComSucesso() {
		
		List<String> linhas = new ArrayList<>();
		linhas.add("23/04/2013 15:34:22 - New match 11348965 has started");
		linhas.add("23/04/2013 15:36:04 - Roman killed Nick using M16");
		linhas.add("23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN");
		linhas.add("23/04/2013 15:39:22 - Match 11348965 has ended");
		
		Long numeroPartida = new Long("11348965");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dataInicio = LocalDateTime.parse("23/04/2013 15:34:22", formatter);
		LocalDateTime dataFim = LocalDateTime.parse("23/04/2013 15:39:22", formatter);
		
		Jogador jogadorRoman = new Jogador("Roman");
		jogadorRoman.adicionadaAssassinatos();
		Jogador jogadorNick = new Jogador("Nick");
		jogadorNick.adicionadaMortes();
		jogadorNick.adicionadaMortes();
		
		Map<String, Jogador> jogadores = new HashMap<>();
		jogadores.put("Roman", jogadorRoman);
		jogadores.put("Nick", jogadorNick);
		
		Partida partida = new Partida(numeroPartida, dataInicio, jogadores, dataFim);
		
		List<Partida> resultadoEsperadoPartidas = new ArrayList<>();
		resultadoEsperadoPartidas.add(partida);
		
		List<Evento> eventos = new ArrayList<>();

		String linhaStart = "23/04/2013 15:34:22 - New match 11348965 has started";
		EventoStart eventoStart = new EventoStart(linhaStart);
		eventos.add(eventoStart);
		
		String linhaKiller1 = "23/04/2013 15:36:04 - Roman killed Nick using M16";
		EventoKiller eventoKiller1 = new EventoKiller(linhaKiller1);
		eventos.add(eventoKiller1);
		
		String linhaKiller2 = "23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN";
		EventoKiller eventoKiller2 = new EventoKiller(linhaKiller2);
		eventos.add(eventoKiller2);
		
		String linhaEnd = "23/04/2013 15:39:22 - Match 11348965 has ended";
		EventoEnd eventoEnd = new EventoEnd(linhaEnd);
		eventos.add(eventoEnd);
		
		List<Partida> extrairPartidas = partidaService.extrairPartidas(eventos);
		Assert.assertEquals(resultadoEsperadoPartidas, extrairPartidas);
	}
	
	@Test
	public void testExtrairPartidasComInvalida() {
		
		List<String> linhas = new ArrayList<>();
		linhas.add("23/04/2013 15:34:22 - New match 11348965 has started");
		linhas.add("23/04/2013 15:36:04 - Roman killed Nick using M16");
		linhas.add("23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN");
		linhas.add("23/04/2013 15:39:22 - Match 11348965 has ended");
		
		Long numeroPartida = new Long("11348965");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dataInicio = LocalDateTime.parse("23/04/2013 15:34:22", formatter);
		LocalDateTime dataFim = LocalDateTime.parse("23/04/2013 15:39:22", formatter);
		
		Jogador jogadorRoman = new Jogador("Roman");
		jogadorRoman.adicionadaAssassinatos();
		
		Jogador jogadorNick = new Jogador("Nick");
		jogadorNick.adicionadaMortes();
		jogadorNick.adicionadaMortes();

		Jogador jogadorWord = new Jogador("<WORLD>");
		jogadorWord.adicionadaMortes();
		
		Map<String, Jogador> jogadores = new HashMap<>();
		jogadores.put("Roman", jogadorRoman);
		jogadores.put("Nick", jogadorNick);
		jogadores.put("<WORLD>", jogadorWord);
		
		Partida partida = new Partida(numeroPartida, dataInicio, jogadores, dataFim);
		
		List<Partida> resultadoEsperadoPartidas = new ArrayList<>();
		resultadoEsperadoPartidas.add(partida);
		
		List<Evento> eventos = new ArrayList<>();

		String linhaStart = "23/04/2013 15:34:22 - New match 11348965 has started";
		EventoStart eventoStart = new EventoStart(linhaStart);
		eventos.add(eventoStart);
		
		String linhaKiller1 = "23/04/2013 15:36:04 - Roman killed Nick using M16";
		EventoKiller eventoKiller1 = new EventoKiller(linhaKiller1);
		eventos.add(eventoKiller1);
		
		String linhaKiller2 = "23/04/2013 15:36:33 - <WORLD> killed Nick by DROWN";
		EventoKiller eventoKiller2 = new EventoKiller(linhaKiller2);
		eventos.add(eventoKiller2);
		
		String linhaEnd = "23/04/2013 15:39:22 - Match 11348965 has ended";
		EventoEnd eventoEnd = new EventoEnd(linhaEnd);
		eventos.add(eventoEnd);
		
		List<Partida> extrairPartidas = partidaService.extrairPartidas(eventos);
		Assert.assertNotEquals(resultadoEsperadoPartidas, extrairPartidas);
	}
}
