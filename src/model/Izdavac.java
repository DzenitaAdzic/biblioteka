/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "izdavac")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Izdavac.findAll", query = "SELECT i FROM Izdavac i"),
    @NamedQuery(name = "Izdavac.findByIzdavacId", query = "SELECT i FROM Izdavac i WHERE i.izdavacId = :izdavacId"),
    @NamedQuery(name = "Izdavac.findByNaziv", query = "SELECT i FROM Izdavac i WHERE i.naziv = :naziv"),
    @NamedQuery(name = "Izdavac.findBySjediste", query = "SELECT i FROM Izdavac i WHERE i.sjediste = :sjediste")})
public class Izdavac implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "izdavac_id")
    private Integer izdavacId;
    @Basic(optional = false)
    @Column(name = "naziv")
    private String naziv;
    @Column(name = "sjediste")
    private String sjediste;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "izdavacId")
    private Collection<Knjiga> knjigaCollection;

    public Izdavac() {
    }

    public Izdavac(Integer izdavacId) {
        this.izdavacId = izdavacId;
    }

    public Izdavac(Integer izdavacId, String naziv) {
        this.izdavacId = izdavacId;
        this.naziv = naziv;
    }

    public Integer getIzdavacId() {
        return izdavacId;
    }

    public void setIzdavacId(Integer izdavacId) {
        this.izdavacId = izdavacId;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getSjediste() {
        return sjediste;
    }

    public void setSjediste(String sjediste) {
        this.sjediste = sjediste;
    }

    @XmlTransient
    public Collection<Knjiga> getKnjigaCollection() {
        return knjigaCollection;
    }

    public void setKnjigaCollection(Collection<Knjiga> knjigaCollection) {
        this.knjigaCollection = knjigaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (izdavacId != null ? izdavacId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Izdavac)) {
            return false;
        }
        Izdavac other = (Izdavac) object;
        if ((this.izdavacId == null && other.izdavacId != null) || (this.izdavacId != null && !this.izdavacId.equals(other.izdavacId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return naziv + " (" + sjediste + ")";
    }
    
}
