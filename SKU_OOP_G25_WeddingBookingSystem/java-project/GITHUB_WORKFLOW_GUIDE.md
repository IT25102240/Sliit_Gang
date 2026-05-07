# GitHub Workflow Guide — SKU_OOP_G25

## Repository Setup (One person does this — Vidura recommended)

### Step 1 — Create the GitHub repository
1. Go to [github.com](https://github.com) and sign in
2. Click **New repository** (+ icon, top right)
3. Fill in:
   - Repository name: `SKU_OOP_G25_Wedding_Planning`
   - Description: `SE1020 OOP Project — Wedding Planning and Vendor Booking System`
   - Visibility: **Private** (add teammates as collaborators)
   - Do NOT tick "Add README" (we already have one)
4. Click **Create repository**

### Step 2 — Push the project from your computer
Open terminal in the `java-project/` folder and run these commands **one by one**:

```bash
git init
git add .
git commit -m "Initial commit: project scaffold, all models, services, controllers, and templates"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/SKU_OOP_G25_Wedding_Planning.git
git push -u origin main
```

### Step 3 — Add team members as collaborators
1. GitHub → your repo → **Settings → Collaborators**
2. Click **Add people** → search each member's GitHub username → **Add**
3. Members will receive an email invitation — they must accept it

---

## Each Member Clones the Repo

Every team member runs this once on their computer:

```bash
git clone https://github.com/YOUR_USERNAME/SKU_OOP_G25_Wedding_Planning.git
cd SKU_OOP_G25_Wedding_Planning
```

Then open in IntelliJ: **File → Open → select the folder**.

---

## Daily Git Workflow (Follow This Every Time)

### RULE: Always pull before you start coding

```bash
git pull origin main
```

### Step-by-step for each work session:

```bash
# 1. Make sure you're on main and pull latest
git checkout main
git pull origin main

# 2. Create your own branch for the feature you're working on
git checkout -b vidura/user-search

# 3. Write your code in IntelliJ IDEA...

# 4. Check what you changed
git status
git diff

# 5. Stage all your changes
git add .

# 6. Commit with a clear message (see commit format below)
git commit -m "Vidura: add name search to UserService and update list.html"

# 7. Push your branch to GitHub
git push origin vidura/user-search

# 8. Create a Pull Request on GitHub (see below)
```

---

## Branch Naming Conventions

Always name your branch: `membername/short-description`

| Member | Example Branch Names |
|--------|---------------------|
| Vidura | `vidura/user-crud`, `vidura/dashboard-stats`, `vidura/login-fix` |
| Daham | `daham/booking-create`, `daham/payment-status`, `daham/booking-edit` |
| Chanuka | `chanuka/review-crud`, `chanuka/verified-review`, `chanuka/review-ui` |
| Lahiru | `lahiru/package-crud`, `lahiru/linked-list`, `lahiru/bubble-sort` |

**Rules:**
- Use lowercase only
- No spaces — use hyphens instead
- Keep it short and descriptive
- Never commit directly to `main`

---

## Commit Message Format

```
MemberName: action + what was changed
```

**Examples (use these as templates):**

```bash
# Adding new functionality
git commit -m "Vidura: implement user search by name in UserService"
git commit -m "Lahiru: add package list page sorted by price using bubble sort"
git commit -m "Daham: create booking form with package pre-fill"
git commit -m "Chanuka: implement verified review submission with booking ID"

# Fixing a bug
git commit -m "Vidura: fix null pointer when user not in session"
git commit -m "Daham: fix payment amount validation in BookingController"

# UI changes
git commit -m "Lahiru: style package cards with Bootstrap grid"
git commit -m "Chanuka: add star rating UI to review submit form"

# Multiple changes
git commit -m "Daham: add createBooking, updateStatus, deleteBooking + list/detail pages"
```

**What makes a good commit message:**
- Start with your name
- Use present tense ("add", "fix", "update" — not "added", "fixed")
- Be specific about what changed
- Keep it under 72 characters

---

## Creating a Pull Request (PR)

After pushing your branch, merge it into `main` via a Pull Request:

1. Go to your repo on GitHub
2. You'll see a yellow banner: **"Compare & pull request"** → click it
3. Fill in the PR form:
   - **Title:** same as your commit message
   - **Description:** brief list of what you did
4. Request review from one teammate (good practice)
5. Click **Create pull request**
6. Once approved (or just reviewed), click **Merge pull request**
7. Click **Confirm merge**
8. Delete the branch (GitHub offers this button)

**After merging — everyone pulls:**
```bash
git checkout main
git pull origin main
```

---

## File Ownership Map

To avoid conflicts, each member primarily edits their own files:

### Vidura — don't edit without asking
```
model/Person.java
model/User.java
model/AdminUser.java
service/UserService.java
controller/UserController.java
controller/DashboardController.java
templates/users/login.html
templates/users/register.html
templates/users/list.html
templates/users/edit.html
templates/users/profile.html
templates/dashboard.html
```

### Lahiru — don't edit without asking
```
model/WeddingPackage.java
model/BasicPackage.java
model/PremiumPackage.java
service/PackageService.java
controller/PackageController.java
util/VendorLinkedList.java
templates/packages/list.html
templates/packages/detail.html
templates/packages/add.html
templates/packages/edit.html
```

### Daham — don't edit without asking
```
model/Booking.java
model/Payment.java
service/BookingService.java
service/PaymentService.java
controller/BookingController.java
templates/bookings/list.html
templates/bookings/detail.html
templates/bookings/create.html
templates/bookings/edit.html
```

### Chanuka — don't edit without asking
```
model/Review.java
model/PublicReview.java
model/VerifiedReview.java
service/ReviewService.java
controller/ReviewController.java
templates/reviews/list.html
templates/reviews/submit.html
templates/reviews/edit.html
```

### Shared files (coordinate before editing)
```
templates/home.html
WeddingBookingApplication.java
pom.xml
application.properties
```

---

## Resolving Merge Conflicts

If Git says there's a conflict after `git pull`:

1. Open the conflicted file in IntelliJ IDEA
2. You'll see markers like this:
   ```
   <<<<<<< HEAD
   your code
   =======
   their code
   >>>>>>> main
   ```
3. IntelliJ shows a visual merge tool — click **Resolve** in the editor gutter
4. Choose: keep yours, keep theirs, or combine both
5. Save the file, then:
   ```bash
   git add .
   git commit -m "Vidura: resolve merge conflict in UserController"
   git push origin vidura/your-branch
   ```

**Best way to avoid conflicts:** always pull before starting, and finish your feature quickly before pushing.

---

## GitHub Commit Checklist (Before Submitting Assignment)

Each member should have **at least 5–8 meaningful commits** to show individual contribution.

### Minimum commits per member:

| Member | Required Commits |
|--------|-----------------|
| Vidura | login/register, user list, user edit, user delete, dashboard stats |
| Lahiru | add package, list sorted, package detail, edit, delete, linked list |
| Daham | create booking, booking list, booking detail, payment record, status update |
| Chanuka | submit review, list reviews, edit review, delete review, verified review |

### Check commit history on GitHub:
- Go to your repo → click **X commits** at the top of the file list
- Or run: `git log --oneline --author="YourName"`

---

## Quick Reference Card

```bash
# Start of day
git checkout main && git pull origin main
git checkout -b vidura/todays-feature

# While coding — save progress
git add . && git commit -m "Vidura: work in progress - user search"

# Done for the day — push
git push origin vidura/todays-feature

# Check what's changed
git status
git log --oneline -10

# Undo last commit (keep changes)
git reset --soft HEAD~1

# Discard all local changes (CAREFUL — cannot undo)
git checkout -- .
```

---

## Presentation Tips

When presenting, each member should demo their own section:

- **Vidura:** Login → Register → User list (show search working) → Dashboard stats
- **Lahiru:** Package list (show sort by price, sort by name) → Add package → Edit → Delete
- **Daham:** Book a package → Booking list → Booking detail → Record a payment → Change status
- **Chanuka:** Submit a review (public + verified) → Edit → Delete → Show star ratings

**Explain the OOP concepts you used** — markers will ask!

---

*Generated for SKU_OOP_G25 | SE1020 Object Oriented Programming*
