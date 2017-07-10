package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

public class KnjigaAutorPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "autor_id")
    private int autorId;
    
    @Basic(optional = false)
    @Column(name = "knjiga_id")
    private int knjigaId;

    public KnjigaAutorPK() {
    	
    }

    public KnjigaAutorPK(int autorId, int knjigaId) {
        this.autorId = autorId;
        this.knjigaId = knjigaId;
    }

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

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KnjigaAutorPK)) {
            return false;
        }
        KnjigaAutorPK other = (KnjigaAutorPK) object;
        if (this.autorId != other.autorId) {
            return false;
        }
        if (this.knjigaId != other.knjigaId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.KnjigaAutorPK[ autorId=" + autorId + ", knjigaId=" + knjigaId + " ]";
    }
    
}
