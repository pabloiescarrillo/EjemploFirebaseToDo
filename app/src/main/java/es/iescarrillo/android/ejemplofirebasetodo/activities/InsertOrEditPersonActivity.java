package es.iescarrillo.android.ejemplofirebasetodo.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import es.iescarrillo.android.ejemplofirebasetodo.R;

public class InsertOrEditPersonActivity extends AppCompatActivity {

    // TODO: declaración componentes de la pantalla

    // TODO: declaración variables del Intent

    // TODO: declaración servicios a usar en la Activity

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

        // TODO: evento para volver a la pantalla principal si se pulsa el botón cancelar

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

        // TODO: evento del botón eliminar: primero eliminar de FirebaseAuth y después de RealTime
    }

    private void loadComponents(){
        // TODO: cargar los componentes de la pantalla
    }
}