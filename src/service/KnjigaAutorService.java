package service;

import java.util.List;

import model.Knjiga;
import model.KnjigaAutor;

public class KnjigaAutorService extends AbstractService<KnjigaAutor> {

    public KnjigaAutorService() {
        super(KnjigaAutor.class);
    }

    @Override
    public List<KnjigaAutor> findAll() {
        return super.findAll();
    }

    @Override
    public List<KnjigaAutor> findAllSortedAsc(String kolona) {
        return super.findAllSortedAsc(kolona);
    }

    public List<KnjigaAutor> findBy(String kolona, String vrijednost) {
        return super.findBy(vrijednost, kolona);
    }

    @Override
    public void create(KnjigaAutor user) throws ServiceException {
        super.create(user);
    }

    public KnjigaAutor find(Integer id) {
        return super.find(id);
    }

    @Override
    public void edit(KnjigaAutor user) throws ServiceException {
        super.edit(user);
    }

    @Override
    public void remove(KnjigaAutor user) throws ServiceException {
        super.remove(user);
    }

}
