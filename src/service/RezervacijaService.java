package service;

import java.util.List;

import model.Rezervacija;

public class RezervacijaService extends AbstractService<Rezervacija>{
	 public RezervacijaService() {
	        super(Rezervacija.class);
	    }

	    @Override
	    public List<Rezervacija> findAll() {
	        return super.findAll();
	    }

	    @Override
	    public List<Rezervacija> findAllSortedAsc(String kolona) {
	        return super.findAllSortedAsc(kolona);
	    }

	    public List<Rezervacija> findBy(String kolona, String vrijednost) {
	        return super.findBy(vrijednost, kolona);
	    }

	    @Override
	    public void create(Rezervacija r) throws ServiceException {
	        super.create(r);
	    }

	    public Rezervacija find(Integer id) {
	        return super.find(id);
	    }

	    @Override
	    public void edit(Rezervacija r) throws ServiceException {
	        super.edit(r);
	    }

	    @Override
	    public void remove(Rezervacija r) throws ServiceException {
	        super.remove(r);
	    }
}
