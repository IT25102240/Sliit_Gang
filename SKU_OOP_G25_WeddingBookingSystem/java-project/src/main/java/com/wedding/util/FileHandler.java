package com.wedding.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// FILE HANDLING utility — used by all service classes
// FIX: Added sanitise() helper — strips the pipe character "|" from ANY
//      user-supplied string before it reaches the file.  Previously only
//      specialRequests was sanitised; all other fields (venueName, packageName,
//      comment, etc.) could corrupt a record if a user typed a "|".
public class FileHandler {

    private final String dataDir;

    public FileHandler(String dataDir) {
        this.dataDir = dataDir;
        new File(dataDir).mkdirs();
    }

    // ── Sanitise a single value before writing to file ──────────────────────
    // Replaces "|" with a comma so the pipe-delimited format is never broken.
    // Call this on EVERY user-supplied string before building toFileString().
    public static String sanitise(String value) {
        if (value == null) return "";
        return value.replace("|", ",");
    }

    // READ all lines from a file (returns empty list if file doesn't exist)
    public List<String> readAll(String filename) {
        List<String> lines = new ArrayList<>();
        File file = new File(dataDir + File.separator + filename);
        if (!file.exists()) return lines;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading " + filename + ": " + e.getMessage());
        }
        return lines;
    }

    // WRITE all lines to a file (overwrites existing content)
    public void writeAll(String filename, List<String> lines) {
        File file = new File(dataDir + File.separator + filename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing " + filename + ": " + e.getMessage());
        }
    }

    // APPEND a single line to a file
    public void appendLine(String filename, String line) {
        File file = new File(dataDir + File.separator + filename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error appending to " + filename + ": " + e.getMessage());
        }
    }

    // CHECK if a file exists
    public boolean fileExists(String filename) {
        return new File(dataDir + File.separator + filename).exists();
    }
}
