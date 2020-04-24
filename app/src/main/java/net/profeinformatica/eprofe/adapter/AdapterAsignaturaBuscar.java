package net.profeinformatica.eprofe.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.profeinformatica.eprofe.R;
import net.profeinformatica.eprofe.modelo.Asignatura;

import java.util.List;

public class AdapterAsignaturaBuscar extends ArrayAdapter<Asignatura> {

    private Context context;
    private List<Asignatura> asignaturas;

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }



    public AdapterAsignaturaBuscar(@NonNull Context context, @NonNull List<Asignatura> a) {
        super(context, R.layout.item_asignatura, a);

        this.context=context;
        asignaturas=a;
    }

    @Override
    public int getCount() {
        return asignaturas.size();
    }

    @Override
    public Asignatura getItem(int i) {
        return asignaturas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return asignaturas.get(i).getId();
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
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_asignatura_buscar,null);
        }
        TextView tvNombre = (TextView)convertView.findViewById(R.id.tvAsignatura);
        tvNombre.setText(asignaturas.get(position).getNombre());



        return convertView;
    }
}
