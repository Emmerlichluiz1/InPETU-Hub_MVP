package inpetu.exception;

public class EntidadeNaoEncontradaException extends NegocioException {
    public EntidadeNaoEncontradaException(String entidade, int id) {
        super(entidade + " com ID " + id + " nao encontrado(a).");
    }
}
