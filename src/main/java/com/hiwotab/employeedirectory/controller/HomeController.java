package com.hiwotab.employeedirectory.controller;

import com.hiwotab.employeedirectory.models.Department;
import com.hiwotab.employeedirectory.models.Employee;
import com.hiwotab.employeedirectory.models.Team;
import com.hiwotab.employeedirectory.repositories.DepartmentRepo;
import com.hiwotab.employeedirectory.repositories.EmployeeRepo;
import com.hiwotab.employeedirectory.repositories.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    DepartmentRepo departmentRepo;
    @Autowired
    EmployeeRepo employeeRepo;
    @Autowired
    TeamRepo teamRepo;
    @RequestMapping("/")
    public String showHomePage() {
        return "index";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/load")
    public @ResponseBody String LoadDB() {
        Department department = new Department();
        department.setDepName("Operations");
        departmentRepo.save(department);
        department = new Department();
        department.setDepName("Accounting");
        departmentRepo.save(department);
        department = new Department();
        department.setDepName("Human Resources");
        departmentRepo.save(department);
        return "Loaded!";
    }

     @RequestMapping("/homePage")
    public String showHomePages() {
        return "index";
    }

    @GetMapping("/addEmployee")
    public String addEmployeeInfo(Model model) {

        Employee employee=new Employee();
        //employee.setDepartment(departmentRepo.findOne(id));
        model.addAttribute("newEmp", employee);
        model.addAttribute("allDepts", departmentRepo.findAll());
        return "addEmployee";
    }

    @PostMapping("/addEmployee")
    public String addEmployeeInfo(@Valid @ModelAttribute("newEmp") Employee employee, @RequestParam("departments") long department_id,  BindingResult result) {
        if (result.hasErrors()) {
            return "addEmployee";
        }


        //Department department=departmentRepo.findOne(employee.getDepartment().getId());

            employee.setDepartment(departmentRepo.findOne(department_id));
            employeeRepo.save(employee);

//
        return "dispEmpInfo";
    }

    @GetMapping("/addTeam")
    public String addTeam (Model model)
    {
        Team team = new Team();
        model.addAttribute("newTeam", team);
        model.addAttribute("empforTeam", employeeRepo.findAll());
        return "addTeam";
    }

    @PostMapping("/addTeam")
    public String addTeam(@ModelAttribute ("newTeam") Team team)
    {
        teamRepo.save(team);
        return "dispteaminfo";
    }
    @GetMapping("/addemployeetoteam/{id}")
    public String addEmptoTeam(@PathVariable("id") long teamID, Model model)
    {
        model.addAttribute("team",teamRepo.findOne(new Long(teamID)));
        model.addAttribute("empList",employeeRepo.findAll());
        return "employeetoteam";
    }

    @PostMapping("/addemployeetoteam/{empid}")
    public String addEmptoTeam(@RequestParam("teams") String teamID, @PathVariable("empid") long empID,  @ModelAttribute("aTeam") Team t, Model model){
        Employee e = employeeRepo.findOne(new Long(empID));
        e.addTeam(teamRepo.findOne(new Long(teamID)));
        employeeRepo.save(e);
        model.addAttribute("teamList",teamRepo.findAll());
        model.addAttribute("emList",employeeRepo.findAll());
        return "redirect:/";
    }
    @RequestMapping("/listEmpTeam")
    public String showListET(Model model)
     {
        model.addAttribute("empList",employeeRepo.findAll());
        model.addAttribute("teamList",teamRepo.findAll());
        return "listEmpTeam";
        }


    @GetMapping("/addDepartment")
    public String addDepartmentInfo(Model model) {
        model.addAttribute("newDep", new Department());
        return "addDepartment";
    }

    @PostMapping("/addDepartment")
    public String addDepartemntInfo(@Valid @ModelAttribute("newDep") Department department, @RequestParam("employeeSelect") long empHeadId, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addDepartment";
        }
        department.setDepHName(empHeadId);
        departmentRepo.save(department);
        model.addAttribute("emp", employeeRepo.findOne(department.getId()));
        return "dispDepInfo";
    }

    @GetMapping("/Depupdate/{id}")
    public String updateDep(@PathVariable("id") long id, Model model) {
        model.addAttribute("newDep", departmentRepo.findOne(id));

        return "updateDep";
    }
    @RequestMapping("/Empupdate/{id}")
    public String updateEmp(@PathVariable("id") long id, Model model) {
        model.addAttribute("newEmp", employeeRepo.findOne(id));
        return "addEmployee";
    }
    @RequestMapping("/Empdelete/{id}")
    public String delEmployee(@PathVariable("id") long id){
        /*DONT DO THIS!!! Can't delete a child from a database without deleting the parent.
        Instead, have a "deleted" field in the model.  If true, don't display child.
        employeeRepo.delete(id);
        */

        return "redirect:/listEmp";
    }
    @RequestMapping("/listDep")
    public String listAllDep(Model model) {
        model.addAttribute("department", departmentRepo.findAll());
        return "listDep";
    }

    @RequestMapping("/listEmp")
    public String listAllEmp(Model model) {
        model.addAttribute("employee", employeeRepo.findAll());
        return "listEmp";
    }
    @RequestMapping("/Depdetail/{id}")
    public String showDepartment(@PathVariable("id") long id, Model model) {
        Department departmentD=departmentRepo.findOne(id);
        model.addAttribute("departmentD", departmentD);
        model.addAttribute("listEmp",employeeRepo.findByDepartment(departmentD));
        return "dispDepDetail";
    }

    @RequestMapping("/Empdetail/{id}")
    public String showEmployee(@PathVariable("id") long id, Model model) {
        model.addAttribute("listEmp", employeeRepo.findOne(id));
        return "dispEmpDetail";
    }


}
