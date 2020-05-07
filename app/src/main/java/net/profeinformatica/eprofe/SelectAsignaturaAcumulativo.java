package net.profeinformatica.eprofe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import net.profeinformatica.eprofe.adapter.AdapterAsignaturaSeccion;
import net.profeinformatica.eprofe.adapter.AdapterSeccion;
import net.profeinformatica.eprofe.modelo.Asignatura;
import net.profeinformatica.eprofe.modelo.Seccion;
import net.profeinformatica.eprofe.modeloDao.AsignaturaDao;
import net.profeinformatica.eprofe.modeloDao.ModeloDaoBasic;
import net.profeinformatica.eprofe.modeloDao.SeccionDao;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SelectAsignaturaAcumulativo extends AppCompatActivity {
   // private ApiService mAPIService;
    private SwipeMenuListView lvAcumulativos;
    private AdapterAsignaturaSeccion adapterAsignaturaSeccion;
    private ArrayList<Asignatura> asignaturas=new ArrayList<Asignatura>();
    private Asignatura asignatura;

    private AdapterSeccion dapterSeccion;
    private ArrayList<Seccion> seccions=new ArrayList<Seccion>();
    private  Spinner spSecciones=null;
    private SeccionDao seccionDao=null;
    private AsignaturaDao asignaturaDao=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_asignatura_acumulativo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        seccionDao=new SeccionDao();
        asignaturaDao=new AsignaturaDao();

        lvAcumulativos=(SwipeMenuListView)findViewById(R.id.lvAsignaturaAcum);
        adapterAsignaturaSeccion=new AdapterAsignaturaSeccion(this,asignaturas);
        lvAcumulativos.setAdapter(adapterAsignaturaSeccion);

        spSecciones=(Spinner)findViewById(R.id.spSeccion);
        dapterSeccion=new AdapterSeccion(this,seccions);
        spSecciones.setAdapter(dapterSeccion);

        lvAcumulativos.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                asignatura=adapterAsignaturaSeccion.getAsignaturas().get(position);
                //System.out.println(new String("Abrir:===============>"+adapterAsistencia.getAsistencias().get(position).toString()));

                setParcial();

            }


        });


        List<Seccion> seccions1 =seccionDao.buscarPorDocente(ModeloDaoBasic.getDocente());

        if(seccions1!=null) {

            dapterSeccion.setSecciones(seccions1);

            dapterSeccion.notifyDataSetChanged();
        }




        //se escucha la seleccion de la seccion
        spSecciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //int codigo= sSecciones.getSelectedItemPosition();

                //System.out.println(new String("CLICK en SECCION:===============>"));

                Seccion seleccionSeccion;
                if(!(spSecciones.getSelectedItem()==null)) {
                    seleccionSeccion= (Seccion) spSecciones.getSelectedItem();
                    // System.out.println(new String("Holasfdfs" + seleccionSeccion.toString()));
                   getAsignaturas(seleccionSeccion.getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private void  setParcial(){

        final Spinner sParciales = new Spinner(this);
        ArrayAdapter<String> spinnerCountShoesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.parciales));
        sParciales.setAdapter(spinnerCountShoesArrayAdapter);


        new MaterialAlertDialogBuilder(this)
                .setTitle("Seleccione el parcial")
                .setView(sParciales)
                .setPositiveButton("IR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        String parcial=sParciales.getSelectedItem()+"";

                        // Toast.makeText(getApplicationContext(), day+"/"+ month+"/"+ year ,Toast.LENGTH_SHORT).show();
                        if(parcial.length()!=0) {
                            dialogInterface.dismiss();

                            lazarEditar(parcial);
                        }




                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                })
                .show();


    }

    private void lazarEditar(String parcial) {

        Seccion seleccionSeccion=null;
        if(!(spSecciones.getSelectedItem()==null)) {
            seleccionSeccion= (Seccion) spSecciones.getSelectedItem();
        }

        Intent editarAcumulativo = new Intent(this, EditarNotasAlumnoAsignaturaP.class);
        editarAcumulativo.putExtra("parcial", parcial);
        editarAcumulativo.putExtra("asignatura",(Parcelable)asignatura);
        editarAcumulativo.putExtra("idSeccion", seleccionSeccion.getId());
        startActivity(editarAcumulativo);
    }


    public void getAsignaturas(int codigoSeccion){

        List<Asignatura> asignaturas1 = asignaturaDao.buscarPorSeccionDocente(codigoSeccion);

        if (asignaturas1 != null) {
            adapterAsignaturaSeccion.setAsignaturas(asignaturas1);
            adapterAsignaturaSeccion.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume(){
        super.onResume();


    }

}
