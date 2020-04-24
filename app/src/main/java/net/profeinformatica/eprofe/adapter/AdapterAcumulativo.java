package net.profeinformatica.eprofe.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.profeinformatica.eprofe.R;
import net.profeinformatica.eprofe.modelo.Acumulativo;

import java.util.List;

public class AdapterAcumulativo extends ArrayAdapter<Acumulativo> {

    private Context context;
    private List<Acumulativo> acumulativos;

    public List<Acumulativo> getAcumulativos() {
        return acumulativos;
    }

    public void setAcumulativos(List<Acumulativo> acumulativos) {
        this.acumulativos = acumulativos;
    }



    public AdapterAcumulativo(@NonNull Context context, @NonNull List<Acumulativo> a) {
        super(context, R.layout.item_acumulativos, a);

        this.context=context;
        acumulativos =a;
    }

    @Override
    public int getCount() {
        return acumulativos.size();
    }

    @Override
    public Acumulativo getItem(int i) {
        return acumulativos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return acumulativos.get(i).getId();
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
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_acumulativos,null);
        }
        TextView tvAsignatura = (TextView)convertView.findViewById(R.id.tvAsignatura);
        tvAsignatura.setText(acumulativos.get(position).getAsignatura().getNombre());

        TextView tvSeccion=(TextView)convertView.findViewById(R.id.tvSeccion);
        tvSeccion.setText(acumulativos.get(position).getSeccion().getSeccionSort());

        TextView tvDescripcion=(TextView)convertView.findViewById(R.id.tvDescripcion);
        tvDescripcion.setText(acumulativos.get(position).getDescripcion());

        TextView tvParcial=(TextView)convertView.findViewById(R.id.tvPacial);
        tvParcial.setText("Parcial "+acumulativos.get(position).getParcial());







        return convertView;
    }
}
