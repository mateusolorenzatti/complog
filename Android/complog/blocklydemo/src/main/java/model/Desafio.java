package model;

import com.google.blockly.android.demo.R;

import java.io.Serializable;

public class Desafio implements Serializable {

    private int id;

    private String titulo;

    private String conteudo;

    private String autor;

    private int nivel;

    public Desafio(int id, String titulo, String conteudo, String autor, int nivel) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.autor = autor;

        switch(nivel){
            case 1: this.nivel = R.drawable.nivel_facil;
                    break;
            case 2: this.nivel = R.drawable.nivel_medio;
                    break;
            case 3: this.nivel = R.drawable.nivel_dificil;
                    break;
        };
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
