package net.profeinformatica.eprofe.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.profeinformatica.eprofe.R;
import net.profeinformatica.eprofe.modelo.TipoAcumulativo;


import java.util.List;

public class AdapterTipoAcumulativo extends ArrayAdapter<TipoAcumulativo> {

    private Context context;
    private List<TipoAcumulativo> tipoAcumulativos;

    public List<TipoAcumulativo> getTipoAcumulativos() {
        return tipoAcumulativos;
    }

    public void setTipoAcumulativos(List<TipoAcumulativo> tipoAcumulativos) {
        this.tipoAcumulativos = tipoAcumulativos;
    }



    public AdapterTipoAcumulativo(@NonNull Context context, @NonNull List<TipoAcumulativo> a) {
        super(context, R.layout.item_tipo_acumulativo, a);

        this.context=context;
        tipoAcumulativos =a;
    }

    @Override
    public int getCount() {
        return tipoAcumulativos.size();
    }

    @Override
    public TipoAcumulativo getItem(int i) {
        return tipoAcumulativos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return tipoAcumulativos.get(i).getId();
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
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_tipo_acumulativo,null);
        }
        TextView tvNombre = (TextView)convertView.findViewById(R.id.tvTipoAcumulativo);
        tvNombre.setText(tipoAcumulativos.get(position).getDescripcion());


        return convertView;
    }
}
