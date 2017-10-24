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
import ar.com.impuesto_ar.selenium.MenuPrincipal;

/* 
 * Se realizan pruebas en la navegacion del menu principal de la aplicacion" 
 */

/*
 * PRECONDICI�N: 	
 * 				Debe existir en el sistema un usuario con acceso  * 					
 * 					
 */


public class Test_Navegacion_ImpuestoAR {

	private Base_Test driver;	
	private Logger log;
	private String URL_AMBIENTE;
	private Ingreso ingreso ;

	@Parameters({"browser","ipNodo","url_ambiente","usr","pass"})
	@BeforeClass
	public void startUp(@Optional("chrome") String browser, String ipNodo,String ambiente, String usr, String pass, ITestContext ctx) throws InterruptedException {			

		log = Logger.getLogger(getClass().getName());
		if (log.isInfoEnabled()) {
			log.info("************** INICIA SUITE DE PRUEBA **************");
			log.info("Suite: " + ctx.getCurrentXmlTest().getSuite().getName());
			log.info("Inicia StartUp");
		}		

		URL_AMBIENTE = ambiente;

		//Configuramos el driver segun el tipo de browser
		driver =  new Base_Test(browser,ipNodo);
		
		//ingresamos al ambiente
		ingreso = new Ingreso(driver.getDriver(), URL_AMBIENTE);
		ingreso.login(usr, pass);
		
		MenuPrincipal menu = new MenuPrincipal(driver.getDriver(), URL_AMBIENTE);
		Assert.assertTrue(menu.esVisibleMenuPrincipal());
		if(log.isInfoEnabled()){
			log.info("Assert: esVisibleMenuPrincipal() = OK");
		}
		
		Thread.sleep(2000);

		if (log.isInfoEnabled()) {
			log.info("Finaliza StartUp");
		}	
	}
		

	@Test	
	public void irVR() throws InterruptedException {	
		if (log.isInfoEnabled()) {			
			log.info("Inicia Test:  irVR");			
		}
		
		MenuPrincipal menu = new MenuPrincipal(driver.getDriver(), URL_AMBIENTE);
		
		menu.irVR();
		
		Thread.sleep(2000);
			
		if (log.isInfoEnabled()) {
			log.info("Finaliza Test:  irVR");
		}
	}
	
	@Test	
	public void irGeneral() throws InterruptedException {	
		if (log.isInfoEnabled()) {			
			log.info("Inicia Test:  irGeneral");			
		}
		
		MenuPrincipal menu = new MenuPrincipal(driver.getDriver(), URL_AMBIENTE);
		
		menu.irGeneral();
		
		Thread.sleep(2000);
			
		if (log.isInfoEnabled()) {
			log.info("Finaliza Test:  irGeneral");
		}
	}
	
	@Test	
	public void irSalir() throws InterruptedException {	
		if (log.isInfoEnabled()) {			
			log.info("Inicia Test:  irSalir");			
		}
		
		MenuPrincipal menu = new MenuPrincipal(driver.getDriver(), URL_AMBIENTE);		
		
		menu.irSalir();
		
		Assert.assertTrue(menu.esVisiblePopupSalir());
		if(log.isInfoEnabled()){
			log.info("Assert: esVisiblePopupSalir() = OK");
		}
		
		Thread.sleep(1000);
		
		menu.clickBtnCerrarPopupLogout();
			
		Thread.sleep(1000);
		
		if (log.isInfoEnabled()) {
			log.info("Finaliza Test:  irSalir");
		}
	}
	
	@Test	
	public void cancelarLogout() throws InterruptedException {	
		if (log.isInfoEnabled()) {			
			log.info("Inicia Test:  irSalir");			
		}
		
		MenuPrincipal menu = new MenuPrincipal(driver.getDriver(), URL_AMBIENTE);		
		
		menu.irSalir();
		
		Assert.assertTrue(menu.esVisiblePopupSalir());
		if(log.isInfoEnabled()){
			log.info("Assert: esVisiblePopupSalir() = OK");
		}
		
		Thread.sleep(1000);
		
		menu.clickBtnCancelarLogout();
		
		Assert.assertTrue(menu.esVisibleMenuPrincipal());
		if(log.isInfoEnabled()){
			log.info("Assert: esVisibleMenuPrincipal() = OK");
		}
			
		Thread.sleep(1000);
		
		if (log.isInfoEnabled()) {
			log.info("Finaliza Test:  irSalir");
		}
	}
	
	@Parameters({"msjValidacionLogout"})
	@Test	
	public void realizarLogout(String msjValidacionLogout) throws InterruptedException {	
		if (log.isInfoEnabled()) {			
			log.info("Inicia Test:  irSalir");			
		}
		
		MenuPrincipal menu = new MenuPrincipal(driver.getDriver(), URL_AMBIENTE);		
		
		menu.irSalir();
		
		Assert.assertTrue(menu.esVisiblePopupSalir());
		if(log.isInfoEnabled()){
			log.info("Assert: esVisiblePopupSalir() = OK");
		}
		
		Thread.sleep(1000);
		
		menu.clickBtnConfirmarLogout();
		
		
		Assert.assertTrue(ingreso.esVisibleMsjUsrLogout(msjValidacionLogout));
		if(log.isInfoEnabled()){
			log.info("Assert: esVisibleMsjUsrLogout() = OK");
		}
			
		Thread.sleep(1000);
		
		if (log.isInfoEnabled()) {
			log.info("Finaliza Test:  irSalir");
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
