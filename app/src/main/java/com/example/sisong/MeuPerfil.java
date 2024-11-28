package com.example.sisong;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeuPerfil extends AppCompatActivity {

    private TextView txtMeuPerfil;
    private EditText edNomePerfil, edEmailPerfil, edUsernamePerfil, edSenhaPerfil;
    private Button btnEditarPerfil, btnVoltarPerfil;
    private Doador usuarioLogado;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meu_perfil);

        txtMeuPerfil = findViewById(R.id.txtMeuPerfil);
        edNomePerfil = findViewById(R.id.edNomePerfil);
        edEmailPerfil = findViewById(R.id.edEmailPerfil);
        edUsernamePerfil = findViewById(R.id.edUsernamePerfil);
        edSenhaPerfil = findViewById(R.id.edSenhaPerfil);
        btnEditarPerfil = (Button) findViewById(R.id.btnEditarPerfil);
        btnVoltarPerfil = findViewById(R.id.btnVoltarPerfil);

        // Receber dados do usuário logado
        Intent intent = getIntent();
        usuarioLogado = (Doador) intent.getSerializableExtra("usuarioLogado");

        txtMeuPerfil.setText("Olá, " + usuarioLogado.getNomeDoador());

        // Preencher os campos com os dados do usuário logado
        if (usuarioLogado != null) {
            edNomePerfil.setText(usuarioLogado.getNomeDoador());
            edEmailPerfil.setText(usuarioLogado.getEmailDoador());
            edUsernamePerfil.setText(usuarioLogado.getUsername());
            edSenhaPerfil.setText(usuarioLogado.getSenha());
        }

        // Botão de edição
        btnEditarPerfil.setOnClickListener(v -> {
            if (edNomePerfil.getText().toString().isEmpty() ||
                    edEmailPerfil.getText().toString().isEmpty() ||
                    edUsernamePerfil.getText().toString().isEmpty() ||
                    edSenhaPerfil.getText().toString().isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Atualizar e enviar para a API
            usuarioLogado.setDoadorId(usuarioLogado.getDoadorId());
            usuarioLogado.setNomeDoador(edNomePerfil.getText().toString());
            usuarioLogado.setEmailDoador(edEmailPerfil.getText().toString());
            usuarioLogado.setUsername(edUsernamePerfil.getText().toString());
            usuarioLogado.setSenha(edSenhaPerfil.getText().toString());

            atualizarPerfil(usuarioLogado);
        });

        // Botão Voltar
        btnVoltarPerfil.setOnClickListener(v -> finish());
    }

    private void atualizarPerfil(Doador usuario) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // validar o ID
        int id = usuario.getDoadorId();
        Log.d("AtualizarPerfil", "Enviando DoadorId: " + id);
        Log.d("AtualizarPerfil", "Dados enviados: " + new Gson().toJson(usuario));

        apiService.editarDoador(id, usuario).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MeuPerfil.this, "Perfil atualizado com sucesso!", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        String errorResponse = response.errorBody().string();
                        Log.e("AtualizarPerfil", "Erro ao atualizar: " + errorResponse);
                        Toast.makeText(MeuPerfil.this, "Erro: " + errorResponse, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(MeuPerfil.this, "Erro desconhecido ao atualizar.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("AtualizarPerfil", "Erro de conexão: " + t.getMessage());
                Toast.makeText(MeuPerfil.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
