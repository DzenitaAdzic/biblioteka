package service;

import java.util.List;

import model.Knjiga;

public class KnjigaService extends AbstractService<Knjiga> {

    public KnjigaService() {
        super(Knjiga.class);
    }

    @Override
    public List<Knjiga> findAll() {
        return super.findAll();
    }

    @Override
    public List<Knjiga> findAllSortedAsc(String kolona) {
        return super.findAllSortedAsc(kolona);
    }

    public List<Knjiga> findBy(String kolona, String vrijednost) {
        return super.findBy(vrijednost, kolona);
    }

    @Override
    public void create(Knjiga user) throws ServiceException {
        super.create(user);
    }

    public Knjiga find(Integer id) {
        return super.find(id);
    }

    @Override
    public void edit(Knjiga user) throws ServiceException {
        super.edit(user);
    }

    @Override
    public void remove(Knjiga user) throws ServiceException {
        super.remove(user);
    }

}
