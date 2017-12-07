/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.domain;

/**
 *
 * @author mnoora
 */
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.*;
import javax.persistence.Column;


@AllArgsConstructor
@Data
@Entity
public class Uutinen extends AbstractPersistable<Long>{
    
    @Column
    private String otsikko;
    @Column
    private String ingressi;
    @Column
    private String teksti;
    @Column
    private LocalDateTime time;
    @Lob
    @Basic(fetch=FetchType.LAZY)
    private byte[] content;
    
    @ManyToMany(mappedBy="uutiset")
    private List<Kirjoittaja> kirjoittajat;
    
    @ManyToMany(mappedBy="uutiset")
    private List<Kategoria> kategoriat;
    
    
    
    public Uutinen(){
        this.time=LocalDateTime.now();
        this.kategoriat=new ArrayList<>();
        this.kirjoittajat=new ArrayList<>();
    }
    
    
}
