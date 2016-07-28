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
import org.springframework.stereotype.Service;

import br.com.rrc.cs.rank.beans.Jogador;
import br.com.rrc.cs.rank.beans.Partida;
import br.com.rrc.cs.rank.beans.enums.TipoEventoEnum;
import br.com.rrc.cs.rank.controller.FileUploadController;
import br.com.rrc.cs.rank.service.FileUploadService;

@Service
public class FileUploadServiceImpl implements FileUploadService {
	
	private Log log = LogFactory.getLog(FileUploadController.class);
	
	@Override
	public List<Partida> processarArquivo(Stream<String> linhas) {
		
		List<String> eventos = linhas.collect(Collectors.toList()); 
		
		Partida partida = null ;
		List<Jogador> jogadores = null;
		List<Partida> partidas = new ArrayList<>();
		
		Pattern patternStart = Pattern.compile("([0-9]{2}/[0-9]{2}/[0-9]{4}\\s+[0-9]{2}:[0-9]{2}:[0-9]{2})\\s*\\W*\\s*New match\\s+([0-9]+)\\s+has started\\s*$");
		Pattern patternEnd = Pattern.compile("([0-9]{2}/[0-9]{2}/[0-9]{4}\\s+[0-9]{2}:[0-9]{2}:[0-9]{2})\\s*\\W*\\s*Match\\s+([0-9]+)\\s+has ended\\s*$");
		Pattern patternKiller = Pattern.compile("([0-9]{2}/[0-9]{2}/[0-9]{4}\\s+[0-9]{2}:[0-9]{2}:[0-9]{2})\\s+\\W+\\s+(\\S*)\\s+killed\\s+(\\S*)\\s+.*\\s+(\\S*)\\s*$");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		
		for (String evento : eventos) {
			
			log.debug(String.format("%s%s", "evento:", evento));
			
			TipoEventoEnum tipoEvento = TipoEventoEnum.buscaTipoEvento(evento);
			log.debug(String.format("%s%s", "Tipo de Evento:", tipoEvento));
			
			
			switch (tipoEvento) {
			
			case START:
				
				Matcher matcherStart = patternStart.matcher(evento);
				
				if (!matcherStart.matches()) {
					throw new IllegalArgumentException(String.format("Linha %s esta invalida", evento));
				}
				
				LocalDateTime dataInicio = LocalDateTime.parse(matcherStart.group(1), formatter);
				partida = new Partida(new Long(matcherStart.group(2)), dataInicio);
				jogadores = new ArrayList<>();
				break;
			
			case END:
				
				Matcher matcherEnd = patternEnd.matcher(evento);

				if (!matcherEnd.matches()) {
					throw new IllegalArgumentException(String.format("Linha %s esta invalida", evento));
				}
				
				LocalDateTime dataFinal = LocalDateTime.parse(matcherEnd.group(1), formatter);
				partida.setDataFim(dataFinal);
				partida.setJogadores(jogadores);
				partidas.add(partida);
				break;
			
			case KILL:

				Matcher matcherKiller = patternKiller.matcher(evento);
				
				if (!matcherKiller.matches()) {
					throw new IllegalArgumentException(String.format("Linha %s esta invalida", evento));
				}
				
				LocalDateTime dataKiller = LocalDateTime.parse(matcherKiller.group(1), formatter);
				Jogador jogador = new Jogador(matcherKiller.group(2));
				jogadores.add(jogador);
				break;
			}
		}
		
		log.debug(partidas);

		System.out.println("Partidas: " + partidas );
		return partidas;
	}
}
