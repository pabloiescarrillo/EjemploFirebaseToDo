package es.iescarrillo.android.ejemplofirebasetodo.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import es.iescarrillo.android.ejemplofirebasetodo.R;

public class LoginActivity extends AppCompatActivity {

    // TODO: declaración componentes de la pantalla

    // TODO: declaración variables del Intent

    // TODO: declaración servicios a usar en la Activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadComponents();

        /* TODO: evento del botón de Login => comprobar email y pass, en caso correcto enviar
        a la Activity de Main, en caso contrario mostrar mensaje de error.
        No olvides comprobar que los campos sean blancos
         */

        // TODO: evento del botón de SignUp => enviar a la Activity de inserción indicando que el editMode es falso
    }

    private void loadComponents(){
        // TODO: cargar los componentes de la pantalla
    }
}