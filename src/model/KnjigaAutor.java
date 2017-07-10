package model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@IdClass(KnjigaAutorPK.class)
public class KnjigaAutor {
    
	@Id
	private int autorId;
	
	@Id
	private int knjigaId;
	  
	@Basic(optional = false)
    @Column(name = "redni_broj_autora")
    private short redniBrojAutora;
    
	@ManyToOne
    @JoinColumn(name = "autorId", updatable = false, insertable = false)
    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "knjigaId", updatable = false, insertable = false)
    private Knjiga knjiga;
    
    public int getAutorId() {
		return autorId;
	}

	public void setAutorId(int autorId) {
		this.autorId = autorId;
	}

	public int getKnjigaId() {
		return knjigaId;
	}

	public void setKnjigaId(int knjigaId) {
		this.knjigaId = knjigaId;
	}
	
    public KnjigaAutor() {
    }

    public short getRedniBrojAutora() {
        return redniBrojAutora;
    }

    public void setRedniBrojAutora(short redniBrojAutora) {
        this.redniBrojAutora = redniBrojAutora;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KnjigaAutor)) {
            return false;
        }
        KnjigaAutor other = (KnjigaAutor) object;
        if ((knjiga.getKnjigaId()!=other.getKnjiga().getKnjigaId()) || (autor.getAutorId()!=other.getAutor().getAutorId())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return knjiga.getNaslov() + " - " + autor.toString();
    }
    
}
