package br.com.bradesco.cinv.model.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoDocumentoEnum {
	TIPO_PDF(1, "PDF"),
	TIPO_XLS(2, "XLS");

	Integer codigo;
	String perfil;
	
	public static TipoDocumentoEnum obterPorCodigo(Integer codigo){
		TipoDocumentoEnum tipo = null;
		for(TipoDocumentoEnum tipoSelecionado : TipoDocumentoEnum.values()){
			if(tipoSelecionado.codigo.equals(codigo)){
				tipo =  tipoSelecionado;
			}	
		}
		return tipo;
	}

}
