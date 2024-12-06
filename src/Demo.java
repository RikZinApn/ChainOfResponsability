
import middleware.Middleware;
import middleware.RoleCheckMiddleware;
import middleware.ThrottlingMiddleware;
import middleware.UserExistsMiddleware;
import server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Classe Demo. Ponto de entrada do sistema, onde tudo se integra.
 */
public class Demo {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // Leitor de entrada do usuário.
    private static Server server; // Instância do servidor que gerencia os dados dos usuários e a cadeia de middleware.

    /**
     * Inicializa o servidor e configura a cadeia de responsabilidade (Chain of Responsibility).
     */
    private static void init() {
        server = new Server();
        
        // Cadastra dois usuários no servidor para teste: um admin e um usuário comum.
        server.register("admin@example.com", "admin_pass");
        server.register("user@example.com", "user_pass");

        // Cria e vincula a cadeia de responsabilidade, definindo a ordem das verificações.
        Middleware middleware = Middleware.link(
            new ThrottlingMiddleware(2),       // Limita o número de requisições por minuto.
            new UserExistsMiddleware(server),  // Verifica se o usuário existe e a senha está correta.
            new RoleCheckMiddleware()          // Verifica o nível de acesso do usuário (admin ou usuário comum).
        );

        // Define a cadeia de responsabilidade no servidor para que ele possa utilizá-la nas autenticações.
        server.setMiddleware(middleware);
    }

    /**
     * Método principal que gerencia a interação com o usuário.
     * Solicita email e senha e tenta autenticar até que o login seja bem-sucedido.
     */
    public static void main(String[] args) throws IOException {
        init(); // Chama a função de inicialização para configurar o servidor e os middlewares.

        boolean success; // Variável de controle para manter o loop até o login ser bem-sucedido.
        do {
            System.out.print("Enter email: ");
            String email = reader.readLine(); // Lê o email do usuário.
            System.out.print("Input password: ");
            String password = reader.readLine(); // Lê a senha do usuário.
            
            // Tenta fazer login com o email e a senha fornecidos.
            success = server.logIn(email, password);
        } while (!success); // Continua no loop até o login ser bem-sucedido.
    }
}
