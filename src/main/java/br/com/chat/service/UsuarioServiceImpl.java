package br.com.chat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.chat.dao.UsuarioDao;
import br.com.chat.exception.SenhaInvalidaException;
import br.com.chat.exception.UsuarioJaCadastradoException;
import br.com.chat.exception.UsuarioJaLogadoException;
import br.com.chat.exception.UsuarioNaoCadastradoException;
import br.com.chat.model.Usuario;
import br.com.chat.util.SecurityUtil;

@Component
public class UsuarioServiceImpl implements UsuarioService {
	private static final long serialVersionUID = 1L;

	@Autowired
	private UsuarioDao dao;

	@Override
	public List<Usuario> getUsuarios() {
		return dao.getUsuarios();
	}

	@Override
	@Transactional
	public void salvar(Usuario usuario) throws UsuarioJaCadastradoException {

		if (this.getUsuarios().contains(usuario))
			throw new UsuarioJaCadastradoException();
		
		usuario.setSenha(SecurityUtil.criaMd5(usuario.getSenha()));
		dao.salvar(usuario);
	}

	@Override
	public void logar(Usuario u) throws UsuarioNaoCadastradoException, SenhaInvalidaException, UsuarioJaLogadoException {
		if (!(dao.getUsuarios().contains(u)))
			throw new UsuarioNaoCadastradoException();

		Usuario uBanco = dao.getUsuarios().get(dao.getUsuarios().indexOf(u));

		if (dao.getLogados().contains(u))
			throw new UsuarioJaLogadoException();

		u.setSenha(SecurityUtil.criaMd5(u.getSenha()));

		if (!(uBanco.getSenha().equals(u.getSenha())))
			throw new SenhaInvalidaException();

		u.setHexa(uBanco.getHexa());
		u.setId(uBanco.getId());
		u.atualiza();
		dao.logar(u);

	}

	@Override
	public void deslogar(Usuario u) {
		dao.deslogar(u);
	}

	@Override
	public void atualizarLogados() {
		List<Usuario> users = dao.getLogados();
		List<Usuario> deslogar = new ArrayList<Usuario>();
		
		for (Usuario u : users)
			if (!u.isOnline()){
				deslogar.add(u);
			}

		for (Usuario u : deslogar)
			dao.deslogar(u);
	}

	@Override
	public List<Usuario> getLogados() {
		return dao.getLogados();
	}

	@Override
	@Transactional
	public void deletar(Usuario u) {
		dao.deletar(u);
		dao.deslogar(u);
	}

	@Override
	@Transactional
	public void alterar(Usuario u) {
		dao.alterar(u);
	}

}