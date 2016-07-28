package br.com.rrc.cs.rank;

import org.junit.Assert;
import org.junit.Test;

import br.com.rrc.cs.rank.beans.enums.TipoEventoEnum;

public class TesteTipoEvento {
	
	@Test
	public void testBuscaTipoEventoStard() {
		
		String linha = "23/04/2013 15:34:22 - New match 11348965 has started";
		TipoEventoEnum TipoEventoEnumEnum = TipoEventoEnum.buscaTipoEvento(linha);
		Assert.assertEquals(TipoEventoEnum.START, TipoEventoEnumEnum);
	}

	@Test
	public void testBuscaTipoEventoKilled() {
		
		String linha = "23/04/2013 15:36:04 - Roman killed Nick using M16";
		TipoEventoEnum TipoEventoEnumEnum = TipoEventoEnum.buscaTipoEvento(linha);
		Assert.assertEquals(TipoEventoEnum.KILL, TipoEventoEnumEnum);
	}
	
	@Test
	public void testBuscaTipoEventoEnd() {
		
		String linha = "23/04/2013 15:39:22 - Match 11348965 has ended";
		TipoEventoEnum TipoEventoEnumEnum = TipoEventoEnum.buscaTipoEvento(linha);
		Assert.assertEquals(TipoEventoEnum.END, TipoEventoEnumEnum);
	}

	@Test(expected = IllegalAccessException.class)
	public void testBuscaTipoEventoNull() {
		
		String linha = null;
		TipoEventoEnum.buscaTipoEvento(linha);
		Assert.fail();
	}
	
	@Test(expected = IllegalAccessException.class)
	public void testBuscaTipoEventoVazio() {
		
		String linha = "";
		TipoEventoEnum.buscaTipoEvento(linha);
		Assert.fail();
	}

	@Test(expected = IllegalAccessException.class)
	public void testBuscaTipoEventoInvalido() {
		
		String linha = "23/04/2013 15:39:22";
		TipoEventoEnum.buscaTipoEvento(linha);
		Assert.fail();
	}

}
