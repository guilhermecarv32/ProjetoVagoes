package testeProjeto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Composicao {
	private int numero;
    private Stack<Vagao> vagoes;

    private boolean noTerminal;
    private boolean noPontoInterconexao;
    private boolean EmMovimento;

    public static final String[] conteudosPossiveisRecebeR1 = {"amendoim", "feijao", "milho", "soja", "trigo"};
	public static final String[] conteudosPossiveisRecebeR2 = {"cobre", "ferro", "magnesita", "niquel"};   
   
    public Composicao(int numero) {
        this.numero = numero;
        this.vagoes = new Stack<>();
        this.noTerminal = true;
        this.noPontoInterconexao = false;
        this.EmMovimento=false; 
    }

	public int getNumero() {
        return numero;
    }
    
    public boolean getEmMovimento() {
		return EmMovimento;   	
    }
    
    public void setEmMovimento(boolean EmMovimento) {
		this.EmMovimento=EmMovimento;   	
    }


    public Stack<Vagao> getVagoes() {
        return vagoes;
    }

    public void adicionarVagao(Vagao vagao) {
        vagoes.push(vagao);
        vagao.setDisponivel(false);
        System.out.println("Vagão " + vagao.getNome() + " adicionado à composição " + numero + ".");
    }

    public void adicionarCarga(String identificador, String conteudo, int quantidade) {
        for (Vagao vagao : vagoes){
        	
            if (vagao.getNome().equals(identificador)) {
            	if(vagao.isVazio()) {
                vagao.adicionarCarga(quantidade, conteudo, quantidade);
                System.out.println("Carga de " + quantidade + "kg de " + conteudo + " adicionada ao vagão " +
                        vagao.getNome() + " na composição " + numero + ".");
            	}else
            		System.out.println("Vagao já está cheio");
                return;
            }
        }
        System.out.println("Vagão " + identificador + " não encontrado na composição " + numero + ".");
    }

    public String desembarcarCarga(String identificador, int composicao) {
    	String cargaD = null;
    	List<String> conteudosPermitidos = new ArrayList<String>();
        if (composicao == 1) {
            conteudosPermitidos = Arrays.asList(conteudosPossiveisRecebeR1);
        } else if (composicao == 2) {
            conteudosPermitidos = Arrays.asList(conteudosPossiveisRecebeR2);
        } else {
            System.out.println("Composição inválida.");

        }

        for (Vagao vagao : vagoes) {
            if (vagao.getNome().equals(identificador)) {
                if (vagao.isVazio()) {
                    System.out.println("Vagão " + vagao.getNome() + " na composição " + numero + " já está vazio.");
                } else {
                    String carga = vagao.getCarga().toLowerCase();
                    if (conteudosPermitidos.contains(carga)) {
                        vagao.desembarcarCarga();
                        System.out.println("Carga desembarcada do vagão " + vagao.getNome() + " na composição " + numero + ".");
                    } else {
                        System.out.println("Não é permitido desembarcar a carga " + vagao.getCarga() +
                                " no vagão " + vagao.getNome() + " na composição " + numero + ".");
                        
                       cargaD = vagao.getCarga();
                    }
                }
                
            }
        }
        System.out.println("Vagão " + identificador + " não encontrado na composição " + numero + ".");
		return cargaD;
    }
    
    public void transferirVagoes(Composicao outraComposicao) {
        if (noTerminal || outraComposicao.noTerminal) {
            System.out.println("Não é possível fazer a transferência de vagões no terminal.");
            return;
        }

        if (noPontoInterconexao && outraComposicao.noPontoInterconexao) {
           
            Stack<Vagao> vagoesAtual = new Stack<>();
            Stack<Vagao> vagoesOutra = new Stack<>();

            while (!vagoes.isEmpty()) {
                vagoesAtual.push(vagoes.pop());
            }
            while (!outraComposicao.vagoes.isEmpty()) {
                vagoesOutra.push(outraComposicao.vagoes.pop());
            }

            while (!vagoesAtual.isEmpty()) {
                outraComposicao.vagoes.push(vagoesAtual.pop());
            }
            while (!vagoesOutra.isEmpty()) {
                vagoes.push(vagoesOutra.pop());
            }

            System.out.println("Transferência de vagões entre a composição " + numero + " e a composição " +
                    outraComposicao.numero + " realizada com sucesso.");
            return;
        }

        
    }

    public void setNoTerminal(boolean noTerminal) {
        this.noTerminal = noTerminal;
        if (noTerminal) {
            noPontoInterconexao = false;
            System.out.println("Composição " + numero + " está no terminal: Embarque/Desembarque.");
        }
    }
    
    public boolean isNoTerminal() {
        return noTerminal;
    }

    public void setNoPontoInterconexao(boolean noPontoInterconexao) {
        this.noPontoInterconexao = noPontoInterconexao;
        if (noPontoInterconexao) {
            noTerminal = false;
            System.out.println("Composição " + numero + " está no ponto de interconexão: Transferência de vagões.");
        }
    }
    
   

    
    public void viagemComposicao1() {
        noTerminal = false;
        noPontoInterconexao = false;

        for (int i = 10; i > 0; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        noTerminal = false;
        noPontoInterconexao = true;

        System.out.println("\nComposição 1 chegou ao Ponto de Interconexão.");
    }
    
    public void viagemComposicao2() {
        noTerminal = true;
        noPontoInterconexao = false;

        for (int i = 10; i > 0; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        noTerminal = false;
        noPontoInterconexao = true;

        System.out.println("Composição 2 chegou ao Ponto de Interconexão.");
    }
    
    private List<String> getConteudosPossiveis(int composicao) {
        if (composicao == 1) {
            return Arrays.asList("amendoim", "feijao", "milho", "soja", "trigo");
        } else if (composicao == 2) {
            return Arrays.asList("cobre", "ferro", "magnesita", "niquel");
        } else {
            return Collections.emptyList();
        }
    }
    
}