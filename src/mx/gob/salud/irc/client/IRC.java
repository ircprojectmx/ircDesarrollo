package mx.gob.salud.irc.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IRC implements EntryPoint {
	public void onModuleLoad() {
		Resources.rootPanel = RootPanel.get();
		Log.setUncaughtExceptionHandler();
		SplashPanel splash = new SplashPanel();
		Resources.rootPanel.add(splash);
		
		Resources.security.userLogin();
	}
}
