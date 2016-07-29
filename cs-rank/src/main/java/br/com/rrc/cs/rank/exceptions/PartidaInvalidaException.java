package br.com.rrc.cs.rank.exceptions;

public class PartidaInvalidaException extends RuntimeException {

	/**
	 * Serial.
	 */
	private static final long serialVersionUID = 1L;

	private static final String MSG_ERR0_PARTIDA_INVALIDA = "A partida %s informada é invalida";

	public PartidaInvalidaException(String mensagem) {
		super (mensagem);
	}
	
	public PartidaInvalidaException(Long numeroPartida) {
		super (String.format(MSG_ERR0_PARTIDA_INVALIDA , numeroPartida));
	}
}
