package es.iescarrillo.android.ejemplofirebasetodo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import es.iescarrillo.android.ejemplofirebasetodo.R;

public class LoginActivity extends AppCompatActivity {

    // TODO: declaración componentes de la pantalla
    private EditText etEmail, etPassword;
    private Button btnLogin, btnSignUp;

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
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, InsertOrEditPersonActivity.class);
                intent.putExtra("editMode", false);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadComponents(){
        // TODO: cargar los componentes de la pantalla
        etEmail = findViewById(R.id.etEmailLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
    }
}