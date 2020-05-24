package application.domain;

import lombok.Builder;
import lombok.Generated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oriName;

    private String savedName;

    private Long size;

    @Builder
    public File(String oriName, String savedName, Long size){
        this.oriName = oriName;
        this.savedName = savedName;
        this.size = size;
    }
}