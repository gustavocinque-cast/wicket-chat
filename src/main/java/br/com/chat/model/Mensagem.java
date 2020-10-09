package br.com.chat.model;

import java.io.Serializable;

public class Mensagem implements Serializable {
	private static final long serialVersionUID = 1L;

	private String texto;
	private Usuario usuario;

	public Mensagem(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
