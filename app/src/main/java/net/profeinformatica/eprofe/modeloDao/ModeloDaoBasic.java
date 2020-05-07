package net.profeinformatica.eprofe.modeloDao;

import net.profeinformatica.eprofe.dataBase.eProfDbHelper;
import net.profeinformatica.eprofe.modelo.Docente;

import java.text.SimpleDateFormat;
import java.util.List;


public abstract class  ModeloDaoBasic {

	private static Docente docente=null;
	protected static eProfDbHelper dbHelper = new eProfDbHelper(null);


	public static Docente getDocente() {
		return docente;
	}

	public static void setDocente(Docente docente) {
		ModeloDaoBasic.docente = docente;
	}
	protected ModeloDaoBasic(){
		//dbHelper=new eProfDbHelper(null);
	}




	public static SimpleDateFormat getDateFormatterOnlyYear() {
		return dateFormatterOnlyYear;
	}

	public static void setDateFormatterOnlyYear(SimpleDateFormat dateFormatterOnlyYear) {
		ModeloDaoBasic.dateFormatterOnlyYear = dateFormatterOnlyYear;
	}

	private static SimpleDateFormat dateFormatterOnlyYear = new SimpleDateFormat("yyy");
	
	abstract public  boolean eliminar(Object c);
	abstract public boolean registrar(Object c);
	abstract public boolean actualizar(Object c);
	abstract public List todos(int limInf,int cantidad);
	abstract public List todos();
	abstract public Object buscarPorId(int id);
	abstract public List buscarPorDescripcion(String busqueda);
	abstract public int sincronizarBDlocal(Object c);
	abstract public boolean sincronizarServidor(Object c);
	abstract public int getGeneratedKeys();
	

	
}
