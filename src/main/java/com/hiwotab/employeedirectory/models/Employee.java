package com.hiwotab.employeedirectory.models;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotEmpty
    @Size(min=2,max =30 )
    private String fname;
    @NotEmpty
    @Size(min=2,max = 30)
    private String lname;
    @NotEmpty
    @Size(min=10)
    private String phone;

    @NotEmpty
    @Size(min=2,max = 20)
    private String workno;

    @ManyToOne(fetch = FetchType.EAGER)

    @JoinColumn(name="department_id")

    private Department department;

    public Employee() {
        this.setTeamSet(new HashSet<Team>());
    }

    @ManyToMany()
    private Set<Team> teamSet;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWorkno() {
        return workno;
    }

    public void setWorkno(String workno) {
        this.workno = workno;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<Team> getTeamSet() {
        return teamSet;
    }

    public void setTeamSet(Set<Team> teamSet) {
        this.teamSet = teamSet;
    }
    public void addTeam(Team t)
    {
        teamSet.add(t);
    }
}
