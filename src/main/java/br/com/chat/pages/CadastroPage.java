package br.com.chat.pages;

import java.util.regex.Pattern;

import org.apache.wicket.feedback.ExactLevelFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;

import br.com.chat.enun.Cor;
import br.com.chat.exception.UsuarioJaCadastradoException;
import br.com.chat.model.Usuario;
import br.com.chat.service.UsuarioService;

public class CadastroPage extends BasePage {

	private static final long serialVersionUID = 1L;

	@SpringBean
	UsuarioService usuarioService;
	
	Usuario usuario;

	public CadastroPage() {
		carregaConfig();
		criaFormulario();
	}

	private void carregaConfig() {
		usuario = new Usuario();
		setDefaultModel(new CompoundPropertyModel<Usuario>(usuario));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void criaFormulario() {

		Form<Usuario> f = new Form<Usuario>("formulario") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				super.onSubmit();


				try {
					Usuario u = (Usuario) this.getParent().getDefaultModelObject();
					
					if(!Pattern.matches("[a-zA-Z][_a-zA-Z0-9]+", u.getLogin())){
						System.out.println(u.getLogin()+" kkkkkkkkk");
						error("Login somente caracteres alfanuméricos e _");
					}else{
						usuarioService.salvar(u);
						LoginPage pagina = new LoginPage();
						pagina.success("Cadastrado com sucesso!");
						setResponsePage(pagina);
					}
				} catch (UsuarioJaCadastradoException e) {
					error("Usuario ja cadastrado!");
				}
			}

		};
		f.add(new RequiredTextField("login").add(new StringValidator(5, 10)));
		f.add(new PasswordTextField("senha"));
		
		f.add(new FeedbackPanel("mensagensErro",new ExactLevelFeedbackMessageFilter(FeedbackMessage.ERROR)));

		ChoiceRenderer<Cor> renderer = new ChoiceRenderer<>("cor");
		RadioChoice<Cor> radioChoice = new RadioChoice<Cor>("hexa",Cor.getCores(),renderer);
		radioChoice.setSuffix("<br/>");
		f.add(radioChoice);
		
		f.add(new Link("voltar"){
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(LoginPage.class);
			}
			
		});
		
		
		add(f);
	}

}
