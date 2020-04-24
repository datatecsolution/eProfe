package net.profeinformatica.eprofe.modeloDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import net.profeinformatica.eprofe.dataBase.eProfContract;
import net.profeinformatica.eprofe.modelo.Alumno;
import net.profeinformatica.eprofe.modelo.Matricula;
import net.profeinformatica.eprofe.modelo.Seccion;

import java.util.ArrayList;
import java.util.List;

public class MatriculaDao extends ModeloDaoBasic {



    @Override
    public boolean eliminar(Object c) {
        return false;
    }

    @Override
    public boolean registrar(Object c) {
        Matricula matricula=(Matricula) c;

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.MatriculasTable.COLUMN_NAME_ID,matricula.getId());
        values.put(eProfContract.MatriculasTable.COLUMN_NAME_ALUMNOID,matricula.getAlumnoId());
        values.put(eProfContract.MatriculasTable.COLUMN_NAME_SECCIONID,matricula.getSeccionId());
        values.put(eProfContract.MatriculasTable.COLUMN_NAME_YEAR,matricula.getYear());
        values.put(eProfContract.MatriculasTable.COLUMN_NAME_CREATEDAT,matricula.getCreatedAt());
        values.put(eProfContract.MatriculasTable.COLUMN_NAME_UPDATEDAT,matricula.getUpdatedAt());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(eProfContract.MatriculasTable.TABLE_NAME, null, values);

        if(newRowId!=-1)
            return true;
        else
            return false;
    }

    @Override
    public boolean actualizar(Object c) {
        Matricula matricula=(Matricula) c;
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.MatriculasTable.COLUMN_NAME_ID,matricula.getId());
        values.put(eProfContract.MatriculasTable.COLUMN_NAME_ALUMNOID,matricula.getAlumnoId());
        values.put(eProfContract.MatriculasTable.COLUMN_NAME_SECCIONID,matricula.getSeccionId());
        values.put(eProfContract.MatriculasTable.COLUMN_NAME_YEAR,matricula.getYear());
        values.put(eProfContract.MatriculasTable.COLUMN_NAME_CREATEDAT,matricula.getCreatedAt());
        values.put(eProfContract.MatriculasTable.COLUMN_NAME_UPDATEDAT,matricula.getUpdatedAt());


        String selection = eProfContract.MatriculasTable.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+matricula.getId() };
        int count = db.update(
                eProfContract.MatriculasTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(count>0)
            return true;
        else
            return false;
    }

    @Override
    public List<Matricula> todos(int limInf, int cantidad) {
        return null;
    }

    @Override
    public List<Matricula> todos() {
        return null;
    }

    @Override
    public Matricula buscarPorId(int id) {

        Matricula matricula=new Matricula();
        matricula.setId(-1);



        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //db.execSQL(eProfContract.getSQL_CREATE_MatriculasTable());


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.MatriculasTable.COLUMN_NAME_ID,
                eProfContract.MatriculasTable.COLUMN_NAME_ALUMNOID,
                eProfContract.MatriculasTable.COLUMN_NAME_SECCIONID,
                eProfContract.MatriculasTable.COLUMN_NAME_YEAR,
                eProfContract.MatriculasTable.COLUMN_NAME_CREATEDAT,
                eProfContract.MatriculasTable.COLUMN_NAME_UPDATEDAT
        };




        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.MatriculasTable.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+id };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.MatriculasTable.COLUMN_NAME_ID + " DESC";

        Cursor cursor = db.query(
                eProfContract.MatriculasTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {

            matricula.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.MatriculasTable.COLUMN_NAME_ID)));
            matricula.setAlumnoId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.MatriculasTable.COLUMN_NAME_ALUMNOID)));
            matricula.setSeccionId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.MatriculasTable.COLUMN_NAME_SECCIONID)));
            matricula.setYear(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.MatriculasTable.COLUMN_NAME_YEAR)));
            matricula.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.MatriculasTable.COLUMN_NAME_CREATEDAT)));
            matricula.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.MatriculasTable.COLUMN_NAME_UPDATEDAT)));




        }
        cursor.close();


        return matricula;
    }

    @Override
    public List<Matricula> buscarPorDescripcion(String busqueda) {
        return null;
    }

    @Override
    public int sincronizarBDlocal(Object c) {

        //0=no accion, 1=guardar, 2 =actualizar
        int resultado=0;

        Matricula matricula=(Matricula)c;

        Matricula matriculaOnBD=buscarPorId(matricula.getId());

        //System.out.println(new String("Matricula ID ========>>>>>" +matricula.getId()));

        if(matriculaOnBD.getId()==-1){

            if(registrar(matricula)){
                resultado=1;
            }
        }else{
            if(actualizar(matricula)){
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
        return 0;
    }

    public List<Matricula> buscarPorSeccion(Seccion seccion) {
        boolean existe=false;

        List<Matricula> matriculas=new ArrayList<Matricula>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        AlumnoDao alumnoDao=new AlumnoDao();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.MatriculasTable.COLUMN_NAME_ID,
                eProfContract.MatriculasTable.COLUMN_NAME_ALUMNOID,
                eProfContract.MatriculasTable.COLUMN_NAME_SECCIONID,
                eProfContract.MatriculasTable.COLUMN_NAME_YEAR,
                eProfContract.MatriculasTable.COLUMN_NAME_CREATEDAT,
                eProfContract.MatriculasTable.COLUMN_NAME_UPDATEDAT
        };




        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.MatriculasTable.COLUMN_NAME_SECCIONID + " = ?";
        String[] selectionArgs = { ""+seccion.getId() };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.MatriculasTable.COLUMN_NAME_ID + " ASC";

        Cursor cursor = db.query(
                eProfContract.MatriculasTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {

            Matricula matricula=new Matricula();

            matricula.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.MatriculasTable.COLUMN_NAME_ID)));
            matricula.setAlumnoId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.MatriculasTable.COLUMN_NAME_ALUMNOID)));
            matricula.setSeccionId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.MatriculasTable.COLUMN_NAME_SECCIONID)));
            matricula.setYear(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.MatriculasTable.COLUMN_NAME_YEAR)));
            matricula.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.MatriculasTable.COLUMN_NAME_CREATEDAT)));
            matricula.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.MatriculasTable.COLUMN_NAME_UPDATEDAT)));

           Alumno alumno=alumnoDao.buscarPorId(matricula.getAlumnoId());

           if(alumno.getId()!=-1)
                matricula.setAlumno(alumno);

            matriculas.add(matricula);

            existe=true;

        }
        cursor.close();

        if (existe) {
            return matriculas;
        }
        else return null;
    }


}
