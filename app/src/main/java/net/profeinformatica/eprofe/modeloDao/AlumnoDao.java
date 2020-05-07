package net.profeinformatica.eprofe.modeloDao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.profeinformatica.eprofe.dataBase.eProfContract;
import net.profeinformatica.eprofe.modelo.Alumno;
import net.profeinformatica.eprofe.modelo.Matricula;
import net.profeinformatica.eprofe.modelo.Seccion;
import net.profeinformatica.eprofe.modeloDao.MatriculaDao;
import net.profeinformatica.eprofe.modeloDao.ModeloDaoBasic;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiService;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiUtils;

import java.util.Calendar;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static net.profeinformatica.eprofe.modelo.MarcasSincronizado.UPDATE_SERVER;

public class AlumnoDao extends ModeloDaoBasic {

    private MatriculaDao matriculaDaoAlumno=new MatriculaDao();


    @Override
    public boolean eliminar(Object c) {
        return false;
    }

    @Override
    public boolean registrar(Object c) {
        Alumno alumno=(Alumno)c;

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_ID,alumno.getId());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_RNE,alumno.getRne());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_USERNAME,alumno.getUsername());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_NOMBRE,alumno.getNombre());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_APELLIDO,alumno.getApellido());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_GENERO,alumno.getGenero());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_TELEFONO,alumno.getTelefono());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_EMAIL,alumno.getEmail());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_PASSWORD,alumno.getPassword());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_SINCRONIZACIONSERVER,alumno.getSicronizadoServidor());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_REMEMBERTOKEN,alumno.getRememberToken());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_CREATEDAT,alumno.getCreatedAt());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_UPDATEDAT,alumno.getUpdatedAt());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(eProfContract.AlumnosTable.TABLE_NAME, null, values);

        if(newRowId!=-1)
            return true;
        else
            return false;
    }

    @Override
    public boolean actualizar(Object c) {
        Alumno alumno=(Alumno) c;

        alumno.setSicronizadoServidor(UPDATE_SERVER);

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_ID,alumno.getId());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_RNE,alumno.getRne());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_USERNAME,alumno.getUsername());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_NOMBRE,alumno.getNombre());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_APELLIDO,alumno.getApellido());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_GENERO,alumno.getGenero());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_TELEFONO,alumno.getTelefono());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_EMAIL,alumno.getEmail());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_PASSWORD,alumno.getPassword());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_SINCRONIZACIONSERVER,alumno.getSicronizadoServidor());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_REMEMBERTOKEN,alumno.getRememberToken());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_CREATEDAT,alumno.getCreatedAt());
        values.put(eProfContract.AlumnosTable.COLUMN_NAME_UPDATEDAT,alumno.getUpdatedAt());

        String selection = eProfContract.AlumnosTable.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+alumno.getId() };
        int count = db.update(
                eProfContract.AlumnosTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(count>0)
            return true;
        else
            return false;
    }

    @Override
    public List<Alumno> todos(int limInf, int cantidad) {
        return null;
    }

    @Override
    public List<Alumno> todos() {
        return null;
    }

    @Override
    public Alumno buscarPorId(int id) {

        Alumno alumno=new Alumno();
        alumno.setId(-1);

        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                eProfContract.AlumnosTable.COLUMN_NAME_ID,
                eProfContract.AlumnosTable.COLUMN_NAME_RNE,
                eProfContract.AlumnosTable.COLUMN_NAME_USERNAME,
                eProfContract.AlumnosTable.COLUMN_NAME_NOMBRE,
                eProfContract.AlumnosTable.COLUMN_NAME_APELLIDO,
                eProfContract.AlumnosTable.COLUMN_NAME_GENERO,
                eProfContract.AlumnosTable.COLUMN_NAME_TELEFONO,
                eProfContract.AlumnosTable.COLUMN_NAME_EMAIL,
                eProfContract.AlumnosTable.COLUMN_NAME_PASSWORD,
                eProfContract.AlumnosTable.COLUMN_NAME_SINCRONIZACIONSERVER,
                eProfContract.AlumnosTable.COLUMN_NAME_REMEMBERTOKEN,
                eProfContract.AlumnosTable.COLUMN_NAME_CREATEDAT,
                eProfContract.AlumnosTable.COLUMN_NAME_UPDATEDAT
        };



        // Filter results WHERE "title" = 'My Title'
        String selection = eProfContract.AlumnosTable.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { ""+id };


        // How you want the results sorted in the resulting Cursor
        String sortOrder = eProfContract.AlumnosTable.COLUMN_NAME_ID + " ASC";

        Cursor cursor = db.query(
                eProfContract.AlumnosTable.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {

            alumno.setId(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_ID)));
            alumno.setRne(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_RNE)));
            alumno.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_USERNAME)));
            alumno.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_NOMBRE)));
            alumno.setApellido(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_APELLIDO)));
            alumno.setGenero(cursor.getInt(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_GENERO)));
            alumno.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_TELEFONO)));
            alumno.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_EMAIL)));
            alumno.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_PASSWORD)));
            //boolean sincronizacion=cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_SINCRONIZACIONSERVER) == 1 ? true : false;
            alumno.setSicronizadoServidor(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_SINCRONIZACIONSERVER));
            alumno.setRememberToken(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_REMEMBERTOKEN)));
            alumno.setCreatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_CREATEDAT)));
            alumno.setUpdatedAt(cursor.getString(cursor.getColumnIndexOrThrow(eProfContract.AlumnosTable.COLUMN_NAME_UPDATEDAT)));




        }
        cursor.close();


        return alumno;
    }

    @Override
    public List<Alumno> buscarPorDescripcion(String busqueda) {
        return null;
    }

    @Override
    public int sincronizarBDlocal(Object c) {

        //0=no accion, 1=guardar, 2 =actualizar
        int resultado=0;

        Alumno alumno=(Alumno) c;

        Alumno alumnoOnBD=buscarPorId(alumno.getId());

        if (alumnoOnBD.getId()==-1){

            if (registrar(alumno)){

                resultado=1;
            }
        }else{
            if(actualizar(alumno)){
                resultado=2;
            }
        }
        return resultado;
    }

    /**
     * Permite sicronizar los alumnos y la matricula de las secciones.
     * @author jdmayorga
     * @param c se debe enviar una Seccion
     *
     */

    @Override
    public boolean sincronizarServidor(Object c) {

        Seccion seccion=(Seccion)c;

        String year= ModeloDaoBasic.getDateFormatterOnlyYear().format(Calendar.getInstance().getTime());

        ApiService mAPIService = ApiUtils.getApiService();


        mAPIService.getAlumnosSeccion(year,seccion.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Matricula>>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {

                       //System.out.println(new String(" Error ========>>>>>"+e.getMessage() ));


                    }

                    @Override
                    public void onNext(List<Matricula> matriculas) {

                        //System.out.println(new String(" # Matricula ========>>>>>" +matriculas.size()));

                        if(matriculas!=null) {
                            for (int x = 0; x < matriculas.size(); x++) {


                                matriculas.get(x).getAlumno().setSicronizadoServidor(1);


                                //se manda a sincronizar en la base de datos local a el alumno
                                int resul=sincronizarBDlocal(matriculas.get(x).getAlumno());
                                //System.out.println(new String("Resultado al sicronizar alumno ========>>>>>" +resul));
                                if(resul!=0){
                                    //System.out.println(new String(" Se manta a sicronizar la matricula========>>>>>" ));
                                    matriculaDaoAlumno.sincronizarBDlocal(matriculas.get(x));//si el alumno se guarda se registra la matricula

                                }
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

    public void setDatosPrueba(){


        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        String sql="INSERT INTO " +
                            " alumnos (id, rne, username, nombre, apellido, genero, telefono, email, password, sincronizar_servidor, remember_token, created_at, updated_at) " +
                    " VALUES " +
                        " (19, '0108200500541', 'RCORNEJO', 'REYNA YAJAIRA', 'CORNEJO ZELAYA', 1, '0', 'RCORNEJO', '0108200500541', 1, null, null, null), " +
                        " (87, '0105200400109', '0105200400109', 'Ashlye Aracely', 'Castro Merlo', 1, '0', 'NA', '$2y$10$Sf8p4XEevjFEzq0i56mluONl5mxw7/TY2P60K0PXQYh77Fsy.8NvG', 1, null, '2019-02-19 03:43:35', '2019-02-19 03:43:35'), " +
                        " (88, '0103200500048', '0103200500048', 'Diana Lisbeth', 'Sabillon Roman', 1, '0', 'NA', '$2y$10$sEzkvYlvulyRWm.Xd0hS..yxqekdjKbTBBkdFjWSbDmXNzYVm7ZOy', 1, null, '2019-02-19 03:43:35', '2019-02-19 03:43:35'), " +
                        " (89, '0105200300240', '0105200300240', 'Digna Nayely', 'Ayala Gomez', 1, '0', 'NA', '$2y$10$XWkWynmbXBHRBU5kWzo/Le51gnu47aAMj1SaoUXs/WOJapbjdeLdC', 1, null, '2019-02-19 03:43:35', '2019-02-19 03:43:35'), " +
                        " (90, '0503200500547', '0503200500547', 'Doris Arely', 'Gomez Marcos', 1, '0', 'NA', '$2y$10$aMOFHvFePYE/bb.cqKRhhebB/VCiet6pCntY1fDXhhrni/065E3ee', 1, null, '2019-02-19 03:43:36', '2019-02-19 03:43:36'), " +
                        " (91, '0105200200953', '0105200200953', 'Emy Lohany', 'Hernandez Martinez', 1, '0', 'NA', '$2y$10$QXfiJ9/1v55azZmhqasAjuzGi5f4zNB07pqlXxG0cvlkmOp0pmfCK', 1, null, '2019-02-19 03:43:36', '2019-02-19 03:43:36'), " +
                        " (92, '0105200300486', '0105200300486', 'Greisy Mayeli', 'Garcia Funez', 1, '0', 'NA', '$2y$10$EEQ.iTR5dp8F1p4NoFYj5.eveSUnbHvSOz0nAId.ED638.cpmkv6W', 1, null, '2019-02-19 03:43:36', '2019-02-19 03:43:36'), " +
                        " (96, '0105200300395', '0105200300395', 'Katherin Yessenia', 'Montufar Lainez', 1, '0', 'NA', '$2y$10$M87JnGP1kNWLDNgQL2cFoOdIaCAdRym5JELtIETAMoIbma3GnvU3W', 1, null, '2019-02-19 03:43:36', '2019-02-19 03:43:36')," +
                        " (97, '0103200300128', '0103200300128', 'Kenia Liliana', 'Cornejo Mejia', 1, '0', 'NA', '$2y$10$Ah4y6NYMJ.9KfSzHU4oUu.wB2BeQhgf/sGOYPhjYHz0gQSoK2DlFu', 1, null, '2019-02-19 03:43:36', '2019-02-19 03:43:36')," +
                        " (98, '0501200304116', '0501200304116', 'Kerlin Jasmin', 'Portillo Santos', 1, '0', 'NA', '$2y$10$4V/K8fYmhWTlRgDjrlAK2ObdosKJ5wsxsIa0PvytvCxqEY.c/K2DS', 1, null, '2019-02-19 03:43:36', '2019-02-19 03:43:36')," +
                        " (100, '0313199900465', '0313199900465', 'Marlly Sicela', 'Palma Madrid', 1, '0', 'NA', '$2y$10$D7/TJhamVCdz.1KLzqv7WOpCXN6yTItv1OF5WxD9ZKtB7x7MTpNGu', 1, null, '2019-02-19 03:43:36', '2019-02-19 03:43:36'), " +
                        " (101, '0105200400437', '0105200400437', 'Mayra Francheska', 'Funez Orellana', 1, '0', 'NA', '$2y$10$1FCevBVxOOwGjQNcFvZm0ukTFJBl6YZ3WhK/qlL/AAL8yljW3d9US', 1, null, '2019-02-19 03:43:36', '2019-02-19 03:43:36'), " +
                        " (102, '0103200800206', '0103200800206', 'Meidy Elisa', 'Rivas Villalovo', 1, '0', 'NA', '$2y$10$pU6MKAvVagCoEM2a8KHjwO5VTU1iCSqA8PNvrzlZC7YdbkCoWN7Ei', 1, null, '2019-02-19 03:43:36', '2019-02-19 03:43:36'), " +
                        " (104, '0105200300445', '0105200300445', 'Axel Alberto', 'Martinez Arriaga', 2, '0', 'NA', '$2y$10$tKPFUPkYFQomo.4saZxAEO5V1.gsRGLJFQFuCXiW7ztXKo/vmDaGG', 1, null, '2019-02-19 03:43:37', '2019-02-21 04:06:09'), " +
                        " (106, '0105200400334', '0105200400334', 'Francisco Javier', 'Fernandez Mejia', 2, '0', 'NA', '$2y$10$sZsZBlhfVJapNammffXj2epqRE0PDHEui4kCKjTFClyanTBMo4dly', 1, null, '2019-02-19 03:43:37', '2019-02-21 04:06:15'), " +
                        " (113, '0105200200360', '0105200200360', 'Stiven Imanol', 'Amaya Cuaresma', 2, 'NA', 'NA', '$2y$10$EjdpjmgAjjt99HINowYmSejZQmG/CCcj8vWgiLBe1WuybL5Wmcw0W', 1, null, '2019-02-20 23:01:12', '2019-02-21 04:06:26'), " +
                        " (227, '0210199900959', '0210199900959', 'Dunia Arasely', 'Milla Peralta', 1, 'NA', 'NA', '$2y$10$c2yjewwdjMYCKXI3EsV22eAAetwAZ6H22IUFctatwMIn.hKlh7PBK', 1, null, '2020-04-27 01:50:20', '2020-04-27 01:50:20'), " +
                        " (228, '0209200301500', '0209200301500', 'Heydi Yaritza', 'Guerra Garza', 1, 'NA', 'NA', '$2y$10$JV3Al9Wj5brTz5dTwfsa5u1ZJCT79EHoydLqgb.OLOGlGiO/mLvz2', 1, null, '2020-04-27 01:50:20', '2020-04-27 01:50:20'), " +
                        " (229, '0103200500340', '0103200500340', 'Josary Nicol', 'Granados Perez', 1, 'NA', 'NA', '$2y$10$31JsX.vLYbD0I4MAD1pxVe2KZN8Gu419R/eVT7xCS3CPT8Ntreu9u', 1, null, '2020-04-27 01:50:20', '2020-04-27 01:50:20'), " +
                        " (230, '0105200501051', '0105200501051', 'Katherine Paoly', 'Caceres Antunez', 1, 'NA', 'NA', '$2y$10$oPseBlQU3K5Pp67HODMxFeKyWT93Sg356B9Q0i3HXQGtrrbE16ubi', 1, null, '2020-04-27 01:50:21', '2020-04-27 01:50:21'), " +
                        " (231, '0105200400917', '0105200400917', 'Mindy Alexa', 'Umanzor Castro', 1, 'NA', 'NA', '$2y$10$pDlPwSmcgmba8ufBiatc4OMhVABU/trfTQiG2.bPn8q88cMNcyVie', 1, null, '2020-04-27 01:50:21', '2020-04-27 01:50:21'), " +
                        " (232, '0511200400507', '0511200400507', 'Scarleth Michell', 'Rivera Castellanos', 1, 'NA', 'NA', '$2y$10$3gS7mTJIpDXo3DLw0azaVuG2POuo0W6p8F//m8nDXhSc2w0KaJ.s.', 1, null, '2020-04-27 01:50:21', '2020-04-27 01:50:21'), " +
                        " (233, '0107200500237', '0107200500237', 'Sonia Nicolle', 'Flores Funez', 1, 'NA', 'NA', '$2y$10$RjL0QXSBewLUZUHzIPW6/Ohf70U5dgffCSrx19mxS5mWvDUa61Js2', 1, null, '2020-04-27 01:50:21', '2020-04-27 01:50:21'), " +
                        " (234, '0103200400349', '0103200400349', 'Tania Lizeth', 'Canales Baquedano', 1, 'NA', 'NA', '$2y$10$eFUWpvtGKinbqZOAVfPaPOf0LjewNFjtZzsOTCaq7ZnKQ77.NifHm', 1, null, '2020-04-27 01:50:21', '2020-04-27 01:50:21'), " +
                        " (235, '0105200400091', '0105200400091', 'Yohely Estefania', 'Santos Nuñez', 1, 'NA', 'NA', '$2y$10$39liVVdsE2R8QxlfHZrkQeCqbHeb1qZhBUMQnt2aIZ.xCBeTrve1e', 1, null, '2020-04-27 01:50:21', '2020-04-27 01:50:21'), " +
                        " (236, '0108200400050', '0108200400050', 'Jose David', 'Hernandez Hernandez', 1, 'NA', 'NA', '$2y$10$0zAzal0kNom0hePnGx1vOegkLG11pb9tKPEyDNQaRAS.28IBDXHXm', 1, null, '2020-04-27 01:50:21', '2020-04-27 01:50:21'), " +
                        " (237, '0103200400117', '0103200400117', 'Juan Carlos', 'De Dios Mejia', 1, 'NA', 'NA', '$2y$10$43x6yo7XSm.sfO7GXfQBRuE4mYhruWlBEQAWySmQy4P1yQJyH.Iay', 1, null, '2020-04-27 01:50:21', '2020-04-27 01:50:21'), " +
                        " (238, '0103200200337', '0103200200337', 'Kevin Josue', 'Granados Colindres', 1, 'NA', 'NA', '$2y$10$Hx4EiSDN5mRB2tOkVECJrO5NqQZL8HaRMgueifGzX1w8uidiv6md2', 1, null, '2020-04-27 01:50:21', '2020-04-27 01:50:21'), " +
                        " (239, '0103200400165', '0103200400165', 'Marcio Javier', 'De Dios Mejia', 1, 'NA', 'NA', '$2y$10$bzdMkJqtLSjr.ED0mrcLReFGWjEZqrKQxdBD65D.nYRzgGDnuqmMO', 1, null, '2020-04-27 01:50:21', '2020-04-27 01:50:21'), " +
                        " (240, '0502200401966', '0502200401966', 'Meyfran Joseph', 'Villanueva Campos', 1, 'NA', 'NA', '$2y$10$RmX.3x0peRza4s7JTGmZWO9w9r.K2XLl9aVMB8j8CEilZIcWNgUFK', 1, null, '2020-04-27 01:50:21', '2020-04-27 01:50:21'), " +
                        " (241, '0101200100741', '0101200100741', 'Genesis Sarahi', 'Lopez Pagoada', 1, 'NA', 'NA', '$2y$10$3YrZMWTjCwWjs1dr1e3iwu1BfD1vqSmi3TXtfMFR1dq3Vq2aAW8c.', 1, null, '2020-04-27 01:53:10', '2020-04-27 01:53:10') ";

        db.execSQL(sql);

    }

}
