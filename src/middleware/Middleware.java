package middleware;
/**
 * Classe base para o middleware.
 */
public abstract class Middleware {
    // Pr�ximo middleware na cadeia de responsabilidade.
    private Middleware next;

    /**
     * Cria a cadeia de objetos middleware.
     * O m�todo 'link' permite encadear m�ltiplos objetos Middleware em sequ�ncia,
     * formando a cadeia de responsabilidade.
     */
    public static Middleware link(Middleware first, Middleware... chain) {
        Middleware head = first;
        for (Middleware nextInChain: chain) {
            // Define o pr�ximo middleware na cadeia.
            head.next = nextInChain;
            // Avan�a para o pr�ximo middleware.
            head = nextInChain;
        }
        return first; // Retorna o primeiro middleware para in�cio da cadeia.
    }

    /**
     * M�todo abstrato que ser� implementado pelas subclasses com verifica��es concretas.
     * Recebe um email e senha para realizar a valida��o espec�fica.
     */
    public abstract boolean check(String email, String password);

    /**
     * Executa a verifica��o no pr�ximo objeto da cadeia ou encerra a verifica��o
     * se estamos no �ltimo objeto da cadeia.
     * O m�todo retorna true se chegar ao fim da cadeia (sem falhas nas verifica��es).
     */
    protected boolean checkNext(String email, String password) {
        // Se n�o houver pr�ximo na cadeia, retorna true, indicando sucesso na verifica��o.
        if (next == null) {
            return true;
        }
        // Executa a verifica��o no pr�ximo middleware.
        return next.check(email, password);
    }
}

