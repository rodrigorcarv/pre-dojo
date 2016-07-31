package br.com.rrc.cs.rank.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.rrc.cs.rank.beans.Evento;
import br.com.rrc.cs.rank.beans.InformacaoLinha;
import br.com.rrc.cs.rank.beans.Jogador;
import br.com.rrc.cs.rank.beans.Partida;
import br.com.rrc.cs.rank.service.PartidaService;
import br.com.rrc.cs.rank.service.utils.LogUtil;

@Service
public class PartidaServiceImpl implements PartidaService {
	
	private static final LogUtil LOG = LogUtil.getLog(PartidaServiceImpl.class);

	public List<Partida> extrairPartidas(List<Evento> eventos) {
		
		Partida partida = null ;
		List<Partida> partidas = new ArrayList<>();
		
		for (Evento evento : eventos) {
			
			LOG.info("evento: %s", evento);

			InformacaoLinha infLinha = evento.analisaEvento(); 
			
			switch (evento.getTipoEventoEnum()) {
			
			case START:
				partida = new Partida(infLinha.getNumeroPartida(), infLinha.getData());
				break;
			
			case END:
				
				partida.finalizarPartida(infLinha.getNumeroPartida(), infLinha.getData());
				partidas.add(partida);
				break;
			
			case KILL:

				partida.killer(new Jogador(infLinha.getJogadorUm()), 
						new Jogador(infLinha.getJogadorDois()));
				break;
			}
		}
		
		return partidas;
	}
}