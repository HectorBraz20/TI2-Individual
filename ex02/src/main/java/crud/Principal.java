package crud;

import java.util.List;
import java.util.Scanner;

public class Principal {

    private static CarroDAO dao = new CarroDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            int opcao = 0;
            do {
                exibirMenu();
                if (scanner.hasNextInt()) {
                    opcao = scanner.nextInt();
                    scanner.nextLine(); 
                    processarOpcao(opcao);
                } else {
                    System.out.println("🚫 Opção inválida. Digite um número.");
                    scanner.nextLine(); 
                    opcao = 0; 
                }
            } while (opcao != 5);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            System.out.println("Programa encerrado.");
        }
    }

    private static void exibirMenu() {
        System.out.println("\n--- Menu CRUD Carro ---");
        System.out.println("1. Listar Carros");
        System.out.println("2. Inserir Novo Carro");
        System.out.println("3. Atualizar Carro");
        System.out.println("4. Excluir Carro");
        System.out.println("5. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1: listarCarros(); break;
            case 2: inserirCarro(); break;
            case 3: atualizarCarro(); break;
            case 4: excluirCarro(); break;
            case 5: break;
            default: System.out.println("🚫 Opção inválida. Tente novamente.");
        }
    }

    private static void listarCarros() {
        List<Carro> carros = dao.listar();
        if (carros.isEmpty()) {
            System.out.println("Nenhum carro cadastrado.");
        } else {
            System.out.println("\n--- Lista de Carros ---");
            for (Carro c : carros) {
                System.out.println(c);
            }
        }
    }

    private static void inserirCarro() {
        System.out.print("Digite o nome do carro: ");
        String nome = scanner.nextLine();
        System.out.print("Digite a cor do carro: ");
        String cor = scanner.nextLine();
        System.out.print("Digite o ano do carro: ");
        
        try {
            int ano = scanner.nextInt();
            scanner.nextLine(); 
            Carro novoCarro = new Carro(nome, cor, ano);
            dao.inserir(novoCarro);
        } catch (java.util.InputMismatchException e) {
            System.out.println("🚫 Entrada inválida para o ano. Tente novamente.");
            scanner.nextLine(); 
        }
    }

    private static void atualizarCarro() {
        System.out.print("Digite o ID do carro que deseja atualizar: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("Novo nome: ");
            String nome = scanner.nextLine();
            System.out.print("Nova cor: ");
            String cor = scanner.nextLine();
            System.out.print("Novo ano: ");
            int ano = scanner.nextInt();
            scanner.nextLine();

            Carro carroAtualizar = new Carro(id, nome, cor, ano);
            dao.atualizar(carroAtualizar);
        } catch (java.util.InputMismatchException e) {
            System.out.println("🚫 Entrada inválida para ID ou Ano. Tente novamente.");
            scanner.nextLine(); 
        }
    }

    private static void excluirCarro() {
        System.out.print("Digite o ID do carro que deseja excluir: ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();
            dao.excluir(id);
        } catch (java.util.InputMismatchException e) {
            System.out.println("🚫 Entrada inválida para ID. Tente novamente.");
            scanner.nextLine(); 
        }
    }
}