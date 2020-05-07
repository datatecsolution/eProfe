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
import android.widget.Spinner;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import net.profeinformatica.eprofe.adapter.AdapterAsistencia;
import net.profeinformatica.eprofe.adapter.AdapterSeccion;
import net.profeinformatica.eprofe.modelo.EncabezadoAsistencia;
import net.profeinformatica.eprofe.modelo.Seccion;
import net.profeinformatica.eprofe.modeloDao.EncabezadoAsistenciaDao;
import net.profeinformatica.eprofe.modeloDao.ModeloDaoBasic;
import net.profeinformatica.eprofe.modeloDao.SeccionDao;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ListAsistencia extends AppCompatActivity {

   // private ApiService mAPIService;
    private Toolbar toolbar;
    private AdapterAsistencia adapterAsistencia;
    private SwipeMenuListView lvAsistencia;
    private ArrayList<EncabezadoAsistencia> asistencias=new ArrayList<EncabezadoAsistencia>();
    private EncabezadoAsistencia encabezadoAsistecia;
    private AdapterSeccion adapterSeccion;
    private ArrayList<Seccion> seccions=new ArrayList<Seccion>();
    private  Spinner sSecciones=null;
    private SeccionDao seccionDao=null;
    private EncabezadoAsistenciaDao encabezadoAsistenciaDao=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_asistencia);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sSecciones=(Spinner)findViewById(R.id.sSeccionesBuscar);
        adapterSeccion=new AdapterSeccion(this,seccions);
        sSecciones.setAdapter(adapterSeccion);

        seccionDao=new SeccionDao();
        encabezadoAsistenciaDao=new EncabezadoAsistenciaDao();


        sSecciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //int codigo= sSecciones.getSelectedItemPosition();

                // System.out.println(new String("Holasfdfs" + codigo));

                Seccion seleccionSeccion;
                if(!(sSecciones.getSelectedItem()==null)) {
                    seleccionSeccion= (Seccion) sSecciones.getSelectedItem();
                    // System.out.println(new String("Holasfdfs" + seleccionSeccion.toString()));
                    getAsistencias(seleccionSeccion.getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //mAPIService = ApiUtils.getApiService();


        lvAsistencia=(SwipeMenuListView)findViewById(R.id.lvAsistencias);
        adapterAsistencia=new AdapterAsistencia(this,asistencias);
        lvAsistencia.setAdapter(adapterAsistencia);

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
        lvAsistencia.setMenuCreator(creator);

        lvAsistencia.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, final int index) {
                switch (index) {
                    case 0:

                        encabezadoAsistenciaDao.eliminar(adapterAsistencia.getItem(position));

                        Seccion seleccionSeccion;
                        if(!(sSecciones.getSelectedItem()==null)) {


                            seleccionSeccion = (Seccion) sSecciones.getSelectedItem();
                            getAsistencias(seleccionSeccion.getId());

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

        lvAsistencia.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
               encabezadoAsistecia=adapterAsistencia.getAsistencias().get(position);
                System.out.println(new String("Abrir:===============>"+adapterAsistencia.getAsistencias().get(position).toString()));

               lazarEditarExistencia(encabezadoAsistecia);

            }


        });











    }
    @Override
    public void onResume(){
        super.onResume();
        // put your code here...



        List<Seccion> seccions1 =seccionDao.buscarPorDocente(ModeloDaoBasic.getDocente());



        if(seccions1!=null) {

            adapterSeccion.setSecciones(seccions1);
            adapterSeccion.notifyDataSetChanged();


            Seccion seleccionSeccion;
            if(!(sSecciones.getSelectedItem()==null)) {


                seleccionSeccion = (Seccion) sSecciones.getSelectedItem();
                getAsistencias(seleccionSeccion.getId());

            }
        }


    }

    private void getAsistencias(Integer idSeccion) {
        adapterAsistencia.getAsistencias().clear();
        adapterAsistencia.notifyDataSetChanged();

        List<EncabezadoAsistencia> encabezados=encabezadoAsistenciaDao.buscarPorSeccionDocente(idSeccion);

        if(encabezados!=null && !encabezados.isEmpty()) {
            adapterAsistencia.setAsistencias(encabezados);
        }else{
            adapterAsistencia.getAsistencias().clear();
        }
        adapterAsistencia.notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.it_agregar:
                Intent agregarAsistencia=new Intent(this,SeleccionarAsistencia.class);
                startActivity(agregarAsistencia);
                break;

        }
        return true;
    }
    private void lazarEditarExistencia(EncabezadoAsistencia encabezado) {
        //System.out.println(new String("Se dio clip en el boton crear"+strDate));
        Intent editarAsistencia = new Intent(this, EditarAsistencia.class);
       // agregarEntrada.putExtra("IdEncabezado", encabezadoAsistecia.getId());
        editarAsistencia.putExtra("idEncabezado", encabezado.getMovilId());
        startActivity(editarAsistencia);
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

}
