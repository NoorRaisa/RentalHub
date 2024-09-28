package Model;

public class UserInFeedModel {
    String Name,Email,Phone,Image,id;
    UserInFeedModel()
    {

    }
    UserInFeedModel(String Name,String Email,String Phone,String Image,String id)
    {
        this.Email=Email;
        this.Name=Name;
        this.Phone=Phone;
        this.Image=Image;
        this.id=id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }
}
