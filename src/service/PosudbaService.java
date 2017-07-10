
package service;

import java.util.List;

import model.Posudba;



public class PosudbaService extends AbstractService<Posudba> {

    public PosudbaService() {
        super(Posudba.class);
    }

    @Override
    public List<Posudba> findAll() {
        return super.findAll();
    }

    @Override
    public List<Posudba> findAllSortedAsc(String kolona) {
        return super.findAllSortedAsc(kolona);
    }

    public List<Posudba> findBy(String kolona, String vrijednost) {
        return super.findBy(vrijednost, kolona);
    }

    @Override
    public void create(Posudba user) throws ServiceException {
        super.create(user);          
    }

    public Posudba find(Integer id) {
        return super.find(id);
    }

    @Override
    public void edit(Posudba user) throws ServiceException {
        super.edit(user);
    }

    @Override
    public void remove(Posudba user) throws ServiceException {
        super.remove(user);
    }

}

