package inpetu.service;

import inpetu.model.Reserva;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IReservaService {
    Reserva solicitar(int contatoId, int recursoId, LocalDate data,
                      LocalTime horaInicio, LocalTime horaFim, int qtdPessoas, String motivo);
    Reserva editar(int id, LocalDate data, LocalTime horaInicio, LocalTime horaFim,
                   int qtdPessoas, String motivo);
    Reserva consultar(int id);
    List<Reserva> listar();
    Reserva aprovar(int id);
    Reserva recusar(int id);
    Reserva cancelar(int id);
    boolean verificarDisponibilidade(int recursoId, LocalDate data,
                                     LocalTime horaInicio, LocalTime horaFim);
}
