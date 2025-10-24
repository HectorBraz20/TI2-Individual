package model;

public class Carro {
    private int id;
    private String nome;
    private String cor;
    private int ano;
    private String marca;
    private String linha;

    // Construtor padrão (Para o GSON serializar/desserializar)
    public Carro() {}

    // Construtor Completo (Para o DAO ao listar/buscar)
    public Carro(int id, String nome, String cor, int ano, String marca, String linha) {
        this.id = id;
        this.nome = nome;
        this.cor = cor;
        this.ano = ano;
        this.marca = marca;
        this.linha = linha;
    }

    // Construtor para Inserir (Sem ID, pois é gerado pelo banco)
    public Carro(String nome, String cor, int ano, String marca, String linha) {
        this.nome = nome;
        this.cor = cor;
        this.ano = ano;
        this.marca = marca;
        this.linha = linha;
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
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getLinha() { return linha; }
    public void setLinha(String linha) { this.linha = linha; }

    @Override
    public String toString() {
        return "ID: " + id + " | Nome: " + nome + " | Cor: " + cor + " | Ano: " + ano + " | Marca: " + marca + " | Linha: " + linha;
    }
}