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
        Employee result = null;
        for (Employee employee : employees.values()) {
            if (employee.getBossId() == 0) {
                result = employee;
            } else {
                Employee boss = employees.get(employee.getBossId());
                employee.setBoss(boss);
                boss.getSubordinate().add(employee);
            }
        }
        return result;
    }

    private Map<Long, Employee> readCsvFile(File csvFile) throws FileNotFoundException {
        Map<Long, Employee> result = new HashMap<>();
        try (Scanner scanner = new Scanner(csvFile)) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(";");

                Employee newEmployee = new Employee();
                newEmployee.setId(Long.parseLong(data[0]));
                newEmployee.setBossId(Long.parseLong(data[1].isEmpty() ? "0" : data[1]));
                newEmployee.setName(data[2]);
                newEmployee.setPosition(data[3]);

                result.put(newEmployee.getId(), newEmployee);
            }
        }
        return result;
    }
}