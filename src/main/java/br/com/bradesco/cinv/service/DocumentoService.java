package br.com.bradesco.cinv.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import br.com.bradesco.cinv.model.CarteiraDTO;
import br.com.bradesco.cinv.model.DocumentoDTO;
import br.com.bradesco.cinv.model.relatorio.CarteiraRelatorioDTO;
import br.com.bradesco.cinv.model.relatorio.RelatorioDTO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class DocumentoService {

	@Autowired
	private ResourceLoader resourceLoader;

	ResourceBundle messages = ResourceBundle.getBundle("messages");

	protected static Logger logger = Logger.getLogger(DocumentoService.class);

	private static final String REPORTS_FOLDER = "WEB-INF/reports/report.jasper";
	private static final String SUBREPORT_FOLDER = "WEB-INF/reports/carteirasSubreport.jasper";
	private static final String IMG_FOLDER = "WEB-INF/reports/img/logo-prime.png";

	public String getDocumento(DocumentoDTO documento) {
		RelatorioDTO relatorioDTO = getRelatorioDTO(documento);

		String relatorio = null;
		try {
			relatorio = gerarRelatorio(relatorioDTO);
		} catch (JRException | IOException e) {
			logger.error("Erro ao gerar o relatorio: " + e.getMessage());
		}
		return relatorio;
	}

	private RelatorioDTO getRelatorioDTO(DocumentoDTO documento) {
		RelatorioDTO relatorio = new RelatorioDTO();
		Locale local = new Locale("pt", "BR");
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM", local);
		Calendar calendar = Calendar.getInstance();
		calendar.set(documento.getAno(), documento.getMes() - 1, 1);
		String mes = formatter.format(calendar.getTime());

		StringBuilder bd = new StringBuilder();
		bd.append(mes);
		bd.append("/");
		bd.append(documento.getAno());

		relatorio.setMesAno(bd.toString());

		relatorio.setNomeSegmento(documento.getNomeSegmento());
		relatorio.setCarteiras(new ArrayList<>());
		for (CarteiraDTO carteira : documento.getCarteiras()) {
			CarteiraRelatorioDTO dto = new CarteiraRelatorioDTO();

			DecimalFormat df = new DecimalFormat("0.00");
			dto.setNomeSegmento(carteira.getNomePerfil());
			dto.setCdi(df.format(carteira.getCdi()).concat("%"));
			dto.setAcoes(df.format(carteira.getAcoes()).concat("%"));
			dto.setCreditoPrivado(df.format(carteira.getCreditoPrivado()).concat("%"));
			dto.setPrefixado(df.format(carteira.getPrefixado()).concat("%"));
			dto.setJuroReal(df.format(carteira.getJuroReal()).concat("%"));
			dto.setMultimercado(df.format(carteira.getMultimercado()).concat("%"));
			dto.setAcoes(df.format(carteira.getAcoes()).concat("%"));
			dto.setInternacional(df.format(carteira.getInternacional()).concat("%"));
			dto.setCambial(df.format(carteira.getCambial()).concat("%"));
			relatorio.getCarteiras().add(dto);
		}
		return relatorio;
	}

	private String gerarRelatorio(RelatorioDTO documento) throws JRException, IOException {
		String pdfBase64 = null;
		HashMap<String, Object> params = new HashMap<>();
		InputStream content = resourceLoader.getResource(REPORTS_FOLDER).getInputStream();
		params.put("logo", resourceLoader.getResource(IMG_FOLDER).getURI().getPath());
		params.put("subreport", resourceLoader.getResource(SUBREPORT_FOLDER).getURI().getPath());
		BufferedInputStream bufferedInputStream = new BufferedInputStream(content);
		List<RelatorioDTO> relatorios = new ArrayList<RelatorioDTO>();
		relatorios.add(documento);
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(relatorios);
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, jrbcds);
		byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
		content.close();
		bufferedInputStream.close();
		pdfBase64 = Base64.encodeBase64String(pdf);
		return pdfBase64;
	}
}
