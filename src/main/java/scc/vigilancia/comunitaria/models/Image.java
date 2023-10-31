package scc.vigilancia.comunitaria.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

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