package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "predmet")
public class Predmet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "sifra")
    private String sifra;
    
    @Basic(optional = false)
    @Column(name = "naziv")
    private String naziv;
    
    @Basic(optional = false)
    @Column(name = "skraceni_naziv")
    private String skraceniNaziv;
    
    @Basic(optional = false)
    @Column(name = "semestar")
    private short semestar;
    
    @ManyToMany(mappedBy = "predmetCollection")
    private Collection<Nastavnik> nastavnikCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "predmet")
    private Collection<Literatura> literaturaCollection;

    public Predmet() {
    }

    public Predmet(String sifra) {
        this.sifra = sifra;
    }

    public Predmet(String sifra, String naziv, String skraceniNaziv, short semestar) {
        this.sifra = sifra;
        this.naziv = naziv;
        this.skraceniNaziv = skraceniNaziv;
        this.semestar = semestar;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getSkraceniNaziv() {
        return skraceniNaziv;
    }

    public void setSkraceniNaziv(String skraceniNaziv) {
        this.skraceniNaziv = skraceniNaziv;
    }

    public short getSemestar() {
        return semestar;
    }

    public void setSemestar(short semestar) {
        this.semestar = semestar;
    }

    @XmlTransient
    public Collection<Nastavnik> getNastavnikCollection() {
        return nastavnikCollection;
    }

    public void setNastavnikCollection(Collection<Nastavnik> nastavnikCollection) {
        this.nastavnikCollection = nastavnikCollection;
    }

    @XmlTransient
    public Collection<Literatura> getLiteraturaCollection() {
        return literaturaCollection;
    }

    public void setLiteraturaCollection(Collection<Literatura> literaturaCollection) {
        this.literaturaCollection = literaturaCollection;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Predmet)) {
            return false;
        }
        Predmet other = (Predmet) object;
        if ((this.sifra == null && other.sifra != null) || (this.sifra != null && !this.sifra.equals(other.sifra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.skraceniNaziv + " " + this.naziv;
    }
    
}
