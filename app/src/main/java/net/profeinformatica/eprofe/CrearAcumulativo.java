package net.profeinformatica.eprofe;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.profeinformatica.eprofe.adapter.AdapterAlumnoAcumulativo;
import net.profeinformatica.eprofe.modelo.Acumulativo;
import net.profeinformatica.eprofe.modelo.Asignatura;
import net.profeinformatica.eprofe.modelo.Matricula;
import net.profeinformatica.eprofe.modelo.NotaAcumulativo;
import net.profeinformatica.eprofe.modelo.Seccion;
import net.profeinformatica.eprofe.modelo.TipoAcumulativo;
import net.profeinformatica.eprofe.modeloDao.AcumulativosDao;
import net.profeinformatica.eprofe.modeloDao.MatriculaDao;
import net.profeinformatica.eprofe.modeloDao.NotaAcumulativosDao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CrearAcumulativo extends AppCompatActivity {

    private Acumulativo acumulativo;

    private AdapterAlumnoAcumulativo adapterAlumnoAcumulativo;
    private ListView lvMatricula;

    private SimpleDateFormat dateFormatter3 = new SimpleDateFormat("yyy");
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyy-MM-dd");
    private String fecha;
    private Toolbar toolbar;

    private ListView lvAcumulativo;
    private TextView tvAcumulativo;
    private TextView tvAsignatura;
    private TextView tvTipoTarea;
    private MatriculaDao matriculaDao=null;
    private AcumulativosDao acumulativosDao=null;
    private NotaAcumulativosDao notaAcumulativosDao=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_acumulativo);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        acumulativo=new Acumulativo();
        matriculaDao=new MatriculaDao();
        acumulativosDao=new AcumulativosDao();
        notaAcumulativosDao=new NotaAcumulativosDao();

        Intent intent = getIntent();

        lvMatricula=(ListView)findViewById(R.id.lvAlumnosAcumulativos);
        adapterAlumnoAcumulativo=new AdapterAlumnoAcumulativo(this,acumulativo.getNotasAcumulativos());
        lvMatricula.setAdapter(adapterAlumnoAcumulativo);

        tvAsignatura=(TextView)findViewById(R.id.tvAsignatura);
        tvAcumulativo=(TextView)findViewById(R.id.tvAcumulativo);
        tvTipoTarea=(TextView)findViewById(R.id.tvTipoTarea);

        acumulativo.setSeccion((Seccion) intent.getParcelableExtra("seccion"));
        acumulativo.setAsignatura((Asignatura) intent.getParcelableExtra("asignatura"));
        acumulativo.setTipoAcumulativo((TipoAcumulativo) intent.getParcelableExtra("tipoAcumulativo"));
        acumulativo.setDescripcion(intent.getExtras().getString("descripcion"));
        acumulativo.setParcial(intent.getExtras().getString("parcial"));
        acumulativo.setValor(intent.getExtras().getDouble("valor"));
        adapterAlumnoAcumulativo.setValor(acumulativo.getValor());
        acumulativo.setFecha(dateFormatter.format(Calendar.getInstance().getTime()));

        editarTitulo();
        adapterAlumnoAcumulativo.setValor(acumulativo.getValor());


        String year= dateFormatter3.format(Calendar.getInstance().getTime());




        final ProgressDialog progressDoalog2;
        progressDoalog2 = new ProgressDialog(this);
        progressDoalog2.setMax(100);
        progressDoalog2.show();

        if(acumulativo.getSeccion()!=null){

            //se buscan los alumnos por la matricula de la seccion
            List<Matricula> matriculas= matriculaDao.buscarPorSeccion(acumulativo.getSeccion());

            if(matriculas!=null) {
                for (int x = 0; x < matriculas.size(); x++) {
                    NotaAcumulativo unDetalle = new NotaAcumulativo();
                    unDetalle.setAlumno(matriculas.get(x).getAlumno());
                    unDetalle.setAlumnoId(matriculas.get(x).getAlumno().getId());
                    acumulativo.getNotasAcumulativos().add(unDetalle);
                }
                adapterAlumnoAcumulativo.notifyDataSetChanged();
                progressDoalog2.dismiss();
            }

        }

    }

    private void editarTitulo() {
        tvAcumulativo.setText(acumulativo.getDescripcion()+" / "+acumulativo.getValor()+"%"+" - "+acumulativo.getAsignatura().getNombre());
        tvTipoTarea.setText(acumulativo.getTipoAcumulativo().getDescripcion());
        //tvSeccion.setText(acumulativo.getSeccion().getSeccionSort());
        tvAsignatura.setText(" Parcial "+acumulativo.getParcial()+"-"+acumulativo.getSeccion().getSeccionSort());
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


                if(adapterAlumnoAcumulativo.getDetalles().size()>0) {

                    acumulativo.setTipoAcumulativoId(acumulativo.getTipoAcumulativo().getId());
                    acumulativo.setSeccionId(acumulativo.getSeccion().getId());
                    acumulativo.setAsignaturaId(acumulativo.getAsignatura().getId());
                   // System.out.println("ANTESSSS=>>>>>>"+acumulativo.toString());

                   // acumulativo.setNotasAcumulativos(detalles);


                    final ProgressDialog progressDoalog2;
                    progressDoalog2 = new ProgressDialog(this);
                    progressDoalog2.setMax(100);
                    progressDoalog2.setMessage("guardando....");
                    //progressDoalog.setTitle("ProgressDialog bar example");
                    progressDoalog2.show();


                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String date = df.format(Calendar.getInstance().getTime());

                    acumulativo.setUpdatedAt(date);
                    acumulativo.setCreatedAt(date);
                    //se establece que se necesita actualizar en el servidor
                    acumulativo.setSicronizadoServidor(2);



                    if(acumulativosDao.registrar(acumulativo)){
                        List<NotaAcumulativo> notas=adapterAlumnoAcumulativo.getDetalles();

                        for (int x=0; x<notas.size();x++){

                            notas.get(x).setUpdatedAt(date);
                            notas.get(x).setCreatedAt(date);
                            //se estrablece el codigo del acumulativo registrado arriba
                            notas.get(x).setAcumulativoMovilId(acumulativo.getMovilId());

                            //se establece que se necesita actualizar en el servidor
                            notas.get(x).setSicronizadoServidor(2);

                            notaAcumulativosDao.registrar(notas.get(x));

                        }
                        progressDoalog2.dismiss();
                        Toast.makeText(getApplicationContext(), "Se registro el acumulativo", Toast.LENGTH_SHORT).show();

                        Intent returnIntent=new Intent();
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    }


                }else{
                    Toast.makeText(getApplicationContext(), "Error: No hay alumnos matriculados para pasar asistencia", Toast.LENGTH_SHORT).show();
                }


                break;
        }
        return true;
    }



}
