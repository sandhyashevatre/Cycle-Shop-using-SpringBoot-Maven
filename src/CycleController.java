package com.example.learningcycles.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.learningcycles.entity.Cycles;
import com.example.learningcycles.exception.UnsupportedActionException;
import com.example.learningcycles.repository.CycleRepository;


import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/cycle")
public class CycleController {

 

    @Autowired
    private Optional<Cycles> cycle;

    // @Autowired
    // private UserRepository userRepo;

   
    @Autowired
     private CycleRepository cycleRepo;


    // @GetMapping("/login")
    // public String loginPage(Model model){

    //     List<User> users = new ArrayList<>();


    //     model.addAttribute("Users", users);

    //     return "login";

    // }

   

    @GetMapping("/list")

    public String getFrontPage(Model model){

        List<Cycles> cycleList = new ArrayList<>();

        // var user = userRepo.findByName(name);

        // model.addAttribute("name",((User) user).isCustomer());

        cycleRepo.findAll().forEach(cycle -> cycleList.add(cycle));

        model.addAttribute("cycleList", cycleList);

        return "cyclelist";

    }


    @GetMapping("/borrow/{id}")

    public String takeCycle(@PathVariable("id") int id,@RequestParam(name="action") String action) throws IOException, UnsupportedActionException {

        cycle = cycleRepo.findById(id);

        if("Borrow".equals(action)) {

            int count = cycle.get().getStock();

            if(count>0){

                count--;

                cycle.get().setStock(count);

                cycleRepo.save(cycle.get());

            }else{

                throw new UnsupportedActionException(cycle.get().getCompany()+" Stock Empty");

            }

        }

        return "redirect:/cycle/list";

    }

   

    @GetMapping("/restock/{id}")

    public String returnCycle(@PathVariable("id") int id,@RequestParam(name="action") String action,@RequestParam(name="number") String quantity) throws IOException, UnsupportedActionException {

        cycle = cycleRepo.findById(id);

        if("Restock".equals(action)) {

            int count = cycle.get().getStock();

            if(count<1000){

                count+=Integer.parseInt(quantity);

                cycle.get().setStock(count);

                cycleRepo.save(cycle.get());

            }else{

                throw new UnsupportedActionException(cycle.get().getCompany()+" Stock Empty");

            }

        }

        return "redirect:/cycle/list";

    }

}