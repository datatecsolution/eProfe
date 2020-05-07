package net.profeinformatica.eprofe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.profeinformatica.eprofe.R;
import net.profeinformatica.eprofe.modelo.Periodo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;

public class AdapterPeriodo extends ArrayAdapter<Periodo> {

    private Context context;
    private List<Periodo> periodos;

    private Date fecha;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyy-MM-dd");
    private SimpleDateFormat dateFormatter2 = new SimpleDateFormat(" dd MMM yyy");
    private SimpleDateFormat dateFormatter3 = new SimpleDateFormat("yyy");

    public AdapterPeriodo(@NonNull Context context,@NonNull List<Periodo> p){
        super(context,R.layout.item_periodo,p);
        this.context=context;
        periodos=p;
    }
    public List<Periodo> getPeriodos(){
        return periodos;
    }
    public void setPeriodos(List<Periodo> p){
        periodos=p;
    }
    @Override
    public int getCount() {
        return periodos.size();
    }

    @Override
    public Periodo getItem(int i) {
        return periodos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return periodos.get(i).getId();
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
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_periodo,null);
        }

        TextView tvFechaInicio=(TextView)convertView.findViewById(R.id.tvFechaInicio);
       // tvFechaInicio.setText(dateFormatter2.format(periodos.get(position).getFechaInicio()));
        tvFechaInicio.setText(periodos.get(position).getFechaFinal());

        TextView tvFechaFinal=(TextView)convertView.findViewById(R.id.tvFechaFinal);
        //tvFechaFinal.setText(dateFormatter2.format(periodos.get(position).getFechaFinal()));
        tvFechaFinal.setText(periodos.get(position).getFechaFinal());



        return convertView;
    }
}
