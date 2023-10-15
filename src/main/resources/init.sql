CREATE TABLE "comunidade" (
  "id" SERIAL,
  "nome" VARCHAR(255) NOT NULL,
  PRIMARY KEY ("id")
);

CREATE TABLE "usuario" (
  "email" VARCHAR(255) primary key,
  "nome" VARCHAR(255) NOT NULL,
  "senha" VARCHAR(255) NOT NULL,
  "permissao" VARCHAR(255) NOT NULL
 );

CREATE TABLE "comunidade_usuario" (
  "id_comunidade" INTEGER NOT NULL,
  "email_usuario" VARCHAR(255) NOT NULL,
  PRIMARY KEY ("id_comunidade", "email_usuario"),
  FOREIGN KEY ("id_comunidade") REFERENCES "comunidade"("id"),
  FOREIGN KEY ("email_usuario") REFERENCES "usuario"("email")
);

CREATE TABLE "postagem" (
  "id" SERIAL NOT NULL,
  "id_autor" VARCHAR(255) NOT NULL,
  "id_comunidade" INTEGER NOT NULL,
  "titulo" VARCHAR(255) NOT NULL,
  "conteudo" TEXT,
  "tipo" INTEGER NOT NULL,
  "status" INTEGER NOT NULL,
  PRIMARY KEY ("id"),
  FOREIGN KEY ("id_autor", "id_comunidade") REFERENCES "comunidade_usuario"("email_usuario", "id_comunidade")
);
--check tipo ou tabela tipo-nome
--check status ou tabela status-nome

CREATE TABLE "imagem" (
  "caminho_s3" VARCHAR(100) NOT NULL,
  PRIMARY KEY ("caminho_s3")
);

CREATE TABLE "postagem_imagem" (
  "id_postagem" INTEGER NOT NULL,
  "id_imagem" VARCHAR(100) NOT null,
  PRIMARY KEY ("id_postagem", "id_imagem"),
  FOREIGN KEY ("id_postagem") REFERENCES "postagem"("id"),
  FOREIGN KEY ("id_imagem") REFERENCES "imagem"("caminho_s3")
);

INSERT INTO public.usuario
(email, nome, senha, permissao)
VALUES('admin@admin.com', 'admin', MD5('admin'), 'ADMINISTRADOR');

INSERT INTO public.comunidade
(id, nome)
VALUES(nextval('comunidade_id_seq'::regclass), 'USP');

INSERT INTO public.comunidade_usuario
(id_comunidade, email_usuario)
VALUES(1, 'admin@admin.com');
