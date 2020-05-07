package net.profeinformatica.eprofe.modeloDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.profeinformatica.eprofe.dataBase.eProfContract;
import net.profeinformatica.eprofe.modelo.DetalleAsistencia;
import net.profeinformatica.eprofe.modelo.EncabezadoAsistencia;

import java.util.ArrayList;
import java.util.List;

import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.ADD_SERVER;
import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.DELETE_SEVER;
import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.NO_ADD_SERVER;
import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.UPDATE_SERVER;

public class DetalleAsistenciaDao extends ModeloDaoBasic {

    @Override
    public boolean eliminar(Object c) {

        EncabezadoAsistencia encabezadoAsistencia=(EncabezadoAsistencia)c;

        //DetalleAsistencia detalleAsistencia=(DetalleAsistencia) c;


        int deletedRows=0;

        //se verifica que el registro no este en agregado al servidor
        if(encabezadoAsistencia.getId()==NO_ADD_SERVER){

            // Gets the data repository in write mode
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Define 'where' part of query.
            String selection = eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ENCABEZADOASISTENCIA_MOIVIL_ID + " = ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = { "" +encabezadoAsistencia.getMovilId()};
            // Issue SQL statement.
            deletedRows = db.delete(eProfContract.DetallesAsistenciasTable.TABLE_NAME, selection, selectionArgs);

        }else{
            encabezadoAsistencia.setSicronizadoServidor(DELETE_SEVER);
            if(actulizarEstadoSincronizado(encabezadoAsistencia))
                deletedRows=1;

        }


        if(deletedRows>0)
            return true;
        else
            return false;
    }

    @Override
    public boolean registrar(Object c) {
        DetalleAsistencia detalleAsistencia=(DetalleAsistencia) c;

        detalleAsistencia.setSicronizadoServidor(ADD_SERVER);

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ID,detalleAsistencia.getId());
        values.put(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ALUMNO_ID,detalleAsistencia.getAlumnoId());
        values.put(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ENCABEZADOASISTENCIA_ID,detalleAsistencia.getEncabezadoasistenciaId());
        values.put(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ENCABEZADOASISTENCIA_MOIVIL_ID,detalleAsistencia.getEncabezadoasistenciaMovilId());
        values.put(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ESTADO,detalleAsistencia.isEstado());
        values.put(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_EXCUSA,detalleAsistencia.isExcusa());
        values.put(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_SINCRONIZACION_SERVER,detalleAsistencia.getSicronizadoServidor());
        values.put(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_CREATEDAT,detalleAsistencia.getCreatedAt());
        values.put(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_UPDATEDAT,detalleAsistencia.getUpdatedAt());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(eProfContract.DetallesAsistenciasTable.TABLE_NAME, null, values);

        if(newRowId!=-1)
            return true;
        else
            return false;
    }

    @Override
    public boolean actualizar(Object c) {

        DetalleAsistencia detalleAsistencia=(DetalleAsistencia) c;

        //se verifica que el registro no este en agregado al servidor
        if(detalleAsistencia.getId()==NO_ADD_SERVER){
            detalleAsistencia.setSicronizadoServidor(ADD_SERVER);
        }else{
            detalleAsistencia.setSicronizadoServidor(UPDATE_SERVER);
        }

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ID,detalleAsistencia.getId());
        values.put(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ALUMNO_ID,detalleAsistencia.getAlumnoId());
        values.put(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ENCABEZADOASISTENCIA_ID,detalleAsistencia.getEncabezadoasistenciaId());
        values.put(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ENCABEZADOASISTENCIA_MOIVIL_ID,detalleAsistencia.getEncabezadoasistenciaMovilId());
        values.put(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ESTADO,detalleAsistencia.isEstado());
        values.put(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_EXCUSA,detalleAsistencia.isExcusa());
        values.put(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_SINCRONIZACION_SERVER,detalleAsistencia.getSicronizadoServidor());
        values.put(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_CREATEDAT,detalleAsistencia.getCreatedAt());
        values.put(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_UPDATEDAT,detalleAsistencia.getUpdatedAt());


        String selection = eProfContract.DetallesAsistenciasTable.COLUMN_NAME_MOVILID + " = ?";
        String[] selectionArgs = { ""+detalleAsistencia.getMovilId() };
        int count = db.update(
                eProfContract.DetallesAsistenciasTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(count>0)
            return true;
        else
            return false;
    }


    public boolean actulizarEstadoSincronizado(EncabezadoAsistencia encabezadoAsistencia) {

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_SINCRONIZACION_SERVER,encabezadoAsistencia.getSicronizadoServidor());



        String selection = eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ENCABEZADOASISTENCIA_MOIVIL_ID + " = ?";
        String[] selectionArgs = { ""+encabezadoAsistencia.getMovilId() };
        int count = db.update(
                eProfContract.DetallesAsistenciasTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(count>0)
            return true;
        else
            return false;
    }

    @Override
    public List<DetalleAsistencia> todos(int limInf, int cantidad) {
        return null;
    }

    @Override
    public List<DetalleAsistencia> todos() {
        return null;
    }

    @Override
    public DetalleAsistencia buscarPorId(int id) {

        DetalleAsistencia detalleAsistencia=new DetalleAsistencia();
        detalleAsistencia.setId(-1);

        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ID,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_MOVILID,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ALUMNO_ID,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ENCABEZADOASISTENCIA_ID,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ENCABEZADOASISTENCIA_MOIVIL_ID,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ESTADO,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_EXCUSA,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_SINCRONIZACION_SERVER,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_CREATEDAT,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_UPDATEDAT
        };




        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.DetallesAsistenciasTable.COLUMN_NAME_MOVILID + " = ? and "
                +eProfContract.DetallesAsistenciasTable.COLUMN_NAME_SINCRONIZACION_SERVER+" <>3 ";
        String[] selectionArgs = { ""+id };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.DetallesAsistenciasTable.COLUMN_NAME_MOVILID + " DESC";

        Cursor cursor = db.query(
                eProfContract.DetallesAsistenciasTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {

            detalleAsistencia.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ID)));
            detalleAsistencia.setMovilId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_MOVILID)));
            detalleAsistencia.setAlumnoId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ALUMNO_ID)));
            detalleAsistencia.setEncabezadoasistenciaId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ENCABEZADOASISTENCIA_ID)));
            detalleAsistencia.setEncabezadoasistenciaMovilId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ENCABEZADOASISTENCIA_MOIVIL_ID)));
            detalleAsistencia.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ESTADO)));
            detalleAsistencia.setExcusa(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_EXCUSA)));
            detalleAsistencia.setSicronizadoServidor(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_SINCRONIZACION_SERVER)));
            detalleAsistencia.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_CREATEDAT)));
            detalleAsistencia.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_UPDATEDAT)));

        }
        cursor.close();
        return detalleAsistencia;
    }

    public DetalleAsistencia buscarPorIdServer(int id) {

        DetalleAsistencia detalleAsistencia=new DetalleAsistencia();
        detalleAsistencia.setId(-1);

        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ID,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_MOVILID,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ALUMNO_ID,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ENCABEZADOASISTENCIA_ID,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ENCABEZADOASISTENCIA_MOIVIL_ID,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ESTADO,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_EXCUSA,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_SINCRONIZACION_SERVER,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_CREATEDAT,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_UPDATEDAT
        };




        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ID + " = ? and "
                            +eProfContract.DetallesAsistenciasTable.COLUMN_NAME_SINCRONIZACION_SERVER+" <>3 ";
        String[] selectionArgs = { ""+id };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ID + " DESC";

        Cursor cursor = db.query(
                eProfContract.DetallesAsistenciasTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {

            detalleAsistencia.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ID)));
            detalleAsistencia.setMovilId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_MOVILID)));
            detalleAsistencia.setAlumnoId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ALUMNO_ID)));
            detalleAsistencia.setEncabezadoasistenciaId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ENCABEZADOASISTENCIA_ID)));
            detalleAsistencia.setEncabezadoasistenciaMovilId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ENCABEZADOASISTENCIA_MOIVIL_ID)));
            detalleAsistencia.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ESTADO)));
            detalleAsistencia.setExcusa(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_EXCUSA)));
            detalleAsistencia.setSicronizadoServidor(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_SINCRONIZACION_SERVER)));
            detalleAsistencia.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_CREATEDAT)));
            detalleAsistencia.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_UPDATEDAT)));

        }
        cursor.close();
        return detalleAsistencia;
    }


    public List<DetalleAsistencia> buscarPorIdEncabezado(int idEncabezado) {

        List<DetalleAsistencia> detalleAsistencias=new ArrayList<DetalleAsistencia>();

        boolean exite=false;

        AlumnoDao alumnoDao=new AlumnoDao();

        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ID,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_MOVILID,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ALUMNO_ID,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ENCABEZADOASISTENCIA_ID,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ENCABEZADOASISTENCIA_MOIVIL_ID,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ESTADO,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_EXCUSA,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_SINCRONIZACION_SERVER,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_CREATEDAT,
                eProfContract.DetallesAsistenciasTable.COLUMN_NAME_UPDATEDAT
        };




        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ENCABEZADOASISTENCIA_MOIVIL_ID + " = ? and "
                            +eProfContract.DetallesAsistenciasTable.COLUMN_NAME_SINCRONIZACION_SERVER+" <>3 ";
        String[] selectionArgs = { ""+idEncabezado };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ID + " ASC";

        Cursor cursor = db.query(
                eProfContract.DetallesAsistenciasTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {

            DetalleAsistencia detalleAsistencia=new DetalleAsistencia();

            detalleAsistencia.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ID)));
            detalleAsistencia.setMovilId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_MOVILID)));
            detalleAsistencia.setAlumnoId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ALUMNO_ID)));
            detalleAsistencia.setEncabezadoasistenciaId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ENCABEZADOASISTENCIA_ID)));
            detalleAsistencia.setEncabezadoasistenciaMovilId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ENCABEZADOASISTENCIA_MOIVIL_ID)));
            detalleAsistencia.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_ESTADO)));
            detalleAsistencia.setExcusa(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_EXCUSA)));
            detalleAsistencia.setSicronizadoServidor(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_SINCRONIZACION_SERVER)));
            detalleAsistencia.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_CREATEDAT)));
            detalleAsistencia.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.DetallesAsistenciasTable.COLUMN_NAME_UPDATEDAT)));

            detalleAsistencia.setAlumno(alumnoDao.buscarPorId(detalleAsistencia.getAlumnoId()));

            detalleAsistencias.add(detalleAsistencia);
            exite=true;

        }
        cursor.close();
        if (exite)
            return detalleAsistencias;
        else
            return null;
    }

    @Override
    public List<DetalleAsistencia> buscarPorDescripcion(String busqueda) {
        return null;
    }

    @Override
    public int sincronizarBDlocal(Object c) {
        //0=no accion, 1=guardar, 2 =actualizar
        int resultado=0;

        DetalleAsistencia detalleAsistencia=(DetalleAsistencia) c;
        DetalleAsistencia detalleAsistenciaOnBD=buscarPorIdServer(detalleAsistencia.getId());

        //se comprueba que la seccion exite
        if (detalleAsistenciaOnBD.getId()==-1){
            if(registrar(detalleAsistencia)){
                resultado=1;
            }

        }else {
            detalleAsistencia.setMovilId(detalleAsistenciaOnBD.getMovilId());
            if(actualizar(detalleAsistencia)){
                resultado=2;
            }
        }
        return resultado;
    }

    @Override
    public boolean sincronizarServidor(Object c) {
        return false;
    }

    @Override
    public int getGeneratedKeys() {
        int key=0;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT seq FROM SQLITE_SEQUENCE where name=?";

        String[] selectionArgs = {eProfContract.DetallesAsistenciasTable.TABLE_NAME};

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
