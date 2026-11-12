package inpetu.ui;

import inpetu.exception.ValidacaoException;
import inpetu.model.TipoRecurso;
import inpetu.service.IRecursoService;

public class MenuRecurso extends MenuBase {
    private final IRecursoService service;

    public MenuRecurso(Console console, IRecursoService service) {
        super(console);
        this.service = service;
    }

    @Override
    public String titulo() {
        return "Cadastro de Recursos";
    }

    @Override
    protected String[] opcoes() {
        return new String[]{"Cadastrar", "Consultar", "Editar", "Listar", "Excluir"};
    }

    @Override
    protected void tratar(int opcao) {
        switch (opcao) {
            case 1 -> console.mensagem("Cadastrado: " + service.cadastrar(
                    console.texto("Nome"), lerTipo(), console.inteiro("Capacidade"),
                    console.texto("Descricao")));
            case 2 -> console.mensagem(service.consultar(console.inteiro("ID")).toString());
            case 3 -> console.mensagem("Atualizado: " + service.editar(console.inteiro("ID"),
                    console.texto("Novo nome"), lerTipo(), console.inteiro("Nova capacidade"),
                    console.texto("Nova descricao")));
            case 4 -> {
                var recursos = service.listar();
                if (recursos.isEmpty()) {
                    console.mensagem("Nenhum recurso cadastrado.");
                }
                recursos.forEach(r -> console.mensagem(r.toString()));
            }
            case 5 -> {
                service.remover(console.inteiro("ID"));
                console.mensagem("Recurso excluido.");
            }
            default -> console.mensagem("Opcao invalida.");
        }
    }

    private TipoRecurso lerTipo() {
        TipoRecurso[] tipos = TipoRecurso.values();
        for (int i = 0; i < tipos.length; i++) {
            console.mensagem("  " + (i + 1) + " - " + tipos[i].getDescricao());
        }
        int escolha = console.inteiro("Tipo");
        if (escolha < 1 || escolha > tipos.length) {
            throw new ValidacaoException("Tipo de recurso invalido.");
        }
        return tipos[escolha - 1];
    }
}
