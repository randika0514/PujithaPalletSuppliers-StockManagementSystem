package model;

public class Employee {
    private String empId;
    private String empName;
    private String empAddress;
    private String empContact;
    private double empSalary;

    public Employee() {
    }

    public Employee(String empId, String empName, String empAddress, String empContact, Double empSalary) {
        this.empId = empId;
        this.empName = empName;
        this.empAddress = empAddress;
        this.empContact = empContact;
        this.empSalary = empSalary;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getEmpContact() {
        return empContact;
    }

    public void setEmpContact(String empContact) {
        this.empContact = empContact;
    }

    public Double getEmpSalary() {
        return empSalary;
    }

    public void setEmpSalary(double empSalary) {
        this.empSalary = empSalary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", empAddress='" + empAddress + '\'' +
                ", empContact='" + empContact + '\'' +
                ", empSalary=" + empSalary +
                '}';
    }
}
