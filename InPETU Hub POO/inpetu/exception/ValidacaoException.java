package inpetu.exception;

/** Dado de entrada invalido ou ausente. */
public class ValidacaoException extends NegocioException {
    public ValidacaoException(String mensagem) {
        super(mensagem);
    }
}
