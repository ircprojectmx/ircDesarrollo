package mx.gob.salud.irc.client.services.remote;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SecurityAsync {

	void login(String user, String password, AsyncCallback<HashMap<String,String>> callback);
}
