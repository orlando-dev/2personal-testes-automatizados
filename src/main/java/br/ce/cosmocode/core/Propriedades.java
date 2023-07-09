package br.ce.cosmocode.core;
/**
 * @author orlando-dev
 */
public class Propriedades {
	
public static boolean FECHAR_BROWSER = false;
	
	public static Browsers BROWSER = Browsers.CHROME;
	
	public static TipoExecucao TIPO_EXECUCAO = TipoExecucao.LOCAL;
	
	public enum Browsers {
		CHROME,
		FIREFOX
	}
	
	public enum TipoExecucao {
		LOCAL,
		GRID
	}
}
