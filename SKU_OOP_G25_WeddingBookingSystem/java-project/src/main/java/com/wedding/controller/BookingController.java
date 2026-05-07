package com.wedding.controller;

import com.wedding.model.Booking;
import com.wedding.model.Payment;
import com.wedding.model.User;
import com.wedding.model.WeddingPackage;
import com.wedding.service.BookingService;
import com.wedding.service.PackageService;
import com.wedding.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;

// DAHAM - Booking & Payment Management Controller
@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final PackageService packageService;
    private final PaymentService paymentService;

    public BookingController(BookingService bookingService,
                              PackageService packageService,
                              PaymentService paymentService) {
        this.bookingService = bookingService;
        this.packageService = packageService;
        this.paymentService = paymentService;
    }

    // ─── LIST BOOKINGS ──────────────────────────────────────────────────────
    @GetMapping
    public String list(HttpSession session, Model model) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null) return "redirect:/login";

        List<Booking> bookings = current.getRole().equals("admin")
                ? bookingService.getAllBookings()
                : bookingService.getBookingsByUser(current.getId());

        model.addAttribute("bookings", bookings);
        model.addAttribute("currentUser", current);
        return "bookings/list";
    }

    // ─── VIEW BOOKING DETAIL ─────────────────────────────────────────────────
    @GetMapping("/{id}")
    public String detail(@PathVariable String id, HttpSession session, Model model) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null) return "redirect:/login";

        Booking booking = bookingService.findById(id);
        if (booking == null) return "redirect:/bookings";

        List<Payment> payments = paymentService.getByBookingId(id);
        double totalPaid = paymentService.getTotalPaid(id);
        double remaining = booking.getTotalAmount() - totalPaid;

        model.addAttribute("booking", booking);
        model.addAttribute("payments", payments);
        model.addAttribute("totalPaid", totalPaid);
        model.addAttribute("remaining", remaining);
        model.addAttribute("currentUser", current);
        return "bookings/detail";
    }

    // ─── CREATE BOOKING ──────────────────────────────────────────────────────
    @GetMapping("/create/{packageId}")
    public String createPage(@PathVariable String packageId,
                              HttpSession session, Model model) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null) return "redirect:/login";

        WeddingPackage pkg = packageService.findById(packageId);
        if (pkg == null) return "redirect:/packages";

        model.addAttribute("pkg", pkg);
        model.addAttribute("currentUser", current);
        return "bookings/create";
    }

    @PostMapping("/create")
    public String createBooking(@RequestParam String packageId,
                                 @RequestParam String packageName,
                                 @RequestParam String eventDate,
                                 @RequestParam int guestCount,
                                 @RequestParam String venueName,
                                 @RequestParam double totalAmount,
                                 @RequestParam(defaultValue = "") String specialRequests,
                                 HttpSession session,
                                 RedirectAttributes ra) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null) return "redirect:/login";

        bookingService.createBooking(current.getId(), packageId, packageName,
                eventDate, guestCount, venueName, totalAmount, specialRequests);
        ra.addFlashAttribute("success", "Booking submitted successfully!");
        return "redirect:/bookings";
    }

    // ─── UPDATE STATUS (admin only) ──────────────────────────────────────────
    @PostMapping("/status/{id}")
    public String updateStatus(@PathVariable String id,
                                @RequestParam String status,
                                RedirectAttributes ra) {
        boolean ok = bookingService.updateStatus(id, status);
        ra.addFlashAttribute(ok ? "success" : "error", ok ? "Status updated." : "Update failed.");
        return "redirect:/bookings/" + id;
    }

    // ─── EDIT BOOKING ────────────────────────────────────────────────────────
    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable String id, HttpSession session, Model model) {
        User current = (User) session.getAttribute("currentUser");
        if (current == null) return "redirect:/login";
        Booking booking = bookingService.findById(id);
        if (booking == null) return "redirect:/bookings";
        model.addAttribute("booking", booking);
        model.addAttribute("currentUser", current);
        return "bookings/edit";
    }

    @PostMapping("/update/{id}")
    public String updateBooking(@PathVariable String id,
                                 @RequestParam String eventDate,
                                 @RequestParam int guestCount,
                                 @RequestParam String venueName,
                                 @RequestParam String specialRequests,
                                 RedirectAttributes ra) {
        boolean ok = bookingService.updateBooking(id, eventDate, guestCount, venueName, specialRequests);
        ra.addFlashAttribute(ok ? "success" : "error", ok ? "Booking updated." : "Update failed.");
        return "redirect:/bookings";
    }

    // ─── DELETE BOOKING ──────────────────────────────────────────────────────
    @PostMapping("/delete/{id}")
    public String deleteBooking(@PathVariable String id, RedirectAttributes ra) {
        boolean ok = bookingService.deleteBooking(id);
        ra.addFlashAttribute(ok ? "success" : "error", ok ? "Booking deleted." : "Delete failed.");
        return "redirect:/bookings";
    }

    // ─── RECORD PAYMENT ──────────────────────────────────────────────────────
    @PostMapping("/{id}/payment")
    public String recordPayment(@PathVariable String id,
                                 @RequestParam double amount,
                                 @RequestParam String method,
                                 @RequestParam(defaultValue = "") String transactionRef,
                                 RedirectAttributes ra) {
        paymentService.recordPayment(id, amount, method, transactionRef);
        ra.addFlashAttribute("success", "Payment recorded successfully.");
        return "redirect:/bookings/" + id;
    }
}
