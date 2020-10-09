package br.com.chat.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.chat.model.Mensagem;

@Component
public class MensagemDaoImpl implements MensagemDao {
	private static final long serialVersionUID = 1L;
	
	private List<Mensagem> mensagens = new ArrayList<>();

	@Override
	public List<Mensagem> getMensagens() {
		return mensagens;
	}

	@Override
	public void salvar(Mensagem m) {
		mensagens.add(m);
	}
}
