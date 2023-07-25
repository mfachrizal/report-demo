package com.report.demo.rest;

import java.io.IOException;

import javax.ws.rs.core.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.report.demo.dto.VoReq;
import com.report.demo.service.ReportService;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("rest")
public class ReportRest {

	@Autowired
	private ReportService reportService;

	@GetMapping("create-report")
	public ResponseEntity<ByteArrayResource> createReport() throws JRException, IOException {
		VoReq request = new VoReq();
		byte[] byteFile = reportService.createReport(request);
        ByteArrayResource resource = new ByteArrayResource(byteFile);
		return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "Report.pdf")
                .contentType(MediaType.APPLICATION_PDF).contentLength(byteFile.length)
                .body(resource);
	}

}
