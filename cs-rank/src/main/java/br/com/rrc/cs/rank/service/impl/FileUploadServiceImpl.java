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

			InformacaoLinha informacaoLinha = evento.getInformacaoLinha(); 
			
			switch (evento.getTipoEventoEnum()) {
			
			
			case START:
				partida = new Partida(informacaoLinha.getNumeroPartida(), informacaoLinha.getData());
				break;
			
			case END:

				Matcher matcherEnd = getMatcher(patternEnd, evento.getLinha());

				LocalDateTime dataFinal = LocalDateTime.parse(matcherEnd.group(1), formatter);
				partida.setDataFim(dataFinal);
				partidas.add(partida);
				break;
			
			case KILL:

				Matcher matcherKiller = getMatcher(patternKiller, evento.getLinha());
				
				LocalDateTime dataKiller = LocalDateTime.parse(matcherKiller.group(1), formatter);
				Jogador jogador = new Jogador(matcherKiller.group(2));
				partida.adicionaJogar(jogador);
				break;
			}
		}
		
		log.debug(partidas);

		System.out.println("Partidas: " + partidas );
		return partidas;
	}
	
	/**
	 * Obtem o {@link Matcher} com base nos paramentros informados
	 * 
	 * @param pattern {@link Pattern} 
	 * @param token texto a ser virificado se atendo o  {@link Pattern} 
	 * @return Retorna o {@link o Matcher} do token
	 * 
	 * @throws IllegalArgumentException Exececao sera disparada caso nao ocorra o {@link Matcher} 
	 *                                  do token.
	 */
	private Matcher getMatcher(Pattern pattern, String token) {

		final Matcher matcher = pattern.matcher(token);
		
		if (!matcher.matches()) {
			throw new IllegalArgumentException(String.format("InformacaoLinha %s esta invalida", token));
		}
		
		return matcher;
	}
	
}
