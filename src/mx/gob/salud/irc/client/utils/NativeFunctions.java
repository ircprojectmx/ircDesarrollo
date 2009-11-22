package mx.gob.salud.irc.client.utils;

public class NativeFunctions {
	
	native  public static void close()/*-{  $wnd.close();

	}-*/;
	
	native public static void redirect(String url)
	/*-{
	        $wnd.location.replace(url);

	}-*/; 
}
