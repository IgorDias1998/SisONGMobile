package com.example.sisong;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("api/doador/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("api/Doacao")
    Call<Void> fazerDoacao(@Body Doacao doacao);

    @POST("api/Doador")
    Call<Void> cadastrarDoador(@Body Doador doador);

    @GET("api/Doador")
    Call<List<Doador>> getDoadores();

    @GET("api/Doacao")
    Call<List<Doacao>> getDoacoes();

    @GET("api/Doacao/minhas-doacoes/{id}")
    Call<List<Doacao>> getMinhasDoacoes(@Path("id") int id);
}