package inpetu.ui;

import inpetu.service.IReservaService;

public class MenuReserva extends MenuBase {
    private final IReservaService service;

    public MenuReserva(Console console, IReservaService service) {
        super(console);
        this.service = service;
    }

    @Override
    public String titulo() {
        return "Gestao de Reservas";
    }

    @Override
    protected String[] opcoes() {
        return new String[]{"Solicitar reserva", "Consultar", "Editar", "Listar",
                "Aprovar", "Recusar", "Cancelar", "Verificar disponibilidade"};
    }

    @Override
    protected void tratar(int opcao) {
        switch (opcao) {
            case 1 -> console.mensagem("Solicitada: " + service.solicitar(
                    console.inteiro("ID do contato"), console.inteiro("ID do recurso"),
                    console.data("Data"), console.hora("Hora de inicio"),
                    console.hora("Hora de fim"), console.inteiro("Qtd. de pessoas"),
                    console.texto("Motivo")));
            case 2 -> console.mensagem(service.consultar(console.inteiro("ID")).toString());
            case 3 -> console.mensagem("Atualizada: " + service.editar(console.inteiro("ID"),
                    console.data("Nova data"), console.hora("Nova hora de inicio"),
                    console.hora("Nova hora de fim"), console.inteiro("Nova qtd. de pessoas"),
                    console.texto("Novo motivo")));
            case 4 -> {
                var reservas = service.listar();
                if (reservas.isEmpty()) {
                    console.mensagem("Nenhuma reserva cadastrada.");
                }
                reservas.forEach(r -> console.mensagem(r.toString()));
            }
            case 5 -> console.mensagem("Aprovada: " + service.aprovar(console.inteiro("ID")));
            case 6 -> console.mensagem("Recusada: " + service.recusar(console.inteiro("ID")));
            case 7 -> console.mensagem("Cancelada: " + service.cancelar(console.inteiro("ID")));
            case 8 -> console.mensagem(service.verificarDisponibilidade(
                    console.inteiro("ID do recurso"), console.data("Data"),
                    console.hora("Hora de inicio"), console.hora("Hora de fim"))
                    ? "Recurso DISPONIVEL no periodo." : "Recurso INDISPONIVEL no periodo.");
            default -> console.mensagem("Opcao invalida.");
        }
    }
}
