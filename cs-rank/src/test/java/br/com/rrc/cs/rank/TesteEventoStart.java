package br.com.rrc.cs.rank;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Test;

import br.com.rrc.cs.rank.beans.EventoStart;
import br.com.rrc.cs.rank.beans.InformacaoLinha;

public class TesteEventoStart {
	
	@Test
	public void testEventoStardSucesso() {
		
		
		String linha = "23/04/2013 15:34:22 - New match 11348965 has started";
		InformacaoLinha informacaoLinha = new EventoStart(linha).analisaEvento();
		
		Long numeroPartida = new Long("11348965");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dataInicio = LocalDateTime.parse("23/04/2013 15:34:22", formatter);
		
		InformacaoLinha informacaoLinhaEsperado = new InformacaoLinha(numeroPartida, dataInicio);
		
		Assert.assertEquals(informacaoLinha, informacaoLinhaEsperado);
		Assert.assertNull(informacaoLinha.getJogadorUm());
		Assert.assertNull(informacaoLinha.getJogadorDois());
		Assert.assertNull(informacaoLinha.getNomeArma());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testEventoStardInformandoLinhaInvalida() {
		
		String linha = "23/04/2013 15:34:22 - New match 11348965 has start";
		new EventoStart(linha).analisaEvento();
		
		Assert.fail("Nao foi lancada a excecao IllegalArgumentException");
	}
}
