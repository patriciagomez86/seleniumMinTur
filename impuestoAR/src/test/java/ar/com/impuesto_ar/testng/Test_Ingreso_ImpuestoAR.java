package ar.com.impuesto_ar.testng;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import ar.com.impuesto_ar.selenium.Base_Test;
import ar.com.impuesto_ar.selenium.Ingreso;

/* 
 * Se realizan pruebas en las validaciones de ingreso a la aplicacion" 
 */

/*
 * PRECONDICI�N: 	
 * 				Debe existir en el sistema un usuario con acceso  * 					
 * 					
 */


public class Test_Ingreso_ImpuestoAR {

	private Base_Test driver;	
	private Logger log;
	private String URL_AMBIENTE;

	@Parameters({"browser","ipNodo","url_ambiente"})
	@BeforeClass
	public void startUp(@Optional("chrome") String browser, String ipNodo,String ambiente, ITestContext ctx) throws InterruptedException {			

		log = Logger.getLogger(getClass().getName());
		if (log.isInfoEnabled()) {
			log.info("************** INICIA SUITE DE PRUEBA **************");
			log.info("Suite: " + ctx.getCurrentXmlTest().getSuite().getName());
			log.info("Inicia StartUp");
		}		

		URL_AMBIENTE = ambiente;

		//Configuramos el driver segun el tipo de browser
		driver =  new Base_Test(browser,ipNodo);

		if (log.isInfoEnabled()) {
			log.info("Finaliza StartUp");
		}	
	}

	@Parameters({"usrVacio","passVacio","msjValidacionDatoObligatorio"})
	@Test	
	public void login_con_datos_vacios(String usrVacio, String passVacio, String msjValidacionDatoObligatorio) throws InterruptedException {	
		if (log.isInfoEnabled()) {			
			log.info("Inicia Test:  login_con_datos_vacios");			
		}
		
		Ingreso ingreso = new Ingreso(driver.getDriver(), URL_AMBIENTE);
		ingreso.aniadirUsuario(usrVacio);		
		ingreso.aniadirContrasenia(passVacio);
		ingreso.clickBtnIngreso();
		Assert.assertTrue(ingreso.esVisibleMsjUsrSinDatos(msjValidacionDatoObligatorio));
		
		if(log.isInfoEnabled()){
			log.info("Assert: esVisibleMsjUsrSinDatos() = OK");
		}
		
		Assert.assertTrue(ingreso.esVisibleMsjPassSinDatos(msjValidacionDatoObligatorio));

		if(log.isInfoEnabled()){
			log.info("Assert: esVisibleMsjPassSinDatos() = OK");
		}
		
		if (log.isInfoEnabled()) {
			log.info("Finaliza Test:  login_con_datos_vacios");
		}
	}

	@Parameters({"usrSuperaLimite","passSuperaLimite","msjValidacionLongitudMaxima"})
	@Test	
	public void login_con_datos_supera_longitud(String usrSuperaLimite, String passSuperaLimite, String msjValidacionLongitudMaxima) throws InterruptedException {
		if (log.isInfoEnabled()) {			
			log.info("Inicia Test:  login_con_datos_supera_longitud");			
		}
		
		Ingreso ingreso = new Ingreso(driver.getDriver(), URL_AMBIENTE);
		ingreso.aniadirUsuario(usrSuperaLimite);
		ingreso.aniadirContrasenia(passSuperaLimite);
		
		Assert.assertTrue(ingreso.esVisibleMsjUsrDatoExtenso(msjValidacionLongitudMaxima));
		if(log.isInfoEnabled()){
			log.info("Assert: esVisibleMsjUsrDatoExtenso() = OK");
		}
		
		Assert.assertTrue(ingreso.esVisibleMsjPassDatoExtenso(msjValidacionLongitudMaxima));
		
		if(log.isInfoEnabled()){
			log.info("Assert: esVisibleMsjPassDatoExtenso() = OK");
		}
		
		if (log.isInfoEnabled()) {
			log.info("Finaliza Test:  login_con_datos_supera_longitud");
		}
	}

	@Parameters({"usrNoValido","passNoValido","msjValidacionUsrNoValido"})
	@Test	
	public void login_con_datos_erroneos(String usrNoValido, String passNoValido, String msjValidacionUsrNoValido) throws InterruptedException {	
		if (log.isInfoEnabled()) {			
			log.info("Inicia Test:  login_con_datos_erroneos");			
		}
		
		Ingreso ingreso = new Ingreso(driver.getDriver(), URL_AMBIENTE);
		ingreso.aniadirUsuario(usrNoValido);		
		ingreso.aniadirContrasenia(passNoValido);
		ingreso.clickBtnIngreso();
		
		Thread.sleep(2000);
		
		Assert.assertTrue(ingreso.esVisibleMsjUsrNoValido(msjValidacionUsrNoValido));
		if(log.isInfoEnabled()){
			log.info("Assert: esVisibleMsjUsrNoValido() = OK");
		}
				
		Thread.sleep(2000);
		
		if (log.isInfoEnabled()) {
			log.info("Finaliza Test:  login_con_datos_erroneos");
		}
	}

	@Parameters({"usrCorrecto","passCorrecto"})
	@Test	
	public void login_con_datos_correctos(String usrCorrecto, String passCorrecto) throws InterruptedException {
		if (log.isInfoEnabled()) {			
			log.info("Inicia Test:  login_con_datos_correctos");			
		}
		
		Ingreso ingreso = new Ingreso(driver.getDriver(), URL_AMBIENTE);
		ingreso.login(usrCorrecto, passCorrecto);
		
		Thread.sleep(2000);
		
		if (log.isInfoEnabled()) {
			log.info("Finaliza Test:  login_con_datos_correctos");
		}
	}

	@Parameters({"path_ScreenShotOnFailure"})
	@AfterMethod 
	public void takeScreenShotOnFailure(String path_test_fail, ITestResult testResult) throws IOException { 
		if (testResult.getStatus() == ITestResult.FAILURE) { 		
			if (log.isInfoEnabled()) {
				log.info("Test: " + testResult.getName().toString().trim()+" Estado: " + testResult.getStatus());
			}

			String methodName = testResult.getName().toString().trim();
			String className = getClass().getName().substring(29).toString().trim();	
			//String timeStamp = new SimpleDateFormat("_yyyy_MM_dd_HH_mm_ss").format(new Date());
			File scrFile = ((TakesScreenshot)driver.getDriver()).getScreenshotAs(OutputType.FILE); 
			FileUtils.copyFile(scrFile, new File(path_test_fail + "/" + className + "_" + methodName + ".png"));
			if (log.isInfoEnabled()) {						
				log.info("Se capturo la pantalla luego del error en el test: " + testResult.getName().toString().trim());
				log.info("La imagen se encuentra en: "+path_test_fail + "/" + className + "_" + methodName + ".png");
			}
		} 
	}

	@AfterClass
	public void tearDown() {
		driver.closeDriver();
		if (log.isInfoEnabled()) {						
			log.info("Se cierra el driver");
		}	
	}

}
