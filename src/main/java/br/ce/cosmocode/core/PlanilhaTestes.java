package br.ce.cosmocode.core;
/**
 * @author orlando-dev
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.ce.cosmocode.utils.DataUtils;

public class PlanilhaTestes {

	@SuppressWarnings("resource")
	public static void escreverResultadoTeste(String nomePlanilha, String nomeClasseTeste, String resultado) throws IOException {
    
		File file = new File("src/main/resources/" + nomePlanilha);

        FileInputStream inputStream = new FileInputStream(file);
        
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        
        XSSFSheet sheet = workbook.getSheetAt(0);
        
        int nomeColuna = -1;
        for (Cell celula : sheet.getRow(0)) {
            if (celula.getStringCellValue().equals("NOME DO TESTE AUTOMATIZADO")) {
                nomeColuna = celula.getColumnIndex();
                break;
            }
        }
        
        if (nomeColuna == -1) {
            throw new IllegalArgumentException("Coluna 'NOME DO TESTE AUTOMATIZADO' não encontrada na planilha.");
        }
        
        int resultadoColuna = -1;
        for (Cell celula : sheet.getRow(0)) {
            if (celula.getStringCellValue().equals("RESULTADO OBTIDO")) {
                resultadoColuna = celula.getColumnIndex();
                break;
            }
        }
        
        if (resultadoColuna == -1) {
            throw new IllegalArgumentException("Coluna 'RESULTADO OBTIDO' não encontrada na planilha.");
        }
        
        int dataTesteColuna = -1;
        for (Cell celula : sheet.getRow(0)) {
            if (celula.getStringCellValue().equals("DATA DE TESTE REALIZADO")) {
                dataTesteColuna = celula.getColumnIndex();
                break;
            }
        }
        
        if (dataTesteColuna == -1) {
            throw new IllegalArgumentException("Coluna 'DATA DE TESTE REALIZADO' não encontrada na planilha.");
        }
        
        for (Row linha : sheet) {
            Cell cell = linha.getCell(nomeColuna);
            if (cell != null && cell.getStringCellValue().equals(nomeClasseTeste)) {
                XSSFCell resultCell = (XSSFCell) linha.getCell(resultadoColuna, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                resultCell.setCellValue(resultado);
                
                String dataAtual = DataUtils.obterDataFormatada(new Date());
                
                XSSFCell dataTesteCell = (XSSFCell) linha.getCell(dataTesteColuna, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                dataTesteCell.setCellValue(dataAtual);
                
                break;
            }
        }
        
        
        inputStream.close();
        
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();
        
        workbook.close();
    }
}
