package service;

import java.util.List;
import model.Nastavnik;

public class NastavnikService extends AbstractService<Nastavnik> {

    public NastavnikService() {
        super(Nastavnik.class);
    }

    @Override
    public List<Nastavnik> findAll() {
        return super.findAll();
    }

    @Override
    public List<Nastavnik> findAllSortedAsc(String kolona) {
        return super.findAllSortedAsc(kolona);
    }

    public List<Nastavnik> findBy(String kolona, String vrijednost) {
        return super.findBy(vrijednost, kolona);
    }

    @Override
    public void create(Nastavnik user) throws ServiceException {
        super.create(user);
    }

    public Nastavnik find(Integer id) {
        return super.find(id);
    }

    @Override
    public void edit(Nastavnik user) throws ServiceException {
        super.edit(user);
    }

    @Override
    public void remove(Nastavnik user) throws ServiceException {
        super.remove(user);
    }

}
