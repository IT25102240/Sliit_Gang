package com.wedding.controller;

import com.wedding.model.User;
import com.wedding.model.WeddingPackage;
import com.wedding.service.PackageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;

// LAHIRU - Wedding Package Management Controller
// Handles: Add, List (sorted), View, Edit, Delete packages
@Controller
@RequestMapping("/packages")
public class PackageController {

    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    // ─── LIST PACKAGES ──────────────────────────────────────────────────────
    @GetMapping
    public String list(@RequestParam(defaultValue = "price") String sort,
                       @RequestParam(required = false) String search,
                       Model model, HttpSession session) {
        List<WeddingPackage> packages;
        if (search != null && !search.isBlank()) {
            packages = packageService.searchByName(search);
        } else if ("name".equals(sort)) {
            packages = packageService.getAllSortedByName(); // BUBBLE SORT by name
        } else {
            packages = packageService.getAllSortedByPrice(); // BUBBLE SORT by price
        }
        model.addAttribute("packages", packages);
        model.addAttribute("sort", sort);
        model.addAttribute("search", search);
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        return "packages/list";
    }

    // ─── VIEW SINGLE PACKAGE ────────────────────────────────────────────────
    @GetMapping("/{id}")
    public String view(@PathVariable String id, Model model, HttpSession session) {
        WeddingPackage pkg = packageService.findById(id);
        if (pkg == null) return "redirect:/packages";
        model.addAttribute("pkg", pkg);
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        return "packages/detail";
    }

    // ─── ADD PACKAGE (admin only) ────────────────────────────────────────────
    @GetMapping("/add")
    public String addPage(HttpSession session, Model model) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null || !current.getRole().equals("admin")) return "redirect:/packages";
        model.addAttribute("currentUser", current);
        return "packages/add";
    }

    @PostMapping("/add")
    public String addPackage(@RequestParam String type,
                              @RequestParam String name,
                              @RequestParam String description,
                              @RequestParam double price,
                              @RequestParam int maxGuests,
                              @RequestParam String vendorName,
                              @RequestParam String includes,
                              @RequestParam(defaultValue = "") String addOns,
                              RedirectAttributes ra) {
        packageService.addPackage(type, name, description, price, maxGuests, vendorName, includes, addOns);
        ra.addFlashAttribute("success", "Package \"" + name + "\" added successfully.");
        return "redirect:/packages";
    }

    // ─── EDIT PACKAGE (admin only) ───────────────────────────────────────────
    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable String id, HttpSession session, Model model) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null || !current.getRole().equals("admin")) return "redirect:/packages";
        WeddingPackage pkg = packageService.findById(id);
        if (pkg == null) return "redirect:/packages";
        model.addAttribute("pkg", pkg);
        model.addAttribute("currentUser", current);
        return "packages/edit";
    }

    @PostMapping("/update/{id}")
    public String updatePackage(@PathVariable String id,
                                 @RequestParam String name,
                                 @RequestParam String description,
                                 @RequestParam double price,
                                 @RequestParam int maxGuests,
                                 @RequestParam String vendorName,
                                 RedirectAttributes ra) {
        boolean ok = packageService.updatePackage(id, name, description, price, maxGuests, vendorName);
        ra.addFlashAttribute(ok ? "success" : "error", ok ? "Package updated." : "Update failed.");
        return "redirect:/packages";
    }

    // ─── DELETE PACKAGE (admin only) ─────────────────────────────────────────
    @PostMapping("/delete/{id}")
    public String deletePackage(@PathVariable String id, RedirectAttributes ra) {
        boolean ok = packageService.deletePackage(id);
        ra.addFlashAttribute(ok ? "success" : "error", ok ? "Package deleted." : "Delete failed.");
        return "redirect:/packages";
    }
}
