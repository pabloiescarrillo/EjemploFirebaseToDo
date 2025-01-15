package es.iescarrillo.android.ejemplofirebasetodo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import es.iescarrillo.android.ejemplofirebasetodo.R;
import es.iescarrillo.android.ejemplofirebasetodo.enums.Provider;
import es.iescarrillo.android.ejemplofirebasetodo.models.Person;
import es.iescarrillo.android.ejemplofirebasetodo.services.PersonService;

public class InsertOrEditPersonActivity extends AppCompatActivity {

    // TODO: declaración componentes de la pantalla
    private EditText etName, etSurname, etAge, etEmail, etPassword;
    private Button btnSave, btnCancel, btnDelete;

    // TODO: declaración variables del Intent
    private Boolean editMode;
    private Person person;

    // TODO: variables locales
    private FirebaseAuth instanceAuth;

    // TODO: declaración servicios a usar en la Activity
    private PersonService personService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insert_or_edit_person);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadComponents();
        // Si estoy en modo edición tendré que cargar los editText con los valores de la persona que he recibido en el intent
        if(editMode){
            etName.setText(person.getName());
            etSurname.setText(person.getSurname());
            etAge.setText(person.getAge().toString());
            etEmail.setText(person.getEmail());
        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            etName.setText(currentUser.getDisplayName());
            etEmail.setText(currentUser.getEmail());
            etPassword.setVisibility(View.GONE);
            etEmail.setEnabled(false);


        }

        // TODO: evento para volver a la pantalla principal si se pulsa el botón cancelar
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InsertOrEditPersonActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        /* TODO: evento botón guardar:
        * 1) comprobar si se está en modo edición o no.
        * 2) si se está en modo edición =>
        *       2.1) comprobar que lso campos obligatorios no están en blanco
        *       2.2) actualizar la persona
        * 3) si NO se está en modo edición
        *       3.1) crear un nuevo usuario en Firebase Authentication
        *       3.2) con los datos del nuevo usuario de FirebaseAuth y los introducidos en la pantalla,
        *       crear una nueva persona en FirebaseRealTime
        * */
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instancia del módulo de Authenticación
                instanceAuth = FirebaseAuth.getInstance();

                if(instanceAuth.getCurrentUser() == null) {
                    instanceAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Se ejecuta este bloque si el registro es correcto
                                        createPerson();
                                    } else {
                                        // Se ejecuta este bloque si se ha producido un error en el registro
                                        Toast.makeText(InsertOrEditPersonActivity.this, R.string.error_sign_up, Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                }
                            });
                } else {
                    createPerson();
                }
            }
        });

        // TODO: evento del botón eliminar: primero eliminar de FirebaseAuth y después de RealTime
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            personService.delete(person);

                            Intent intent = new Intent(InsertOrEditPersonActivity.this, LoginActivity.class);
                            Toast.makeText(InsertOrEditPersonActivity.this, R.string.delete_successfull, Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(InsertOrEditPersonActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
    }

    private void createPerson(){
        person.setAge(etAge.getText().toString().isBlank() ? null : Integer.valueOf(etAge.getText().toString()));
        person.setEmail(etEmail.getText().toString());
        person.setName(etName.getText().toString());
        person.setSurname(etSurname.getText().toString());

        // Obtenemos el UID del usuario creado en el módulo de Autenticación
        person.setUid(Objects.requireNonNull(instanceAuth.getCurrentUser()).getUid());

        if (editMode) {
            // Funcionalidad de edición
            personService.update(person);
            Toast.makeText(InsertOrEditPersonActivity.this, R.string.update_successfully, Toast.LENGTH_SHORT).show();
        } else {
            // Funcionalidad de inserción
            if (instanceAuth.getCurrentUser() != null)
                person.setProvider(Provider.GOOGLE);
            else
                person.setProvider(Provider.EMAIL);
            personService.insert(person);
            Toast.makeText(InsertOrEditPersonActivity.this, R.string.insert_successfully, Toast.LENGTH_SHORT).show();
        }

        // Volvemos a la pantalla de Main
        Intent intent = new Intent(InsertOrEditPersonActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void loadComponents(){
        // TODO: cargar los componentes de la pantalla
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etAge = findViewById(R.id.etAge);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        btnDelete = findViewById(R.id.btnDelete);

        // Si estoy editando voy a recibir un objeto person en el Intent desde otra Activity
        person = (Person) getIntent().getSerializableExtra("person");
        // En caso contrario vendrá a null y tendremos que llamar al constructor
        if(person==null)
            person = new Person();
        editMode = getIntent().getBooleanExtra("editMode", false);
        // MOdificamos la visibilidad del botón delete en función del modo de edición
        btnDelete.setVisibility(editMode? View.VISIBLE : View.GONE);

        personService = new PersonService(getApplicationContext());
    }
}