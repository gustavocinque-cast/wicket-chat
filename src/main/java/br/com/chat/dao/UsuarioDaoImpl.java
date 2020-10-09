package br.com.chat.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import br.com.chat.model.Usuario;

@Component
public class UsuarioDaoImpl implements UsuarioDao {
	private static final long serialVersionUID = 1L;
	
	public UsuarioDaoImpl() {}
	
	@PersistenceContext(unitName = "persistencia")
	private EntityManager entityManager;

	protected EntityManager getEntityManager() {
		return this.entityManager;
	}

	protected Session getHibernateCurrentSession() {
		return (Session) getEntityManager().getDelegate();
	}

	private List<Usuario> usuariosLogados = new ArrayList<Usuario>();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> getUsuarios() {
		StringBuilder hql = new StringBuilder("from Usuario");
		Query busca = getEntityManager().createQuery(hql.toString());
		return busca.getResultList();
	}

	@Override
	public List<Usuario> getLogados() {
		return usuariosLogados;
	}

	@Override
	public void logar(Usuario usuario) {
		usuariosLogados.add(usuario);
		Collections.sort(usuariosLogados);
	}

	@Override
	public void deslogar(Usuario usuario) {
		usuariosLogados.remove(usuario);
	}

	@Override
	public void salvar(Usuario usuario) {
		getEntityManager().persist(usuario);
	}

	@Override
	public void deletar(Usuario u) {
		u = (Usuario) getHibernateCurrentSession().get(u.getClass(), u.getId());
		getEntityManager().remove(u);	
	}

	@Override
	public void alterar(Usuario u) {
		getEntityManager().merge(u);		
	}
}