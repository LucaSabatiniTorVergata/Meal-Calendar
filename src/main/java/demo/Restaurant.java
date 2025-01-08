package demo;


public class Restaurant {

    private String name;
    private String address;
    private String phone;
    private String description;

    public Restaurant(String name, String address, String phone, String description) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.description = description;
    }

    public void PrintData() {

        System.out.println(name + " " + address + " " + phone + " " + description);
    }



}