package ar.com.impuesto_ar.selenium;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class DGA {
	private static String URL_PAGINA_LOGIN = "/login";
	private WebDriver driver;
	private WebDriverWait wait;
	private Logger log;


	@CacheLookup	    
	@FindBy(id = "username")	    
	private WebElement txtUsuario;	    
	@FindBy(id = "password")
	private WebElement txtContrasenia;
	@FindBy(xpath = "//input[@type='submit'][@value='Ingresar']")	
	private WebElement btnLoginBoton;

	@FindBy(xpath = "//small[@data-fv-validator='notEmpty'][@data-fv-for = 'username']")	
	private WebElement msjUsrSinDatos;	 
	
	@FindBy(xpath = "//small[@data-fv-validator='notEmpty'][@data-fv-for = 'password']")	
	private WebElement msjPassSinDatos;

	
	@FindBy(xpath = "//small[@data-fv-validator='stringLength'][@data-fv-for = 'username']")
	private WebElement msjUsrDatoExtenso;
	
	@FindBy(xpath = "//small[@data-fv-validator='stringLength'][@data-fv-for = 'password']")
	private WebElement msjPassDatoExtenso;

	@FindBy(xpath = "//div[@class='text-center alert alert-danger']")	
	private WebElement msjUsrNoValido;	


	public DGA(WebDriver driver, String url) {
		this.driver = driver;
		//se eliminan las cookies del browser
		this.driver.manage().deleteAllCookies();
		driver.get(url + URL_PAGINA_LOGIN);
		wait = new WebDriverWait(driver,5);
		PageFactory.initElements(this.driver, this);
		log = Logger.getLogger(getClass().getName());
	}

	//Se realiza el refresh de la pagina
	public void refreshPagina() {
		//Se refresca la pagina
		this.driver.navigate().refresh();		
	}

	public DGA aniadirUsuario(String nombreDeUsuario) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(txtUsuario));
			this.txtUsuario.clear();
			this.txtUsuario.sendKeys(nombreDeUsuario);
			return this;
		} catch (Exception e) {
			if (log.isDebugEnabled()) {				
				log.debug("No se encontro txt Username");			 
				log.debug("Se reinicia la pagina");
			}
			refreshPagina();
			wait.until(ExpectedConditions.elementToBeClickable(txtUsuario));
			this.txtUsuario.clear();
			this.txtUsuario.sendKeys(nombreDeUsuario);			 
			return this;			 		
		}				 
	}

	public DGA aniadirContrasenia(String contrasenia) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(txtContrasenia));
			this.txtContrasenia.clear();
			this.txtContrasenia.sendKeys(contrasenia);
			return this;
		} catch (Exception e) {
			log.error("No se encontro txt passsword - " +e);
			return this;
		}		
	}

	public DGA login(String nombreDeUsuario, String contrasenia) throws InterruptedException {
		try { 
			aniadirUsuario(nombreDeUsuario);
			aniadirContrasenia(contrasenia);			 			
			wait.until(ExpectedConditions.elementToBeClickable(btnLoginBoton));			 
			Thread.sleep(1000);
			this.btnLoginBoton.click();
			Thread.sleep(1000);			 
			return this;	
		} catch (Exception e1) {
			log.error("Ocurrio un error en el login - " +e1);
			return this;
		}

	}
	
	public DGA clickBtnIngreso() throws InterruptedException {
		try { 						 			
			wait.until(ExpectedConditions.elementToBeClickable(btnLoginBoton));			 
			Thread.sleep(1000);
			this.btnLoginBoton.click();
			Thread.sleep(1000);			 
			return this;	
		} catch (Exception e1) {
			log.error("Ocurrio un error en el click del btn de ingreso - " +e1);
			return this;
		}

	}	

	public boolean esVisibleMsjUsrSinDatos(String msjValidacion)  throws InterruptedException  {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(msjUsrSinDatos));			
			if (msjUsrSinDatos.isDisplayed()) {				
				if (msjUsrSinDatos.getText().equals(msjValidacion)) {
					return true;
				}			
			}			
		} catch (Exception e) {
			log.error("Ocurrio un error al buscar mensaje de Validación = " + msjValidacion +" - " +e);			
		}		
		return false;
	}
	
	public boolean esVisibleMsjPassSinDatos(String msjValidacion)  throws InterruptedException  {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(msjPassSinDatos));			
			if (msjPassSinDatos.isDisplayed()) {				
				if (msjPassSinDatos.getText().equals(msjValidacion)) {
					return true;
				}			
			}			
		} catch (Exception e) {
			log.error("Ocurrio un error al buscar mensaje de Validación = " + msjValidacion +" - " +e);			
		}		
		return false;
	}

	public boolean esVisibleMsjUsrDatoExtenso(String msjValidacion)  throws InterruptedException  {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(msjUsrDatoExtenso));			
			if (msjUsrDatoExtenso.isDisplayed()) {				
				if (msjUsrDatoExtenso.getText().equals(msjValidacion)) {
					return true;
				}			
			}			
		} catch (Exception e) {
			log.error("Ocurrio un error al buscar mensaje de Validación = " + msjValidacion +" - " +e);			
		}		
		return false;
	}
	
	public boolean esVisibleMsjPassDatoExtenso(String msjValidacion)  throws InterruptedException  {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(msjPassDatoExtenso));			
			if (msjPassDatoExtenso.isDisplayed()) {				
				if (msjPassDatoExtenso.getText().equals(msjValidacion)) {
					return true;
				}			
			}			
		} catch (Exception e) {
			log.error("Ocurrio un error al buscar mensaje de Validación = " + msjValidacion +" - " +e);			
		}		
		return false;
	}

	public boolean esVisibleMsjUsrNoValido(String msjValidacion)  throws InterruptedException  {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(msjUsrNoValido));			
			if (msjUsrNoValido.isDisplayed()) {				
				if (msjUsrNoValido.getText().equals(msjValidacion)) {
					return true;
				}			
			}			
		} catch (Exception e) {
			log.error("Ocurrio un error al buscar mensaje de Validación = " + msjValidacion +" - " +e);			
		}		
		return false;
	}

}
