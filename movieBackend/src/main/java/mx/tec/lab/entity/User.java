package mx.tec.lab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users", uniqueConstraints={@UniqueConstraint(columnNames={"email"}), @UniqueConstraint(columnNames={"username"})})
public class User {
    @Id
    @GeneratedValue
    private long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column(name = "email")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Column(name = "username")
    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;






    /* SETTERS AND GETTERS */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}

