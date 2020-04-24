package net.profeinformatica.eprofe;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import net.profeinformatica.eprofe.modelo.Alumno;
import net.profeinformatica.eprofe.modelo.Matricula;
import net.profeinformatica.eprofe.modelo.Seccion;
import net.profeinformatica.eprofe.modeloDao.AlumnoDao;
import net.profeinformatica.eprofe.modeloDao.MatriculaDao;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditarAlumno extends AppCompatActivity {

    private List<Alumno> alumnos=new ArrayList<Alumno>();
    private Seccion seccion;
    //private ApiService mAPIService;
    private SimpleDateFormat dateFormatter3 = new SimpleDateFormat("yyy");

    private CoordinatorLayout clPrincipal;

    private Integer posicion=0;
    private boolean updateImagen=false;

    private TextInputLayout etNombre;
    private TextInputLayout etApellido;
    private TextInputLayout etTelefono;
    private TextInputLayout etEmail;
    private TextInputLayout tvRNE;
    private RadioButton rbFemenino;
    private RadioButton rbMasculino;
    private ImageView ivAlumno;
    private Toolbar toolbar;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    public static final int REQUEST_IMAGE = 100;
    public static final int REQUEST_PERMISSION = 200;
    private String imageFilePath = "";

    private MatriculaDao matriculaDao=null;
    private AlumnoDao alumnoDao=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_alumno);
         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvRNE=(TextInputLayout)findViewById(R.id.tvRNE);

        etNombre=(TextInputLayout)findViewById(R.id.etNombre);
        etApellido=(TextInputLayout)findViewById(R.id.etApellido);
        etTelefono=(TextInputLayout)findViewById(R.id.etTelefono);
        etEmail =(TextInputLayout)findViewById(R.id.etEmail);
        clPrincipal=(CoordinatorLayout)findViewById(R.id.constraintLayout);
        rbFemenino=(RadioButton)findViewById(R.id.rbFemenino);
        rbMasculino=(RadioButton)findViewById(R.id.rbMasculino);
        ivAlumno=(ImageView)findViewById(R.id.ivFoto);


       // mAPIService = ApiUtils.getApiService();

        matriculaDao=new MatriculaDao();
        alumnoDao=new AlumnoDao();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }


        Intent intent = getIntent();

        seccion=(Seccion) intent.getParcelableExtra("seccion");
        String year= dateFormatter3.format(Calendar.getInstance().getTime());

        final ProgressDialog progressDoalog2;
        progressDoalog2 = new ProgressDialog(this);
        progressDoalog2.setMax(100);
        progressDoalog2.show();

        //se buscan los alumnos por la matricula de la seccion
       List<Matricula> matriculas= matriculaDao.buscarPorSeccion(seccion);

        if(matriculas!=null) {
            for (int x = 0; x < matriculas.size(); x++) {

                Alumno alum =alumnoDao.buscarPorId(matriculas.get(x).getAlumnoId());
                if(alum!=null)
                   alumnos.add(alum);



            }
            if(alumnos.size()>0)
                setViewAlumno();
            progressDoalog2.dismiss();
        }



        clPrincipal.setOnTouchListener(new OnSwipeTouchListener(this){
            public void onSwipeTop() {
                //  Toast.makeText(EditarAcumulativoAsignatura.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {

                System.out.println(new String("Clip:===============>BOTON Back"));
                posicion--;
                if(posicion>=0){

                    setViewAlumno();
                   // cambiarColor();
                }else{
                    posicion=0;
                }




                //Toast.makeText(EditarAcumulativoAsignatura.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {



                System.out.println(new String("Clip:===============>BOTON next"));

                posicion++;
                if(posicion<alumnos.size()){

                    setViewAlumno();
                }else{
                    posicion=alumnos.size()-1;
                    // R.id_next
                }
                // Toast.makeText(EditarAcumulativoAsignatura.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                // Toast.makeText(EditarAcumulativoAsignatura.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void setViewAlumno(){
        Alumno alumno=alumnos.get(posicion);

        //System.out.println(new String("Alumno:===============>"+alumno.toString()));

        tvRNE.getEditText().setText(alumno.getRne());
        //tvRNE.setText("Hola");
        etNombre.getEditText().setText(alumno.getNombre());
        etApellido.getEditText().setText(alumno.getApellido());
        etTelefono.getEditText().setText(alumno.getTelefono());
        etEmail.getEditText().setText(alumno.getEmail());

        if(alumno.getGenero()==1){

            rbFemenino.setChecked(true);

        }else{
            if(alumno.getGenero()==2){
                rbMasculino.setChecked(true);

            }
        }
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir,alumnos.get(posicion).getRne()+".jpg");
        if (image.exists()) {
          // Bitmap bMap = BitmapFactory.decodeFile(image.getAbsolutePath());

            //Matrix mat = new Matrix();
            //mat.postRotate(90);
           // bMap = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), mat, true);

           // ivAlumno.setImageURI(Uri.parse(imageFilePath));
            imageFilePath=image.getAbsolutePath();
            //setPic(imageFilePath,ivAlumno);
        /*
            Picasso.get()
                    .load(image)
                    .resize(100, 100)
                    .centerCrop()
                    .into(ivAlumno);*/

           ivAlumno.setImageBitmap(decodeFile(image,ivAlumno.getMaxWidth(),ivAlumno.getMaxHeight()));
        }else{
            if(alumno.getGenero()==1) {
                ivAlumno.setImageResource(R.drawable.v_alumna);//.setImageDrawable();

            }

            if(alumno.getGenero()==2)
                ivAlumno.setImageResource(R.drawable.v_alumno);
        }

        System.out.println(new String("Alumno:===============>"+alumno.toString()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.m_editar_alumno, menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                return onOptionsItemSelected(item);
            }
        });



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Thanks for granting Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File createImageFile() throws IOException{

        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        //String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir,alumnos.get(posicion).getRne()+".jpg");//File.createTempFile(alumnos.get(posicion).getRne(), ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();
        System.out.println(new String("Alumno:===============>"+imageFilePath));

        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {


                Bitmap bMap = BitmapFactory.decodeFile(imageFilePath);

                Matrix mat = new Matrix();
                mat.postRotate(90);
                bMap = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), mat, true);

               // ivAlumno.setImageURI(Uri.parse(imageFilePath));

                ivAlumno.setImageBitmap(bMap);



            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "You cancelled the operation", Toast.LENGTH_SHORT).show();
            }
        }




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){


        //Toast.makeText(this,"Se manda a actualizar las notas "+item.getItemId(),Toast.LENGTH_SHORT);
        switch (item.getItemId()) {

            case R.id.it_actualizar:


                if(setAlumno()) {

                    Alumno alumno = alumnos.get(posicion);

                    alumnoDao.actualizar(alumno);
                    //progressDoalog2.dismiss();
                    Toast.makeText(this, "Se actualizo el registro del alumno.", Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.agregar_img:

                openCameraIntent();


                break;



        }
        return true;
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Uri photoUri = FileProvider.getUriForFile(this, getPackageName() +".provider", photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(pictureIntent, REQUEST_IMAGE);
        }
    }


    public static Bitmap decodeFile(File file, int reqWidth, int reqHeight){

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file.getPath(), options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


    private boolean setAlumno() {

        boolean result=false;


        if(etNombre.getEditText().getText().toString().length() == 0 ) {

            etNombre.setError("El campo descripcion no puedo quedar en vacio");
        }else if(etApellido.getEditText().getText().toString().length() == 0){

            etApellido.setError("El campo descripcion no puedo quedar en vacio");

        }else {

            alumnos.get(posicion).setNombre(etNombre.getEditText().getText().toString());
            alumnos.get(posicion).setApellido(etApellido.getEditText().getText().toString());

            if (rbMasculino.isChecked()) {
                alumnos.get(posicion).setGenero(2);
            } else {
                if (rbFemenino.isChecked()) {
                    alumnos.get(posicion).setGenero(1);
                    //System.out.println(new String("ALUMNOOOOOOOO:===============>"+ alumnos.get(posicion).toString()));
                }
            }
            alumnos.get(posicion).setTelefono(etTelefono.getEditText().getText().toString());
            alumnos.get(posicion).setEmail(etEmail.getEditText().getText().toString());



            result=true;
        }
        return result;
    }



}
