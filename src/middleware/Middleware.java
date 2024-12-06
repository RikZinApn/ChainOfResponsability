package middleware;
/**
 * Classe base para o middleware.
 */
public abstract class Middleware {
    // Próximo middleware na cadeia de responsabilidade.
    private Middleware next;

    /**
     * Cria a cadeia de objetos middleware.
     * O método 'link' permite encadear múltiplos objetos Middleware em sequência,
     * formando a cadeia de responsabilidade.
     */
    public static Middleware link(Middleware first, Middleware... chain) {
        Middleware head = first;
        for (Middleware nextInChain: chain) {
            // Define o próximo middleware na cadeia.
            head.next = nextInChain;
            // Avança para o próximo middleware.
            head = nextInChain;
        }
        return first; // Retorna o primeiro middleware para início da cadeia.
    }

    /**
     * Método abstrato que será implementado pelas subclasses com verificações concretas.
     * Recebe um email e senha para realizar a validação específica.
     */
    public abstract boolean check(String email, String password);

    /**
     * Executa a verificação no próximo objeto da cadeia ou encerra a verificação
     * se estamos no último objeto da cadeia.
     * O método retorna true se chegar ao fim da cadeia (sem falhas nas verificações).
     */
    protected boolean checkNext(String email, String password) {
        // Se não houver próximo na cadeia, retorna true, indicando sucesso na verificação.
        if (next == null) {
            return true;
        }
        // Executa a verificação no próximo middleware.
        return next.check(email, password);
    }
}

