package net.profeinformatica.eprofe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;


import net.profeinformatica.eprofe.adapter.AdapterAcumulativo;
import net.profeinformatica.eprofe.adapter.AdapterAsignaturaBuscar;
import net.profeinformatica.eprofe.adapter.AdapterSeccionSimple;
import net.profeinformatica.eprofe.modelo.Acumulativo;
import net.profeinformatica.eprofe.modelo.Asignatura;
import net.profeinformatica.eprofe.modelo.Seccion;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiService;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListaAcumulativos extends AppCompatActivity {

    private Toolbar toolbar;
    private SwipeMenuListView lvAcumulativos;
    private AdapterAcumulativo adapterAcumulativo;
    private ArrayList<Acumulativo> acumulativos=new ArrayList<Acumulativo>();
    private ApiService mAPIService;
    private Acumulativo acumulativo;
    private AdapterSeccionSimple adapterSeccion;
    private Spinner spSecciones;
    private List<Seccion> seccions =new ArrayList<Seccion>();
    private Spinner spParcial;

    private Spinner spAsignatura;
    private AdapterAsignaturaBuscar adapterAsignatura;
    private ArrayList<Asignatura> asignaturas=new ArrayList<Asignatura>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_acumulativos);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAPIService = ApiUtils.getApiService();

        lvAcumulativos=(SwipeMenuListView)findViewById(R.id.lvAlumnosAcumulativos);
        adapterAcumulativo=new AdapterAcumulativo(this,acumulativos);
        lvAcumulativos.setAdapter(adapterAcumulativo);

        spSecciones=(Spinner)findViewById(R.id.spSeccion);
        adapterSeccion=new AdapterSeccionSimple(this,seccions);
        spSecciones.setAdapter(adapterSeccion);

        spParcial=(Spinner)findViewById(R.id.spParcial);
        ArrayAdapter<String> spinnerCountShoesArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_parcial, getResources().getStringArray(R.array.parciales));
        spParcial.setAdapter(spinnerCountShoesArrayAdapter);

        spAsignatura=(Spinner)findViewById(R.id.spAsignatura) ;
        adapterAsignatura=new AdapterAsignaturaBuscar(this,asignaturas);
        spAsignatura.setAdapter(adapterAsignatura);






        //se escucha la seleccion de la seccion
        spSecciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //int codigo= sSecciones.getSelectedItemPosition();

                System.out.println(new String("CLICK en SECCION:===============>"));

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

        spAsignatura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



                Seccion seleccionSeccion;
                if(!(spSecciones.getSelectedItem()==null)) {
                    seleccionSeccion= (Seccion) spSecciones.getSelectedItem();

                    Asignatura seleccionAsignatura;
                    if(!(spAsignatura.getSelectedItem()==null)) {



                        seleccionAsignatura= (Asignatura) spAsignatura.getSelectedItem();
                        getAcumulativo(seleccionSeccion.getId(), spParcial.getSelectedItem() + "",seleccionAsignatura.getId());
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //se escucha la seleccion de la seccion
        spParcial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //int codigo= sSecciones.getSelectedItemPosition();

                Seccion seleccionSeccion;
                if(!(spSecciones.getSelectedItem()==null)) {
                    seleccionSeccion= (Seccion) spSecciones.getSelectedItem();

                    Asignatura seleccionAsignatura;
                    if(!(spAsignatura.getSelectedItem()==null)) {



                        seleccionAsignatura= (Asignatura) spAsignatura.getSelectedItem();
                        getAcumulativo(seleccionSeccion.getId(), spParcial.getSelectedItem() + "",seleccionAsignatura.getId());
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //se crea las opciones para el swiper de la lista de articulos
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(255,
                        0, 0)));
                //deleteItem.setBackground(new ColorDrawable(Color.rgb()));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_action_delete_dark);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        lvAcumulativos.setMenuCreator(creator);

        //controlardor de evento en los botones swiperMenu
        lvAcumulativos.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, final int index) {
                switch (index) {
                    case 0:
                        // adapter.getItems().remove(index);
                        //adapter.notifyDataSetChanged();
                        mAPIService.deleteAcumulativo(adapterAcumulativo.getItem(position).getId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Acumulativo>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(Acumulativo acumulativo) {

                                        Toast.makeText(getApplicationContext(), "Se elimino la asistencia", Toast.LENGTH_SHORT).show();
                                        adapterAcumulativo.getAcumulativos().remove(index);
                                        adapterAcumulativo.notifyDataSetChanged();

                                    }
                                });

                        //Log.d(TAG, "onMenuItemClick: clicked item " + index);
                        break;
                    case 1:
                        //Log.d(TAG, "onMenuItemClick: clicked item " + index);
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });


        lvAcumulativos.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                acumulativo=adapterAcumulativo.getAcumulativos().get(position);
                //System.out.println(new String("Abrir:===============>"+adapterAsistencia.getAsistencias().get(position).toString()));

                lazarEditarAcumulativo(acumulativo);

            }


        });
    }

    public void getAsignaturas(int seccion_id){
        // int sDocenteId=R.integer.docente_id;
        //se extrae de las preferencias el codigo del docente almacenado
        SharedPreferences pref = getApplicationContext().getSharedPreferences("prefeDocenteLocal", 0); // 0 - for private mode
        Integer codigo= pref.getInt("docente_codigo", -1); // getting Integer

        mAPIService.getAsignaturasDocente(codigo,seccion_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Asignatura>>() {
                    @Override
                    public void onCompleted() {

                        System.out.println(new String("Exitooooo en ASIGNATURA:===============>"));
                        Seccion seleccionSeccion;
                        if(!(spSecciones.getSelectedItem()==null)) {
                            seleccionSeccion= (Seccion) spSecciones.getSelectedItem();

                            Asignatura seleccionAsignatura;
                            if(!(spAsignatura.getSelectedItem()==null)) {

                                seleccionAsignatura= (Asignatura) spAsignatura.getSelectedItem();
                                getAcumulativo(seleccionSeccion.getId(), spParcial.getSelectedItem() + "",seleccionAsignatura.getId());
                            }

                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(new String("ERROR:===============>"+e.getMessage()));
                    }

                    @Override
                    public void onNext(List<Asignatura> as) {
                        adapterAsignatura.setAsignaturas(as);

                        adapterAsignatura.notifyDataSetChanged();


                    }
                });
    }

    private void getAcumulativo(Integer idSeccion, String parcial,Integer idAsignatura){
        //se extrae de las preferencias el codigo del docente almacenado
        SharedPreferences pref = getApplicationContext().getSharedPreferences("prefeDocenteLocal", 0); // 0 - for private mode
        Integer codigo= pref.getInt("docente_codigo", -1); // getting Integer

        mAPIService.getAcumulativosSeccionParcial(codigo,idSeccion,parcial,idAsignatura)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Acumulativo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(new String("ERRORRRRR ACUMULATIVO:===============>" +e.getMessage()));
                        adapterAcumulativo.getAcumulativos().clear();
                        adapterAcumulativo.notifyDataSetChanged();

                    }

                    @Override
                    public void onNext(List<Acumulativo> acumulativos) {


                        System.out.println(new String("Exitooooo en ACUMULATIVO:===============>"));

                        if(acumulativos.isEmpty()){
                            adapterAcumulativo.getAcumulativos().clear();
                            adapterAcumulativo.notifyDataSetChanged();

                        }else {

                            //System.out.println(new String("EXITOOOOOOO:===============>"+acumulativos.get(0).toString()));
                            adapterAcumulativo.setAcumulativos(acumulativos);
                            adapterAcumulativo.notifyDataSetChanged();
                            //adapterAsistencia.setAsistencias(encabezadoAsistencias);
                            //adapterAsistencia.notifyDataSetChanged();
                        }

                    }
                });

    }

    private void lazarEditarAcumulativo(Acumulativo acumulativo) {

        Intent editarAcumulativo = new Intent(this, EditarAcumulativo.class);
        editarAcumulativo.putExtra("idAcumulativo", acumulativo.getId());
        startActivity(editarAcumulativo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.it_agregar:
               Intent agregarAcumulativo=new Intent(this,SeleccionarAcumulativo.class);
                startActivity(agregarAcumulativo);
               // Toast.makeText(this,"Lanzar la actividad de crear acumulativos",Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.m_lista_asistencia, menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                return onOptionsItemSelected(item);
            }
        });



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...


        //se extrae de las preferencias el codigo del docente almacenado
        SharedPreferences pref = getApplicationContext().getSharedPreferences("prefeDocenteLocal", 0); // 0 - for private mode
        Integer codigo= pref.getInt("docente_codigo", -1); // getting Integer

        mAPIService.getSecciones(codigo).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Seccion>>() {
                    @Override
                    public void onCompleted() {
                        Seccion seleccionSeccion;
                        if(!(spSecciones.getSelectedItem()==null)) {
                            seleccionSeccion= (Seccion) spSecciones.getSelectedItem();
                            getAsignaturas(seleccionSeccion.getId());



                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Seccion> seccions) {

                        adapterSeccion.setSecciones(seccions);

                        System.out.println(new String("Exitooooo en SECCIONES:===============>"));




                        adapterSeccion.notifyDataSetChanged();





                    }
                });


    }

}
