package dbfacade;

import entity.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public class PersonFacade {
    
    
    private static EntityManagerFactory emf;
    private static PersonFacade instance;
    
    private PersonFacade(){}
    
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf){
        if(instance == null){
             emf = _emf;
            instance = new PersonFacade();
        }
        return instance; 
    }
    
    public Person addPerson(String fn,String ln, String a1, String a2,String email){
        Person p = new Person(fn,ln,a1,a2,email);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
        
        return p; 
    }
    
    public List<Person> getPersons(){
         EntityManager em = emf.createEntityManager();
         List<Person> persons;
        try {
            persons = em.createQuery("select p from Person p").getResultList();
        }
        finally {
            em.close();
        }
        return persons;
    }
    
    public Person getPerson(int id){
         EntityManager em = emf.createEntityManager();
         Person person;
        try {
            person = em.find(Person.class,id);
        }
        finally {
            em.close();
        }
        return person;
    }
    
    
}
