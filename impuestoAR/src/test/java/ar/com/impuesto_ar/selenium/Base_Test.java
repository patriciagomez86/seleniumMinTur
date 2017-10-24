package ar.com.impuesto_ar.selenium;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.net.MalformedURLException;

public class Base_Test {
	
	protected WebDriver driver;	   
    protected enum browsers {chrome, iexplorer}
    private String sBrowser;
    private Logger log;
    
    public Base_Test(String browser, String ipNodo) {
    	this.sBrowser = browser.toLowerCase();
    	log = Logger.getLogger(getClass().getName());
    	
        switch (browsers.valueOf(sBrowser)) {
            case chrome:
                /**
                 * Para configurar el Driver de Chrome tenemos que seguir varios pasos:
                 * Descargar el driver desde: https://sites.google.com/a/chromium.org/chromedriver/downloads
                 * Guardar en una carpeta y obtener la direccion hacia nuestro driver
                 * Y pasarle como propiedad esa direccion al driver
                 */
            	//Ahora es necesario ingresar el path al levantar el proceso del nodo
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                try {
                	driver = new RemoteWebDriver (new URL(ipNodo), capabilities);
                } catch (MalformedURLException e) {
                	log.error("Ocurrio un error al inicializar driver en nodo - "  + e);
                	// TODO Auto-generated catch block
                	e.printStackTrace();
                }
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);               
                break;                            
                
            case iexplorer:
            	//Ahora es necesario ingresar el path al levantar el proceso del nodo          
                
                capabilities = DesiredCapabilities.internetExplorer();								                
                capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS,"true");        		        		    		        		                    	
                capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,"true");
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
               
                try {
                	driver = new RemoteWebDriver (new URL(ipNodo), capabilities);
                } catch (MalformedURLException e) {
                	log.error("Ocurrio un error al inicializar driver en nodo - " + e);
                	// TODO Auto-generated catch block
                	e.printStackTrace();
                }
                
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);                

                break;
        }
    }
    
    public WebDriver getDriver() {
    	return driver;
    }

    public String getBrowser() {
    	return this.sBrowser;
    }
    
    public void refrescarPagina() {
    	this.driver.navigate().refresh();
    }

    public void closeDriver() {
    	driver.close();
    	driver.quit();	    	
    	finalizarProcesoDriver();
    }

    private void finalizarProcesoDriver() {
    	try {   
    		    		 
            switch (browsers.valueOf(this.sBrowser)) {
                case iexplorer:
                	Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
                	break;
                case chrome:
                	Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
                	break;
            }
    	} catch (IOException e) {
    		log.error("Ocurrio un error al matar el proceso -" + e);
    		e.printStackTrace();
    	}
    }
}

