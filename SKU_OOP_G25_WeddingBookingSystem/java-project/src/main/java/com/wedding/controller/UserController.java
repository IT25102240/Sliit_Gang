package com.wedding.controller;

import com.wedding.model.User;
import com.wedding.service.BookingService;
import com.wedding.service.ReviewService;
import com.wedding.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;

// VIDURA IT25102240 - User Management Controller
// profile now loads booking/review stats and supports POST /profile/update

@Controller
public class UserController {

    private final UserService userService;
    private final BookingService bookingService;
    private final ReviewService reviewService;

    public UserController(UserService userService,
                          BookingService bookingService,
                          ReviewService reviewService) {
        this.userService   = userService;
        this.bookingService = bookingService;
        this.reviewService  = reviewService;
    }

    // HOME
    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        return "home";
    }

    //Login
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

    //Register
    @GetMapping("/register")
    public String registerPage() { return "users/register"; }

    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String phone,
                           @RequestParam String password,
                           RedirectAttributes ra) {
        boolean ok = userService.registerUser(name, email, phone, password, "customer");
        if (!ok) {
            ra.addFlashAttribute("error", "Email already in use.");
            return "redirect:/register";
        }
        ra.addFlashAttribute("success", "Account created! Please sign in.");
        return "redirect:/login";
    }

    // Profile (Get)
    // Now loads booking count, confirmed count, and review count for the stats row
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null) return "redirect:/login";

        long totalBookings     = bookingService.getBookingsByUser(current.getId()).size();
        long confirmedBookings = bookingService.getBookingsByUser(current.getId()).stream()
                .filter(b -> "confirmed".equals(b.getStatus())).count();
        long totalReviews      = reviewService.getAllReviews().stream()
                .filter(r -> r.getUserId().equals(current.getId())).count();

        model.addAttribute("currentUser",       current);
        model.addAttribute("totalBookings",     totalBookings);
        model.addAttribute("confirmedBookings", confirmedBookings);
        model.addAttribute("totalReviews",      totalReviews);
        return "users/profile";
    }

    //PROFILE (POST /profile/update)
    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String name,
                                @RequestParam String phone,
                                @RequestParam(defaultValue = "") String newPassword,
                                @RequestParam(defaultValue = "") String confirmPassword,
                                HttpSession session,
                                RedirectAttributes ra) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null) return "redirect:/login";

        // Validate password match if provided
        if (!newPassword.isBlank()) {
            if (!newPassword.equals(confirmPassword)) {
                ra.addFlashAttribute("error", "Passwords do not match.");
                return "redirect:/profile";
            }
            if (newPassword.length() < 6) {
                ra.addFlashAttribute("error", "Password must be at least 6 characters.");
                return "redirect:/profile";
            }
            current.setPassword(newPassword); // hashed by User.setPassword()
        }

        current.setName(name);
        current.setPhone(phone);

        boolean ok = userService.update(current);
        if (ok) {
            // Refresh session with updated user object
            session.setAttribute("currentUser", userService.findById(current.getId()));
            ra.addFlashAttribute("success", "Profile updated successfully.");
        } else {
            ra.addFlashAttribute("error", "Update failed. Please try again.");
        }
        return "redirect:/profile";
    }

    //LIST USERS (admin only)
    @GetMapping("/users")
    public String listUsers(@RequestParam(required = false) String search,
                            Model model, HttpSession session) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null || !current.getRole().equals("admin")) return "redirect:/login";
        List<User> users = (search != null && !search.isBlank())
                ? userService.searchByName(search)
                : userService.getAllUsers();
        model.addAttribute("users",       users);
        model.addAttribute("search",      search);
        model.addAttribute("currentUser", current);
        return "users/list";
    }

    //EDIT USER (admin)
    @GetMapping("/users/edit/{id}")
    public String editPage(@PathVariable String id, Model model, HttpSession session) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null || !current.getRole().equals("admin")) return "redirect:/login";
        User user = userService.findById(id);
        if (user == null) return "redirect:/users";
        model.addAttribute("user",        user);
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

    //DELETE USER (admin)
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable String id, RedirectAttributes ra) {
        boolean ok = userService.deleteUser(id);
        ra.addFlashAttribute(ok ? "success" : "error", ok ? "User deleted." : "Delete failed.");
        return "redirect:/users";
    }
}