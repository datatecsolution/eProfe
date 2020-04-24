package net.profeinformatica.eprofe.adapter;

import android.content.Context;
import android.os.Environment;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.profeinformatica.eprofe.R;
import net.profeinformatica.eprofe.modelo.DetalleAsistencia;


import java.io.File;
import java.util.List;

public class AdapterAlumnoAsistencia extends ArrayAdapter<DetalleAsistencia> {

    private Context context;
    private List<DetalleAsistencia> detalles;

    public List<DetalleAsistencia> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleAsistencia> detalles) {
        this.detalles = detalles;
        notifyDataSetChanged();
    }



    public AdapterAlumnoAsistencia(@NonNull Context context, @NonNull List<DetalleAsistencia> a) {
        super(context, R.layout.item_alumno_asistencia, a);

        this.context=context;
        detalles =a;
    }

    @Override
    public int getCount() {
        return detalles.size();
    }

    @Override
    public DetalleAsistencia getItem(int i) {
        return detalles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return detalles.get(i).getMovilId();
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

        final ViewHolderAlumnoAsistencia holder;

        if (convertView == null)
        {
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_alumno_asistencia,null);

            holder=new ViewHolderAlumnoAsistencia();

            holder.tvNombre = (TextView)convertView.findViewById(R.id.tvAsignatura);

            holder.tvRne=(TextView)convertView.findViewById(R.id.tvRne);

            holder.sExcusa=(Switch)convertView.findViewById(R.id.swExcusa);

            holder.cbAsistencia=(CheckBox)convertView.findViewById(R.id.cbAsistencia);

            holder.ivAlumno=(ImageView)convertView.findViewById(R.id.ivFoto);

            holder.tvNombre.setTag(position);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolderAlumnoAsistencia) convertView.getTag();
        }

        int tag_position=(Integer)holder.tvNombre.getTag();

        System.out.println(new String("Se cambio el texto y es:=========================>"+tag_position));



        holder.tvNombre.setId(tag_position);


        holder.tvNombre.setText(detalles.get(position).getAlumno().getNombre()+" "+ detalles.get(position).getAlumno().getApellido());



        holder.tvRne.setText(detalles.get(position).getAlumno().getRne());

        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir,detalles.get(position).getAlumno().getRne()+".jpg");
        if (image.exists()) {

            /*
            Bitmap bMap = BitmapFactory.decodeFile( image.getAbsolutePath());

            Matrix mat = new Matrix();
            mat.postRotate(90);
            bMap = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), mat, true);

            // ivAlumno.setImageURI(Uri.parse(imageFilePath));

            holder.ivAlumno.setImageBitmap(bMap);*/
            Picasso.get()
                    .load(image)
                    .resize(50, 50)
                    .centerCrop()
                    .into(holder.ivAlumno);
        }else{
            if(detalles.get(position).getAlumno().getGenero()==1)
                holder.ivAlumno.setImageResource(R.drawable.v_alumna);//.setImageDrawable();
            if(detalles.get(position).getAlumno().getGenero()==2)
                holder.ivAlumno.setImageResource(R.drawable.v_alumno);
        }






        if(detalles.get(position).isEstado()==1)
            holder.cbAsistencia.setChecked(true);
        else
            holder.cbAsistencia.setChecked(false);

       // cbAsistencia.setOnClickListener();

        // Register listener
        holder.cbAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // int posi=position;
                boolean accion=((CheckBox) v).isChecked();
                if(accion) {
                    detalles.get(position).setEstado(1);
                    detalles.get(position).setExcusa(0);
                    holder.sExcusa.setChecked(false);
                }
                else {
                    detalles.get(position).setEstado(0);
                }
            }
        });


        if(detalles.get(position).isExcusa()==1)
            holder.sExcusa.setChecked(true);
        else
            holder.sExcusa.setChecked(false);

        holder.sExcusa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean accion=((Switch) v).isChecked();
                if(accion) {
                    detalles.get(position).setExcusa(1);
                    detalles.get(position).setEstado(0);
                    holder.cbAsistencia.setChecked(false);
                }
                else {
                    detalles.get(position).setExcusa(0);
                }

            }
        });







        return convertView;
    }
}
