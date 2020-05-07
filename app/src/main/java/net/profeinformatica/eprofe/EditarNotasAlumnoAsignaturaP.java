package net.profeinformatica.eprofe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.profeinformatica.eprofe.adapter.AdapterAcumulativoNota;
import net.profeinformatica.eprofe.modelo.Alumno;
import net.profeinformatica.eprofe.modelo.Asignatura;
import net.profeinformatica.eprofe.modelo.Matricula;
import net.profeinformatica.eprofe.modelo.NotaAcumulativo;
import net.profeinformatica.eprofe.modelo.Seccion;
import net.profeinformatica.eprofe.modeloDao.MatriculaDao;
import net.profeinformatica.eprofe.modeloDao.NotaAcumulativosDao;
import net.profeinformatica.eprofe.modeloDao.SeccionDao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditarNotasAlumnoAsignaturaP extends AppCompatActivity {

    private Asignatura asignatura;
    private String parcial;
    private SimpleDateFormat dateFormatter3 = new SimpleDateFormat("yyy");

    //private MaterialTextView tvNombreAlumno;
    private ListView lsNotas;
    private TextView tvAsignatura;
    private TextView tvParcial;
    private LinearLayout clPrincipal;
    private TextView tvNombreAlumno;
    private TextView tvTotalNota;
    private ImageView ivAvatarAlumno;
    private Toolbar toolbar;


    private MatriculaDao matriculaDao=null;
    private NotaAcumulativosDao notaAcumulativosDao=null;
    private SeccionDao seccionDao=null;


    private Integer posicionNavegacionAlumno=0;
    private List<Alumno> alumnos=new ArrayList<Alumno>();
    private List<NotaAcumulativo> notaAcumulativos=new ArrayList<NotaAcumulativo>();
    private Seccion selecSeccionActivityAnterio;
    private AdapterAcumulativoNota adapterAcumulativoNota;


    private Animation animation;//= AnimationUtils.loadAnimation(this, R.anim.swing_up_left);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_notas_alumno_asignatura_p);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvNombreAlumno=(TextView)findViewById(R.id.tvNombreAlumno);
        tvTotalNota=(TextView)findViewById(R.id.tvTotalNota);
        ivAvatarAlumno=(ImageView)findViewById(R.id.ivAvatarAlumno);

        notaAcumulativosDao=new NotaAcumulativosDao();
        matriculaDao=new MatriculaDao();
        seccionDao=new SeccionDao();

        animation = AnimationUtils.loadAnimation(this, R.anim.swing_up_left);


       // tvNombreAlumno=(MaterialTextView)findViewById(R.id.tvNombreAlumno);




        /* se capturar los datos enviados dese la otra activity

         */

        Intent intent = getIntent();
        asignatura=(Asignatura) intent.getParcelableExtra("asignatura");
        parcial=intent.getExtras().getString("parcial");
        Integer idSeccion=intent.getExtras().getInt("idSeccion");


        String year= dateFormatter3.format(Calendar.getInstance().getTime());

        selecSeccionActivityAnterio=seccionDao.buscarPorId(idSeccion);

        /*
            Fin de capturas los datos de la otra activity
         */




        adapterAcumulativoNota = new AdapterAcumulativoNota(this,notaAcumulativos );
        adapterAcumulativoNota.setEtTotal(tvTotalNota);

        if(getDatosMatriculaOnBD()){
            getDatosNotasAlumnoOnBD();

        }

        clPrincipal=(LinearLayout) findViewById(R.id.lyEditarNota);
        lsNotas=(ListView)findViewById(R.id.lvAcumEditar2);
        lsNotas.setAdapter(adapterAcumulativoNota);

        tvAsignatura=(TextView)findViewById(R.id.tvAsignatura);
        tvParcial=(TextView)findViewById(R.id.tvParcial2);
        setTitulo();



        clPrincipal.setOnTouchListener(new OnSwipeTouchListener(this){
            public void onSwipeTop() {
                //  Toast.makeText(EditarAcumulativoAsignatura.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {

                //System.out.println(new String("Clip:===============>BOTON Back"));
                posicionNavegacionAlumno--;
                if(posicionNavegacionAlumno>=0){
                    clPrincipal.startAnimation(animation);
                    getDatosNotasAlumnoOnBD();
                    adapterAcumulativoNota.notifyDataSetChanged();
                    setTitulo();
                }else{
                    posicionNavegacionAlumno=0;
                }




                //Toast.makeText(EditarAcumulativoAsignatura.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {



               // System.out.println(new String("Clip:===============>BOTON next"));

                posicionNavegacionAlumno++;
                if(posicionNavegacionAlumno<alumnos.size()){
                    clPrincipal.startAnimation(animation);

                    getDatosNotasAlumnoOnBD();
                    setTitulo();
                    adapterAcumulativoNota.notifyDataSetChanged();
                }else{
                    posicionNavegacionAlumno=alumnos.size()-1;
                    // R.id_next
                }
                // Toast.makeText(EditarAcumulativoAsignatura.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                // Toast.makeText(EditarAcumulativoAsignatura.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });


    }

    public void setTitulo(){
        tvAsignatura.setText(asignatura.getNombre());
        tvParcial.setText("Parcial "+parcial);

        tvNombreAlumno.setText(alumnos.get(posicionNavegacionAlumno).getNombre()+" "+alumnos.get(posicionNavegacionAlumno).getApellido());
        tvTotalNota.setText(""+adapterAcumulativoNota.getTotal());
    }

    private void getDatosNotasAlumnoOnBD() {


        if (alumnos != null && alumnos.size() > 0) {

            //notaAcumulativos.clear();
            adapterAcumulativoNota.getNotas().clear();
            List<NotaAcumulativo> notas = notaAcumulativosDao.buscarPorAsignSeccionAlumParcial(asignatura.getId(), parcial, selecSeccionActivityAnterio.getId(), alumnos.get(posicionNavegacionAlumno).getId());
            if (notas != null) {

                for (NotaAcumulativo not : notas
                ) {
                    adapterAcumulativoNota.getNotas().add(not);
                }

                //Se coloca la imagen del alumno
                File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File image = new File(storageDir,alumnos.get(posicionNavegacionAlumno).getRne()+".jpg");
                if (image.exists()) {


                    Bitmap bMap = BitmapFactory.decodeFile( image.getAbsolutePath());

                    Matrix mat = new Matrix();
                    mat.postRotate(90);
                    bMap = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), mat, true);

                    // ivAlumno.setImageURI(Uri.parse(imageFilePath));

                    //ivAlumno.setImageBitmap(bMap);

                    Picasso.get()
                            .load(image)
                            .resize(100, 100)
                            .centerCrop()
                            .into(ivAvatarAlumno);

                }else{
                    if(alumnos.get(posicionNavegacionAlumno).getGenero()==1)
                        ivAvatarAlumno.setImageResource(R.drawable.v_alumna);//.setImageDrawable();
                    if(alumnos.get(posicionNavegacionAlumno).getGenero()==2)
                        ivAvatarAlumno.setImageResource(R.drawable.v_alumno);
                }


            }

        }
    }

    private boolean getDatosMatriculaOnBD() {

        boolean resultado=false;

         /*Extraer de la base de datos la lista de alumnos de la seccion
            Extraer de la base de datos los acumulativos del primer alumno
         */
        List<Matricula> matriculas=matriculaDao.buscarPorSeccion(selecSeccionActivityAnterio);

        if(matriculas!=null) {

            for (int xx = 0; xx < matriculas.size(); xx++) {

                alumnos.add(matriculas.get(xx).getAlumno());

            }
            resultado=true;

        }
        return resultado;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.m_editar_nota_alum_asig, menu);

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
            case  R.id.left:

                posicionNavegacionAlumno--;
                if(posicionNavegacionAlumno>=0){
                    clPrincipal.startAnimation(animation);
                    getDatosNotasAlumnoOnBD();
                    adapterAcumulativoNota.notifyDataSetChanged();
                    setTitulo();
                }else{
                    posicionNavegacionAlumno=0;
                }



                break;
            case R.id.next:


                posicionNavegacionAlumno++;
                if(posicionNavegacionAlumno<alumnos.size()){
                    clPrincipal.startAnimation(animation);

                    getDatosNotasAlumnoOnBD();
                    setTitulo();
                    adapterAcumulativoNota.notifyDataSetChanged();
                }else{
                    posicionNavegacionAlumno=alumnos.size()-1;
                    // R.id_next
                }




                break;
        }
        return true;
    }




}
