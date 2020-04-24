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

import java.util.List;

public class AdapterSeccion extends ArrayAdapter<Seccion> {

    private Context context;
    private List<Seccion> secciones;

    public AdapterSeccion(@NonNull Context context, @NonNull List<Seccion> s) {
        super(context,R.layout.item_secciones, s);

        this.context=context;
        secciones=s;
    }

    public List<Seccion> getSecciones() {
        return secciones;
    }

    public void setSecciones(List<Seccion> secciones) {
        this.secciones = secciones;
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

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        //return super.getView(position, convertView, parent);

        if (convertView == null)
        {
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_secciones,null);
        }
        TextView tvCurso = (TextView)convertView.findViewById(R.id.tvCurso);
        tvCurso.setText(secciones.get(position).getCurso());

        TextView tvSeccion=(TextView)convertView.findViewById(R.id.tvSeccion);
        tvSeccion.setText(secciones.get(position).getSeccion());

        TextView tvModalidad=(TextView)convertView.findViewById(R.id.tvModalidad);
        tvModalidad.setText(secciones.get(position).getModalidad().getAlias());



        return convertView;
    }

}
