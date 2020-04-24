package net.profeinformatica.eprofe.adapter;

import android.content.Context;
import android.os.Environment;
import androidx.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import net.profeinformatica.eprofe.R;
import net.profeinformatica.eprofe.modelo.NotaAcumulativo;
import net.profeinformatica.eprofe.modeloDao.NotaAcumulativosDao;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterAlumnoAcumulativo extends ArrayAdapter<NotaAcumulativo> {

    private Context context;
    private List<NotaAcumulativo> detalles;
    private double valor=0;
    private NotaAcumulativosDao notaAcumulativosDao=null;


    public List<NotaAcumulativo> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<NotaAcumulativo> d) {
        this.detalles = d;
        notifyDataSetChanged();
    }
    public void setValor(double newValor){
        valor=newValor;

    }

    public void calcularNewAcumAlumnos(double newValor) {
        for (NotaAcumulativo nota:detalles
             ) {

            if(valor!=0&&nota.getNota()!=0&&newValor!=0) {
                double newNota = (nota.getNota() / valor) * newValor;
                nota.setNota(newNota);
                nota.setSicronizadoServidor(2);
            }else
                nota.setNota(0);

        }
        notifyDataSetChanged();
    }


    public AdapterAlumnoAcumulativo(@NonNull Context con, @NonNull List<NotaAcumulativo> a) {
        super(con, R.layout.item_alumno_acumulativo, a);

        this.context=con;
        detalles =a;
        notaAcumulativosDao=new NotaAcumulativosDao();
    }



    @Override
    public int getCount() {
        return detalles.size();
    }

    @Override
    public NotaAcumulativo getItem(int i) {
        return detalles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return detalles.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return getCustomView(position, convertView, parent);
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {

        return getCustomView(position, convertView, parent);

    }

    public View getCustomView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        //return super.getView(position, convertView, parent);

        final SimpleViewHolder holder;

        if (convertView == null)
        {

            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_alumno_acumulativo,null);

            holder=new SimpleViewHolder();


            holder.tvNombre= (TextView)convertView.findViewById(R.id.tvAsignatura);

            holder.tvRne=(TextView)convertView.findViewById(R.id.tvRne);

            holder.etNota=(EditText)convertView.findViewById(R.id.etNota);
            holder.etNota.addTextChangedListener(new ListenetEditar(holder,detalles));
            holder.ivAlumno=(ImageView)convertView.findViewById(R.id.ivFoto);

            holder.etNota.setTag(position);
            holder.positionItem=position;

            convertView.setTag(holder);

        }else {
            holder = (SimpleViewHolder) convertView.getTag();
        }

       int tag_position=(Integer)holder.etNota.getTag();
        holder.positionItem=position;



        holder.etNota.setId(tag_position);

        holder.tvNombre.setText(detalles.get(position).getAlumno().getNombre()+" "+ detalles.get(position).getAlumno().getApellido());

        holder.tvRne.setText(detalles.get(position).getAlumno().getRne());



        //holder.ivAlumno.setImageBitmap(detalles.get(position).getAlumno().getFotoPerfil());



        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir,detalles.get(position).getAlumno().getRne()+".jpg");
        if (image.exists()) {

            /*
            Bitmap bMap = BitmapFactory.decodeFile( image.getAbsolutePath());

            Matrix mat = new Matrix();
            mat.postRotate(90);
            bMap = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), mat, true);



            holder.ivAlumno.setImageBitmap(bMap);
            */

            Picasso.get()
                    .load(image)
                    .resize(50, 50)
                    .centerCrop()
                    .into(holder.ivAlumno);


            //holder.ivAlumno.setImageBitmap(decodeFile(image,holder.ivAlumno.getMaxWidth(),holder.ivAlumno.getMaxHeight()));

        }else{
            if(detalles.get(position).getAlumno().getGenero()==1)
                holder.ivAlumno.setImageResource(R.drawable.v_alumna);//.setImageDrawable();
            if(detalles.get(position).getAlumno().getGenero()==2)
                holder.ivAlumno.setImageResource(R.drawable.v_alumno);
        }



        if(!(detalles.get(position).getNota()==0.0))
            holder.etNota.setText(detalles.get(position).getNota()+"");



        holder.etNota.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


               // Toast.makeText(context, " Carracter: "+s.length(), Toast.LENGTH_SHORT).show();




            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                final int positio2=holder.etNota.getId();
                final EditText Caption=(EditText)holder.etNota;

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = df.format(Calendar.getInstance().getTime());


                double entrada=0;



                if(!Caption.getText().toString().isEmpty() && Caption.getText().toString().length() > 0) {
                    entrada = Double.parseDouble(Caption.getText().toString());


                        if (entrada <= valor) {

                            detalles.get(positio2).setNota(Double.parseDouble(Caption.getText().toString()));

                            //se coloca la hora y fecha de la modificacion
                            detalles.get(positio2).setUpdatedAt(date);
                            //se establece que se necesita actualizar en el servidor
                            detalles.get(positio2).setSicronizadoServidor(2);
                            //se actualiza en la bd local
                            notaAcumulativosDao.actualizar(detalles.get(positio2));

                        } else {
                            Caption.setError("La nota no puede ser mayor al valor de acumulativo");
                            Caption.setText("");
                        }


                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                /*
                final int positio3=holder.etNota.getId();
                final EditText caption3=(EditText)holder.etNota;
                System.out.println(new String("TERMINO LA EDICION DE LOS ACUMULATIVO==================================>" +caption3.getText()));

                 */


            }
        });





        return convertView;
    }

    private class ListenetEditar implements TextWatcher {

        SimpleViewHolder mHolder;
        List<NotaAcumulativo> data;

        public ListenetEditar(SimpleViewHolder ho, List<NotaAcumulativo> d){
            mHolder=ho;
            data=d;

        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            EditText caption=(EditText)mHolder.etNota;
            caption.selectAll();

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = df.format(Calendar.getInstance().getTime());


            int position =mHolder.positionItem;
            EditText caption=(EditText)mHolder.etNota;
            double nota=0;
            //se verifica que se escribio un numero
            if(!caption.getText().toString().isEmpty() && caption.getText().toString().length() > 0) {

                nota = Double.parseDouble(caption.getText().toString());


                if (nota <= valor) {


                    //se verifica que el valor en la caja de texto es diferente al del alista
                    if(data.get(position).getNota()!=nota) {

                        detalles.get(position).setNota(nota);

                        //se coloca la hora y fecha de la modificacion
                        detalles.get(position).setUpdatedAt(date);
                        //se establece que se necesita actualizar en el servidor
                        detalles.get(position).setSicronizadoServidor(2);
                        //se actualiza en la bd local
                        notaAcumulativosDao.actualizar(detalles.get(position));
                    }

                } else {
                    caption.setError("La nota no puede ser mayor al valor de acumulativo");
                    caption.setText("");
                }

            }else{
                data.get(position).setNota(0);
            }

        }
    }
}
