/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author mnoora
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Kategoria extends AbstractPersistable<Long>{

    @Column
    private String nimi;
    @ManyToMany
    private List<Uutinen> uutiset;
    
}
