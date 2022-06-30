package Order.FactoryCleanType;




import CompanyUtils.Company;
import CompanyUtils.PriceUtils.PriceCalculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

class CleanTypeSelectorTest
{
    CleanData cleanData;
    Company company;
    CleanTypeSelector cleanTypeSelector;
    PriceCalculator priceCalculator;

    @BeforeEach
    void setUp()
    {
        company = new Company(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        priceCalculator = new PriceCalculator();
        cleanTypeSelector = CleanTypeSelector.getInstance(company);
        company.setPriceCalculator(priceCalculator);
        String date ="2022-06-10";

        HashSet <String>residuos=new HashSet();
        residuos.add("Polvo");
        cleanData = new CleanData(date,residuos,1);
    }

    @Test
    void numberOfPetsSimpleOk()
    {
        assertTrue(cleanTypeSelector.numberOfPetsSimple(cleanData.cantMascotas));
    }
    @Test
    void numberOfPetsSimpleFail()
    {
        cleanData.setCantMascotas(5);
        assertFalse(cleanTypeSelector.numberOfPetsSimple(cleanData.cantMascotas));
    }

    @Test
    void hasJustDustOk(){

        assertTrue(cleanTypeSelector.hasJustPolvoOrEmpty(cleanData.residuos));
    }

    @Test
    void doesNotContainMudFalse(){
        cleanData.getResiduos().add("Barro");
        assertFalse(cleanTypeSelector.doesNotContainMud(cleanData.residuos));
    }
    @Test
    void doesNotContainMudOk(){
        cleanData.getResiduos().remove("Barro");
        assertTrue(cleanTypeSelector.doesNotContainMud(cleanData.residuos));
    }
    @Test
    void recentlyCleanedOk(){
        cleanData.setLastCleanDate("2022-06-10");
        String currentDate = "2022-06-12";
        assertTrue(cleanTypeSelector.recentlyCleaned(currentDate,cleanData.lastCleanDate));
    }

    @Test
    void recentlyCleanedFalse(){
        cleanData.setLastCleanDate("2022-04-10");
        String currentDate = "2022-06-12";
        assertFalse(cleanTypeSelector.recentlyCleaned(currentDate,cleanData.lastCleanDate));
    }

    @Test
    void isEmptyOk(){
        cleanData.getResiduos().remove("Polvo");
        assertTrue(cleanTypeSelector.hasJustPolvoOrEmpty(cleanData.residuos));

    }

    @Test
    void returnsSimpleOk()
    {
        assertEquals(new SimpleClean().getClass(), cleanTypeSelector.setCleanStrategy(cleanData).getClass());
    }

    @Test
    void returnsSimpleWithMultipleDogsBecauseOfJustDustOk(){
        cleanData.setCantMascotas(5);
        assertInstanceOf(SimpleClean.class, cleanTypeSelector.setCleanStrategy(cleanData));
    }

    @Test
    void returnsSimpleWith2MaterialsBecauseOfDate(){
        cleanData.setLastCleanDate(LocalDate.now().toString());
        cleanData.getResiduos().add("Barro");
        cleanData.setCantMascotas(5);
        assertInstanceOf(SimpleClean.class, cleanTypeSelector.setCleanStrategy(cleanData));
    }

    @Test
    void returnsComplexBecauseNotSimpleWithBarro() {
        cleanData.setLastCleanDate("2021-05-19");
        cleanData.getResiduos().add("Barro");
        cleanData.setCantMascotas(5);
        assertInstanceOf(ComplexClean.class, cleanTypeSelector.setCleanStrategy(cleanData));
    }
    @Test
    void returnsComplexBecauseNotSimpleWith2Residuos(){
        cleanData.setLastCleanDate("2021-05-19");
        cleanData.getResiduos().add("Pelos");
        cleanData.setCantMascotas(5);
        assertInstanceOf(ComplexClean.class, cleanTypeSelector.setCleanStrategy(cleanData));
    }
}