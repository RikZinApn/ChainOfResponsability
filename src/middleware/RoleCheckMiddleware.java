package middleware;

/**
 * Classe RoleCheckMiddleware. Manipulador concreto que verifica o papel do usu�rio.
 */
public class RoleCheckMiddleware extends Middleware {
    
    /**
     * Verifica o papel do usu�rio com base no email fornecido.
     *
     * @param email    O email do usu�rio.
     * @param password A senha do usu�rio.
     * @return true se o papel do usu�rio foi identificado e a cadeia de responsabilidade
     *         pode ser interrompida. Caso contr�rio, passa para o pr�ximo middleware na cadeia.
     */
    public boolean check(String email, String password) {
        // Verifica se o email pertence ao administrador.
        if (email.equals("admin@example.com")) {
            System.out.println("Hello, admin!");
            return true; // Se o usu�rio � admin, interrompe a cadeia retornando true.
        }
        
        // Caso contr�rio, o usu�rio � tratado como padr�o.
        System.out.println("Hello, user!");
        
        // Passa a verifica��o para o pr�ximo middleware, se houver.
        return checkNext(email, password);
    }
}
