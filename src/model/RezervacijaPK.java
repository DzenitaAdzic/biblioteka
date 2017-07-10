package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RezervacijaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "korisnik_sifra")
    private String korisnikSifra;
    
    @Basic(optional = false)
    @Column(name = "primjerak_inv_broj")
    private String primjerakInvBroj;

    public RezervacijaPK() {
    }

    public RezervacijaPK(String korisnikSifra, String primjerakInvBroj) {
        this.korisnikSifra = korisnikSifra;
        this.primjerakInvBroj = primjerakInvBroj;
    }

    public String getKorisnikSifra() {
        return korisnikSifra;
    }

    public void setKorisnikSifra(String korisnikSifra) {
        this.korisnikSifra = korisnikSifra;
    }

    public String getPrimjerakInvBroj() {
        return primjerakInvBroj;
    }

    public void setPrimjerakInvBroj(String primjerakInvBroj) {
        this.primjerakInvBroj = primjerakInvBroj;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RezervacijaPK)) {
            return false;
        }
        RezervacijaPK other = (RezervacijaPK) object;
        if ((this.korisnikSifra == null && other.korisnikSifra != null) || (this.korisnikSifra != null && !this.korisnikSifra.equals(other.korisnikSifra))) {
            return false;
        }
        if ((this.primjerakInvBroj == null && other.primjerakInvBroj != null) || (this.primjerakInvBroj != null && !this.primjerakInvBroj.equals(other.primjerakInvBroj))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.RezervacijaPK[ korisnikSifra=" + korisnikSifra + ", primjerakInvBroj=" + primjerakInvBroj + " ]";
    }
    
}
