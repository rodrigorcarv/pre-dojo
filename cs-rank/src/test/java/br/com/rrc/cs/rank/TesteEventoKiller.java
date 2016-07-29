package br.com.rrc.cs.rank;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Test;

import br.com.rrc.cs.rank.beans.EventoKiller;
import br.com.rrc.cs.rank.beans.InformacaoLinha;

public class TesteEventoKiller {
	
	@Test
	public void testEventoKillerSucesso() {
		
		String linha = "23/04/2013 15:36:04 - Roman killed Nick using M16";
		InformacaoLinha informacaoLinha = new EventoKiller(linha).analisaEvento();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dataInicio = LocalDateTime.parse("23/04/2013 15:36:04", formatter);
		
		InformacaoLinha informacaoLinhaEsperado = new InformacaoLinha(dataInicio, "Roman", "Nick", "M16");
		
		Assert.assertEquals(informacaoLinha, informacaoLinhaEsperado);
		Assert.assertNull(informacaoLinha.getNumeroPartida());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testEventoKillerInformandoLinhaInvalida() {
		
		String linha = "23/04/2013 15:36:04 - Roman kil Nick us M16";
		new EventoKiller(linha).analisaEvento();
		
		Assert.fail("Nao foi lancada a excecao IllegalArgumentException");
	}
}
