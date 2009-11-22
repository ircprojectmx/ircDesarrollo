package mx.gob.salud.irc.client;

import mx.gob.salud.irc.client.ficha.FichaAlta;
import mx.gob.salud.irc.client.utils.UserMessage;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;

public class MainMenu extends MenuBar{

	private Command cmdEmpty = new Command() {
	      public void execute() {
	        UserMessage.warning("Menu Principal", "Opcion no Definida.", null);
	      }
	    };

	public MainMenu(){
		//addStyleName(Codes.msg.styleMenu());
		setAutoOpen(true);
	    setAnimationEnabled(true);
	    ensureDebugId("cwMenuMain");
		
	    
	    
	    
	    
	    Command cmdFichaAlta = new Command() {
		      public void execute() {
		        Resources.mainPanel.open(new FichaAlta());
		      }
		    };
		    
		 MenuBar menuFicha = new MenuBar(true);
		 menuFicha.addItem(Resources.msg.menAlta(), cmdFichaAlta);
		 menuFicha.addItem(Resources.msg.menSubsecuente(), cmdEmpty);
		 MenuBar menuTamizaje = new MenuBar(true);
		 menuTamizaje.addItem(Resources.msg.menAlta(),cmdEmpty);
		 menuTamizaje.addItem(Resources.msg.menSubsecuente(),cmdEmpty);
		 menuFicha.addItem(Resources.msg.menTamizaje(), menuTamizaje);
		 addItem(Resources.msg.menFicha(), menuFicha);
		 
		 MenuBar menuConsultas = new MenuBar(true);
		 menuConsultas.addItem(Resources.msg.menAlta(), cmdEmpty);
		 menuConsultas.addItem(Resources.msg.menSubsecuente(), cmdEmpty);
		 menuConsultas.addItem(Resources.msg.menInterConsulta(), cmdEmpty);
		 addItem(Resources.msg.menConsulta(), menuConsultas);
		 
		 MenuBar menuLab = new MenuBar(true);
		 menuLab.addItem(Resources.msg.menAlta(), cmdEmpty);
		 menuLab.addItem(Resources.msg.menSubsecuente(), cmdEmpty);
		 addItem(Resources.msg.menLaboratorio(), menuLab);
		 
		 MenuBar menuGabinete = new MenuBar(true);
		 menuGabinete.addItem(Resources.msg.menUs(), cmdEmpty);
		 menuGabinete.addItem(Resources.msg.menCistou(), cmdEmpty);
		 menuGabinete.addItem(Resources.msg.menGamag(), cmdEmpty);
		 menuGabinete.addItem(Resources.msg.menTAC(), cmdEmpty);
		 menuGabinete.addItem(Resources.msg.menCistosiopia(), cmdEmpty);
		 addItem(Resources.msg.menGabinete(), menuGabinete);
		 
		 MenuBar menuCx = new MenuBar(true);
		 menuCx.addItem(Resources.msg.menAlta(), cmdEmpty);
		 menuCx.addItem(Resources.msg.menSubsecuente(), cmdEmpty);
		 addItem(Resources.msg.menCx(), menuCx);
		 
		 MenuBar menuCatalogos = new MenuBar(true);
		 menuCatalogos.addItem(Resources.msg.menAlta(), cmdEmpty);
		 menuCatalogos.addItem(Resources.msg.menSubsecuente(), cmdEmpty);
		 addItem(Resources.msg.menCx(), menuCatalogos);
	}
}
