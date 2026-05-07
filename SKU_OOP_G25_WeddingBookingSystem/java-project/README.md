# SKU_OOP_G25 — Wedding Planning and Vendor Booking System

**Course:** SE1020 – Object Oriented Programming  
**Group:** G25  
**Team:** Vidura · Daham · Chanuka · Lahiru

---

## Project Overview

An all-in-one web-based platform for wedding venues and vendors. Centralizes inquiries, scheduling, payments, and analytics to streamline operations and increase bookings.

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.2 |
| Frontend | HTML5 + CSS3 + Bootstrap 5 |
| Template Engine | Thymeleaf |
| Data Storage | File Handling (`.txt` files) |
| Version Control | Git & GitHub |
| IDE | IntelliJ IDEA |

---

## OOP Concepts Applied

| Concept | Where Used |
|---------|-----------|
| **Encapsulation** | All model classes (User, WeddingPackage, Booking, Payment, Review) — private fields + getters/setters |
| **Inheritance** | `User` → `AdminUser`, `WeddingPackage` → `BasicPackage` / `PremiumPackage`, `Review` → `PublicReview` / `VerifiedReview` |
| **Polymorphism** | `getRole()`, `toFileString()`, `getDiscountedPrice()`, `getReviewType()` overridden in subclasses |
| **Abstraction** | `Person`, `WeddingPackage`, `Review` are abstract classes with abstract methods |
| **Linked List** | `VendorLinkedList` — custom linked list stores packages dynamically |
| **Bubble Sort** | `sortByPrice()` and `sortByName()` in `VendorLinkedList` |
| **File Handling** | `FileHandler` utility reads/writes all `.txt` data files |

---

## Team Member Contributions

### Vidura — User Management + Analytics Dashboard
- **Files:** `User.java`, `AdminUser.java`, `UserService.java`, `UserController.java`, `DashboardController.java`
- **UI:** `login.html`, `register.html`, `users/list.html`, `users/edit.html`, `profile.html`, `dashboard.html`
- **CRUD:** Create (register), Read (search/list), Update (edit), Delete (remove user)

### Lahiru — Wedding Package Management
- **Files:** `WeddingPackage.java`, `BasicPackage.java`, `PremiumPackage.java`, `PackageService.java`, `PackageController.java`
- **UI:** `packages/list.html`, `packages/detail.html`, `packages/add.html`, `packages/edit.html`
- **Data Structure:** Custom LinkedList + Bubble Sort
- **CRUD:** Create, Read (sorted), Update, Delete

### Daham — Booking + Payment Management
- **Files:** `Booking.java`, `Payment.java`, `BookingService.java`, `PaymentService.java`, `BookingController.java`
- **UI:** `bookings/list.html`, `bookings/detail.html`, `bookings/create.html`, `bookings/edit.html`
- **CRUD:** Create booking, Read/Search, Update status, Delete; + Record/View payments

### Chanuka — Feedback & Review Management
- **Files:** `Review.java`, `PublicReview.java`, `VerifiedReview.java`, `ReviewService.java`, `ReviewController.java`
- **UI:** `reviews/list.html`, `reviews/submit.html`, `reviews/edit.html`
- **CRUD:** Create (submit), Read (list/filter), Update (edit), Delete

---

## Installation & Setup

### Prerequisites
- Java 17+ — [adoptium.net](https://adoptium.net)
- Maven 3.9+ (bundled with IntelliJ)
- IntelliJ IDEA — [jetbrains.com/idea](https://www.jetbrains.com/idea/)
- Git — [git-scm.com](https://git-scm.com)

### Step 1 — Clone the repository
```bash
git clone https://github.com/IT25102240/SKU_OOP_G25_Wedding_Planning.git
cd SKU_OOP_G25_Wedding_Planning
```

### Step 2 — Open in IntelliJ IDEA
1. Open IntelliJ → **File → Open** → select the `java-project` folder
2. IntelliJ will detect it as a Maven project and download dependencies automatically
3. Wait for indexing to finish (bottom status bar)

### Step 3 — Run the application
1. Open `src/main/java/com/wedding/WeddingBookingApplication.java`
2. Click the green ▶ Run button next to `main()`
3. Wait for: `Started WeddingBookingApplication in X seconds`

### Step 4 — Open in browser
```
http://localhost:8080
```

### Demo Accounts
| Role | Email | Password |
|------|-------|----------|
| Admin | admin@wedding.com | admin123 |
| Customer | emma@example.com | customer123 |

---

## Data Storage

Data is stored in plain `.txt` files in the `/data` directory (created automatically):
- `data/users.txt`
- `data/packages.txt`
- `data/bookings.txt`
- `data/payments.txt`
- `data/reviews.txt`

Each line = one record, fields separated by `|`.

---

## GitHub Workflow (for team members)

```bash
# Before starting work — always pull first
git pull origin main

# Work on your branch
git checkout -b vidura/feature-name

# After coding — commit and push
git add .
git commit -m "Vidura: implement user search functionality"
git push origin vidura/feature-name

# Create Pull Request on GitHub to merge into main
```

---

## Project Structure

```
java-project/
├── src/main/java/com/wedding/
│   ├── WeddingBookingApplication.java     ← entry point
│   ├── model/
│   │   ├── Person.java                    ← abstract base (Abstraction)
│   │   ├── User.java                      ← extends Person (Inheritance)
│   │   ├── AdminUser.java                 ← extends User
│   │   ├── WeddingPackage.java            ← abstract (Abstraction)
│   │   ├── BasicPackage.java              ← extends WeddingPackage
│   │   ├── PremiumPackage.java            ← extends WeddingPackage
│   │   ├── Booking.java
│   │   ├── Payment.java
│   │   ├── Review.java                    ← abstract
│   │   ├── PublicReview.java              ← extends Review
│   │   └── VerifiedReview.java            ← extends Review
│   ├── util/
│   │   ├── VendorLinkedList.java          ← Linked List + Bubble Sort
│   │   ├── FileHandler.java               ← File Read/Write
│   │   └── IdGenerator.java
│   ├── service/
│   │   ├── UserService.java               ← Vidura
│   │   ├── PackageService.java            ← Lahiru
│   │   ├── BookingService.java            ← Daham
│   │   ├── PaymentService.java            ← Daham
│   │   └── ReviewService.java             ← Chanuka
│   └── controller/
│       ├── UserController.java            ← Vidura
│       ├── PackageController.java         ← Lahiru
│       ├── BookingController.java         ← Daham
│       ├── ReviewController.java          ← Chanuka
│       └── DashboardController.java       ← Vidura
├── src/main/resources/
│   ├── application.properties
│   └── templates/
│       ├── home.html
│       ├── dashboard.html                 ← Vidura
│       ├── users/                         ← Vidura
│       ├── packages/                      ← Lahiru
│       ├── bookings/                      ← Daham
│       └── reviews/                       ← Chanuka
├── data/                                  ← txt file storage
└── pom.xml
```
