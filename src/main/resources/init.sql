CREATE TABLE "COMUNIDADE" (
  "id" SERIAL,
  "nome" VARCHAR(255) NOT NULL,
  PRIMARY KEY ("id")
);

CREATE TABLE "USUARIO" (
  "email" VARCHAR(255) primary key,
  "nome" VARCHAR(255) NOT NULL,
  "senha" VARCHAR(255) NOT NULL,
  "permissao" INTEGER NOT NULL
 );

CREATE TABLE "COMUNIDADE_USUARIO" (
  "id_comunidade" INTEGER NOT NULL,
  "email_usuario" VARCHAR(255) NOT NULL,
  PRIMARY KEY ("id_comunidade", "email_usuario"),
  FOREIGN KEY ("id_comunidade") REFERENCES "COMUNIDADE"("id"),
  FOREIGN KEY ("email_usuario") REFERENCES "USUARIO"("email")
);

CREATE TABLE "POSTAGEM" (
  "id" SERIAL NOT NULL,
  "id_autor" VARCHAR(255) NOT NULL,
  "id_comunidade" INTEGER NOT NULL,
  "titulo" VARCHAR(255) NOT NULL,
  "conteudo" TEXT,
  "tipo" INTEGER NOT NULL,
  "status" INTEGER NOT NULL,
  PRIMARY KEY ("id"),
  FOREIGN KEY ("id_autor", "id_comunidade") REFERENCES "COMUNIDADE_USUARIO"("email_usuario", "id_comunidade")
);
//check tipo ou tabela tipo-nome
//check status ou tabela status-nome

CREATE TABLE "IMAGEM" (
  "caminho_s3" VARCHAR(100) NOT NULL,
  PRIMARY KEY ("caminho_s3")
);


CREATE TABLE "POSTAGEM_IMAGEM" (
  "id_postagem" INTEGER NOT NULL,
  "id_imagem" VARCHAR(100) NOT null,
  PRIMARY KEY ("id_postagem", "id_imagem"),
  FOREIGN KEY ("id_postagem") REFERENCES "POSTAGEM"("id"),
  FOREIGN KEY ("id_imagem") REFERENCES "IMAGEM"("caminho_s3")
);