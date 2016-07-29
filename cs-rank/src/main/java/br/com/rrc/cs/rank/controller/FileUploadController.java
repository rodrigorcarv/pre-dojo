package br.com.rrc.cs.rank.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.rrc.cs.rank.beans.Partida;
import br.com.rrc.cs.rank.service.FileUploadService;
import br.com.rrc.cs.rank.service.utils.LogUtil;

@RestController
public class FileUploadController {
	
	private static final LogUtil LOG = LogUtil.getLog(FileUploadController.class);
	
	private static final String MENSAGEM_UPLOAD_FALHA  = "Falha ao realizar upload";
	private static final String MENSAGEM_UPLOAD_ARQUIVO_VAZIO =  "Failed to upload, pois a arquivo informado esta vazio";
	
	@Autowired
	private FileUploadService fileUploadService;
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/upload")
	public @ResponseBody List<Partida> fileUpload(@RequestParam("file") MultipartFile multipart, RedirectAttributes redirectAttributes) {

		if (!multipart.isEmpty()) {

			try {
				
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				String caminho = String.format("%s%s%s",dir.getAbsolutePath(), File.separator, multipart.getOriginalFilename());
				
				File serverFile = new File(caminho);
				LOG.info("Caminho do Arquivo: ", caminho);
			        
				Stream<String> linhas = Files.lines(serverFile.toPath());
				
				return fileUploadService.processarArquivo(linhas);
				
			} catch (IOException e) {
				LOG.error(MENSAGEM_UPLOAD_FALHA, e.getMessage(), e);
				throw new RuntimeException(MENSAGEM_UPLOAD_FALHA);
			}
		} else {
			LOG.error(MENSAGEM_UPLOAD_ARQUIVO_VAZIO);
			throw new IllegalArgumentException(MENSAGEM_UPLOAD_ARQUIVO_VAZIO);
		}
	}
}
