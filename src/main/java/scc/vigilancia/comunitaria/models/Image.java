package scc.vigilancia.comunitaria.models;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "IMAGEM")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Image {

    @Id
    @Column(name = "caminho_s3")
    private String caminhoS3;

}