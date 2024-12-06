package middleware;

/**
 * Classe ThrottlingMiddleware, um manipulador concreto.
 * Verifica se o limite de tentativas de login falhas foi excedido.
 */
public class ThrottlingMiddleware extends Middleware {
    private int requestPerMinute; // Limite m�ximo de requisi��es por minuto.
    private int request;          // Contador de requisi��es feitas dentro do per�odo atual.
    private long currentTime;     // Marca o momento atual em milissegundos para monitorar o per�odo de 1 minuto.

    /**
     * Construtor que define o limite de requisi��es por minuto.
     * Inicializa o tempo atual para controlar o intervalo de requisi��es.
     *
     * @param requestPerMinute Limite de requisi��es por minuto permitido.
     */
    public ThrottlingMiddleware(int requestPerMinute) {
        this.requestPerMinute = requestPerMinute;
        this.currentTime = System.currentTimeMillis(); // Define o tempo atual.
    }

    /**
     * O m�todo 'check' verifica o n�mero de requisi��es e controla se elas est�o dentro do limite permitido.
     * Se o limite de requisi��es for excedido, o sistema exibe uma mensagem e encerra a execu��o da thread.
     * Caso contr�rio, passa a solicita��o para o pr�ximo middleware na cadeia, se existir.
     *
     * Observa��o: o m�todo checkNext() pode ser chamado no in�cio ou no fim deste m�todo,
     * o que permite flexibilidade na ordem das verifica��es e na forma como o encadeamento � realizado.
     *
     * @param email    O email do usu�rio (n�o utilizado diretamente aqui).
     * @param password A senha do usu�rio (n�o utilizado diretamente aqui).
     * @return true se a requisi��o foi aprovada ou se a verifica��o da cadeia foi bem-sucedida.
     */
    public boolean check(String email, String password) {
        // Verifica se passou mais de 1 minuto desde a �ltima requisi��o.
        if (System.currentTimeMillis() > currentTime + 60_000) {
            // Reinicia o contador de requisi��es e redefine o tempo atual.
            request = 0;
            currentTime = System.currentTimeMillis();
        }

        // Incrementa o contador de requisi��es.
        request++;
        
        // Se o n�mero de requisi��es exceder o limite definido, bloqueia o acesso.
        if (request > requestPerMinute) {
            System.out.println("Request limit exceeded!");
            Thread.currentThread().stop(); // Encerra a thread atual (uso em produ��o n�o recomendado).
        }

        // Chama o pr�ximo middleware na cadeia, se houver.
        return checkNext(email, password);
    }
}
