package br.com.rrc.cs.rank.service.impl;

import org.springframework.stereotype.Service;

import br.com.rrc.cs.rank.beans.Evento;
import br.com.rrc.cs.rank.beans.enums.TipoEventoEnum;
import br.com.rrc.cs.rank.service.EventoService;

@Service
public class EventoServiceImpl implements EventoService {
	
	@Override
	public Evento linha2EventoPartida(String linha) {
		
		TipoEventoEnum tipoEvento = TipoEventoEnum.buscaTipoEvento(linha);
		return tipoEvento.criarEvento(linha);
	}

}
