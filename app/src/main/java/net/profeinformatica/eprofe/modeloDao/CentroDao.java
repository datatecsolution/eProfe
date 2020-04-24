package net.profeinformatica.eprofe.modeloDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import net.profeinformatica.eprofe.dataBase.eProfContract;
import net.profeinformatica.eprofe.modelo.Centro;

import java.util.List;

public class CentroDao extends ModeloDaoBasic {
    protected CentroDao() {
    }

    @Override
    public boolean eliminar(Object c) {
        return false;
    }

    @Override
    public boolean registrar(Object c) {

        Centro centro=(Centro)c;
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.CentrosTable.COLUMN_NAME_ID, centro.getId());
        values.put(eProfContract.CentrosTable.COLUMN_NAME_NOMBRE, centro.getNombre());
        values.put(eProfContract.CentrosTable.COLUMN_NAME_CODIGOSACE , centro.getCodigoSace());
        values.put(eProfContract.CentrosTable.COLUMN_NAME_DIRECCION, centro.getDireccion());
        values.put(eProfContract.CentrosTable.COLUMN_NAME_TELEFONO, centro.getTelefono());
        values.put(eProfContract.CentrosTable.COLUMN_NAME_REMEMBERTOKEN , centro.getRememberToken());
        values.put(eProfContract.CentrosTable.COLUMN_NAME_CREATEDAT, centro.getCreatedAt());
        values.put(eProfContract.CentrosTable.COLUMN_NAME_UPDATEDAT,centro.getUpdatedAt());



        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(eProfContract.CentrosTable.TABLE_NAME, null, values);


        if(newRowId!=-1)
            return true;
        else
            return false;
    }

    @Override
    public boolean actualizar(Object c) {

        Centro centro=(Centro) c;
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.CentrosTable.COLUMN_NAME_ID, centro.getId());
        values.put(eProfContract.CentrosTable.COLUMN_NAME_NOMBRE, centro.getNombre());
        values.put(eProfContract.CentrosTable.COLUMN_NAME_CODIGOSACE , centro.getCodigoSace());
        values.put(eProfContract.CentrosTable.COLUMN_NAME_DIRECCION, centro.getDireccion());
        values.put(eProfContract.CentrosTable.COLUMN_NAME_TELEFONO, centro.getTelefono());
        values.put(eProfContract.CentrosTable.COLUMN_NAME_REMEMBERTOKEN , centro.getRememberToken());
        values.put(eProfContract.CentrosTable.COLUMN_NAME_CREATEDAT, centro.getCreatedAt());
        values.put(eProfContract.CentrosTable.COLUMN_NAME_UPDATEDAT,centro.getUpdatedAt());

        String selection = eProfContract.CentrosTable.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+centro.getId() };

        int count = db.update(
                eProfContract.CentrosTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(count>0)
            return true;
        else
            return false;
    }

    @Override
    public List todos(int limInf, int cantidad) {
        return null;
    }

    @Override
    public List todos() {
        return null;
    }

    @Override
    public Centro buscarPorId(int id) {

        Centro centro=new Centro();
        centro.setId(-1);


        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.CentrosTable.COLUMN_NAME_ID,
                eProfContract.CentrosTable.COLUMN_NAME_NOMBRE ,
                eProfContract.CentrosTable.COLUMN_NAME_CODIGOSACE,
                eProfContract.CentrosTable.COLUMN_NAME_DIRECCION,
                eProfContract.CentrosTable.COLUMN_NAME_TELEFONO,
                eProfContract.CentrosTable.COLUMN_NAME_REMEMBERTOKEN ,
                eProfContract.CentrosTable.COLUMN_NAME_CREATEDAT,
                eProfContract.CentrosTable.COLUMN_NAME_UPDATEDAT
        };


        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.CentrosTable.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+id };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.CentrosTable.COLUMN_NAME_ID + " DESC";

        Cursor cursor = db.query(
                eProfContract.CentrosTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {

            centro.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.CentrosTable.COLUMN_NAME_ID)));
            centro.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.CentrosTable.COLUMN_NAME_NOMBRE)));
            centro.setCodigoSace(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.CentrosTable.COLUMN_NAME_CODIGOSACE)));
            centro.setDireccion(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.CentrosTable.COLUMN_NAME_DIRECCION)));
            centro.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.CentrosTable.COLUMN_NAME_TELEFONO)));
            centro.setRememberToken(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.CentrosTable.COLUMN_NAME_REMEMBERTOKEN)));
            centro.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.CentrosTable.COLUMN_NAME_CREATEDAT)));
            centro.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.CentrosTable.COLUMN_NAME_UPDATEDAT)));

        }

        cursor.close();

        return centro;

    }

    @Override
    public List<Centro> buscarPorDescripcion(String busqueda) {
        return null;
    }

    @Override
    public int sincronizarBDlocal(Object c) {

        //0=no accion, 1=guardar, 2 =actualizar
        int resultado=0;

        Centro centro=(Centro)c;

        Centro centroOnDB=buscarPorId(centro.getId());

        //se comprueba que exita el registro
        if (centroOnDB.getId()==-1){

            //sino exite se registra
            if(registrar(centro))
                resultado=1;

        }else {
            //sino exite se actualiza
            if(actualizar(centro))
                resultado=2;
        }



        return resultado;
    }

    @Override
    public boolean sincronizarServidor(Object c) {
        return false;
    }

    @Override
    public int getGeneratedKeys() {
        return 0;
    }

}
