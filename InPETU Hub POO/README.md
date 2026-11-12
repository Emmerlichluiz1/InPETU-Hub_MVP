# Sistema de Reserva de Recursos - InPETU hub

MVP em Java (compativel com **Java 25**) para gestao de contatos, recursos
(salas de reuniao, auditorio, equipamentos, visitas tecnicas) e reservas do
InPETU hub. Disciplina de Programacao Orientada a Objetos - UniSENAI SC
(GR SADP 2026/1 N1).

## Como compilar e executar

Requer JDK 25 (nenhuma dependencia externa).

```bash
# na raiz do projeto
mkdir out
javac -d out $(find src -name "*.java")      # Linux/macOS
# Windows (PowerShell): javac -d out (Get-ChildItem -Recurse src -Filter *.java).FullName

java -cp out inpetu.Application             # aplicacao (menu de console)
java -cp out inpetu.teste.TesteRunner       # testes automatizados
```

Datas no formato `dd/mm/aaaa` e horas no formato `hh:mm`.

## Funcionalidades

| Modulo   | Cadastrar | Consultar | Editar | Listar | Excluir | Extras |
|----------|:---------:|:---------:|:------:|:------:|:-------:|--------|
| Contatos | x | x | x | x | x | e-mail unico |
| Recursos | x | x | x | x | x | nome unico, tipos, capacidade |
| Reservas | x | x | x | x | - | aprovar, recusar, cancelar, verificar disponibilidade |

## Regras de negocio implementadas

1. Contato ou recurso vinculado a uma reserva (em qualquer status) **nao pode ser excluido**.
2. Uma reserva so e aceita se o recurso estiver **disponivel** no periodo: nao pode haver
   reserva ativa (SOLICITADA ou APROVADA) do mesmo recurso, no mesmo dia, com horarios
   sobrepostos. Horarios que apenas se tocam (fim 10:00 / inicio 10:00) sao permitidos.
   Reservas CANCELADA ou RECUSADA liberam o horario.
3. A quantidade de pessoas nao pode exceder a capacidade do recurso.
4. Nao e permitido reservar em data passada; a hora de fim deve ser posterior a de inicio.
5. Transicoes de status: SOLICITADA -> APROVADA | RECUSADA | CANCELADA; APROVADA -> CANCELADA.
   Reservas recusadas ou canceladas nao podem ser editadas.
6. Sem controle de acesso (conforme escopo do MVP).

## Tratamento de erros

Todos os erros esperados sao `NegocioException` (`ValidacaoException`,
`RegraNegocioException`, `EntidadeNaoEncontradaException`). A interface de console captura
essas excecoes, exibe `ERRO: <mensagem>` e volta ao menu, sem encerrar a aplicacao. Entradas
nao numericas, datas e horas invalidas tambem viram `ValidacaoException`. As validacoes sao
feitas **antes** de alterar o objeto, mantendo o sistema em estado consistente apos uma falha.

## Arquitetura e principios SOLID

```
inpetu
 |- model        Entidade, Contato, Recurso, TipoRecurso, Reserva, StatusReserva
 |- exception    NegocioException e especializacoes
 |- repository   IRepository<T>, IContatoRepository, IRecursoRepository, IReservaRepository
 |   \- memory    MemoryRepository<T> (base) + implementacoes em memoria
 |- service      I...Service + implementacoes (regras de negocio)
 |- ui           Console, MenuBase, MenuContato, MenuRecurso, MenuReserva, MenuPrincipal
 |- teste        TesteRunner (roteiro automatizado)
 \- Application  composition root
```

- **S** - cada classe tem uma responsabilidade: modelo guarda estado, repositorio persiste,
  servico aplica regras, menu faz E/S.
- **O** - novos tipos de menu entram implementando `IMenu`; novas formas de persistencia
  (arquivo, banco) entram implementando as interfaces de repositorio, sem alterar servicos.
- **L** - qualquer implementacao de `IRepository<T>` pode substituir a outra; `Contato`,
  `Recurso` e `Reserva` sao usaveis onde `Entidade` e esperada.
- **I** - interfaces pequenas e especificas por agregado (`IContatoRepository`,
  `IReservaService`, ...), em vez de uma interface unica.
- **D** - servicos e menus dependem de interfaces; somente `Application` conhece as
  classes concretas e faz a injecao de dependencias pelo construtor.

Relacao com o UML do enunciado: `Empreendedor` = `Contato`; `EspacoFisico` = `Recurso`
(generalizado para tambem aceitar equipamentos e visitas); `ReservaService` com
solicitar/aprovar/recusar/cancelar/verificar conflito; `RegistroAuditoria` ficou fora do
escopo do MVP.

## Roteiro de testes

Executado por `inpetu.teste.TesteRunner` (23 casos, todos passando). Para testar manualmente
pelo menu, siga a ordem abaixo.

| # | Cenario | Passos | Resultado esperado |
|---|---------|--------|--------------------|
| T01 | Cadastrar contato | Contatos > Cadastrar: Maria / maria@ufsc.br / 48999990000 | Contato criado com ID 1 |
| T02 | Contato invalido | Cadastrar com nome vazio; e-mail "abc"; telefone vazio | `ERRO` para cada caso; nada gravado |
| T03 | E-mail duplicado | Cadastrar outro contato com maria@ufsc.br | `ERRO` de e-mail ja existente |
| T04 | Consultar / listar contato | Consultar ID 1; consultar ID 99; Listar | Dados do contato; erro "nao encontrado"; lista |
| T05 | Editar contato | Editar ID 1 com novos dados validos; depois com e-mail invalido | Atualiza; erro e dados originais preservados |
| T06 | Cadastrar recurso | Recursos > Cadastrar: Sala 1 / tipo 1 / capacidade 10 | Recurso criado com ID 1 |
| T07 | Recurso invalido | Capacidade 0 ou -3; nome vazio; nome repetido; tipo 9 | `ERRO` em todos os casos |
| T08 | Consultar / editar / listar recurso | Consultar ID 1 e 99; editar capacidade para 20; listar | Comportamento analogo ao T04 e T05 |
| T09 | Solicitar reserva | Reservas > Solicitar: contato 1, recurso 1, amanha, 09:00-10:00, 5 pessoas, "Reuniao" | Reserva ID 1, status SOLICITADA |
| T10 | Conflito de horario | Solicitar mesmo recurso/dia, 09:30-10:30 | `ERRO` de recurso indisponivel |
| T11 | Horario adjacente | Solicitar 10:00-11:00 no mesmo recurso/dia | Aceita |
| T12 | Capacidade | Solicitar 11 pessoas na Sala 1 (capacidade 10) | `ERRO` de capacidade excedida |
| T13 | Datas e horas invalidas | Data passada; 31/02/2026; hora "9h"; fim antes do inicio | `ERRO` em todos os casos |
| T14 | Contato/recurso inexistente | Solicitar com contato 99 ou recurso 99 | `ERRO` de nao encontrado |
| T15 | Editar reserva | Editar reserva 1 para 09:30-10:30 (sem outro conflito) | Atualiza sem conflitar consigo mesma |
| T16 | Editar com conflito | Editar reserva 1 para sobrepor a reserva 2 | `ERRO`; reserva 1 mantem dados originais |
| T17 | Ciclo de status | Aprovar reserva 1; aprovar de novo; recusar; cancelar; cancelar de novo | APROVADA; erros nas transicoes invalidas; CANCELADA |
| T18 | Cancelamento libera horario | Cancelar reserva e solicitar o mesmo periodo | Nova reserva aceita |
| T19 | Exclusao bloqueada | Excluir contato 1 e recurso 1 (com reservas) | `ERRO` de vinculo com reserva |
| T20 | Exclusao permitida | Cadastrar contato e recurso novos, sem reservas, e excluir | Excluidos; consulta posterior da erro de nao encontrado |
| T21 | Entrada nao numerica | Digitar "abc" em qualquer opcao ou ID | `ERRO`; aplicacao continua no menu |
| T22 | Verificar disponibilidade | Reservas > Verificar disponibilidade em periodo livre e ocupado | DISPONIVEL / INDISPONIVEL |

## Limitacoes conhecidas

- Persistencia apenas em memoria: os dados sao perdidos ao encerrar a aplicacao.
- Sem controle de acesso, conforme escopo do MVP.
