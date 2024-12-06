package middleware;

import server.Server;

/**
 * Classe UserExistsMiddleware, um manipulador concreto.
 * Verifica se o usuário com as credenciais fornecidas existe no sistema.
 */
public class UserExistsMiddleware extends Middleware {
    private Server server; // Referência ao servidor que contém os dados de usuários.

    /**
     * Construtor que recebe uma instância de servidor para acessar os dados de usuários.
     *
     * @param server O servidor que armazena as informações de email e senha dos usuários.
     */
    public UserExistsMiddleware(Server server) {
        this.server = server;
    }

    /**
     * Verifica se o email do usuário existe no servidor e se a senha está correta.
     * Caso alguma dessas verificações falhe, a solicitação é interrompida.
     * Caso contrário, a verificação é passada para o próximo middleware na cadeia.
     *
     * @param email    O email do usuário.
     * @param password A senha do usuário.
     * @return true se o email e a senha estão corretos, caso contrário, retorna false.
     */
    public boolean check(String email, String password) {
        // Verifica se o email existe no servidor.
        if (!server.hasEmail(email)) {
            System.out.println("This email is not registered!");
            return false; // Interrompe a cadeia se o email não estiver registrado.
        }

        // Verifica se a senha fornecida está correta para o email.
        if (!server.isValidPassword(email, password)) {
            System.out.println("Wrong password!");
            return false; // Interrompe a cadeia se a senha estiver incorreta.
        }

        // Chama o próximo middleware na cadeia, se houver, para continuar as verificações.
        return checkNext(email, password);
    }
}
