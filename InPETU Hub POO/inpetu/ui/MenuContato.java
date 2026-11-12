package inpetu.ui;

import inpetu.service.IContatoService;

public class MenuContato extends MenuBase {
    private final IContatoService service;

    public MenuContato(Console console, IContatoService service) {
        super(console);
        this.service = service;
    }

    @Override
    public String titulo() {
        return "Cadastro de Contatos";
    }

    @Override
    protected String[] opcoes() {
        return new String[]{"Cadastrar", "Consultar", "Editar", "Listar", "Excluir"};
    }

    @Override
    protected void tratar(int opcao) {
        switch (opcao) {
            case 1 -> {
                var contato = service.cadastrar(
                        console.texto("Nome"), console.texto("E-mail"), console.texto("Telefone"),
                        console.texto("Nome da empresa (opcional)"),
                        console.texto("Motivo da visita"));
                console.mensagem(">>> Contato cadastrado com ID: " + contato.getId());
                console.mensagem(contato.toString());
            }
            case 2 -> console.mensagem(service.consultar(console.inteiro("ID")).toString());
            case 3 -> console.mensagem("Atualizado: " + service.editar(console.inteiro("ID"),
                    console.texto("Novo nome"), console.texto("Novo e-mail"),
                    console.texto("Novo telefone"),
                    console.texto("Novo nome da empresa"),
                    console.texto("Novo motivo da visita")));
            case 4 -> {
                var contatos = service.listar();
                if (contatos.isEmpty()) {
                    console.mensagem("Nenhum contato cadastrado.");
                }
                contatos.forEach(c -> console.mensagem(c.toString()));
            }
            case 5 -> {
                service.remover(console.inteiro("ID"));
                console.mensagem("Contato excluido.");
            }
            default -> console.mensagem("Opcao invalida.");
        }
    }
}
