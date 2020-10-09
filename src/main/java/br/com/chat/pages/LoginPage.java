package br.com.chat.pages;

import org.apache.wicket.feedback.ExactLevelFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;

import br.com.chat.exception.SenhaInvalidaException;
import br.com.chat.exception.UsuarioJaLogadoException;
import br.com.chat.exception.UsuarioNaoCadastradoException;
import br.com.chat.model.Usuario;
import br.com.chat.service.UsuarioService;

public class LoginPage extends BasePage {

	private static final long serialVersionUID = 1L;
	
	Usuario usuario;
	
	@SpringBean
	UsuarioService service;

	public LoginPage() {
		try {
			carregaConfiguracoes();
			criaFormulario();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void carregaConfiguracoes() {
		usuario = new Usuario();
		setDefaultModel(new CompoundPropertyModel<Usuario>(usuario));
	}

	/*FORMULARIO DE LOGIN*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void criaFormulario() {
		Form<Usuario> f = new Form<Usuario>("formulario") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit() {
				try {
					service.atualizarLogados();
					service.logar(usuario);					
					ChatPage pagina = new ChatPage(usuario);
					setResponsePage(pagina);
					getSession().replaceSession();
				} catch (UsuarioNaoCadastradoException e) {
					error("Usuario não cadastrado!");
				} catch (SenhaInvalidaException e) {
					error("Senha invalida!");
				} catch (UsuarioJaLogadoException e) {
					error("Este usuario já esta logado!");
				}
			}
		};
		f.add(new RequiredTextField("login").add(StringValidator.maximumLength(10)));
		f.add(new PasswordTextField("senha"));
		f.add(new FeedbackPanel("mensagensErro", new ExactLevelFeedbackMessageFilter(FeedbackMessage.ERROR)));
		f.add(new FeedbackPanel("mensagensSucesso", new ExactLevelFeedbackMessageFilter(FeedbackMessage.SUCCESS)));
		
		/*LINK DE CADASTRO*/
		f.add(new Link("cadastro") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick() {
				setResponsePage(CadastroPage.class);
			}
		});
		add(f);
	}
}
