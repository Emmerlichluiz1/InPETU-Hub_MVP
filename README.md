# 🏢 MVP de CRM para o InPETU Hub

> Sistema web de CRM para centralizar a gestão operacional do **InPETU Hub (UFSC)**: locação de espaços, atendimento ao empreendedor, agendamento de reuniões e controle de serviços.

![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)
![Projeto](https://img.shields.io/badge/projeto-integrador-blue)
![Front-end](https://img.shields.io/badge/front--end-HTML-61DAFB)
![Back-end](https://img.shields.io/badge/back--end-JavaScript-339933)
![Banco](https://img.shields.io/badge/banco-PostgreSQL-4169E1)

Projeto Integrador da **Faculdade de Tecnologia SENAI – 2026** (Curso de Graduação Tecnológica, turma GR SADP 2026/1 N1).

---

## 📑 Sumário

- [Sobre o projeto](#-sobre-o-projeto)
- [Problema e proposta de valor](#-problema-e-proposta-de-valor)
- [Objetivos](#-objetivos)
- [Módulos do MVP](#-módulos-do-mvp)
- [Funcionalidades](#-funcionalidades)
- [Requisitos funcionais](#-requisitos-funcionais)
- [Requisitos não funcionais](#-requisitos-não-funcionais)
- [Regras de negócio](#-regras-de-negócio)
- [Modelagem de dados](#-modelagem-de-dados)
- [Protótipo no Figma](#-protótipo-no-figma)
- [Tecnologias](#-tecnologias)
- [Como executar](#-como-executar)
- [Cronograma](#-cronograma)
- [Validação do MVP](#-validação-do-mvp)
- [Pontos em aberto](#-pontos-em-aberto)
- [Equipe](#-equipe)
- [Referências](#-referências)

---

## 📖 Sobre o projeto

O **InPETU Hub** é o centro de inovação, pesquisa, empreendedorismo e tecnologia da Universidade Federal de Santa Catarina (UFSC), integrado ao **Sapiens Parque**, no Norte da Ilha de Florianópolis/SC. Ele conecta acadêmicos, startups e o ecossistema corporativo do parque (cerca de 130 instituições e empresas residentes).

Para sustentar essa missão, o Hub lida diariamente com locação de espaços de coworking, atendimento a empreendedores, agendamento de reuniões e controle de serviços. Hoje esses processos dependem de formulários espalhados e e-mails.

Este projeto desenvolve um **MVP de CRM customizado** que substitui esses controles descentralizados por uma plataforma unificada.

## 🎯 Problema e proposta de valor

**Problema:** descentralização na gestão dos espaços de trabalho, falta de histórico estruturado de atendimento aos empreendedores e conflitos na agenda de reuniões e serviços.

**Proposta de valor:** centralizar em uma única plataforma web a operação do Hub, permitindo agendar espaços sem atrito, acompanhar o funil de atendimento ao empreendedor e controlar visualmente os serviços prestados.

## 🧭 Objetivos

**Objetivo geral:** desenvolver um MVP de CRM para a gestão e operação do InPETU Hub (locação de espaços, atendimento ao empreendedor, agendamento de reuniões e controle de serviços).

**Objetivos específicos:**

1. Mapear e estruturar o fluxo de atendimento aos empreendedores e solicitações de serviços.
2. Criar um módulo centralizado para agendamento de salas, reuniões e locação de espaços de trabalho colaborativo.
3. Desenvolver o controle e acompanhamento do histórico de atendimentos e serviços prestados.
4. Validar as funcionalidades essenciais do MVP com a equipe gestora e usuários do Hub.

> ⭐ **Prioridade absoluta definida pelo cliente:** **Histórico de Relacionamento** e **Motivo da Visita**. A equipe precisa saber quem visitou o Hub, por qual razão e o que foi tratado no atendimento.

## 🧩 Módulos do MVP

| # | Módulo | Objetivo |
|---|--------|----------|
| 1 | **Locação de Espaços** | Reserva e disponibilidade das áreas físicas. |
| 2 | **Atendimento ao Empreendedor** | Ciclo de vida e histórico de empreendedores e startups no Hub. |
| 3 | **Agendamento de Reuniões** | Gestão da agenda de mentores, gestores e empreendedores. |
| 4 | **Controle de Serviços** | Acompanhamento de solicitações e ordens de serviço prestadas aos residentes. |
| 5 | **Governança, Perfis de Acesso e Métricas** | Estrutura de usuários, relatórios e indicadores (ex.: taxa de ocupação, atendimentos no mês, receita por locação). |

## ✨ Funcionalidades

| Funcionalidade | Prioridade | Tecnologia |
|----------------|:----------:|------------|
| Cadastro de Empreendedores e Empresas | Essencial | JS / PostgreSQL |
| Agendamento de Reuniões e Espaços | Essencial | HTML / JS |
| Gestão de Atendimentos e Solicitações | Essencial | HTML /  CSS |
| Painel / Controle de Serviços Prestados | Essencial | HTML / JS |
| Notificações Automáticas de Lembrete | Desejável | Nodemailer / API |

**Fluxo de reserva (versão inicial):** qualquer pessoa solicita a reserva por formulário → o sistema mostra a disponibilidade → a equipe **aprova ou recusa manualmente** → a reserva é confirmada e fica registrada no histórico. Reservas também podem ser canceladas, com registro do cancelamento.

## 📋 Requisitos funcionais

<details>
<summary><b>Ver os 20 requisitos funcionais</b></summary>

| ID | Requisito | Descrição | Módulo |
|----|-----------|-----------|--------|
| RF01 | Cadastro de empreendedores | Cadastro obrigatório com Nome, E-mail, CPF, Canal de Origem e Espaço de interesse. | Atendimento |
| RF02 | Perfis de usuário | Múltiplos perfis (Admin, Equipe, Professor, Aluno, Empresa) com permissões específicas. | Governança |
| RF03 | Restrição de acesso por perfil | Funcionalidades e dados restritos conforme o perfil logado. | Governança |
| RF04 | Tipos de espaço reserváveis | Reserva de sala de reunião, auditório e coworking. | Locação |
| RF05 | Formulário de reserva | Formulário online aberto a qualquer solicitante. | Locação |
| RF06 | Exibição de disponibilidade | Mostra data/horário disponíveis antes da confirmação. | Locação |
| RF07 | Aprovação manual de reservas | Toda reserva passa por aprovação manual da equipe. | Locação |
| RF08 | Histórico de reservas | Histórico centralizado com sala, horário, solicitante e finalidade. | Locação |
| RF09 | Regras de uso | Antecedência mínima e limite de horas por usuário/empresa. | Locação |
| RF10 | Indicação de cobrança | Sinaliza e registra se a reserva envolve cobrança. | Locação |
| RF11 | Notificações automáticas | Notifica solicitante e equipe sobre o status da reserva. | Locação |
| RF12 | Registro de visita | Registra o motivo da visita/atendimento de cada empreendedor. | Atendimento |
| RF13 | Histórico de relacionamento | Histórico por empreendedor: atendimentos, mentorias, demandas. | Atendimento |
| RF14 | Origem e categorização | Registro da origem e categoria das visitas/solicitações. | Atendimento |
| RF15 | Acompanhamento de solicitações | Status e prazo de serviços (suporte, mentorias, demandas). | Serviços |
| RF16 | Status das solicitações | Pendente, Em andamento, Concluída ou Cancelada. | Serviços |
| RF17 | Informações dos espaços | Serviços e recursos oferecidos em cada espaço. | Locação |
| RF18 | Gestão dos espaços | Cadastro e gerenciamento dos espaços disponíveis. | Locação |
| RF19 | Origem da visita | Registro do canal: site, indicação, evento ou outros. | Atendimento |
| RF20 | Controle de acesso | Perfil define o que cada usuário pode ver, cadastrar, alterar ou excluir. | Governança |

</details>

## ⚙️ Requisitos não funcionais

<details>
<summary><b>Ver os 15 requisitos não funcionais</b></summary>

| ID | Requisito | Descrição |
|----|-----------|-----------|
| RNF01 | Usabilidade | Interface simples e intuitiva para todos os perfis, inclusive leigos. |
| RNF02 | Segurança e privacidade | Dados pessoais tratados conforme a LGPD. |
| RNF03 | Controle de acesso | Controle de acesso baseado em perfil. |
| RNF04 | Disponibilidade | Consulta de espaços acessível 24/7. |
| RNF05 | Confiabilidade | Backup para evitar perda de histórico. |
| RNF06 | Auditabilidade | Logs de aprovações, alterações e cancelamentos. |
| RNF07 | Performance | Consultas rápidas mesmo com crescimento da base. |
| RNF08 | Interoperabilidade | Integração com a Prefeitura de Florianópolis via API ou protocolo definido. |
| RNF09 | Escalabilidade | Suporte a crescimento de volume sem perda de desempenho. |
| RNF10 | Manutenibilidade | Arquitetura modular para expansão futura. |
| RNF11 | Tempo de notificação | Notificações de status em no máximo 2 minutos após a alteração. |
| RNF12 | Proteção de dados (LGPD) | Nome, CPF e e-mail com acesso restrito por perfil e armazenamento seguro. |
| RNF13 | Desempenho do histórico | Buscas no histórico com resposta inferior a 1 segundo. |
| RNF14 | Facilidade no formulário aberto | Interface intuitiva para o público externo, sem treinamento prévio. |
| RNF15 | Validação de entradas | Validação dos campos obrigatórios e bloqueio de cadastros duplicados (ex.: CPF repetido). |

</details>

## 📐 Regras de negócio

| ID | Regra | Descrição |
|----|-------|-----------|
| RN01 | Cadastro de empreendedor | Todo empreendedor possui nome completo, e-mail, CPF, canal de origem e espaço de interesse. |
| RN02 | Unicidade do cadastro | CPF e e-mail não podem estar vinculados a mais de um cadastro ativo. |
| RN03 | Reserva de espaço | Apenas pessoas cadastradas reservam; a reserva só é confirmada após aprovação da equipe. |
| RN04 | Disponibilidade | Um espaço não pode ter duas reservas confirmadas no mesmo horário. |
| RN05 | Cancelamento | A equipe ou o usuário autorizado pode cancelar; o cancelamento fica no histórico. |
| RN06 | Histórico de relacionamento | Todo atendimento, visita, solicitação ou interação relevante é registrado. |
| RN07 | Motivo da visita | Todo atendimento ou visita tem motivo ou categoria informada. |
| RN08 | Aprovação de reserva | Somente equipe ou gestores autorizados aprovam ou recusam reservas. |
| RN09 | Registro de alterações | Mudanças importantes em reservas, cadastros e solicitações guardam data e usuário responsável. |
| RN10 | Solicitações de serviço | Toda solicitação tem solicitante, tipo de serviço, status e acompanhamento. |

## 🗄️ Modelagem de dados

**Modelo conceitual** — sete entidades: `Empreendedor`, `Empresa`, `Reserva`, `Espaço`, `Tipo de Espaço`, `Usuário` e `Atendimento`.

| Relacionamento | Descrição |
|----------------|-----------|
| Empreendedor → Empresa | reúne |
| Empreendedor → Reserva (de um Espaço) | solicita |
| Usuário → Reserva | decide |
| Usuário → Espaço | gerencia |
| Espaço → Tipo de Espaço | é categorizado por |
| Empreendedor → Usuário | acessa |
| Empreendedor → Atendimento | recebe |
| Usuário → Atendimento | realiza |

**Modelo lógico (versão atual):**

| Tabela | Atributos |
|--------|-----------|
| `EMPREENDEDOR` | id_empreendedor (PK), nome, cpf, origem, telefone, email |
| `EMPRESA` | id_empresa (PK), razao_social, cnpj |
| `ESPACO` | id_espaco (PK), nome, capacidade, valor_hora |
| `TIPO_ESPACO` | id_tipo_espaco (PK), nome |
| `RESERVA` | id_reserva (PK), data_reserva, horario_inicio, hora_fim, quantidade_pessoas, finalidade, status, cobranca |
| `USUARIO` | id_usuario (PK), nome, email, senha, perfil |
| `ATENDIMENTO` | id_atendimento (PK), data_hora, tipo, motivo |
| `ACESSA` | tabela associativa Empreendedor × Usuário |

> ⚠️ **O modelo lógico está em revisão.** Algumas chaves estrangeiras precisam ser movidas para o lado "N" das relações, faltam entidades (Solicitação de Serviço, Canal de Origem, Perfil/Permissão, Log de Auditoria, Recursos do Espaço, Notificação) e a senha deve ser armazenada apenas como **hash** (bcrypt/argon2). Os diagramas ficam em [`/docs`](./docs).

## 🎨 Protótipo no Figma

Veja o protótipo navegável do site no Figma:

👉 **[Acessar protótipo no Figma](https://www.figma.com/design/WDIbkEQ5KdmgwHm0GyAbqD/InPETUHub?node-id=0-1&t=5yuVTe8WsqZzrBta-1)**

## 🛠️ Tecnologias

| Camada | Tecnologias |
|--------|-------------|
| Front-end | HTML, CSS |
| Back-end | JavaScript |
| Banco de dados | PostgreSQL |
| Notificações | Nodemailer |
| Prototipação e design | Figma |
| Gestão e versionamento | GitHub |

## 🚀 Como executar

O projeto é feito com HTML, CSS e JavaScript puros, então não precisa
instalar dependências nem fazer build.

Pré-requisitos: um navegador moderno (Chrome, Edge, Firefox) e,
opcionalmente, o Git e o VS Code com a extensão Live Server.

bash
# 1. Clonar o repositório
git clone https://github.com/<organizacao>/<repositorio>.git
cd <repositorio>

Opção 1 — Abrir direto no navegador: dê dois cliques no arquivo index.html.

Opção 2 — Com Live Server (recomendado): abra a pasta no VS Code,
clique com o botão direito em index.html e escolha Open with Live
Server. A página recarrega sozinha a cada alteração.

Opção 3 — Servidor local pelo terminal:

bash
# Com Python
python -m http.server 5500

Depois acesse http://localhost:5500 no navegador.

📁 Estrutura de pastas sugerida

.

├── index.html

├── css/

│   └── style.css

├── js/

│   └── script.js

├── assets/

│   └── img/

├── docs/

└── README.md


📝 Ajuste a estrutura acima conforme a organização real do repositório.

## 🗓️ Cronograma

| Etapa | Atividade | Início | Término |
|:-----:|-----------|:------:|:-------:|
| 1 | Elicitação e mapeamento de requisitos operacionais | 20/08 | 30/08 |
| 2 | Prototipação e modelagem da arquitetura do MVP | 31/08 | 25/09 |
| 3 | Desenvolvimento do core (Espaços, Reuniões e Atendimento) | 25/09 | 05/11 |
| 4 | Testes integrados, homologação e ajustes | 05/11 | 25/11 |
| 5 | Apresentação final, validação com o InPETU Hub e entrega dos artefatos | 25/11 | 05/12 |

## ✅ Validação do MVP

- **Testes internos:** validação técnica das funcionalidades de agendamento e cadastro, conduzida pela equipe de QA.
- **Validação com usuários-chave:** apresentação de telas e fluxos à gestão do InPETU Hub, com cenários reais (como simular a locação de um espaço e registrar o atendimento a uma nova startup do Sapiens Parque) para coletar feedback antes de fechar a versão.

## ❓ Pontos em aberto

- **Integração com a Prefeitura de Florianópolis:** funcionamento técnico da API/sistema e quais dados serão trocados.
- **SLA:** campos e etapas necessários nas solicitações de serviço para medir o cumprimento de prazos.
- **Regras de reserva:** antecedência mínima e limite de horas por perfil de usuário.
- **Painel inicial:** indicadores que a gestão precisa ver além de taxa de ocupação e volume de atendimentos.
- **Cobrança:** se há cobrança pelo uso dos espaços e para quais perfis.

## 👥 Equipe

| Integrante |
|------------|
| Gabriel Alves Poleto |
| Isadora Silveira Nunes |
| Luiz Eduardo Emmerlich |
| Matheus Trevisan Ribeiro |
| Yan Vinicios Rios Costa |

**Orientadora:** Milena Maredmi Correa Teixeira Veiga

**Instituição:** Faculdade de Tecnologia SENAI – Florianópolis, Santa Catarina

**Turma:** GR SADP 2026/1 N1

## 📚 Referências

- INPETU HUB / UFSC. *Conheça o InPETU Hub: Inovação e Pesquisa da UFSC.*
- SAPIENS PARQUE. *O Parque Tecnológico da Inovação em Florianópolis.*
- PRESSMAN, Roger S.; MAXIM, Bruce R. *Engenharia de Software: Uma Abordagem Profissional.* 8. ed. Porto Alegre: AMGH, 2016.

---

<p align="center">Projeto acadêmico · Faculdade de Tecnologia SENAI · 2026</p>
