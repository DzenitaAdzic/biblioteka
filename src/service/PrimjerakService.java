package service;

import java.util.List;

import model.Primjerak;

public class PrimjerakService extends AbstractService<Primjerak> {

    public PrimjerakService() {
        super(Primjerak.class);
    }

    @Override
    public List<Primjerak> findAll() {
        return super.findAll();
    }

    @Override
    public List<Primjerak> findAllSortedAsc(String kolona) {
        return super.findAllSortedAsc(kolona);
    }

    public List<Primjerak> findBy(String kolona, String vrijednost) {
        return super.findBy(vrijednost, kolona);
    }

    @Override
    public void create(Primjerak object) throws ServiceException {
        super.create(object);
    }

    public Primjerak find(Integer id) {
        return super.find(id);
    }

    @Override
    public void edit(Primjerak object) throws ServiceException {
        super.edit(object);
    }

    @Override
    public void remove(Primjerak object) throws ServiceException {
        super.remove(object);
    }

}
