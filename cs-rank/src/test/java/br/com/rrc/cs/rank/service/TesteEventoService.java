package br.com.rrc.cs.rank.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.com.rrc.cs.rank.CsRankApplication;
import br.com.rrc.cs.rank.beans.Evento;
import br.com.rrc.cs.rank.beans.EventoStart;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CsRankApplication.class)
@WebAppConfiguration
public class TesteEventoService {
	
	@Autowired
	EventoService eventoService;
	
	@Test
	public void testBuscaTipoEventoStard() {
		
		String linha = "23/04/2013 15:34:22 - New match 11348965 has started";
		Evento eventoPartida = eventoService.linha2EventoPartida(linha);
		
		Evento evento = new EventoStart(linha);
		Assert.assertEquals(eventoPartida, evento);
	}
}
