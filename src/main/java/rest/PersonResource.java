package rest;

import dbfacade.PersonFacade;
import dto.PersonDto;
import entity.EntityManagerFactoryCreator;
import entity.Person;
import error.ResourceNotFound;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;



@Path("person")
public class PersonResource {

    private static EntityManagerFactory emf = EntityManagerFactoryCreator.getEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/mydb",
            //"jdbc:mysql://localhost:3306/mydb",
            "dev",
            "ax2",
            EntityManagerFactoryCreator.Strategy.CREATE);
    private static PersonFacade facade = PersonFacade.getPersonFacade(emf);
    
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PersonResource
     */
    public PersonResource() {
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDto getPerson(@PathParam("id") int id) throws ResourceNotFound {
       Person p = facade.getPerson(id);
       if(p == null){
           throw new ResourceNotFound("No person found with id "+id);
       }
       return new PersonDto(p);
    }
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonDto> getPersons() throws ResourceNotFound {
       List<Person> persons = facade.getPersons();
       return PersonDto.getListOfPersonDtos(persons);
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMsg()  {
       return "{\"msg\": \"Hardcoded JSON string\"}";
    }

}
