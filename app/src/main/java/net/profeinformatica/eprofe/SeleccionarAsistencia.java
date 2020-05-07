package net.profeinformatica.eprofe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.Spinner;

import net.profeinformatica.eprofe.adapter.AdapterAsignatura;
import net.profeinformatica.eprofe.adapter.AdapterSeccion;
import net.profeinformatica.eprofe.modelo.Asignatura;
import net.profeinformatica.eprofe.modelo.Seccion;
import net.profeinformatica.eprofe.modeloDao.AsignaturaDao;
import net.profeinformatica.eprofe.modeloDao.ModeloDaoBasic;
import net.profeinformatica.eprofe.modeloDao.SeccionDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class SeleccionarAsistencia extends AppCompatActivity {

    private Spinner sSecciones;
    private Spinner sAsignatura;
    private CalendarView cvFecha;
    private MenuItem searchItem;
    private ArrayList<Asignatura> asignaturas=new ArrayList<Asignatura>();

    private ArrayList<Seccion> seccions=new ArrayList<Seccion>();
    private AdapterSeccion adapterSeccion;
    private AdapterAsignatura adapterAsignatura;
    private Toolbar toolbar;
    private int PICK_CONTACT_REQUEST=1;

    private SeccionDao seccionDao=null;
    private AsignaturaDao asignaturaDao=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_asistencia);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Crear asistencia");

        seccionDao=new SeccionDao();
        asignaturaDao=new AsignaturaDao();

        cvFecha=(CalendarView) findViewById(R.id.cvFecha);

        sSecciones=(Spinner)findViewById(R.id.sSecciones);
        adapterSeccion=new AdapterSeccion(this,seccions);
        sSecciones.setAdapter(adapterSeccion);
        sSecciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //int codigo= sSecciones.getSelectedItemPosition();

                // System.out.println(new String("Holasfdfs" + codigo));

                Seccion seleccionSeccion;
                if(sSecciones!=null ||!(sSecciones.getSelectedItem()==null)) {
                    seleccionSeccion= (Seccion) sSecciones.getSelectedItem();
                    // System.out.println(new String("Holasfdfs" + seleccionSeccion.toString()));
                    getAsignaturas(seleccionSeccion.getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sAsignatura=(Spinner)findViewById(R.id.sAsignatura);
        adapterAsignatura=new AdapterAsignatura(this,asignaturas);
        sAsignatura.setAdapter(adapterAsignatura);


        List<Seccion> seccions1 =seccionDao.buscarPorDocente(ModeloDaoBasic.getDocente());

        if(seccions1!=null) {

            adapterSeccion.setSecciones(seccions1);
            adapterSeccion.notifyDataSetChanged();

        }


    }

    public void getAsignaturas(int seccion_id){

        List<Asignatura> asignaturas1=asignaturaDao.buscarPorSeccionDocente(seccion_id);

        if(asignaturas1!=null){
            adapterAsignatura.setAsignaturas(asignaturas1);
            //dataAdapter.notifyDataSetChanged();
            //sAsignatura.setSelection(0);
            adapterAsignatura.notifyDataSetChanged();
//            System.out.println(new String(asignaturas.get(0).toString()));
            // adapterSeccion.setSecciones(seccions);
            //sSecciones.setSelection(0);
            //adapterSeccion.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.it_crear:



                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                //Date d = new Date(year, month, day);
                String strDate = dateFormatter.format(new Date(cvFecha.getDate()));

                if(sSecciones!=null && sAsignatura!=null) {

                    Seccion seleccionSeccion = null;
                    if (!(sSecciones.getSelectedItem() == null)) {
                        seleccionSeccion = (Seccion) sSecciones.getSelectedItem();
                    }

                    Asignatura seleccionAsignatura = null;
                    if (!(sAsignatura.getSelectedItem() == null)) {
                        seleccionAsignatura = (Asignatura) sAsignatura.getSelectedItem();
                    }

                    //Se se verifica que exista el fecha
                    if (strDate.length() != 0) {

                        //System.out.println(new String("Se dio clip en el boton crear"+strDate));
                        Intent agregarEntrada = new Intent(this, CrearAsistencia.class);
                        agregarEntrada.putExtra("seccion", (Parcelable) seleccionSeccion);
                        agregarEntrada.putExtra("fecha", strDate);
                        agregarEntrada.putExtra("asignatura", (Parcelable) seleccionAsignatura);
                        startActivityForResult(agregarEntrada, PICK_CONTACT_REQUEST);
                    }
                }

                break;
        }
        return true;
    }

    private void startActivityForResult(Intent agregarEntrada) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the request went well (OK) and the request was PICK_CONTACT_REQUEST
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                return onOptionsItemSelected(item);
            }
        });



        return super.onCreateOptionsMenu(menu);
    }

}
