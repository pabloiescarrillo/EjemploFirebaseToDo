package es.iescarrillo.android.ejemplofirebasetodo.services;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.iescarrillo.android.ejemplofirebasetodo.models.Person;

public class PersonService {

    // TODO: métodos de inserción, actualización y eliminación
    private DatabaseReference reference;

    public PersonService(Context context){
        reference = FirebaseDatabase.getInstance().getReference("persons");
    }

    public String insert(Person person){
        DatabaseReference newReference = reference.push();
        String id = newReference.getKey();
        person.setId(id);

        newReference.setValue(person);

        return id;
    }

    public void update(Person person){
        reference.child(person.getId()).setValue(person);
    }

    public void delete(Person person){
        reference.child(person.getId()).removeValue();
    }

    public void deleteById(String id){
        reference.child(id).removeValue();
    }

}
