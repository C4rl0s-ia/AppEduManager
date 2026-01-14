# 🎓 EduManager - Sistema de Gestão Acadêmica

![Java](https://img.shields.io/badge/Java-23%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-MVC-4285F4?style=for-the-badge&logo=java&logoColor=white)
![Status](https://img.shields.io/badge/Status-Concluído-green?style=for-the-badge)

> **Um Mini-ERP acadêmico desenvolvido para demonstrar o poder da Programação Orientada a Objetos e da Arquitetura MVC.**

---

## 📄 Sobre o Projeto

O **EduManager** é uma aplicação Desktop completa desenvolvida em Java para o gerenciamento de registros escolares. O sistema permite o controle total (CRUD) de diferentes perfis acadêmicos, simulando o ambiente administrativo de uma instituição de ensino.

Este projeto transcende um simples exercício de cadastro: ele implementa persistência de dados real, geração de relatórios compatíveis com Excel e uma interface gráfica responsiva e moderna.

---

## 🛠️ Tecnologias e Arquitetura

O projeto foi construído seguindo rigorosamente o padrão **MVC (Model-View-Controller)**, garantindo desacoplamento e fácil manutenção.

### 📚 Stack Tecnológica
* **Linguagem:** Java 23+
* **Interface Gráfica:** JavaFX (com FXML)
* **Estilização:** CSS3 (Customizado com design moderno e responsivo)
* **Persistência:** Arquivos locais (`.txt`) processados com Java NIO
* **IDE Recomendada:** IntelliJ IDEA

### 🏗️ Destaques da Arquitetura

1.  **Modelagem Orientada a Objetos (OOP):**
    * **Herança:** Utilização de uma superclasse `Pessoa` para atributos comuns, estendida por `Aluno`, `Professor` e `Visitante`.
    * **Abstração:** Uso de classes abstratas para impedir a instância de entidades genéricas.

2.  **Services Avançados:**
    * **Generics (`<T>`):** Implementação de serviços genéricos para exportação e salvamento de arquivos, permitindo que o mesmo código gerencie Professores, Alunos ou Técnicos sem duplicação.
    * **Excel Compatibility:** O serviço de exportação CSV inclui tratamento de *BOM (Byte Order Mark)* para garantir que acentos (UTF-8) abram corretamente no Excel.

3.  **User Experience (UX):**
    * Feedback visual imediato com alertas personalizados e ícones.
    * Campos de formulário com estados visuais de "Leitura" vs "Edição".
    * Tabelas com formatação condicional (ex: status de matrícula colorido).

---

## 🚀 Funcionalidades

### 1. Gestão de Perfis (CRUD)
O sistema gerencia 5 tipos de usuários, cada um com atributos específicos:
* **👨‍🏫 Professor:** Especialidade e Salário.
* **🎓 Aluno:** Curso, Matrícula e Mensalidade.
* **🥇 Bolsista:** Herda de Aluno, com gestão de Bolsa (%).
* **🛠️ Técnico:** Herda de Aluno, com Registro Profissional.
* **👀 Visitante:** Perfil simplificado para acesso rápido.

### 2. Recursos do Sistema
* **Persistência Automática:** Os dados são salvos em arquivos `.txt` na pasta `data/` automaticamente. Não requer instalação de banco de dados SQL.
* **Busca Inteligente:** Localização de registros por ID único gerado pelo sistema (ex: `aln_Xy9Z`).
* **Exportação de Relatórios:** Botão dedicado para exportar qualquer tabela visualizada para `.CSV`.
* **Validação de Dados:** O sistema impede cadastros incompletos ou tipos de dados errados (ex: texto no campo idade).

---

## 📂 Estrutura do Projeto

```text
com.example.appedumanager
├── 🎮 controller   # Lógica de interação entre a Tela e o Modelo
├── 📦 model        # Classes POO (Pessoa, Aluno, Professor...)
├── ⚙️ service      # Regras de Negócio (Salvar, Ler, Exportar CSV)
├── 🛠️ util         # Utilitários (Gerador de ID, Alertas, Formatadores)
└── 🖥️ views        # Arquivos FXML e CSS
    └── image       # Ícones e assets
```

---
## 📸 Screenshots

|              Dashboard Principal              |             Painel de Cadastro             |
|:---------------------------------------------:|:----------------------------------------------:|
| ![Dashboard](https://i.imgur.com/O20PEUm.png) | ![Formulário](https://i.imgur.com/PWFagza.png) |

|              Visualização e Edição               |             Exportação CSV              |
|:------------------------------------------------:|:---------------------------------------:|
| ![Visualização](https://i.imgur.com/G8m5JVx.png) | ![CSV](https://i.imgur.com/KxTx5rm.png) |

---

## 🔧 Como Executar

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/SEU-USUARIO/EduManager.git](https://github.com/SEU-USUARIO/EduManager.git)
    ```
2.  **Importe o projeto** na sua IDE favorita (IntelliJ IDEA, Eclipse, NetBeans) como um projeto **Maven/Gradle**.
3.  **Sincronize as dependências** (JavaFX).
4.  **Execute a classe Principal:**
    * Localize a classe `com.example.appedumanager.Application`.
    * Execute o método `main`.
5.  **Pronto!** O sistema criará automaticamente a pasta de banco de dados na primeira execução.

---

## 💡 Créditos e Inspiração

Este projeto foi inspirado nas aulas de **Programação Orientada a Objetos** do **Curso em Vídeo**, ministrado pelo professor **Gustavo Guanabara**.

O conceito inicial de Herança foi expandido para uma aplicação comercial completa, adicionando camadas de serviço, persistência de arquivos e interface gráfica profissional.

---
## 👨‍💻 Autor

Desenvolvido por **Carlos Augusto da Silva Souza**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-blue?style=flat&logo=linkedin)](https://www.linkedin.com/in/carlos-augusto-da-silva-souza-43079b21a)
