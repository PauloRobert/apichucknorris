package br.com.automacao.ChuckNorris;

import static com.jayway.restassured.RestAssured.given;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

//Ordenar os testes com Junit
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ListarCategorias {

	String urlBase = "https://api.chucknorris.io/jokes/categories";

	static ExtentTest test;
	static ExtentReports report;

	@BeforeClass
	public static void startTest() {
		report = new ExtentReports(System.getProperty("user.dir") + "\\ListarCategorias.html", true);
		report.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));

	}

	@Test
	public void CT01_ListarCategorias() {

		test = report.startTest("CT01_ListarCategorias");

		System.out.println("#### CT01 - Listar Categorias ####\n");

		Response response = given().contentType("application/json").get(urlBase);
		test.log(LogStatus.INFO, "Chamando a Api", urlBase);

		JsonPath QuantidadeCategorias = new JsonPath(response.asString());

		System.out.println("Quantidade de Cateorias: " + QuantidadeCategorias);
		if (response.getStatusCode() == 200) {
			test.log(LogStatus.PASS, "Retorno", response.getBody().prettyPrint());
			test.assignAuthor("Paulo Roberto");
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
