# VigCom - Backend
Solução de apoio à vigilância de comunidades

## Time
- Eduardo Maciel de Matos **[PROJECT OWNER] [FULLSTACK]** <a href="https://github.com/edumaciel10" target="_blank">(Perfil)</a>
- Gustavo de Oliveira Martins **[FRONTEND]** <a href="https://github.com/GustavoOM" target="_blank">(Perfil)</a>
- Hugo Ferreira Gabriel Vieira **[BACKEND]** <a href="https://github.com/hugoferreirj" target="_blank">(Perfil)</a>
- João Otávio da Silva **[FULLSTACK]** <a href="https://github.com/joao3" target="_blank">(Perfil)</a>
- João Pedro Ribeiro da Silva **[BACKEND]** <a href="https://github.com/ribe3iro" target="_blank">(Perfil)</a>
- Leonardo Minoru Iwashima **[FRONTEND]** <a href="https://github.com/lminoru" target="_blank">(Perfil)</a>
- Raquel de Jesus Santos Valadão **[SCRUM MASTER] [BACKEND]** <a href="https://github.com/raquelvaladao" target="_blank">(Perfil)</a>

## Especificações do projeto
- Spring 2.7x
- Java 11
- Maven 3.8.4
- PostgreSQL

## Como rodar
- Essa aplicação possui um arquivo *docker-compose.yaml* na raíz do repositório, contendo os containers: da aplicação, do banco já populado com alguns dados iniciais (init.sql) e do PgAdmin para visualização de dados do banco.
- Estando **dentro da pasta raíz** do repositório e com o Docker ligado, digite o comando num terminal como o Git Bash:
```
make dev
```
- O resultado final no Docker deve ser como o seguinte, e para ver os logs de um container clique na parte cinza em algum container
  ![Exemplo de Imagem](readme_imgs/containers.png)


## Como acessar o backend
- O backend do container está na porta 8080, então basta fazer http://localhost:8080. O caminho do swagger é http://localhost:8080/swagger-ui.html

## Como acessar o client do banco
- O PgAdmin é um client do banco PostgreSQL. Ele possui credenciais para entrar e estão no docker compose. Mas estão abaixo também. Acesse http://localhost:16543 e faça login com as credenciais abaixo
  - email: test@gmail.com
  - senha: postgres
- Assim que entrar, conecte no banco da seguinte forma e vc poderá executar queries SQL:
  - 1: Acesse http://localhost:16543. A página pode demorar um pouco pra carregar. Adicione o email e senha especificados

    ![Exemplo de Imagem](readme_imgs/login.png)
  - 2: Inclua o container do banco do client como a seguir. O login e senha do banco estão no docker-compose. O login é "postgres" e a senha é "postgres". E clique em Save
    
    ![Exemplo de Imagem](readme_imgs/config.png)
  - 3: Você verá no menu lateral direito que o PgAdmin se conectou ao banco. Vá em Tools>Query Tool para adicionar uma query
    
    ![Exemplo de Imagem](readme_imgs/query.png)
  - 4: Faça a query desejada e clique no botão de Play
    ![Exemplo de Imagem](readme_imgs/select.png)


## Observações
- O docker-compose faz referência a um arquivo **.env**, que só existe localmente e **não** está na raíz do projeto. Apenas os devs têm esse arquivo, contendo os seguintes dados:
```
AWS_ACCESS_KEY_ID=access_key_aws
AWS_SECRET_ACCESS_KEY=secret_key_aws
S3_BUCKET=nome_do_bucket_pra_salvar_foto
DB_USERNAME=user banco
DB_PASSWORD=senha banco
DB_SERVER=url banco
```
- O docker-compose usa automaticamente o application-docker.yaml e faz referencia ao banco na AWS. Não é necessário fazer mudanças.
- Caso hajam mudanças no backend, basta rodar o comando do docker compose descrito acima para as mudanças serem aplicadas e os novos containers rodarem
