package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "literatura")
public class Literatura implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId    
    protected LiteraturaPK literaturaPK;
    
    @Column(name = "obavezna")
    private Short obavezna;
    
    @Column(name = "vaznost")
    private String vaznost;
    
    @JoinColumn(name = "predmet_sifra", referencedColumnName = "sifra", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Predmet predmet;
    
    @JoinColumn(name = "knjiga_id", referencedColumnName = "knjiga_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Knjiga knjiga;

    public Literatura() {
    }

    public Literatura(LiteraturaPK literaturaPK) {
        this.literaturaPK = literaturaPK;
    }

    public Literatura(String predmetSifra, int knjigaId) {
        this.literaturaPK = new LiteraturaPK(predmetSifra, knjigaId);
    }

    public LiteraturaPK getLiteraturaPK() {
        return literaturaPK;
    }

    public void setLiteraturaPK(LiteraturaPK literaturaPK) {
        this.literaturaPK = literaturaPK;
    }

    public Short getObavezna() {
        return obavezna;
    }

    public void setObavezna(Short obavezna) {
        this.obavezna = obavezna;
    }

    public String getVaznost() {
        return vaznost;
    }

    public void setVaznost(String vaznost) {
        this.vaznost = vaznost;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
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
        if (!(object instanceof Literatura)) {
            return false;
        }
        Literatura other = (Literatura) object;
        if ((this.literaturaPK == null && other.literaturaPK != null) || (this.literaturaPK != null && !this.literaturaPK.equals(other.literaturaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Literatura[ literaturaPK=" + literaturaPK + " ]";
    }
    
}
