package net.profeinformatica.eprofe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import net.profeinformatica.eprofe.R;
import net.profeinformatica.eprofe.modelo.Periodo;
import net.profeinformatica.eprofe.modelo.Seccion;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiService;
import net.profeinformatica.eprofe.modeloDao.apiWeb.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AdapterSeccionCentroPeriodo  extends ArrayAdapter<Seccion> {

    private Context context;
    private List<Seccion> secciones;
    private ArrayList<Periodo> periodos =new ArrayList<Periodo>();
    private AdapterPeriodo adapterPeriodo;
    private ApiService mAPIService;

    private boolean enableHolder=true;


    public AdapterSeccionCentroPeriodo(@NonNull Context context, @NonNull List<Seccion> s) {
        super(context, R.layout.item_secciones, s);

        this.context=context;
        secciones=s;

        mAPIService = ApiUtils.getApiService();

        mAPIService.getPeriodos().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Periodo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Periodo> per) {
                        //System.out.println(new String("Holasfdfs" + periodos.get(0).toString()));

                        adapterPeriodo.setPeriodos(per);

                        adapterPeriodo.notifyDataSetChanged();

                    }
                });

        adapterPeriodo=new AdapterPeriodo(super.getContext(),periodos);
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
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_seccion_centro_periodo,null);
            holder=new ViewHolderSeccion();

            holder.tvCurso = (TextView)convertView.findViewById(R.id.tvCurso);

            holder.tvModalidad=(TextView)convertView.findViewById(R.id.tvModalidad);

            holder.tvSeccion=(TextView)convertView.findViewById(R.id.tvSeccion);

            holder.tvCentro=(TextView)convertView.findViewById(R.id.tvCentro);

            holder.tvJornada=(TextView)convertView.findViewById(R.id.tvJornada);

            holder.sPeriodos=(Spinner)convertView.findViewById(R.id.sPeriodos);

            holder.sPeriodos.setTag(position);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolderSeccion) convertView.getTag();
        }

        int tag_position=(Integer)holder.sPeriodos.getTag();
        holder.sPeriodos.setId(tag_position);


        holder.tvCurso.setText(secciones.get(position).getCurso());

        holder.tvModalidad.setText(secciones.get(position).getModalidad().getAlias());


        holder.tvSeccion.setText(secciones.get(position).getSeccion());


       holder.tvCentro.setText(secciones.get(position).getCentro().getNombre());


        holder.tvJornada.setText(secciones.get(position).getJornada());



        holder.sPeriodos.setAdapter(adapterPeriodo);


        if(enableHolder==false){
            holder.sPeriodos.setEnabled(false);
            holder.sPeriodos.setClickable(false);
            //holder.sPeriodos.seta
        }


        //System.out.println(new String("periodo cantidad====>" + adapterPeriodo.getPeriodos().size()));

        for (int x=0;x<adapterPeriodo.getPeriodos().size();x++){

           // System.out.println(new String("periodo====>" + periodos.get(x).toString()));
            //System.out.println(new String("seccion====>" + secciones.get(position).getPeriodo().toString()));
            if(adapterPeriodo.getPeriodos().get(x).getId()==secciones.get(position).getPeriodo().getId()){
                holder.sPeriodos.setSelection(x);

            }
        }

        holder.sPeriodos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                final int positio2=holder.sPeriodos.getId();
                secciones.get(positio2).setPeriodoId(adapterPeriodo.getPeriodos().get(position).getId());

               System.out.println(new String("Holasfdfdfsfasdfdafas---->>>>>>"+positio2));




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return convertView;
    }
}
