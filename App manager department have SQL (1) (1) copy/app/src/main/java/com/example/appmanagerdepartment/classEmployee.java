package com.example.appmanagerdepartment;

public class classEmployee {
    private String empcode;
    private String name;
    private String gender;
    private String phone;
    private String address;
    private String depcode;
    private byte[] image;

    public classEmployee(String empcode, String name, String gender, String phone, String address, byte[] image,String depcode) {
        this.empcode = empcode;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.depcode = depcode;
        this.image = image;
    }

    public classEmployee() {
    }

    public String getEmpcode() {
        return empcode;
    }

    public void setEmpcode(String empcode) {
        this.empcode = empcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepcode() {
        return depcode;
    }

    public void setDepcode(String depcode) {
        this.depcode = depcode;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empcode='" + empcode + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", depcode='" + depcode + '\'' +
                '}';
    }
}
