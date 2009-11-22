package mx.gob.salud.irc.client.services.remote;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("Security")
public interface Security extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static SecurityAsync instance;
		public static SecurityAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(Security.class);
			}
			return instance;
		}
	}
	
	/**
	 * Valida si puede el usuario logearse al sistema.
	 * @param user
	 * @param password
	 * @return Mapa con las Propiedades del usuario extraidas de la BD, si no tiene acceso no existen las propiedades del usuario.
	 * @see mx.gob.salud.sidtee.server.services.SecurityImpl
	 */
	HashMap<String,String> login(String user, String password);
}
