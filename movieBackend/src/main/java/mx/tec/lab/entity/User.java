package mx.tec.lab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @JsonIgnore
    private long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column(name = "email", unique = true)
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Column(name = "username", unique = true)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<UserMovieWatchList> watchLists;

    @JsonIgnore
    @OneToMany
    private Set<User> followingUsers;


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

    public Set<UserMovieWatchList> getWatchLists() {
        return watchLists;
    }

    public void setWatchLists(Set<UserMovieWatchList> watchLists) {
        this.watchLists = watchLists;
    }

    @JsonIgnore
    public List<UserMovieWatchList> getWatchListsAsList() {
        return new ArrayList<>(watchLists);
    }

    public Set<User> getFollowingUsers() {
        return followingUsers;
    }

    public void setFollowingUsers(Set<User> followingUsers) {
        this.followingUsers = followingUsers;
    }

    public void appendUserToFollowingList(User user) {
        if (user != null) {
            this.followingUsers.add(user);
        }
    }

    public void removeFromFollowingList(User user) {
        if (user != null) {
            this.followingUsers.remove(user);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(email, user.email) &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, email, username);
    }
}

