package net.profeinformatica.eprofe.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.profeinformatica.eprofe.R;
import net.profeinformatica.eprofe.modelo.Alumno;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;

public class AdapterAlumno extends ArrayAdapter<Alumno> {

    private Context context;
    private List<Alumno> detalles;

    public List<Alumno> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Alumno> detalles) {
        this.detalles = detalles;
        notifyDataSetChanged();
    }



    public AdapterAlumno(@NonNull Context context, @NonNull List<Alumno> a) {
        super(context, R.layout.item_alumno, a);

        this.context=context;
        detalles =a;
    }

    @Override
    public int getCount() {
        return detalles.size();
    }

    @Override
    public Alumno getItem(int i) {
        return detalles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return detalles.get(i).getId();
    }

    //este método establece el elemento seleccionado sobre el botón del spinner
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

        if (convertView == null)
        {
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_alumno,null);
        }
        TextView tvNombre = (TextView)convertView.findViewById(R.id.tvAsignatura);
        tvNombre.setText(detalles.get(position).getNombre()+" "+ detalles.get(position).getApellido());

        TextView tvRne=(TextView)convertView.findViewById(R.id.tvRne);
        ImageView ivAlumno=(ImageView)convertView.findViewById(R.id.ivFoto);

        tvRne.setText(detalles.get(position).getRne());

        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir,detalles.get(position).getRne()+".jpg");
        if (image.exists()) {
            Bitmap bMap = BitmapFactory.decodeFile(image.getAbsolutePath());

            Matrix mat = new Matrix();
            mat.postRotate(90);
            bMap = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), mat, true);

            // ivAlumno.setImageURI(Uri.parse(imageFilePath));

            ivAlumno.setImageBitmap(bMap);
        }else{
            ivAlumno.setImageResource(R.drawable.avatar_1);//.setImageDrawable();
        }





        return convertView;
    }
}
