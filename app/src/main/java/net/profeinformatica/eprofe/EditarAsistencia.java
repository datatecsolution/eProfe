package net.profeinformatica.eprofe;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import net.profeinformatica.eprofe.adapter.AdapterAlumnoAsistencia;
import net.profeinformatica.eprofe.modelo.EncabezadoAsistencia;
import net.profeinformatica.eprofe.modeloDao.DetalleAsistenciaDao;
import net.profeinformatica.eprofe.modeloDao.EncabezadoAsistenciaDao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditarAsistencia extends AppCompatActivity {

    private EncabezadoAsistencia encabezadoAsistecia=new EncabezadoAsistencia();
    //private ApiService mAPIService;
    private SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd/MM/yyy");
    private AdapterAlumnoAsistencia adapterAlumno;

    private ListView lvAsistencia;
    private Toolbar toolbar;

    private TextView tvSeccion;
    private TextView tvAsignatura;
    private TextView tvFecha;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyy-MM-dd");
    private SimpleDateFormat dateFormatter3 = new SimpleDateFormat("yyy");
    private SimpleDateFormat dateFormatter4 = new SimpleDateFormat("EEE dd, MM/yyy");

    private EncabezadoAsistenciaDao encabezadoAsistenciaDao=null;
    private DetalleAsistenciaDao detalleAsistenciaDao=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_asistencia);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       // mAPIService = ApiUtils.getApiService();
        lvAsistencia=(ListView)findViewById(R.id.lvAlumnosEditar);
        adapterAlumno=new AdapterAlumnoAsistencia(this,encabezadoAsistecia.getDetallesAsistencia());
        lvAsistencia.setAdapter(adapterAlumno);

        tvSeccion=(TextView)findViewById(R.id.tvSeccion);
        tvAsignatura=(TextView)findViewById(R.id.tvAsignatura);
        tvFecha=(TextView)findViewById(R.id.tvFecha);

        encabezadoAsistenciaDao=new EncabezadoAsistenciaDao();
        detalleAsistenciaDao=new DetalleAsistenciaDao();


        Intent intent = getIntent();

        Integer idEncabezado=intent.getExtras().getInt("idEncabezado");


        final ProgressDialog progressDoalog2;
        progressDoalog2 = new ProgressDialog(this);
        progressDoalog2.setMax(100);
        progressDoalog2.show();


        //se asigna la existecia del servidor a la del programa
        encabezadoAsistecia=encabezadoAsistenciaDao.buscarPorId(idEncabezado);

        if(encabezadoAsistecia.getId()!=-1) {
            cambiarTitulo();

            encabezadoAsistecia.setDetallesAsistencia(encabezadoAsistecia.getDetallesAsistencia());

            adapterAlumno.setDetalles(encabezadoAsistecia.getDetallesAsistencia());

            adapterAlumno.notifyDataSetChanged();

            cambiarTitulo();

            progressDoalog2.dismiss();
        }







    }
    public static String formateDateFromstring( String inputDate){

        String inputFormat="yyy-MM-dd";
        String outputFormat="EEE dd, MM/yyy";

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
           // LOG(TAG, "ParseException - dateFormat");
        }

        return outputDate;

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.m_asistencia_editar, menu);

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

            case R.id.it_editar:

                actualizarEncabezado();

                break;
            case R.id.it_edit_fecha:




                AlertDialog.Builder builder = new AlertDialog.Builder(this, Spinner.MODE_DIALOG);
                builder.setTitle("Seleccione la fecha:");

                //final EditText etNoFactura=new EditText(this);
                final DatePicker spFechaFactura=new DatePicker(this);
                spFechaFactura.setCalendarViewShown(false);
                //spFechaFactura.setLayoutMode();

                //spFechaFactura
                //etNoFactura.setInputType(InputType.TYPE_CLASS_DATETIME);
                builder.setView(spFechaFactura);

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
                dialog.show();

                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean wantToCloseDialog = false;

                        // String text=etNoFactura.getText().toString();
                        Integer day=spFechaFactura.getDayOfMonth();
                        Integer month=spFechaFactura.getMonth()+1;
                        Integer year=spFechaFactura.getYear();




                        //SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                        //Date d = new Date(year, month, day);
                        String strDate = year+"-"+month+"-"+day;

                        //myFactura.setFechaCompra(strDate);

                        // Toast.makeText(getApplicationContext(), day+"/"+ month+"/"+ year ,Toast.LENGTH_SHORT).show();
                        if(strDate.length()!=0) {


                            wantToCloseDialog=true;
                            encabezadoAsistecia.setFecha(strDate);
                            cambiarTitulo();
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
                break;
        }
        return true;
    }

    private void actualizarEncabezado() {



        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        encabezadoAsistecia.setUpdatedAt(date);
        //se establece que se necesita actualizar en el servidor
        encabezadoAsistecia.setSicronizadoServidor(2);


        if(encabezadoAsistenciaDao.actualizar(encabezadoAsistecia)){

            for (int x=0; x<adapterAlumno.getDetalles().size();x++){

                adapterAlumno.getDetalles().get(x).setUpdatedAt(date);
                //se establece que se necesita actualizar en el servidor
                adapterAlumno.getDetalles().get(x).setSicronizadoServidor(2);

                detalleAsistenciaDao.actualizar(adapterAlumno.getDetalles().get(x));

            }
            Toast.makeText(getApplicationContext(), "Se actualizo la asistencia", Toast.LENGTH_SHORT).show();
            finish();


        }
        //


/*
        mAPIService.updateAsistencia(encabezadoAsistecia.getId(),encabezadoAsistecia)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EncabezadoAsistencia>() {
                    @Override
                    public void onCompleted() {

                       // actualizarDetalles();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                       System.out.println(new String("Error encabezado================>" +e.getMessage()+e.toString()));
                    }

                    @Override
                    public void onNext(EncabezadoAsistencia encabezado) {
                        //System.out.println(new String("Actualizo================>" +encabezado.getId()));

                        finish();

                    }
                });*/
    }


    private void cambiarTitulo() {

        tvSeccion.setText(encabezadoAsistecia.getSeccion().getSeccionSort());
        tvAsignatura.setText(encabezadoAsistecia.getAsignatura().getNombre());

        Date fecha= null;
        try {
            fecha = dateFormatter.parse(encabezadoAsistecia.getFecha());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvFecha.setText(dateFormatter4.format(fecha));

    }

}
