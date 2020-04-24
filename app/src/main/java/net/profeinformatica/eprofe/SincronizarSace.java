package net.profeinformatica.eprofe;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import net.profeinformatica.eprofe.adapter.AdapterSeccionCentroPeriodo;
import net.profeinformatica.eprofe.modelo.Seccion;
import net.profeinformatica.eprofe.modeloDao.ModeloDaoBasic;
import net.profeinformatica.eprofe.modeloDao.SeccionDao;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiService;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SincronizarSace extends AppCompatActivity {

    private Toolbar toolbar;
    private AdapterSeccionCentroPeriodo adapterSeccionCentroPeriodo;

    private ApiService mAPIService;
    private ListView lvSecciones;
    private Button btnSincronizarBD;
    private Button btnSincronizarSace;
    private List<Seccion> secciones=new ArrayList<Seccion>();
    private SeccionDao seccionDao=null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sincronizar_sace);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvSecciones =(ListView)findViewById(R.id.lvSecciones);
        btnSincronizarBD=(Button)findViewById(R.id.btnSincronizarBD);
        btnSincronizarSace=(Button)findViewById(R.id.btnSincronizarSACE);


        adapterSeccionCentroPeriodo=new AdapterSeccionCentroPeriodo(this,secciones);


        lvSecciones.setAdapter(adapterSeccionCentroPeriodo);

        mAPIService = ApiUtils.getApiService();
        seccionDao=new SeccionDao();


        btnSincronizarBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Se sincronizara la base de datos con el servidor... ", Toast.LENGTH_SHORT).show();
                seccionDao.sincronizarServidor(ModeloDaoBasic.getDocente());

            }
        });
        btnSincronizarSace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Se conectara a SACE para extraer sus alumnos", Toast.LENGTH_SHORT).show();

            }
        });




    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("prefeDocenteLocal", 0); // 0 - for private mode
        Integer codigo= pref.getInt("docente_codigo", -1); // getting Integer


        switch (item.getItemId()) {
            case R.id.it_sincronizar:

                // Set up progress before call
                final ProgressDialog progressDoalog;
                progressDoalog = new ProgressDialog(this);
                progressDoalog.setMax(100);
                progressDoalog.setMessage("Se esta descargando sus datos del SACE....");
                //progressDoalog.setTitle("ProgressDialog bar example");
                progressDoalog.show();


                mAPIService.sicronizarSace(codigo)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<Seccion>>() {
                            @Override
                            public void onCompleted() {
                                progressDoalog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {

                                System.out.println(new String("ERROR:===============>"+e.getLocalizedMessage()));
                                progressDoalog.dismiss();

                            }

                            @Override
                            public void onNext(List<Seccion> secciones) {

                                //System.out.println(new String("EXITOOOOOOO:===============>"+secciones.get(0).toString()));

                                adapterSeccionCentroPeriodo.setSecciones(secciones);
                                adapterSeccionCentroPeriodo.notifyDataSetChanged();

                            }
                        });

                break;
            case R.id.it_guardar:




                // Set up progress before call
                final ProgressDialog progressDoalog2;
                progressDoalog2 = new ProgressDialog(this);
                progressDoalog2.setMax(100);
                progressDoalog2.setMessage("actualizando....");
                //progressDoalog.setTitle("ProgressDialog bar example");
                progressDoalog2.show();

                // Convert the object to a JSON string
               //String json = new Gson().toJson(adapterSeccionCentroPeriodo.getSecciones());
               //System.out.println(""+json);
                //adapterSeccionCentroPeriodo.getSecciones().get(0).setPeriodoId(2);

                mAPIService.updateSecciones(adapterSeccionCentroPeriodo.getSecciones())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Seccion>() {
                            @Override
                            public void onCompleted() {
                                progressDoalog2.dismiss();
                                Toast.makeText(getApplicationContext(), "Se actualizo las secciones", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onError(Throwable e) {
                                progressDoalog2.dismiss();

                                if (e instanceof HttpException) {

                                    HttpException response = (HttpException)e;

                                    int code = response.code();
                                    System.out.println(new String("ERROR:===============>"+response.message()));

                                }


                            }

                            @Override
                            public void onNext(Seccion s) {

                               // System.out.println(new String("EXITOOOOOOO:===============>"+s));



                            }


                        });
                break;

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.m_sincronizar_sace, menu);

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


        List<Seccion> seccions1 =seccionDao.buscarPorDocente(ModeloDaoBasic.getDocente());

        if(seccions1!=null) {

            adapterSeccionCentroPeriodo.setSecciones(seccions1);

            adapterSeccionCentroPeriodo.notifyDataSetChanged();
        }

        /*
        //se extrae de las preferencias el codigo del docente almacenado
        SharedPreferences pref = getApplicationContext().getSharedPreferences("prefeDocenteLocal", 0); // 0 - for private mode
        Integer codigo= pref.getInt("docente_codigo", -1); // getting Integer
        //String codigoSace=pref.getString("docente_codigo_sace","NA");

        mAPIService.getSecciones(codigo).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Seccion>>() {
                    @Override
                    public void onCompleted() {




                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println(new String("error========>>>>>" + e.getMessage()));

                    }

                    @Override
                    public void onNext(List<Seccion> seccions) {

                        adapterSeccionCentroPeriodo.setSecciones(seccions);




                        adapterSeccionCentroPeriodo.notifyDataSetChanged();



                    }
                });

         */


    }

}
