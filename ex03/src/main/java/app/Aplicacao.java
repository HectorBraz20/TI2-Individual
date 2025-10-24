package app;

import static spark.Spark.*;
import service.CarroService; 

public class Aplicacao {

    private static CarroService carroService = new CarroService();

    public static void main(String[] args) {
     
    	port(4567); 
        
    	staticFiles.location("/public");
        
        get("/hello", (req, res) -> "API CRUD Carro (Ex03) rodando!");

        post("/carro", (request, response) -> carroService.inserir(request, response)); 
        
        get("/carro", (request, response) -> carroService.listar(request, response)); 
        
        get("/carro/:id", (request, response) -> carroService.buscar(request, response)); 
        
        put("/carro/:id", (request, response) -> carroService.atualizar(request, response));

        delete("/carro/:id", (request, response) -> carroService.excluir(request, response)); 

        System.out.println("Servidor Spark iniciado na porta 4567. Pronto para testes.");
    }
}