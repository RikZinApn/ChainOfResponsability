package server;

import middleware.Middleware;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe Server que representa o servidor de autentica��o.
 */
public class Server {
    private Map<String, String> users = new HashMap<>(); // Mapeia emails para senhas.
    private Middleware middleware; // Inst�ncia de middleware que gerenciar� a cadeia de verifica��es.

    /**
     * Permite ao cliente definir a cadeia de middleware no servidor.
     * Isso melhora a flexibilidade e facilita o teste da classe Server.
     *
     * @param middleware O objeto middleware que ser� usado pelo servidor para processar verifica��es.
     */
    public void setMiddleware(Middleware middleware) {
        this.middleware = middleware;
    }

    /**
     * Recebe email e senha do cliente e envia a solicita��o de autoriza��o para a cadeia de middleware.
     *
     * @param email    O email do usu�rio.
     * @param password A senha do usu�rio.
     * @return true se a autoriza��o for bem-sucedida, false caso contr�rio.
     */
    public boolean logIn(String email, String password) {
        // Inicia a verifica��o na cadeia de middleware.
        if (middleware.check(email, password)) {
            System.out.println("Authorization have been successful!");
            
            // Aqui � onde o sistema poderia realizar a��es adicionais para usu�rios autorizados.
            
            return true;
        }
        return false; // Retorna false se a autoriza��o falhar em algum ponto da cadeia.
    }

    /**
     * Registra um novo usu�rio no sistema.
     *
     * @param email    O email do usu�rio.
     * @param password A senha do usu�rio.
     */
    public void register(String email, String password) {
        users.put(email, password); // Armazena o email e senha no mapa de usu�rios.
    }

    /**
     * Verifica se um email est� registrado no sistema.
     *
     * @param email O email a ser verificado.
     * @return true se o email existir no registro de usu�rios, false caso contr�rio.
     */
    public boolean hasEmail(String email) {
        return users.containsKey(email);
    }

    /**
     * Verifica se a senha fornecida corresponde � senha armazenada para o email.
     *
     * @param email    O email do usu�rio.
     * @param password A senha a ser verificada.
     * @return true se a senha for v�lida para o email fornecido, false caso contr�rio.
     */
    public boolean isValidPassword(String email, String password) {
        return users.get(email).equals(password); // Compara a senha informada com a senha armazenada.
    }
}
