package com.report.demo.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.report.demo.dto.VoReq;
import com.report.demo.service.ReportService;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	ResourceLoader resourceLoader;

	@Override
	public byte[] createReport(VoReq request) throws JRException, IOException {
		Map<String, Object> m = new HashMap<String, Object>();
		List<String> legends = new ArrayList<>();
		legends.add("LEGEND 1");
		legends.add("LEGEND 2");
		legends.add("LEGEND 3");
		request.setLegends(legends);
		m.put("IMAGE_URL", "https://i0.wp.com/karinov.co.id/wp-content/uploads/2020/02/Gambar-Peta-Indonesia-Lengkap-Official.jpg");
		m.put("LEGEND", new JRBeanCollectionDataSource(request.getLegends()));
		byte[] file = generateJasperPdf("Blank_A4.jasper", m);
		return file;
	}

	private byte[] generateJasperPdf(String jasperName, Map<String, Object> m) throws JRException, IOException {
		JasperPrint jasperPrint = null;
		try {
			jasperPrint = JasperFillManager.fillReport(resourceLoader.getResource("classpath:\\report\\" + jasperName).getInputStream(), m, new JREmptyDataSource());
		} catch (JRException e) {
			e.printStackTrace();
		}
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}

}
