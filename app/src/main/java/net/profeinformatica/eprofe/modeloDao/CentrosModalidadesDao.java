package net.profeinformatica.eprofe.modeloDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.profeinformatica.eprofe.dataBase.eProfContract;
import net.profeinformatica.eprofe.modelo.Seccion;

import java.util.List;

public class CentrosModalidadesDao extends ModeloDaoBasic {
    private Seccion seccionLocal=null;
    public CentrosModalidadesDao() {
    }

    @Override
    public boolean eliminar(Object c) {
        return false;
    }

    @Override
    public boolean registrar(Object c) {
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.CentrosModalidadesTable.COLUMN_NAME_ID, seccionLocal.getId());
        values.put(eProfContract.CentrosModalidadesTable.COLUMN_NAME_CENTROID,seccionLocal.getCentroId());
        values.put(eProfContract.CentrosModalidadesTable.COLUMN_NAME_MODALIDADID,seccionLocal.getModalidadId());
        values.put(eProfContract.CentrosModalidadesTable.COLUMN_NAME_CREATEDAT, seccionLocal.getCreatedAt());
        values.put(eProfContract.CentrosModalidadesTable.COLUMN_NAME_UPDATEDAT, seccionLocal.getUpdatedAt());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(eProfContract.CentrosModalidadesTable.TABLE_NAME, null, values);

        if(newRowId!=-1)
            return true;
        else
            return false;
    }

    @Override
    public boolean actualizar(Object c) {
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
    public Boolean buscarPorId(int id) {

        Boolean existe=false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.CentrosModalidadesTable.COLUMN_NAME_ID,
                eProfContract.CentrosModalidadesTable.COLUMN_NAME_CENTROID,
                eProfContract.CentrosModalidadesTable.COLUMN_NAME_MODALIDADID,
                eProfContract.CentrosModalidadesTable.COLUMN_NAME_CREATEDAT,
                eProfContract.CentrosModalidadesTable.COLUMN_NAME_UPDATEDAT
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.CentrosModalidadesTable.COLUMN_NAME_MODALIDADID + " = ? and "+ eProfContract.CentrosModalidadesTable.COLUMN_NAME_CENTROID+ " = ?";
        String[] selectionArgs = { ""+seccionLocal.getModalidadId(),""+seccionLocal.getCentroId()};


        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.CentrosModalidadesTable.COLUMN_NAME_ID + " DESC";

        Cursor cursor = db.query(
                eProfContract.CentrosModalidadesTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {
            existe=true;
        }
        cursor.close();
        return existe;
    }

    @Override
    public List buscarPorDescripcion(String busqueda) {
        return null;
    }

    @Override
    public int sincronizarBDlocal(Object c) {
        int resul=0;

        Seccion seccion=(Seccion)c;
        seccionLocal=seccion;

        Boolean resultado=(Boolean)buscarPorId(1);

        if (resultado==false){
            registrar(seccion);
            resul=1;
        }

        return resul;
    }

    @Override
    public boolean sincronizarServidor(Object c) {
        return false;
    }

    @Override
    public int getGeneratedKeys() {
        return 0;
    }

    public void setDatosPrueba(){


        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        String sql="INSERT INTO centros_modalidades (id, centro_id, modalidad_id, created_at, updated_at) VALUES (10, 2, 1, '2020-05-06 00:28:34', '2020-05-06 00:28:34')";

        db.execSQL(sql);




    }

}
