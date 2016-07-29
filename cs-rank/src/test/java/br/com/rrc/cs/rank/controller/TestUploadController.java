package br.com.rrc.cs.rank.controller;

import static com.jayway.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.RestAssured;

import br.com.rrc.cs.rank.CsRankApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CsRankApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class TestUploadController {
	
	@Value("${local.server.port}")
	private int port;

	@Before
	public void setUp() {
		RestAssured.port = port;
	}	

	@Test
	public void uploadFileTest() throws IOException, URISyntaxException {
		
		Path caminho = Paths.get(ClassLoader.getSystemResource("logGame.txt").toURI());
		File file = caminho.toFile();
		
		given().
			multiPart("file", file).
		expect().
				statusCode(HttpStatus.SC_OK).
		when().
		        post("/upload");
	}
	
	@Test
	public void uploadFileTestArquivoVazio() throws IOException, URISyntaxException {
		
		Path caminho = Paths.get(ClassLoader.getSystemResource("logGameVazio.txt").toURI());
		File file = caminho.toFile();
		
		given().
			multiPart("file", file).
		expect().
				statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR).
		when().
		        post("/upload");
	}
}
