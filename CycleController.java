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
import com.example.learningcycles.repository.CycleRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/cycle")
public class CycleController {


    @Autowired
    private Optional<Cycles> cycle;

    @Autowired
    private CycleRepository cycleRepo;

    @GetMapping
    public void getFrontPage(HttpServletResponse resp) throws IOException {
        resp.getWriter().append("hello there");
    }

    @GetMapping("/listAllAvailableCycles")
    public String getCycles(Model model) {
        List<Cycles> cycleList = cycleRepo.findAllByStockGreaterThan(0);
        model.addAttribute("cycleList", cycleList);
        return "cyclelist";
    }

    @PostMapping("/listAllAvailableCycles")
    public String takeCycle(@RequestParam(name = "taken") Integer id, @RequestParam(name = "action") String action) {
        Optional<Cycles> cycle = cycleRepo.findById(id);
        if (cycle.isPresent()) {
            if ("take!".equals(action) && cycle.get().getStock() > 0) {
                cycle.get().setTaken(true);
                cycle.get().setStock(cycle.get().getStock() + 1);
                cycleRepo.save(cycle.get());
            } else if ("return!".equals(action) && cycle.get().isTaken()) {
                cycle.get().setTaken(false);
                cycle.get().setStock(cycle.get().getStock()+1);
                cycleRepo.save(cycle.get());
            }
        }
        return "redirect:/cycle/listAllAvailableCycles";
    }

    @GetMapping("/borrow/{id}")
    public String getResponse(@PathVariable int id, Model model) throws IOException {
        Optional<Cycles> cycle = cycleRepo.findById(id);
        if (cycle.isPresent() && !cycle.get().isTaken() && cycle.get().getStock() > 0) {
            cycle.get().setTaken(true);
            cycle.get().setStock(cycle.get().getStock()-1);;
            cycleRepo.save(cycle.get());
        }
        model.addAttribute("Cycles", cycle.orElse(null));
        return "borrowForm";
    }

    @PostMapping("/borrow/{id}")
    public String redirectFromBorrow(@RequestParam(name = "action") String action) {
        if ("goback".equals(action)) {
            return "redirect:/cycle/listAllAvailableCycles";
        } else {
            return "borrowForm";
        }
    }
    @GetMapping("/return/{id}")
    public String putResponse(@PathVariable int id, Model model) {
        Optional<Cycles> cycle = cycleRepo.findById(id);
        if (cycle.isPresent() && cycle.get().isTaken()) {
            cycle.get().setTaken(false);
            cycleRepo.save(cycle.get());
        }
        model.addAttribute("Cycles", cycle.orElse(null));
        return "returnForm";
    }

    @PostMapping("/return/{id}")
    public String redirectFromReturn(@RequestParam(name = "action") String action) {
        if ("goback".equals(action)) {
            return "redirect:/cycle/listAllAvailableCycles";
        } else {
            return "returnForm";
        }
    }

    @GetMapping("/restock")
    public String getRestockPage(Model model) {
        List<Cycles> cycleList = new ArrayList<>();
        cycleRepo.findAll().forEach(cycle -> cycleList.add(cycle));
        model.addAttribute("cycleList", cycleList);
        return "restock";
    }

    @PostMapping("/restock/{id}")
    public String restockCycle(@PathVariable int id, @RequestParam(name = "qty", defaultValue = "1") int qty) {
        cycle = cycleRepo.findById(id);
        cycle.get().setStock(cycle.get().getStock()+qty);
        cycleRepo.save(cycle.get());
        return "redirect:/cycle/restock";
    }
}
