package crud; // Certifique-se de usar o nome correto do seu pacote

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    
    // Configurações do seu PostgreSQL LOCAL (localhost e o banco db_crud_java)
    private static final String URL = "jdbc:postgresql://localhost:5432/db_crud_java"; 
    private static final String USER = "postgres";             // Seu usuário local
    private static final String PASSWORD = "sua_senha_local";  // <--- ATUALIZE AQUI!

    public static Connection conectar() {
        Connection conexao = null;
        try {
            conexao = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados. Verifique o serviço PostgreSQL e as credenciais.");
            e.printStackTrace();
        }
        return conexao;
    }
}