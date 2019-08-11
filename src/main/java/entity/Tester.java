package entity;

import dbfacade.PersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;


public class Tester {
    public static void main(String[] args) {
        
        EntityManagerFactory emf = EntityManagerFactoryCreator.getEntityManagerFactory(
                "pu", 
                "jdbc:mysql://localhost:3307/mydb", 
                "dev", 
                "ax2", 
                EntityManagerFactoryCreator.Strategy.DROP_AND_CREATE);
        PersonFacade facade = PersonFacade.getPersonFacade(emf);
        facade.addPerson("Kurt","Wonnegut","Sowhere","Somewhere 2","a@b.dk");
        facade.addPerson("Hanne","Wonnegut","Sowhere","Somewhere 2","h@b.dk");
        
        List<Person> persons = facade.getPersons();
        for(Person p : persons){
            System.out.println(p);
        }
        
        
//        EntityManager em = emf.createEntityManager();
//        try{
//            Person p = new Person("Kurt","Wonnegut","Sowhere","Sowhere 2","a@b.dk");
//            em.getTransaction().begin();
//            em.persist(p);
//            em.getTransaction().commit();
//        
//        }finally{
//            em.close();
//        }
        
    }
}
