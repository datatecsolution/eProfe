package net.profeinformatica.eprofe;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import net.profeinformatica.eprofe.adapter.AdapterAlumnoAsistencia;
import net.profeinformatica.eprofe.modelo.Asignatura;
import net.profeinformatica.eprofe.modelo.DetalleAsistencia;
import net.profeinformatica.eprofe.modelo.EncabezadoAsistencia;
import net.profeinformatica.eprofe.modelo.Matricula;
import net.profeinformatica.eprofe.modelo.Seccion;
import net.profeinformatica.eprofe.modeloDao.DetalleAsistenciaDao;
import net.profeinformatica.eprofe.modeloDao.EncabezadoAsistenciaDao;
import net.profeinformatica.eprofe.modeloDao.MatriculaDao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CrearAsistencia extends AppCompatActivity {

   // private ApiService mAPIService;

    private EncabezadoAsistencia encabezadoAsistencia=new EncabezadoAsistencia();
    private ArrayList<Matricula> matriculas=new ArrayList<Matricula>();
    private ArrayList<DetalleAsistencia> detalles=new ArrayList<DetalleAsistencia>();

    private AdapterAlumnoAsistencia adapterAlumno;

    private ListView lvMatricula;
    private Toolbar toolbar;

    private Date fecha;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyy-MM-dd");
    private SimpleDateFormat dateFormatter2 = new SimpleDateFormat("EEE dd, MM/yyy");
    private SimpleDateFormat dateFormatter3 = new SimpleDateFormat("yyy");
    private TextView tvSeccion;
    private TextView tvAsignatura;
    private TextView tvFecha;
    //private Switch swExcusa

    private MatriculaDao matriculaDao=null;
    private EncabezadoAsistenciaDao encabezadoAsistenciaDao=null;
    private DetalleAsistenciaDao detalleAsistenciaDao=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_asistencia);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //mAPIService = ApiUtils.getApiService();

        matriculaDao=new MatriculaDao();
        encabezadoAsistenciaDao=new EncabezadoAsistenciaDao();
        detalleAsistenciaDao=new DetalleAsistenciaDao();

        tvSeccion=(TextView)findViewById(R.id.tvSeccion);
        tvAsignatura=(TextView)findViewById(R.id.tvAsignatura);
        tvFecha=(TextView)findViewById(R.id.tvFecha);
        lvMatricula=(ListView)findViewById(R.id.lvAlumnos);
        adapterAlumno=new AdapterAlumnoAsistencia(this,detalles);
        lvMatricula.setAdapter(adapterAlumno);

        Intent intent = getIntent();


        Seccion seccion =(Seccion) intent.getParcelableExtra("seccion");
        Asignatura asignatura=(Asignatura) intent.getParcelableExtra("asignatura");

        if(seccion!=null || asignatura!=null) {

            encabezadoAsistencia.setSeccion(seccion);
            encabezadoAsistencia.setAsignatura(asignatura);



            String getFecha=intent.getExtras().getString("fecha");

            try {
                fecha = dateFormatter.parse(getFecha);
                encabezadoAsistencia.setFecha(dateFormatter.format(fecha));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // String fecha=intent.getExtras().getInt("day")+"/"+intent.getExtras().getInt("month")+"/"+intent.getExtras().getInt("year");
            //getSupportActionBar().setTitle(encabezadoAsistencia.getAsignatura().getNombre()+ " || "+dateFormatter2.format(fecha));
            tvSeccion.setText(encabezadoAsistencia.getSeccion().getSeccionSort());
            tvAsignatura.setText(encabezadoAsistencia.getAsignatura().getNombre());
            tvFecha.setText(dateFormatter2.format(fecha));

        String year=dateFormatter3.format(fecha);

        final ProgressDialog progressDoalog2;
        progressDoalog2 = new ProgressDialog(this);
        progressDoalog2.setMax(100);
        progressDoalog2.show();


            //se buscan los alumnos por la matricula de la seccion
            List<Matricula> matriculas= matriculaDao.buscarPorSeccion(seccion);

            if(matriculas!=null) {
                for (int x = 0; x < matriculas.size(); x++) {
                    DetalleAsistencia unDetalle = new DetalleAsistencia();
                    unDetalle.setAlumno(matriculas.get(x).getAlumno());
                    detalles.add(unDetalle);
                }
                adapterAlumno.setDetalles(detalles);
                adapterAlumno.notifyDataSetChanged();
                progressDoalog2.dismiss();
            }


        }


    }
    public Seccion getSeccion() {
        return encabezadoAsistencia.getSeccion();
    }

    public void setSeccion(Seccion seccion) {
        this.encabezadoAsistencia.setSeccion(seccion);
    }

    public Asignatura getAsignatura() {
        return encabezadoAsistencia.getAsignatura();
    }

    public void setAsignatura(Asignatura asignatura) {
        this.encabezadoAsistencia.setAsignatura(asignatura);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.m_asistencia, menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                return onOptionsItemSelected(item);
            }
        });



        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.it_guardar:


                if(adapterAlumno.getDetalles().size()>0) {

                    //encabezadoAsistencia.setDetallesAsistencia(adapterAlumno.getDetalles());


                    final ProgressDialog progressDoalog2;
                    progressDoalog2 = new ProgressDialog(this);
                    progressDoalog2.setMax(100);
                    progressDoalog2.show();

                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date = df.format(Calendar.getInstance().getTime());

                    encabezadoAsistencia.setCreatedAt(date);
                    encabezadoAsistencia.setUpdatedAt(date);


                    if(encabezadoAsistenciaDao.registrar(encabezadoAsistencia)){
                        for (int x=0; x<adapterAlumno.getDetalles().size();x++){
                            adapterAlumno.getDetalles().get(x).setEncabezadoasistenciaMovilId(encabezadoAsistencia.getMovilId());
                            adapterAlumno.getDetalles().get(x).setCreatedAt(date);
                            adapterAlumno.getDetalles().get(x).setUpdatedAt(date);
                            detalleAsistenciaDao.registrar(adapterAlumno.getDetalles().get(x));
                        }

                        Toast.makeText(getApplicationContext(), "Se guardo la asistencia", Toast.LENGTH_SHORT).show();

                        progressDoalog2.dismiss();
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    }


                    /*

                    mAPIService.saveEncabezadoAsistencia(encabezadoAsistencia)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<EncabezadoAsistencia>() {
                                @Override
                                public void onCompleted() {
                                    progressDoalog2.dismiss();

                                }

                                @Override
                                public void onError(Throwable e) {
                                    progressDoalog2.dismiss();

                                    System.out.println("ERROR:========>>>>"+e.getMessage()+e.toString());
                                    Toast.makeText(getApplicationContext(), "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onNext(EncabezadoAsistencia encabezadoAsistencia) {
                                    //System.out.println(encabezadoAsistencia.toString());
                                   // guardarDetalles(encabezadoAsistencia);

                                    Toast.makeText(getApplicationContext(), "Se guardo la asistencia", Toast.LENGTH_SHORT).show();

                                    Intent returnIntent = new Intent();
                                    setResult(Activity.RESULT_OK, returnIntent);
                                    finish();
                                }


                            });

                     */
                }else{
                    Toast.makeText(getApplicationContext(), "Error: No hay alumnos matriculados para pasar asistencia", Toast.LENGTH_SHORT).show();
                }


                break;
        }
        return true;
    }

    /*

    private void guardarDetalles(EncabezadoAsistencia encabezadoAsistencia) {

        for(int x=0;x<detalles.size();x++){
            //System.out.println(detalles.get(x).toString());
            mAPIService.saveDetalleAsistencia(detalles.get(x).getAlumno().getId(),encabezadoAsistencia.getId(),detalles.get(x).isEstado())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<DetalleAsistencia>() {
                        @Override
                        public void onCompleted() {


                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(DetalleAsistencia detalleAsistencia) {

                          //  System.out.println("se guardo||||"+detalleAsistencia.toString());
                            Intent returnIntent = new Intent();
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();

                        }


                    });
        }
    }*/

    /*private void lanzarLista() {
        Intent listaAsistencia=new Intent(this,ListAsistencia.class);
        startActivity(listaAsistencia);
    }*/


}
