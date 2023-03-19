package lesson3.fileSort;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Sorter {
    private static final int SIZE_OF_PART = 1_000_000;

    public File sortFile(File dataFile) throws IOException {
        Queue<File> files = splitIntoSortedFiles(dataFile);
        File mergedFile = mergeFiles(files);
        return copySortedFile(mergedFile, dataFile);
    }

    private Queue<File> splitIntoSortedFiles(File dataFile) throws IOException {
        Queue<File> files = new LinkedList<>();
        try (Scanner scanner = new Scanner(new FileInputStream(dataFile))) {
            List<Long> tempList = new ArrayList<>(SIZE_OF_PART);
            while (scanner.hasNextLong()) {
                for (int i = 0; i < SIZE_OF_PART && scanner.hasNextLong(); i++) {
                    tempList.add(scanner.nextLong());
                }
                Collections.sort(tempList);

                File tempFile = createTempFile();
                try (PrintWriter pw = new PrintWriter(tempFile)) {
                    for (Long aLong : tempList) {
                        pw.println(aLong);
                    }
                    pw.flush();
                }

                tempList.clear();
                files.add(tempFile);
            }
        }
        return files;
    }

    private File mergeFiles(Queue<File> files) throws IOException {
        while (files.size() > 1) {
            File tempFile = createTempFile();
            try (Scanner scanner1 = new Scanner(new FileInputStream(files.poll()));
                 Scanner scanner2 = new Scanner(new FileInputStream(files.poll()));
                 PrintWriter pw = new PrintWriter(tempFile)) {
                Long number1 = null;
                Long number2 = null;
                while (scanner1.hasNextLong()
                        || scanner2.hasNextLong()
                        || number1 != null
                        || number2 != null) {
                    if (number1 == null && scanner1.hasNextLong()) {
                        number1 = scanner1.nextLong();
                    }
                    if (number2 == null && scanner2.hasNextLong()) {
                        number2 = scanner2.nextLong();
                    }
                    if (number2 == null || (number1 != null && number1 <= number2)) {
                        pw.println(number1);
                        number1 = null;
                    } else {
                        pw.println(number2);
                        number2 = null;
                    }
                }
                pw.flush();
            }
            files.add(tempFile);
        }
        return files.poll();
    }

    private File copySortedFile(File sortedFile, File dataFile) throws IOException {
        Path path = Paths.get(dataFile.getAbsolutePath());
        String newName = path.getParent() + File.separator + "Sorted_" + path.getFileName();
        Files.copy(Paths.get(sortedFile.getAbsolutePath()), Paths.get(newName), StandardCopyOption.REPLACE_EXISTING);
        return new File(newName);
    }

    private File createTempFile() throws IOException {
        File currentFile = File.createTempFile("tmp", ".txt", new File("/home/maria/temmp/"));
        currentFile.deleteOnExit();
        return currentFile;
    }
}