package net.profeinformatica.eprofe.modeloDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.profeinformatica.eprofe.dataBase.eProfContract;
import net.profeinformatica.eprofe.modelo.Docente;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiService;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiUtils;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        values.put(eProfContract.DocentesTable.COLUMN_NAME_SINCRONIZACIONSERVER, true);
        values.put(eProfContract.DocentesTable.COLUMN_NAME_UPDATEDAT, myDocente.getUpdatedAt());
        values.put(eProfContract.DocentesTable.COLUMN_NAME_CREATEDAT, myDocente.getCreatedAt());
        values.put(eProfContract.DocentesTable.COLUMN_NAME_REMEMBERTOKEN, myDocente.getRememberToken());
        values.put(eProfContract.DocentesTable.COLUMN_NAME_USERNAME, myDocente.getUsername());
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
        //0=no accion, 1=guardar, 2 =actualizar
        int resultado=0;
        Docente docente=(Docente)c;
        Docente docenteOnDB=buscarPorId(docente.getId());

        if (docenteOnDB.getId()==-1){
            if(registrar(docente)){
                resultado=1;
                docente.setId(getGeneratedKeys());
                ModeloDaoBasic.setDocente(docente);
            }
        }else{
            docente.setId(docenteOnDB.getId());
            if (actualizar(docente)){
                resultado=2;
            }
        }
        return resultado;
    }

    @Override
    public boolean sincronizarServidor(Object c) {

        Docente myDocente=(Docente)c;

        ApiService mAPIService = ApiUtils.getApiService();

        mAPIService.saveDocente(myDocente.getNombre(), myDocente.getApellido(),
                myDocente.getGenero(), myDocente.getDireccion(),
                myDocente.getUserSace(), myDocente.getPasswordSace(),
                myDocente.getEmail(), myDocente.getTelefono())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Docente>() {
                    @Override
                    public void onCompleted() {
                       //

                    }

                    @Override
                    public void onError(Throwable e) {
                       // progressDoalog2.dismiss();

                    }

                    @Override
                    public void onNext(Docente docente) {
                       // myDocente.setId(docente.getId());

                        //setPrefencias();
                        if(docente!=null){
                            sincronizarBDlocal(docente);
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

        String[] selectionArgs = {eProfContract.DocentesTable.TABLE_NAME};

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
    public void setDatosPrueba(){


        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        String sql="INSERT INTO docentes (id, username, nombre, apellido, genero, direccion, user_sace, password_sace, telefono, email, password, sincronizar_servidor, remember_token, created_at, updated_at) VALUES (1, 'jdmayorga', 'David', 'Mayorga', 2, 'barrio suyapa San Juan Pueblo', '01051986004177011', null, '98837876', 'jose.david.mayorga@yahoo.com', null, 1, null, '2019-02-09 00:42:45', '2020-05-07 00:21:40')";

        db.execSQL(sql);




    }


}
