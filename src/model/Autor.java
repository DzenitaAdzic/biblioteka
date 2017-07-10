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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "autor")
public class Autor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "autor_id")
    private Integer autorId;
    
    @Basic(optional = false)
    @Column(name = "ime")
    private String ime;
    
    @Basic(optional = false)
    @Column(name = "prezime")
    private String prezime;
    
    @OneToMany(mappedBy = "autor")
    private Collection<KnjigaAutor> knjigaAutorCollection;

    public Autor() {
    }

    public Autor(Integer autorId) {
        this.autorId = autorId;
    }

    public Autor(Integer autorId, String ime, String prezime) {
        this.autorId = autorId;
        this.ime = ime;
        this.prezime = prezime;
    }

    public Integer getAutorId() {
        return autorId;
    }

    public void setAutorId(Integer autorId) {
        this.autorId = autorId;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    @XmlTransient
    public Collection<KnjigaAutor> getKnjigaAutorCollection() {
        return knjigaAutorCollection;
    }

    public void setKnjigaAutorCollection(Collection<KnjigaAutor> knjigaAutorCollection) {
        this.knjigaAutorCollection = knjigaAutorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (autorId != null ? autorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Autor)) {
            return false;
        }
        Autor other = (Autor) object;
        if ((this.autorId == null && other.autorId != null) || (this.autorId != null && !this.autorId.equals(other.autorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.ime + " " + this.prezime;
    }
    
}
