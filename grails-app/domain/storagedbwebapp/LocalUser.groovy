package storagedbwebapp

import AWSAccessors.User;

/**
 * Created by spencersharp on 6/18/18.
 */
public class LocalUser
{
    long id;
    String type;
    String name;
    String password;

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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public static LocalUser createFromUser(User user)
    {
        LocalUser localUser = new LocalUser();
        localUser.setId(user.getId());
        localUser.setType(user.getType());
        localUser.setName(user.getName());
        localUser.setPassword(user.getPassword());

        return localUser;
    }

    public String toString()
    {
        return name + " " + type;
    }
}
