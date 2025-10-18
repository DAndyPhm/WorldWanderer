package flight;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FlightSearchTest {

    private FlightSearch flightSearch;

    //Create new object before each test
    @BeforeEach
    public void setUp() {
        flightSearch = new FlightSearch();
    }

    // Test 1: Passenger Count Boundary (Valid)
    @Test
    public void TC1testPassengerCountBoundaryValid() {
        // assign initial values as nulls
        assertNull(flightSearch.getDepartureDate());
        assertEquals(0, flightSearch.getAdultPassengerCount());

        boolean result = flightSearch.runFlightSearch("25/11/2025", "syd", false,
                "30/11/2025", "mel", "economy", 9, 0, 0);

        assertTrue(result);

        // Test values updated correctly
        assertEquals("25/11/2025", flightSearch.getDepartureDate());
        assertEquals("syd", flightSearch.getDepartureAirportCode());
        assertEquals(9, flightSearch.getAdultPassengerCount());
    }

    // Test 2: Children in Emergency Row or First Class (Invalid)
    @Test
    public void TC2testChildrenInEmergencyOrFirstClass() {
        // preload valid values
        flightSearch.runFlightSearch("01/12/2025", "syd", false,
                "05/12/2025", "mel", "economy", 2, 0, 0);

        boolean result = flightSearch.runFlightSearch("01/12/2025", "syd", true,
                "05/12/2025", "mel", "economy", 1, 1, 0);

        assertFalse(result);

        // Test values didn't updated
        assertEquals("01/12/2025", flightSearch.getDepartureDate());
        assertEquals("syd", flightSearch.getDepartureAirportCode());
        assertEquals(0, flightSearch.getInfantPassengerCount());
    }

    // Test 3: Infants in Emergency or Business (Invalid)
    @Test
    public void TC3testInfantsInEmergencyOrBusiness() {
        boolean result = flightSearch.runFlightSearch("10/12/2025", "mel", true,
                "15/12/2025", "pvg", "business", 1, 0, 1);
        assertFalse(result);
        // Fields remain unchanged (null)
        assertNull(flightSearch.getDepartureDate());
    }

    // Test 4: Children per adult boundary valid
    @Test
    public void TC4testChildrenPerAdultBoundaryValid() {
        boolean result = flightSearch.runFlightSearch("15/12/2025", "mel", false,
                "20/12/2025", "pvg", "economy", 1, 2, 0);
        assertTrue(result);
        assertEquals(2, flightSearch.getChildPassengerCount());
    }

    // Test 5: Infants per adult boundary valid
    @Test
    public void TC5testInfantsPerAdultBoundaryValid() {
        boolean result = flightSearch.runFlightSearch("15/12/2025", "mel", false,
                "20/12/2025", "pvg", "economy", 1, 0, 1);
        assertTrue(result);
        assertEquals(1, flightSearch.getInfantPassengerCount());
    }

    // Test 6: Departure Date in Past (Invalid)
    @Test
    public void TC6testDepartureDateInPast() {
        boolean result = flightSearch.runFlightSearch("01/01/2020", "syd", false,
                "02/01/2020", "mel", "economy", 1, 0, 0);
        assertFalse(result);
        // Fields remain unchanged (null)
        assertNull(flightSearch.getDepartureDate());
    }

    // Test 7: Invalid Date Format or Leap Year
    @Test
    public void TC7testInvalidDateFormat() {
        boolean result = flightSearch.runFlightSearch("32/01/2025", "syd", false,
                "02/02/2025", "mel", "economy", 1, 0, 0);
        assertFalse(result);
        // Fields remain unchanged (null)
        assertNull(flightSearch.getDepartureDate());
    }

    // Test 8: Return Date Before Departure
    @Test
    public void TC8testReturnBeforeDeparture() {
        boolean result = flightSearch.runFlightSearch("10/10/2025", "syd", false,
                "09/10/2025", "mel", "economy", 1, 0, 0);
        assertFalse(result);
        // Fields remain unchanged (null)
        assertNull(flightSearch.getDepartureDate());
    }

    // Test 9: Invalid Seating Class
    @Test
    public void TC9testInvalidSeatingClass() {
        boolean result = flightSearch.runFlightSearch("10/10/2025", "syd", false,
                "15/10/2025", "mel", "vip", 1, 0, 0);
        assertFalse(result);
        // Fields remain unchanged (null)
        assertNull(flightSearch.getDepartureDate());
    }

    // Test 10: Invalid Emergency Row Rules
    @Test
    public void TC10testInvalidEmergencyRowRules() {
        boolean result = flightSearch.runFlightSearch("10/10/2025", "syd", true,
                "15/10/2025", "mel", "business", 1, 0, 0);
        assertFalse(result);
        // Fields remain unchanged (null)
        assertNull(flightSearch.getDepartureDate());
    }

    // Test 11: Invalid Airports
    @Test
    public void TC11testInvalidAirports() {
        boolean result = flightSearch.runFlightSearch("10/10/2025", "abc", false,
                "15/10/2025", "xyz", "economy", 1, 0, 0);
        assertFalse(result);
        // Fields remain unchanged (null)
        assertNull(flightSearch.getDepartureDate());
    }
        //Negative Children
    @Test
    public void testNegativeChildren() {
        boolean result = flightSearch.runFlightSearch("01/12/2025", "syd", false,
                "05/12/2025", "mel", "economy", 2, -1, 0);
        assertFalse(result);
        // Fields remain unchanged (null)
        assertNull(flightSearch.getDepartureDate());
    }

    // Negative Infants
    @Test
    public void testNegativeInfants() {
        boolean result = flightSearch.runFlightSearch("01/12/2025", "syd", false,
                "05/12/2025", "mel", "economy", 2, 0, -1);
        assertFalse(result);
        // Fields remain unchanged (null)
        assertNull(flightSearch.getDepartureDate());
    }

    // test 12: Valid Multiple Case
    @Test
    public void TC12testValidMultipleData() {
        boolean result = flightSearch.runFlightSearch("01/01/2026", "lax", false,
                "05/01/2026", "cdg", "business", 1, 0, 0);
        assertTrue(result);
        // Fields changed to loaded values
        assertEquals("01/01/2026", flightSearch.getDepartureDate());
        assertEquals("lax", flightSearch.getDepartureAirportCode());
    }


}
