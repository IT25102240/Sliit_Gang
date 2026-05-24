package com.wedding.controller;

import com.wedding.model.Review;
import com.wedding.model.User;
import com.wedding.service.PackageService;
import com.wedding.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;

// CHANUKA - Feedback & Review Management Controller
// FIX: ownership guard added to edit, update, and delete endpoints.
//      A customer can only edit/delete their OWN reviews.
//      Admin can edit/delete any review.
@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final PackageService packageService;

    public ReviewController(ReviewService reviewService, PackageService packageService) {
        this.reviewService  = reviewService;
        this.packageService = packageService;
    }

    // ─── LIST ALL REVIEWS ───────────────────────────────────────────────────
    @GetMapping
    public String list(Model model, HttpSession session) {
        model.addAttribute("reviews", reviewService.getAllReviews());
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        return "reviews/list";
    }

    // ─── SUBMIT REVIEW FORM ─────────────────────────────────────────────────
    @GetMapping("/submit")
    public String submitPage(HttpSession session, Model model) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null) return "redirect:/login";
        model.addAttribute("packages", packageService.getAll());
        model.addAttribute("currentUser", current);
        return "reviews/submit";
    }

    @PostMapping("/submit")
    public String submitReview(@RequestParam String packageId,
                               @RequestParam String packageName,
                               @RequestParam int rating,
                               @RequestParam String title,
                               @RequestParam String comment,
                               @RequestParam(defaultValue = "") String bookingId,
                               HttpSession session,
                               RedirectAttributes ra) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null) return "redirect:/login";

        // OOP: POLYMORPHISM — creates VerifiedReview if bookingId provided, else PublicReview
        if (!bookingId.isBlank()) {
            reviewService.submitVerifiedReview(current.getId(), current.getName(),
                    packageId, packageName, rating, title, comment, bookingId);
        } else {
            reviewService.submitPublicReview(current.getId(), current.getName(),
                    packageId, packageName, rating, title, comment);
        }
        ra.addFlashAttribute("success", "Thank you for your feedback!");
        return "redirect:/reviews";
    }

    // ─── EDIT REVIEW ────────────────────────────────────────────────────────
    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable String id, HttpSession session, Model model) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null) return "redirect:/login";

        Review review = reviewService.findById(id);
        if (review == null) return "redirect:/reviews";

        // FIX: ownership guard — customers can only edit their own reviews
        if (!current.getRole().equals("admin") && !review.getUserId().equals(current.getId())) {
            return "redirect:/reviews";
        }

        model.addAttribute("review", review);
        model.addAttribute("currentUser", current);
        return "reviews/edit";
    }

    @PostMapping("/update/{id}")
    public String updateReview(@PathVariable String id,
                               @RequestParam String title,
                               @RequestParam String comment,
                               @RequestParam int rating,
                               HttpSession session,
                               RedirectAttributes ra) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null) return "redirect:/login";

        Review review = reviewService.findById(id);
        if (review == null) return "redirect:/reviews";

        // FIX: ownership guard
        if (!current.getRole().equals("admin") && !review.getUserId().equals(current.getId())) {
            ra.addFlashAttribute("error", "You can only edit your own reviews.");
            return "redirect:/reviews";
        }

        boolean ok = reviewService.updateReview(id, title, comment, rating);
        ra.addFlashAttribute(ok ? "success" : "error", ok ? "Review updated." : "Update failed.");
        return "redirect:/reviews";
    }

    // ─── DELETE REVIEW ───────────────────────────────────────────────────────
    @PostMapping("/delete/{id}")
    public String deleteReview(@PathVariable String id,
                               HttpSession session,
                               RedirectAttributes ra) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null) return "redirect:/login";

        Review review = reviewService.findById(id);
        if (review == null) return "redirect:/reviews";

        // FIX: ownership guard — customers can only delete their own reviews
        if (!current.getRole().equals("admin") && !review.getUserId().equals(current.getId())) {
            ra.addFlashAttribute("error", "You can only delete your own reviews.");
            return "redirect:/reviews";
        }

        boolean ok = reviewService.deleteReview(id);
        ra.addFlashAttribute(ok ? "success" : "error", ok ? "Review deleted." : "Delete failed.");
        return "redirect:/reviews";
    }
}
