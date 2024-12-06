package server;

import middleware.Middleware;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe Server que representa o servidor de autenticação.
 */
public class Server {
    private Map<String, String> users = new HashMap<>(); // Mapeia emails para senhas.
    private Middleware middleware; // Instância de middleware que gerenciará a cadeia de verificações.

    /**
     * Permite ao cliente definir a cadeia de middleware no servidor.
     * Isso melhora a flexibilidade e facilita o teste da classe Server.
     *
     * @param middleware O objeto middleware que será usado pelo servidor para processar verificações.
     */
    public void setMiddleware(Middleware middleware) {
        this.middleware = middleware;
    }

    /**
     * Recebe email e senha do cliente e envia a solicitação de autorização para a cadeia de middleware.
     *
     * @param email    O email do usuário.
     * @param password A senha do usuário.
     * @return true se a autorização for bem-sucedida, false caso contrário.
     */
    public boolean logIn(String email, String password) {
        // Inicia a verificação na cadeia de middleware.
        if (middleware.check(email, password)) {
            System.out.println("Authorization have been successful!");
            
            // Aqui é onde o sistema poderia realizar ações adicionais para usuários autorizados.
            
            return true;
        }
        return false; // Retorna false se a autorização falhar em algum ponto da cadeia.
    }

    /**
     * Registra um novo usuário no sistema.
     *
     * @param email    O email do usuário.
     * @param password A senha do usuário.
     */
    public void register(String email, String password) {
        users.put(email, password); // Armazena o email e senha no mapa de usuários.
    }

    /**
     * Verifica se um email está registrado no sistema.
     *
     * @param email O email a ser verificado.
     * @return true se o email existir no registro de usuários, false caso contrário.
     */
    public boolean hasEmail(String email) {
        return users.containsKey(email);
    }

    /**
     * Verifica se a senha fornecida corresponde à senha armazenada para o email.
     *
     * @param email    O email do usuário.
     * @param password A senha a ser verificada.
     * @return true se a senha for válida para o email fornecido, false caso contrário.
     */
    public boolean isValidPassword(String email, String password) {
        return users.get(email).equals(password); // Compara a senha informada com a senha armazenada.
    }
}
