package br.com.bradesco.cinv.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bradesco.cinv.model.DocumentoDTO;
import br.com.bradesco.cinv.service.DocumentoService;

@RestController
@RequestMapping("/documentos")

public class DocumentoResource {

	@Autowired
	private DocumentoService service;

	@PostMapping 
	public ResponseEntity<String> gerarDocumento(@RequestBody DocumentoDTO documento) {
		return new ResponseEntity<>(service.getDocumento(documento), HttpStatus.OK);
	}

}
