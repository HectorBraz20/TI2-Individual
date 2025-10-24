package service;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import dao.CarroDAO;
import model.Carro;
import java.util.List;

public class CarroService {
    
    private CarroDAO carroDAO = new CarroDAO();
    private Gson gson = new Gson();

    // POST /carro (CREATE)
    public Object inserir(Request request, Response response) {
        response.type("application/json");
        try {
            Carro novoCarro = gson.fromJson(request.body(), Carro.class);
            
            if (novoCarro.getNome() == null || novoCarro.getNome().isEmpty()) {
                response.status(400); // Bad Request
                return gson.toJson("Falha: Nome do carro não pode ser vazio.");
            }
            
            if (carroDAO.inserir(novoCarro)) {
                response.status(201); // Created
                return gson.toJson("Carro inserido com sucesso!");
            } else {
                response.status(500); // Internal Server Error
                return gson.toJson("Falha na inserção no banco de dados.");
            }
            
        } catch (Exception e) {
            response.status(400); 
            return gson.toJson("Erro ao processar requisição: " + e.getMessage());
        }
    }

    // GET /carro (READ - Listar Todos)
    public Object listar(Request request, Response response) {
        response.type("application/json");
        List<Carro> carros = carroDAO.listar();
        response.status(200); // OK
        return gson.toJson(carros); 
    }
    
    // GET /carro/:id (READ - Buscar por ID)
    public Object buscar(Request request, Response response) {
        response.type("application/json");
        try {
            int id = Integer.parseInt(request.params(":id"));
            Carro carro = carroDAO.buscar(id);
            
            if (carro != null) {
                response.status(200);
                return gson.toJson(carro);
            } else {
                response.status(404); // Not Found
                return gson.toJson("Carro não encontrado.");
            }
        } catch (NumberFormatException e) {
            response.status(400); // Bad Request
            return gson.toJson("ID inválido.");
        }
    }

    // PUT /carro/:id (UPDATE)
    public Object atualizar(Request request, Response response) {
        response.type("application/json");
        try {
            int id = Integer.parseInt(request.params(":id"));
            Carro carroAtualizado = gson.fromJson(request.body(), Carro.class);
            carroAtualizado.setId(id);
            
            if (carroDAO.atualizar(carroAtualizado)) {
                response.status(200); // OK
                return gson.toJson("Carro ID " + id + " atualizado.");
            } else {
                response.status(404); // Not Found
                return gson.toJson("Carro ID " + id + " não encontrado para atualizar.");
            }
        } catch (NumberFormatException e) {
            response.status(400); 
            return gson.toJson("ID inválido.");
        } catch (Exception e) {
            response.status(400);
            return gson.toJson("Erro ao processar requisição: " + e.getMessage());
        }
    }

    // DELETE /carro/:id (DELETE)
    public Object excluir(Request request, Response response) {
        response.type("application/json");
        try {
            int id = Integer.parseInt(request.params(":id"));
            
            if (carroDAO.excluir(id)) {
                response.status(200); // OK
                return gson.toJson("Carro ID " + id + " excluído.");
            } else {
                response.status(404); // Not Found
                return gson.toJson("Carro ID " + id + " não encontrado para exclusão.");
            }
        } catch (NumberFormatException e) {
            response.status(400); // Bad Request
            return gson.toJson("ID inválido.");
        }
    }
}