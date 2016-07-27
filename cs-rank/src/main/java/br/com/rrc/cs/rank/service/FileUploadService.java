package br.com.rrc.cs.rank.service;

import java.util.List;
import java.util.stream.Stream;

import br.com.rrc.cs.rank.beans.Partida;

public interface FileUploadService {

	List<Partida> processarArquivo(Stream<String> linhas);

}
