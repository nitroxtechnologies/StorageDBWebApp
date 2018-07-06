package storagedbwebapp

import AWSAccessors.User

import java.time.LocalDateTime;

/**
 * Created by spencersharp on 6/18/18.
 */
public class LocalUser
{
    long id;
    String type;
    String firstName;
    String lastName;
    String username;
    String password;
    boolean isActive;
    LocalDateTime dateCreated;
    LocalDateTime dateUpdated;

    public LocalUser()
    {

    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setIsActive(boolean isActive)
    {
        this.isActive = isActive;
    }

    public LocalDateTime getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateUpdated()
    {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated)
    {
        this.dateUpdated = dateUpdated;
    }

    public static LocalUser createFromUser(User user)
    {
        LocalUser localUser = new LocalUser();
        localUser.setId(user.getId());
        localUser.setType(user.getType());
        localUser.setFirstName(user.getFirstName());
        localUser.setLastName(user.getLastName());
        localUser.setUsername(user.getUsername());
        localUser.setPassword(user.getPassword());
        localUser.setIsActive(user.isActive());
        localUser.setDateCreated(user.getDateCreated());
        localUser.setDateUpdated(user.getDateUpdated());

        return localUser;
    }

    public String toString()
    {
        return firstName + " " + lastName + " " + username + " " + type;
    }
}
