package net.profeinformatica.eprofe.modeloDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.profeinformatica.eprofe.dataBase.eProfContract;
import net.profeinformatica.eprofe.modelo.Asignatura;
import net.profeinformatica.eprofe.modelo.DetalleAsistencia;
import net.profeinformatica.eprofe.modelo.EncabezadoAsistencia;
import net.profeinformatica.eprofe.modelo.MarcasSincronizado;
import net.profeinformatica.eprofe.modelo.Seccion;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiService;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.ADD_SERVER;
import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.DELETE_SEVER;
import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.NO_ADD_SERVER;
import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.UPDATE_SERVER;

public class EncabezadoAsistenciaDao extends ModeloDaoBasic {
//    private SeccionDao seccionDao2=new SeccionDao();


    @Override
    public boolean eliminar(Object c) {
        EncabezadoAsistencia encabezadoAsistencia=(EncabezadoAsistencia) c;
        DetalleAsistenciaDao detalleAsistenciaDao=new DetalleAsistenciaDao();



        int deletedRows=0;

        //se verifica que el registro no este en agregado al servidor
        if(encabezadoAsistencia.getId()==NO_ADD_SERVER){

            // Gets the data repository in write mode
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Define 'where' part of query.
            String selection = eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_MOVILID + " = ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = { "" +encabezadoAsistencia.getMovilId()};
            // Issue SQL statement.
            deletedRows = db.delete(eProfContract.EncabezadoAsistenciasTable.TABLE_NAME, selection, selectionArgs);

        }else{
            encabezadoAsistencia.setSicronizadoServidor(DELETE_SEVER);
            if(actulizarEstadoSincronizado(encabezadoAsistencia))
                deletedRows=1;

        }



        if(deletedRows>0) {
            detalleAsistenciaDao.eliminar(encabezadoAsistencia);
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean registrar(Object c) {

        EncabezadoAsistencia encabezadoAsistencia=(EncabezadoAsistencia) c;

        encabezadoAsistencia.setSicronizadoServidor(ADD_SERVER);

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_ID,encabezadoAsistencia.getId());
        values.put(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_SECCIONID,encabezadoAsistencia.getSeccionId());
        values.put(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_ASIGNATURAID,encabezadoAsistencia.getAsignaturaId());
        values.put( eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_FECHA,encabezadoAsistencia.getFecha());
        values.put( eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_SINCRONIZACIONSERVER,encabezadoAsistencia.getSicronizadoServidor());
        values.put(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_CREATEDAT,encabezadoAsistencia.getCreatedAt());
        values.put( eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_UPDATEDAT,encabezadoAsistencia.getUpdatedAt());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(eProfContract.EncabezadoAsistenciasTable.TABLE_NAME, null, values);

        if(newRowId!=-1) {
            //se le asigna la id autogenerado al encabezado asistencia
            encabezadoAsistencia.setMovilId(getGeneratedKeys());
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean actualizar(Object c) {

        EncabezadoAsistencia encabezadoAsistencia=(EncabezadoAsistencia) c;

        //se verifica que el registro no este en agregado al servidor
        if(encabezadoAsistencia.getId()==NO_ADD_SERVER){
            encabezadoAsistencia.setSicronizadoServidor(ADD_SERVER);
        }else{
            encabezadoAsistencia.setSicronizadoServidor(UPDATE_SERVER);
        }

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_ID,encabezadoAsistencia.getId());
        values.put(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_SECCIONID,encabezadoAsistencia.getSeccionId());
        values.put(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_ASIGNATURAID,encabezadoAsistencia.getAsignaturaId());
        values.put( eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_FECHA,encabezadoAsistencia.getFecha());
        values.put( eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_SINCRONIZACIONSERVER,encabezadoAsistencia.getSicronizadoServidor());
        values.put(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_CREATEDAT,encabezadoAsistencia.getCreatedAt());
        values.put( eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_UPDATEDAT,encabezadoAsistencia.getUpdatedAt());


        String selection = eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_MOVILID + " = ?";
        String[] selectionArgs = { ""+encabezadoAsistencia.getMovilId()};
        int count = db.update(
                eProfContract.EncabezadoAsistenciasTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(count>0)
            return true;
        else
            return false;
    }

    public boolean actulizarEstadoSincronizado(Object c) {

        EncabezadoAsistencia encabezadoAsistencia=(EncabezadoAsistencia) c;


        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put( eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_SINCRONIZACIONSERVER,encabezadoAsistencia.getSicronizadoServidor());



        String selection = eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_MOVILID + " = ?";
        String[] selectionArgs = { ""+encabezadoAsistencia.getMovilId()};
        int count = db.update(
                eProfContract.EncabezadoAsistenciasTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);



        if(count>0) {
            return true;
        }
        else
            return false;
    }

    @Override
    public List<EncabezadoAsistencia> todos(int limInf, int cantidad) {
        return null;
    }

    @Override
    public List<EncabezadoAsistencia> todos() {
        return null;
    }

    public List<EncabezadoAsistencia> buscarPorSeccionDocente(Integer idSeccion){
        AsignaturaDao asignaturaDao=new AsignaturaDao();
        boolean existe=false;
        List<EncabezadoAsistencia> encabezadoAsistencias=new ArrayList<EncabezadoAsistencia>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        SeccionDao seccionDao2=new SeccionDao();


        String sql=" SELECT * FROM "
                +eProfContract.EncabezadoAsistenciasTable.TABLE_NAME+ " join "
                +eProfContract.AsignaturasSeccionesTable.TABLE_NAME+
                    " ON " +eProfContract.EncabezadoAsistenciasTable.TABLE_NAME+"."+eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_ASIGNATURAID+" = "+eProfContract.AsignaturasSeccionesTable.TABLE_NAME+"." +eProfContract.AsignaturasSeccionesTable.COLUMN_NAME_ASIGNATURAID
                +" WHERE "+eProfContract.AsignaturasSeccionesTable.TABLE_NAME+"."+eProfContract.AsignaturasSeccionesTable.COLUMN_NAME_DOCENTEID+"=? and " +
                eProfContract.EncabezadoAsistenciasTable.TABLE_NAME+"."+eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_SECCIONID+"=? and "+
                eProfContract.EncabezadoAsistenciasTable.TABLE_NAME+"."+eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_SINCRONIZACIONSERVER+"<>3 order by "+eProfContract.EncabezadoAsistenciasTable.TABLE_NAME+"."+eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_MOVILID+" desc";






        String[] selectionArgs = { ""+ModeloDaoBasic.getDocente().getId(),""+ idSeccion};

        Cursor cursor = db.rawQuery(sql,selectionArgs);


        while(cursor.moveToNext()) {

            EncabezadoAsistencia encabezadoAsistencia=new EncabezadoAsistencia();

            encabezadoAsistencia.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_ID)));
            encabezadoAsistencia.setMovilId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_MOVILID)));
            encabezadoAsistencia.setSeccionId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_SECCIONID)));
            encabezadoAsistencia.setAsignaturaId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_ASIGNATURAID)));
            encabezadoAsistencia.setFecha(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_FECHA)));
            encabezadoAsistencia.setSicronizadoServidor(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_SINCRONIZACIONSERVER)));
            encabezadoAsistencia.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_CREATEDAT)));
            encabezadoAsistencia.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_UPDATEDAT)));

            Asignatura asignatura=asignaturaDao.buscarPorId(encabezadoAsistencia.getAsignaturaId());
            encabezadoAsistencia.setAsignatura(asignatura);

           Seccion seccion=seccionDao2.buscarPorId(encabezadoAsistencia.getSeccionId());
           encabezadoAsistencia.setSeccion(seccion);


            encabezadoAsistencias.add(encabezadoAsistencia);

            existe=true;

        }
        cursor.close();

        if(existe)
            return encabezadoAsistencias;
        else
            return null;

    }

    @Override
    public EncabezadoAsistencia buscarPorId(int id) {

        AsignaturaDao asignaturaDao=new AsignaturaDao();

        EncabezadoAsistencia encabezadoAsistencia=new EncabezadoAsistencia();
        encabezadoAsistencia.setId(-1);

        DetalleAsistenciaDao detalleAsistenciaDao=new DetalleAsistenciaDao();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        SeccionDao seccionDao2=new SeccionDao();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_ID,
                eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_MOVILID,
                eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_SECCIONID,
                eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_ASIGNATURAID,
                eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_FECHA,
                eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_SINCRONIZACIONSERVER,
                eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_CREATEDAT,
                eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_UPDATEDAT
        };


        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_MOVILID + " = ? and "
                            +eProfContract.EncabezadoAsistenciasTable.TABLE_NAME+"."+eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_SINCRONIZACIONSERVER+"<>3";
        String[] selectionArgs = { ""+id };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_MOVILID + " DESC";

        Cursor cursor = db.query(
                eProfContract.EncabezadoAsistenciasTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {

            encabezadoAsistencia.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_ID)));
            encabezadoAsistencia.setMovilId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_MOVILID)));
            encabezadoAsistencia.setSeccionId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_SECCIONID)));
            encabezadoAsistencia.setAsignaturaId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_ASIGNATURAID)));
            encabezadoAsistencia.setFecha(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_FECHA)));
            encabezadoAsistencia.setSicronizadoServidor(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_SINCRONIZACIONSERVER)));
            encabezadoAsistencia.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_CREATEDAT)));
            encabezadoAsistencia.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_UPDATEDAT)));

            List<DetalleAsistencia> detalles=detalleAsistenciaDao.buscarPorIdEncabezado(encabezadoAsistencia.getMovilId());
            encabezadoAsistencia.setDetallesAsistencia(detalles);

            Asignatura asignatura=asignaturaDao.buscarPorId(encabezadoAsistencia.getAsignaturaId());
            encabezadoAsistencia.setAsignatura(asignatura);

            Seccion seccion=seccionDao2.buscarPorId(encabezadoAsistencia.getSeccionId());
            encabezadoAsistencia.setSeccion(seccion);

        }
        cursor.close();
        return encabezadoAsistencia;
    }


    public EncabezadoAsistencia buscarPorIdServer(int id) {

        AsignaturaDao asignaturaDao=new AsignaturaDao();

        EncabezadoAsistencia encabezadoAsistencia=new EncabezadoAsistencia();
        encabezadoAsistencia.setId(-1);

        DetalleAsistenciaDao detalleAsistenciaDao=new DetalleAsistenciaDao();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        SeccionDao seccionDao2=new SeccionDao();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_ID,
                eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_MOVILID,
                eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_SECCIONID,
                eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_ASIGNATURAID,
                eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_FECHA,
                eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_SINCRONIZACIONSERVER,
                eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_CREATEDAT,
                eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_UPDATEDAT
        };


        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_ID + " = ? and "
                +eProfContract.EncabezadoAsistenciasTable.TABLE_NAME+"."+eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_SINCRONIZACIONSERVER+"<>3";
        String[] selectionArgs = { ""+id };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_ID + " DESC";

        Cursor cursor = db.query(
                eProfContract.EncabezadoAsistenciasTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {

            encabezadoAsistencia.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_ID)));
            encabezadoAsistencia.setMovilId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_MOVILID)));
            encabezadoAsistencia.setSeccionId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_SECCIONID)));
            encabezadoAsistencia.setAsignaturaId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_ASIGNATURAID)));
            encabezadoAsistencia.setFecha(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_FECHA)));
            encabezadoAsistencia.setSicronizadoServidor(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_SINCRONIZACIONSERVER)));
            encabezadoAsistencia.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_CREATEDAT)));
            encabezadoAsistencia.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.EncabezadoAsistenciasTable.COLUMN_NAME_UPDATEDAT)));

            List<DetalleAsistencia> detalles=detalleAsistenciaDao.buscarPorIdEncabezado(encabezadoAsistencia.getId());
            encabezadoAsistencia.setDetallesAsistencia(detalles);

            Asignatura asignatura=asignaturaDao.buscarPorId(encabezadoAsistencia.getAsignaturaId());
            encabezadoAsistencia.setAsignatura(asignatura);

            Seccion seccion=seccionDao2.buscarPorId(encabezadoAsistencia.getSeccionId());
            encabezadoAsistencia.setSeccion(seccion);

        }
        cursor.close();
        return encabezadoAsistencia;
    }

    @Override
    public List<EncabezadoAsistencia> buscarPorDescripcion(String busqueda) {
        return null;
    }

    @Override
    public int sincronizarBDlocal(Object c) {
        //0=no accion, 1=guardar, 2 =actualizar
        int resultado=0;

        EncabezadoAsistencia encabezadoAsistencia=(EncabezadoAsistencia)c;

        EncabezadoAsistencia encabezadoAsistenciaOnBD=buscarPorId(encabezadoAsistencia.getId());

        //se comprueba que la seccion exite
        if (encabezadoAsistenciaOnBD.getId()==-1){
            if(registrar(encabezadoAsistencia)){
                resultado=1;
            }

        }else {
            encabezadoAsistencia.setMovilId(encabezadoAsistenciaOnBD.getMovilId());
            if(actualizar(encabezadoAsistencia)){
                resultado=2;
            }
        }
        return resultado;
    }

    @Override
    public boolean sincronizarServidor(Object c) {

        Seccion seccion =(Seccion)c;

       final DetalleAsistenciaDao detalleAsistenciaDao=new DetalleAsistenciaDao();

        ApiService mAPIService = ApiUtils.getApiService();
        mAPIService.getAsistencias(ModeloDaoBasic.getDocente().getId(),seccion.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<EncabezadoAsistencia>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(new String("ERRORRRRR:===============>" +e.getMessage()));

                    }

                    @Override
                    public void onNext(List<EncabezadoAsistencia> encabezadoAsistencias) {

                        // System.out.println(new String("EXITOOOOOOO:===============>"+encabezadoAsistencias.get(0).toString()));
                        if(encabezadoAsistencias!=null && !encabezadoAsistencias.isEmpty()) {

                            for (int i = 0; i < encabezadoAsistencias.size(); i++) {

                                //se marca el registro como sincronizado con el servidor
                                encabezadoAsistencias.get(i).setSicronizadoServidor(MarcasSincronizado.SINCRONIZADO);

                                //se manda sincronizar con la base de datos local
                                int resul=sincronizarBDlocal(encabezadoAsistencias.get(i));

                                if(resul!=0 && encabezadoAsistencias.get(i).getDetallesAsistencia()!=null){

                                    List<DetalleAsistencia> detalles=encabezadoAsistencias.get(i).getDetallesAsistencia();

                                    for (int c=0;c<detalles.size();c++) {
                                        //se agrega al detalle el codigo del encabezado en el movil
                                        detalles.get(c).setEncabezadoasistenciaMovilId(encabezadoAsistencias.get(i).getMovilId());
                                        detalleAsistenciaDao.sincronizarBDlocal(detalles.get(c));
                                    }
                                }

                            }

                        }

                    }
                });


        return false;
    }

    @Override
    public int getGeneratedKeys() {
        int key=0;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT seq FROM SQLITE_SEQUENCE where name=?";

        String[] selectionArgs = {eProfContract.EncabezadoAsistenciasTable.TABLE_NAME};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        if (cursor.moveToFirst()){
            do{

                key=cursor.getInt(cursor.getColumnIndex("seq"));

            }while (cursor.moveToNext());
        }

        cursor.close();

        if(key!=0)
            return key;
        else
            return 0;
    }
}
