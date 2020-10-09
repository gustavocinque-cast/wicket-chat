package br.com.chat.service;

import java.io.Serializable;
import java.util.List;

import br.com.chat.exception.SenhaInvalidaException;
import br.com.chat.exception.UsuarioJaCadastradoException;
import br.com.chat.exception.UsuarioNaoCadastradoException;
import br.com.chat.model.Usuario;

public interface UsuarioService extends Serializable {
	
	List<Usuario> getUsuarios();
	List<Usuario> getLogados();	
	void salvar(Usuario u) throws UsuarioJaCadastradoException;
	void deletar(Usuario u);
	void logar(Usuario u) throws UsuarioNaoCadastradoException, SenhaInvalidaException;
	void deslogar(Usuario u);
	void atualizarLogados();
	void alterar(Usuario u);
}
