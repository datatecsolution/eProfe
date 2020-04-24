package net.profeinformatica.eprofe.modeloDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.profeinformatica.eprofe.dataBase.eProfContract;
import net.profeinformatica.eprofe.modelo.Modalidad;

import java.util.List;

public class ModalidadDao extends ModeloDaoBasic {


    @Override
    public boolean eliminar(Object c) {
        return false;
    }

    @Override
    public boolean registrar(Object c) {

        Modalidad modalidad=(Modalidad)c;

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.ModalidadesTable.COLUMN_NAME_ID, modalidad.getId());
        values.put(eProfContract.ModalidadesTable.COLUMN_NAME_ALIAS, modalidad.getAlias());
        values.put(eProfContract.ModalidadesTable.COLUMN_NAME_NOMBRE , modalidad.getNombre());
        values.put(eProfContract.ModalidadesTable.COLUMN_NAME_OBSERVACIONES, modalidad.getObservaciones());
        values.put(eProfContract.ModalidadesTable.COLUMN_NAME_CREATEDAT, modalidad.getCreatedAt());
        values.put(eProfContract.ModalidadesTable.COLUMN_NAME_UPDATEDAT,modalidad.getUpdatedAt());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(eProfContract.ModalidadesTable.TABLE_NAME, null, values);


        if(newRowId!=-1)
            return true;
        else
            return false;

    }

    @Override
    public boolean actualizar(Object c) {

        Modalidad modalidad=(Modalidad) c;
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.ModalidadesTable.COLUMN_NAME_ID, modalidad.getId());
        values.put(eProfContract.ModalidadesTable.COLUMN_NAME_ALIAS, modalidad.getAlias());
        values.put(eProfContract.ModalidadesTable.COLUMN_NAME_NOMBRE , modalidad.getNombre());
        values.put(eProfContract.ModalidadesTable.COLUMN_NAME_OBSERVACIONES, modalidad.getObservaciones());
        values.put(eProfContract.ModalidadesTable.COLUMN_NAME_CREATEDAT, modalidad.getCreatedAt());
        values.put(eProfContract.ModalidadesTable.COLUMN_NAME_UPDATEDAT,modalidad.getUpdatedAt());
        String selection = eProfContract.ModalidadesTable.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+modalidad.getId() };
        int count = db.update(
                eProfContract.ModalidadesTable.TABLE_NAME,
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
    public Modalidad buscarPorId(int id) {
        Modalidad modalidad=new Modalidad();
        modalidad.setId(-1);

        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.ModalidadesTable.COLUMN_NAME_ID,
                eProfContract.ModalidadesTable.COLUMN_NAME_ALIAS,
                eProfContract.ModalidadesTable.COLUMN_NAME_NOMBRE ,
                eProfContract.ModalidadesTable.COLUMN_NAME_OBSERVACIONES,
                eProfContract.ModalidadesTable.COLUMN_NAME_CREATEDAT,
                eProfContract.ModalidadesTable.COLUMN_NAME_UPDATEDAT
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.ModalidadesTable.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+id };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.ModalidadesTable.COLUMN_NAME_ID + " DESC";

        Cursor cursor = db.query(
                eProfContract.ModalidadesTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {

            modalidad.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.ModalidadesTable.COLUMN_NAME_ID)));
            modalidad.setAlias(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.ModalidadesTable.COLUMN_NAME_ALIAS)));
            modalidad.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.ModalidadesTable.COLUMN_NAME_NOMBRE)));
            modalidad.setObservaciones(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.ModalidadesTable.COLUMN_NAME_OBSERVACIONES)));
            modalidad.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.ModalidadesTable.COLUMN_NAME_CREATEDAT)));
            modalidad.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.ModalidadesTable.COLUMN_NAME_UPDATEDAT)));

        }
        cursor.close();
        return modalidad;
    }

    @Override
    public List<Modalidad> buscarPorDescripcion(String busqueda) {
        return null;
    }

    @Override
    public int sincronizarBDlocal(Object c) {
        //0=no accion, 1=guardar, 2 =actualizar
        int resultado=0;

        Modalidad modalidad=(Modalidad)c;

        Modalidad modalidadBD=buscarPorId(modalidad.getId());

        //se comprueba que exita el registro
        if (modalidadBD.getId()==-1){

            //sino exite se registra
            if(registrar(modalidad))
                resultado=1;

        }else {
            //sino exite se actualiza
            if(actualizar(modalidad))
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
