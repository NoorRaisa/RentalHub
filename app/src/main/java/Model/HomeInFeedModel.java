package Model;

public class HomeInFeedModel {
    String Publisher, Mobile, date, Description, HomeName, Image, Address,pId, Rent, Rooms, time,Advance;

    public HomeInFeedModel() {
    }
    public HomeInFeedModel(String Publisher,String Advance, String Mobile, String date, String Description, String HomeName, String Image, String Address, String pId, String Rent, String Rooms, String time ) {
        this.Publisher = Publisher;
        this.Mobile = Mobile;
        this.date = date;
        this.Description = Description;
        this.HomeName = HomeName;
        this.Image = Image;
        this.Address = Address;
        this.pId = pId;
        this.Rent = Rent;
        this.Rooms = Rooms;
        this.time = time;
        this.Advance=Advance;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String Publisher) {
        this.Publisher = Publisher;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getHomeName() {
        return HomeName;
    }

    public void setHomeName(String HomeName) {
        this.HomeName = HomeName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getRent() {
        return Rent;
    }

    public void setRent(String Rent) {
        this.Rent = Rent;
    }

    public String getRooms() {
        return Rooms;
    }

    public void setRooms(String Rooms) {
        this.Rooms = Rooms;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAdvance() {
        return Advance;
    }

    public void setAdvance(String advance) {
        Advance = advance;
    }
}
