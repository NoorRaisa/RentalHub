package Model;

public class Renters {
    String Address,Name,Phone,HomeName;
    public Renters()
    {

    }

    public Renters(String Address,String Name,String Phone,String HomeName) {
        this.Address=Address;
        this.Name=Name;
        this.Phone=Phone;
        this.HomeName=HomeName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getHomeName() {
        return HomeName;
    }

    public void setHomeName(String homeName) {
        HomeName = homeName;
    }
}
