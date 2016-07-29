package br.com.rrc.cs.rank.service.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Encapsula as funcionalidades do Log4J para que nao seja necessario adicionar o 
 * verificacao de esta ativo um determinado level de debug e adiciona a funcionalidade 
 * do string format para geracao da mensagem
 */
public class LogUtil {

	Log log;
	
	public static LogUtil getLog(Class<?> clazz){
		return new LogUtil(clazz);
	}
	
	public LogUtil(Class<?> clazz) {
		log = LogFactory.getLog(clazz);
	}
	
	public  void debug(String message, Object...args){
		if (log.isDebugEnabled()){
			log.debug(String.format(message, args));
		}
	}
	public  void info(String message, Object...args){
		if (log.isInfoEnabled()){
			log.info(String.format(message, args));
		}
	}
	public  void trace(String message, Object...args){
		if (log.isTraceEnabled()){
			log.trace(String.format(message, args));
		}
	}
	public  void error(String message, Object...args){
		if (log.isErrorEnabled()){
			log.error(String.format(message, args));
		}
	}
	public  void warn(String message, Object...args){
		if (log.isWarnEnabled()){
			log.warn(String.format(message, args));
		}
	}
}