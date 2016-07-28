package br.com.rrc.cs.rank;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Test;

import br.com.rrc.cs.rank.beans.EventoEnd;
import br.com.rrc.cs.rank.beans.InformacaoLinha;

public class TesteEventoEnd {
	
	
	
	@Test
	public void testEventoEndSucesso() {
		
		String linha = "23/04/2013 15:39:22 - Match 11348965 has ended";
		InformacaoLinha informacaoLinha = new EventoEnd(linha).analisaEvento();
		
		Long numeroPartida = new Long("11348965");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dataFim = LocalDateTime.parse("23/04/2013 15:39:22", formatter);
		
		InformacaoLinha informacaoLinhaEsperado = new InformacaoLinha(numeroPartida, dataFim);
		
		Assert.assertEquals(informacaoLinha, informacaoLinhaEsperado);
		Assert.assertNull(informacaoLinha.getJogadorUm());
		Assert.assertNull(informacaoLinha.getJogadorDois());
		Assert.assertNull(informacaoLinha.getNomeArma());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testEventoEndInformandoLinhaInvalida() {
		
		String linha = "23/04/2013 15:39:22 - Match 11348965 has ended";
		new EventoEnd(linha).analisaEvento();
		
		Assert.fail("Nao foi lancada a excecao IllegalArgumentException");
	}
}
