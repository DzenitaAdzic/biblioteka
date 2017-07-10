package service;

import java.util.List;
import model.Student;

public class StudentService extends AbstractService<Student> {

    public StudentService() {
        super(Student.class);
    }

    @Override
    public List<Student> findAll() {
        return super.findAll();
    }

    @Override
    public List<Student> findAllSortedAsc(String kolona) {
        return super.findAllSortedAsc(kolona);
    }

    public List<Student> findBy(String kolona, String vrijednost) {
        return super.findBy(vrijednost, kolona);
    }

    @Override
    public void create(Student user) throws ServiceException {
        super.create(user);
    }

    public Student find(Integer id) {
        return super.find(id);
    }

    @Override
    public void edit(Student user) throws ServiceException {
        super.edit(user);
    }

    @Override
    public void remove(Student user) throws ServiceException {
        super.remove(user);
    }

}

