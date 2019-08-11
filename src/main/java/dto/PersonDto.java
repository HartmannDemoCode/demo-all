package dto;

import entity.Person;
import java.util.ArrayList;
import java.util.List;

public class PersonDto {
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String email;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  PersonDto() {
    }

    public PersonDto(String fName, String lName, String addr1, String addr2, String email,int id) {
        this.firstName = fName;
        this.lastName = lName;
        this.address1 = addr1;
        this.address2 = addr2;
        this.email = email;
        this.id = id;
    }
    
    public static List<PersonDto> getListOfPersonDtos(List<Person> personList){
        List<PersonDto> persons = new ArrayList();
        for(Person p : personList){
            persons.add(new PersonDto(p));
        }
        return persons;
    }

    /** 
     * Create a PersonDto, given a Person Entity Class
     * @param p 
     */
    public PersonDto(Person p) {
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.address1 = p.getAddress1();
        this.address2 = p.getAddress2();
        this.email = p.getEmail();
        this.id = p.getId();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}