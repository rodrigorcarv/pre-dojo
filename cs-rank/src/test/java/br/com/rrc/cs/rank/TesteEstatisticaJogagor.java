package br.com.rrc.cs.rank;

import org.junit.Assert;
import org.junit.Test;

import br.com.rrc.cs.rank.beans.EstatisticaJogador;
import br.com.rrc.cs.rank.beans.EventoEnd;
import br.com.rrc.cs.rank.beans.Jogador;
import br.com.rrc.cs.rank.exceptions.PartidaInvalidaException;

public class TesteEstatisticaJogagor {

	@Test
	public void testAdicionarArmaUtilizadaComSucesso() {

		Jogador jogadorRoman = new Jogador("Roman");
		EstatisticaJogador estatisticaJogador = jogadorRoman.getEstatisticaJogador();
		estatisticaJogador.adicionadaAssassinatos();
		estatisticaJogador.adicionarArmaUtilizada("M16");
		estatisticaJogador.adicionadaAssassinatos();
		estatisticaJogador.adicionarArmaUtilizada("M16");
		estatisticaJogador.adicionadaAssassinatos();
		estatisticaJogador.adicionarArmaUtilizada("AK47");
		
		estatisticaJogador.getArmaPredila();
		
		Assert.assertEquals(estatisticaJogador.getArmaPredila(), "M16");
	}
	
	@Test(expected=PartidaInvalidaException.class)
	public void testAdicionarArmaUtilizadaNula() {

		Jogador jogadorRoman = new Jogador("Roman");
		EstatisticaJogador estatisticaJogador = jogadorRoman.getEstatisticaJogador();
		estatisticaJogador.adicionadaAssassinatos();
		estatisticaJogador.adicionarArmaUtilizada(null);
		
		estatisticaJogador.getArmaPredila();
	}
	
	@Test(expected=PartidaInvalidaException.class)
	public void testAdicionarArmaUtilizadaVazia() {

		Jogador jogadorRoman = new Jogador("Roman");
		EstatisticaJogador estatisticaJogador = jogadorRoman.getEstatisticaJogador();
		estatisticaJogador.adicionarArmaUtilizada("");
		
		estatisticaJogador.getArmaPredila();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testEventoEndInformandoLinhaInvalida() {
		
		String linha = "23/04/2013 15:39:22 - Match 11348965 has e";
		new EventoEnd(linha).analisaEvento();
		
		Assert.fail("Nao foi lancada a excecao IllegalArgumentException");
	}

}