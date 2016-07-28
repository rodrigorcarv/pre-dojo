package br.com.rrc.cs.rank.service;

import br.com.rrc.cs.rank.beans.Evento;

public interface EventoService {
	
	/**
	 * Metodo que realiza o parse da descricao de um {@link String informacaoLinha} para o
	 * {@link Evento} 
	 * 
	 * @param informacaoLinha informacaoLinha
	 * @return {@link Evento} 
	 */
	public Evento linha2EventoPartida(String linha) ;

}
