/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import service.KnjigaAutorService;
import service.ServiceException;


@Entity
@Table(name = "knjiga")
@XmlRootElement
public class Knjiga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "knjiga_id")
    private Integer knjigaId;
    
    @Basic(optional = false)
    @Column(name = "naslov")
    private String naslov;
    
    @Basic(optional = false)
    @Column(name = "broj_stranica")
    private int brojStranica;
    
    @Column(name = "godina_izdanja")
    private Integer godinaIzdanja;
    
    @Column(name = "tip")
    private Short tip;
    
    @Column(name = "negativni_bodovi")
    private Integer negativniBodovi;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "knjigaId")
    private Collection<Primjerak> primjerakCollection;
    
    @JoinColumn(name = "izdavac_id", referencedColumnName = "izdavac_id")
    @ManyToOne(optional = false)
    private Izdavac izdavacId;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "knjiga")
    private Collection<Literatura> literaturaCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "knjiga")
    private List<KnjigaAutor> knjigaAutorCollection;

    public Knjiga() {
    }

    public Knjiga(Integer knjigaId) {
        this.knjigaId = knjigaId;
    }

    public Knjiga(Integer knjigaId, String naslov, int brojStranica) {
        this.knjigaId = knjigaId;
        this.naslov = naslov;
        this.brojStranica = brojStranica;
    }

    public Integer getKnjigaId() {
        return knjigaId;
    }

    public void setKnjigaId(Integer knjigaId) {
        this.knjigaId = knjigaId;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public int getBrojStranica() {
        return brojStranica;
    }

    public void setBrojStranica(int brojStranica) {
        this.brojStranica = brojStranica;
    }

    public Integer getGodinaIzdanja() {
        return godinaIzdanja;
    }

    public void setGodinaIzdanja(Integer godinaIzdanja) {
        this.godinaIzdanja = godinaIzdanja;
    }

    public Short getTip() {
        return tip;
    }

    public void setTip(Short tip) {
        this.tip = tip;
    }

    public Integer getNegativniBodovi() {
        return negativniBodovi;
    }

    public void setNegativniBodovi(Integer negativniBodovi) {
        this.negativniBodovi = negativniBodovi;
    }

    @XmlTransient
    public Collection<Primjerak> getPrimjerakCollection() {
        return primjerakCollection;
    }

    public void setPrimjerakCollection(Collection<Primjerak> primjerakCollection) {
        this.primjerakCollection = primjerakCollection;
    }

    public Izdavac getIzdavacId() {
        return izdavacId;
    }

    public void setIzdavacId(Izdavac izdavacId) {
        this.izdavacId = izdavacId;
    }

    @XmlTransient
    public Collection<Literatura> getLiteraturaCollection() {
        return literaturaCollection;
    }

    public void setLiteraturaCollection(Collection<Literatura> literaturaCollection) {
        this.literaturaCollection = literaturaCollection;
    }

    @XmlTransient
    public List<KnjigaAutor> getKnjigaAutorCollection() {
        return knjigaAutorCollection;
    }

    public void setKnjigaAutorCollection(List<KnjigaAutor> knjigaAutorCollection) {
        this.knjigaAutorCollection = knjigaAutorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (knjigaId != null ? knjigaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Knjiga)) {
            return false;
        }
        Knjiga other = (Knjiga) object;
        if ((this.knjigaId == null && other.knjigaId != null) || (this.knjigaId != null && !this.knjigaId.equals(other.knjigaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return naslov + " (" + this.godinaIzdanja + ")";
    }
    
    public void addAutor(Autor autor, int redniBroj) {
        KnjigaAutor knjigaAutor = new KnjigaAutor();
        knjigaAutor.setAutor(autor);
        knjigaAutor.setKnjiga(this);
        knjigaAutor.setAutorId(autor.getAutorId());
        knjigaAutor.setKnjigaId(this.getKnjigaId());
        knjigaAutor.setRedniBrojAutora((short)redniBroj);

        this.knjigaAutorCollection.add(knjigaAutor);

        autor.getKnjigaAutorCollection().add(knjigaAutor);
      }
    
    public void removeAutor(KnjigaAutor autor) {
    	KnjigaAutorService s = new KnjigaAutorService();
        try {
			s.remove(autor);
		} catch (ServiceException e) {
			
		}
        
      }
    
}
