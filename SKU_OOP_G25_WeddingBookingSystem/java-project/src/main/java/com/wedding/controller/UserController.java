package com.wedding.controller;

import com.wedding.model.User;
import com.wedding.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;

// VIDURA - User Management Controller
// Handles: Register, Login, List Users, Update, Delete
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // HOME PAGE
    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        model.addAttribute("currentUser", user);
        return "home";
    }

    // ─── LOGIN ──────────────────────────────────────────────────────────────
    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("currentUser") != null) return "redirect:/";
        return "users/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes ra) {
        User user = userService.login(email, password);
        if (user == null) {
            ra.addFlashAttribute("error", "Invalid email or password.");
            return "redirect:/login";
        }
        session.setAttribute("currentUser", user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // ─── REGISTER ───────────────────────────────────────────────────────────
    @GetMapping("/register")
    public String registerPage() {
        return "users/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String phone,
                           @RequestParam String password,
                           RedirectAttributes ra) {
        boolean success = userService.registerUser(name, email, phone, password, "customer");
        if (!success) {
            ra.addFlashAttribute("error", "Email already in use. Please use a different email.");
            return "redirect:/register";
        }
        ra.addFlashAttribute("success", "Account created! Please sign in.");
        return "redirect:/login";
    }

    // ─── LIST USERS (admin only) ─────────────────────────────────────────────
    @GetMapping("/users")
    public String listUsers(@RequestParam(required = false) String search,
                             Model model, HttpSession session) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null || !current.getRole().equals("admin")) return "redirect:/login";

        List<User> users = (search != null && !search.isBlank())
                ? userService.searchByName(search)
                : userService.getAllUsers();

        model.addAttribute("users", users);
        model.addAttribute("search", search);
        model.addAttribute("currentUser", current);
        return "users/list";
    }

    // ─── PROFILE PAGE ────────────────────────────────────────────────────────
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null) return "redirect:/login";
        model.addAttribute("currentUser", current);
        return "users/profile";
    }

    // ─── UPDATE USER ─────────────────────────────────────────────────────────
    @GetMapping("/users/edit/{id}")
    public String editPage(@PathVariable String id, Model model, HttpSession session) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null || !current.getRole().equals("admin")) return "redirect:/login";
        User user = userService.findById(id);
        if (user == null) return "redirect:/users";
        model.addAttribute("user", user);
        model.addAttribute("currentUser", current);
        return "users/edit";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable String id,
                              @RequestParam String name,
                              @RequestParam String phone,
                              @RequestParam String role,
                              RedirectAttributes ra) {
        boolean ok = userService.updateUser(id, name, phone, role);
        ra.addFlashAttribute(ok ? "success" : "error", ok ? "User updated." : "Update failed.");
        return "redirect:/users";
    }

    // ─── DELETE USER ──────────────────────────────────────────────────────────
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable String id, RedirectAttributes ra) {
        boolean ok = userService.deleteUser(id);
        ra.addFlashAttribute(ok ? "success" : "error", ok ? "User deleted." : "Delete failed.");
        return "redirect:/users";
    }
}
