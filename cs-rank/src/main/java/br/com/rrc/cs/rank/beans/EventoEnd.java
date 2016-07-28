package br.com.rrc.cs.rank.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.rrc.cs.rank.beans.enums.TipoEventoEnum;

public class EventoEnd extends Evento {

	public EventoEnd(String linha) {
		super(linha, TipoEventoEnum.END);
		this.informacaoLinha = analisaEvento(linha);
	}

	@Override
	public InformacaoLinha analisaEvento(String linha) {
		
		Pattern pattern = Pattern.compile("([0-9]{2}/[0-9]{2}/[0-9]{4}\\s+[0-9]{2}:[0-9]{2}:[0-9]{2})\\s*\\W*\\s*Match\\s+([0-9]+)\\s+has ended\\s*$");
		Matcher matcher = getMatcher(pattern, linha);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dataFinal = LocalDateTime.parse(matcher.group(1), formatter);
		
		Long numeroPartida = new Long(matcher.group(2));
		
		return new InformacaoLinha(numeroPartida, dataFinal);
	}
}
