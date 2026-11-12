package inpetu.exception;

/** Erro de negocio esperado (validacao, regra violada, entidade ausente). */
public class NegocioException extends RuntimeException {
    public NegocioException(String mensagem) {
        super(mensagem);
    }
}
