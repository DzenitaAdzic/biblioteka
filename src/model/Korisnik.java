package model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "korisnik")
public class Korisnik implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    private String sifra;
    
    @Basic(optional = false)
    private String ime;
    
    @Basic(optional = false)
    private String prezime;
    
    private String korisnickoIme;
    
    private Integer uloga;
    
    private Integer negativniBodovi;
    
    private String password;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "korisnik")
    private Collection<Rezervacija> rezervacijaCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "korisnik")
    private Collection<Posudba> posudbaCollection;

    public Korisnik() {
    }

    public Korisnik(String sifra) {
        this.sifra = sifra;
    }

    public String getPassword(){
    	return this.password;
    }
    
    public void setPassword(String password){
    	this.password = password;
    }
    
    public String getKorisnickoIme(){
    	return this.korisnickoIme;
    }
    
    public void setKorisnickoIme(String korisnickoIme){
    	this.korisnickoIme = korisnickoIme;
    }
    
    public Integer getUloga(){
    	return this.uloga;
    }
    
    public void setUloga(Integer uloga){
    	this.uloga = uloga;
    }
    
    public Korisnik(String sifra, String ime, String prezime) {
        this.sifra = sifra;
        this.ime = ime;
        this.prezime = prezime;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
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

    public Integer getNegativniBodovi() {
        return negativniBodovi;
    }

    public void setNegativniBodovi(Integer negativniBodovi) {
        this.negativniBodovi = negativniBodovi;
    }

    @XmlTransient
    public Collection<Rezervacija> getRezervacijaCollection() {
        return rezervacijaCollection;
    }

    public void setRezervacijaCollection(Collection<Rezervacija> rezervacijaCollection) {
        this.rezervacijaCollection = rezervacijaCollection;
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
        if (!(object instanceof Korisnik)) {
            return false;
        }
        Korisnik other = (Korisnik) object;
        if ((this.sifra == null && other.sifra != null) || (this.sifra != null && !this.sifra.equals(other.sifra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Korisnik[ sifra=" + sifra + " ]";
    }
    
}
