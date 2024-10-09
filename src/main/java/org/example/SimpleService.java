package org.example;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public final class SimpleService {
    public static final String API_URL = "https://api.github.com";

    public static class Repositorio {
        public final String name;
        public final String description;
        public final Integer stargazers_count;

        public Repositorio(String name, String description, int stargazers_count) {
            this.name = name;
            this.description = description;
            this.stargazers_count = stargazers_count;
        }
    }

    public interface GitHub {
        @GET("/users/{owner}/repos")
        Call<List<Repositorio>> Repositorios(@Path("owner") String owner);
    }

    public static void main(String... args) throws IOException {



        Boolean ok = true;
        while (ok) {// Create a very simple REST adapter which points the GitHub API.
            try {
                Retrofit retrofit =
                        new Retrofit.Builder()
                                .baseUrl(API_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                // Create an instance of our GitHub API interface.
                GitHub github = retrofit.create(GitHub.class);

                Scanner scanner = new Scanner(System.in);
                System.out.print("Introduce un usuario de github: ");
                String usuario = scanner.nextLine();

                // Create a call instance for looking up Repositories
                Call<List<Repositorio>> call = github.Repositorios(usuario);

                // Fetch and print a list of Repositories
                List<Repositorio> Repositorios = call.execute().body();
                for (Repositorio Repository : Repositorios) {
                    System.out.println("Nombre repositorio: " + Repository.name + ", Descripción del repositorio: " + Repository.description + ", Numero de stargazers: " + " (" + Repository.stargazers_count + ")");
                }

            } catch (java.lang.NullPointerException e) {
                System.out.println("Has introducido un user que no es valido");
            }

        }
    }
}