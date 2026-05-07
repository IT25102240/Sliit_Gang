package com.wedding.util;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

// FILE HANDLING utility — used by all service classes
// Implements: read all lines, write all lines, append a line
public class FileHandler {

    private final String dataDir;

    public FileHandler(String dataDir) {
        this.dataDir = dataDir;
        // Make sure the data directory exists
        new File(dataDir).mkdirs();
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
