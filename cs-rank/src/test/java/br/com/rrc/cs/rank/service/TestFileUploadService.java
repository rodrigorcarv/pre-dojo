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
import br.com.rrc.cs.rank.beans.Jogador;
import br.com.rrc.cs.rank.beans.Partida;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CsRankApplication.class)
@WebAppConfiguration
public class TestFileUploadService {
	
	@Autowired
	FileUploadService fileUploadService;
	
	@Test
	public void testProcessamentoDoArquivo() {

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
		
		List<Partida> partidas = new ArrayList<>();
		partidas.add(partida);
		
		List<Partida> processarArquivo = fileUploadService.processarArquivo(linhas.stream());
		Assert.assertEquals(partidas, processarArquivo);
		
	}
}
