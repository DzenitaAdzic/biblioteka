package service;

import java.util.List;

import model.Izdavac;

public class IzdavacService extends AbstractService<Izdavac> {

    public IzdavacService() {
        super(Izdavac.class);
    }

    @Override
    public List<Izdavac> findAll() {
        return super.findAll();
    }

    @Override
    public List<Izdavac> findAllSortedAsc(String kolona) {
        return super.findAllSortedAsc(kolona);
    }

    public List<Izdavac> findBy(String kolona, String vrijednost) {
        return super.findBy(vrijednost, kolona);
    }

    @Override
    public void create(Izdavac user) throws ServiceException {
        super.create(user);
    }

    public Izdavac find(Integer id) {
        return super.find(id);
    }

    @Override
    public void edit(Izdavac user) throws ServiceException {
        super.edit(user);
    }

    @Override
    public void remove(Izdavac user) throws ServiceException {
        super.remove(user);
    }

}
