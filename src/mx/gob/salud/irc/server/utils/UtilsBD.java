package mx.gob.salud.irc.server.utils;

import java.sql.*;
//import java.io.*;
import java.util.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.sql.*;
//import javax.naming.*;

/** 
* Clase wrapper para las operaciones de base de datos. <br>
* Se emplea para abreviar el acceso a la base de datos, sin tener
* que declarar excepciones y bloques try..catch. <br>
* Los mï¿½todos principales son queryMapa, queryLista, queryInt y queryString;
* estos dos ï¿½ltimos asumen que se debe devolver sï¿½lo la primer columna
* del primer renglï¿½n del resultado, independientenemente del query.
* <br>. Todos los mï¿½todos que devuelven resultados de queries devuelven
* un objeto null cuando el query no devuelve resultados o tiene error, excepto
* queryInt que devuelve un -1.<br>
* <b>Uso recomendado</b><br>
* En general, es recomendable instanciar el objeto UtilsBD() dentro del mï¿½todo
* que lo manda llamar, porque su inicializaciï¿½n es casi gratuita y asï¿½ se evita
* tener el objeto rondando por ahï¿½ en el caso de hacerlo variable miembro de la clase. 
* De este modo, el cï¿½digo se mantiene limpio de potenciales problemas por el uso de 
* variables de clase y contenciï¿½n causada por cï¿½digo no thread-safe. Esta clase
* no es thread-safe porque no sincroniza en ningï¿½n momento sus mï¿½todos, para 
* darle velocidad.<br>
* El uso tï¿½pico es simplemente hacer lo siguiente:
* <pre>String nombre = new UtilsBD().queryString("select nombre from tabla");</pre>
* El ejemplo anterior aplica para queries que devuelvan un renglï¿½n con una columna. 
* Cuando sabemos que el resultado serï¿½ un <i>int</i>, podemos hacer lo siguiente:
* <pre>int max = new UtilsBD().queryInt("select max(id) from tabla");</pre>
* Para casos mï¿½s generales, se utiliza el mï¿½todo queryLista, o si sabemos que
* el query me devuelve sï¿½lo un renglï¿½n, puede usarse queryMapa. Ver la documentaciï¿½n
* de las clases que acceden estos datos para entender cï¿½mo consumir estos resultados.
* <br>
* <b>Soporte para transacciones</b><br>
* Simplemente hay que mandar llamar al mï¿½todo iniciaTran(), que se encarga 
* de inicializar una conexiï¿½n y utilizarla en todos los queries subsecuentes.
* Al final se debe llamar, obviamente, a commit() o a rollback() en caso de
* problemas; sin embargo, si el programador olvida llamar alguno de esos dos mï¿½todos,
* asumo que todo va bien y en el mï¿½todo destroy() del objeto hago el commit,
* devolviendo la conexiï¿½n al pool (esto es importantï¿½simo, de lo contrario estarï¿½a
* consumiendo conexiones del pool cada vez que utilizara transacciones).<br>
* Despuï¿½s de llamar al mï¿½todo commit() o rollback(), el objeto UtilsBD sigue
* siendo vï¿½lido para ejecutar queries, sin embargo 
*
* <br><br>
* Julio 2007: DV, cambios para hacer que soporte transacciones.
* 
* @author Dario Vasconcelos (dario.vasconcelos@gmail.com)
* @version 
*/
public class UtilsBD {
//	private static final Logger logger = Logger.getLogger(UtilsBD.class.getName());
	
	/** 
	 * Contiene la conexiï¿½n que se utilizarï¿½ sï¿½lo cuando se inicie una
	 * transacciï¿½n con el mï¿½todo iniciaTran().
	 */
	private Connection conexionInterna = null;

	/**
	 * Contiene un statement que se almacena para que se pueda hacer un batch.
	 */
	private Statement stmtInterno = null;

	/** 
	 * True cuando ya quiero que se cierre la conexion interna; util para
	 * los metodos commit() y rollback(). 
	 */
	private boolean cierraConexionInterna = false;

	/** 
	 * Guarda el estatus de la ultima operacion ejecutada. 
	 */
	private boolean ultimaOperacionOK = false;
	
	/** 
	 * Apunta hacia el pool de conexiones; es una variable estatica para solo
	 * tener que obtener una vez la referencia hacia ella. <br>Es un objeto
	 * de tipo javax.sql.DataSource. 
	 */
//	private static DataSource pool = getDataSource();
	//private static JCMCache pool = getJCMCache();
	static DBConnectionPool pool = DBConnectionPool.getInstance();
	
	/******************************** Inicia definiciï¿½n de mï¿½todos ******************/

	/*	
	private static JCMCache getJCMCache() {
		try {
			return JCM.getCacheByName(Propiedades.get("db.dataSource"));
		} catch (Exception e) {
			System.out.println("Error al obtener el cache:" + e.getMessage());
		}
		return null;
	}
	*/

//	/** 
//	 * Obtiene el data source desde el archivo de propiedades y lo utiliza
//	 * para las conexiones a la base de datos. 
//	 * 
//	 * @return 
//	 */
//	private static DataSource getDataSource() {
//		/*
//		Context ctx = null;
//		try {
//			logger.log(Level.INFO, "Utilizando datasource " + 
//				Propiedades.get("db.dataSource"));
//
//			ctx = new InitialContext();
//			DataSource ds = (DataSource)ctx.lookup("jdbc/" + Propiedades.get("db.dataSource"));
//			return ds;
//		} catch (NamingException e) {
//			// Intento con el otro tipo de DS (tomcat)
//			try {
//				logger.log(Level.FINE, "Intentando con el otro dataSource");
//				Context envCtx = (Context) ctx.lookup("java:comp/env");
//				return (DataSource)envCtx.lookup("jdbc/" + Propiedades.get("db.dataSource"));
//			} catch (Exception e2) {
//				System.out.println("Error al inicializar el datasource: consulte al administrador");
//				System.out.println(e2.getMessage());
//				//e2.printStackTrace();
//			}
//		}
//		return null;
//		*/
//		DataSource dataSource = (DataSource) BeanFactory.getBean("DataSource");
//		return dataSource;
//	}

	/** Devuelve una referencia al pool, que es una variable estatica
	 */
	//public JCMCache getPool() {
//	public DataSource getPool() {
//		return pool;
//	}

	/** Devuelve una referencia al pool, que es una variable estatica
	 */
	public DBConnectionPool getPool() {
		return pool;
	}

	/**
	 * Se usa cuando se quiere utilizar directamente el objeto Connection para alguna
	 * tarea no implementada en esta clase, como PreparedStatements o similares.<br>
	 * NO funciona con transacciones.
	 * 
	 * @return una Conexion nueva.
	 */
	public Connection getConnection() {
		try {
			return pool.getConnection();
		} catch (Exception e) {
			System.out.println("UtilsBD: error en getConnection: " + e.getMessage());
		}
		return null;
	}

	/** 
	 * Inicia una transacciï¿½n; internamente, abre una conexiï¿½n a la base de datos
	 * y pone el autoCommit a false, para que todas las operaciones se ejecuten dentro
	 * de una transacciï¿½n. 
	 */
	public boolean iniciaTran() {
		try {
			this.conexionInterna = pool.getConnection();
			this.conexionInterna.setAutoCommit(false);
			ultimaOperacionOK = true;
		} catch (Exception e) {
			System.out.println(getHora() + "UtilsBD, Error en iniciaTran: " + e.getMessage());
			// Si no puedo abrir la conexion, mando un false para 
			// que la aplicacion maneje el problema...
			return false;
		}
		return true;
	}

	/**
	 * Inicializa el modo batch, por supuesto solo si estoy en modo transaccion
	 * 
	 * @return
	 */
	public boolean iniciaBatch() {
		ultimaOperacionOK = false;
		if (conexionInterna == null) 
			return false;
		try {
			stmtInterno = conexionInterna.createStatement();
		} catch (Exception e) {
			System.out.println(getHora() + "UtilsBD, Error en iniciaBatch: " + e.getMessage());
			return false;
		}
		ultimaOperacionOK = true;
		return true;
	}

	/**
	 * Agrega una sentencia al batch del statement interno, solamente debe utilizarse
	 * si antes ya se inicializo el batch y si estoy usando transacciones.
	 * @param query
	 * @return
	 */
	public boolean addBatch(String query) {
		ultimaOperacionOK = false;
		if (stmtInterno == null) 
			return false;
		try {
			stmtInterno.addBatch(query);
		} catch (Exception e) {
			System.out.println(getHora() + "UtilsBD, Error en addBatch: " + e.getMessage());
			return false;
		}
		ultimaOperacionOK = true;
		return true;
	}

	public String ejecutaBatch() {
		ultimaOperacionOK = false;
		if (stmtInterno == null) 
			return "Opci&oacute;n inv&aacute;lida: no se ha inicializado el batch";
		try {
			stmtInterno.executeBatch();
		} catch (BatchUpdateException e) {
			int[] updates = e.getUpdateCounts();
			for (int i=0; i<updates.length; i++)
				System.out.println("Update " + i + " , " + updates[i]);
			System.out.println(getHora() + "UtilsBD, Error en addBatch (bue): " + e.getMessage());
			return e.getMessage();
		} catch (SQLException e) {
			System.out.println(getHora() + "UtilsBD, Error en addBatch: " + e.getLocalizedMessage());
			SQLException siguiente = e.getNextException();
			while (siguiente != null) {
				System.out.println("error -->" + siguiente.getLocalizedMessage());
				siguiente = siguiente.getNextException();
			}
			return e.getMessage();
		}
		ultimaOperacionOK = true;
		return null;
	}

	/** 
	 * Ejecuta el commit a la conexion y la cierra para que el programador
	 * no tenga que ejecutar siempre el devuelveConexion(); si quiere reutilizar
	 * el objeto UtilsBD, estarï¿½ usï¿½ndolo en modo no-transacciï¿½n, a menos por 
	 * supuesto que haga un iniciaTran().
	 * 
	 * @return true si no hubo ningun problema al hacer el commit, false
	 * 		si hubo problemas o si no estoy en modo de transaccion
	 */
	public boolean commit() {
		if (this.conexionInterna == null)
			return false;
		try {
			this.conexionInterna.commit();
		} catch (Exception e) {
			System.out.println(getHora() + "Excepcion al hacer el commit");
			return false;
		} finally {
			// En cualquier caso, devuelvo la conexiï¿½n al pool y la pongo 
			// a null.
			cierraConexionInterna = true;
			cierraJDBC(null, this.conexionInterna);
			this.conexionInterna = null;
		}
		return true;
	}

	/** 
	 * Mismo caso que el commit, pero para el rollback.
	 * 
	 * @return 
	 */
	public boolean rollback() {
		if (this.conexionInterna == null)
			return false;
		try {
			this.conexionInterna.rollback();
		} catch (Exception e) {
			System.out.println(getHora() + "Excepcion al hacer el rollback" + e.getMessage());
			return false;
		} finally {
			// En cualquier caso, devuelvo la conexiï¿½n al pool y la pongo 
			// a null.
			cierraConexionInterna = true;
			cierraJDBC(null, this.conexionInterna);
			this.conexionInterna = null;
		}
		return true;
	}

	/** 
	 * Llamado cuando el objeto es desechado del heap; si estaba en modo
	 * transaccional, entonces hace el commit() (para entender las razones de esto,
	 * leer la documentaciï¿½n del inicio de la clase) y desecha la conexiï¿½n.
	 * 
	 * @throws Throwable 
	 */
	protected void finalize() throws Throwable {
		if (this.conexionInterna != null) {
			this.conexionInterna.commit();
			cierraJDBC(null, this.conexionInterna);
			this.conexionInterna = null;
		}
	}

	/** 
	 * Devuelve el estatus de la ï¿½ltima operaciï¿½n ejecutada. 
	 * 
	 * @return 
	 */
	public boolean isOK() {
		return ultimaOperacionOK;
	}

	/** 
	 * Ejecuta un query, y devuelve un Resultset. Este mï¿½todo no utiliza toda
	 * la infraestructura de devolver Collections, de modo que hay que utilizarlo
	 * con cuidado porque no devuelve la conexiï¿½n al pool ni estï¿½ preparado
	 * para utilizar transacciones intrï¿½nsecamente.
	 * 
	 * @param query 
	 * @param stmt 
	 * @param rs 
	 * @return 
	 */
	public ResultSet query( String query, Statement stmt, ResultSet rs) {
		try {
			rs = stmt.executeQuery( query );
		} catch (Exception e) {
			System.out.println(getHora() + "query: Error ejecutando el query " + query +
						                    "\n              Motivo: " + e.getMessage());
		}
		return rs;
	}

	/** 
	 * Ejecuta un update o insert, a partir de una cadena que es el query. 
	 * 
	 * @param query Query a ejecutar
	 * @return Un -1 si algo estï¿½ mal
	 */
	public int update( String query ) {
		Connection con = null;
		Statement stmt = null;
		int result = -1;
		try {
			if (conexionInterna == null)
				con = pool.getConnection();
			else 
				con = conexionInterna;
			stmt = con.createStatement();
			result = stmt.executeUpdate( query );
			ultimaOperacionOK = true;
		} catch (Exception e) {
			System.out.println(getHora() + "update: Error ejecutando el update " + query +
												  "\n               Motivo: " + e.getMessage());
			ultimaOperacionOK = false;
		} finally {
			cierraJDBC(stmt, con);
		}
		return result;
	}

	/** 
	 * Simplemente llama a update(String query) porque el cï¿½digo es el mismo;
	 * creï¿½ este mï¿½todo para que el cï¿½digo resultante sea mï¿½s claro. 
	 * 
	 * @param query 
	 * @return 
	 */
	public int insert( String query) {
		return update(query);
	}

	/** 
	 * Simplemente llama a update(String query) porque el cï¿½digo es el mismo;
	 * creï¿½ este mï¿½todo para que el cï¿½digo resultante sea mï¿½s claro. 
	 * 
	 * @param query 
	 * @return 
	 */
	public int delete( String query) {
		return update(query);
	}

	/** Simplifica obtener un sï¿½lo dato de un query (entero); devuelve un -1 cuando ocurre un
	 * error (por lo tanto, hay que cuidar que -1 no sea un resultado vï¿½lido)
	 * @param query Cadena con el query a ejecutar
	 * @return El valor como int de la 1a columna del 1er renglï¿½n del resultado. -1 si ocurre un error al ejecutar el query.
	 */
	public int queryInt( String query ) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs   = null;
		int result = -1;
		try {
			if (conexionInterna == null)
				con = pool.getConnection();
			else 
				con = conexionInterna;
			stmt = con.createStatement();
			rs = query( query, stmt, rs);
			rs.next();
			result = rs.getInt(1);
			ultimaOperacionOK = true;
		} catch (Exception e) {
			ultimaOperacionOK = false;
		} finally {
			cierraJDBC(rs, stmt, con);
		}
		return result;
	}

	/** Simplifica obtener un sï¿½lo dato de un query (String); devuelve <i>null</i> 
	 * si ocurre error en el query.
	 * @param query Cadena con el query a ejecutar
	 * @return El valor como String de la 1a columna del 1er renglï¿½n del resultado. null si ocurre un error al ejecutar el query.
	 */
	public String queryString( String query ) {
		Connection con = null;
		String result = null;
		Statement stmt = null;
		ResultSet rs   = null;
		try {
			if (conexionInterna == null)
				con = pool.getConnection();
			else 
				con = conexionInterna;
			stmt = con.createStatement();
			rs = query( query, stmt, rs);
			rs.next();
			result = rs.getString(1);
			ultimaOperacionOK = true;
		} catch (Exception e) {
			ultimaOperacionOK = false;
		} finally {
			cierraJDBC(rs, stmt, con);
		}
		return result;
	}

	/** 
	 * Devuelve una lista llena de mapas, uno por cada renglï¿½n del ResultSet. 
	 * 
	 * @param query 
	 * @return 
	 */
	public ArrayList<HashMap<String, String>> queryLista( String query ) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs   = null;
		ArrayList<HashMap<String, String>> lista = null;
		try {
			if (conexionInterna == null)
				con = pool.getConnection();
			else 
				con = conexionInterna;
			stmt = con.createStatement();
			System.out.println("UtilsBD.queryLista(), query = " + query);
			rs = query( query, stmt, rs);
			lista = RSToArrayList(rs);
			ultimaOperacionOK = true;
		} catch (Exception e) {
			ultimaOperacionOK = false;
		} finally {
			cierraJDBC(rs, stmt, con);
		}
		return lista;
	}

	/** 
	 * Devuelve un mapa, para un query que devuelve sï¿½lo un
	 * renglï¿½n. <br>Es importante mencionar que dentro del mapa
	 * cada valor queda ligado al nombre de la columna, convertido
	 * a minï¿½sculas. 
	 * 
	 * @param query 
	 * @return 
	 */
	public HashMap queryMapa( String query ) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs   = null;
		ArrayList lista = null;
		HashMap mapa = null;
		try {
			if (conexionInterna == null)
				con = pool.getConnection();
			else 
				con = conexionInterna;
			stmt = con.createStatement();
			System.out.println("UtilsBD.queryMapa(), query = " + query);
			rs = query( query, stmt, rs);
			lista = RSToArrayList(rs);
			mapa = (HashMap)lista.get(0);
			ultimaOperacionOK = false;
		} catch (Exception e) {
			ultimaOperacionOK = false;
		} finally {
			cierraJDBC(rs, stmt, con);
		}
		return mapa;
	}

	/** Convierte un renglï¿½n (sï¿½lo uno) de un ResultSet en un HashMap. ï¿½til
	 * para cuando hay que guardar los datos de un resultado en un Collection
	 * de algï¿½n tipo. Para que todo el ResultSet quede en un Collection, llamar
	 * a RSToHashMap.<br>Antes de agregar la columna al mapa, le hago un toLowerCase()
	 * para evitar problemas.
	 * @param rs ResultSet con el renglï¿½n correcto (no le hago next() desde aquï¿½)
	 * @return Mapa con los nombres de las columnas asociados con sus valores.
	 */
	public static HashMap<String,String> RSRowToHashMap (ResultSet rs) throws SQLException {
		HashMap<String,String> mapa = new HashMap<String,String>();
		ResultSetMetaData meta = rs.getMetaData();
		int numCols = meta.getColumnCount();
		for (int i=1; i<=numCols; i++) {
			String nombre = meta.getColumnLabel(i).toLowerCase();
			mapa.put(nombre, rs.getString(i));
		}
		return mapa;
	}

	/** 
	 * Convierte un ResultSet a una lista, agregando un mapa por cada renglï¿½n del
	 * resultado. 
	 * 
	 * @param rs 
	 */
	public static ArrayList<HashMap<String, String>> RSToArrayList (ResultSet rs) {
		ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			while (rs.next()) {
				HashMap<String,String> mapa = RSRowToHashMap( rs );
				lista.add(mapa);
			}
		} catch (Exception e) {
			System.out.println(getHora() + "Error al recorrer el rs: " + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			//cierraJDBC(rs, stmt, con);
		}
		return lista;
	}

	/** Alimenta todos los objetos que estï¿½n dentro de una lista a un 
	 * preparedStatement. Esto lo hace obteniendo el tipo de cada objeto
	 * del arrayList, para alimentar el tipo correcto.
	 */
	@SuppressWarnings("unused")
	private static void alimentaParams( PreparedStatement stmt, ArrayList lista) {
		try {
			for (int i=0; i<lista.size(); i++) {
				Object elem = lista.get(i);
				if (elem instanceof String)
					stmt.setString(i+1,(String)elem);
				else if (elem instanceof Integer)
					stmt.setInt(i+1,((Integer)elem).intValue());
				else if (elem instanceof java.sql.Date)
					stmt.setDate(i+1,(java.sql.Date)elem);
				else
					System.out.println(getHora() + "Error: elemento de tipo desconocido: " + elem.getClass().getName());

			}
		} catch (Exception e) {
			System.out.println(getHora() + "Error: el nï¿½mero de parï¿½metros en la lista no coincide con " +
					" el statement: " + lista);
		}
	}

	/** Devuelve la hora, formateada para el log de errores. 
	 */
	protected static String getHora() {
		Calendar fecha = Calendar.getInstance();
		return fecha.get(Calendar.MONTH) + "/" +
				 fecha.get(Calendar.DAY_OF_MONTH) + "/" +
				 fecha.get(Calendar.YEAR) + " " +
				 fecha.get(Calendar.HOUR_OF_DAY) + ":" +
				 fecha.get(Calendar.MINUTE) + ":" +
				 fecha.get(Calendar.SECOND) + ":" +
				 fecha.get(Calendar.MILLISECOND) + ". ";
	}

	/** 
	 * Cierra la conexiï¿½n y hace todas las operaciones de limpieza (cierra el
	 * resultset, y cierra el statement y conexiï¿½n). 
	 * 
	 * @param rs 
	 * @param stmt 
	 * @param con 
	 */
	public void cierraJDBC(ResultSet rs, Statement stmt, Connection con) {
		try {
			if (rs != null) 
				rs.close();
		} catch (Exception e) {
			System.out.println(getHora() + "Error en cierraJDBC: " + e.getMessage());
		} finally {
			rs = null;
			cierraJDBC(stmt, con);
		}
	}

	/** 
	 * Cierra un statement y una conexiï¿½n. 
	 * <br>
	 * Julio 2007 DV. Le hice un cambio para que, si estoy utilizando
	 * el modo transaccional, se cierre el Statement pero no el 
	 * connection, de modo que sï¿½lo se cierra un objeto Connection
	 * en modo transaccional si antes de llamar a cierraJDBC pongo
	 * la variable <i>cierraConexionInterna</i> a true.
	 * 
	 * @param stmt 
	 * @param con 
	 */
	public void cierraJDBC(Statement stmt, Connection con) {
		try {
			if (stmt != null) 
				stmt.close();
		} catch (Exception e) {
			System.out.println(getHora() + "Error en cierraJDBC: " + e.getMessage());
		} finally {
			stmt = null;
			if (con == null)
				return;
			if (cierraConexionInterna == true || this.conexionInterna == null) {
				// Solo entro a este bloque si la conexion interna es nulo (es decir,
				// no estoy en modo transaccional) o si cierraConexionInterna es 
				// true, es decir, estoy haciendo commit o rollback.
				try {
					pool.freeConnection(con);
					//con.close(); // El close es la seï¿½al para que el DataSource devuelva
									// la conexiï¿½n al pool.
					cierraConexionInterna = false;
				} catch (Exception e2) {
					con = null;
					System.out.println(getHora() + "Error en cierraJDBC, cerrando conexion: " + 
												e2.getMessage());
					// Probablemente no hay nada que hacer, el pool no va a poder recuperar
					// esta conexion.
				}
			} // fin del if con!= null...
		} // fin del finally.
	}
      
    /**
     * @Parameters
     *    query - Es la cadena preconstruida SQL
     * @Return
     *    result - Es el resultset que se obtiene, este debera
     *    cerrarse posteriormente mediante el metodo
     *    public void cierraRS(ResultSet rs)
     */
      
   public ResultSet ejecutaQuery(String query) throws Exception{
       Connection con = null;
       Statement stmt = null;
       ResultSet result = null;
       try {
          con = pool.getConnection();
          stmt = con.createStatement();
          result = stmt.executeQuery(query);
      } catch (Exception se) {
          System.out.println(getHora() + "ejecutaQuery: Error ejecutando el query " + query +
                  "\n               Motivo: " + se.getMessage());
          throw se;
      } finally {
          //cierraJDBC(stmt, con);
      }
      return result;
  }
         
    /**
     * Este metodo se utiliza para cerrar un resultset desde una clase de servicios
     * @Parameters
     *    rs - Es el resultset a cerrarse
     */
   
  public void cierraRS(ResultSet rs){
      try{
          rs.close();
      }
      catch (Exception e){
          e.printStackTrace();
      }
  }

	 /** 
	  * Devuelve true si estoy utilizando una conexion interna. Utilizado para los
	  * casos de prueba, en los que quiero saber si se esta abriendo una conexion
	  * interna. 
	  * 
	  * @return 
	  */
	 public boolean getHayConexionInterna() {
		 return (this.conexionInterna != null);
	 }
	 
	 
	/** 
	 * Ejecuta un SP el cual puede tener datos de salida, p.e.: "sp_i_dato 'a',1,'x',?,?,?"
	 * 
	 * @param query Query a ejecutar
	 * @param numResultados Es el numero salidas
	 * @return Un -1 si algo estï¿½ mal
	 */
	public String updateSP( String query, int numResultados ) {
		Connection con = null;
		CallableStatement cstmt = null;
		StringBuilder result = new StringBuilder();
		try {
			if (conexionInterna == null)
				con = pool.getConnection();
			else 
				con = conexionInterna;
			
			cstmt = con.prepareCall(query);
			if(numResultados > 0){
				for(int i = 1; i <= numResultados; i ++){
					cstmt.registerOutParameter(i, Types.VARCHAR);
				}
			}
			cstmt.executeUpdate();
			
			if(numResultados > 0){
				for(int i = 1; i <= numResultados; i ++){
					result.append(cstmt.getString(i));
					result.append(",");
				}
				result.deleteCharAt(result.length()); // elimino la última coma
			}
			
			ultimaOperacionOK = true;
		} catch (Exception e) {
			System.out.println(getHora() + "update: Error ejecutando el update " + query +  "\n               Motivo: " + e.getMessage());
			ultimaOperacionOK = false;
		} finally {
			cierraJDBC(cstmt, con);
		}
		return result.toString();
	}
}

