package br.com.chat.dao;

import java.io.Serializable;
import java.util.List;

import br.com.chat.model.Usuario;

public interface UsuarioDao extends Serializable {
	
	List<Usuario> getUsuarios();
	List<Usuario> getLogados();
	void salvar(Usuario usuario);
	void logar(Usuario usuario);
	void deslogar(Usuario usuario);
	void deletar(Usuario u);
	void alterar(Usuario u);
}