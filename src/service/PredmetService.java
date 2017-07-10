package service;

import java.util.List;

import model.Predmet;

public class PredmetService extends AbstractService<Predmet>{
	 public PredmetService() {
	        super(Predmet.class);
	    }

	    @Override
	    public List<Predmet> findAll() {
	        return super.findAll();
	    }

	    @Override
	    public List<Predmet> findAllSortedAsc(String kolona) {
	        return super.findAllSortedAsc(kolona);
	    }

	    public List<Predmet> findBy(String kolona, String vrijednost) {
	        return super.findBy(vrijednost, kolona);
	    }

	    @Override
	    public void create(Predmet p) throws ServiceException {
	        super.create(p);
	    }

	    public Predmet find(Integer id) {
	        return super.find(id);
	    }

	    @Override
	    public void edit(Predmet p) throws ServiceException {
	        super.edit(p);
	    }

	    @Override
	    public void remove(Predmet p) throws ServiceException {
	        super.remove(p);
	    }

}
