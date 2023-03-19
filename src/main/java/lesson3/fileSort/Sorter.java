package lesson3.fileSort;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Sorter {
    public File sortFile(File dataFile) throws IOException {
        System.out.println("Starts at " + new Date());
        Queue<File> files = splitIntoSortedParts(dataFile);
        File mergedFile = merge(files);

        System.out.println("Coping file");
        Path path = Paths.get(dataFile.getAbsolutePath());
        String newName = path.getParent() + File.separator + "Sorted_" + path.getFileName();
        Files.copy(Paths.get(mergedFile.getAbsolutePath()), Paths.get(newName), StandardCopyOption.REPLACE_EXISTING);

//        return mergedFile != null ? mergedFile : dataFile;

        System.out.println("Ended at " + new Date());

        return new File(newName);
    }

    private Queue<File> splitIntoSortedParts(File dataFile) throws IOException {

        Queue<File> files = new LinkedList<>();
        int sizePart = 1_000_000;

        System.out.println("Split file");

        try (Scanner scanner = new Scanner(new FileInputStream(dataFile))) {
            List<Long> tempList = new ArrayList<>(sizePart);
            while (scanner.hasNextLong()) {
                File currentFile = File.createTempFile("split", ".txt");
                currentFile.deleteOnExit();
                for (int i = 0; i < sizePart && scanner.hasNextLong(); i++) {
                    tempList.add(scanner.nextLong());
                }
                Collections.sort(tempList);
                try (PrintWriter pw = new PrintWriter(new FileWriter(currentFile, true))) {
                    for (Long aLong : tempList) {
                        pw.println(aLong);
                    }
                    pw.flush();
                }
                tempList.clear();
                files.add(currentFile);
                System.out.println("split on " + files.size() + " parts");
            }
        }
        return files;
    }

    private File merge(Queue<File> files) throws IOException {
        System.out.println("Merging files...");
        while (files.size() > 1) {
            File fileC = File.createTempFile("merge", ".txt");
            fileC.deleteOnExit();
            try (Scanner scanner1 = new Scanner(new FileInputStream(files.poll()));
                 Scanner scanner2 = new Scanner(new FileInputStream(files.poll()));
                 PrintWriter pw = new PrintWriter(new FileWriter(fileC, true))) {
                Long a = null;
                Long b = null;
                while (scanner1.hasNextLong()
                        || scanner2.hasNextLong()
                        || a != null
                        || b != null) {
                    if (a == null && scanner1.hasNextLong()) {
                        a = scanner1.nextLong();
                    }
                    if (b == null && scanner2.hasNextLong()) {
                        b = scanner2.nextLong();
                    }
                    if (b == null || (a != null && a <= b)) {
                        pw.println(a);
                        a = null;
                    } else {
                        pw.println(b);
                        b = null;
                    }
                }
                pw.flush();
            }
            files.add(fileC);
            System.out.println("Left " + files.size() + " file");
        }
        return files.poll();
    }
}
