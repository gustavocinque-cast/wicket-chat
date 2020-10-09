package br.com.chat.service;

import java.io.Serializable;
import java.util.List;

import br.com.chat.model.Mensagem;
import br.com.chat.model.Usuario;

public interface MensagemService extends Serializable {
	
	List<Mensagem> getMensagens();
	void salvar(Mensagem mensagem);

}
