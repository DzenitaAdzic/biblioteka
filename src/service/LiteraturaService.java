package service;

import java.util.List;

import model.Knjiga;
import model.Literatura;

public class LiteraturaService extends AbstractService<Literatura>{
	
    public LiteraturaService() {
        super(Literatura.class);
    }

    @Override
    public List<Literatura> findAll() {
        return super.findAll();
    }

    @Override
    public List<Literatura> findAllSortedAsc(String kolona) {
        return super.findAllSortedAsc(kolona);
    }

    public List<Literatura> findBy(String kolona, String vrijednost) {
        return super.findBy(vrijednost, kolona);
    }

    @Override
    public void create(Literatura l) throws ServiceException {
        super.create(l);
    }

    public Literatura find(Integer id) {
        return super.find(id);
    }

    @Override
    public void edit(Literatura l) throws ServiceException {
        super.edit(l);
    }

    @Override
    public void remove(Literatura l) throws ServiceException {
        super.remove(l);
    }

}
