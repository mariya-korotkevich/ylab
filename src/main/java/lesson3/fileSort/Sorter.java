package lesson3.fileSort;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Sorter {
    private static final int SIZE_OF_PART = 1_000_000;

    public File sortFile(File dataFile) throws IOException {
        Queue<File> files = splitIntoSortedFiles(dataFile);
        if (files.isEmpty()) {
            throw new IllegalArgumentException("Нет данных для сортировки");
        }
        File mergedFile = mergeFiles(files);
        return copySortedFile(mergedFile, dataFile);
    }

    private Queue<File> splitIntoSortedFiles(File dataFile) throws IOException {
        Queue<File> files = new LinkedList<>();
        try (Scanner scanner = new Scanner(new FileInputStream(dataFile))) {
            while (scanner.hasNextLong()) {
                File tempFile = getFileWithSortedDataPart(scanner);
                files.add(tempFile);
            }
        }
        return files;
    }

    private File getFileWithSortedDataPart(Scanner scanner) throws IOException {
        List<Long> tempList = getSortedDataPart(scanner);
        return writeListToFile(tempList);
    }

    private List<Long> getSortedDataPart(Scanner scanner) {
        List<Long> tempList = new ArrayList<>(SIZE_OF_PART);
        for (int i = 0; i < SIZE_OF_PART && scanner.hasNextLong(); i++) {
            tempList.add(scanner.nextLong());
        }
        Collections.sort(tempList);
        return tempList;
    }

    private File createTempFile() throws IOException {
        File currentFile = File.createTempFile("tmp", ".txt");
        currentFile.deleteOnExit();
        return currentFile;
    }

    private File writeListToFile(List<Long> list) throws IOException {
        File tempFile = createTempFile();
        try (PrintWriter pw = new PrintWriter(tempFile)) {
            for (Long aLong : list) {
                pw.println(aLong);
            }
            pw.flush();
        }
        return tempFile;
    }

    private File mergeFiles(Queue<File> files) throws IOException {
        while (files.size() > 1) {
            File tempFile = mergeTwoFiles(files.poll(), files.poll());
            files.add(tempFile);
        }
        return files.poll();
    }

    private File mergeTwoFiles(File file1, File file2) throws IOException {
        File tempFile = createTempFile();
        try (Scanner scanner1 = new Scanner(new FileInputStream(file1));
             Scanner scanner2 = new Scanner(new FileInputStream(file2));
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
        return tempFile;
    }

    private File copySortedFile(File sortedFile, File dataFile) throws IOException {
        Path path = Paths.get(dataFile.getAbsolutePath());
        String newName = path.getParent() + File.separator + "Sorted_" + path.getFileName();
        Files.copy(Paths.get(sortedFile.getAbsolutePath()), Paths.get(newName), StandardCopyOption.REPLACE_EXISTING);
        return new File(newName);
    }
}