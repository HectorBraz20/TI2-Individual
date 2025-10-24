package crud; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/db_crud_java"; 
    private static final String USER = "postgres";
    private static final String PASSWORD = System.getenv("POSTGRES_PASS"); 

    public static Connection conectar() {
        Connection conexao = null;
        try {
            if (PASSWORD == null) {
                throw new SQLException("A senha do banco de dados (POSTGRES_PASS) não foi configurada como variável de ambiente.");
            }
            
            conexao = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Erro de Conexão. Verifique as credenciais ou a variável de ambiente POSTGRES_PASS.");
            e.printStackTrace();
        }
        return conexao;
    }
}