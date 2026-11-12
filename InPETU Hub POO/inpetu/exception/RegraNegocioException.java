package inpetu.exception;

/** Violacao de regra de negocio (vinculo com reserva, conflito de horario, etc.). */
public class RegraNegocioException extends NegocioException {
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
