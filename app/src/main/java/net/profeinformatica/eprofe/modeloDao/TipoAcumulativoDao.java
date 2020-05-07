package net.profeinformatica.eprofe.modeloDao;



import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.profeinformatica.eprofe.dataBase.eProfContract;
import net.profeinformatica.eprofe.modelo.TipoAcumulativo;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiService;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TipoAcumulativoDao extends ModeloDaoBasic{
    List<TipoAcumulativo> tipoAcumulativos=null;


    public List<TipoAcumulativo> getAcumulativos(){
        ApiService mAPIService = ApiUtils.getApiService();


        mAPIService.getTipoAcumulativos().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TipoAcumulativo>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<TipoAcumulativo> tipoAcum) {

                        tipoAcumulativos=tipoAcum;

                    }
                });
        return tipoAcumulativos;
    }

    @Override
    public boolean eliminar(Object c) {
        return false;
    }

    @Override
    public boolean registrar(Object c) {
        TipoAcumulativo tipoAcumulativo=(TipoAcumulativo) c;

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.TipoAcumulativos.COLUMN_NAME_ID,tipoAcumulativo.getId());
        values.put(eProfContract.TipoAcumulativos.COLUMN_NAME_DESCRIPCION,tipoAcumulativo.getDescripcion());
        values.put(eProfContract.TipoAcumulativos.COLUMN_NAME_CREATEDAT,tipoAcumulativo.getCreatedAt());
        values.put(eProfContract.TipoAcumulativos.COLUMN_NAME_UPDATEDAT,tipoAcumulativo.getUpdatedAt());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(eProfContract.TipoAcumulativos.TABLE_NAME, null, values);

        if(newRowId!=-1)
            return true;
        else
            return false;
    }

    @Override
    public boolean actualizar(Object c) {
        TipoAcumulativo tipoAcumulativo=(TipoAcumulativo) c;
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.TipoAcumulativos.COLUMN_NAME_ID,tipoAcumulativo.getId());
        values.put(eProfContract.TipoAcumulativos.COLUMN_NAME_DESCRIPCION,tipoAcumulativo.getDescripcion());
        values.put(eProfContract.TipoAcumulativos.COLUMN_NAME_CREATEDAT,tipoAcumulativo.getCreatedAt());
        values.put(eProfContract.TipoAcumulativos.COLUMN_NAME_UPDATEDAT,tipoAcumulativo.getUpdatedAt());


        String selection = eProfContract.TipoAcumulativos.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+tipoAcumulativo.getId() };
        int count = db.update(
                eProfContract.TipoAcumulativos.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(count>0)
            return true;
        else
            return false;
    }

    @Override
    public List<TipoAcumulativo> todos(int limInf, int cantidad) {
        return null;
    }

    @Override
    public List<TipoAcumulativo> todos() {
        List<TipoAcumulativo> tipoAcumulativos=new ArrayList<TipoAcumulativo>();

        boolean existe=false;


        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.TipoAcumulativos.COLUMN_NAME_ID,
                eProfContract.TipoAcumulativos.COLUMN_NAME_DESCRIPCION,
                eProfContract.TipoAcumulativos.COLUMN_NAME_CREATEDAT,
                eProfContract.TipoAcumulativos.COLUMN_NAME_UPDATEDAT
        };




        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.TipoAcumulativos.COLUMN_NAME_ID + " ASC";

        Cursor cursor = db.query(
                eProfContract.TipoAcumulativos.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {
            TipoAcumulativo tipoAcumulativo=new TipoAcumulativo();

            tipoAcumulativo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.TipoAcumulativos.COLUMN_NAME_ID)));
            tipoAcumulativo.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.TipoAcumulativos.COLUMN_NAME_DESCRIPCION)));
            tipoAcumulativo.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.TipoAcumulativos.COLUMN_NAME_CREATEDAT)));
            tipoAcumulativo.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.TipoAcumulativos.COLUMN_NAME_UPDATEDAT)));

            tipoAcumulativos.add(tipoAcumulativo);
            existe=true;




        }
        cursor.close();

        if(existe)
            return tipoAcumulativos;
        else
            return null;
    }

    @Override
    public TipoAcumulativo buscarPorId(int id) {
        TipoAcumulativo tipoAcumulativo=new TipoAcumulativo();
        tipoAcumulativo.setId(-1);

        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.TipoAcumulativos.COLUMN_NAME_ID,
                eProfContract.TipoAcumulativos.COLUMN_NAME_DESCRIPCION,
                eProfContract.TipoAcumulativos.COLUMN_NAME_CREATEDAT,
                eProfContract.TipoAcumulativos.COLUMN_NAME_UPDATEDAT
        };





        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.TipoAcumulativos.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+id };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.TipoAcumulativos.COLUMN_NAME_ID + " DESC";

        Cursor cursor = db.query(
                eProfContract.TipoAcumulativos.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {

            tipoAcumulativo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.TipoAcumulativos.COLUMN_NAME_ID)));
            tipoAcumulativo.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.TipoAcumulativos.COLUMN_NAME_DESCRIPCION)));
            tipoAcumulativo.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.TipoAcumulativos.COLUMN_NAME_CREATEDAT)));
            tipoAcumulativo.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.TipoAcumulativos.COLUMN_NAME_UPDATEDAT)));




        }
        cursor.close();


        return tipoAcumulativo;
    }

    @Override
    public List<TipoAcumulativo> buscarPorDescripcion(String busqueda) {
        return null;
    }

    @Override
    public int sincronizarBDlocal(Object c) {

        //0=no accion, 1=guardar, 2 =actualizar
        int resultado=0;

        TipoAcumulativo tipoAcumulativo=(TipoAcumulativo)c;

        TipoAcumulativo tipoAcumulativoBd=buscarPorId(tipoAcumulativo.getId());


        if (tipoAcumulativoBd.getId()==-1){
            if(registrar(tipoAcumulativo)){
                resultado=1;
            }
        }else {
            if(actualizar(tipoAcumulativo)){
                resultado=2;
            }
        }

        return resultado;
    }

    @Override
    public boolean sincronizarServidor(Object c) {


        ApiService mAPIService = ApiUtils.getApiService();

        mAPIService.getTipoAcumulativos().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TipoAcumulativo>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                        System.out.println("ERROR SINCRONIZAR TIPO ACUMULATIVO ====================>>>>>>"+e.getMessage());

                    }

                    @Override
                    public void onNext(List<TipoAcumulativo> tipoAcum) {


                        for(int x=0;x<tipoAcum.size();x++){
                            sincronizarBDlocal(tipoAcum.get(x));
                        }

                    }
                });



        return false;
    }

    @Override
    public int getGeneratedKeys() {
        return 0;
    }

    public void setDatosPrueba(){


        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String sql="INSERT INTO " +
                            " tipoacumulativos (id, descripcion, created_at, updated_at) " +
                        " VALUES " +
                            " (1, 'Tareas Clase', '2018-07-23 12:34:41', '2018-07-23 12:34:44'), " +
                            " (2, 'Tareas Casa', '2018-07-23 12:34:58', '2018-07-23 12:35:02'), " +
                            " (3, 'Examen', '2018-07-23 12:35:12', '2018-07-23 12:35:14')";
        db.execSQL(sql);




    }
}
