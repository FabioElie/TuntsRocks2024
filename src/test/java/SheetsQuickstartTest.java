import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SheetsQuickstartTest {

    private SheetsQuickstart sheetsQuickstart;


    @BeforeEach
    void setUp() {
        sheetsQuickstart = new SheetsQuickstart();
    }


    @Test
    void calculateResultTest() {
        assertEquals("Reprovado por Falta", SheetsQuickstart.calculateResult(30, 10)); // Absences above allowed and maximum grade
        assertEquals("Reprovado por Nota", SheetsQuickstart.calculateResult(0, 4));  // No absences and insufficient grade for approval
        assertEquals("Exame Final", SheetsQuickstart.calculateResult(20, 6)); // Absences below the allowed limit and grade that leads to taking the Final Exam
        assertEquals("Aprovado", SheetsQuickstart.calculateResult(20, 8)); // Absences below the allowed limit and sufficient grade for approval
    }

    @Test
    void calculateResultEdgeCasesTest() {
        assertEquals("Reprovado por Falta", SheetsQuickstart.calculateResult(26, 0)); // Exact minimum absences that fail and grade zero
        assertEquals("Aprovado", SheetsQuickstart.calculateResult(25, 10)); // Exact maximum absences possible without failing and maximum grade
        assertEquals("Aprovado", SheetsQuickstart.calculateResult(0, 7)); // No absences and sufficient grade for approval
    }

    @Test
    void calculateResultNegativeValuesTest() {
        assertEquals("Reprovado por Falta", SheetsQuickstart.calculateResult(-40, 5)); // Negative absences
        assertEquals("Reprovado por Nota", SheetsQuickstart.calculateResult(20, -3)); // Negative grade
        assertEquals("Reprovado por Falta", SheetsQuickstart.calculateResult(-30, -3)); // Negative absences and grade
    }

    @Test
    void calculateResultBoundaryValuesTest() {
        assertEquals("Reprovado por Nota", SheetsQuickstart.calculateResult(25, 4)); // Exact maximum absences possible without failing and grade below the required
        assertEquals("Exame Final", SheetsQuickstart.calculateResult(25, 5)); // Exact maximum absences possible without failing and grade on the edge for a final exam
        assertEquals("Reprovado por Falta", SheetsQuickstart.calculateResult(26, 10)); // Exact minimum absences that fail and maximum grade
    }

    // Add more tests for other methods and edge cases as needed
}
