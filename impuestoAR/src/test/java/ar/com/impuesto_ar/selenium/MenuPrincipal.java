package ar.com.impuesto_ar.selenium;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MenuPrincipal {

	private WebDriver driver;
	private WebDriverWait wait;
	private Logger log;


	@CacheLookup	    
	@FindBy(id = "logout")	    
	private WebElement menuSalir;	    

	@FindBy(xpath = "//a[@href='/']")	
	private WebElement menuInicio;

	@FindBy(xpath = "//a[@href='#'][text()='VR']")	
	private WebElement menuVR;

	@FindBy(xpath = "//a[@href='#'][text()='General']")	
	private WebElement menuGeneral;

	@FindBy(xpath = "//div[@class='modal-content']")	
	private WebElement popupSalir;

	@FindBy(id = "menu")	    
	private WebElement menuPrincipal;	
	
	@FindBy(xpath = "//button[@class='bootbox-close-button close']")	
	private WebElement btnCerrarPopup;
	
	@FindBy(xpath = "//button[@data-bb-handler='cancel']")	
	private WebElement btnCancelarLogout;
	
	@FindBy(xpath = "//button[@data-bb-handler='confirm']")	
	private WebElement btnConfirmarLogout;
	
	


	public MenuPrincipal(WebDriver driver, String url) {
		this.driver = driver;
		//se eliminan las cookies del browser
		this.driver.manage().deleteAllCookies();
		wait = new WebDriverWait(driver,5);
		PageFactory.initElements(this.driver, this);
		log = Logger.getLogger(getClass().getName());
	}

	//Se realiza el refresh de la pagina
	public void refreshPagina() {
		//Se refresca la pagina
		this.driver.navigate().refresh();		
	}

	public boolean esVisibleMenuPrincipal()  throws InterruptedException  {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(menuPrincipal));			
			if (menuPrincipal.isDisplayed()) {								
				return true;

			}			
		} catch (Exception e) {
			log.error("Ocurrio un error al buscar el menu principal - " +e);			
		}		
		return false;
	}
	
	public boolean esVisiblePopupSalir()  throws InterruptedException  {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(popupSalir));			
			if (popupSalir.isDisplayed()) {								
				return true;

			}			
		} catch (Exception e) {
			log.error("Ocurrio un error al buscar el popup de Logout - " +e);			
		}		
		return false;
	}		
	
	public MenuPrincipal clickBtnCerrarPopupLogout() throws InterruptedException {
		try { 						 			
			wait.until(ExpectedConditions.elementToBeClickable(btnCerrarPopup));			 
			Thread.sleep(1000);
			this.btnCerrarPopup.click();
			Thread.sleep(1000);			 
			return this;	
		} catch (Exception e1) {
			log.error("Ocurrio un error en el click del btn de cerrar popup Logout - " +e1);
			return this;
		}

	}
	
	public MenuPrincipal clickBtnCancelarLogout() throws InterruptedException {
		try { 						 			
			wait.until(ExpectedConditions.elementToBeClickable(btnCancelarLogout));			 
			Thread.sleep(1000);
			this.btnCancelarLogout.click();
			Thread.sleep(1000);			 
			return this;	
		} catch (Exception e1) {
			log.error("Ocurrio un error en el click del btn de cancelar Logout - " +e1);
			return this;
		}

	}
	
	public MenuPrincipal clickBtnConfirmarLogout() throws InterruptedException {
		try { 						 			
			wait.until(ExpectedConditions.elementToBeClickable(btnConfirmarLogout));			 
			Thread.sleep(1000);
			this.btnConfirmarLogout.click();
			Thread.sleep(1000);			 
			return this;	
		} catch (Exception e1) {
			log.error("Ocurrio un error en el click del btn de confirmar Logout - " +e1);
			return this;
		}

	}	


	public MenuPrincipal irInicio() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(menuInicio));
			this.menuInicio.click();			
			return this;
		} catch (Exception e) {
			log.error("No se encontro Opcion Menu Inicio - " +e);
			return this;		
		}				 
	}

	public MenuPrincipal irSalir() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(menuSalir));
			this.menuSalir.click();			
			return this;
		} catch (Exception e) {
			log.error("No se encontro Opcion Menu Salir - " +e);
			return this;		
		}				 
	}

	public MenuPrincipal irGeneral() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(menuGeneral));
			this.menuGeneral.click();			
			return this;
		} catch (Exception e) {
			log.error("No se encontro Opcion Menu General - " +e);
			return this;		
		}				 
	}

	public MenuPrincipal irVR() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(menuVR));
			this.menuVR.click();			
			return this;
		} catch (Exception e) {
			log.error("No se encontro Opcion Menu VR - " +e);
			return this;		
		}				 
	}

}
