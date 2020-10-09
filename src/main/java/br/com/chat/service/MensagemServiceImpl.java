package br.com.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chat.dao.MensagemDao;
import br.com.chat.model.Mensagem;

@Component
public class MensagemServiceImpl implements MensagemService {
	private static final long serialVersionUID = 1L;

	@Autowired
	private MensagemDao dao;

	@Override
	public List<Mensagem> getMensagens() {
		return dao.getMensagens();
	}

	@Override
	public void salvar(Mensagem mensagem){
		dao.salvar(mensagem);
	}

}