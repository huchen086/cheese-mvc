package com.chen.cheesemvc.controllers;

import com.chen.cheesemvc.models.Category;
import com.chen.cheesemvc.models.Cheese;
import com.chen.cheesemvc.models.data.CategoryDao;
import com.chen.cheesemvc.models.data.CheeseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("cheese")
public class CheeseController {

    @Autowired
    private CheeseDao cheeseDao;

    @Autowired
    private CategoryDao categoryDao;

    // Request path: /cheese
    @RequestMapping(value="")
    public String index(Model model) {

        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "My Cheese");

        return "cheese/index";
    }

    @RequestMapping(value="add", method= RequestMethod.GET)
    public String displayAddCheeseForm(Model model) {
        model.addAttribute("title","Add Cheese");
        model.addAttribute(new Cheese());
        model.addAttribute("categories", categoryDao.findAll());
        return "cheese/add";
    }

    @RequestMapping(value="add", method= RequestMethod.POST)
    public String processAddCheeseForm(@ModelAttribute @Valid Cheese newCheese, Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title","Add Cheese");
            model.addAttribute("categories", categoryDao.findAll());
            return "cheese/add";
        }

        //Category cat = categoryDao.findOne(categoryId);
        //newCheese.setCategory(cat);
        cheeseDao.save(newCheese);
        // Redirect to /cheese
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model) {
        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "Remove Cheese");
        return "cheese/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@RequestParam int[] cheeseIds) {

        for (int cheeseId : cheeseIds) {
            cheeseDao.delete(cheeseId);
        }

        return "redirect:";
    }

    @RequestMapping(value = "category", method = RequestMethod.GET)
    public String category(Model model, @RequestParam int id) {
        Category cat = categoryDao.findOne(id);
        List<Cheese> cheeses = cat.getCheeses();
        model.addAttribute("cheeses", cheeses);
        model.addAttribute("title", "Cheeses in Category: " + cat.getName());
        return "cheese/index";
    }

    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable Integer cheeseId) {
        model.addAttribute("title","Edit Cheese");
        Cheese cheeseToEdit = cheeseDao.findOne(cheeseId);
        model.addAttribute("cheese", cheeseToEdit);
        model.addAttribute("categories", categoryDao.findAll());
        return "cheese/edit";
    }

    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.POST)
    public String processEditForm(@ModelAttribute @Valid Cheese newCheese, Errors errors, Model model, @PathVariable Integer cheeseId) {

        if(errors.hasErrors()) {
            model.addAttribute("title","Edit Cheese");
            //Cheese cheeseToEdit = cheeseDao.findOne(cheeseId);
            //model.addAttribute("cheese", cheeseToEdit);
            model.addAttribute("categories", categoryDao.findAll());
            return "cheese/edit";
        }

        //Category cat = categoryDao.findOne(categoryId);
        //newCheese.setCategory(cat);
        cheeseDao.save(newCheese);
        //Cheese theCheese = cheeseDao.findOne(cheeseId);
        //theCheese.setName(name);
        //theCheese.setDescription(description);
        return "redirect:/cheese";
    }
}
