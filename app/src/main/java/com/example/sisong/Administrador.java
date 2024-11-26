package com.example.sisong;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Administrador extends AppCompatActivity {

    private TextView txtDoadores, txtDoacoes;
    private Button btnVoltarInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Inicializando os TextViews
        setContentView(R.layout.activity_administrador);
        txtDoadores = findViewById(R.id.txtDoadores);
        txtDoacoes = findViewById(R.id.txtDoacoes);
        btnVoltarInicio = findViewById(R.id.btnVoltarInicio);

        getDoadores();
        getDoacoes();

        btnVoltarInicio.setOnClickListener(v -> finish());
    }

    public void getDoadores() {
        ApiClient.getClient().create(ApiService.class).getDoadores().enqueue(new Callback<List<Doador>>() {
            @Override
            public void onResponse(Call<List<Doador>> call, Response<List<Doador>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Doador> doadores = response.body();

                    StringBuilder doadoresInfo = new StringBuilder();
                    for (int i = 0; i < doadores.size(); i++) {
                        Doador doador = doadores.get(i);
                        doadoresInfo.append("Nome: ").append(doador.getNomeDoador())
                                .append("\nUsername: ").append(doador.getUsername())
                                .append("\nEmail: ").append(doador.getEmailDoador())
                                .append("\n\n");
                    }

                    TextView txtDoadoresNomes = findViewById(R.id.txtDoadores);
                    txtDoadoresNomes.setText(doadoresInfo.toString());

                } else {
                    Toast.makeText(Administrador.this, "Erro ao carregar doadores", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Doador>> call, Throwable t) {
                Toast.makeText(Administrador.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getDoacoes() {
        ApiClient.getClient().create(ApiService.class).getDoacoes().enqueue(new Callback<List<Doacao>>() {
            @Override
            public void onResponse(Call<List<Doacao>> call, Response<List<Doacao>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Doacao> doacoes = response.body();

                    StringBuilder doacoesInfo = new StringBuilder();
                    for (int i = 0; i < doacoes.size(); i++) {
                        Doacao doacao = doacoes.get(i);
                        doacoesInfo.append("Nome: ").append(doacao.getNomeDoador())
                                .append("\nValor: R$").append(doacao.getValorDoacao())
                                .append("\nData: ").append(doacao.getDataDoacao())
                                .append("\n\n");
                    }

                    TextView txtDoacoes = findViewById(R.id.txtDoacoes);
                    txtDoacoes.setText(doacoesInfo.toString());

                } else {
                    Toast.makeText(Administrador.this, "Erro ao carregar doações", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Doacao>> call, Throwable t) {
                Toast.makeText(Administrador.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
