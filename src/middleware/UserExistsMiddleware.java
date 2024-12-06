package middleware;

import server.Server;

/**
 * Classe UserExistsMiddleware, um manipulador concreto.
 * Verifica se o usu�rio com as credenciais fornecidas existe no sistema.
 */
public class UserExistsMiddleware extends Middleware {
    private Server server; // Refer�ncia ao servidor que cont�m os dados de usu�rios.

    /**
     * Construtor que recebe uma inst�ncia de servidor para acessar os dados de usu�rios.
     *
     * @param server O servidor que armazena as informa��es de email e senha dos usu�rios.
     */
    public UserExistsMiddleware(Server server) {
        this.server = server;
    }

    /**
     * Verifica se o email do usu�rio existe no servidor e se a senha est� correta.
     * Caso alguma dessas verifica��es falhe, a solicita��o � interrompida.
     * Caso contr�rio, a verifica��o � passada para o pr�ximo middleware na cadeia.
     *
     * @param email    O email do usu�rio.
     * @param password A senha do usu�rio.
     * @return true se o email e a senha est�o corretos, caso contr�rio, retorna false.
     */
    public boolean check(String email, String password) {
        // Verifica se o email existe no servidor.
        if (!server.hasEmail(email)) {
            System.out.println("This email is not registered!");
            return false; // Interrompe a cadeia se o email n�o estiver registrado.
        }

        // Verifica se a senha fornecida est� correta para o email.
        if (!server.isValidPassword(email, password)) {
            System.out.println("Wrong password!");
            return false; // Interrompe a cadeia se a senha estiver incorreta.
        }

        // Chama o pr�ximo middleware na cadeia, se houver, para continuar as verifica��es.
        return checkNext(email, password);
    }
}
