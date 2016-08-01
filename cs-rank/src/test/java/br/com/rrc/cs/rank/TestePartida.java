package br.com.rrc.cs.rank;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Test;

import br.com.rrc.cs.rank.beans.EstatisticaJogador;
import br.com.rrc.cs.rank.beans.EstatisticaPartida;
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
		partida.killer(new Jogador("Alfredo"), new Jogador("Joao"), "AK47");
		partida.finalizarPartida(numeroPartida, dataFim);

		EstatisticaPartida estatisticaPartida = partida.getEstatisticaPartida();

		Assert.assertEquals(partida.getDataFim(), dataFim);
		Assert.assertEquals(partida.getDataInicio(), dataInicio);
		Assert.assertEquals(estatisticaPartida.getDuracaoPartida(), Duration.ofMinutes(5));

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
	

	@Test
	public void testFinalizarPartidaComVencedoSemBonusrSucesso() {

		Long numeroPartida = new Long("11348965");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dataInicio = LocalDateTime.parse("23/04/2013 15:34:22", formatter);
		LocalDateTime dataFim = LocalDateTime.parse("23/04/2013 15:39:22", formatter);

		Partida partida = new Partida(numeroPartida , dataInicio);
		partida.killer(new Jogador("Alfredo"), new Jogador("Joao"), "AK47");
		partida.killer(new Jogador("Test"), new Jogador("Alfredo"), "AK47");
		partida.killer(new Jogador("Alfredo"), new Jogador("Test"), "AK47");
		partida.finalizarPartida(numeroPartida, dataFim);

		EstatisticaPartida estatisticaPartida = partida.getEstatisticaPartida();

		Assert.assertEquals(partida.getDataFim(), dataFim);
		Assert.assertEquals(partida.getDataInicio(), dataInicio);
		Assert.assertEquals(estatisticaPartida.getDuracaoPartida(), Duration.ofMinutes(5));

	}
	
	@Test
	public void testFinalizarPartidaComEmpate() {

		Long numeroPartida = new Long("11348965");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dataInicio = LocalDateTime.parse("23/04/2013 15:34:22", formatter);
		LocalDateTime dataFim = LocalDateTime.parse("23/04/2013 15:39:22", formatter);

		Partida partida = new Partida(numeroPartida , dataInicio);
		partida.killer(new Jogador("Alfredo"), new Jogador("Joao"), "AK47");
		partida.killer(new Jogador("Test"), new Jogador("Alfredo"), "M16");
		partida.killer(new Jogador("Alfredo"), new Jogador("Test"), "AK47");
		partida.killer(new Jogador("Test"), new Jogador("Joao"), "M16");
		partida.finalizarPartida(numeroPartida, dataFim);

		EstatisticaPartida estatisticaPartida = partida.getEstatisticaPartida();

		Assert.assertEquals(partida.getDataFim(), dataFim);
		Assert.assertEquals(partida.getDataInicio(), dataInicio);
		Assert.assertEquals(estatisticaPartida.getDuracaoPartida(), Duration.ofMinutes(5));
		
		Assert.assertNotNull(partida.getJogadores());
		Assert.assertEquals(partida.getJogadores().size(), 3);
		Assert.assertNull(partida.getVencedor());
		
		Jogador jogadorAlfredo = partida.getJogadores().get("Alfredo");
		EstatisticaJogador estatisticaJogadorAlfredo = jogadorAlfredo.getEstatisticaJogador();
		
		Assert.assertEquals(estatisticaJogadorAlfredo.getQtdAssinatos(), 2);
		Assert.assertEquals(estatisticaJogadorAlfredo.getQtdMortes(), 1);
		

	}

	@Test
	public void testKillerPartida() {

		Long numeroPartida = new Long("11348965");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dataInicio = LocalDateTime.parse("23/04/2013 15:34:22", formatter);

		Partida partida = new Partida(numeroPartida , dataInicio);
		Jogador assassino = new Jogador("Roman");
		Jogador vitima = new Jogador("Nick");
		String arma = "M16";
		partida.killer(assassino, vitima, arma);
		
		Assert.assertNotNull(partida.getJogadores());
		Assert.assertEquals(partida.getJogadores().size(), 2);
		
		Jogador jogadorRoman = partida.getJogadores().get("Roman");
		EstatisticaJogador estatisticaJogadorRoman = jogadorRoman.getEstatisticaJogador();
		
		Assert.assertEquals(estatisticaJogadorRoman.getQtdAssinatos(), 1);
		Assert.assertEquals(estatisticaJogadorRoman.getQtdMortes(), 0);
		Assert.assertEquals(estatisticaJogadorRoman.getArmaPredila(), "M16");
		
		Jogador jogadorNick = partida.getJogadores().get("Nick");
		EstatisticaJogador estatisticaJogadorNick = jogadorNick.getEstatisticaJogador();
		
		Assert.assertEquals(estatisticaJogadorNick.getQtdAssinatos(), 0);
		Assert.assertEquals(estatisticaJogadorNick.getQtdMortes(), 1);
		Assert.assertNull(estatisticaJogadorNick.getArmaPredila());
	}

	@Test(expected=PartidaInvalidaException.class)
	public void testKillerPartidaInvalida() {

		Long numeroPartida = new Long("11348965");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dataInicio = LocalDateTime.parse("23/04/2013 15:34:22", formatter);

		Partida partida = new Partida(numeroPartida , dataInicio);
		Jogador assassino = new Jogador("Roman");
		String arma = "M16";
		partida.killer(assassino, null, arma);
	}

	@Test
	public void testVencedorPartidaComSucesso() {

		Long numeroPartida = new Long("11348965");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dataInicio = LocalDateTime.parse("23/04/2013 15:34:22", formatter);
		LocalDateTime dataFim = LocalDateTime.parse("23/04/2013 15:39:22", formatter);
		
		Jogador jogadorRoman = new Jogador("Roman");
		Jogador jogadorNick = new Jogador("Nick");
		Jogador jogadorTest = new Jogador("Test");

		Partida partida = new Partida(numeroPartida, dataInicio);
		partida.killer(jogadorRoman, jogadorNick, "M16");
		partida.killer(jogadorRoman, jogadorNick, "M16");
		partida.killer(jogadorRoman, jogadorNick, "M16");
		partida.killer(jogadorTest, jogadorNick, "AK47");
		partida.finalizarPartida(numeroPartida, dataFim);
		
		Assert.assertNotNull(partida.getJogadores());
		Assert.assertEquals(partida.getJogadores().size(), 3);
		Assert.assertEquals(partida.getVencedor().getNome(), jogadorRoman.getNome());
		
		EstatisticaJogador estatisticaJogadorRoman = jogadorRoman.getEstatisticaJogador();
		
		Assert.assertEquals(estatisticaJogadorRoman.getQtdAssinatos(), 3);
		Assert.assertEquals(estatisticaJogadorRoman.getQtdMortes(), 0);

		Assert.assertEquals(partida.getVencedor().getNome(), jogadorRoman.getNome());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testVencedorPartidaListaJogadoresVazia() {

		Long numeroPartida = new Long("11348965");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dataInicio = LocalDateTime.parse("23/04/2013 15:34:22", formatter);
		
		Partida partida = new Partida(numeroPartida, dataInicio);
		partida.analisaVencedor();
	}
}
