package mx.gob.salud.irc.client;

import com.allen_sauer.gwt.log.client.DivLogger;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.gwt.user.client.ui.Widget;

import mx.gob.salud.irc.client.catalogos.Catalogs;
import mx.gob.salud.irc.client.security.LocalSecurity;
import mx.gob.salud.irc.client.services.CommonCallBack;
import mx.gob.salud.irc.client.statics.LocalMessages;
import mx.gob.salud.irc.client.statics.PropertiesLoader;
import mx.gob.salud.irc.client.utils.UserMessage;

/**
 * Resources contiene los recursos que serán globales para toda la aplicación.
 * Como son los catalogos o el perfil de seguridad del usuario, etc.
 * @author Rommel Medina
 *
 */
public class Resources {

	//public static Resources res = null;
	public static Panel rootPanel = null;
	public static LocalMessages msg = (LocalMessages) GWT.create(LocalMessages.class);
	public static PropertiesLoader properties = (PropertiesLoader) GWT.create(PropertiesLoader.class);
	public static LocalSecurity security = new LocalSecurity();
	public static MainPanel mainPanel = new MainPanel();
	public static Catalogs cat = new Catalogs();
	
	
	private Resources(){
		 
	}
	
	/*public static Resources getInstance(){
		if (res == null)
			res = new Resources();
		
		return (res);
	}*/
	
	public static void initApplication(){
		UserMessage.wait(Resources.msg.appName(), Resources.msg.loadingData(Resources.msg.appCode()), Resources.msg.runningProc("initApplication"));
		
		cat.load(false, new CommonCallBack() {
			public void onFailure() {
				UserMessage.error(Resources.msg.userErrorTitle(), Resources.msg.loadingData("Catalogos"), getFullMessage(), null);
				UserMessage.waitHide();
			}
			public void onSuccess() {
				
				UserMessage.wait(Resources.msg.appName(), Resources.msg.loadingData(Resources.msg.appCode()), Resources.msg.runningProc("Loading Main Panel"));
				Resources.rootPanel.clear();
				
				//Solo para usuarios de sistemas
				if (Resources.security.getRol().equals("0")){
					Widget divLogger = Log.getLogger(DivLogger.class).getWidget();
					divLogger.setWidth("100%");
					//divLogger.setWidth("30%");
					
					VerticalPanel vp = new VerticalPanel();
					vp.add(divLogger);
					vp.add(Resources.mainPanel);
					vp.setCellHeight(divLogger, "30%");
					Resources.rootPanel.add(vp);
					Resources.rootPanel = vp;
				}
				else{
					Resources.rootPanel.add(Resources.mainPanel);
				}
				UserMessage.waitHide();
			}
			});
	}
}
