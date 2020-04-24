package net.profeinformatica.eprofe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.google.android.material.textfield.TextInputLayout;

import net.profeinformatica.eprofe.adapter.AdapterAsignatura;
import net.profeinformatica.eprofe.adapter.AdapterSeccion;
import net.profeinformatica.eprofe.adapter.AdapterTipoAcumulativo;
import net.profeinformatica.eprofe.modelo.Asignatura;
import net.profeinformatica.eprofe.modelo.Seccion;
import net.profeinformatica.eprofe.modelo.TipoAcumulativo;
import net.profeinformatica.eprofe.modeloDao.AcumulativosDao;
import net.profeinformatica.eprofe.modeloDao.AsignaturaDao;
import net.profeinformatica.eprofe.modeloDao.ModeloDaoBasic;
import net.profeinformatica.eprofe.modeloDao.SeccionDao;
import net.profeinformatica.eprofe.modeloDao.TipoAcumulativoDao;

import java.util.ArrayList;
import java.util.List;

public class SeleccionarAcumulativo extends AppCompatActivity {

    private Spinner sTipoAcumulativo;
    private Spinner sSecciones;
    private Spinner sAsignatura;

    private ArrayList<Seccion> seccions=new ArrayList<Seccion>();
    private ArrayList<Asignatura> asignaturas=new ArrayList<Asignatura>();
    private ArrayList<TipoAcumulativo> tipoAcumulativos=new ArrayList<TipoAcumulativo>();
    private AdapterSeccion adapterSeccion;
    private AdapterAsignatura adapterAsignatura;
    private AdapterTipoAcumulativo adapterTipoAcumulativo;
    private Spinner sParciales;
    private Toolbar toolbar;
    private int PICK_CONTACT_REQUEST=1;
    private TextInputLayout etDescripcion;
    private TextInputLayout etValor;


    private SeccionDao seccionDao=null;
    private AsignaturaDao asignaturaDao=null;
    private TipoAcumulativoDao tipoAcumulativoDao=null;
    private AcumulativosDao acumulativosDao=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_acumulativo);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etDescripcion=(TextInputLayout)findViewById(R.id.etDescripcion);
        etValor=(TextInputLayout) findViewById(R.id.etValor);

        tipoAcumulativoDao=new TipoAcumulativoDao();
        seccionDao=new SeccionDao();
        asignaturaDao=new AsignaturaDao();
        acumulativosDao=new AcumulativosDao();

        sSecciones=(Spinner)findViewById(R.id.sSecciones2);
        adapterSeccion=new AdapterSeccion(this,seccions);
        sSecciones.setAdapter(adapterSeccion);

        List<Seccion> seccions1 =seccionDao.buscarPorDocente(ModeloDaoBasic.getDocente());

        if(seccions1!=null) {
            adapterSeccion.setSecciones(seccions1);
            adapterSeccion.notifyDataSetChanged();
        }

        //se escucha la seleccion de la seccion
        sSecciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //int codigo= sSecciones.getSelectedItemPosition();

                // System.out.println(new String("Holasfdfs" + codigo));

                Seccion seleccionSeccion;
                if(!(sSecciones.getSelectedItem()==null)) {
                    seleccionSeccion= (Seccion) sSecciones.getSelectedItem();
                    // System.out.println(new String("Holasfdfs" + seleccionSeccion.toString()));
                    getAsignaturas(seleccionSeccion.getId());

                    double valorMax=getTotalMax();
                    if(valorMax!=-1){
                        etValor.setHint("Valor max "+valorMax);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sAsignatura=(Spinner)findViewById(R.id.sAsignatura2);
        adapterAsignatura=new AdapterAsignatura(this,asignaturas);
        sAsignatura.setAdapter(adapterAsignatura);


        sAsignatura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                double valorMax=getTotalMax();
                if(valorMax!=-1){
                    etValor.setHint("Valor max "+valorMax);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        sTipoAcumulativo=(Spinner)findViewById(R.id.sTipoAcumulativo);
        adapterTipoAcumulativo=new AdapterTipoAcumulativo(this,tipoAcumulativos);
        sTipoAcumulativo.setAdapter(adapterTipoAcumulativo);

        sTipoAcumulativo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                double valorMax=getTotalMax();
                if(valorMax!=-1){
                    etValor.setHint("Valor max "+valorMax);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        List<TipoAcumulativo> tipoAcumulativos =tipoAcumulativoDao.todos();

        if(tipoAcumulativos!=null){

            adapterTipoAcumulativo.setTipoAcumulativos(tipoAcumulativos);
            adapterTipoAcumulativo.notifyDataSetChanged();

        }



        sParciales = (Spinner)findViewById(R.id.sParcial);
        ArrayAdapter<String> spinnerCountShoesArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_parcial2, getResources().getStringArray(R.array.parciales));
        sParciales.setAdapter(spinnerCountShoesArrayAdapter);

        sParciales.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                double valorMax=getTotalMax();
                if(valorMax!=-1){
                    etValor.setHint("Valor max "+valorMax);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etValor.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!etValor.getEditText().getText().toString().isEmpty() && etValor.getEditText().getText().toString().length() > 0) {
                   double entrada = Double.parseDouble(etValor.getEditText().getText().toString());

                   double total= getTotalMax();

                   if(total!=-1)
                       if(entrada>total){
                           etValor.getEditText().setError("El valor del acumulativo no pueder ser mayor a "+total);
                       }

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }
    public void getAsignaturas(int seccion_id) {

        List<Asignatura> asignaturas1 = asignaturaDao.buscarPorSeccionDocente(seccion_id);

        if (asignaturas1 != null) {
            adapterAsignatura.setAsignaturas(asignaturas1);
            adapterAsignatura.notifyDataSetChanged();

        }
    }
    public double getTotalMax(){

        double valorMax=0;
        boolean exite=false;

        Seccion seleccionSeccion = null;
        if (!(sSecciones.getSelectedItem() == null)) {
            seleccionSeccion = (Seccion) sSecciones.getSelectedItem();

            Asignatura seleccionAsignatura = null;
            if (!(sAsignatura.getSelectedItem() == null)) {
                seleccionAsignatura = (Asignatura) sAsignatura.getSelectedItem();

                TipoAcumulativo seleccionTipoAcumulativo = null;
                if (!(sTipoAcumulativo.getSelectedItem() == null)) {
                    seleccionTipoAcumulativo = (TipoAcumulativo) sTipoAcumulativo.getSelectedItem();

                    String parcial = sParciales.getSelectedItem() + "";
                    valorMax=acumulativosDao.buscarTotalAsigParcial(seleccionSeccion.getId(),seleccionAsignatura.getId(),parcial);

                    exite=true;

                }
            }
        }

        if(exite)
            return valorMax;
        else
            return -1;







    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.it_crear:


                if( etDescripcion.getEditText().getText().toString().length() == 0 ) {

                    etDescripcion.setError("El campo descripcion no puedo quedar en vacio");
                }
                else{

                    if(sSecciones!=null && sAsignatura!=null && sTipoAcumulativo!=null ) {

                        //Toast.makeText(getApplicationContext(), "Se mandara a crear el acumulativo "+etDescripcion.getText(), Toast.LENGTH_LONG).show();

                        String parcial = sParciales.getSelectedItem() + "";
                        String acumulativoNombre = etDescripcion.getEditText().getText() + "";

                        double valor=0;

                        if(!etValor.getEditText().getText().toString().isEmpty() && etValor.getEditText().getText().toString().length() > 0)
                            valor = Double.parseDouble(etValor.getEditText().getText().toString());

                        Seccion seleccionSeccion = null;
                        if (!(sSecciones.getSelectedItem() == null)) {
                            seleccionSeccion = (Seccion) sSecciones.getSelectedItem();
                        }

                        Asignatura seleccionAsignatura = null;
                        if (!(sAsignatura.getSelectedItem() == null)) {
                            seleccionAsignatura = (Asignatura) sAsignatura.getSelectedItem();
                        }

                        TipoAcumulativo seleccionTipoAcumulativo = null;
                        if (!(sTipoAcumulativo.getSelectedItem() == null)) {
                            seleccionTipoAcumulativo = (TipoAcumulativo) sTipoAcumulativo.getSelectedItem();
                        }

                       double total=acumulativosDao.buscarTotalAsigParcial(seleccionSeccion.getId(),seleccionAsignatura.getId(),parcial);
                        if((total+valor)<=100) {

                            Intent agregarEntrada = new Intent(this, CrearAcumulativo.class);
                            agregarEntrada.putExtra("seccion", (Parcelable) seleccionSeccion);
                            agregarEntrada.putExtra("asignatura", (Parcelable) seleccionAsignatura);
                            agregarEntrada.putExtra("parcial", parcial);
                            agregarEntrada.putExtra("descripcion", acumulativoNombre);
                            agregarEntrada.putExtra("valor", valor);
                            agregarEntrada.putExtra("tipoAcumulativo", (Parcelable) seleccionTipoAcumulativo);
                            startActivityForResult(agregarEntrada, PICK_CONTACT_REQUEST);
                        }else{
                            etValor.setError("El valor maximo del acumulativo debe ser de "+(100-total));
                        }
                    }
                }
                break;
        }
        return true;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the request went well (OK) and the request was PICK_CONTACT_REQUEST
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

}
