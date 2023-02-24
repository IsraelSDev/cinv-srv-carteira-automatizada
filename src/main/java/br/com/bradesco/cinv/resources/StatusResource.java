package br.com.bradesco.cinv.resources;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/health")
public class StatusResource {

	@GetMapping(produces = "application/json;charset=UTF-8")
	public ResponseEntity<Date> health() {
		return new ResponseEntity<>(new Date(), HttpStatus.OK);
	}
}
