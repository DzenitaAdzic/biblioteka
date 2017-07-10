package model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class Nastavnik extends Korisnik {
    
	private String akademskoZvanje;
    
    @JoinTable(name = "nastavnik_predmet", joinColumns = {
    @JoinColumn(name = "sifra", referencedColumnName = "sifra")}, inverseJoinColumns = {
    @JoinColumn(name = "predmet_sifra", referencedColumnName = "sifra")})
    @ManyToMany
    private Collection<Predmet> predmetCollection;
    
    public Nastavnik() {
    	super();
    }

    public String getAkademskoZvanje() {
        return akademskoZvanje;
    }

    public void setAkademskoZvanje(String akademskoZvanje) {
        this.akademskoZvanje = akademskoZvanje;
    }

    @XmlTransient
    public Collection<Predmet> getPredmetCollection() {
        return predmetCollection;
    }

    public void setPredmetCollection(Collection<Predmet> predmetCollection) {
        this.predmetCollection = predmetCollection;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nastavnik)) {
            return false;
        }
        Nastavnik other = (Nastavnik) object;
        if ((this.getSifra() == null && other.getSifra() != null) || (this.getSifra() != null && !this.getSifra().equals(other.getSifra()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
    	return "Nastavnik: " + this.getIme() + " " + this.getPrezime();
    }
    
}
