# How to Run the Project — Step by Step

Complete guide for Windows. Every step has exact instructions.

---

## PART 1 — Install the Required Software (Do This Once)

You need 3 programs: **Java 17**, **IntelliJ IDEA**, and **Git**.

---

### Install Java 17

1. Go to: https://adoptium.net/temurin/releases/
2. Set these filters:
   - Version: **17**
   - Operating System: **Windows**
   - Architecture: **x64**
   - Package Type: **JDK**
3. Click **Download .msi**
4. Run the downloaded file
5. On the installation screen — tick the checkbox: **"Set JAVA_HOME variable"**
6. Click Next → Next → Install
7. Verify it worked: open **Command Prompt** (search "cmd") and type:
   ```
   java -version
   ```
   You should see: `openjdk version "17..."`

---

### Install IntelliJ IDEA Community Edition (Free)

1. Go to: https://www.jetbrains.com/idea/download/
2. Scroll down to **Community Edition** → click **Download**
3. Run the downloaded `.exe` file
4. On options screen, tick:
   - ✅ Add "Open Folder as Project"
   - ✅ .java (file association)
5. Click Next → Install → Finish

---

### Install Git

1. Go to: https://git-scm.com/download/win
2. Click the top download link (64-bit)
3. Run the installer — click Next through all steps (defaults are fine)
4. Verify: open Command Prompt and type:
   ```
   git --version
   ```
   You should see: `git version 2.xx`

---

## PART 2 — Get the Project Files

You have two options: download the archive OR clone from GitHub.

---

### Option A — Extract the Downloaded Archive

If you downloaded `SKU_OOP_G25_WeddingBookingSystem.tar.gz` from Replit:

**On Windows** (built-in, Windows 11):
1. Right-click the file
2. Click **Extract All**
   - If you don't see "Extract All", you need **7-Zip** (free): https://7-zip.org
   - Right-click → **7-Zip → Extract Here**
3. You will get a folder called `java-project`

**On Mac/Linux** (Terminal):
```bash
tar -xzf SKU_OOP_G25_WeddingBookingSystem.tar.gz
```

---

### Option B — Clone from GitHub (After Vidura pushes)

Open Command Prompt and run:
```bash
cd Desktop
git clone https://github.com/YOUR_USERNAME/SKU_OOP_G25_Wedding_Planning.git
cd SKU_OOP_G25_Wedding_Planning
```

---

## PART 3 — Open the Project in IntelliJ IDEA

1. Open **IntelliJ IDEA**
2. On the Welcome screen, click **Open**
3. Navigate to the `java-project` folder (the one with `pom.xml` inside it)
4. Select the `java-project` folder → click **OK**

   > ⚠️ Important: Select the `java-project` folder, NOT a file inside it

5. A popup appears: **"Trust and Open Project?"** → click **Trust Project**
6. IntelliJ will open and you'll see a progress bar at the bottom: **"Downloading Maven dependencies..."**
   - This downloads Spring Boot automatically
   - Wait until it finishes (1–3 minutes, depends on internet speed)
   - You'll know it's done when the progress bar disappears

---

## PART 4 — Create the data folder

The app needs a `data/` folder to store its text files.

1. In IntelliJ, look at the left panel (Project view)
2. Right-click the `java-project` folder → **New → Directory**
3. Type: `data`
4. Press Enter

   (If the `data/` folder already exists from the archive, skip this step)

---

## PART 5 — Run the Application

1. In IntelliJ's left panel, expand:
   ```
   java-project
   └── src
       └── main
           └── java
               └── com
                   └── wedding
                       └── WeddingBookingApplication.java  ← open this
   ```

2. Double-click `WeddingBookingApplication.java` to open it

3. You will see a green **▶ play button** in the left margin next to `public static void main`

4. Click that green **▶ button** → select **Run 'WeddingBookingApplication'**

5. Watch the **Run panel** at the bottom of IntelliJ

6. Wait until you see this line:
   ```
   Started WeddingBookingApplication in X.XXX seconds
   ```

   This means the server is running!

---

## PART 6 — Open in Your Browser

1. Open any browser (Chrome, Firefox, Edge)
2. Go to:
   ```
   http://localhost:8080
   ```
3. You should see the **EverAfter Wedding Booking System** home page

---

## PART 7 — Log In

Use these demo accounts:

| Role | Email | Password | Can access |
|------|-------|----------|-----------|
| **Admin** | admin@wedding.com | admin123 | Everything — users, dashboard, all bookings |
| **Customer** | emma@example.com | customer123 | Packages, bookings, reviews |

---

## PART 8 — Stop the Application

When you're done:
- In IntelliJ, click the **red ■ Stop button** at the top right of the Run panel
- Or press **Ctrl + F2**

---

## Troubleshooting

### "Port 8080 already in use"
Another program is using port 8080. Either:
- Close the other program
- Or change the port: open `src/main/resources/application.properties` and change:
  ```
  server.port=8080
  ```
  to:
  ```
  server.port=9090
  ```
  Then go to `http://localhost:9090` instead.

---

### "Cannot find Java SDK" or red errors in IntelliJ

1. Go to **File → Project Structure** (Ctrl + Alt + Shift + S)
2. Under **Project**, set **SDK** to: `17` (or temurin-17)
3. If you don't see it, click **Add SDK → JDK...**
4. Navigate to where you installed Java: usually `C:\Program Files\Eclipse Adoptium\jdk-17...`
5. Click OK → Apply → OK

---

### Maven dependencies won't download

1. Go to the right side of IntelliJ — click the **Maven** tab
2. Click the **↻ Reload** button (refresh icon)
3. Wait for it to finish downloading

---

### "data folder not found" error in console

Create the folder manually:
- Open File Explorer → navigate to your `java-project` folder
- Right-click → New → Folder → name it `data`
- Restart the app

---

### Red underlines everywhere in the code

IntelliJ is still indexing. Wait 2–3 minutes for it to finish loading. You'll see "Indexing..." in the bottom status bar. Once it disappears, the errors should clear.

---

## How Each Member Runs the Same Project (Via GitHub)

Once Vidura has pushed to GitHub:

```
Step 1:  Open Command Prompt
Step 2:  cd Desktop
Step 3:  git clone https://github.com/VIDURA_USERNAME/SKU_OOP_G25_Wedding_Planning.git
Step 4:  Open IntelliJ IDEA → Open → select the cloned folder
Step 5:  Wait for Maven to download dependencies
Step 6:  Run WeddingBookingApplication.java
Step 7:  Go to http://localhost:8080
```

---

*If you get stuck on any step, screenshot the error and share it in the group chat.*
