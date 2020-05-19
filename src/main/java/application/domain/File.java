package application.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class File {
    @Id
    private Long id;

    private String oriName;

    private String savedName;

    private int size;
}