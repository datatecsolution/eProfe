package net.profeinformatica.eprofe;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import net.profeinformatica.eprofe.adapter.AdapterAlumnoAcumulativo;
import net.profeinformatica.eprofe.adapter.AdapterAsignatura;
import net.profeinformatica.eprofe.adapter.AdapterTipoAcumulativo;
import net.profeinformatica.eprofe.modelo.Acumulativo;
import net.profeinformatica.eprofe.modelo.Asignatura;
import net.profeinformatica.eprofe.modelo.NotaAcumulativo;
import net.profeinformatica.eprofe.modelo.TipoAcumulativo;
import net.profeinformatica.eprofe.modeloDao.AcumulativosDao;
import net.profeinformatica.eprofe.modeloDao.NotaAcumulativosDao;
import net.profeinformatica.eprofe.modeloDao.TipoAcumulativoDao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;

public class EditarAcumulativo extends AppCompatActivity {

    private Toolbar toolbar;
    private Acumulativo acumulativo=new Acumulativo();

    private ListView lvAcumulativo;
    //private TextView tvSeccion;
    private TextView tvAcumulativo;
    private TextView tvAsignatura;
    private TextView tvTipoTarea;
    private AdapterAlumnoAcumulativo adapterAlumnoAcumulativo;

    private ArrayList<Asignatura> asignaturas=new ArrayList<Asignatura>();
    private ArrayList<TipoAcumulativo> tipoAcumulativos=new ArrayList<TipoAcumulativo>();
    private AdapterAsignatura adapterAsignatura;
    private AdapterTipoAcumulativo adapterTipoAcumulativo;

    private AcumulativosDao acumulativosDao=null;
    private TipoAcumulativoDao tipoAcumulativoDao=null;
    private NotaAcumulativosDao notaAcumulativosDao=null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_acumulativo);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        acumulativosDao=new AcumulativosDao();
        tipoAcumulativoDao=new TipoAcumulativoDao();
        notaAcumulativosDao=new NotaAcumulativosDao();


       // tvSeccion=(TextView)findViewById(R.id.tvSeccion);
        tvAsignatura=(TextView)findViewById(R.id.tvAsignatura);
        tvAcumulativo=(TextView)findViewById(R.id.tvAcumulativo);
        tvTipoTarea=(TextView)findViewById(R.id.tvTipoTarea);

        lvAcumulativo=(ListView)findViewById(R.id.lvAcumulativoEditar);
        adapterAlumnoAcumulativo=new AdapterAlumnoAcumulativo(this,acumulativo.getNotasAcumulativos());
        lvAcumulativo.setAdapter(adapterAlumnoAcumulativo);

        Intent intent = getIntent();

        Integer idAcumulativo=intent.getExtras().getInt("idAcumulativo");

        final ProgressDialog progressDoalog2;
        progressDoalog2 = new ProgressDialog(this);
        progressDoalog2.setMax(100);

        progressDoalog2.show();


        Acumulativo acum=acumulativosDao.buscarPorId(idAcumulativo);

        if(acum.getId()!=-1){
            acumulativo=acum;

            /*
            Bitmap alumnoFoto = getBitmapFromVectorDrawable(this,R.drawable.v_alumno);//BitmapFactory.decodeResource(this.getResources(),R.drawable.v_alumno);
            Bitmap alumnaFoto =getBitmapFromVectorDrawable(this,R.drawable.v_alumna); //BitmapFactory.decodeResource(this.getResources(),R.drawable.v_alumna);

            for (NotaAcumulativo nota:acumulativo.getNotasAcumulativos()
                 ) {

                if(nota.getAlumno()!=null) {

                    File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    File image = new File(storageDir,nota.getAlumno().getRne()+".jpg");
                    if (image.exists()) {

                        nota.getAlumno().setFotoPerfil(MenuPrincipal.decodeFile(image, 50, 50));
                    }else{
                        nota.getAlumno().setFotoPerfil(null);
                        if(nota.getAlumno().getGenero()==1)
                            nota.getAlumno().setFotoPerfil(alumnaFoto);
                        if(nota.getAlumno().getGenero()==2)
                            nota.getAlumno().setFotoPerfil(alumnoFoto);
                    }
                }

            }

             */

            if(acumulativo.getNotasAcumulativos()!=null&&acumulativo.getNotasAcumulativos().size()>0) {

                adapterAlumnoAcumulativo.setDetalles(acumulativo.getNotasAcumulativos());

                adapterAlumnoAcumulativo.setValor(acum.getValor());

                adapterAlumnoAcumulativo.notifyDataSetChanged();


            }
            editarTitulo();
            progressDoalog2.dismiss();

            //System.out.println(new String("Encontro las noto esta sooonnnn==================================>" +acum.getNotasAcumulativos().toString()));



        }else{
            progressDoalog2.dismiss();
        }



    }
    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = AppCompatResources.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
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
        inflater.inflate(R.menu.m_acumulativo_editar, menu);

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
            case  R.id.it_edit_valor:
                    editarValor();
                break;
            /*
            case R.id.it_editar:

                actualizarAcumulativo();

                break;

             */
            case R.id.it_edit_descripcion:
                editarDescripcion();

                break;

            case R.id.it_edit_tipo_acumulativo:
                editarTipoAcumulativo();

                break;

            case R.id.it_edit_parcial:
                editarParcial();
            break;
        }
        return true;
    }

    private void editarParcial() {


        final Spinner sParciales = new Spinner(this);
        ArrayAdapter<String> spinnerCountShoesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.parciales));
        sParciales.setAdapter(spinnerCountShoesArrayAdapter);


        new MaterialAlertDialogBuilder(this)
                .setTitle("Cambiar tipo de acumulativo")
                .setView(sParciales)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                        String parcial=sParciales.getSelectedItem()+"";

                        if(parcial.length()!=0) {

                            acumulativo.setParcial(parcial);


                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String date = df.format(Calendar.getInstance().getTime());

                            acumulativo.setUpdatedAt(date);
                            acumulativo.setSicronizadoServidor(2);

                            acumulativosDao.actualizar(acumulativo);

                            editarTitulo();
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();


                    }
                })
                .show();

    }

    private void editarTipoAcumulativo() {

        final Spinner sTipoAcumulativo = new Spinner(this);
        adapterTipoAcumulativo=new AdapterTipoAcumulativo(this,tipoAcumulativos);
        sTipoAcumulativo.setAdapter(adapterTipoAcumulativo);

        List<TipoAcumulativo> tipoAcumulativos =tipoAcumulativoDao.todos();

        if(tipoAcumulativos!=null){

            adapterTipoAcumulativo.setTipoAcumulativos(tipoAcumulativos);
            adapterTipoAcumulativo.notifyDataSetChanged();

        }


        new MaterialAlertDialogBuilder(this)
                .setTitle("Cambiar la descripcion acumulativo")
                .setView(sTipoAcumulativo)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        TipoAcumulativo tipoAcumulativo2=(TipoAcumulativo)sTipoAcumulativo.getSelectedItem();

                        if(tipoAcumulativo2!=null) {
                            acumulativo.setTipoAcumulativo(tipoAcumulativo2);
                            acumulativo.setTipoAcumulativoId(tipoAcumulativo2.getId());

                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String date = df.format(Calendar.getInstance().getTime());

                            acumulativo.setUpdatedAt(date);

                            acumulativo.setSicronizadoServidor(2);

                            acumulativosDao.actualizar(acumulativo);

                            editarTitulo();
                        }



                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();


                    }
                })
                .show();

    }

    private void editarDescripcion() {


        final View view = LayoutInflater.from(MenuPrincipal.getContext()).inflate(R.layout.text_input_valor_acum, null, true);
        final TextInputLayout input2 =(TextInputLayout) view.findViewById(R.id.etValorAcumulativo2);
        input2.setHint("Nueva descripcion");
        input2.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);

        new MaterialAlertDialogBuilder(this)
                .setTitle("Cambiar la descripcion acumulativo")
                .setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                        String m_Text = input2.getEditText().getText().toString();
                        if(!m_Text.isEmpty() && m_Text.length() > 0)
                        {
                            acumulativo.setDescripcion(m_Text);

                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String date = df.format(Calendar.getInstance().getTime());

                            acumulativo.setUpdatedAt(date);
                            acumulativo.setSicronizadoServidor(2);

                            acumulativosDao.actualizar(acumulativo);

                            editarTitulo();
                        }



                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();


                    }
                })
                .show();


    }


    /*
    Pop para editar valor del acumulativo
     */

    private void editarValor() {

       final View view = LayoutInflater.from(MenuPrincipal.getContext()).inflate(R.layout.text_input_valor_acum, null, true);
       final TextInputLayout input =(TextInputLayout) view.findViewById(R.id.etValorAcumulativo2);

        final double valorMax=acumulativosDao.buscarTotalAsigParcial(acumulativo.getSeccionId(),acumulativo.getAsignaturaId(),acumulativo.getParcial());


        input.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!input.getEditText().getText().toString().isEmpty() && input.getEditText().getText().toString().length() > 0) {
                    double entrada = Double.parseDouble(input.getEditText().getText().toString());



                        if(entrada>valorMax){
                            input.getEditText().setError("El valor del acumulativo no pueder ser mayor a "+valorMax);
                        }

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        new MaterialAlertDialogBuilder(this)
                .setTitle("Cambiar valor acumulativo")
                .setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                        String m_Text = input.getEditText().getText().toString();
                        if(!m_Text.isEmpty() && m_Text.length() > 0)
                        {
                            double entrada = Double.parseDouble(m_Text);
                            double valorMax=acumulativosDao.buscarTotalAsigParcial(acumulativo.getSeccionId(),acumulativo.getAsignaturaId(),acumulativo.getParcial());

                            if(entrada<=valorMax) {

                                acumulativo.setValor(entrada);

                                adapterAlumnoAcumulativo.calcularNewAcumAlumnos(entrada);
                                adapterAlumnoAcumulativo.setValor(entrada);

                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String date = df.format(Calendar.getInstance().getTime());

                                acumulativo.setUpdatedAt(date);

                                acumulativosDao.actualizar(acumulativo);

                                editarTitulo();
                            }else {
                                input.getEditText().setError("El valor del acumulativo no pueder ser mayor a "+valorMax);
                            }
                        }



                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                })
                .show();
    }

    private void actualizarAcumulativo() {

       /* Gson gson = new Gson();
        String json ="{\"acumulativo\":"+gson.toJson(acumulativo)+"}";*/



       // System.out.println("ANTESSSS=>>>>>>"+acumulativo.toString());
         final ProgressDialog progressDoalog= new ProgressDialog(this);
        progressDoalog.setMax(100);

        progressDoalog.show();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        acumulativo.setUpdatedAt(date);
        //se establece que se necesita actualizar en el servidor
        acumulativo.setSicronizadoServidor(2);



        if(acumulativosDao.actualizar(acumulativo)){
            List<NotaAcumulativo> notas=adapterAlumnoAcumulativo.getDetalles();

            for (int x=0; x<notas.size();x++){

                notas.get(x).setUpdatedAt(date);

                //se establece que se necesita actualizar en el servidor
                notas.get(x).setSicronizadoServidor(2);

                notaAcumulativosDao.actualizar(notas.get(x));

            }
            Toast.makeText(getApplicationContext(), "Se actualizo el acumulativo", Toast.LENGTH_SHORT).show();
            finish();
        }


    }




}
