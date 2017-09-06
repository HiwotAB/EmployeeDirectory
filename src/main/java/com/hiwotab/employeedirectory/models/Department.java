package com.hiwotab.employeedirectory.models;

import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotEmpty
    @Size(min=2,max = 50)
    private String depName;
    private long depHName;

    @OneToMany(mappedBy = "department",cascade= CascadeType.ALL,fetch=FetchType.EAGER)
    public Set<Employee> employees;

    public Department(){
        setEmployees(new HashSet<Employee>());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public long getDepHName() {
        return depHName;
    }

    public void setDepHName(long depHName) {
        this.depHName = depHName;
    }

    public void addEmployee(Employee employee){
        employee.setDepartment(this);
        this.employees.add(employee);
    }
}
