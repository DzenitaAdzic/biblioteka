package model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@IdClass(RezervacijaPK.class)
public class Rezervacija {
	@Id
	private String korisnikSifra;
	
	@Id
	private String primjerakInvBroj;
    
    @Basic(optional = false)
    @Column(name = "rok_preuzimanja")
    private long rokPreuzimanja;
    
    @Column(name = "datum_preuzimanja")
    private long datumPreuzimanja;
    
    @ManyToOne
    @JoinColumn(name = "korisnik_sifra", referencedColumnName = "sifra")    
    private Korisnik korisnik;
    
    @ManyToOne
    @JoinColumn(name = "primjerak_inv_broj", referencedColumnName = "inv_broj")    
    private Primjerak primjerak;

    public Rezervacija() {
    }

    public void setKorisnikSifra(String korisnikSifra){    	
    	this.korisnikSifra = korisnikSifra;    	
    }
    
    public void setPrimjerakInvBroj(String primjerakInvBroj){
    	this.primjerakInvBroj = primjerakInvBroj;
    }
    
    public String getKorisnikSifra(){
    	return this.korisnikSifra;
    }
    
    public String getPrimjerakInvBroj(){
    	return this.primjerakInvBroj;
    }
    
    public long getRokPreuzimanja() {
        return rokPreuzimanja;
    }

    public void setRokPreuzimanja(long rokPreuzimanja) {
        this.rokPreuzimanja = rokPreuzimanja;
    }

    public long getDatumPreuzimanja() {
        return datumPreuzimanja;
    }

    public void setDatumPreuzimanja(long datumPreuzimanja) {
        this.datumPreuzimanja = datumPreuzimanja;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
    	this.korisnikSifra = korisnik.getSifra();
        this.korisnik = korisnik;
    }

    public Primjerak getPrimjerak() {
        return primjerak;
    }

    public void setPrimjerak(Primjerak primjerak) {
    	this.primjerakInvBroj = primjerak.getInvBroj();
        this.primjerak = primjerak;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rezervacija)) {
            return false;
        }
        Rezervacija other = (Rezervacija) object;
        if ((other.korisnikSifra!=null && other.primjerakInvBroj!=null) && (!other.korisnikSifra.equals(korisnikSifra) || !other.primjerakInvBroj.equals(primjerakInvBroj))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Rezervacija korisnika " + this.korisnik.toString();
    }
    
}
