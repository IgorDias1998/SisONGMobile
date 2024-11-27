package com.example.sisong;

import static com.example.sisong.R.id.btnSair;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaDoacao extends AppCompatActivity {

    private TextView tvUserName, txtMinhasDoacoes;
    private EditText etDoacao;
    private Button btnDoar, btnSair;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_doacao);

        tvUserName = findViewById(R.id.tvUserName);
        etDoacao = findViewById(R.id.etDoacao);
        btnDoar = findViewById(R.id.btnDoar);
        btnSair = findViewById(R.id.btnSair);

        // Receber o nome do usuário da tela de login
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        int userId = intent.getIntExtra("userId", -1);

        // Exibir o nome do usuário
        tvUserName.setText("Olá, " + userName);

        //Exibe as doações a partir do Id do Usuário recebido
        minhasDoacoes(userId);

        btnDoar.setOnClickListener(v -> {
            String doacaoValorStr = etDoacao.getText().toString();

            if (doacaoValorStr.isEmpty()) {
                Toast.makeText(this, "Por favor, insira um valor.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Converter valor para decimal
            double doacaoValor = Double.parseDouble(doacaoValorStr);

            // Chamar a API para registrar a doação passando os parâmetros Id e Valor
            realizarDoacao(userId, doacaoValor);
        });

        btnSair.setOnClickListener(v -> finish());
    }

    private void realizarDoacao(int userId, double doacaoValor) {
        Doacao doacao = new Doacao(userId, doacaoValor);

        // Chama a API
        ApiClient.getClient().create(ApiService.class).fazerDoacao(doacao).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    minhasDoacoes(userId);
                    Toast.makeText(TelaDoacao.this, "Doação realizada com sucesso!", Toast.LENGTH_LONG).show();
                    etDoacao.setText("");
                } else {
                    Toast.makeText(TelaDoacao.this, "Erro ao realizar doação.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(TelaDoacao.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void minhasDoacoes(int userId) {
        ApiClient.getClient().create(ApiService.class).getMinhasDoacoes(userId).enqueue(new Callback<List<Doacao>>() {
            @Override
            public void onResponse(Call<List<Doacao>> call, Response<List<Doacao>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Doacao> doacoes = response.body();

                    StringBuilder doacoesInfo = new StringBuilder();
                    for (int i = 0; i < doacoes.size(); i++) {
                        Doacao doacao = doacoes.get(i);
                        doacoesInfo.append("\nValor: R$").append(doacao.getValorDoacao())
                                .append("\nData: ").append(doacao.getDataDoacao())
                                .append("\n\n");
                    }
                    TextView txtMinhasDoacoes = findViewById(R.id.txtMinhasDoacoes);
                    txtMinhasDoacoes.setText(doacoesInfo.toString());
                } else {
                    Log.e("Erro", "Erro na resposta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Doacao>> call, Throwable t) {
                Log.e("Erro", "Falha na conexão: " + t.getMessage());
            }
        });
    }
}
