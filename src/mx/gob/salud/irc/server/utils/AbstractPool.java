package mx.gob.salud.irc.server.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 *  Clase abstracta para crear un pool de cualquier tipo de objetos,
 *  la sincronización, generación y borrado de objetos ya está implementada,
 *  solo se necesitan sobrecargar dos métodos, uno que devuelva una instancia
 *  del objeto deseado (este método básicamente se implementa con la llamada de
 *  un constructor) y un evento que se llama antes de liberar el objeto, en
 *  donde se pueden agregar funciones para reinicializar el objeto, liberar
 *  una conexión a bd, cerrar archivos, etc.
 *
 */
public abstract class AbstractPool{
	 /** Utilizo un collection porque le voy quitando y poniendo elementos.
	  */
    private Collection freeObjects = new HashSet();
	/** Utilizo un mapa porque necesito llevar la cuenta de cuántas veces ha sido
	    usado cada objeto.*/
	 //private Map allObjects   = new HashMap();
    private int min;
    private int max;
    private int borrowedObjects = 0;

    protected AbstractPool(int min, int max){
    	
		System.out.println("Constructor de AbstractPool");
        this.min = min;
        this.max = max;

        for(int i=0; i < this.min; i++)
			  addNewObject();
    }

	 /** Rutina para crear un nuevo objeto, agregarlo al arreglo "freeObjects"
	  * (los objetos que están en el pool) y también agregarlo al "allObjects"
	  * (los objetos que han estado en el pool) con una cuenta de 0 usos.
	  */
	 @SuppressWarnings("unchecked")
	protected Object addNewObject() {
		Object obj = createObject();
		freeObjects.add(obj);
		//allObjects.put(obj, new Integer(0));
		return obj;
	 }
    
    protected abstract Object createObject();

	 /** Le suma uno al número de "usos" de cada objeto 
	  * @param obj El objeto en cuestión
	  * @return El número nuevo de usos que tiene el objeto 
	  * */
	 /*
	 public int addOne(Object obj) {
		 int valor = ((Integer)allObjects.get(obj)).intValue();
		 allObjects.put(obj, new Integer(valor + 1));
		 return valor +1;
	 }
	 */

	 /** Remueve el objeto del mapa allObjects, porque ya no va a ser utilizado. */
	 /*
	 public void resetObject(Object obj) {
		 System.out.println("ResetObject: objeto " + obj);
		 allObjects.remove(obj);
	 }
	 */

	 /** Devuelve el mapa que mantiene todos los objetos de este pool.
	  */
	 /*
	 public Map getAllObjectsPool() {
		 return allObjects;
	 }
	 */

	 /** Devuelve la lista que mantiene los objetos de este pool.
	  */
	 public Collection getPool() {
		 return freeObjects;
	 }

    public synchronized Object getObject(){
        //Mientras no existan objetos disponibles
        while(freeObjects.size() < 1){
            /*  Si ya prestamos tantos objetos como teníamos permitidos
                entonces tendremos que esperar a que se libere uno
             */
            if(borrowedObjects >= max){
                try{
                    wait();
                } catch(InterruptedException interruptedException){ }
            }
            /*  Si todavía podemos prestar más, entonces creamos otro*/
            else{
					 addNewObject();
            }
        }
        /*  Prestamos un objeto del mapa: obtengo un numero aleatorio
			*  para saber cual escoger (por culpa del HashSet, que siempre
			*  utiliza el mismo orden en sus elementos) */
		  int numObjeto = (int)(Math.random() * freeObjects.size() + 1);
		  Iterator it = freeObjects.iterator();
		  Object object = null;
		  for (int i=0; i<numObjeto ; i++)
			  object = it.next();
		  freeObjects.remove(object);
		  // Si testObject devuelve true -significando que el objeto es válido-, 
		  // devuelvo el objeto en cuestión
        if(testObject(object)){
            borrowedObjects++;
            return object;
        } else {
			   System.out.println("Posible desconexión... reintentando en 1 seg");
				try {
					Thread.sleep(1000);
				} catch (Exception eSleep) { }
				//Quité el getObject porque estaba devolviendo objetos del pool, no creando
				//uno nuevo...
            //return getObject();
			   return addNewObject();
        }
    }

	 /** Agrego el objeto de vuelta al arreglo freeObjects, pero no al 
	  * allObjects, porque ya está ahí y desde aquí no se suma uno. */
    @SuppressWarnings("unchecked")
	public synchronized void freeObject(Object object){
        if(beforeFreeObject(object)){
            freeObjects.add(object);
            
        }
        /*  Nos devolvieron un objeto asi que lo apuntamos y avisamos a quien
                esté esperando que ya hay un objeto libre*/
        borrowedObjects--;
        notify();
    }
    /**
     *  El método se debe implementar por si se quiere hacer algo con el objeto
     *  que va a formar parte de los objetos libres, si no se quiere que el objeto
     *  vuelva a formar parte de los objetos libres, sino que la intención es
     *  desecharlo, entonces el método debe regresar false.
     */
    protected abstract boolean beforeFreeObject(Object object);
    /**
     *  La implementación de este método debe devolver true si es un objeto
     *  valido y false si es un objeto no valido, el método es llamado antes
     *  de devolver un objeto en <code>getObject()</code>.  El método
     *  es especialmente útil cuando se necesita probar que un objeto esté
     *  listo como una conexión a bd.
     */
    protected abstract boolean testObject(Object object);
}
