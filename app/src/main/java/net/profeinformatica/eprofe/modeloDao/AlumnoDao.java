package net.profeinformatica.eprofe.modeloDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.profeinformatica.eprofe.dataBase.eProfContract;
import net.profeinformatica.eprofe.modelo.Alumno;
import net.profeinformatica.eprofe.modelo.Matricula;
import net.profeinformatica.eprofe.modelo.Seccion;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiService;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiUtils;

import java.util.Calendar;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.UPDATE_SERVER;

public class AlumnoDao extends ModeloDaoBasic {

    private MatriculaDao matriculaDaoAlumno=new MatriculaDao();


    @Override
    public boolean eliminar(Object c) {
        return false;
    }

    @Override
    public boolean registrar(Object c) {
        Alumno alumno=(Alumno)c;

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_ID,alumno.getId());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_RNE,alumno.getRne());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_USERNAME,alumno.getUsername());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_NOMBRE,alumno.getNombre());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_APELLIDO,alumno.getApellido());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_GENERO,alumno.getGenero());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_TELEFONO,alumno.getTelefono());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_EMAIL,alumno.getEmail());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_PASSWORD,alumno.getPassword());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_SINCRONIZACIONSERVER,alumno.getSicronizadoServidor());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_REMEMBERTOKEN,alumno.getRememberToken());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_CREATEDAT,alumno.getCreatedAt());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_UPDATEDAT,alumno.getUpdatedAt());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(eProfContract.AlumnosTable.TABLE_NAME, null, values);

        if(newRowId!=-1)
            return true;
        else
            return false;
    }

    @Override
    public boolean actualizar(Object c) {
        Alumno alumno=(Alumno) c;

        alumno.setSicronizadoServidor(UPDATE_SERVER);

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_ID,alumno.getId());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_RNE,alumno.getRne());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_USERNAME,alumno.getUsername());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_NOMBRE,alumno.getNombre());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_APELLIDO,alumno.getApellido());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_GENERO,alumno.getGenero());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_TELEFONO,alumno.getTelefono());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_EMAIL,alumno.getEmail());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_PASSWORD,alumno.getPassword());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_SINCRONIZACIONSERVER,alumno.getSicronizadoServidor());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_REMEMBERTOKEN,alumno.getRememberToken());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_CREATEDAT,alumno.getCreatedAt());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_UPDATEDAT,alumno.getUpdatedAt());

        String selection = eProfContract.AlumnosTable.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+alumno.getId() };
        int count = db.update(
                eProfContract.AlumnosTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(count>0)
            return true;
        else
            return false;
    }

    @Override
    public List<Alumno> todos(int limInf, int cantidad) {
        return null;
    }

    @Override
    public List<Alumno> todos() {
        return null;
    }

    @Override
    public Alumno buscarPorId(int id) {

        Alumno alumno=new Alumno();
        alumno.setId(-1);

        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.AlumnosTable.COLUMN_NAME_ID,
                eProfContract.AlumnosTable.COLUMN_NAME_RNE,
                eProfContract.AlumnosTable.COLUMN_NAME_USERNAME,
                eProfContract.AlumnosTable.COLUMN_NAME_NOMBRE,
                eProfContract.AlumnosTable.COLUMN_NAME_APELLIDO,
                eProfContract.AlumnosTable.COLUMN_NAME_GENERO,
                eProfContract.AlumnosTable.COLUMN_NAME_TELEFONO,
                eProfContract.AlumnosTable.COLUMN_NAME_EMAIL,
                eProfContract.AlumnosTable.COLUMN_NAME_PASSWORD,
                eProfContract.AlumnosTable.COLUMN_NAME_SINCRONIZACIONSERVER,
                eProfContract.AlumnosTable.COLUMN_NAME_REMEMBERTOKEN,
                eProfContract.AlumnosTable.COLUMN_NAME_CREATEDAT,
                eProfContract.AlumnosTable.COLUMN_NAME_UPDATEDAT
        };



        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.AlumnosTable.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+id };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.AlumnosTable.COLUMN_NAME_ID + " ASC";

        Cursor cursor = db.query(
                eProfContract.AlumnosTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {

            alumno.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_ID)));
            alumno.setRne(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_RNE)));
            alumno.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_USERNAME)));
            alumno.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_NOMBRE)));
            alumno.setApellido(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_APELLIDO)));
            alumno.setGenero(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_GENERO)));
            alumno.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_TELEFONO)));
            alumno.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_EMAIL)));
            alumno.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_PASSWORD)));
            //boolean sincronizacion=cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_SINCRONIZACIONSERVER) == 1 ? true : false;
            alumno.setSicronizadoServidor(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_SINCRONIZACIONSERVER));
            alumno.setRememberToken(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_REMEMBERTOKEN)));
            alumno.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_CREATEDAT)));
            alumno.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_UPDATEDAT)));




        }
        cursor.close();


        return alumno;
    }

    @Override
    public List<Alumno> buscarPorDescripcion(String busqueda) {
        return null;
    }

    @Override
    public int sincronizarBDlocal(Object c) {

        //0=no accion, 1=guardar, 2 =actualizar
        int resultado=0;

        Alumno alumno=(Alumno) c;

        Alumno alumnoOnBD=buscarPorId(alumno.getId());

        if (alumnoOnBD.getId()==-1){

            if (registrar(alumno)){

                resultado=1;
            }
        }else{
            if(actualizar(alumno)){
                resultado=2;
            }
        }
        return resultado;
    }

    /**
     * Permite sicronizar los alumnos y la matricula de las secciones.
     * @author jdmayorga
     * @param c se debe enviar una Seccion
     *
     */

    @Override
    public boolean sincronizarServidor(Object c) {

        Seccion seccion=(Seccion)c;

        String year= ModeloDaoBasic.getDateFormatterOnlyYear().format(Calendar.getInstance().getTime());

        ApiService mAPIService = ApiUtils.getApiService();


        mAPIService.getAlumnosSeccion(year,seccion.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Matricula>>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {

                       //System.out.println(new String(" Error ========>>>>>"+e.getMessage() ));


                    }

                    @Override
                    public void onNext(List<Matricula> matriculas) {

                        //System.out.println(new String(" # Matricula ========>>>>>" +matriculas.size()));

                        if(matriculas!=null) {
                            for (int x = 0; x < matriculas.size(); x++) {


                                matriculas.get(x).getAlumno().setSicronizadoServidor(1);


                                //se manda a sincronizar en la base de datos local a el alumno
                                int resul=sincronizarBDlocal(matriculas.get(x).getAlumno());
                                //System.out.println(new String("Resultado al sicronizar alumno ========>>>>>" +resul));
                                if(resul!=0){
                                    //System.out.println(new String(" Se manta a sicronizar la matricula========>>>>>" ));
                                    matriculaDaoAlumno.sincronizarBDlocal(matriculas.get(x));//si el alumno se guarda se registra la matricula

                                }
                            }
                        }



                    }
                });

        return false;
    }

    @Override
    public int getGeneratedKeys() {
        return 0;
    }

}
