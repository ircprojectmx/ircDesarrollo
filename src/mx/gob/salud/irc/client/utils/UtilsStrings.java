package mx.gob.salud.irc.client.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;

/**
 * @author Roberto
 *
 */
public class UtilsStrings {
	/**
	 * Formatea una cantidad para su desplegado; si ya est‡ formateada no le hace
	 * nada, y asume que en caso contrario siempre ser‡ un float, puesto que ya 
	 * pas— la validaci—n.<br>Tuve que hacer una implementacion rara del DecimalFormat
	 * porque el GWT no lo reconoce.
	 * 
	 * @param cantidad
	 * @return La cadena formateada
	 */
	public static String formateaNumero(String cantidad) {
		// Llamo al redondeo de numeros con redondeo
		return formateaNumero(cantidad, true);
	}
	/**
	 * Formatea una cantidad que llega en un String, para ponerle
	 * parentesis a los negativos, validar formato, poner comitas, etc
	 * 
	 * @param cantidad
	 * @param redondear true si quieres que haga redondeo
	 * @return
	 */
	public static String formateaNumero(String cantidad, boolean redondear) {
		// Le pongo un try..catch porque GWT se "come" las excepciones
		try {
			cantidad = quitaFormatoNumero(cantidad);
    		// Redondeo el numero y lo convierto de nuevo a String
    		float numero = Float.parseFloat(cantidad);
			return UtilsStrings.formateaNumero(numero, redondear);
		} catch (Exception e) {
			//e.printStackTrace();
			GWT.log("Error:" + e.getMessage(), e);
			return null;
		}
	}
	/**
	 * Formatea una cantidad que llega en un String, para ponerle
	 * parentesis a los negativos, validar formato, poner comitas, etc
	 * 
	 * @param cantidad
	 * @param redondear true si quieres que haga redondeo
	 * @return
	 */
	public static String formateaNumero(float formatear, boolean redondear) {
		boolean esNegativo = false;
		String cantidad = null;
		// Le pongo un try..catch porque GWT se "come" las excepciones
		try {
			float numero = formatear;
			float tmpNumero = numero;
    		
    		if (!redondear) {
    			// Si no hay que redondear, devuelvo el numero con 'n' decimales
    			String numCadena = String.valueOf(numero);
    			int punto = numCadena.indexOf('.');
    			if (numCadena.length() > punto + 3) {
	    			//tmpNumero = Float.parseFloat(numCadena.substring(0, punto + 3));
	    			tmpNumero = Math.round(tmpNumero * 100f) / 100f;
    			}
    		} else
	    		tmpNumero = Math.round(numero);
    		
    		if (tmpNumero < 0) {
    			esNegativo = true;
    			tmpNumero *= -1;
    		}
    		cantidad = String.valueOf(tmpNumero);
    		//GWT.log("cantidad redondeada: " + cantidad, null);
    		
    		int j = 0;
    		
    		// Si tiene parte decimal, la quito por el momento para poder 
    		// formatear la cantidad
    		int posPunto = cantidad.indexOf('.');
    		String parteDecimal = "";
    		if (posPunto > -1) {
    			parteDecimal = cantidad.substring(posPunto);
    			cantidad = cantidad.substring(0, posPunto);
    		}
    			
    		// Pone parentesis a numeros negativos y comas por miles
    		StringBuffer buf = new StringBuffer();
    		for (int i=cantidad.length(); i>0; i--) {
        		//if (esNegativo && i==cantidad.length())
    				//buf.insert(0,")");
    			j++;
    			if (j>1 && j%3 == 1) 
    				buf.insert(0,',');
    			buf.insert(0,cantidad.charAt(i-1));
    			if (esNegativo && i==1)
    				buf.insert(0,"(");
    		}
    		// Pongo la parte decimal y cierro el parentesis, si se aplica.
    		buf.append(parteDecimal);
    		if (esNegativo)
    			buf.append(')');
        	return buf.toString();
		} catch (Exception e) {
			//e.printStackTrace();
			GWT.log("Error:" + e.getMessage(), e);
			return null;
		}
	}

	public static String quitaFormatoNumero(String cantidad) {
		if (cantidad == null)
			return null;
		if (cantidad.indexOf(',') > -1)
			return cantidad.replaceAll(",", "");
		return cantidad;
	}
	
	public static String formateaPorcentaje(float cantidad){
		return (UtilsStrings.formateaNumero(cantidad, false)+"%");
	}
	
	/**
	 * Formatea una cantidad que llega en un String, para ponerle
	 * parentesis a los negativos, validar formato, poner comitas, etc
	 * Y LE QUITA DECIMALES
	 * 
	 * @param cantidad
	 * @param redondear true si quieres que haga redondeo
	 * @return
	 */
	public static String formateaNumeroEntero(String cantidad) {
		int pos = 0;
		
		pos = cantidad.indexOf('.');
		if (pos > -1){
			if (pos == 0)
				return ("0");
			else
				return (cantidad.substring(0,pos));
		}

		return (cantidad);
	}
	
	/**
	 * Recibe un String, si es nulo devuelve cadena vacia.
	 * @param source String a validar
	 * @return
	 */
	public static String emptyIfIsNull(Object source){
		if (source == null)
			source = "";
		return ((String)source);
	}
	
	/**
	 * Recibe un String, si es nulo devuelve "0".
	 * @param source String a validar
	 * @return
	 */
	public static String zeroIfIsNull(Object source){
		if (source == null)
			source = "0";
		return ((String)source);
	}
	
	/**
	 * Recibe un String, si es nulo devuelve "0".
	 * @param source String a validar
	 * @return
	 */
	public static String zeroIfIsEmptyOrNull(String source){
		if (source == null)
			source = "0";
		else{
			if (source.trim().length() <= 0)
				source = "0";
		}
		return (source);
	}
	
	public static String nombreMes(int mes){
		String ret = null;
		switch(mes){
		case 0:
			ret = "Enero";
			break;
		case 1:
			ret = "Febrero";
			break;
		case 2:
			ret = "Marzo";
			break;
		case 3:
			ret = "Abril";
			break;
		case 4:
			ret = "Mayo";
			break;
		case 5:
			ret = "Junio";
			break;
		case 6:
			ret = "Julio";
			break;
		case 7:
			ret = "Agosto";
			break;
		case 8:
			ret = "Septiembre";
			break;
		case 9:
			ret = "Octubre";
			break;
		case 10:
			ret = "Noviembre";
			break;
		case 11:
			ret = "Diciembre";
			break;
		}
		return (ret);
	}
	
	public static String llenaCeros( int num, int longitud) {
		String str = new Integer( num).toString();

		while( str.length() < longitud)
			str = "0" + str;
		return str;
	}

   public static String llenaCeros(String cadena, int longitud) {
		if (cadena == null)
			cadena = "";
		while( cadena.length() < longitud)
			cadena = "0" + cadena;
		return cadena;
   }

	/** Remueve caracteres no validos en nombres de archivos
	 * 
	 * @param cadena
	 * @return
	 */
	public static String remueveCarsInvalidos(String cadena) {
		cadena = cadena.replaceAll("&", "");
		cadena = cadena.replaceAll("\\", "");
		return cadena;
	}
	
	public static String arrayListHashMapToString(ArrayList<HashMap<String,String>> results){
		StringBuffer ret = new StringBuffer();
		int subIndex = 0, numSubArray = 0;
		HashMap row = null;
		Iterator iterator = null;
		String key = null;
		
		if (results != null){
			numSubArray = results.size();
			for(subIndex = 0; subIndex < numSubArray; subIndex++){
				row = (HashMap) results.get(subIndex);
				if (row != null){
					iterator = row.keySet().iterator();
			        while (iterator.hasNext()) {
			            key = (String) iterator.next();
			            ret.append("Row Num:"+subIndex+"|"+key+"|"+row.get(key)+"|     ");
			        }
				 }
				else
					ret.append("Row Num:"+subIndex+"|nullRow|     ");
			}
		}
		
		return (ret.toString());
	}
	
	public static String hashMapToString(HashMap<String,String> results){
		StringBuffer ret = new StringBuffer();
		Iterator iterator = null;
		String key = null;
		
		if (results != null){
			iterator = results.keySet().iterator();
	        while (iterator.hasNext()) {
	            key = (String) iterator.next();
	            ret.append("DAT:|"+key+"|"+results.get(key)+"|");
	        }
		}
		else
			ret.append("|null|");
		
		return (ret.toString());
	}
	
	/**
	 * Convert String containing "0","1" into boolean value
	 * @param value String containing "0", "1" values
	 * @return boolean value
	 */
	public static boolean intToBoolean(String value){
		if (Integer.parseInt(value) == 1)
			return true;
		else
			return false;
	}
	
	public static String toString(ArrayList<String> data, String separator){
		StringBuffer val = new StringBuffer();
		
		for (int index = 0; index < data.size(); index++){
			if (index > 0)
				val.append(separator);
			val.append(data.get(index));
		}
		return (val.toString());
	}
	
	/**
	 * Convert String containing "Y","N" into boolean value
	 * @param value String containing "Y", "N" values
	 * @return boolean value
	 */
	public static boolean toBoolean(String value){
		if (value.equalsIgnoreCase("Y") || value.equalsIgnoreCase("Yes") || value.equalsIgnoreCase("S") || value.equalsIgnoreCase("Si"))
			return true;
		else
			return false;
	}
}
