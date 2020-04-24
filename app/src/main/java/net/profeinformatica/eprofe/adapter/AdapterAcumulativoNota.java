package net.profeinformatica.eprofe.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import net.profeinformatica.eprofe.R;
import net.profeinformatica.eprofe.modelo.NotaAcumulativo;
import net.profeinformatica.eprofe.modeloDao.NotaAcumulativosDao;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterAcumulativoNota extends ArrayAdapter<NotaAcumulativo> {

    private Context context;
    private List<NotaAcumulativo> notas;
    private double total=0;
    private TextView tvTotal;
    private NotaAcumulativosDao notaAcumulativosDao=new NotaAcumulativosDao();;



    public AdapterAcumulativoNota(@NonNull Context context, @NonNull List<NotaAcumulativo> a) {
        super(context, R.layout.item_acumulativo_nota, a);

        this.context=context;
        notas =a;
        //notaAcumulativosDao=new NotaAcumulativosDao();
    }

    @Override
    public int getCount() {
        return notas.size();
    }

    @Override
    public NotaAcumulativo getItem(int i) {
        return notas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return notas.get(i).getId();
        //return super.getItemId(i);
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



    public View getCustomView( int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        //return super.getView(position, convertView, parent);


        View view= convertView;
        final ViewHolderAcumulativo holder;

        if (view == null)
        {
            //LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

           // convertView = inflater.inflate(R.layout.item_acumulativo_nota,parent, false);
            view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_acumulativo_nota,null);


            holder=new ViewHolderAcumulativo();


            holder.tvNombre= (TextView)view.findViewById(R.id.tvNombreAcumulativo);



            holder.etNota=(EditText)view.findViewById(R.id.etNota);
            holder.etNota.addTextChangedListener(new listenetEditar(holder,notas) );

            holder.etValorAcum=(TextView)view.findViewById(R.id.etValorAcum);

            holder.etNota.setTag(position);
            holder.positionItem=position;

            view.setTag(holder);

        }else {
            holder = (ViewHolderAcumulativo) view.getTag();
        }

        int tag_position=(Integer)holder.etNota.getTag();

       // System.out.println(new String(position+"Se cambio el texto y es:=========================>"+tag_position));

        holder.etNota.setId(tag_position);

        holder.positionItem=position;



        if(notas.get(position).getAcumulativo()!=null) {
            holder.tvNombre.setText(notas.get(position).getAcumulativo().getDescripcion());

            holder.etValorAcum.setText(""+ notas.get(position).getAcumulativo().getValor());
        }




        //if(!(detalles.get(position).getNota()==0.0))
        holder.etNota.setText(notas.get(position).getNota()+"");
        holder.etNota.setError(null);

        /*
        holder.etNota.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, "tvItem button Clicked",
                        Toast.LENGTH_LONG).show();
            }
        });

         */




        return view;
    }
    public void setEtTotal(TextView tv){
        tvTotal=tv;
    }


    public double getTotal(){
        total=0;
        for(int x = 0; x< notas.size(); x++){
            total+= notas.get(x).getNota();
        }
        return total;
    }


    public List<NotaAcumulativo> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaAcumulativo> notas) {
        this.notas = notas;
       // notifyDataSetChanged();
    }

    private class listenetEditar implements TextWatcher {

        ViewHolderAcumulativo mHolder;
        List<NotaAcumulativo> data;

        public listenetEditar(ViewHolderAcumulativo ho, List<NotaAcumulativo> d){
            mHolder=ho;
            data=d;

        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            EditText caption=(EditText)mHolder.etNota;
            caption.selectAll();

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable s) {

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = df.format(Calendar.getInstance().getTime());


                int position =mHolder.positionItem;
                EditText caption=(EditText)mHolder.etNota;
                double nota=0;
                //se verifica que se escribio un numero
                if(!caption.getText().toString().isEmpty() && caption.getText().toString().length() > 0) {

                    nota = Double.parseDouble(caption.getText().toString());

                    //System.out.println(new String("Texto en la caja:================>" + nota));
                    //System.out.println(new String("Valor max del Item en los datos:================>" + data.get(position).getAcumulativo().getValor()));

                    //si la nota de acumulativo es mayor al valor del acumulativo data un error
                    if (nota <=  data.get(position).getAcumulativo().getValor()) {
                        //si al sumar la nota con el total se pasa de 100 regresara un error
                        if (getTotal() + nota > 100) {
                           caption.setError("Las notas no puede ser mayor 100%");
                           // Caption.setText("");
                            data.get(position).setNota(0);
                            tvTotal.setText("" + getTotal());
                        } else {
                            //se verifica que el valor en la caja de texto es diferente al del alista
                            if(data.get(position).getNota()!=nota) {
                                data.get(position).setNota(nota);
                                tvTotal.setText("" + getTotal());
                                data.get(position).setUpdatedAt(date);
                                //se establece que se necesita actualizar en el servidor
                                data.get(position).setSicronizadoServidor(2);


                                notaAcumulativosDao.actualizar(data.get(position));
                            }
                        }
                    }else {
                        caption.setError("La nota no puede ser mayor al valor de acumulativo");
                        //data.get(position).setNota(0);
                    }

                }else{
                    data.get(position).setNota(0);
                }

        }
    }
}
