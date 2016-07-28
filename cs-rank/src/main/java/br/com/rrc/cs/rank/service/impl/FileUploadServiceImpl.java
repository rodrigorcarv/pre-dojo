package br.com.rrc.cs.rank.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rrc.cs.rank.beans.Evento;
import br.com.rrc.cs.rank.beans.Jogador;
import br.com.rrc.cs.rank.beans.InformacaoLinha;
import br.com.rrc.cs.rank.beans.Partida;
import br.com.rrc.cs.rank.controller.FileUploadController;
import br.com.rrc.cs.rank.service.EventoService;
import br.com.rrc.cs.rank.service.FileUploadService;

@Service
public class FileUploadServiceImpl implements FileUploadService {
	
	private Log log = LogFactory.getLog(FileUploadController.class);
	
	@Autowired
	EventoService eventoService;
	
	@Override
	public List<Partida> processarArquivo(Stream<String> linhas) {
		
		List<Evento> eventos = linhas
				.map(linha  -> eventoService.linha2EventoPartida(linha))
				.collect(Collectors.toList()); 
		
		Partida partida = null ;
		List<Partida> partidas = new ArrayList<>();
		
		Pattern patternEnd = Pattern.compile("([0-9]{2}/[0-9]{2}/[0-9]{4}\\s+[0-9]{2}:[0-9]{2}:[0-9]{2})\\s*\\W*\\s*Match\\s+([0-9]+)\\s+has ended\\s*$");
		Pattern patternKiller = Pattern.compile("([0-9]{2}/[0-9]{2}/[0-9]{4}\\s+[0-9]{2}:[0-9]{2}:[0-9]{2})\\s+\\W+\\s+(\\S*)\\s+killed\\s+(\\S*)\\s+.*\\s+(\\S*)\\s*$");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		
		for (Evento evento : eventos) {
			
			log.debug(String.format("%s%s", "evento:", evento));

			InformacaoLinha infLinha = evento.analisaEvento(); 
			
			switch (evento.getTipoEventoEnum()) {
			
			case START:
				partida = new Partida(infLinha.getNumeroPartida(), infLinha.getData());
				break;
			
			case END:
				
				partida.finalizarPartida(infLinha.getNumeroPartida(), infLinha.getData());
				partidas.add(partida);
				break;
			
			case KILL:

				partida.killer(
						infLinha.getData(), 
						new Jogador(infLinha.getJogadorUm()), 
						new Jogador(infLinha.getJogadorDois()), 
						infLinha.getNomeArma());
				break;
			}
		}
		
		log.debug(partidas);

		System.out.println("Partidas: " + partidas );
		return partidas;
	}
}
