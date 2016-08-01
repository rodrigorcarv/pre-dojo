package br.com.rrc.cs.rank;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Test;

import br.com.rrc.cs.rank.beans.EstatisticaPartida;

public class TesteEstatisticaPartida {
	
	@Test
	public void testCalculaDuracaoPartidaComSucesso() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		LocalDateTime dataInicio = LocalDateTime.parse("23/04/2013 15:34:22", formatter);
		LocalDateTime dataFim = LocalDateTime.parse("23/04/2013 15:39:22", formatter);
		
		EstatisticaPartida estatisticaPartida = new EstatisticaPartida();
		estatisticaPartida.calculaDuracaoPartida(dataInicio, dataFim);
		
		Assert.assertEquals(estatisticaPartida.getDuracaoPartida(), Duration.ofMinutes(5));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCalculaDuracaoDaPartidaDataInicioNula() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		LocalDateTime dataInicio = null;
		LocalDateTime dataFim = LocalDateTime.parse("23/04/2013 15:39:22", formatter);
		
		EstatisticaPartida estatisticaPartida = new EstatisticaPartida();
		estatisticaPartida.calculaDuracaoPartida(dataInicio, dataFim);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCalculaDuracaoDaPartidaDataFinalNula() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		LocalDateTime dataInicio = LocalDateTime.parse("23/04/2013 15:34:22", formatter);
		LocalDateTime dataFim = null;
		
		EstatisticaPartida estatisticaPartida = new EstatisticaPartida();
		estatisticaPartida.calculaDuracaoPartida(dataInicio, dataFim);
		
	}
	
}
