package middleware;

/**
 * Classe RoleCheckMiddleware. Manipulador concreto que verifica o papel do usuário.
 */
public class RoleCheckMiddleware extends Middleware {
    
    /**
     * Verifica o papel do usuário com base no email fornecido.
     *
     * @param email    O email do usuário.
     * @param password A senha do usuário.
     * @return true se o papel do usuário foi identificado e a cadeia de responsabilidade
     *         pode ser interrompida. Caso contrário, passa para o próximo middleware na cadeia.
     */
    public boolean check(String email, String password) {
        // Verifica se o email pertence ao administrador.
        if (email.equals("admin@example.com")) {
            System.out.println("Hello, admin!");
            return true; // Se o usuário é admin, interrompe a cadeia retornando true.
        }
        
        // Caso contrário, o usuário é tratado como padrão.
        System.out.println("Hello, user!");
        
        // Passa a verificação para o próximo middleware, se houver.
        return checkNext(email, password);
    }
}
