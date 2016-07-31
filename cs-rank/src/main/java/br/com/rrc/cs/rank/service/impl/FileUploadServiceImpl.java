package br.com.rrc.cs.rank.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rrc.cs.rank.beans.Evento;
import br.com.rrc.cs.rank.beans.Partida;
import br.com.rrc.cs.rank.service.EventoService;
import br.com.rrc.cs.rank.service.FileUploadService;
import br.com.rrc.cs.rank.service.PartidaService;
import br.com.rrc.cs.rank.service.utils.LogUtil;

@Service
public class FileUploadServiceImpl implements FileUploadService {
	

	@Autowired
	public EventoService eventoService;
	
	@Autowired
	public PartidaService partidaService;
	
	private static final LogUtil LOG = LogUtil.getLog(FileUploadServiceImpl.class);
	
	@Override
	public List<Partida> processarArquivo(List<String> linhas) {
		
		List<Evento> eventos = linhas
				.stream()
				.map(linha  -> eventoService.linha2EventoPartida(linha))
				.collect(Collectors.toList()); 
		
		List<Partida> partidas = partidaService.extrairPartidas(eventos);
		
		LOG.debug("Partidas: %s", partidas);
		return partidas;
	}
}
