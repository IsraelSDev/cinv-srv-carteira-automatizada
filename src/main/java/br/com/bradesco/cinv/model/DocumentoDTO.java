package br.com.bradesco.cinv.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentoDTO {
	private Integer tpDocumento;
	private String nomeSegmento;
	private Integer mes;
	private Integer ano;
	private List<CarteiraDTO> carteiras;
}
