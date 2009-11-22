package mx.gob.salud.irc.client.services;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;

import mx.gob.salud.irc.client.Resources;
import mx.gob.salud.irc.client.statics.Codes;

public class CommonCallBack {

	private int code = Codes.OK;
	private String message = Resources.msg.okProc();
	private String methodName = "";
	private String detail = "";
	private AsyncCallback<HashMap<String,String>> results = null;
	
	
	public void onFailure(){
		
	}
	
	public void onSuccess(){
		
	}

	public String getFullMessage(){
		StringBuffer msg = new StringBuffer();
		if (methodName != null && methodName.length() > 0)
			msg.append(methodName+", ");
		msg.append(message);
		if (detail != null && detail.length() > 0)
			msg.append(", "+detail);
			
		return (msg.toString());
	}
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AsyncCallback<HashMap<String, String>> getResults() {
		return results;
	}

	public void setResults(AsyncCallback<HashMap<String, String>> results) {
		this.results = results;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}
