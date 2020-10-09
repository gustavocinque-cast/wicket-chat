package br.com.chat.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="usuarios")
public class Usuario implements Serializable, Comparable<Usuario> {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String login;
	
	@Column
	private String senha;
	
	@Column
	private String hexa;
	
	@Transient
	private Long online=0L;

	@Transient
	private String data="";
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean isOnline(){
		Long atual = Calendar.getInstance().getTime().getTime();
		return ((atual-online)<2400);
	}

	public void atualiza() {
		this.online = Calendar.getInstance().getTime().getTime();
	}

	public String getHexa() {
		return hexa;
	}

	public void setHexa(String hexa) {
		this.hexa = hexa;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Usuario) {
			Usuario u = (Usuario) obj;
			if (u.getLogin().equals(this.getLogin())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int compareTo(Usuario u) {
		String nome = u.getLogin().toLowerCase();
		return this.getLogin().toLowerCase().compareTo(nome);
	}
	
	@Override
	public String toString() {
		return this.login;
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}