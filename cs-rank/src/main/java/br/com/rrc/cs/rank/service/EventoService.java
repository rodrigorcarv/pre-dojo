package br.com.rrc.cs.rank.service;

import br.com.rrc.cs.rank.beans.Evento;

public interface EventoService {
	
	/**
	 * Metodo que realiza o parse da descricao de um {@link String linha} para o
	 * {@link Evento} 
	 * 
	 * @param linha linha
	 * @return {@link Evento} 
	 */
	public Evento linha2EventoPartida(String linha) ;

}
