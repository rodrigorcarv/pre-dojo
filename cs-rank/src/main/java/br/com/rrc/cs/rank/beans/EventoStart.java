package br.com.rrc.cs.rank.beans;

import br.com.rrc.cs.rank.beans.enums.TipoEventoEnum;

public class EventoStart extends Evento {

	public EventoStart(String linha) {
		super(linha, TipoEventoEnum.START);
	}

	@Override
	public void analisaEvento(Partida partida) {

	}

}
