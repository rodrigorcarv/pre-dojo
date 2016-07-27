package br.com.rrc.cs.rank.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.rrc.cs.rank.beans.EstatisticaPartida;
import br.com.rrc.cs.rank.service.FileUploadService;

@RestController
public class FileUploadController {
	
	@Autowired
	private FileUploadService fileUploadService;
	
	
	private Log log = LogFactory.getLog(FileUploadController.class);
	private static final String MENSAGEM_UPLOAD_FALHA  = "Falha ao realizar upload";
	private static final String MENSAGEM_UPLOAD_ARQUIVO_VAZIO =  "Failed to upload, pois a arquivo informado esta vazio";
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/upload")
	public @ResponseBody EstatisticaPartida fileUpload(@RequestParam("file") MultipartFile multipart, RedirectAttributes redirectAttributes) {

		EstatisticaPartida estatisticaPartida = null;
		
		if (!multipart.isEmpty()) {

			try {
				
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				String caminho = String.format("%s%s%s",dir.getAbsolutePath(), File.separator, multipart.getOriginalFilename());
				
				File serverFile = new File(caminho);
				log.debug(String.format("%s", caminho));
			        
				Stream<String> linhas = Files.lines(serverFile.toPath());
				
				estatisticaPartida = fileUploadService.getEstatisticaPartida(linhas);
				
			} catch (IOException e) {
				log.error(String.format("%s%s", MENSAGEM_UPLOAD_FALHA, e.getMessage()), e);
				throw new RuntimeException(MENSAGEM_UPLOAD_FALHA);
			}
		} else {
			log.error(MENSAGEM_UPLOAD_ARQUIVO_VAZIO);
			throw new IllegalArgumentException(MENSAGEM_UPLOAD_ARQUIVO_VAZIO);
		}
		return estatisticaPartida;
	}
}
