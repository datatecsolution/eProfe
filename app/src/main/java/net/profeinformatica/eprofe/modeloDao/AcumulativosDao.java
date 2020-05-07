package net.profeinformatica.eprofe.modeloDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.profeinformatica.eprofe.dataBase.eProfContract;
import net.profeinformatica.eprofe.modelo.Acumulativo;
import net.profeinformatica.eprofe.modelo.Asignatura;
import net.profeinformatica.eprofe.modelo.NotaAcumulativo;
import net.profeinformatica.eprofe.modelo.Seccion;
import net.profeinformatica.eprofe.modelo.TipoAcumulativo;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiService;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.ADD_SERVER;
import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.DELETE_SEVER;
import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.NO_ADD_SERVER;
import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.UPDATE_SERVER;

public class AcumulativosDao extends ModeloDaoBasic {



    @Override
    public boolean eliminar(Object c) {

        Acumulativo acumulativo=(Acumulativo) c;

        NotaAcumulativosDao notaAcumulativosDao=new NotaAcumulativosDao();


        int deletedRows=0;

        //se verifica que el registro no este en agregado al servidor
        if(acumulativo.getId()==NO_ADD_SERVER){

            // Gets the data repository in write mode
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Define 'where' part of query.
            String selection = eProfContract.AcumulativosTable.COLUMN_NAME_MOVILID + " = ?";
            // Specify arguments in placeholder order.
            String[] selectionArgs = { "" +acumulativo.getMovilId()};
            // Issue SQL statement.
            deletedRows = db.delete(eProfContract.AcumulativosTable.TABLE_NAME, selection, selectionArgs);

        }else{
            acumulativo.setSicronizadoServidor(DELETE_SEVER);
            if(actulizarEstadoSincronizado(acumulativo))
                deletedRows=1;

        }


        if(deletedRows>0) {
            notaAcumulativosDao.eliminar(acumulativo);
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean registrar(Object c) {
        Acumulativo acumulativo=(Acumulativo) c;

        acumulativo.setSicronizadoServidor(ADD_SERVER);

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.AcumulativosTable.COLUMN_NAME_ID,acumulativo.getId());
        values.put(eProfContract.AcumulativosTable.COLUMN_NAME_SECCIONID,acumulativo.getSeccionId());
        values.put(eProfContract.AcumulativosTable.COLUMN_NAME_DESCRIPCION,acumulativo.getDescripcion());
        values.put(eProfContract.AcumulativosTable.COLUMN_NAME_TIPOACUMULATICOID,acumulativo.getTipoAcumulativoId());
        values.put(eProfContract.AcumulativosTable.COLUMN_NAME_FEHA,acumulativo.getFecha());
        values.put(eProfContract.AcumulativosTable.COLUMN_NAME_PARCIAL,acumulativo.getParcial());
        values.put(eProfContract.AcumulativosTable.COLUMN_NAME_VALOR,acumulativo.getValor());
        values.put(eProfContract.AcumulativosTable.COLUMN_NAME_ASIGNAURAID,acumulativo.getAsignaturaId());
        values.put(eProfContract.AcumulativosTable.COLUMN_NAME_SINCRONIZACIONSERVER,acumulativo.getSicronizadoServidor());
        values.put(eProfContract.AcumulativosTable.COLUMN_NAME_CREATEDAT,acumulativo.getCreatedAt());
        values.put(eProfContract.AcumulativosTable.COLUMN_NAME_UPDATEDAT,acumulativo.getUpdatedAt());



        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(eProfContract.AcumulativosTable.TABLE_NAME, null, values);

        if(newRowId!=-1) {
            acumulativo.setMovilId(getGeneratedKeys());
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean actualizar(Object c) {
        Acumulativo acumulativo=(Acumulativo) c;

        //se verifica que el registro no este en agregado al servidor
        if(acumulativo.getId()==NO_ADD_SERVER){
            acumulativo.setSicronizadoServidor(ADD_SERVER);
        }else{
            acumulativo.setSicronizadoServidor(UPDATE_SERVER);
        }

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.AcumulativosTable.COLUMN_NAME_SECCIONID,acumulativo.getSeccionId());
        values.put(eProfContract.AcumulativosTable.COLUMN_NAME_DESCRIPCION,acumulativo.getDescripcion());
        values.put(eProfContract.AcumulativosTable.COLUMN_NAME_TIPOACUMULATICOID,acumulativo.getTipoAcumulativoId());
        values.put(eProfContract.AcumulativosTable.COLUMN_NAME_FEHA,acumulativo.getFecha());
        values.put(eProfContract.AcumulativosTable.COLUMN_NAME_PARCIAL,acumulativo.getParcial());
        values.put(eProfContract.AcumulativosTable.COLUMN_NAME_VALOR,acumulativo.getValor());
        values.put(eProfContract.AcumulativosTable.COLUMN_NAME_ASIGNAURAID,acumulativo.getAsignaturaId());
        values.put(eProfContract.AcumulativosTable.COLUMN_NAME_SINCRONIZACIONSERVER,acumulativo.getSicronizadoServidor());
        values.put(eProfContract.AcumulativosTable.COLUMN_NAME_CREATEDAT,acumulativo.getCreatedAt());
        values.put(eProfContract.AcumulativosTable.COLUMN_NAME_UPDATEDAT,acumulativo.getUpdatedAt());

        String selection = eProfContract.AcumulativosTable.COLUMN_NAME_MOVILID + " = ?";
        String[] selectionArgs = { ""+acumulativo.getMovilId() };
        int count = db.update(
                eProfContract.AcumulativosTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(count>0)
            return true;
        else
            return false;
    }

    @Override
    public List<Acumulativo> todos(int limInf, int cantidad) {
        return null;
    }

    @Override
    public List<Acumulativo> todos() {
        return null;
    }

    @Override
    public Acumulativo buscarPorId(int id) {
        Acumulativo acumulativo=new Acumulativo();
        acumulativo.setId(-1);

        TipoAcumulativoDao tipoAcumulativoDao=new TipoAcumulativoDao();
        SeccionDao seccionDao=new SeccionDao();
        AsignaturaDao asignaturaDao=new AsignaturaDao();
        NotaAcumulativosDao notaAcumulativosDao=new NotaAcumulativosDao();

        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.AcumulativosTable.COLUMN_NAME_MOVILID,
                eProfContract.AcumulativosTable.COLUMN_NAME_ID,
                eProfContract.AcumulativosTable.COLUMN_NAME_SECCIONID,
                eProfContract.AcumulativosTable.COLUMN_NAME_DESCRIPCION,
                eProfContract.AcumulativosTable.COLUMN_NAME_TIPOACUMULATICOID,
                eProfContract.AcumulativosTable.COLUMN_NAME_FEHA,
                eProfContract.AcumulativosTable.COLUMN_NAME_PARCIAL,
                eProfContract.AcumulativosTable.COLUMN_NAME_VALOR,
                eProfContract.AcumulativosTable.COLUMN_NAME_ASIGNAURAID,
                eProfContract.AcumulativosTable.COLUMN_NAME_SINCRONIZACIONSERVER,
                eProfContract.AcumulativosTable.COLUMN_NAME_CREATEDAT,
                eProfContract.AcumulativosTable.COLUMN_NAME_UPDATEDAT
        };



        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.AcumulativosTable.COLUMN_NAME_MOVILID + " = ? and "
                            +eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_SINCRONIZACIONSERVER+"<>3";
        String[] selectionArgs = { ""+id };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.AcumulativosTable.COLUMN_NAME_MOVILID + " DESC";

        Cursor cursor = db.query(
                eProfContract.AcumulativosTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {

            acumulativo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_ID)));
            acumulativo.setMovilId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_MOVILID)));
            acumulativo.setSeccionId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_SECCIONID)));
            acumulativo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_SECCIONID)));
            acumulativo.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_DESCRIPCION)));
            acumulativo.setTipoAcumulativoId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_TIPOACUMULATICOID)));
            acumulativo.setFecha(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_FEHA)));
            acumulativo.setParcial(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_PARCIAL)));
            acumulativo.setValor(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_VALOR)));
            acumulativo.setAsignaturaId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_ASIGNAURAID)));
            acumulativo.setSicronizadoServidor(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_SINCRONIZACIONSERVER)));
            acumulativo.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_CREATEDAT)));
            acumulativo.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_UPDATEDAT)));


            TipoAcumulativo tipoAcumulativo=tipoAcumulativoDao.buscarPorId(acumulativo.getTipoAcumulativoId());

            if(tipoAcumulativo.getId()!=-1)
                acumulativo.setTipoAcumulativo(tipoAcumulativo);




            Seccion seccion=seccionDao.buscarPorId(acumulativo.getSeccionId());
            if(seccion.getId()!=-1)
                acumulativo.setSeccion(seccion);

            Asignatura asignatura=asignaturaDao.buscarPorId(acumulativo.getAsignaturaId());
            if (asignatura.getId()!=-1)
                acumulativo.setAsignatura(asignatura);

            List<NotaAcumulativo> notas=notaAcumulativosDao.buscarPorIdAcumulativo(acumulativo.getMovilId());
            //if(notas!=null)
                acumulativo.setNotasAcumulativos(notas);



        }
        cursor.close();
        return acumulativo;
    }

    public Acumulativo buscarPorIdServer(int id) {
        Acumulativo acumulativo=new Acumulativo();
        acumulativo.setId(-1);

        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.AcumulativosTable.COLUMN_NAME_ID,
                eProfContract.AcumulativosTable.COLUMN_NAME_MOVILID,
                eProfContract.AcumulativosTable.COLUMN_NAME_SECCIONID,
                eProfContract.AcumulativosTable.COLUMN_NAME_DESCRIPCION,
                eProfContract.AcumulativosTable.COLUMN_NAME_TIPOACUMULATICOID,
                eProfContract.AcumulativosTable.COLUMN_NAME_FEHA,
                eProfContract.AcumulativosTable.COLUMN_NAME_PARCIAL,
                eProfContract.AcumulativosTable.COLUMN_NAME_VALOR,
                eProfContract.AcumulativosTable.COLUMN_NAME_ASIGNAURAID,
                eProfContract.AcumulativosTable.COLUMN_NAME_SINCRONIZACIONSERVER,
                eProfContract.AcumulativosTable.COLUMN_NAME_CREATEDAT,
                eProfContract.AcumulativosTable.COLUMN_NAME_UPDATEDAT
        };



        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.AcumulativosTable.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+id };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.AcumulativosTable.COLUMN_NAME_ID + " DESC";

        Cursor cursor = db.query(
                eProfContract.AcumulativosTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {

            acumulativo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_ID)));
            acumulativo.setMovilId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_MOVILID)));
            acumulativo.setSeccionId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_SECCIONID)));
            acumulativo.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_DESCRIPCION)));
            acumulativo.setTipoAcumulativoId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_TIPOACUMULATICOID)));
            acumulativo.setFecha(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_FEHA)));
            acumulativo.setParcial(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_PARCIAL)));
            acumulativo.setValor(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_VALOR)));
            acumulativo.setAsignaturaId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_ASIGNAURAID)));
            acumulativo.setSicronizadoServidor(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_SINCRONIZACIONSERVER)));
            acumulativo.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_CREATEDAT)));
            acumulativo.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_UPDATEDAT)));




        }
        cursor.close();
        return acumulativo;
    }

    @Override
    public List<Acumulativo> buscarPorDescripcion(String busqueda) {
        return null;
    }

    @Override
    public int sincronizarBDlocal(Object c) {
        //0=no accion, 1=guardar, 2 =actualizar
        int resultado=0;
        Acumulativo acumulativo=(Acumulativo)c;
        Acumulativo acumulativoOnBD=buscarPorIdServer(acumulativo.getId());

        if(acumulativoOnBD.getId()==-1){
            if(registrar(acumulativo)){
                resultado=1;
            }
        }else{
            acumulativo.setMovilId(acumulativoOnBD.getMovilId());
            if(actualizar(acumulativo)){
                resultado=2;
            }
        }
        return resultado;
    }

    @Override
    public boolean sincronizarServidor(Object c) {



        Seccion seccion=(Seccion)c;

        final NotaAcumulativosDao notaAcumulativosDao=new NotaAcumulativosDao();

        ApiService mAPIService = ApiUtils.getApiService();

        mAPIService.getAcumulativosDocenteSeccion(ModeloDaoBasic.getDocente().getId(),seccion.getId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Acumulativo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Acumulativo> acumulativos) {

                        if(acumulativos!=null){
                            for (int i = 0; i < acumulativos.size(); i++) {

                                //se manda sincronizar con la base de datos local
                                int resul=sincronizarBDlocal(acumulativos.get(i));

                                if(resul!=0 && acumulativos.get(i).getNotasAcumulativos()!=null){

                                    List<NotaAcumulativo> notas=acumulativos.get(i).getNotasAcumulativos();

                                    for (int c=0;c<notas.size();c++) {
                                        notas.get(c).setAcumulativoMovilId(acumulativos.get(i).getMovilId());
                                        notaAcumulativosDao.sincronizarBDlocal(notas.get(c));
                                    }
                                }

                            }
                        }

                    }
                });



        return false;
    }

    @Override
    public int getGeneratedKeys() {
        int key=0;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT seq FROM SQLITE_SEQUENCE where name=?";

        String[] selectionArgs = {eProfContract.AcumulativosTable.TABLE_NAME};

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

    public List<Acumulativo> buscarPorToDocenteSeccionAsig(Integer idSeccion,Integer idAsignatura,String parcial) {
        List<Acumulativo> acumulativos=new ArrayList<Acumulativo>();
        boolean existe=false;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        SeccionDao seccionDao=new SeccionDao();





        String sql="SELECT "


                +eProfContract.AsignaturasTable.TABLE_NAME+"."+eProfContract.AsignaturasTable.COLUMN_NAME_ALIAS+" , "
                +eProfContract.AsignaturasTable.TABLE_NAME+"."+eProfContract.AsignaturasTable.COLUMN_NAME_NOMBRE+" , "
                +eProfContract.AsignaturasTable.TABLE_NAME+"."+eProfContract.AsignaturasTable.COLUMN_NAME_TIPO+" , "

                +eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_ID+" , "
                +eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_MOVILID+" , "
                +eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_SECCIONID+" , "
                +eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_DESCRIPCION+" , "
                +eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_TIPOACUMULATICOID+" , "
                +eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_FEHA+" , "
                +eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_PARCIAL+" , "
                +eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_VALOR+" , "
                +eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_ASIGNAURAID+" , "
                +eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_SINCRONIZACIONSERVER+" , "
                +eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_CREATEDAT+" , "
                +eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_UPDATEDAT
                +" FROM " +eProfContract.AcumulativosTable.TABLE_NAME
                            +"  JOIN "+eProfContract.AsignaturasSeccionesTable.TABLE_NAME
                                    +" ON " +eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_ASIGNAURAID
                                            +"="+eProfContract.AsignaturasSeccionesTable.TABLE_NAME+"."+eProfContract.AsignaturasSeccionesTable.COLUMN_NAME_ASIGNATURAID
                            +" JOIN "+ eProfContract.AsignaturasTable.TABLE_NAME
                                    +" ON "+eProfContract.AsignaturasTable.TABLE_NAME+"."+eProfContract.AsignaturasTable.COLUMN_NAME_ID
                                            +"="+eProfContract.AsignaturasSeccionesTable.TABLE_NAME+"."+eProfContract.AsignaturasSeccionesTable.COLUMN_NAME_ASIGNATURAID
                                    +" AND "+eProfContract.AcumulativosTable.TABLE_NAME +"."+eProfContract.AcumulativosTable.COLUMN_NAME_SECCIONID
                                            +"="+eProfContract.AsignaturasSeccionesTable.TABLE_NAME+"."+eProfContract.AsignaturasSeccionesTable.COLUMN_NAME_SECCIONID
                +" WHERE "+ eProfContract.AsignaturasSeccionesTable.TABLE_NAME+"."+eProfContract.AsignaturasSeccionesTable.COLUMN_NAME_DOCENTEID+"=? and " +
                            eProfContract.AsignaturasSeccionesTable.TABLE_NAME+"."+eProfContract.AsignaturasSeccionesTable.COLUMN_NAME_SECCIONID+"=? and "+
                            eProfContract.AsignaturasTable.TABLE_NAME+"."+eProfContract.AsignaturasTable.COLUMN_NAME_ID+"=? and " +
                            eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_PARCIAL+"=? and "+
                            eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_SINCRONIZACIONSERVER+"<>3";








        String[] selectionArgs = { ""+ModeloDaoBasic.getDocente().getId(),""+ idSeccion,""+ idAsignatura,parcial};

        Cursor cursor = db.rawQuery(sql,selectionArgs);

        System.out.println(new String("Exitooooo en Acumulativo:===============>"+sql));


        while(cursor.moveToNext()) {
            Acumulativo acumulativo=new Acumulativo();

            acumulativo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_ID)));
            acumulativo.setMovilId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_MOVILID)));
            acumulativo.setSeccionId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_SECCIONID)));
            acumulativo.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_DESCRIPCION)));
            acumulativo.setTipoAcumulativoId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_TIPOACUMULATICOID)));
            acumulativo.setFecha(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_FEHA)));
            acumulativo.setParcial(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_PARCIAL)));
            acumulativo.setValor(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_VALOR)));
            acumulativo.setAsignaturaId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_ASIGNAURAID)));
            acumulativo.setSicronizadoServidor(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_SINCRONIZACIONSERVER)));
            acumulativo.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_CREATEDAT)));
            acumulativo.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_UPDATEDAT)));

            Asignatura asignatura=new Asignatura();

            asignatura.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_ASIGNAURAID)));
            asignatura.setAlias(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AsignaturasTable.COLUMN_NAME_ALIAS)));
            asignatura.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AsignaturasTable.COLUMN_NAME_NOMBRE)));
            asignatura.setTipo(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AsignaturasTable.COLUMN_NAME_TIPO)));

            acumulativo.setAsignatura(asignatura);

            Seccion seccion=new Seccion();

            seccion=seccionDao.buscarPorId(acumulativo.getSeccionId());

            if(seccion.getId()!=-1)
                acumulativo.setSeccion(seccion);

            acumulativos.add(acumulativo);

            existe=true;

        }
        cursor.close();


        if(existe)
            return acumulativos;
        else
            return null;
    }

    public boolean actulizarEstadoSincronizado(Object c) {
        Acumulativo acumulativo=(Acumulativo)c;
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put( eProfContract.AcumulativosTable.COLUMN_NAME_SINCRONIZACIONSERVER,acumulativo.getSicronizadoServidor());


        String selection = eProfContract.AcumulativosTable.COLUMN_NAME_MOVILID + " = ?";
        String[] selectionArgs = { ""+acumulativo.getMovilId()};
        int count = db.update(
                eProfContract.AcumulativosTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(count>0) {
            return true;
        }
        else
            return false;
    }

    public double buscarTotalAsigParcial(Integer idSeccion,Integer idAsignatura,String parcial) {
        double total=0;
        boolean existe=false;

        SQLiteDatabase db = dbHelper.getReadableDatabase();


        String sql="SELECT "
                +eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_VALOR+"  "
                +" FROM " +eProfContract.AcumulativosTable.TABLE_NAME
                +"  JOIN "+eProfContract.AsignaturasSeccionesTable.TABLE_NAME
                +" ON " +eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_ASIGNAURAID
                +"="+eProfContract.AsignaturasSeccionesTable.TABLE_NAME+"."+eProfContract.AsignaturasSeccionesTable.COLUMN_NAME_ASIGNATURAID
                +" JOIN "+ eProfContract.AsignaturasTable.TABLE_NAME
                +" ON "+eProfContract.AsignaturasTable.TABLE_NAME+"."+eProfContract.AsignaturasTable.COLUMN_NAME_ID
                +"="+eProfContract.AsignaturasSeccionesTable.TABLE_NAME+"."+eProfContract.AsignaturasSeccionesTable.COLUMN_NAME_ASIGNATURAID
                +" AND "+eProfContract.AcumulativosTable.TABLE_NAME +"."+eProfContract.AcumulativosTable.COLUMN_NAME_SECCIONID
                +"="+eProfContract.AsignaturasSeccionesTable.TABLE_NAME+"."+eProfContract.AsignaturasSeccionesTable.COLUMN_NAME_SECCIONID
                +" WHERE "+ eProfContract.AsignaturasSeccionesTable.TABLE_NAME+"."+eProfContract.AsignaturasSeccionesTable.COLUMN_NAME_DOCENTEID+"=? and " +
                eProfContract.AsignaturasSeccionesTable.TABLE_NAME+"."+eProfContract.AsignaturasSeccionesTable.COLUMN_NAME_SECCIONID+"=? and "+
                eProfContract.AsignaturasTable.TABLE_NAME+"."+eProfContract.AsignaturasTable.COLUMN_NAME_ID+"=? and " +
                eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_PARCIAL+"=? and "+
                eProfContract.AcumulativosTable.TABLE_NAME+"."+eProfContract.AcumulativosTable.COLUMN_NAME_SINCRONIZACIONSERVER+"<>3";



        String[] selectionArgs = { ""+ModeloDaoBasic.getDocente().getId(),""+ idSeccion,""+ idAsignatura,parcial};

        Cursor cursor = db.rawQuery(sql,selectionArgs);

       // System.out.println(new String("Exitooooo en Acumulativo:===============>"+sql));


        while(cursor.moveToNext()) {

            total=total+cursor.getDouble(cursor.getColumnIndexOrThrow(eProfContract.AcumulativosTable.COLUMN_NAME_VALOR));
            existe=true;

        }
        cursor.close();





        if(existe)
            return total;
        else
            return 0;
    }
}
