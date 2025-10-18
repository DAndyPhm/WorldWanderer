package flight;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class FlightSearchTest {

    @Test
    void testValidInput() {
        FlightSearch fs = new FlightSearch();
        boolean result = fs.runFlightSearch("23/12/2025", "mel", false, "28/12/2025", "pvg", "economy", 2, 2, 1);
        assertTrue(result);
        assertEquals("mel", fs.getDepartureAirportCode());
        assertEquals("pvg", fs.getDestinationAirportCode());
    }

    @Test
    void testTooManyPassengers() {
        FlightSearch fs = new FlightSearch();
        boolean result = fs.runFlightSearch("23/12/2025", "mel", false, "28/12/2025", "pvg", "economy", 5, 3, 2);
        assertFalse(result);
    }

    @Test
    void testChildrenInFirstClass() {
        FlightSearch fs = new FlightSearch();
        assertFalse(fs.runFlightSearch("23/12/2025", "syd", false, "28/12/2025", "lax", "first", 2, 1, 0));
    }

    @Test
    void testInfantInBusinessClass() {
        FlightSearch fs = new FlightSearch();
        assertFalse(fs.runFlightSearch("23/12/2025", "mel", false, "25/12/2025", "cdg", "business", 1, 0, 1));
    }

    @Test
    void testTooManyChildrenForAdults() {
        FlightSearch fs = new FlightSearch();
        assertFalse(fs.runFlightSearch("23/12/2025", "mel", false, "25/12/2025", "pvg", "economy", 1, 3, 0));
    }

    @Test
    void testInfantsWithoutEnoughAdults() {
        FlightSearch fs = new FlightSearch();
        assertFalse(fs.runFlightSearch("23/12/2025", "mel", false, "25/12/2025", "pvg", "economy", 1, 0, 2));
    }

    @Test
    void testPastDepartureDate() {
        FlightSearch fs = new FlightSearch();
        assertFalse(fs.runFlightSearch("01/01/2020", "mel", false, "05/01/2020", "pvg", "economy", 1, 0, 0));
    }

    @Test
    void testInvalidDateFormat() {
        FlightSearch fs = new FlightSearch();
        assertFalse(fs.runFlightSearch("2025-12-23", "mel", false, "28/12/2025", "pvg", "economy", 1, 0, 0));
    }

    @Test
    void testReturnBeforeDeparture() {
        FlightSearch fs = new FlightSearch();
        assertFalse(fs.runFlightSearch("25/12/2025", "mel", false, "20/12/2025", "pvg", "economy", 1, 0, 0));
    }

    @Test
    void testInvalidSeatingClass() {
        FlightSearch fs = new FlightSearch();
        assertFalse(fs.runFlightSearch("25/12/2025", "mel", false, "28/12/2025", "pvg", "vip", 1, 0, 0));
    }

    @Test
    void testEmergencyRowInBusiness() {
        FlightSearch fs = new FlightSearch();
        assertFalse(fs.runFlightSearch("25/12/2025", "mel", true, "28/12/2025", "pvg", "business", 1, 0, 0));
    }

    @Test
    void testInvalidAirports() {
        FlightSearch fs = new FlightSearch();
        assertFalse(fs.runFlightSearch("25/12/2025", "xyz", false, "28/12/2025", "mel", "economy", 1, 0, 0));
    }

    @Test
    void testSameDepartureAndDestination() {
        FlightSearch fs = new FlightSearch();
        assertFalse(fs.runFlightSearch("25/12/2025", "mel", false, "28/12/2025", "mel", "economy", 1, 0, 0));
    }
}
