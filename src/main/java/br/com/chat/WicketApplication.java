package br.com.chat;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import br.com.chat.pages.LoginPage;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see br.com.chat.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage() {
		return LoginPage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init() {
		super.init();

		getMarkupSettings().setDefaultMarkupEncoding("utf-8");
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));

		/*getComponentPreOnBeforeRenderListeners().add(
				new JQComponentOnBeforeRenderListener());
		JQContributionConfigCustom config = new JQContributionConfigCustom();
		getComponentPreOnBeforeRenderListeners().add(new JQComponentOnBeforeRenderListener(config));		
		*/

	}
}
