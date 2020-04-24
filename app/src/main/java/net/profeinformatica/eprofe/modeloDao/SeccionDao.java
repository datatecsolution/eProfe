package net.profeinformatica.eprofe.modeloDao;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import net.profeinformatica.eprofe.dataBase.eProfContract;
import net.profeinformatica.eprofe.modelo.Docente;
import net.profeinformatica.eprofe.modelo.Seccion;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiService;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SeccionDao extends ModeloDaoBasic {

    private ModalidadDao modalidadDao=new ModalidadDao();
    private CentroDao centroDao=new CentroDao();
    private PeriodoDao periodoDao=new PeriodoDao();
    private AsignaturaDao asignaturaDao=new AsignaturaDao();
    private AlumnoDao alumnoDao=new AlumnoDao();
    private TipoAcumulativoDao tipoAcumulativoDao=new TipoAcumulativoDao();
    private CentroDocenteDao centroDocenteDao=new CentroDocenteDao();
    private CentrosModalidadesDao centrosModalidadesDao=new CentrosModalidadesDao();
    private EncabezadoAsistenciaDao encabezadoAsistenciaDao=new EncabezadoAsistenciaDao();
    private AcumulativosDao acumulativoDao=new AcumulativosDao();

    @Override
    public boolean eliminar(Object c) {
        return false;
    }

    @Override
    public boolean registrar(Object c) {

        Seccion seccion=(Seccion)c;
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.SeccionsTable.COLUMN_NAME_ID, seccion.getId());
        values.put(eProfContract.SeccionsTable.COLUMN_NAME_MODALIDADID, seccion.getModalidadId());
        values.put(eProfContract.SeccionsTable.COLUMN_NAME_CURSO, seccion.getCurso());
        values.put(eProfContract.SeccionsTable.COLUMN_NAME_SECCION, seccion.getSeccion());
        values.put(eProfContract.SeccionsTable.COLUMN_NAME_JORNADA, seccion.getJornada());
        values.put(eProfContract.SeccionsTable.COLUMN_NAME_CENTROID,seccion.getCentroId());
        values.put(eProfContract.SeccionsTable.COLUMN_NAME_PERIODOID,seccion.getPeriodoId());
        values.put(eProfContract.SeccionsTable.COLUMN_NAME_SINCRONIZACIONSERVER,seccion.isSincronizarServer());
        values.put(eProfContract.SeccionsTable.COLUMN_NAME_CREATEDAT, seccion.getCreatedAt());
        values.put(eProfContract.SeccionsTable.COLUMN_NAME_UPDATEDAT, seccion.getUpdatedAt());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(eProfContract.SeccionsTable.TABLE_NAME, null, values);

        if(newRowId!=-1)
            return true;
        else
            return false;
    }

    @Override
    public boolean actualizar(Object c) {

        Seccion seccion=(Seccion)c;
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.SeccionsTable.COLUMN_NAME_ID, seccion.getId());
        values.put(eProfContract.SeccionsTable.COLUMN_NAME_MODALIDADID, seccion.getModalidadId());
        values.put(eProfContract.SeccionsTable.COLUMN_NAME_CURSO, seccion.getCurso());
        values.put(eProfContract.SeccionsTable.COLUMN_NAME_SECCION, seccion.getSeccion());
        values.put(eProfContract.SeccionsTable.COLUMN_NAME_JORNADA, seccion.getJornada());
        values.put(eProfContract.SeccionsTable.COLUMN_NAME_CENTROID,seccion.getCentroId());
        values.put(eProfContract.SeccionsTable.COLUMN_NAME_PERIODOID,seccion.getPeriodoId());
        values.put(eProfContract.SeccionsTable.COLUMN_NAME_SINCRONIZACIONSERVER,seccion.isSincronizarServer());
        values.put(eProfContract.SeccionsTable.COLUMN_NAME_CREATEDAT, seccion.getCreatedAt());
        values.put(eProfContract.SeccionsTable.COLUMN_NAME_UPDATEDAT, seccion.getUpdatedAt());

        String selection = eProfContract.SeccionsTable.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+seccion.getId() };
        int count = db.update(
                eProfContract.SeccionsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(count>0)
            return true;
        else
            return false;
    }

    @Override
    public List<Seccion> todos(int limInf, int cantidad) {
        return null;
    }

    @Override
    public List<Seccion> todos() {
        return null;
    }

    @Override
    public Seccion buscarPorId(int id) {
        Seccion seccion=new Seccion();
        seccion.setId(-1);

        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.SeccionsTable.COLUMN_NAME_ID,
                eProfContract.SeccionsTable.COLUMN_NAME_MODALIDADID,
                eProfContract.SeccionsTable.COLUMN_NAME_CURSO,
                eProfContract.SeccionsTable.COLUMN_NAME_SECCION,
                eProfContract.SeccionsTable.COLUMN_NAME_JORNADA,
                eProfContract.SeccionsTable.COLUMN_NAME_CENTROID,
                eProfContract.SeccionsTable.COLUMN_NAME_PERIODOID,
                eProfContract.SeccionsTable.COLUMN_NAME_SINCRONIZACIONSERVER,
                eProfContract.SeccionsTable.COLUMN_NAME_CREATEDAT,
                eProfContract.SeccionsTable.COLUMN_NAME_UPDATEDAT
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.SeccionsTable.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+id };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.SeccionsTable.COLUMN_NAME_ID + " DESC";

        Cursor cursor = db.query(
                eProfContract.SeccionsTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {

            seccion.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.SeccionsTable.COLUMN_NAME_ID)));
            seccion.setModalidadId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.SeccionsTable.COLUMN_NAME_MODALIDADID)));
            seccion.setCurso(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.SeccionsTable.COLUMN_NAME_CURSO)));
            seccion.setSeccion(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.SeccionsTable.COLUMN_NAME_SECCION)));
            seccion.setJornada(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.SeccionsTable.COLUMN_NAME_JORNADA)));
            seccion.setCentroId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.SeccionsTable.COLUMN_NAME_CENTROID)));
            seccion.setPeriodoId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.SeccionsTable.COLUMN_NAME_PERIODOID)));
            seccion.setSincronizarServer(cursor.getColumnIndexOrThrow(eProfContract.SeccionsTable.COLUMN_NAME_SINCRONIZACIONSERVER) == 1 ? true : false);
            seccion.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.SeccionsTable.COLUMN_NAME_CREATEDAT)));
            seccion.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.SeccionsTable.COLUMN_NAME_UPDATEDAT)));

            seccion.setModalidad(modalidadDao.buscarPorId(seccion.getModalidadId()));
            seccion.setCentro(centroDao.buscarPorId(seccion.getCentroId()));
            seccion.setPeriodo(periodoDao.buscarPorId(seccion.getPeriodoId()));

        }
        cursor.close();
        return seccion;
    }

    @Override
    public List<Seccion> buscarPorDescripcion(String busqueda) {
        return null;
    }



    public List<Seccion> buscarPorDocente(Docente docente) {

        List<Seccion> seccions=new ArrayList<Seccion>();
        boolean existe=false;



        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.SeccionsTable.COLUMN_NAME_ID,
                eProfContract.SeccionsTable.COLUMN_NAME_MODALIDADID,
                eProfContract.SeccionsTable.COLUMN_NAME_CURSO,
                eProfContract.SeccionsTable.COLUMN_NAME_SECCION,
                eProfContract.SeccionsTable.COLUMN_NAME_JORNADA,
                eProfContract.SeccionsTable.COLUMN_NAME_CENTROID,
                eProfContract.SeccionsTable.COLUMN_NAME_PERIODOID,
                eProfContract.SeccionsTable.COLUMN_NAME_CREATEDAT,
                eProfContract.SeccionsTable.COLUMN_NAME_UPDATEDAT
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.SeccionsTable.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+docente.getId()};




        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.SeccionsTable.COLUMN_NAME_ID + " DESC";

        Cursor cursor = db.query(
                eProfContract.SeccionsTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {

            System.out.println(new String("SECCIONES OJO ==========================>>>>>"));

            Seccion seccion=new Seccion();
            seccion.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.SeccionsTable.COLUMN_NAME_ID)));
            seccion.setModalidadId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.SeccionsTable.COLUMN_NAME_MODALIDADID)));
            seccion.setCurso(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.SeccionsTable.COLUMN_NAME_CURSO)));
            seccion.setSeccion(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.SeccionsTable.COLUMN_NAME_SECCION)));
            seccion.setJornada(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.SeccionsTable.COLUMN_NAME_JORNADA)));
            seccion.setCentroId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.SeccionsTable.COLUMN_NAME_CENTROID)));
            seccion.setPeriodoId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.SeccionsTable.COLUMN_NAME_PERIODOID)));
            seccion.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.SeccionsTable.COLUMN_NAME_CREATEDAT)));
            seccion.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.SeccionsTable.COLUMN_NAME_UPDATEDAT)));

            seccion.setModalidad(modalidadDao.buscarPorId(seccion.getModalidadId()));
            seccion.setCentro(centroDao.buscarPorId(seccion.getCentroId()));
            seccion.setPeriodo(periodoDao.buscarPorId(seccion.getPeriodoId()));

            seccions.add(seccion);
            existe=true;

        }
        cursor.close();


        if (existe) {
            return seccions;
        }
        else return null;
    }
    @Override
    public boolean sincronizarServidor(Object c){



        Docente docente=(Docente)c;

        //System.out.println(new String("Docente ========>>>>>" +docente.toString()));

        ApiService mAPIService = ApiUtils.getApiService();

        mAPIService.getSecciones(docente.getId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Seccion>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(new String("Error en el sevidor remoto========>>>>>" + e.getLocalizedMessage()));

                    }

                    @Override
                    public void onNext(List<Seccion> seccions) {


                        if(seccions!=null) {



                           // Toast.makeText(null, "Actualizando la base de datos del telefono...", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < seccions.size(); i++) {

                                seccions.get(i).setSincronizarServer(true);

                                sincronizarBDlocal(seccions.get(i));


                                asignaturaDao.sincronizarServidor(seccions.get(i));
                                alumnoDao.sincronizarServidor(seccions.get(i));
                                encabezadoAsistenciaDao.sincronizarServidor(seccions.get(i));
                                acumulativoDao.sincronizarServidor(seccions.get(i));

                            }
                            tipoAcumulativoDao.sincronizarServidor(null);
                        }
                    }
                });




            return false;
    }

    @Override
    public int getGeneratedKeys() {
        return 0;
    }

    @Override
    public int sincronizarBDlocal(Object c) {
        //0=no accion, 1=guardar, 2 =actualizar
        int resultado=0;

        Seccion seccion=(Seccion)c;
        Seccion seccionBD=buscarPorId(seccion.getId());

        //se comprueba que la seccion exite
        if (seccionBD.getId()==-1){


            //se registra y se verifica que se registro bien
            if(registrar(seccion)) {

                //se verifica que la seccion trae una modalidad
                if(seccion.getModalidad()!=null) {
                    // se sincroniza la modalidad
                    modalidadDao.sincronizarBDlocal(seccion.getModalidad());
                }
                //se verifica que la seccion tenga un centro
                if (seccion.getCentro()!=null) {
                    //se sincroniza el centro
                    centroDao.sincronizarBDlocal(seccion.getCentro());
                   // se sincroniza la tabla centro-docente
                    centroDocenteDao.sincronizarBDlocal(seccion);
                   // se sincroniza la tabla centro modalidades
                    centrosModalidadesDao.sincronizarBDlocal(seccion);

                }

                //se verifica que la seccion tenga un periodo
                if(seccion.getPeriodo()!=null) {
                    periodoDao.sincronizarBDlocal(seccion.getPeriodo());
                }




                resultado=1;
            }

        }else {

            System.out.println(new String("Actualizando la base de datos del telefono..."+seccionBD.toString() ));

            if(seccion.getModalidad()!=null)
                // se sincroniza la modalidad
                modalidadDao.sincronizarBDlocal(seccion.getModalidad());

            if (seccion.getCentro()!=null) {
                centroDao.sincronizarBDlocal(seccion.getCentro());
               // joinCentroToDocente(seccion);
                centroDocenteDao.sincronizarBDlocal(seccion);
                //joinCentroToModalidad(seccion);
                centrosModalidadesDao.sincronizarBDlocal(seccion);
            }


            if(seccion.getPeriodo()!=null)
                periodoDao.sincronizarBDlocal(seccion.getPeriodo());

        }

        return resultado;
    }



}
