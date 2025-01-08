package es.iescarrillo.android.ejemplofirebasetodo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import es.iescarrillo.android.ejemplofirebasetodo.R;
import es.iescarrillo.android.ejemplofirebasetodo.models.Person;


public class PersonAdapter extends ArrayAdapter<Person> {

    public PersonAdapter(Context context, List<Person> persons){
        super(context, 0, persons);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Person p = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_person, parent, false);

        TextView tvName = convertView.findViewById(R.id.tvNameAndSurname);
        TextView tvAge = convertView.findViewById(R.id.tvAge);

        tvName.setText(p.getName() + " " + p.getSurname());

        if (p.getAge() == null)
            tvAge.setVisibility(View.GONE);
        else
            tvAge.setText(String.valueOf(p.getAge()));

        return convertView;
    }
}
