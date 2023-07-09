package br.ce.cosmocode.core;
/**
 * @author orlando-dev
 */
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import br.ce.cosmocode.core.Propriedades.TipoExecucao;

public class DriverFactory {
	
	private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<WebDriver>() {
		@Override
		protected synchronized WebDriver initialValue() {
			return initDriver();
		}
	};
	
	private DriverFactory() {}
	
	public static WebDriver getDriver() {
		return threadDriver.get();
	}
	
	
	public static WebDriver initDriver(){
		
		WebDriver driver = null;
		
		if(Propriedades.TIPO_EXECUCAO == TipoExecucao.LOCAL) {
			switch (Propriedades.BROWSER) {
				case FIREFOX: driver = new FirefoxDriver(); break;
				case CHROME: driver = new ChromeDriver(); break;
			}
		}
		
		if(Propriedades.TIPO_EXECUCAO == TipoExecucao.GRID) {
			DesiredCapabilities capabilities = null;
			switch (Propriedades.BROWSER) {
				case FIREFOX:capabilities = DesiredCapabilities.firefox(); break;
				case CHROME: capabilities = DesiredCapabilities.chrome(); break;
			}
			
			try {
				driver = new RemoteWebDriver(new URL("http://ip:porta/wd/hub"), capabilities);
			} catch (MalformedURLException stack) {
				System.out.println("Falha na conexão com o GRID");
				stack.printStackTrace();
			}
		}
		driver.manage().window().setSize(new Dimension(1366, 768));			
		return driver;
	}

	public static void killDriver(){
		WebDriver driver = getDriver();
		
		if(driver != null) {
			driver.quit();
			driver = null;
		}
		
		if(threadDriver != null) {
			threadDriver.remove();
		}
	}
	
}
