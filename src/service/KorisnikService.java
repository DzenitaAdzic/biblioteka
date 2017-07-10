package service;

import java.util.List;
import model.Korisnik;

public class KorisnikService extends AbstractService<Korisnik> {

    public KorisnikService() {
        super(Korisnik.class);
    }

    @Override
    public List<Korisnik> findAll() {
        return super.findAll();
    }

    @Override
    public List<Korisnik> findAllSortedAsc(String kolona) {
        return super.findAllSortedAsc(kolona);
    }

    public List<Korisnik> findBy(String kolona, String vrijednost) {
        return super.findBy(vrijednost, kolona);
    }

    @Override
    public void create(Korisnik user) throws ServiceException {
        super.create(user);
    }

    public Korisnik find(Integer id) {
        return super.find(id);
    }

    @Override
    public void edit(Korisnik user) throws ServiceException {
        super.edit(user);
    }

    @Override
    public void remove(Korisnik user) throws ServiceException {
        super.remove(user);
    }

}
