package middleware;

/**
 * Classe ThrottlingMiddleware, um manipulador concreto.
 * Verifica se o limite de tentativas de login falhas foi excedido.
 */
public class ThrottlingMiddleware extends Middleware {
    private int requestPerMinute; // Limite máximo de requisições por minuto.
    private int request;          // Contador de requisições feitas dentro do período atual.
    private long currentTime;     // Marca o momento atual em milissegundos para monitorar o período de 1 minuto.

    /**
     * Construtor que define o limite de requisições por minuto.
     * Inicializa o tempo atual para controlar o intervalo de requisições.
     *
     * @param requestPerMinute Limite de requisições por minuto permitido.
     */
    public ThrottlingMiddleware(int requestPerMinute) {
        this.requestPerMinute = requestPerMinute;
        this.currentTime = System.currentTimeMillis(); // Define o tempo atual.
    }

    /**
     * O método 'check' verifica o número de requisições e controla se elas estão dentro do limite permitido.
     * Se o limite de requisições for excedido, o sistema exibe uma mensagem e encerra a execução da thread.
     * Caso contrário, passa a solicitação para o próximo middleware na cadeia, se existir.
     *
     * Observação: o método checkNext() pode ser chamado no início ou no fim deste método,
     * o que permite flexibilidade na ordem das verificações e na forma como o encadeamento é realizado.
     *
     * @param email    O email do usuário (não utilizado diretamente aqui).
     * @param password A senha do usuário (não utilizado diretamente aqui).
     * @return true se a requisição foi aprovada ou se a verificação da cadeia foi bem-sucedida.
     */
    public boolean check(String email, String password) {
        // Verifica se passou mais de 1 minuto desde a última requisição.
        if (System.currentTimeMillis() > currentTime + 60_000) {
            // Reinicia o contador de requisições e redefine o tempo atual.
            request = 0;
            currentTime = System.currentTimeMillis();
        }

        // Incrementa o contador de requisições.
        request++;
        
        // Se o número de requisições exceder o limite definido, bloqueia o acesso.
        if (request > requestPerMinute) {
            System.out.println("Request limit exceeded!");
            Thread.currentThread().stop(); // Encerra a thread atual (uso em produção não recomendado).
        }

        // Chama o próximo middleware na cadeia, se houver.
        return checkNext(email, password);
    }
}
