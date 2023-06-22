package testeProjeto;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Vagao implements Serializable {
	private String nome;
    private boolean disponivel;
    private String carga;
    private int quantidade;
    
    public static final String[] conteudosPossiveisEnvioR1 = {"cobre", "ferro", "magnesita", "niquel", "níquel"};
    public static final String[] conteudosPossiveisEnvioR2 = {"amendoim", "feijao", "feijão", "milho", "soja", "trigo"};
    
    public static final String[] conteudosPossiveisRecebeR1 = {"amendoim", "feijao", "feijão", "milho", "soja", "trigo"};
    public static final String[] conteudosPossiveisRecebeR2 = {"cobre", "ferro", "magnesita", "niquel", "níquel"};

    public Vagao(String nome) {
        this.nome = nome;
        this.disponivel = true;
        this.carga = "";
        this.quantidade = 0;
    }

    public String getNome() {
        return nome;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public String getCarga() {
        return carga;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public boolean isVazio() {
        return carga.isEmpty() && quantidade == 0;
    }

    public void adicionarCarga(int numero, String conteudo, int quantidade) {    	
        this.carga = conteudo;
        this.quantidade = quantidade;       	
    }

    public void desembarcarCarga() {
        this.carga = "";
        this.quantidade = 0;
    }
 
}
