package com.wedding.controller;

import com.wedding.model.User;
import com.wedding.service.BookingService;
import com.wedding.service.PackageService;
import com.wedding.service.ReviewService;
import com.wedding.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

// VIDURA - Analytics Dashboard Controller
@Controller
public class DashboardController {

    private final UserService userService;
    private final BookingService bookingService;
    private final PackageService packageService;
    private final ReviewService reviewService;

    public DashboardController(UserService userService, BookingService bookingService,
                                PackageService packageService, ReviewService reviewService) {
        this.userService = userService;
        this.bookingService = bookingService;
        this.packageService = packageService;
        this.reviewService = reviewService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null || !current.getRole().equals("admin")) return "redirect:/login";

        model.addAttribute("totalUsers", userService.getAllUsers().size());
        model.addAttribute("totalCustomers", userService.countByRole("customer"));
        model.addAttribute("totalPackages", packageService.countPackages());
        model.addAttribute("totalBookings", bookingService.getAllBookings().size());
        model.addAttribute("pendingBookings", bookingService.countByStatus("pending"));
        model.addAttribute("confirmedBookings", bookingService.countByStatus("confirmed"));
        model.addAttribute("totalRevenue", bookingService.totalRevenue());
        model.addAttribute("totalReviews", reviewService.getAllReviews().size());
        model.addAttribute("currentUser", current);
        model.addAttribute("recentBookings", bookingService.getAllBookings().stream().limit(5).toList());
        return "dashboard";
    }
}
