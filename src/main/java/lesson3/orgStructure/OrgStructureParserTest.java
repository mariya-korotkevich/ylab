package lesson3.orgStructure;

import java.io.File;
import java.io.IOException;

public class OrgStructureParserTest {
    public static void main(String[] args) throws IOException {
        File csvFile;
        try {
            csvFile = new File(
                    OrgStructureParserTest.class.getClassLoader().getResource("orgStructure.csv").getFile());
        } catch (NullPointerException exp) {
            System.out.println("Нет файла для загрузки: orgStructure.csv");
            exp.printStackTrace();
            return;
        }

        OrgStructureParser parser = new OrgStructureParserImpl();
        Employee boss = parser.parseStructure(csvFile);
        System.out.println("Боссом является сотрудник: " + boss);
    }
}