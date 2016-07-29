package br.com.rrc.cs.rank.service;

import java.util.List;

import br.com.rrc.cs.rank.beans.Evento;
import br.com.rrc.cs.rank.beans.Partida;

public interface PartidaService {
	
	/**
	 * Metodo extrai e cria partidas de acordo com os eventos informados.
	 * Os {@link Evento} que diferentes de: START , END e KILL seram desconsiderados.
	 * 
	 * @param eventos {@link List} de {@link Evento}
	 * 
	 * @return  {@link List} de {@link Partida}
	 */
	public List<Partida> extrairPartidas(List<Evento> eventos);
	
}
