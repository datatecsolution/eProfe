package net.profeinformatica.eprofe;


import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import net.profeinformatica.eprofe.modelo.Docente;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiService;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiUtils;
import net.profeinformatica.eprofe.modeloDao.DocenteDao;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DatosDocente extends AppCompatActivity {


    private TextInputLayout etCodigo;
    private TextInputLayout etNombre;
    private TextInputLayout etApellido;
    private TextInputLayout etCodigoSace;
    private TextInputLayout etPwdSace;
    private TextInputLayout etTelefono;
    private TextInputLayout etEmail;
    private RadioButton rbFemenino;
    private RadioButton rbMasculino;
    private TextInputLayout etDireccion;
    private Toolbar toolbar;
    private boolean edicion=false;
    private ApiService mAPIService;
    private Docente myDocente=new Docente();
    //eProfDbHelper dbHelper = new eProfDbHelper(this);

    private DocenteDao docenteDao=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_docente);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        docenteDao=new DocenteDao();

       

        etCodigo=(TextInputLayout)findViewById(R.id.etCodigoDocente);
        etNombre=(TextInputLayout)findViewById(R.id.etNombre);
        etApellido=(TextInputLayout)findViewById(R.id.etApellido);
        etCodigoSace=(TextInputLayout)findViewById(R.id.etCodigoSace);
        etPwdSace=(TextInputLayout)findViewById(R.id.etPwdSace);
        etTelefono=(TextInputLayout)findViewById(R.id.etTelefono);
        etEmail=(TextInputLayout)findViewById(R.id.etEmail);
        etDireccion=(TextInputLayout)findViewById(R.id.etDireccion);
        rbFemenino= (RadioButton) findViewById(R.id.rbFemenino);
        rbMasculino= (RadioButton) findViewById(R.id.rbMasculino);

        mAPIService = ApiUtils.getApiService();




        
        
        getPreferensData();

    }

    private void getPreferensData() {


        myDocente=docenteDao.buscarPorId(1);



        if(myDocente.getId()!=-1) {


            if (myDocente.getGenero() == 1) {
                rbFemenino.setChecked(true);
            } else if (myDocente.getGenero() == 2) {
                rbMasculino.setChecked(true);
            }


            etCodigo.getEditText().setText("" + myDocente.getId());
            etNombre.getEditText().setText(myDocente.getNombre());
            etApellido.getEditText().setText(myDocente.getApellido());
            etCodigoSace.getEditText().setText(myDocente.getUserSace());
            etDireccion.getEditText().setText(myDocente.getDireccion());
            etTelefono.getEditText().setText(myDocente.getTelefono());
            etEmail.getEditText().setText(myDocente.getEmail());
            edicion=true;
        }


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

    public void setPrefencias(){




        if(this.edicion==true){
            docenteDao.actualizar(myDocente);
            Toast.makeText(getApplicationContext(), "Se actualizo los datos del docente", Toast.LENGTH_LONG).show();



        }else {

            docenteDao.registrar(myDocente);
            Toast.makeText(getApplicationContext(), "Se registro los datos del docente", Toast.LENGTH_LONG).show();
        }

        /*
        SharedPreferences pref = getApplicationContext().getSharedPreferences("prefeDocenteLocal", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();


        editor.putInt("docente_codigo", myDocente.getId()); // getting Integer
        editor.putString("docente_nombre", myDocente.getNombre()); // getting String
        editor.putString("docente_apellido", myDocente.getApellido()); // getting String
        editor.putString("docente_codigo_sace",myDocente.getUserSace()); // getting String
        editor.putString("docente_direccion",myDocente.getDireccion()); // getting String
        editor.putString("docente_telefono",myDocente.getTelefono()); // getting String
        editor.putString("docente_email",myDocente.getEmail()); // getting String
        editor.putInt("docente_genero", myDocente.getGenero()); // getting Integer

        editor.commit(); // commit changes
        Toast.makeText(getApplicationContext(), "Se actualizo los datos del docente", Toast.LENGTH_LONG).show();
        getPreferensData();
        */


    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()) {

            case R.id.it_guardar:
                //Toast.makeText(this,"Se mando a guardar los datos",Toast.LENGTH_SHORT);

                if(etNombre.getEditText().getText().toString().length() == 0 ) {

                    etNombre.setError("El campo descripcion no puedo quedar en vacio");
                }else if(etApellido.getEditText().getText().toString().length() == 0){

                    etApellido.setError("El campo descripcion no puedo quedar en vacio");

                }else if(etPwdSace.getEditText().getText().length() == 0) {
                    etPwdSace.setError("El campo descripcion no puedo quedar en vacio");
                }else if(etTelefono.getEditText().getText().length() == 0) {
                    etTelefono.setError("El campo descripcion no puedo quedar en vacio");
                }else{


                    myDocente.setNombre(etNombre.getEditText().getText().toString());
                    myDocente.setApellido(etApellido.getEditText().getText().toString());
                    myDocente.setDireccion(etDireccion.getEditText().getText().toString());
                    myDocente.setUserSace(etCodigoSace.getEditText().getText().toString());
                    myDocente.setPasswordSace(etPwdSace.getEditText().getText().toString());
                    myDocente.setEmail(etEmail.getEditText().getText().toString());
                    myDocente.setTelefono(etTelefono.getEditText().getText().toString());

                    if (rbFemenino.isChecked()) {

                        myDocente.setGenero(1);
                    } else {
                        myDocente.setGenero(2);
                    }


                    final ProgressDialog progressDoalog2;
                    progressDoalog2 = new ProgressDialog(this);
                    progressDoalog2.setMax(100);
                    progressDoalog2.show();

                    mAPIService.saveDocente(myDocente.getNombre(), myDocente.getApellido(),
                            myDocente.getGenero(), myDocente.getDireccion(),
                            myDocente.getUserSace(), myDocente.getPasswordSace(),
                            myDocente.getEmail(), myDocente.getTelefono())
                            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Docente>() {
                                @Override
                                public void onCompleted() {
                                    progressDoalog2.dismiss();

                                }

                                @Override
                                public void onError(Throwable e) {
                                    progressDoalog2.dismiss();

                                }

                                @Override
                                public void onNext(Docente docente) {
                                    myDocente.setId(docente.getId());
                                    setPrefencias();


                                }
                            });
                }





                break;
        }
        return true;
    }

}
