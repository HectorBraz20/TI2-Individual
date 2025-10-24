package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Carro;

public class CarroDAO {
    
    // 1. CONFIGURAÇÃO DA CONEXÃO EMBUTIDA
    private static final String URL = "jdbc:postgresql://localhost:5432/db_crud_java"; 
    private static final String USER = "postgres";             
    private static final String PASSWORD = System.getenv("POSTGRES_PASS");
    
    
    private Connection conectar() throws SQLException {
        if (PASSWORD == null) {
            throw new SQLException("A senha do banco de dados (POSTGRES_PASS) não foi configurada como variável de ambiente.");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean checkConnection() {
        String checkPass = System.getenv("POSTGRES_PASS"); 
        
        try (Connection conn = DriverManager.getConnection(URL, USER, checkPass)) {
            System.out.println("✅ Conexão com o Banco de Dados estabelecida com sucesso!");
            return true;
        } catch (SQLException e) {
            System.err.println("❌ ERRO FATAL: Falha ao conectar ao banco de dados.");
            System.err.println("Verifique: 1. Serviço PostgreSQL ativo; 2. Variável de ambiente POSTGRES_PASS; 3. URL/Porta.");
            e.printStackTrace(); 
            return false;
        }
    }


    // METODO INSERIR (CREATE)
    public boolean inserir(Carro carro) {
        String sql = "INSERT INTO carro (nome, cor, ano, marca, linha) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conectar(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, carro.getNome());
            stmt.setString(2, carro.getCor());
            stmt.setInt(3, carro.getAno());
            stmt.setString(4, carro.getMarca());
            stmt.setString(5, carro.getLinha());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("❌ Erro ao inserir carro: " + e.getMessage());
            return false;
        }
    }

    // METODO QUE LISTA TODOS OS CARROS (READ - Listar)
    public List<Carro> listar() {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, nome, cor, ano, marca, linha FROM carro ORDER BY id";
        
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cor = rs.getString("cor");
                int ano = rs.getInt("ano");
                String marca = rs.getString("marca");
                String linha = rs.getString("linha");
                
                carros.add(new Carro(id, nome, cor, ano, marca, linha)); 
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao listar carros: " + e.getMessage());
        }
        return carros;
    }
    
    // METODO QUE BUSCA ID (READ - Buscar)
    public Carro buscar(int id) {
        String sql = "SELECT id, nome, cor, ano, marca, linha FROM carro WHERE id = ?";
        Carro carro = null;
        
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nome");
                    String cor = rs.getString("cor");
                    int ano = rs.getInt("ano");
                    String marca = rs.getString("marca");
                    String linha = rs.getString("linha");
                    
                    carro = new Carro(id, nome, cor, ano, marca, linha);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar carro por ID: " + e.getMessage());
        }
        return carro;
    }

    // METODO UPDATE
    public boolean atualizar(Carro carro) {
        String sql = "UPDATE carro SET nome = ?, cor = ?, ano = ?, marca = ?, linha = ? WHERE id = ?";
        
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, carro.getNome());
            stmt.setString(2, carro.getCor());
            stmt.setInt(3, carro.getAno());
            stmt.setString(4, carro.getMarca());
            stmt.setString(5, carro.getLinha());
            stmt.setInt(6, carro.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("❌ Erro ao atualizar carro: " + e.getMessage());
            return false;
        }
    }

    // METODO DELETE
    public boolean excluir(int id) {
        String sql = "DELETE FROM carro WHERE id = ?";
        
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("❌ Erro ao excluir carro: " + e.getMessage());
            return false;
        }
    }
}