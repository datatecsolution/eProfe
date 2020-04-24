package net.profeinformatica.eprofe;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import net.profeinformatica.eprofe.adapter.AdapterAcumulativoNota;
import net.profeinformatica.eprofe.adapter.AdapterAlumno;
import net.profeinformatica.eprofe.modelo.Alumno;
import net.profeinformatica.eprofe.modelo.Asignatura;
import net.profeinformatica.eprofe.modelo.Matricula;
import net.profeinformatica.eprofe.modelo.NotaAcumulativo;
import net.profeinformatica.eprofe.modelo.Seccion;
import net.profeinformatica.eprofe.modeloDao.MatriculaDao;
import net.profeinformatica.eprofe.modeloDao.NotaAcumulativosDao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditarAcumulativoAsignatura extends AppCompatActivity {

    private Asignatura asignatura;
    private String parcial;
    private Toolbar toolbar;

    private ListView lsNotas;
    private AdapterAcumulativoNota adapterAcumulativoNota;
    private List<NotaAcumulativo> notaAcumulativos=new ArrayList<NotaAcumulativo>();
    private List<Alumno> alumnos=new ArrayList<Alumno>();
    private SimpleDateFormat dateFormatter3 = new SimpleDateFormat("yyy");
    private Integer posicion=0;
    private AdapterAlumno adapterAlumno;//=new AdapterAlumno(this,alumnos);
    private boolean result=true;
    private TextView tvTotalNota;
    private TextView tvModalidad;
    private TextView tvAsignatura;
    private TextView tvParcial;
    private ImageView ivAlumno;
    private LinearLayout clPrincipal;

    private MatriculaDao matriculaDao=null;
    private NotaAcumulativosDao notaAcumulativosDao=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_acumulativo_asignatura);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notaAcumulativosDao=new NotaAcumulativosDao();
        matriculaDao=new MatriculaDao();



        Intent intent = getIntent();
        asignatura=(Asignatura) intent.getParcelableExtra("asignatura");
        parcial=intent.getExtras().getString("parcial");
        Integer idSeccion=intent.getExtras().getInt("idSeccion");


        String year= dateFormatter3.format(Calendar.getInstance().getTime());


        Seccion selecSeccionActivityAnterio=new Seccion();
        selecSeccionActivityAnterio.setId(idSeccion);


        List<Matricula> matriculas=matriculaDao.buscarPorSeccion(selecSeccionActivityAnterio);

        if(matriculas!=null) {

            for (int xx = 0; xx < matriculas.size(); xx++) {

                alumnos.add(matriculas.get(xx).getAlumno());

            }

            List<NotaAcumulativo> notas=notaAcumulativosDao.buscarPorAsignSeccionAlumParcial(asignatura.getId(),parcial,idSeccion,alumnos.get(posicion).getId());
            if(notas!=null) {

               // notaAcumulativos=notas;
                for (NotaAcumulativo not:notas
                     ) {
                    notaAcumulativos.add(not);

                }

            }

        }

        tvTotalNota=(TextView)findViewById(R.id.tvTotalNota);
        tvModalidad=(TextView)findViewById(R.id.tvModalidad);
        clPrincipal=(LinearLayout)findViewById(R.id.clPrincipal3);
        tvAsignatura=(TextView)findViewById(R.id.tvAsignatura);
        tvParcial=(TextView)findViewById(R.id.tvParcial2);
        ivAlumno=(ImageView)findViewById(R.id.ivAvatarAlumno);







        lsNotas=(ListView)findViewById(R.id.lvAcumulativoEditarAsignatura);

        adapterAcumulativoNota=new AdapterAcumulativoNota(this, notaAcumulativos);
        //adapterAcumulativoNota.setEtTotal(tvTotalNota);

        lsNotas.setAdapter(adapterAcumulativoNota);


        adapterAlumno=new AdapterAlumno(this,alumnos);






/*

        clPrincipal.setOnTouchListener(new OnSwipeTouchListener(this){
            public void onSwipeTop() {
              //  Toast.makeText(EditarAcumulativoAsignatura.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {

                System.out.println(new String("Clip:===============>BOTON Back"));
                posicion--;
                if(posicion>=0){

                    getAcumutivo();
                    cambiarColor();
                }else{
                    posicion=0;
                }




                //Toast.makeText(EditarAcumulativoAsignatura.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {



                System.out.println(new String("Clip:===============>BOTON next"));

                posicion++;
                if(posicion<alumnos.size()){

                    getAcumutivo();
                }else{
                    posicion=alumnos.size()-1;
                    // R.id_next
                }
               // Toast.makeText(EditarAcumulativoAsignatura.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
               // Toast.makeText(EditarAcumulativoAsignatura.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });*/



            /*
            List<NotaAcumulativo> notas=notaAcumulativosDao.buscarPorAsignSeccionAlumParcial(asignatura.getId(),parcial,asignatura.getSeccions().get(0).getId(),alumnos.get(posicion).getId());


            if(notas!=null){

                String nombres=alumnos.get(posicion).getNombre();
                String apellidos=alumnos.get(posicion).getApellido();

                // String [] arrayString = nombres.split(" ");
                // String [] arrayString2 = apellidos.split(" ");


                adapterAcumulativoNota.setNotas(notas);
                adapterAcumulativoNota.notifyDataSetChanged();





                //adapterAcumulativoNota.getNotas().clear();
                // notaAcumulativos=notas;

                System.out.println(new String("numeros de item en los acumulativos:=========================>"+notaAcumulativos.size()));



                //adapterAcumulativoNota.notifyDataSetChanged();





                tvModalidad.setText(alumnos.get(posicion).getNombre()+" "+alumnos.get(posicion).getApellido());
                tvAsignatura.setText(asignatura.getNombre());
                tvParcial.setText("Parcial "+parcial);
                tvTotalNota.setText(""+adapterAcumulativoNota.getTotal());


                //Se coloca la imagen del alumno
                File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File image = new File(storageDir,alumnos.get(posicion).getRne()+".jpg");
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
                            .into(ivAlumno);

                }else{
                    if(alumnos.get(posicion).getGenero()==1)
                        ivAlumno.setImageResource(R.drawable.v_alumna);//.setImageDrawable();
                    if(alumnos.get(posicion).getGenero()==2)
                        ivAlumno.setImageResource(R.drawable.v_alumno);
                }


            }*/




    }


    private void getAcumutivo(){


        List<NotaAcumulativo> notas=notaAcumulativosDao.buscarPorAsignSeccionAlumParcial(asignatura.getId(),parcial,asignatura.getSeccions().get(0).getId(),alumnos.get(posicion).getId());


        if(notas!=null){

            String nombres=alumnos.get(posicion).getNombre();
            String apellidos=alumnos.get(posicion).getApellido();

           // String [] arrayString = nombres.split(" ");
           // String [] arrayString2 = apellidos.split(" ");


            for(int xx=0;xx<notas.size();xx++){
                adapterAcumulativoNota.getNotas().add(notas.get(xx));

            }
            adapterAcumulativoNota.notifyDataSetChanged();





            //adapterAcumulativoNota.getNotas().clear();
           // notaAcumulativos=notas;

            System.out.println(new String("numeros de item en los acumulativos:=========================>"+notaAcumulativos.size()));



            //adapterAcumulativoNota.notifyDataSetChanged();





            tvModalidad.setText(alumnos.get(posicion).getNombre()+" "+alumnos.get(posicion).getApellido());
            tvAsignatura.setText(asignatura.getNombre());
            tvParcial.setText("Parcial "+parcial);
            tvTotalNota.setText(""+adapterAcumulativoNota.getTotal());


            //Se coloca la imagen del alumno
            File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = new File(storageDir,alumnos.get(posicion).getRne()+".jpg");
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
                        .into(ivAlumno);

            }else{
                if(alumnos.get(posicion).getGenero()==1)
                    ivAlumno.setImageResource(R.drawable.v_alumna);//.setImageDrawable();
                if(alumnos.get(posicion).getGenero()==2)
                    ivAlumno.setImageResource(R.drawable.v_alumno);
            }


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.m_editar_acumulativo_asignatura, menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                return onOptionsItemSelected(item);
            }
        });



        return super.onCreateOptionsMenu(menu);
    }

    private void updateAcumulativo(){


        /*
        List<NotaAcumulativo> notas=adapterAcumulativoNota.getDetalles();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());



        for(int x=0;x<notas.size();x++){

            notas.get(x).setUpdatedAt(date);
            //se establece que se necesita actualizar en el servidor
            notas.get(x).setSincronizarServer(2);


            notaAcumulativosDao.actualizar(notas.get(x));


        }

        Toast.makeText(getApplicationContext(), "Se actualizaron las notas", Toast.LENGTH_LONG).show();

        */


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){


        Toast.makeText(this,"Se manda a actualizar las notas "+item.getItemId(),Toast.LENGTH_SHORT);
        switch (item.getItemId()) {

            case R.id.it_select_alumno:


                AlertDialog.Builder builder = new AlertDialog.Builder(this, Spinner.MODE_DIALOG);
                builder.setTitle("Seleccione el alumno:");

                final Spinner sAlumnos = new Spinner(this,Spinner.MODE_DIALOG);

                sAlumnos.setAdapter(adapterAlumno);
               // sParciales.sp

                //spFechaFactura
                //etNoFactura.setInputType(InputType.TYPE_CLASS_DATETIME);
                builder.setView(sAlumnos);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  m_Text=null;
                        //  myArticulo=null;
                        dialog.cancel();
                        //setEmptyTitle();
                    }
                });

                builder.setCancelable(false);
                //builder.setCanceledOnTouchOutside(false);
                // builder.create().show();

                final AlertDialog dialog = builder.create();
                //dialog.
                dialog.show();

                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean wantToCloseDialog = false;


                        //String parcial=sAlumnos.getSelectedItem()+"";
                        posicion=sAlumnos.getSelectedItemPosition();

                        // Toast.makeText(getApplicationContext(), day+"/"+ month+"/"+ year ,Toast.LENGTH_SHORT).show();
                        if(posicion>=0 && posicion<=alumnos.size()) {


                            wantToCloseDialog=true;
                            getAcumutivo();
                          //  acumulativo.setParcial(parcial);
                            //    editarTitulo();
                        }
                        else {
                            //etNoFactura.setError("Debe ingresar la fecha de la factura");
                            //return 0;
                        }

                        //Do stuff, possibly set wantToCloseDialog to true then...
                        if(wantToCloseDialog)
                            dialog.dismiss();
                        //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
                    }
                });

                dialog.getButton((AlertDialog.BUTTON_NEGATIVE)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;

        }
        return true;
    }

}
