package net.profeinformatica.eprofe.modeloDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.profeinformatica.eprofe.dataBase.eProfContract;
import net.profeinformatica.eprofe.modelo.Asignatura;
import net.profeinformatica.eprofe.modelo.Seccion;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiService;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AsignaturaDao extends ModeloDaoBasic {
    private Seccion seccionTemp=null;

    public AsignaturaDao() {

    }

    @Override
    public boolean eliminar(Object c) {
        return false;
    }

    @Override
    public boolean registrar(Object c) {
        Asignatura asignatura=(Asignatura) c;
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.AsignaturasTable.COLUMN_NAME_ID, asignatura.getId());
        values.put(eProfContract.AsignaturasTable.COLUMN_NAME_ALIAS, asignatura.getAlias());
        values.put(eProfContract.AsignaturasTable.COLUMN_NAME_NOMBRE, asignatura.getNombre());
        values.put(eProfContract.AsignaturasTable.COLUMN_NAME_TIPO, asignatura.getTipo());
        values.put(eProfContract.AsignaturasTable.COLUMN_NAME_CREATEDAT, asignatura.getCreatedAt());
        values.put(eProfContract.AsignaturasTable.COLUMN_NAME_UPDATEDAT, asignatura.getUpdatedAt());



        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(eProfContract.AsignaturasTable.TABLE_NAME, null, values);

        if(newRowId!=-1)
            return true;
        else
            return false;
    }

    @Override
    public boolean actualizar(Object c) {
        Asignatura asignatura=(Asignatura) c;
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.AsignaturasTable.COLUMN_NAME_ID, asignatura.getId());
        values.put(eProfContract.AsignaturasTable.COLUMN_NAME_ALIAS, asignatura.getAlias());
        values.put(eProfContract.AsignaturasTable.COLUMN_NAME_NOMBRE, asignatura.getNombre());
        values.put(eProfContract.AsignaturasTable.COLUMN_NAME_TIPO, asignatura.getTipo());
        values.put(eProfContract.AsignaturasTable.COLUMN_NAME_CREATEDAT, asignatura.getCreatedAt());
        values.put(eProfContract.AsignaturasTable.COLUMN_NAME_UPDATEDAT, asignatura.getUpdatedAt());

        String selection = eProfContract.AsignaturasTable.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+asignatura.getId() };
        int count = db.update(
                eProfContract.AsignaturasTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(count>0)
            return true;
        else
            return false;
    }

    @Override
    public List<Asignatura> todos(int limInf, int cantidad) {
        return null;
    }

    @Override
    public List<Asignatura> todos() {
        return null;
    }

    @Override
    public Asignatura buscarPorId(int id) {
        Asignatura asignatura=new Asignatura();
        asignatura.setId(-1);

        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.AsignaturasTable.COLUMN_NAME_ID,
                eProfContract.AsignaturasTable.COLUMN_NAME_ALIAS,
                eProfContract.AsignaturasTable.COLUMN_NAME_NOMBRE,
                eProfContract.AsignaturasTable.COLUMN_NAME_TIPO,
                eProfContract.AsignaturasTable.COLUMN_NAME_CREATEDAT,
                eProfContract.AsignaturasTable.COLUMN_NAME_UPDATEDAT
        };


        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.AsignaturasTable.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+id };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.AsignaturasTable.COLUMN_NAME_ID + " DESC";

        Cursor cursor = db.query(
                eProfContract.AsignaturasTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {

            asignatura.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AsignaturasTable.COLUMN_NAME_ID)));
            asignatura.setAlias(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AsignaturasTable.COLUMN_NAME_ALIAS)));
            asignatura.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AsignaturasTable.COLUMN_NAME_NOMBRE)));
            asignatura.setTipo(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AsignaturasTable.COLUMN_NAME_TIPO)));
            asignatura.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AsignaturasTable.COLUMN_NAME_CREATEDAT)));
            asignatura.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AsignaturasTable.COLUMN_NAME_UPDATEDAT)));

        }
        cursor.close();
        return asignatura;
    }

    @Override
    public List buscarPorDescripcion(String busqueda) {
        return null;
    }

    @Override
    public int sincronizarBDlocal(Object c) {

        //0=no accion, 1=guardar, 2 =actualizar
        int resultado=0;

        Asignatura asignatura=(Asignatura)c;


        Asignatura asignaturaOnBD=buscarPorId(asignatura.getId());

        if(asignaturaOnBD.getId()==-1){

            if(registrar(asignatura)) {
                joinAsignaturaToSeccion(asignatura);
                resultado=1;
            }


        }else{
            actualizar(asignatura);
            resultado=2;
        }


        return resultado;
    }
    @Override
    public boolean sincronizarServidor(Object c){



        Seccion seccion=(Seccion)c;

        seccionTemp=seccion;


        ApiService mAPIService = ApiUtils.getApiService();



        mAPIService.getAsignaturasDocente(ModeloDaoBasic.getDocente().getId(),seccion.getId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Asignatura>>() {
                    @Override
                    public void onCompleted() {

                        System.out.println(new String("Exitooooo en ASIGNATURA:===============>"));


                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(new String("ERROR:===============>"+e.getMessage()));
                    }

                    @Override
                    public void onNext(List<Asignatura> asigs) {

                        if (asigs!=null){
                            for (int i = 0; i < asigs.size(); i++) {
                                sincronizarBDlocal(asigs.get(i));

                            }

                        }

                    }
                });





        return false;
    }

    @Override
    public int getGeneratedKeys() {
        return 0;
    }

    public boolean joinAsignaturaToSeccion( Asignatura asignatura){
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.AsignaturasSeccionesTable.COLUMN_NAME_ASIGNATURAID, asignatura.getId());
        values.put(eProfContract.AsignaturasSeccionesTable.COLUMN_NAME_SECCIONID,seccionTemp.getId());
        values.put(eProfContract.AsignaturasSeccionesTable.COLUMN_NAME_DOCENTEID,ModeloDaoBasic.getDocente().getId());



        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(eProfContract.AsignaturasSeccionesTable.TABLE_NAME, null, values);

        if(newRowId!=-1)
            return true;
        else
            return false;
    }


    public List<Asignatura> buscarPorSeccionDocente(Integer idSeccion){

        boolean existe=false;
        List<Asignatura> asignaturas=new ArrayList<Asignatura>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        SeccionDao seccionDao=new SeccionDao();



        String sql=" SELECT * FROM "
                +eProfContract.AsignaturasTable.TABLE_NAME+ " join "
                +eProfContract.AsignaturasSeccionesTable.TABLE_NAME+
                " ON " +eProfContract.AsignaturasTable.TABLE_NAME+"."+eProfContract.AsignaturasTable.COLUMN_NAME_ID+" = "+eProfContract.AsignaturasSeccionesTable.TABLE_NAME+"." +eProfContract.AsignaturasSeccionesTable.COLUMN_NAME_ASIGNATURAID
                +" WHERE "+eProfContract.AsignaturasSeccionesTable.TABLE_NAME+"."+eProfContract.AsignaturasSeccionesTable.COLUMN_NAME_DOCENTEID+"=? and " +
                eProfContract.AsignaturasSeccionesTable.TABLE_NAME+"."+eProfContract.AsignaturasSeccionesTable.COLUMN_NAME_SECCIONID+"=?";






        String[] selectionArgs = { ""+ModeloDaoBasic.getDocente().getId(),""+ idSeccion};

        Cursor cursor = db.rawQuery(sql,selectionArgs);

        //System.out.println(new String("Exitooooo en ASIGNATURA:===============>"+sql));


        while(cursor.moveToNext()) {

            Asignatura asignatura=new Asignatura();
            asignatura.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AsignaturasTable.COLUMN_NAME_ID)));
            asignatura.setAlias(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AsignaturasTable.COLUMN_NAME_ALIAS)));
            asignatura.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AsignaturasTable.COLUMN_NAME_NOMBRE)));
            asignatura.setTipo(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AsignaturasTable.COLUMN_NAME_TIPO)));
            asignatura.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AsignaturasTable.COLUMN_NAME_CREATEDAT)));
            asignatura.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AsignaturasTable.COLUMN_NAME_UPDATEDAT)));


            Seccion seccion=seccionDao.buscarPorId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AsignaturasSeccionesTable.COLUMN_NAME_SECCIONID)));

            if(seccion.getId()!=-1)
                asignatura.getSeccions().add(seccion);


            asignaturas.add(asignatura);

            existe=true;

        }
        cursor.close();

        if(existe)
            return asignaturas;
        else
            return null;

    }

    public void setDatosPrueba(){


        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        String sql="INSERT INTO " +
                        " asignaturas (id, alias, nombre, tipo, created_at, updated_at) " +
                    " VALUES " +
                        " (4, 'NA', 'PROGRAMACIÓN III', '1', null, null), " +
                        " (5, 'NA', 'REDES INFORMÁTICAS I', '1', null, null), " +
                        " (7, 'NA', 'LABORATORIO DE INFORMÁTICA I', '1', null, null), " +
                        " (9, 'NA', 'DISEÑO WEB I', '1', null, null), " +
                        " (10, 'NA', 'LABORATORIO DE INFORMÁTICA III', '1', null, null), " +
                        " (12, 'NA', 'MANTENIMIENTO Y REPARACIÓN I', '1', null, null), " +
                        " (16, 'NA', 'PROGRAMACIÓN I', '1', null, null) ";

        db.execSQL(sql);

        String sql2="INSERT INTO " +
                            " asignaturas_secciones (asignatura_id, seccion_id, docente_id) " +
                    " VALUES " +
                        " (7, 10, 1), " +
                        " (16, 10, 1), " +
                        " (9, 11, 1), " +
                        " (10, 11, 1), " +
                        " (12, 11, 1), " +
                        " (4, 11, 1), " +
                        " (5, 11, 1)";

        db.execSQL(sql2);



    }

}
