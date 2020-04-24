package net.profeinformatica.eprofe.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.profeinformatica.eprofe.EditarAsistencia;
import net.profeinformatica.eprofe.R;
import net.profeinformatica.eprofe.modelo.EncabezadoAsistencia;


import java.util.List;

public class AdapterAsistencia extends ArrayAdapter<EncabezadoAsistencia> {

    private Context context;
    private List<EncabezadoAsistencia> asistencias;

    public List<EncabezadoAsistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<EncabezadoAsistencia> asistencias) {
        this.asistencias = asistencias;
    }



    public AdapterAsistencia(@NonNull Context context, @NonNull List<EncabezadoAsistencia> a) {
        super(context, R.layout.item_asistencias, a);

        this.context=context;
        asistencias =a;
    }

    @Override
    public int getCount() {
        return asistencias.size();
    }

    @Override
    public EncabezadoAsistencia getItem(int i) {
        return asistencias.get(i);
    }

    @Override
    public long getItemId(int i) {
        return asistencias.get(i).getId();
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
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_asistencias,null);
        }
        TextView tvAsignatura = (TextView)convertView.findViewById(R.id.tvAsignatura);
        tvAsignatura.setText(asistencias.get(position).getAsignatura().getNombre());

        TextView tvSeccion=(TextView)convertView.findViewById(R.id.tvSeccion);
        tvSeccion.setText(asistencias.get(position).getSeccion().getCurso()+" "+asistencias.get(position).getSeccion().getSeccion()+" "+asistencias.get(position).getSeccion().getModalidad().getAlias());

        TextView tvFecha=(TextView)convertView.findViewById(R.id.tvFecha);
        tvFecha.setText(EditarAsistencia.formateDateFromstring(asistencias.get(position).getFecha()));

        /*
        if(position%2==1){
            convertView.setBackgroundResource(R.color.colorRow);
        }*/





        return convertView;
    }
}
