package net.profeinformatica.eprofe.modeloDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.profeinformatica.eprofe.dataBase.eProfContract;
import net.profeinformatica.eprofe.modelo.Periodo;
import java.util.List;

public class PeriodoDao extends ModeloDaoBasic {
    protected PeriodoDao() {
    }

    @Override
    public boolean eliminar(Object c) {
        return false;
    }

    @Override
    public boolean registrar(Object c) {

        Periodo periodo=(Periodo) c;

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.PeriodosTable.COLUMN_NAME_ID, periodo.getId());
        values.put(eProfContract.PeriodosTable.COLUMN_NAME_FECHAINICIA, periodo.getFechaInicio().toString());
        values.put(eProfContract.PeriodosTable.COLUMN_NAME_FECHAFINAL, periodo.getFechaFinal().toString());
        values.put(eProfContract.PeriodosTable.COLUMN_NAME_ESTADO, periodo.getEstado());
        values.put(eProfContract.PeriodosTable.COLUMN_NAME_OBSERVACIONES, periodo.getObservaciones());
        values.put(eProfContract.PeriodosTable.COLUMN_NAME_CREATEDAT, periodo.getCreatedAt());
        values.put(eProfContract.PeriodosTable.COLUMN_NAME_UPDATEDAT,periodo.getUpdatedAt());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(eProfContract.PeriodosTable.TABLE_NAME, null, values);


        if(newRowId!=-1)
            return true;
        else
            return false;



    }

    @Override
    public boolean actualizar(Object c) {

        Periodo periodo=(Periodo) c;
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.PeriodosTable.COLUMN_NAME_ID, periodo.getId());
        values.put(eProfContract.PeriodosTable.COLUMN_NAME_FECHAINICIA, periodo.getFechaInicio().toString());
        values.put(eProfContract.PeriodosTable.COLUMN_NAME_FECHAFINAL, periodo.getFechaFinal().toString());
        values.put(eProfContract.PeriodosTable.COLUMN_NAME_ESTADO, periodo.getEstado());
        values.put(eProfContract.PeriodosTable.COLUMN_NAME_OBSERVACIONES, periodo.getObservaciones());
        values.put(eProfContract.PeriodosTable.COLUMN_NAME_CREATEDAT, periodo.getCreatedAt());
        values.put(eProfContract.PeriodosTable.COLUMN_NAME_UPDATEDAT,periodo.getUpdatedAt());

        String selection = eProfContract.PeriodosTable.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+periodo.getId() };
        int count = db.update(
                eProfContract.PeriodosTable.TABLE_NAME,
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
    public Periodo buscarPorId(int id) {

        Periodo periodo=new Periodo();
        periodo.setId(-1);

        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.PeriodosTable.COLUMN_NAME_ID,
                eProfContract.PeriodosTable.COLUMN_NAME_FECHAINICIA,
                eProfContract.PeriodosTable.COLUMN_NAME_FECHAFINAL ,
                eProfContract.PeriodosTable.COLUMN_NAME_ESTADO,
                eProfContract.PeriodosTable.COLUMN_NAME_OBSERVACIONES,
                eProfContract.PeriodosTable.COLUMN_NAME_CREATEDAT,
                eProfContract.PeriodosTable.COLUMN_NAME_UPDATEDAT
        };




        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.PeriodosTable.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+id };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.PeriodosTable.COLUMN_NAME_ID + " DESC";

        Cursor cursor = db.query(
                eProfContract.PeriodosTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {

            periodo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.PeriodosTable.COLUMN_NAME_ID)));
            periodo.setFechaInicio(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.PeriodosTable.COLUMN_NAME_FECHAINICIA)));
            periodo.setFechaFinal(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.PeriodosTable.COLUMN_NAME_FECHAFINAL)));
            periodo.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.PeriodosTable.COLUMN_NAME_ESTADO)));
            periodo.setObservaciones(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.PeriodosTable.COLUMN_NAME_OBSERVACIONES)));
            periodo.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.PeriodosTable.COLUMN_NAME_CREATEDAT)));
            periodo.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.PeriodosTable.COLUMN_NAME_UPDATEDAT)));

        }
        cursor.close();
        return periodo;
    }

    @Override
    public List buscarPorDescripcion(String busqueda) {
        return null;
    }

    @Override
    public int sincronizarBDlocal(Object c) {
        //0=no accion, 1=guardar, 2 =actualizar
        int resultado=0;

        Periodo periodo=(Periodo) c;

        Periodo periodoOnDB=buscarPorId(periodo.getId());

        //se comprueba que exita el registro
        if (periodoOnDB.getId()==-1){

            //sino exite se registra
            if(registrar(periodo))
                resultado=1;

        }else {
            //sino exite se actualiza
            if(actualizar(periodo))
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
