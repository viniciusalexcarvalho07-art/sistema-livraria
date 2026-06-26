# 📚 Sistema de Livraria

Aplicação Java com interface gráfica em **Swing** para gerenciamento de livros (CRUD), demonstrando conceitos de **Programação Orientada a Objetos**: herança, polimorfismo e collections com persistência em **SQLite**.

## ✅ Requisitos Atendidos

- ✅ Interface gráfica com `java.swing` (tabela + formulário dinâmico)
- ✅ CRUD completo (Novo, Salvar, Editar, Deletar)
- ✅ **Herança**: classe abstrata `Livro` com subclasses `LivroFisico` e `Ebook`
- ✅ **Polimorfismo**: `calcularValorFinal()` e `getTipo()` com comportamentos distintos
- ✅ **Collections**: `ArrayList<Livro>` para gerenciar lista de livros
- ✅ **Armazenamento permanente**: SQLite com SQL puro (sem ORM)

## 🏗️ Arquitetura

```
src/main/java/com/livraria/
├── Main.java                    → Ponto de entrada
├── model/
│   ├── Livro.java              → Classe abstrata base
│   ├── LivroFisico.java        → Subclasse: valor + R$ 5.00
│   └── Ebook.java              → Subclasse: valor * 0.85
├── database/
│   └── DatabaseManager.java    → Gerencia conexões SQLite
├── dao/
│   └── LivroDAO.java           → CRUD com SQL puro
└── ui/
    └── JanelaLivraria.java     → Interface Swing
```

## 🔧 Gradle

Gradle é uma ferramenta de **automação de build** — ela compila o código, baixa dependências, roda testes e empacota o projeto em um `.jar` executável. É a alternativa moderna ao Maven, usando uma DSL baseada em Groovy/Kotlin em vez de XML.

### Por que foi usado neste projeto?

Sem o Gradle, seria necessário baixar manualmente o JAR do SQLite, colocá-lo no classpath e rodar o `javac` com os caminhos corretos — algo frágil e difícil de reproduzir em outra máquina.

- **Gerenciamento de dependências**: declara `sqlite-jdbc` no `build.gradle` e o Gradle baixa a versão correta automaticamente do Maven Central, sem precisar baixar JARs manualmente
- **Build portátil**: o Gradle Wrapper (`gradlew`) permite compilar e executar em qualquer máquina que tenha apenas o JDK, sem precisar instalar o Gradle globalmente
- **Plugins**: foi usado o Spotless para formatar o código com Google Java Format, garantindo consistência sem esforço manual
- **Produtividade**: comandos como `./gradlew run` compilam e executam em uma única etapa, sem precisar lidar com `javac`, `java -cp` ou scripts shell

### Como funciona por baixo dos panos?

```
./gradlew run
      │
      ▼
┌─────────────────┐
│ gradlew (script) │  → Baixa o Gradle se necessário (wrapper)
└────────┬────────┘
         ▼
┌─────────────────┐
│  build.gradle    │  → Lê plugins, dependências e tarefas
└────────┬────────┘
         ▼
┌─────────────────┐
│  Resolve deps    │  → Baixa sqlite-jdbc do Maven Central
└────────┬────────┘
         ▼
┌─────────────────┐
│  Compila .java   │  → javac compila src/ → build/classes/
└────────┬────────┘
         ▼
┌─────────────────┐
│  Executa Main    │  → Roda a aplicação com a classe no classpath
└─────────────────┘
```

Cada etapa é uma **task** do Gradle. O `build.gradle` define as tasks e suas dependências entre si — o Gradle monta um grafo e executa na ordem correta, reaproveitando o que já foi feito (incremental).

## 📋 Requisitos

Para compilar e executar o projeto é necessário apenas:

- **JDK 21+** — o Gradle Wrapper já cuida do resto

Nada mais precisa ser instalado. O wrapper (`gradlew`) baixa a versão correta do Gradle e o `build.gradle` resolve todas as dependências automaticamente.

## 🎯 Conceitos OOP

### Herança
```java
public abstract class Livro { ... }
public class LivroFisico extends Livro { ... }
public class Ebook extends Livro { ... }
```

### Polimorfismo
```java
LivroFisico: calcularValorFinal() → valor_base + 5.0
Ebook:       calcularValorFinal() → valor_base * 0.85
```

### Collections
```java
List<Livro> livros = new ArrayList<>();
// Armazena instâncias de LivroFisico e Ebook polimorficamente
```

## 💾 Banco de Dados

SQLite com 2 tabelas:

**`livro_fisico`**
- id, titulo, descricao, isbn (único), valor_base, numero_paginas, editora

**`ebook`**
- id, titulo, descricao, isbn (único), valor_base, tamanho_arquivo_mb

## 🚀 Como Executar

### Formatar Código (opcional)
Apenas para organização do código, não afeta o funcionamento.
```bash
./gradlew spotlessApply
```

### Compilar
```bash
./gradlew clean build
```

### Executar
```bash
./gradlew run
```

## 🎮 Como Usar

### Adicionar Livro Físico
1. Clique em **"Novo"**
2. Selecione **"LIVRO FÍSICO"**
3. Preencha: Título, ISBN, Descrição, Valor Base, Nº Páginas, Editora
4. Clique **"Salvar"**

→ Preço Final = Valor Base + R$ 5,00 (taxa de envio)

### Adicionar Ebook
1. Clique em **"Novo"**
2. Selecione **"EBOOK"**
3. Preencha: Título, ISBN, Descrição, Valor Base, Tamanho (MB)
4. Clique **"Salvar"**

→ Preço Final = Valor Base * 0,85 (15% desconto)

### Editar
- Clique em qualquer livro na tabela
- Modifique os dados
- Clique **"Salvar"**

### Deletar
- Clique em qualquer livro
- Clique **"Deletar"**
- Confirme

## 📊 Exemplo de Dados

| ID | Tipo | Título | ISBN | Valor Base | Valor Final |
|----|------|--------|------|-----------|------------|
| 1 | LIVRO FÍSICO | Clean Code | 978-0132350884 | 79.90 | 84.90 |
| 2 | EBOOK | Pragmatic | 978-0201616224 | 39.00 | 33.15 |

## 🔒 Segurança

- **PreparedStatement** - Previne SQL Injection
- **Try-with-resources** - Fecha conexões automaticamente
- **Validação de entrada** - Campos obrigatórios

## 📦 Dependências

```gradle
implementation 'org.xerial:sqlite-jdbc:3.45.1.0'
```

Plugin:
```gradle
id 'com.diffplug.spotless' version '6.25.0'
```

## 💡 Notas Técnicas

- **Java**: 21
- **Build**: Gradle 9.3.0
- **Padrão**: DAO para separação de camadas
- **SQL**: Queries em texto puro, sem ORM
- **Formatação**: Google Java Format com Spotless

## 📁 Banco de Dados

Arquivo: `livraria.db` (SQLite)
- Criado automaticamente na primeira execução
- Localizado na raiz do projeto

## ✨ Destaques

- Herança real (não é só toString override)
- Polimorfismo em ação na lista de livros
- Interface dinâmica que adapta campos ao tipo
- SQL explícito com PreparedStatement
- Código limpo e profissional
