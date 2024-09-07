package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FolderCabinet implements Cabinet {
    private final File rootDirectory;
    public FolderCabinet(String rootPath) {
        this.rootDirectory = new File(rootPath);
    }
    @Override
    public Optional<File> findFolderByName(String name) {
        return findFolderByName(name, rootDirectory);
    }
    @Override
    public List<File> findFoldersBySize(String size) {
        List<File> result = new ArrayList<>();
        findFoldersBySize(size, rootDirectory, result);
        return result;
    }
    @Override
    public int count() {
        return count(rootDirectory);
    }
    private Optional<File> findFolderByName(String name, File directory) {
        if (!directory.isDirectory()) {
            return Optional.empty();
        }
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory() && file.getName().equals(name)) {
                    return Optional.of(file);
                }
                if (file.isDirectory()) {
                    Optional<File> found = findFolderByName(name, file);
                    if (found.isPresent()) {
                        return found;
                    }
                }
            }
        }
        return Optional.empty();
    }
    private void findFoldersBySize(String size, File directory, List<File> result) {
        if (!directory.isDirectory()) {
            return;
        }
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    String folderSize = determineSize(file);
                    if (folderSize.equals(size)) {
                        result.add(file);
                    }
                    findFoldersBySize(size, file, result);
                }
            }
        }
    }
    private int count(File directory) {
        if (!directory.isDirectory()) {
            return 0;
        }
        int total = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    total++;
                    total += count(file);
                }
            }
        }
        return total;
    }
    private String determineSize(File folder) {
        long sizeInBytes = calculateFolderSize(folder);
        if (sizeInBytes < 1024 * 1024) {
            return "SMALL";
        } else if (sizeInBytes < 100 * 1024 * 1024 && sizeInBytes > 1024 * 1024) {
            return "MEDIUM";
        } else {
            return "LARGE";
        }
    }
    private long calculateFolderSize(File folder) {
        long size = 0;
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    size += file.length();
                } else {
                    size += calculateFolderSize(file);
                }
            }
        }
        return size;
    }
}
