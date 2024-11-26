package com.example.sisong;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroDoador extends AppCompatActivity {

    private EditText edNome, edUsername, edEmail, edSenha;
    private Button btnCadastrar, btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_doador);

        edNome = findViewById(R.id.edNome);
        edUsername = findViewById(R.id.edUsername);
        edEmail = findViewById(R.id.edEmail);
        edSenha = findViewById(R.id.edSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnVoltar = findViewById(R.id.btnVoltar);

        btnCadastrar.setOnClickListener(v -> {
            String nome = edNome.getText().toString();
            String username = edUsername.getText().toString();
            String email = edEmail.getText().toString();
            String senha = edSenha.getText().toString();

            cadastrarDoador(nome, username, email, senha);

            finish();
        });

        btnVoltar.setOnClickListener(v -> finish());
    }

    private void cadastrarDoador(String nome, String username, String email, String senha) {
        Doador doador = new Doador(nome, username, email, senha);

        ApiClient.getClient().create(ApiService.class).cadastrarDoador(doador).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CadastroDoador.this, "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CadastroDoador.this, "Erro ao realizar cadastro." + response.code(), Toast.LENGTH_LONG).show();
                    Log.d("ErroCadastro", "Código: " + response.code() + ", Mensagem: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CadastroDoador.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
