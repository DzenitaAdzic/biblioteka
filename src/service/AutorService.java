package service;

import java.util.List;

import model.Autor;
import model.Nastavnik;

public class AutorService extends AbstractService<Autor> {

    public AutorService() {
        super(Autor.class);
    }

    @Override
    public List<Autor> findAll() {
        return super.findAll();
    }

    @Override
    public List<Autor> findAllSortedAsc(String kolona) {
        return super.findAllSortedAsc(kolona);
    }

    public List<Autor> findBy(String kolona, String vrijednost) {
        return super.findBy(vrijednost, kolona);
    }

    @Override
    public void create(Autor user) throws ServiceException {
        super.create(user);
    }

    public Autor find(Integer id) {
        return super.find(id);
    }

    @Override
    public void edit(Autor user) throws ServiceException {
        super.edit(user);
    }

    @Override
    public void remove(Autor user) throws ServiceException {
        super.remove(user);
    }

}
