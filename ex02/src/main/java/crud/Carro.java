package crud;

public class Carro {
    private int id;
    private String nome;
    private String cor;
    private int ano;

    // Construtor Completo
    public Carro(int id, String nome, String cor, int ano) {
        this.id = id;
        this.nome = nome;
        this.cor = cor;
        this.ano = ano;
    }

    // Construtor para Inserir (ID é gerado pelo banco)
    public Carro(String nome, String cor, int ano) {
        this.nome = nome;
        this.cor = cor;
        this.ano = ano;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }
    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    // Método toString para fácil visualização
    @Override
    public String toString() {
        return "ID: " + id + " | Nome: " + nome + " | Cor: " + cor + " | Ano: " + ano;
    }
}