
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
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // Leitor de entrada do usu�rio.
    private static Server server; // Inst�ncia do servidor que gerencia os dados dos usu�rios e a cadeia de middleware.

    /**
     * Inicializa o servidor e configura a cadeia de responsabilidade (Chain of Responsibility).
     */
    private static void init() {
        server = new Server();
        
        // Cadastra dois usu�rios no servidor para teste: um admin e um usu�rio comum.
        server.register("admin@example.com", "admin_pass");
        server.register("user@example.com", "user_pass");

        // Cria e vincula a cadeia de responsabilidade, definindo a ordem das verifica��es.
        Middleware middleware = Middleware.link(
            new ThrottlingMiddleware(2),       // Limita o n�mero de requisi��es por minuto.
            new UserExistsMiddleware(server),  // Verifica se o usu�rio existe e a senha est� correta.
            new RoleCheckMiddleware()          // Verifica o n�vel de acesso do usu�rio (admin ou usu�rio comum).
        );

        // Define a cadeia de responsabilidade no servidor para que ele possa utiliz�-la nas autentica��es.
        server.setMiddleware(middleware);
    }

    /**
     * M�todo principal que gerencia a intera��o com o usu�rio.
     * Solicita email e senha e tenta autenticar at� que o login seja bem-sucedido.
     */
    public static void main(String[] args) throws IOException {
        init(); // Chama a fun��o de inicializa��o para configurar o servidor e os middlewares.

        boolean success; // Vari�vel de controle para manter o loop at� o login ser bem-sucedido.
        do {
            System.out.print("Enter email: ");
            String email = reader.readLine(); // L� o email do usu�rio.
            System.out.print("Input password: ");
            String password = reader.readLine(); // L� a senha do usu�rio.
            
            // Tenta fazer login com o email e a senha fornecidos.
            success = server.logIn(email, password);
        } while (!success); // Continua no loop at� o login ser bem-sucedido.
    }
}
