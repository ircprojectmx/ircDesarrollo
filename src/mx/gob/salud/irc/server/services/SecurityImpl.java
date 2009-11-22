package mx.gob.salud.irc.server.services;

import java.util.ArrayList;
import java.util.HashMap;

import mx.gob.salud.irc.client.services.remote.Security;
import mx.gob.salud.irc.server.utils.UtilsBD;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SecurityImpl extends RemoteServiceServlet implements Security {

	private static final long serialVersionUID = 4927203683435221101L;

	private UtilsBD dbCon = new UtilsBD();
	
	public static final String STATUS_ACTIVE = "A";
	
	/**
	 * Valida si puede el usuario logearse al sistema.
	 * @param user
	 * @param password
	 * @return Mapa con las Propiedades del usuario extraidas de la BD, si no tiene acceso no existen las propiedades del usuario.
	 * @see mx.gob.salud.sidtee.client.services.remote.Security
	 */
	public HashMap<String,String> login(String user, String password) {
		String query = "SELECT ID_ROL, CD_LOGIN, NB_PATERNO, NB_MATERNO, NB_NOMBRE, CD_TELEFONO, CD_EMAIL, FH_RESET_PWD, FH_ALTA, CD_ESTATUS "+
		//" FROM TSEG_USUARIOS WHERE CD_LOGIN = \""+user+"\" AND CD_PASSWORD = \""+password+"\" AND CD_ESTATUS = \""+SecurityImpl.STATUS_ACTIVE+"\"";
		" FROM TSEG_USUARIOS WHERE CD_LOGIN = '"+user+"' AND CD_PASSWORD = '"+password+"' AND CD_ESTATUS = '"+SecurityImpl.STATUS_ACTIVE+"'";
		ArrayList <HashMap<String,String>> results = dbCon.queryLista(query);
		if (results != null)
			return results.get(0);
		else
			return null;
	}
}
