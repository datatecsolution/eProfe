package net.profeinformatica.eprofe;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import net.profeinformatica.eprofe.dataBase.eProfContract;
import net.profeinformatica.eprofe.dataBase.eProfDbHelper;
import net.profeinformatica.eprofe.modeloDao.DocenteDao;
import net.profeinformatica.eprofe.modeloDao.ModeloDaoBasic;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class MenuPrincipal extends AppCompatActivity {

    private CardView btnAsistencia;
    private CardView btnAcumultivo;
    private CardView btnAsignaturaAcum;
    private CardView btnDatosDocente;
    private CardView btnSincronizarSace;
    private CardView btnGestionAlumnos;
    private DocenteDao docenteDao=null;

    private static MenuPrincipal  instance;

    public static Context getContext()
    {
        return instance;
    }
    public static Bitmap decodeFile(File file, int reqWidth, int reqHeight){

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file.getPath(), options);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;


        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eProfDbHelper dbHelper = new eProfDbHelper(this);
        docenteDao=new DocenteDao();

        ModeloDaoBasic.setDocente(docenteDao.buscarPorId(1));



        //deleteTableSicronizacionMovil();
        //createTableSicronizacionMovil();




        btnAcumultivo=(CardView)findViewById(R.id.laAcumulativos);
        btnAsistencia=(CardView)findViewById(R.id.laAsistencias);
        btnAsignaturaAcum=(CardView)findViewById(R.id.laNotas);
        btnDatosDocente=(CardView)findViewById(R.id.laDocentes);
        btnSincronizarSace=(CardView)findViewById(R.id.laSincronizar);
        btnGestionAlumnos=(CardView)findViewById(R.id.laAlumnos);

        btnGestionAlumnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gestioAlumno= new Intent(getApplicationContext(),SelectSeccionAlumnos.class);
                startActivity(gestioAlumno);
            }
        });
        btnSincronizarSace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sincronizar= new Intent(getApplicationContext(),SincronizarSace.class);
                startActivity(sincronizar);
            }
        });

        btnDatosDocente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent datosDocente=new Intent(getApplicationContext(),DatosDocente.class);
                startActivity(datosDocente);
            }
        });

        btnAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listaAsistencia=new Intent(getApplicationContext(),ListAsistencia.class);
                startActivity(listaAsistencia);
            }
        });
        btnAcumultivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent listaAcumulativo=new Intent(getApplicationContext(),ListaAcumulativos.class);
                Intent listaAcumulativo=new Intent(getApplicationContext(),SelectSeccionAsignatura.class);
                startActivity(listaAcumulativo);
            }
        });
        btnAsignaturaAcum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listaAsignaturaAcumulativo=new Intent(getApplicationContext(),SelectAsignaturaAcumulativo.class);
                startActivity(listaAsignaturaAcumulativo);
            }
        });


    }

    public void deleteTableSicronizacionMovil(){
        eProfDbHelper dbHelper = new eProfDbHelper(this);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.execSQL(eProfContract.getSQL_DELETE_AcumulativosTable());
        db.execSQL(eProfContract.getSQL_DELETE_DetallesAsistenciasTable());
        db.execSQL(eProfContract.getSQL_DELETE_EncabezadoAsistenciasTable());
        db.execSQL(eProfContract.getSQL_DELETE_SeccionsTable());
        db.execSQL(eProfContract.getSQL_DELETE_AlumnosTable());
        db.execSQL(eProfContract.getSQL_DELETE_NotaAcumulativosTable());
        db.execSQL(eProfContract.getSQL_DELETE_MatriculasTable());

    }
    public void createTableSicronizacionMovil(){
        eProfDbHelper dbHelper = new eProfDbHelper(this);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.execSQL(eProfContract.getSQL_CREATE_AcumulativosTable());
        db.execSQL(eProfContract.getSQL_CREATE_DetallesAsistenciasTable());
        db.execSQL(eProfContract.getSQL_CREATE_EncabezadoAsistenciasTable());
        db.execSQL(eProfContract.getSQL_CREATE_SeccionsTable());
        db.execSQL(eProfContract.getSQL_CREATE_AlumnosTable());
        db.execSQL(eProfContract.getSQL_CREATE_NotaAcumulativosTable());
        db.execSQL(eProfContract.getSQL_CREATE_MatriculasTable());

    }

}
