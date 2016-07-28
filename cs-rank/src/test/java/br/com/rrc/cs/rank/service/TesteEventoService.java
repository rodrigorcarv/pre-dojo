package br.com.rrc.cs.rank.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.com.rrc.cs.rank.CsRankApplication;
import br.com.rrc.cs.rank.beans.Evento;
import br.com.rrc.cs.rank.beans.InformacaoLinha;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CsRankApplication.class)
@WebAppConfiguration
public class TesteEventoService {
	
	@Autowired
	EventoService eventoService;
	
	@Test
	public void testParseEventoStard() {
		
		String linha = "23/04/2013 15:34:22 - New match 11348965 has started";
		Evento evento = eventoService.linha2EventoPartida(linha);
		
		Long numeroPartida = new Long("11348965");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dataInicio = LocalDateTime.parse("23/04/2013 15:34:22", formatter);
		
		InformacaoLinha informacaoLinhaEsperado = new InformacaoLinha(numeroPartida, dataInicio);
		InformacaoLinha informacaoLinha = evento.getInformacaoLinha();	
		
		Assert.assertEquals(evento.getLinha(), linha);
		Assert.assertEquals(informacaoLinha, informacaoLinhaEsperado);
		Assert.assertNull(informacaoLinha.getJogadorUm());
		Assert.assertNull(informacaoLinha.getJogadorDois());
		Assert.assertNull(informacaoLinha.getNomeArma());
	}
	
	@Test
	public void testParseEventoEnd() {
		
		String linha = "23/04/2013 15:39:22 - Match 11348965 has ended";
		Evento evento = eventoService.linha2EventoPartida(linha);
		
		Long numeroPartida = new Long("11348965");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dataFim = LocalDateTime.parse("23/04/2013 15:39:22", formatter);
		
		InformacaoLinha informacaoLinhaEsperado = new InformacaoLinha(numeroPartida, dataFim);
		InformacaoLinha informacaoLinha = evento.getInformacaoLinha();	
		
		Assert.assertEquals(evento.getLinha(), linha);
		Assert.assertEquals(informacaoLinha, informacaoLinhaEsperado);
		Assert.assertNull(informacaoLinha.getJogadorUm());
		Assert.assertNull(informacaoLinha.getJogadorDois());
		Assert.assertNull(informacaoLinha.getNomeArma());
	}
	
	@Test
	public void testParseEventoKiller() {
		
		String linha = "23/04/2013 15:36:04 - Roman killed Nick using M16";
		Evento evento = eventoService.linha2EventoPartida(linha);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dataInicio = LocalDateTime.parse("23/04/2013 15:36:04", formatter);
		
		InformacaoLinha informacaoLinhaEsperado = new InformacaoLinha(dataInicio, "Roman", "Nick", "M16");
		InformacaoLinha informacaoLinha = evento.getInformacaoLinha();	
		
		Assert.assertEquals(evento.getLinha(), linha);
		Assert.assertEquals(informacaoLinha, informacaoLinhaEsperado);
		Assert.assertNull(informacaoLinha.getNumeroPartida());
	}
}
