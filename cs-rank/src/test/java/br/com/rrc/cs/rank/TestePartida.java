package br.com.rrc.cs.rank;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import br.com.rrc.cs.rank.beans.Jogador;
import br.com.rrc.cs.rank.beans.Partida;
import br.com.rrc.cs.rank.exceptions.PartidaInvalidaException;

public class TestePartida {

	@Test
	public void testFinalizarPartidaComSucesso() {
		
		Long numeroPartida = new Long("11348965");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dataInicio = LocalDateTime.parse("23/04/2013 15:34:22", formatter);
		LocalDateTime dataFim = LocalDateTime.parse("23/04/2013 15:39:22", formatter);
		
		Partida partida = new Partida(numeroPartida , dataInicio);
		partida.finalizarPartida(numeroPartida, dataFim);
		
		Partida partidaEsperada = new Partida(numeroPartida, dataInicio, new HashMap<>(), dataFim);
		Assert.assertEquals(partida, partidaEsperada);

	}
	
	@Test(expected=PartidaInvalidaException.class)
	public void testFinalizarPartida() {
		
		Long numeroPartida = new Long("11348965");
		Long numeroPartidaInvalido = new Long("11348964");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dataInicio = LocalDateTime.parse("23/04/2013 15:34:22", formatter);
		LocalDateTime dataFim = LocalDateTime.parse("23/04/2013 15:39:22", formatter);
		
		Partida partida = new Partida(numeroPartida , dataInicio);
		partida.finalizarPartida(numeroPartidaInvalido, dataFim);
		
		Assert.fail("Nao foi apresentada a excecao PartidaInvalidaException");
	}
	
	public void testKillerPartida() {
		
		Long numeroPartida = new Long("11348965");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dataInicio = LocalDateTime.parse("23/04/2013 15:34:22", formatter);
		
		Partida partida = new Partida(numeroPartida , dataInicio);
		Jogador assassino = new Jogador("Roman");
		Jogador vitima = new Jogador("Nick");
		partida.killer(assassino, vitima);
	}
	
	@Test(expected=PartidaInvalidaException.class)
	public void testKillerPartidaInvalida() {
		
		Long numeroPartida = new Long("11348965");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dataInicio = LocalDateTime.parse("23/04/2013 15:34:22", formatter);
		
		Partida partida = new Partida(numeroPartida , dataInicio);
		Jogador assassino = new Jogador("Roman");
		partida.killer(assassino, null);
	}
}
