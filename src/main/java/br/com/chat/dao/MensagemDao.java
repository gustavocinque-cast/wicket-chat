package br.com.chat.dao;

import java.io.Serializable;
import java.util.List;

import br.com.chat.model.Mensagem;

public interface MensagemDao extends Serializable {
	
	List<Mensagem> getMensagens();
	
	void salvar(Mensagem m);

}