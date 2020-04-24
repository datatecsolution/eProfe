package net.profeinformatica.eprofe.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.profeinformatica.eprofe.R;
import net.profeinformatica.eprofe.modelo.Seccion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterSeccionCentroPeriodoAlumnos extends ArrayAdapter<Seccion> {

    private Context context;
    private List<Seccion> secciones;



    private SimpleDateFormat dateFormatter2 = new SimpleDateFormat(" dd MMM yyy");

    private boolean enableHolder=true;


    public AdapterSeccionCentroPeriodoAlumnos(@NonNull Context context, @NonNull List<Seccion> s) {
        super(context, R.layout.item_secciones, s);

        this.context=context;
        secciones=s;


    }

    public List<Seccion> getSecciones() {
        return secciones;
    }

    public void setSecciones(List<Seccion> s) {
        this.secciones = s;
    }

    @Override
    public int getCount() {
        return secciones.size();
    }

    @Override
    public Seccion getItem(int i) {
        return secciones.get(i);
    }

    @Override
    public long getItemId(int i) {
        return secciones.get(i).getId();
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

    public void setEnableSpinnerHolder(boolean enable){

    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        //return super.getView(position, convertView, parent);

        final ViewHolderSeccion holder;
        convertView=null;

        if (convertView == null)
        {
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_seccion_centro_periodo_alumno,null);
            holder=new ViewHolderSeccion();

            holder.tvCurso = (TextView)convertView.findViewById(R.id.tvCurso);

            holder.tvModalidad=(TextView)convertView.findViewById(R.id.tvModalidad);

            holder.tvSeccion=(TextView)convertView.findViewById(R.id.tvSeccion);

            holder.tvCentro=(TextView)convertView.findViewById(R.id.tvCentro);

            holder.tvJornada=(TextView)convertView.findViewById(R.id.tvJornada);

            holder.tvPeriodo=(TextView)convertView.findViewById(R.id.tvPeriodo);

            holder.tvPeriodo.setTag(position);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolderSeccion) convertView.getTag();
        }

        int tag_position=(Integer)holder.tvPeriodo.getTag();
        holder.tvPeriodo.setId(tag_position);


        holder.tvCurso.setText(secciones.get(position).getCurso());


        holder.tvModalidad.setText(secciones.get(position).getModalidad().getAlias());


        holder.tvSeccion.setText(secciones.get(position).getSeccion());


        holder.tvCentro.setText(secciones.get(position).getCentro().getNombre());


        holder.tvJornada.setText(secciones.get(position).getJornada());

        try {
            Date fechaInicio =dateFormatter2.parse(secciones.get(position).getPeriodo().getFechaInicio());
            Date fechaFinal =dateFormatter2.parse(secciones.get(position).getPeriodo().getFechaFinal());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //holder.tvPeriodo.setText("PERIODO "+dateFormatter2.format(secciones.get(position).getPeriodo().getFechaInicio())+" HASTA "+dateFormatter2.format(secciones.get(position).getPeriodo().getFechaFinal()));
       holder.tvPeriodo.setText("PERIODO "+secciones.get(position).getPeriodo().getFechaInicio()+" HASTA "+secciones.get(position).getPeriodo().getFechaFinal());


        return convertView;
    }
}
