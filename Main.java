package testeProjeto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;


public class Main {
	private static Composicao composicao1;
	private static Composicao composicao2;
	private static Map<Integer, Map<String, List<String>>> vagoesDesembarcadosPorComposicao;

	public static final String[] conteudosPossiveisEnvioR1 = {"cobre", "ferro", "magnesita", "niquel"};
	public static final String[] conteudosPossiveisEnvioR2 = {"amendoim", "feijao", "milho", "soja", "trigo"};

	public static final String[] conteudosPossiveisRecebeR1 = {"amendoim", "feijao", "milho", "soja", "trigo"};
	public static final String[] conteudosPossiveisRecebeR2 = {"cobre", "ferro", "magnesita", "niquel"};
	
	static List<String> desembarcados = new ArrayList<String>();

	public static void main(String[] args) {
		composicao1 = criarComposicao(1);
		composicao2 = criarComposicao(2);
		vagoesDesembarcadosPorComposicao = new HashMap<>();

		vagoesDesembarcadosPorComposicao.put(1, new HashMap<>());
		vagoesDesembarcadosPorComposicao.put(2, new HashMap<>());

		Composicao composicao = new Composicao(0);
		
		

		Scanner scanner = new Scanner(System.in);

		while (true) {
			exibirMenu();
			int opcao = scanner.nextInt();
			scanner.nextLine(); // Limpar o buffer

			switch (opcao) {
			case 1:
				adicionarCarga(scanner);
				break;
			case 2:
				desembarcarCarga(scanner);
				break;
			case 3:
				visualizarComposicoes();
				break;
			case 4:
				transferirVagoes(scanner);
				break;
			case 5:
				defPonto(scanner);
				break;
			case 6:
				defTerminal(scanner);
				break;
			case 7:
				commodities();
				return;
			case 8:
				System.out.println("Encerrando o programa.");
				return;
			default:
				System.out.println("Opção inválida. Tente novamente.");
				break;
			}
		}
	}

	
	private static Composicao criarComposicao(int numero) {
		Composicao composicao = new Composicao(numero);

		// Criar vagoes de A a M
		for (char c = 'A'; c <= 'M'; c++) {
			composicao.adicionarVagao(new Vagao(String.valueOf(c)));
		}

		return composicao;
	}

	private static void exibirMenu() {
		System.out.println("----- MENU -----");
		System.out.println("1 - Adicionar carga");
		System.out.println("2 - Desembarcar carga");
		System.out.println("3 - Visualizar composições");
		System.out.println("4 - Transferir Vagões");
		System.out.println("5 - Definir Viagem para Ponto de Interconexão");
		System.out.println("6 - Definir Viagem para Terminal");
		System.out.println("7 - Visualizar cargas desembarcadas");
		System.out.println("8 - Sair");
		System.out.println("Digite a opção desejada: ");
	}

	private static void adicionarCarga(Scanner scanner) {

		System.out.println("Digite o número da composição (1 ou 2): ");
		int numeroComposicao = scanner.nextInt();
		scanner.nextLine(); // Limpar o buffer

		Composicao composicao;

		if (numeroComposicao == 1) {
			composicao = composicao1;

		} else if (numeroComposicao == 2) {
			composicao = composicao2;

		} else {
			System.out.println("Composição inválida.");
			return;
		}
		if (!composicao.isNoTerminal()) {
			System.out.println("Operação de desembarcar carga permitida apenas no terminal.");
			return;
		}

		if(composicao.getEmMovimento()==true) {
			System.out.println("Não é possível adicionar a carga pois a composição está em movimento");
			return;
		}

		System.out.println("Digite o identificador do vagão (A a M): ");
		String identificador = scanner.nextLine();

		if (identificador.length() != 1 || identificador.charAt(0) < 'A' || identificador.charAt(0) > 'M') {
			System.out.println("Identificador inválido.");
			return;
		}

		if(composicao==composicao1) {
			System.out.println("Digite o conteúdo da carga: ");
			String conteudo = scanner.nextLine();
			for(int i = 0; i < conteudosPossiveisEnvioR1.length; i++) {
				if(conteudo.equals(conteudosPossiveisEnvioR1[i])) {
					System.out.println("Digite a quantidade (em kg) da carga: ");
					int quantidade = scanner.nextInt();
					scanner.nextLine(); // Limpar o buffer

					composicao.adicionarCarga(identificador, conteudo, quantidade);
				}else
					System.out.println("Conteudo Invalido");
			}
		}

		if(composicao==composicao2) {
			System.out.println("Digite o conteúdo da carga: ");
			String conteudo = scanner.nextLine();
			for(int i = 0; i < conteudosPossiveisEnvioR2.length; i++) {
				if(conteudo.equals(conteudosPossiveisEnvioR2[i])) {
					System.out.println("Digite a quantidade (em kg) da carga: ");
					int quantidade = scanner.nextInt();
					scanner.nextLine(); // Limpar o buffer

					composicao.adicionarCarga(identificador, conteudo, quantidade);
				}else
					System.out.println("Conteudo Invalido");
			}
		}

	}


	private static void desembarcarCarga(Scanner scanner) {
		System.out.println("Digite o número da composição (1 ou 2): ");
		int numeroComposicao = scanner.nextInt();
		scanner.nextLine(); // Limpar o buffer

		Composicao composicao;
		if (numeroComposicao == 1) {
			composicao = composicao1;
		} else if (numeroComposicao == 2) {
			composicao = composicao2;
		} else {
			System.out.println("Composição inválida.");
			return;
		}

		if (!composicao.isNoTerminal()) {
			System.out.println("Operação de desembarcar carga permitida apenas no terminal.");
			return;
		}

		if(composicao.getEmMovimento()==true) {
			System.out.println("Não é possível adicionar a carga pois a composição está em movimento");
			return;
		}

		System.out.println("Digite o identificador do vagão (A a M): ");
		String identificador = scanner.nextLine();

		if (identificador.length() != 1 || identificador.charAt(0) < 'A' || identificador.charAt(0) > 'M') {
			System.out.println("Identificador inválido.");
			return;
		}

		String desembarcada = composicao.desembarcarCarga(identificador, numeroComposicao);
		desembarcados.add(desembarcada);		

	}


	private static void visualizarComposicoes() {
		System.out.println("----- COMPOSIÇÃO 1 -----");
		exibirComposicao(composicao1);

		System.out.println("----- COMPOSIÇÃO 2 -----");
		exibirComposicao(composicao2);
	}

	private static void exibirComposicao(Composicao composicao) {
		Stack<Vagao> vagoes = composicao.getVagoes();

		for (Vagao vagao : vagoes) {
			System.out.print(vagao.getNome() + " - ");
			if (vagao.isVazio()) {
				System.out.println("Vazio");
			} else {
				System.out.println(vagao.getCarga() + " (" + vagao.getQuantidade() + "kg)");
			}
		}
	}

	private static void transferirVagoes(Scanner scanner) {
		System.out.print("Digite o número da primeira composição (1 ou 2): ");
		int numComposicao1 = scanner.nextInt();
		Composicao composicao1Transferencia = numComposicao1 == 1 ? composicao1 : composicao2;
		System.out.print("Digite o número da segunda composição (1 ou 2): ");
		int numComposicao2 = scanner.nextInt();
		Composicao composicao2Transferencia = numComposicao2 == 1 ? composicao1 : composicao2;
		composicao1Transferencia.transferirVagoes(composicao2Transferencia);
	}

	private static void defTerminal(Scanner scanner) {
		System.out.print("Digite o número da composição (1 ou 2): ");
		int numComposicaoTerminal = scanner.nextInt();
		Composicao composicaoTerminal = numComposicaoTerminal == 1 ? composicao1 : composicao2;

		composicaoTerminal.setEmMovimento(true);
		System.out.println("A composição "+numComposicaoTerminal+" ja saiu");

		Thread contadorThread = new Thread(() -> {
			for (int i = 10; i > 0; i--) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			composicaoTerminal.setNoTerminal(true);
			composicaoTerminal.setEmMovimento(false);
		});
		try {
			contadorThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		contadorThread.start();


	}

	private static void defPonto(Scanner scanner) {
		System.out.print("Digite o número da composição (1 ou 2): ");
		int numComposicaoInterconexao = scanner.nextInt();
		Composicao composicaoInterconexao = numComposicaoInterconexao == 1 ? composicao1 : composicao2;

		composicaoInterconexao.setEmMovimento(true);

		System.out.println("A composição "+numComposicaoInterconexao+" ja saiu");

		Thread contadorThread = new Thread(() -> {
			for (int i = 10; i > 0; i--) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			composicaoInterconexao.setEmMovimento(false);
			composicaoInterconexao.setNoPontoInterconexao(true);
		});
		try {
			contadorThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		contadorThread.start();


	}

	public static void iniciarViagem(Scanner scanner, Composicao composicao) {
		System.out.println("Digite o número da composição (1 ou 2)");
		Scanner scanner1 = new Scanner(System.in);
		int numComposicao = scanner1.nextInt();

		if (numComposicao == 1) {
			System.out.println("Viagem da composição 1 iniciada.");
			Thread viagem1Thread = new Thread(() -> composicao.viagemComposicao1());
			viagem1Thread.start();
		} else if (numComposicao == 2) {
			System.out.println("Viagem da composição 2 iniciada.");
			Thread viagem2Thread = new Thread(() -> composicao.viagemComposicao2());
			viagem2Thread.start();
		}
	}

	public static String commodities(){
		String resposta = "";
		Iterator<String> it = desembarcados.iterator();
		while(it.hasNext()) {
			String carga = it.next();
			resposta += carga + ", ";
		}
		
		return resposta;
		
	}

}
