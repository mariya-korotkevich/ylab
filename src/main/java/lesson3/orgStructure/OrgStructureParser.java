package lesson3.orgStructure;

import lesson3.orgStructure.Employee;

import java.io.File;
import java.io.IOException;

public interface OrgStructureParser {
    Employee parseStructure(File csvFile) throws IOException;
}