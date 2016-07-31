package br.com.rrc.cs.rank.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.rrc.cs.rank.beans.enums.TipoEventoEnum;

public class EventoStart extends Evento {
	
	public EventoStart(String linha) {
		super(linha, TipoEventoEnum.START); 
	}

	@Override
	public InformacaoLinha analisaEvento() {
		
		Pattern pattern = Pattern.compile("([0-9]{2}/[0-9]{2}/[0-9]{4}\\s+[0-9]{2}:[0-9]{2}:[0-9]{2})\\s*\\W*\\s*New match\\s+([0-9]+)\\s+has started\\s*$");
		Matcher matcher = getMatcher(pattern, this.getLinha());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dataInicio = LocalDateTime.parse(matcher.group(1), formatter);
		
		Long numeroPartida = new Long(matcher.group(2));

		return new InformacaoLinha(numeroPartida, dataInicio);
	}
}
