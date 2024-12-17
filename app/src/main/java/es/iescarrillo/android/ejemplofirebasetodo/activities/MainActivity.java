package es.iescarrillo.android.ejemplofirebasetodo.activities;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import es.iescarrillo.android.ejemplofirebasetodo.R;

public class MainActivity extends AppCompatActivity {

    // TODO: declaración componentes de la pantalla

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

        // TODO: evento del botón añadir persona=> enviar a la Activity de inserción indicando que el editMode es falso

        /* TODO: evento de hacer clic en un elemento del ListView => enviar a la Activity de
            inserción indicando que el editMode es true y la persona a editar
         */
    }

    private void loadCurrentUser(){
        // TODO: comprobar si hay algún usuario logueado, en caso de haberlo mostrar su email en el textView de Current User
    }

    private void loadComponents(){
        // TODO: cargar los componentes de la pantalla
    }

    // TODO: añadir funcionalidad de cerrar sesión => FirebaseAuth.getInstance().signOut();
}