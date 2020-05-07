package net.profeinformatica.eprofe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import net.profeinformatica.eprofe.adapter.AdapterSeccionCentroPeriodoAlumnos;
import net.profeinformatica.eprofe.modelo.Seccion;
import net.profeinformatica.eprofe.modeloDao.ModeloDaoBasic;
import net.profeinformatica.eprofe.modeloDao.SeccionDao;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SelectSeccionAlumnos extends AppCompatActivity {

    private Toolbar toolbar;

    private AdapterSeccionCentroPeriodoAlumnos adapterSeccionCentroPeriodo;

    //private ApiService mAPIService;
    private ListView lvSecciones;
    private SeccionDao seccionDao=null;

    private ArrayList<Seccion> secciones=new ArrayList<Seccion>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seccion_alumnos);
         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        lvSecciones =(ListView)findViewById(R.id.lvSecciones);
        seccionDao=new SeccionDao();


        adapterSeccionCentroPeriodo=new AdapterSeccionCentroPeriodoAlumnos(this,secciones);


        lvSecciones.setAdapter(adapterSeccionCentroPeriodo);

       // mAPIService = ApiUtils.getApiService();

        lvSecciones.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Seccion seccion=adapterSeccionCentroPeriodo.getSecciones().get(position);
                //System.out.println(new String("Abrir:===============>"+adapterAsistencia.getAsistencias().get(position).toString()));

                lazarEditarAlumnos(seccion);

            }


        });

    }

    private void lazarEditarAlumnos(Seccion seccion) {
        Intent editarAlumno = new Intent(this, EditarAlumno.class);
        editarAlumno.putExtra("seccion", (Parcelable) seccion);
        startActivity(editarAlumno);
    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...


        //se extrae de las preferencias el codigo del docente almacenado
        SharedPreferences pref = getApplicationContext().getSharedPreferences("prefeDocenteLocal", 0); // 0 - for private mode
        Integer codigo= pref.getInt("docente_codigo", -1); // getting Integer
        //String codigoSace=pref.getString("docente_codigo_sace","NA");

        final ProgressDialog progressDoalog2;
        progressDoalog2 = new ProgressDialog(this);
        progressDoalog2.setMax(100);
        progressDoalog2.show();

        List<Seccion> seccions1 =seccionDao.buscarPorDocente(ModeloDaoBasic.getDocente());

        if(seccions1!=null) {
            adapterSeccionCentroPeriodo.setSecciones(seccions1);

            adapterSeccionCentroPeriodo.notifyDataSetChanged();
        }
        progressDoalog2.dismiss();



    }

}
