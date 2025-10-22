package crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarroDAO {

    // METODO INSERIR (CREATE)
    public void inserir(Carro carro) {
        String sql = "INSERT INTO carro (nome, cor, ano) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, carro.getNome());
            stmt.setString(2, carro.getCor());
            stmt.setInt(3, carro.getAno());
            stmt.executeUpdate();
            System.out.println("Carro inserido com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao inserir carro: " + e.getMessage());
        }
    }

    // METODO LISTAR (READ)
    public List<Carro> listar() {
        List<Carro> carros = new ArrayList<>();
        String sql = "SELECT id, nome, cor, ano FROM carro ORDER BY id";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cor = rs.getString("cor");
                int ano = rs.getInt("ano");
                carros.add(new Carro(id, nome, cor, ano));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar carros: " + e.getMessage());
        }
        return carros;
    }

    // METODO ATUALIZAR (UPDATE)
    public void atualizar(Carro carro) {
        String sql = "UPDATE carro SET nome = ?, cor = ?, ano = ? WHERE id = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, carro.getNome());
            stmt.setString(2, carro.getCor());
            stmt.setInt(3, carro.getAno());
            stmt.setInt(4, carro.getId());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Carro ID " + carro.getId() + " atualizado com sucesso!");
            } else {
                System.out.println("Carro ID " + carro.getId() + " não encontrado.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar carro: " + e.getMessage());
        }
    }

    // METODO EXCLUIR (DELETE)
    public void excluir(int id) {
        String sql = "DELETE FROM carro WHERE id = ?";
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Carro ID " + id + " excluído com sucesso!");
            } else {
                System.out.println("Carro ID " + id + " não encontrado.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao excluir carro: " + e.getMessage());
        }
    }
}