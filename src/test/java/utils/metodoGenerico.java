package utils;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class metodoGenerico {
	
	static ExtentTest test;
	static ExtentReports report;
	
	public static String dataHoraParaArquivo() {

		Timestamp ts = new Timestamp(System.currentTimeMillis());
		return new SimpleDateFormat("dd-mm-yyyy-hhmm").format(ts);

	}
	
	public static String escreveRelatorio() {
		
			report = new ExtentReports(System.getProperty("user.dir") + "\\relatorios\\ListarCategorias" + utils.metodoGenerico.dataHoraParaArquivo() + ".html", true);
			report.loadConfig(new File(System.getProperty("user.dir") + "\\relatorios\\configuracao\\extent-config.xml"));
		return escreveRelatorio();
		
	}

}
