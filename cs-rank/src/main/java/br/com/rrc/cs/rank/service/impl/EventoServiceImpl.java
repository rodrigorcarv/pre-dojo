package br.com.rrc.cs.rank.service.impl;

import org.springframework.stereotype.Service;

import br.com.rrc.cs.rank.beans.Evento;
import br.com.rrc.cs.rank.beans.enums.TipoEventoEnum;
import br.com.rrc.cs.rank.service.EventoService;
import br.com.rrc.cs.rank.service.utils.LogUtil;

@Service
public class EventoServiceImpl implements EventoService {
	
	private static final LogUtil LOG = LogUtil.getLog(EventoServiceImpl.class);
	
	@Override
	public Evento linha2EventoPartida(String linha) {
		
		LOG.debug("linha do arquivo: %s", linha);
		TipoEventoEnum tipoEvento = TipoEventoEnum.buscaTipoEvento(linha);
		
		LOG.debug("tipoEvento: %s", tipoEvento);
		return tipoEvento.criarEvento(linha);
	}

}
