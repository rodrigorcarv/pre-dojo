package br.com.rrc.cs.rank.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.rrc.cs.rank.beans.enums.TipoEventoEnum;

public class EventoKiller extends Evento {

	public EventoKiller(String linha) {
		super(linha, TipoEventoEnum.KILL);
	}

	@Override
	public InformacaoLinha analisaEvento() {
		
		Pattern pattern = Pattern.compile("([0-9]{2}/[0-9]{2}/[0-9]{4}\\s+[0-9]{2}:[0-9]{2}:[0-9]{2})\\s+\\W+\\s+(\\S*)\\s+killed\\s+(\\S*)\\s+.*\\s+(\\S*)\\s*$");
		Matcher matcher = getMatcher(pattern, this.getLinha());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime dataKill = LocalDateTime.parse(matcher.group(1), formatter);
		
		String jogadorUm = matcher.group(2);
		String jogadorDois = matcher.group(3);
		String nomeArma = matcher.group(4);
		
		return new InformacaoLinha(dataKill, jogadorUm, jogadorDois, nomeArma);
	}
}
