package br.ce.cosmocode.core;
/**
 * @author orlando-dev
 */
import static br.ce.cosmocode.core.DriverFactory.getDriver;
import static br.ce.cosmocode.core.DriverFactory.killDriver;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class BaseTest {
	
	@Rule
	public TestName testName = new TestName();
	
	private String nomeClasse;
	
	private String nomePlanilha = "2Personal - Casos de teste - Funcionais Automatizados - E2E.xlsx";
	
	@Before
	public void inicializar() {
		
	}
	
	@After
	public void finaliza() throws IOException {
		TakesScreenshot screenshot = (TakesScreenshot) getDriver();
		File arquivo = screenshot.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(arquivo, new File("target" + File.separator + "screenshot" +
				File.separator + testName.getMethodName() + ".jpg"));
		
		System.out.println("------------------------------------------------\nNome da classe executada: " + getClass().getSimpleName() + ".java");
		nomeClasse = getClass().getSimpleName();
		
		if(Propriedades.FECHAR_BROWSER) {
			killDriver();
		}
	}
	
	@Rule
    public TestWatcher watcher = new TestWatcher() {
		
        @Override
        protected void succeeded(Description description) {
        	
            System.out.println("\nMétodo: " + description.getMethodName() + " - OK" + "\n------------------------------------------------\n");
        
            try {
				PlanilhaTestes.escreverResultadoTeste(nomePlanilha, nomeClasse + ".java", "OK");
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
        @Override
        protected void failed(Throwable e, Description description) {
        	
            if (e instanceof AssertionError) {
                AssertionError error = (AssertionError) e;
                System.out.println(description.getMethodName() + " - FALHA: " + error.getMessage() + "\n------------------------------------------------\n");
                
                try {
    				PlanilhaTestes.escreverResultadoTeste(nomePlanilha, nomeClasse + ".java", "FALHA: " + error.getMessage());
    			} catch (IOException exception) {
    				exception.printStackTrace();
    			}
                
            } else {
            	System.out.println("\nMétodo: " + description.getMethodName() + " - ERROR: " + e.getMessage().split("\n")[0] + "\n------------------------------------------------\n");
                try {
    				PlanilhaTestes.escreverResultadoTeste(nomePlanilha, nomeClasse + ".java", "ERROR: " + e.getMessage().split("\n")[0]);
                } catch (IOException exception) {
    				exception.printStackTrace();
    			}
            }
        }
        
        @Override
        protected void skipped(org.junit.AssumptionViolatedException e, Description description) {
            
        	System.out.println("\nMétodo: " + description.getMethodName() + " - SKIP" + "\n------------------------------------------------\n");
           
            try {
				PlanilhaTestes.escreverResultadoTeste(nomePlanilha, nomeClasse + ".java", "SKIP");
            } catch (IOException exception) {
				exception.printStackTrace();
			}
        }
    };
}
