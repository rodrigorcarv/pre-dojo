package br.com.rrc.cs.rank.service;

import java.util.stream.Stream;

import br.com.rrc.cs.rank.beans.EstatisticaPartida;

public interface FileUploadService {

	EstatisticaPartida getEstatisticaPartida(Stream<String> linhas);

}
