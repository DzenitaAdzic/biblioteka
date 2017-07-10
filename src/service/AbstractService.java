package service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import model.Primjerak;

public abstract class AbstractService<T> {
    
    private Class<T> entityClass;
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("DerbyTest");
    EntityManager em;
    
    public AbstractService(Class<T> entityClass) {
        this.entityClass = entityClass;
        em=factory.createEntityManager();
    }
    
    public void refresh(T p){  
    	em.refresh(p);
    }       
    
    public EntityManager getEntityManager(){
        return em;
    }
    
    public void closeConnection(){
    	em.close();
    }
    
    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        if(!getEntityManager().getTransaction().isActive())
            getEntityManager().getTransaction().begin();
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    public List<T> findAllSortedAsc(String field) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));        
        cq.orderBy(getEntityManager().getCriteriaBuilder().asc(cq.from(entityClass).get(field)));
        if(!getEntityManager().getTransaction().isActive())
            getEntityManager().getTransaction().begin();
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    public List<T> findAllSortedDesc(String field) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));        
        cq.orderBy(getEntityManager().getCriteriaBuilder().desc(cq.from(entityClass).get(field)));
        if(!getEntityManager().getTransaction().isActive())
            getEntityManager().getTransaction().begin();
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    public List<T> findAllSortedAsc(String[] fields) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));        
        for(String field : fields){
            cq.orderBy(getEntityManager().getCriteriaBuilder().asc(cq.from(entityClass).get(field)));
        }        
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    public void create(T entity) throws ServiceException {        
        try {
        	if(!em.getTransaction().isActive())
                em.getTransaction().begin();
            getEntityManager().persist(entity);            
            getEntityManager().flush();
            em.getTransaction().commit();
            em.refresh(entity);
        } catch(Exception e){            
        	throw new ServiceException("Dogodila se greška. Opis greške: " + e.getLocalizedMessage());
        }
    }
    
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
    
    public List<T> findBy(Object value, String field) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
        Root t = criteriaQuery.from(entityClass);
        criteriaQuery.where(criteriaBuilder.equal(t.get(field), criteriaBuilder.parameter(String.class, field)));
        Query query = getEntityManager().createQuery(criteriaQuery);
        query.setParameter(field, value);                
        return query.getResultList();
    }
    
    public List<T> findLikeBy(Object value, String field) {    	
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
        Root t = criteriaQuery.from(entityClass);
        criteriaQuery.where(criteriaBuilder.like(t.get(field), criteriaBuilder.parameter(String.class, field)));
        Query query = getEntityManager().createQuery(criteriaQuery);
        query.setParameter(field, value);                
        return query.getResultList();
    }
    
    public void edit(T entity) throws ServiceException {
        try {   
        	if(!em.getTransaction().isActive())
                em.getTransaction().begin();
            getEntityManager().merge(entity);
            getEntityManager().flush();
            em.getTransaction().commit();
            em.refresh(entity);
        } catch(Exception e){            
             throw new ServiceException("Dogodila se greška. Opis greške: " + e.getLocalizedMessage());            
        }         
    }
    
    public void editWithoutRefresh(T entity) throws ServiceException {
        try {   
        	if(!em.getTransaction().isActive())
                em.getTransaction().begin();
            getEntityManager().merge(entity);
            getEntityManager().flush();
            em.getTransaction().commit();            
        } catch(Exception e){            
             throw new ServiceException("Dogodila se greška. Opis greške: " + e.getLocalizedMessage());            
        }         
    }
    
    public void remove(T entity) throws ServiceException {
        try {
        	if(!em.getTransaction().isActive())
                em.getTransaction().begin();
            getEntityManager().remove(getEntityManager().merge(entity));
            getEntityManager().flush();
            em.getTransaction().commit();              
        } catch (Exception e) {
            throw new ServiceException("Prije brisanja je potrebno obrisati sve objekte koji su u relaciji sa brisanim unosom.");            
        }
    }
}
