package es.iescarrillo.android.ejemplofirebasetodo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.iescarrillo.android.ejemplofirebasetodo.R;
import es.iescarrillo.android.ejemplofirebasetodo.adapters.PersonAdapter;
import es.iescarrillo.android.ejemplofirebasetodo.models.Person;

public class MainActivity extends AppCompatActivity {

    // TODO: declaración componentes de la pantalla
    private TextView tvCurrentUser;
    private ListView lvPersons;
    private Button btnAddPerson, btnLogout;
    private List<Person> persons;
    private PersonAdapter personAdapter;

    // TODO: declaración variables del Intent

    // TODO: declaración servicios a usar en la Activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadComponents();
        loadCurrentUser();

        // TODO: evento de comprobar cambios en la BBDD
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("persons");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                persons.clear();

                snapshot.getChildren().forEach(node -> {
                    Log.i("Persona", Objects.requireNonNull(node.getValue(Person.class)).toString());
                    persons.add(node.getValue(Person.class));
                });

                personAdapter = new PersonAdapter(MainActivity.this, persons);
                lvPersons.setAdapter(personAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error firebase", error.toString());
                Toast.makeText(MainActivity.this, R.string.error_firebase, Toast.LENGTH_SHORT).show();
            }
        });



        // TODO: evento del botón añadir persona=> enviar a la Activity de inserción indicando que el editMode es falso
        btnAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InsertOrEditPersonActivity.class);
                intent.putExtra("editMode", false);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Método para cerrar sesión
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, R.string.logout_successfull, Toast.LENGTH_SHORT).show();
            }
        });

        /* TODO: evento de hacer clic en un elemento del ListView => enviar a la Activity de
            inserción indicando que el editMode es true y la persona a editar
         */
        lvPersons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, InsertOrEditPersonActivity.class);
                intent.putExtra("editMode", true);
                intent.putExtra("person", persons.get(position));
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadCurrentUser() {
        // TODO: comprobar si hay algún usuario logueado, en caso de haberlo mostrar su email en el textView de Current User
        tvCurrentUser.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

    private void loadComponents(){
        // TODO: cargar los componentes de la pantalla
        tvCurrentUser = findViewById(R.id.tvCurrentUser);
        lvPersons = findViewById(R.id.lvPersons);
        btnAddPerson = findViewById(R.id.btnAddPerson);
        btnLogout = findViewById(R.id.btnLogout);

        persons = new ArrayList<>();
    }

    // TODO: añadir funcionalidad de cerrar sesión => FirebaseAuth.getInstance().signOut();
}