package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "primjerak")
public class Primjerak implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "inv_broj")
    private String invBroj;
    
    @Basic(optional = false)
    @Column(name = "datum_nabavke")
    private int datumNabavke;
    
    @Column(name = "stanje")
    private String stanje;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "primjerak")
    private Collection<Rezervacija> rezervacijaCollection;
    
    @JoinColumn(name = "knjiga_id", referencedColumnName = "knjiga_id")
    @ManyToOne(optional = false)
    private Knjiga knjigaId;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "primjerak")
    private Collection<Posudba> posudbaCollection;

    public Primjerak() {
    }

    public Primjerak(String invBroj) {
        this.invBroj = invBroj;
    }

    public Primjerak(String invBroj, int datumNabavke) {
        this.invBroj = invBroj;
        this.datumNabavke = datumNabavke;
    }

    public String getInvBroj() {
        return invBroj;
    }

    public void setInvBroj(String invBroj) {
        this.invBroj = invBroj;
    }

    public int getDatumNabavke() {
        return datumNabavke;
    }

    public void setDatumNabavke(int datumNabavke) {
        this.datumNabavke = datumNabavke;
    }

    public String getStanje() {
        return stanje;
    }

    public void setStanje(String stanje) {
        this.stanje = stanje;
    }

    @XmlTransient
    public Collection<Rezervacija> getRezervacijaCollection() {
        return rezervacijaCollection;
    }

    public void setRezervacijaCollection(Collection<Rezervacija> rezervacijaCollection) {
        this.rezervacijaCollection = rezervacijaCollection;
    }

    public Knjiga getKnjigaId() {
        return knjigaId;
    }

    public void setKnjigaId(Knjiga knjigaId) {
        this.knjigaId = knjigaId;
    }

    @XmlTransient
    public Collection<Posudba> getPosudbaCollection() {
        return posudbaCollection;
    }

    public void setPosudbaCollection(Collection<Posudba> posudbaCollection) {
        this.posudbaCollection = posudbaCollection;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Primjerak)) {
            return false;
        }
        Primjerak other = (Primjerak) object;
        if ((this.invBroj == null && other.invBroj != null) || (this.invBroj != null && !this.invBroj.equals(other.invBroj))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getInvBroj() + " - " + this.knjigaId.getNaslov() + " (" + this.knjigaId.getIzdavacId().getNaziv() + " " + this.getKnjigaId().getGodinaIzdanja() + ")";
    }
    
}
