package es.iescarrillo.android.ejemplofirebasetodo.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

import es.iescarrillo.android.ejemplofirebasetodo.enums.Provider;

@IgnoreExtraProperties
public class Person implements Serializable {

    private String id;
    private String name;
    private String surname;
    private Integer age;
    private String email;
    private Provider provider;
    private String uid;

    public Person(){
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", provider='" + provider + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
