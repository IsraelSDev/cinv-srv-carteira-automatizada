package br.com.bradesco.cinv.model.relatorio;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarteiraRelatorioDTO {
	private String nomeSegmento;
	private String cdi;
	private String creditoPrivado;
	private String prefixado;
	private String juroReal;
	private String multimercado;
	private String acoes;
	private String internacional;
	private String cambial;
}
