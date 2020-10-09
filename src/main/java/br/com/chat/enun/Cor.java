package br.com.chat.enun;

import java.util.Arrays;
import java.util.List;

public enum Cor {
	
	Preto("color:#000000;"),
	Verde("color:#248F24;"),
	Azul("color:#0000CC;"),
	Vermelho("color:#FF0000;");
	
	private String cod;

	private Cor(String h){
		this.cod=h;
	}
	
	public String getCor(){
		return this.name();
	}
	
	public String getCod(){
		return cod;
	}
	
	public static List<Cor> getCores(){
		return Arrays.asList(Cor.values());
	}
	
	@Override
	public String toString() {
		return this.cod;
	}

}
