package net.profeinformatica.eprofe.modeloDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.profeinformatica.eprofe.dataBase.eProfContract;
import net.profeinformatica.eprofe.modelo.Docente;

import java.util.List;

public class DocenteDao extends ModeloDaoBasic {

    public DocenteDao(){

    }
    @Override
    public boolean eliminar(Object c) {
        return false;
    }

    @Override
    public boolean registrar(Object c) {
        Docente myDocente=(Docente)c;

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.DocentesTable.COLUMN_NAME_ID, myDocente.getId());
        values.put(eProfContract.DocentesTable.COLUMN_NAME_NOMBRE, myDocente.getNombre());
        values.put(eProfContract.DocentesTable.COLUMN_NAME_APELLIDO, myDocente.getApellido());
        values.put(eProfContract.DocentesTable.COLUMN_NAME_USERSACE, myDocente.getUserSace());
        values.put(eProfContract.DocentesTable.COLUMN_NAME_DIRECCION, myDocente.getDireccion());
        values.put(eProfContract.DocentesTable.COLUMN_NAME_TELEFONO,myDocente.getTelefono());
        values.put(eProfContract.DocentesTable.COLUMN_NAME_EMAIL,myDocente.getEmail());
        values.put(eProfContract.DocentesTable.COLUMN_NAME_GENERO, myDocente.getGenero());
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(eProfContract.DocentesTable.TABLE_NAME, null, values);

        if(newRowId!=-1)
            return true;
        else
            return false;
    }

    @Override
    public boolean actualizar(Object c) {
        Docente myDocente=(Docente)c;

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.DocentesTable.COLUMN_NAME_ID, myDocente.getId());
        values.put(eProfContract.DocentesTable.COLUMN_NAME_NOMBRE, myDocente.getNombre());
        values.put(eProfContract.DocentesTable.COLUMN_NAME_APELLIDO, myDocente.getApellido());
        values.put(eProfContract.DocentesTable.COLUMN_NAME_USERSACE, myDocente.getUserSace());
        values.put(eProfContract.DocentesTable.COLUMN_NAME_DIRECCION, myDocente.getDireccion());
        values.put(eProfContract.DocentesTable.COLUMN_NAME_TELEFONO,myDocente.getTelefono());
        values.put(eProfContract.DocentesTable.COLUMN_NAME_EMAIL,myDocente.getEmail());
        values.put(eProfContract.DocentesTable.COLUMN_NAME_GENERO, myDocente.getGenero());

        String selection = eProfContract.DocentesTable.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+myDocente.getId() };
        int count = db.update(
                eProfContract.DocentesTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(count>0)
            return true;
        else
            return false;
    }

    @Override
    public List<Docente> todos(int limInf, int cantidad) {
        return null;
    }

    @Override
    public List<Docente> todos() {
        return null;
    }

    @Override
    public Docente buscarPorId(int id) {

        Docente myDoc=new Docente();
        myDoc.setId(-1);

        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.DocentesTable.COLUMN_NAME_ID,
                eProfContract.DocentesTable.COLUMN_NAME_NOMBRE,
                eProfContract.DocentesTable.COLUMN_NAME_APELLIDO,
                eProfContract.DocentesTable.COLUMN_NAME_USERSACE,
                eProfContract.DocentesTable.COLUMN_NAME_TELEFONO,
                eProfContract.DocentesTable.COLUMN_NAME_DIRECCION,
                eProfContract.DocentesTable.COLUMN_NAME_GENERO,
                eProfContract.DocentesTable.COLUMN_NAME_EMAIL
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.DocentesTable.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+id };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.DocentesTable.COLUMN_NAME_ID + " DESC";

        Cursor cursor = db.query(
                eProfContract.DocentesTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {
            myDoc.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DocentesTable.COLUMN_NAME_ID)));
            myDoc.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.DocentesTable.COLUMN_NAME_NOMBRE)));
            myDoc.setApellido(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.DocentesTable.COLUMN_NAME_APELLIDO)));
            myDoc.setUserSace(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.DocentesTable.COLUMN_NAME_USERSACE)));
            myDoc.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.DocentesTable.COLUMN_NAME_EMAIL)));
            myDoc.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.DocentesTable.COLUMN_NAME_TELEFONO)));
            myDoc.setDireccion(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.DocentesTable.COLUMN_NAME_DIRECCION)));
            myDoc.setGenero(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.DocentesTable.COLUMN_NAME_GENERO)));

        }
        cursor.close();
        return myDoc;
    }

    @Override
    public List<Docente> buscarPorDescripcion(String busqueda) {
        return null;
    }

    @Override
    public int sincronizarBDlocal(Object c) {
        return 0;
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
