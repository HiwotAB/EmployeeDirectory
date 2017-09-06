package com.hiwotab.employeedirectory.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long teamid;

    @NotEmpty
    private String teamName;

    @ManyToMany(mappedBy = "teamSet")
    private Set<Employee> empMembers;


    public long getTeamid() {
        return teamid;
    }

    public void setTeamid(long teamid) {
        this.teamid = teamid;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Set<Employee> getEmpMembers() {
        return empMembers;
    }

    public void setEmpMembers(Set<Employee> empMembers) {
        this.empMembers = empMembers;
    }

    public void addEmployee(Employee e)
    {
        empMembers.add(e);
    }
}
