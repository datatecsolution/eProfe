package net.profeinformatica.eprofe;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import net.profeinformatica.eprofe.adapter.AdapterAsignatura;
import net.profeinformatica.eprofe.adapter.AdapterSeccion;
import net.profeinformatica.eprofe.modelo.Acumulativo;
import net.profeinformatica.eprofe.modelo.Asignatura;
import net.profeinformatica.eprofe.modelo.Seccion;
import net.profeinformatica.eprofe.modeloDao.AcumulativosDao;
import net.profeinformatica.eprofe.modeloDao.AsignaturaDao;
import net.profeinformatica.eprofe.modeloDao.ModeloDaoBasic;
import net.profeinformatica.eprofe.modeloDao.SeccionDao;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SelectSeccionAsignatura extends AppCompatActivity {

    private Toolbar toolbar;

    private AdapterSeccion dapterSeccion;
    private Spinner spSecciones;

    private List<Seccion> seccions =new ArrayList<Seccion>();
    private Spinner spParcial;

    private Spinner spAsignatura;
    private AdapterAsignatura adapterAsignatura;
    private ArrayList<Asignatura> asignaturas=new ArrayList<Asignatura>();

    private SwipeMenuListView lvAcumulativos;
    private AdapterAcumulativo adapterAcumulativo;
    private ArrayList<Acumulativo> acumulativos=new ArrayList<Acumulativo>();

    private Acumulativo acumulativo;
    private AcumulativosDao acumulativosDao=null;
    private SeccionDao seccionDao=null;
    private AsignaturaDao asignaturaDao=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seccion_asignatura);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Crear acumulativo");

        acumulativosDao=new AcumulativosDao();

        lvAcumulativos=(SwipeMenuListView)findViewById(R.id.lvAlumnosAcumulativos);
        adapterAcumulativo=new AdapterAcumulativo(this,acumulativos);
        lvAcumulativos.setAdapter(adapterAcumulativo);

        spSecciones=(Spinner)findViewById(R.id.spSeccion);
        dapterSeccion=new AdapterSeccion(this,seccions);
        spSecciones.setAdapter(dapterSeccion);

        spSecciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Seccion seleccionSeccion;
                if(spSecciones!=null ||!(spSecciones.getSelectedItem()==null)) {
                    seleccionSeccion= (Seccion) spSecciones.getSelectedItem();
                    // System.out.println(new String("Holasfdfs" + seleccionSeccion.toString()));
                    getAsignaturas(seleccionSeccion.getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spAsignatura=(Spinner)findViewById(R.id.spAsignatura) ;
        adapterAsignatura=new AdapterAsignatura(this,asignaturas);
        spAsignatura.setAdapter(adapterAsignatura);

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



        spParcial = (Spinner)findViewById(R.id.spParcial);
        ArrayAdapter<String> spinnerCountShoesArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_parcial2, getResources().getStringArray(R.array.parciales));
        spParcial.setAdapter(spinnerCountShoesArrayAdapter);

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

        seccionDao=new SeccionDao();
        asignaturaDao=new AsignaturaDao();




        //acumulativos

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

                       boolean result= acumulativosDao.eliminar(adapterAcumulativo.getItem(position));

                        if(result) {

                            Toast.makeText(getApplicationContext(), "Se elimino el acumulativo", Toast.LENGTH_SHORT).show();
                            adapterAcumulativo.getAcumulativos().remove(index);
                            adapterAcumulativo.notifyDataSetChanged();
                        }

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

                lazarEditarAcumulativo(acumulativo);

            }


        });
    }

    private void getAcumulativo(Integer idSeccion, String parcial,Integer idAsignatura){



        List<Acumulativo> acumulativos=acumulativosDao.buscarPorToDocenteSeccionAsig(idSeccion,idAsignatura,parcial);

        if(acumulativos==null||acumulativos.isEmpty()){
            adapterAcumulativo.getAcumulativos().clear();
            adapterAcumulativo.notifyDataSetChanged();

        }else {

            //System.out.println(new String("EXITOOOOOOO:===============>"+acumulativos.get(0).toString()));
            adapterAcumulativo.setAcumulativos(acumulativos);
            adapterAcumulativo.notifyDataSetChanged();
        }

    }

    private void lazarEditarAcumulativo(Acumulativo acumulativo) {

        Intent editarAcumulativo = new Intent(this, EditarAcumulativo.class);
        editarAcumulativo.putExtra("idAcumulativo", acumulativo.getMovilId());
        startActivity(editarAcumulativo);
    }

    public void getAsignaturas(int seccion_id){
        adapterAsignatura.getAsignaturas().clear();
        adapterAsignatura.clear();

        List<Asignatura> asignaturas1=asignaturaDao.buscarPorSeccionDocente(seccion_id);

        if(asignaturas1!=null){
            adapterAsignatura.setAsignaturas(asignaturas1);
            adapterAsignatura.notifyDataSetChanged();
        }

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
        dapterSeccion.getSecciones().clear();
        dapterSeccion.clear();

        List<Seccion> seccions1 =seccionDao.buscarPorDocente(ModeloDaoBasic.getDocente());


        if(seccions1!=null) {
            dapterSeccion.setSecciones(seccions1);
            dapterSeccion.notifyDataSetChanged();

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


    }
}
