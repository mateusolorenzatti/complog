package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.blockly.android.demo.R;

import java.util.ArrayList;

import model.Desafio;

public class DesafioAdapter extends ArrayAdapter<Desafio> {
    private final Context context;
    private final ArrayList<Desafio> elementos;

    public DesafioAdapter(Context context,  ArrayList<Desafio>  elementos) {
        super(context,  R.layout.desafio,  elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.desafio,  parent,  false);

        TextView nomeDesafio=  (TextView)  rowView.findViewById(R.id.nome_desafio);
        TextView autor=  (TextView)  rowView.findViewById(R.id.autor_desafio);
        ImageView nivel  =  (ImageView)  rowView.findViewById(R.id.nivel_desafio);

        nomeDesafio.setText(elementos.get(position).getTitulo());
        autor.setText(elementos.get(position).getAutor());
        nivel.setImageResource(elementos.get(position).getNivel());

        return rowView;
    }
}
