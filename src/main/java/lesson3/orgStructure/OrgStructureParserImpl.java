package lesson3.orgStructure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OrgStructureParserImpl implements OrgStructureParser {
    @Override
    public Employee parseStructure(File csvFile) throws IOException {
        Map<Long, Employee> employees = readCsvFile(csvFile);
        Employee boss = null;
        for (Employee employee : employees.values()) {
            if (employee.getBossId() == 0) {
                boss = employee;
            } else {
                Employee currentBoss = employees.get(employee.getBossId());
                employee.setBoss(currentBoss);
                currentBoss.getSubordinate().add(employee);
            }
        }
        return boss;
    }

    private Map<Long, Employee> readCsvFile(File csvFile) throws FileNotFoundException {
        Map<Long, Employee> result = new HashMap<>();
        try (Scanner scanner = new Scanner(csvFile)) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                Employee newEmployee = readEmployee(scanner.nextLine());
                result.put(newEmployee.getId(), newEmployee);
            }
        }
        return result;
    }

    private Employee readEmployee(String lineFromCsvFile) {
        String[] data = lineFromCsvFile.split(";");

        Employee employee = new Employee();
        employee.setId(Long.parseLong(data[0]));
        employee.setBossId(Long.parseLong(data[1].isEmpty() ? "0" : data[1]));
        employee.setName(data[2]);
        employee.setPosition(data[3]);
        return employee;
    }
}