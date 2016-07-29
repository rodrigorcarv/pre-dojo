package br.com.rrc.cs.rank.service;

import java.util.List;

import br.com.rrc.cs.rank.beans.Partida;

public interface FileUploadService {

	/**
	 * Metodo responsavel por processar as linhas do um arquivo.
	 * 
	 * @param linhas linhas
	 * 
	 * @return Retorna {@link Partida}
	 */
	List<Partida> processarArquivo(List<String> linhas);

}
