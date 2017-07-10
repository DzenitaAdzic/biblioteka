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
@IdClass(PosudbaPK.class)
public class Posudba implements Serializable {
    @Basic(optional = false)
    @Column(name = "datum_posudbe")
    private long datumPosudbe;
    
    @Basic(optional = false)
    @Column(name = "rok_vracanja")
    private int rokVracanja;
    
    @Column(name = "datum_vracanja")
    private long datumVracanja;
    
    @Id
	private String korisnikSifra;
	
	@Id
	private String primjerakInvBroj;
    
    @ManyToOne
    @JoinColumn(name = "korisnik_sifra", referencedColumnName = "sifra")    
    private Korisnik korisnik;
    
    @ManyToOne
    @JoinColumn(name = "primjerak_inv_broj", referencedColumnName = "inv_broj")    
    private Primjerak primjerak;


    public long getDatumPosudbe() {
        return datumPosudbe;
    }

    public void setDatumPosudbe(long datumPosudbe) {
        this.datumPosudbe = datumPosudbe;
    }

    public int getRokVracanja() {
        return rokVracanja;
    }

    public void setRokVracanja(int rokVracanja) {
        this.rokVracanja = rokVracanja;
    }

    public long getDatumVracanja() {
        return datumVracanja;
    }

    public void setDatumVracanja(long datumVracanja) {
        this.datumVracanja = datumVracanja;
    }

    public Posudba() {
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
        if (!(object instanceof Posudba)) {
            return false;
        }
        Posudba other = (Posudba) object;
        if ((other.korisnikSifra!=null && other.primjerakInvBroj!=null) && (!other.korisnikSifra.equals(korisnikSifra) || !other.primjerakInvBroj.equals(primjerakInvBroj))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Posudba";
    }
    
}
