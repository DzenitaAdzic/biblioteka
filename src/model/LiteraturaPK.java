package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LiteraturaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "predmet_sifra")
    private String predmetSifra;
    @Basic(optional = false)
    @Column(name = "knjiga_id")
    private int knjigaId;

    public LiteraturaPK() {
    }

    public LiteraturaPK(String predmetSifra, int knjigaId) {
        this.predmetSifra = predmetSifra;
        this.knjigaId = knjigaId;
    }

    public String getPredmetSifra() {
        return predmetSifra;
    }

    public void setPredmetSifra(String predmetSifra) {
        this.predmetSifra = predmetSifra;
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
        if (!(object instanceof LiteraturaPK)) {
            return false;
        }
        LiteraturaPK other = (LiteraturaPK) object;
        if ((this.predmetSifra == null && other.predmetSifra != null) || (this.predmetSifra != null && !this.predmetSifra.equals(other.predmetSifra))) {
            return false;
        }
        if (this.knjigaId != other.knjigaId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.LiteraturaPK[ predmetSifra=" + predmetSifra + ", knjigaId=" + knjigaId + " ]";
    }
    
}
