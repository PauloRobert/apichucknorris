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

//Ordenar os testes com Junit
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ListarCategoriasJunit4Test {

	String urlBase = "https://api.chucknorris.io/jokes/";

	static ExtentTest test;
	static ExtentReports report;

	@BeforeClass
	public static void startTest() {
		report = new ExtentReports(System.getProperty("user.dir") + "\\relatorios\\ListarCategorias_"
				+ utils.metodoGenerico.dataHoraParaArquivo() + ".html", true);
		report.loadConfig(new File(System.getProperty("user.dir") + "\\relatorios\\configuracao\\extent-config.xml"));

	}

	@Test
	public void CT01_ListarCategorias() {

		test = report.startTest("CT01_ListarCategorias");

		System.out.println("#### CT01 - Listar Categorias ####\n");

		Response response = given().contentType("application/json").get(urlBase + "categories");
		test.log(LogStatus.INFO, "Chamando a Api", urlBase);

		List<String> QuantidadeCategorias = response.jsonPath().get("$");

		System.out.println("Quantidade de Categorias: " + QuantidadeCategorias.size() + "\n");

		if (response.getStatusCode() == 200) {
			test.log(LogStatus.PASS, "Retorno", response.jsonPath().getString("$"));
			test.log(LogStatus.INFO, "Status Code", "200");
			test.assignAuthor("Paulo Roberto");
			test.assignCategory("Api");

		} else {
			test.log(LogStatus.FAIL, "Falha ao Chamar a Api", response.getBody().prettyPrint());

		}

	}


	@Test
	public void CT02_PiadaAleatoria() {

		test = report.startTest("CT02_Piadas Aleatórias");

		System.out.println("#### CT02 - Piadas Aleatórias ####\n");


		for (int i = 1; i < 10; i++) {
			Response response = given().contentType("application/json").get(urlBase + "random");
			test.log(LogStatus.INFO, "Chamada ",  urlBase);

			if (response.getStatusCode() == 200) {
				test.log(LogStatus.PASS, "Piada ", + i + " - " + response.jsonPath().getString("value"));
			} else {
				test.log(LogStatus.FAIL, "Falha ao Chamar a Api", response.getBody().prettyPrint());

			}
		}
	}



	public void CT03_PiadaPorCategoria() {

		test = report.startTest("CT03_Piadas por Categoria");

		System.out.println("#### CT02 - Piadas Por Categoria ####\n");


		for (int i = 1; i < 10; i++) {
			Response response = given().contentType("application/json").get(urlBase + "random?category={'religion'}");
			test.log(LogStatus.INFO, "Chamada ",  urlBase);

			if (response.getStatusCode() == 200) {
				test.log(LogStatus.PASS, "Piada ", + i + " - " + response.jsonPath().getString("value"));
				test.log(LogStatus.PASS, "Categoria ", + i + " - " + response.jsonPath().getString("categories"));

			} else {
				test.log(LogStatus.FAIL, "Falha ao Chamar a Api", response.getBody().prettyPrint());

			}
		}
	}

	@AfterClass
	public static void endTest() {
		report.endTest(test);
		report.flush();

	}

}
