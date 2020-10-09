package br.com.chat.pages;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.feedback.ExactLevelFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;

import br.com.chat.enun.Cor;
import br.com.chat.model.Mensagem;
import br.com.chat.model.Usuario;
import br.com.chat.service.MensagemService;
import br.com.chat.service.UsuarioService;

public class ChatPage extends BasePage {
	private static final long serialVersionUID = 1L;

	@SpringBean
	private MensagemService serviceM;
	
	@SpringBean
	private UsuarioService serviceU;
	
	private Usuario usuario = null;
	
	private WebMarkupContainer opcoesContainer;
	private WebMarkupContainer confirmacao;
	private WebMarkupContainer campoFeedbackMensagens;
	private WebMarkupContainer campoFeedbackLogados;
	
	//construtor
	public ChatPage(final Usuario u) {
		this.usuario=u;
		criaComponentes();		
	}
	
	//componentes
	private void criaComponentes() {
		criaCampoLogados();
		criaCampoMensagens();
		criaFormulario();
		criaBarraMenu();
	}

	private void criaOpcoes() {
		carregaConfigContainer();
		criaForm();
		criaDeletar();
	}
	
	private void carregaConfigContainer(){
		opcoesContainer = new WebMarkupContainer("opcoesContainer");
		opcoesContainer.setOutputMarkupPlaceholderTag(true);
		opcoesContainer.setVisible(false);
		opcoesContainer.setDefaultModel(new CompoundPropertyModel<Usuario>(usuario));
		add(opcoesContainer);
	}
	
	private void criaForm() {
		Form<Usuario> opcoesForm = new Form<Usuario>("opcoesform"){
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				super.onSubmit();
				
				serviceU.alterar(usuario);
				opcoesContainer.setVisible(false);
				
			}
		};
		
		ChoiceRenderer<Cor> renderer = new ChoiceRenderer<>("cor");
		RadioChoice<Cor> radioChoice = new RadioChoice<Cor>("hexa",Cor.getCores(),renderer);
		radioChoice.setSuffix("<br/>");
		opcoesForm.add(radioChoice);
		opcoesContainer.add(opcoesForm);
	}
	
	@SuppressWarnings("rawtypes")
	private void criaDeletar() {
		criaConfirmacaoDeletar();
		opcoesContainer.add(new AjaxLink("deletar"){
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				confirmacao.setVisible(true);
				target.add(confirmacao);
			}
		});
	}

	@SuppressWarnings("rawtypes")
	private void criaConfirmacaoDeletar() {
		confirmacao = new WebMarkupContainer("confirmacao");
		opcoesContainer.add(confirmacao);
		confirmacao.setOutputMarkupPlaceholderTag(true);
		confirmacao.setVisible(false);
		
		confirmacao.add(new AjaxLink("nao"){
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				confirmacao.setVisible(false);
				target.add(confirmacao);
			}
		});
		
		confirmacao.add(new Link("sim") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				serviceU.deletar(usuario);
				setResponsePage(LoginPage.class);
			}
			
		});
	}

	private void criaBarraMenu() {
		
		criaOpcoes();
		
		@SuppressWarnings("rawtypes")
		Link btDeslogar = new Link("deslogar") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				serviceU.deslogar(usuario);
				setResponsePage(LoginPage.class);
			}
		};
		add(btDeslogar);
		
		@SuppressWarnings("rawtypes")
		AjaxLink btOpcoes = new AjaxLink("opcoes") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void onClick(AjaxRequestTarget target) {
				if (!opcoesContainer.isVisible()){
					opcoesContainer.setVisible(true);
				}else{
					opcoesContainer.setVisible(false);
				}
				target.add(opcoesContainer);
			}
		};
		add(btOpcoes);
		
		add(new Label("login",usuario));
	}

	//mostra mensagens e usuarios
	private void criaCampoLogados() {
		
		//cria o campo que atualiza os usuarios logados a cada 1s
		campoFeedbackLogados = new WebMarkupContainer("containerLogados");
		campoFeedbackLogados.add(new AjaxSelfUpdatingTimerBehavior(Duration.ONE_SECOND));
		campoFeedbackLogados.setOutputMarkupId(true);
		
		//cria listview dos usuarios logados
		criaListViewLogados(campoFeedbackLogados);
		add(campoFeedbackLogados);
		
	}
	
	private void criaListViewLogados(WebMarkupContainer campoFeedbackContainer) {
		ListView<Usuario> campoUsuarios = new ListView<Usuario>("usuarios",serviceU.getLogados() ) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void populateItem(ListItem<Usuario> item) {
				final Usuario u = (Usuario) item.getDefaultModelObject();
					Label label = new Label("usuario", u.getLogin());
					label.setVisible(false);
					if(!usuario.equals(u)){
						label.setVisible(true);
					}/*else{
						item.add(new Label("usuario", ""));
					}*/
					item.add(label);
			}
		};
		campoFeedbackContainer.add(campoUsuarios);		
	}
	
	private void criaCampoMensagens() {
		campoFeedbackMensagens = new WebMarkupContainer("containerMensagens"){
			private static final long serialVersionUID = 1L;

			//redireciona se a pagina for fechada por 2 segundos
			@Override
			protected void onAfterRender() {
				super.onAfterRender();
				if(usuario.isOnline()){
					usuario.atualiza();
				}else{
					setResponsePage(LoginPage.class);
				}
			}
		};
		//para ser atualizado via ajax
		campoFeedbackMensagens.add(new AjaxSelfUpdatingTimerBehavior(Duration.ONE_SECOND));
		campoFeedbackMensagens.setOutputMarkupId(true);
		
		//campo onde as mensagens aparecerao ao atualizar o container
		criaCampoMensagens(campoFeedbackMensagens);
		
		add(campoFeedbackMensagens);
		
	}

	

	private void criaCampoMensagens(WebMarkupContainer campoFeedbackMensagens) {
		ListView<Mensagem> campoMensagens = new ListView<Mensagem>("mensagens", serviceM.getMensagens()) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Mensagem> item) {
				Mensagem mensagem = (Mensagem) item.getDefaultModelObject();
				Usuario u = mensagem.getUsuario();
				String estilos = ((u.getHexa()==null)?"color:#000000;":u.getHexa())+" max-width: 100%; border-radius:4px 4px 4px 4px; background-color:rgb(230,230,230);";
				
				String textoPronto = mensagem.getTexto();
				
				if(usuario.getLogin().equals(u.getLogin())){
					estilos+="float: right;";
					item.add(new Label("mensagem",textoPronto).add(new AttributeModifier("style", estilos)));
				}else {
					estilos+="float: left;";
					item.add(new Label("mensagem", u.getLogin()+": "+textoPronto).add(new AttributeModifier("style", estilos)));
				}
			}
		};
		campoFeedbackMensagens.add(campoMensagens);
		
	}

	//campo para escrever
	private void criaFormulario() {
		Mensagem mensagem = new Mensagem(usuario);
		
		final Form<Mensagem> f = new Form<Mensagem>("form");
		f.setDefaultModel(new CompoundPropertyModel<Mensagem>(mensagem));

		f.add(new AjaxFormSubmitBehavior("onsubmit") {
			private static final long serialVersionUID = 1L;
		});
		
		final TextArea<Object> campoTexto = new TextArea<>("texto");		
		campoTexto.setOutputMarkupId(true);
		
		f.add(campoTexto);
		f.add(new FeedbackPanel("mensagensErro", new ExactLevelFeedbackMessageFilter(FeedbackMessage.ERROR)));

		AjaxButton aButton = new AjaxButton("enviar") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {				
				super.onSubmit(target, form);
				if(usuario.isOnline()){
					Mensagem msg = (Mensagem)this.getParent().getDefaultModelObject();
					if(!(msg.getTexto()==null||msg.getTexto().length()>100)){
						msg.setUsuario(usuario);
						serviceM.salvar(msg);
					}
					setResponsePage((Page) this.getParent().getParent());
				}else{
					setResponsePage(LoginPage.class);
				}
			}
		};
		f.setOutputMarkupId(true);
		f.add(aButton);
		add(f);
	}
}
