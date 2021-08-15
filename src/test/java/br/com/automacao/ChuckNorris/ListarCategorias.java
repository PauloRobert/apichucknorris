package br.com.automacao.ChuckNorris;

import static com.jayway.restassured.RestAssured.given;

import java.io.File;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.jayway.restassured.response.Response;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import br.com.automacao.ChuckNorris.*;

//Ordenar os testes com Junit
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ListarCategorias {

	String urlBase = "https://api.chucknorris.io/jokes/categories";

	static ExtentTest test;
	static ExtentReports report;
	
	
	
	

	@BeforeClass
	public static void startTest() {
		report = new ExtentReports(System.getProperty("user.dir") + "\\relatorios\\ListarCategorias" + utils.metodoGenerico.dataHoraParaArquivo() + ".html", true);
		report.loadConfig(new File(System.getProperty("user.dir") + "\\relatorios\\configuracao\\extent-config.xml"));

	}

	@Test
	public void CT01_ListarCategorias() {

		test = report.startTest("CT01_ListarCategorias");

		System.out.println("#### CT01 - Listar Cateddgorias ####\n");

		Response response = given().contentType("application/json").get(urlBase);
		test.log(LogStatus.INFO, "Chamando a Api", urlBase);

		List<String> QuantidadeCategorias = response.jsonPath().get("$");

		System.out.println("Quantidade de Categorias: " + QuantidadeCategorias.size() + "\n");
		// System.out.println("Categorias: " + response.prettyPrint());
		
		System.out.println(System.getProperty("java.home"));

		if (response.getStatusCode() == 200) {
			test.log(LogStatus.PASS, "Retorno", response.jsonPath().getString("$"));
			test.log(LogStatus.INFO, "Status Code", "200");
			test.assignAuthor("Jorge Souza");
			test.assignCategory("Api");

		} else {
			test.log(LogStatus.FAIL, "Falha ao Chamar a Api", response.getBody().prettyPrint());

		}

	}

	@AfterClass
	public static void endTest() {
		report.endTest(test);
		report.flush();

	}

}
