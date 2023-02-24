package br.com.bradesco.cinv.model.relatorio;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatorioDTO {
	private String nomeSegmento;
	private String mesAno;
	private List<CarteiraRelatorioDTO> carteiras;
}
