package com.example.sisong;

import static com.example.sisong.R.id.btnCadastrarDoador;
import static com.example.sisong.R.id.txtUsername;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText txtUsername, txtSenha;
    private Button btnLogin, btnCadastrarDoador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCadastrarDoador = (Button) findViewById(R.id.btnCadastrarDoador);

        btnLogin.setOnClickListener(v -> login());

        btnCadastrarDoador.setOnClickListener(v -> telaCadastro());
    }

    private void login() {
        String username = txtUsername.getText().toString().trim();
        String senha = txtSenha.getText().toString().trim();

        if (username.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        } else if (username.equals("admin") && senha.equals("admin")) {
            txtUsername.setText("");
            txtSenha.setText("");
            Intent intent = new Intent(MainActivity.this, Administrador.class);
            startActivity(intent);
        } else {
            LoginRequest loginRequest = new LoginRequest(username, senha);
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<LoginResponse> call = apiService.login(loginRequest);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        txtUsername.setText("");
                        txtSenha.setText("");
                        LoginResponse loginResponse = response.body();
                        Toast.makeText(MainActivity.this, loginResponse.getMensagem(), Toast.LENGTH_SHORT).show();

                        int doadorId = loginResponse.getDoador().getDoadorId();
                        Log.d("Login", "Doador ID: " + doadorId);

                        Intent intent = new Intent(MainActivity.this, TelaDoacao.class);
                        intent.putExtra("userName", response.body().getDoador().getNomeDoador());
                        intent.putExtra("userId", response.body().getDoador().getDoadorId());
                        startActivity(intent);

                    } else {
                        Toast.makeText(MainActivity.this, "Login falhou", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.e("LoginError", "Erro na conexão: " + t.getMessage(), t);
                    Toast.makeText(MainActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void telaCadastro(){
        Intent intent = new Intent(MainActivity.this, CadastroDoador.class);
        startActivity(intent);
    }
}
