package net.profeinformatica.eprofe.modeloDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.profeinformatica.eprofe.dataBase.eProfContract;
import net.profeinformatica.eprofe.modelo.Acumulativo;
import net.profeinformatica.eprofe.modelo.Alumno;
import net.profeinformatica.eprofe.modelo.NotaAcumulativo;

import java.util.ArrayList;
import java.util.List;

import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.ADD_SERVER;
import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.DELETE_SEVER;
import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.NO_ADD_SERVER;
import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.UPDATE_SERVER;

public class NotaAcumulativosDao extends ModeloDaoBasic {

    public NotaAcumulativosDao() {

    }

    @Override
    public boolean eliminar(Object c) {

        Acumulativo acumulativo=(Acumulativo) c;


        int deletedRows=0;

        //se verifica que el registro no este en agregado al servidor
        if(acumulativo.getId()==NO_ADD_SERVER){

            // Gets the data repository in write mode
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Define 'where' part of query.
            String selection = eProfContract.NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_MOVIL_ID + " = ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = { "" +acumulativo.getMovilId()};
            // Issue SQL statement.
            deletedRows = db.delete(eProfContract.NotaAcumulativosTable.TABLE_NAME, selection, selectionArgs);

        }else{
            acumulativo.setSicronizadoServidor(DELETE_SEVER);
            if(actulizarEstadoSincronizado(acumulativo))
                deletedRows=1;

        }



        if(deletedRows>0)
            return true;
        else
            return false;
    }

    @Override
    public boolean registrar(Object c) {


        NotaAcumulativo notaAcumulativo=(NotaAcumulativo)c;

        notaAcumulativo.setSicronizadoServidor(ADD_SERVER);

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ID,notaAcumulativo.getId());
        values.put(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ALUMNO_ID,notaAcumulativo.getAlumnoId());
        values.put(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_ID,notaAcumulativo.getAcumulativoId());
        values.put(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_MOVIL_ID,notaAcumulativo.getAcumulativoMovilId());
        values.put(eProfContract.NotaAcumulativosTable.COLUMN_NAME_NOTA,notaAcumulativo.getNota());
        values.put(eProfContract.NotaAcumulativosTable.COLUMN_NAME_SINCRONIZACION_SERVER,notaAcumulativo.getSicronizadoServidor());
        values.put(eProfContract.NotaAcumulativosTable.COLUMN_NAME_CREATEDAT,notaAcumulativo.getCreatedAt());
        values.put(eProfContract.NotaAcumulativosTable.COLUMN_NAME_UPDATEDAT,notaAcumulativo.getUpdatedAt());


        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(eProfContract.NotaAcumulativosTable.TABLE_NAME, null, values);

        if(newRowId!=-1)
            return true;
        else
            return false;
    }

    @Override
    public boolean actualizar(Object c) {
        NotaAcumulativo notaAcumulativo=(NotaAcumulativo)c;

        //se verifica que el registro no este en agregado al servidor
        if(notaAcumulativo.getId()==NO_ADD_SERVER){
            notaAcumulativo.setSicronizadoServidor(ADD_SERVER);
        }else{
            notaAcumulativo.setSicronizadoServidor(UPDATE_SERVER);
        }

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ID,notaAcumulativo.getId());
        values.put(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ALUMNO_ID,notaAcumulativo.getAlumnoId());
        values.put(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_ID,notaAcumulativo.getAcumulativoId());
        values.put(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_MOVIL_ID,notaAcumulativo.getAcumulativoMovilId());
        values.put(eProfContract.NotaAcumulativosTable.COLUMN_NAME_NOTA,notaAcumulativo.getNota());
        values.put(eProfContract.NotaAcumulativosTable.COLUMN_NAME_SINCRONIZACION_SERVER,notaAcumulativo.getSicronizadoServidor());
        values.put(eProfContract.NotaAcumulativosTable.COLUMN_NAME_CREATEDAT,notaAcumulativo.getCreatedAt());
        values.put(eProfContract.NotaAcumulativosTable.COLUMN_NAME_UPDATEDAT,notaAcumulativo.getUpdatedAt());


        String selection = eProfContract.NotaAcumulativosTable.COLUMN_NAME_MOVIL_ID + " = ?";
        String[] selectionArgs = { ""+notaAcumulativo.getMovilId()};
        int count = db.update(
                eProfContract.NotaAcumulativosTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(count>0)
            return true;
        else
            return false;
    }

    @Override
    public List<NotaAcumulativo> todos(int limInf, int cantidad) {
        return null;
    }

    @Override
    public List<NotaAcumulativo> todos() {
        return null;
    }

    @Override
    public NotaAcumulativo buscarPorId(int id) {
        NotaAcumulativo notaAcumulativo=new NotaAcumulativo();
        notaAcumulativo.setId(-1);

        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_ID,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_MOVIL_ID,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_ALUMNO_ID,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_ID,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_MOVIL_ID,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_NOTA,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_SINCRONIZACION_SERVER,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_CREATEDAT,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_UPDATEDAT
        };



        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.NotaAcumulativosTable.COLUMN_NAME_MOVIL_ID + " = ? and "
                +eProfContract.NotaAcumulativosTable.COLUMN_NAME_SINCRONIZACION_SERVER+" <> 3";
        String[] selectionArgs = { ""+id };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.NotaAcumulativosTable.COLUMN_NAME_MOVIL_ID + " DESC";

        Cursor cursor = db.query(
                eProfContract.NotaAcumulativosTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {

            notaAcumulativo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ID)));
            notaAcumulativo.setMovilId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_MOVIL_ID)));
            notaAcumulativo.setAlumnoId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ALUMNO_ID)));
            notaAcumulativo.setAcumulativoId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_ID)));
            notaAcumulativo.setAcumulativoMovilId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_MOVIL_ID)));
            notaAcumulativo.setNota(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_NOTA)));
            notaAcumulativo.setSicronizadoServidor(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_SINCRONIZACION_SERVER)));
            notaAcumulativo.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_CREATEDAT)));
            notaAcumulativo.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_UPDATEDAT)));


        }
        cursor.close();
        return notaAcumulativo;
    }

    public List<NotaAcumulativo> buscarPorIdAcumulativo(int id) {
        List<NotaAcumulativo> notas=new ArrayList<NotaAcumulativo>();
        boolean existe=false;
        AlumnoDao alumnoDao =new AlumnoDao();

        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_ID,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_MOVIL_ID,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_ALUMNO_ID,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_ID,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_MOVIL_ID,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_NOTA,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_SINCRONIZACION_SERVER,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_CREATEDAT,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_UPDATEDAT
        };



        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_MOVIL_ID + " = ? and "
                            +eProfContract.NotaAcumulativosTable.COLUMN_NAME_SINCRONIZACION_SERVER+" <> 3";
        String[] selectionArgs = { ""+id };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.NotaAcumulativosTable.COLUMN_NAME_ALUMNO_ID + " ASC";

        Cursor cursor = db.query(
                eProfContract.NotaAcumulativosTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {
            NotaAcumulativo notaAcumulativo=new NotaAcumulativo();

            notaAcumulativo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ID)));
            notaAcumulativo.setMovilId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_MOVIL_ID)));
            notaAcumulativo.setAlumnoId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ALUMNO_ID)));
            notaAcumulativo.setAcumulativoId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_ID)));
            notaAcumulativo.setAcumulativoMovilId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_MOVIL_ID)));
            notaAcumulativo.setNota(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_NOTA)));
            notaAcumulativo.setSicronizadoServidor(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_SINCRONIZACION_SERVER)));
            notaAcumulativo.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_CREATEDAT)));
            notaAcumulativo.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_UPDATEDAT)));

            Alumno alumno=alumnoDao.buscarPorId(notaAcumulativo.getAlumnoId());
            if (alumno.getId()!=-1)
                notaAcumulativo.setAlumno(alumno);

            notas.add(notaAcumulativo);

            //System.out.println(new String("Entro a las notas ==============================>"+notaAcumulativo.toString() ));

            existe=true;


        }
        cursor.close();
        if(existe)
            return notas;
        else
            return null;
    }




    public NotaAcumulativo buscarPorIdServer(int id) {
        NotaAcumulativo notaAcumulativo=new NotaAcumulativo();
        notaAcumulativo.setId(-1);

        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_ID,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_MOVIL_ID,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_ALUMNO_ID,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_ID,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_NOTA,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_SINCRONIZACION_SERVER,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_CREATEDAT,
                eProfContract.NotaAcumulativosTable.COLUMN_NAME_UPDATEDAT
        };



        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.NotaAcumulativosTable.COLUMN_NAME_ID + " = ? and "
                +eProfContract.NotaAcumulativosTable.COLUMN_NAME_SINCRONIZACION_SERVER+" <> 3";
        String[] selectionArgs = { ""+id };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.NotaAcumulativosTable.COLUMN_NAME_ID + " DESC";

        Cursor cursor = db.query(
                eProfContract.NotaAcumulativosTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {

            notaAcumulativo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ID)));
            notaAcumulativo.setMovilId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_MOVIL_ID)));
            notaAcumulativo.setAlumnoId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ALUMNO_ID)));
            notaAcumulativo.setAcumulativoId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_ID)));
            notaAcumulativo.setNota(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_NOTA)));
            notaAcumulativo.setSicronizadoServidor(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_SINCRONIZACION_SERVER)));
            notaAcumulativo.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_CREATEDAT)));
            notaAcumulativo.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_UPDATEDAT)));


        }
        cursor.close();
        return notaAcumulativo;
    }

    @Override
    public List<NotaAcumulativo> buscarPorDescripcion(String busqueda) {
        return null;
    }

    @Override
    public int sincronizarBDlocal(Object c) {
        //0=no accion, 1=guardar, 2 =actualizar
        int resultado=0;
        NotaAcumulativo notaAcumulativo=(NotaAcumulativo)c;
        NotaAcumulativo notaAcumulativoOnBD=buscarPorIdServer(notaAcumulativo.getId());

        if(notaAcumulativoOnBD.getId()==-1){
            if(registrar(notaAcumulativo)){
                resultado=1;
            }
        }else{
            notaAcumulativo.setMovilId(notaAcumulativoOnBD.getMovilId());
            if(actualizar(notaAcumulativo)){
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

        String[] selectionArgs = {eProfContract.NotaAcumulativosTable.TABLE_NAME};

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

    public boolean actulizarEstadoSincronizado(Acumulativo acumulativo) {


        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.NotaAcumulativosTable.COLUMN_NAME_SINCRONIZACION_SERVER,acumulativo.getSicronizadoServidor());



        String selection = eProfContract.NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_MOVIL_ID + " = ?";
        String[] selectionArgs = { ""+acumulativo.getMovilId() };
        int count = db.update(
                eProfContract.NotaAcumulativosTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(count>0)
            return true;
        else
            return false;
    }

    public List<NotaAcumulativo> buscarPorAsignSeccionAlumParcial(Integer idAsignatura, String parcial, Integer idSeccion, Integer idAlumno) {

        List<NotaAcumulativo> notas=new ArrayList<NotaAcumulativo>();
        boolean existe=false;

        AlumnoDao alumnoDao =new AlumnoDao();
        AcumulativosDao acumulativosDao=new AcumulativosDao();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql=" SELECT "

                +eProfContract.NotaAcumulativosTable.TABLE_NAME+"."+eProfContract.NotaAcumulativosTable.COLUMN_NAME_ID+" , "
                +eProfContract.NotaAcumulativosTable.TABLE_NAME+"."+eProfContract.NotaAcumulativosTable.COLUMN_NAME_MOVIL_ID+" , "
                +eProfContract.NotaAcumulativosTable.TABLE_NAME+"."+eProfContract.NotaAcumulativosTable.COLUMN_NAME_ALUMNO_ID+" , "
                +eProfContract.NotaAcumulativosTable.TABLE_NAME+"."+eProfContract.NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_ID+" , "
                +eProfContract.NotaAcumulativosTable.TABLE_NAME+"."+eProfContract.NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_MOVIL_ID+" , "
                +eProfContract.NotaAcumulativosTable.TABLE_NAME+"."+eProfContract.NotaAcumulativosTable.COLUMN_NAME_NOTA+" , "
                +eProfContract.NotaAcumulativosTable.TABLE_NAME+"."+eProfContract.NotaAcumulativosTable.COLUMN_NAME_SINCRONIZACION_SERVER+" , "
                +eProfContract.NotaAcumulativosTable.TABLE_NAME+"."+eProfContract.NotaAcumulativosTable.COLUMN_NAME_CREATEDAT+" , "
                +eProfContract.NotaAcumulativosTable.TABLE_NAME+"."+eProfContract.NotaAcumulativosTable.COLUMN_NAME_UPDATEDAT+
                " FROM " +eProfContract.NotaAcumulativosTable.TABLE_NAME+
                                " join " +eProfContract.AcumulativosTable.TABLE_NAME+
                                         " ON " +eProfContract.NotaAcumulativosTable.TABLE_NAME+"."+eProfContract.NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_MOVIL_ID+" = "+eProfContract.AcumulativosTable.TABLE_NAME+"." +eProfContract.AcumulativosTable.COLUMN_NAME_MOVILID
                        +" WHERE "+eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_ASIGNAURAID+"=? and " +
                eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_PARCIAL+"=? and " +
                eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_SECCIONID+"=? and " +
                eProfContract.NotaAcumulativosTable.TABLE_NAME+"."+eProfContract.NotaAcumulativosTable.COLUMN_NAME_ALUMNO_ID+"=? and "+
                eProfContract.NotaAcumulativosTable.TABLE_NAME+"."+eProfContract.NotaAcumulativosTable.COLUMN_NAME_SINCRONIZACION_SERVER+" <> 3";





        String[] selectionArgs = { ""+idAsignatura,parcial,""+idSeccion,""+idAlumno};

        Cursor cursor = db.rawQuery(sql,selectionArgs);

       // System.out.println(new String("Exitooooo en NOTA Acumulativo:===================>"+sql));




        while(cursor.moveToNext()) {
            NotaAcumulativo notaAcumulativo=new NotaAcumulativo();

            notaAcumulativo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ID)));
            notaAcumulativo.setMovilId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_MOVIL_ID)));
            notaAcumulativo.setAlumnoId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ALUMNO_ID)));
            notaAcumulativo.setAcumulativoId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_ID)));
            notaAcumulativo.setAcumulativoMovilId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_ACUMULATIVO_MOVIL_ID)));
            notaAcumulativo.setNota(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_NOTA)));
            notaAcumulativo.setSicronizadoServidor(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_SINCRONIZACION_SERVER)));
            notaAcumulativo.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_CREATEDAT)));
            notaAcumulativo.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.NotaAcumulativosTable.COLUMN_NAME_UPDATEDAT)));

            Alumno alumno=alumnoDao.buscarPorId(notaAcumulativo.getAlumnoId());
            if (alumno.getId()!=-1)
                notaAcumulativo.setAlumno(alumno);

            Acumulativo acumulativo=acumulativosDao.buscarPorId(notaAcumulativo.getAcumulativoMovilId());
            if(acumulativo.getId()!=-1)
                notaAcumulativo.setAcumulativo(acumulativo);

            notas.add(notaAcumulativo);

           // System.out.println(new String("Entro a las notas ==============================>"+notaAcumulativo.toString() ));

            existe=true;


        }
        cursor.close();
        if(existe)
            return notas;
        else
            return null;
    }
}
