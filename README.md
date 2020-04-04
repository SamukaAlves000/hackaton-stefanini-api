# Treinamento Stefanini

Nessa primeira fase do Treinamento serão abordados os assuntos relacionadosa a:

Slides disponiveis em [Slides](https://github.com/Jpmmdf/projeto-web-base/blob/master/src/main/resources/Treinamento-Parte2.pptx)
* Ferramentas para Desenvolvimento
* GIT
   * Clonar Projeto
   * Executar os comandos Pull/Push/Add/Commit
* JAVA
    * Objetos e Variaveis
    * Mapeamento de Entidades
    * JPA
    * CDI
    * Bean Validation

## Requisitos

* [GIT](https://git-scm.com) -  Ferramenta para versionar o código
* [JDK](https://jdk.java.net/13/)  - Kit de Desenvolvimento Java
* [MAVEN](https://maven.apache.org/install.html) - Gerenciador de dependências
* [POSTGRESQL](https://www.postgresql.org/download/) - Sistema de Banco de Dados
* [ECLIPSE](https://www.eclipse.org/downloads/) - Ferramenta para o Desenvolvimento 
* [PGADMIN](https://www.pgadmin.org/download/) - (Opcional) Ferramenta para o Postgres
* [SOURCETREE](https://www.sourcetreeapp.com) - (Opcional) Ferramenta para o GIT

## Uso

### Importando o projeto no Eclipse

Após a configuração do ambiente, clonar esse projeto e importa-lo como Maven Project no Eclipse


# Projeto WebApp base para 2 Parte do Treinamento


## Projeto Final

* [1] -  Recuperar a entidade pessoa cheia, carregada com todos os atributos, executando somente uma Query
no Backend, hoje ele executa várias queries para recuperar os perfils e endereços, e ordenar pelo
nome da pessoa.
* [2]  - Criar um serviço no backend para se integrar com o site viacep e antes de cadastrar um endereço
recuperar as informações e mostrar na tela já preenchido.
* [3] - Criar os testes unitários.
* [4] - Adicionar o novo campo na tabela de pessoa DS_CAMINHO_IMAGEM VARCHAR(1000).
* [5] - Alterar a tela de cadastro de pessoa, para que seja exibido uma combobox com os perfis já cadastrados
e agora uma pessoa terá uma foto, com isso será necessário fazer o upload da imagem no frontend e
salvar o caminho no backend, no campo cadastrado acima.
* [6] - Extra : Fazer a paginação no Backend e não no frontend.
