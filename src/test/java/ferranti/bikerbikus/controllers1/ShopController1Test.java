package ferranti.bikerbikus.controllers1;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ShopController1Test {

    @Test
    void finalPrice() {
        LocalDate day = LocalDate.now().plusDays(10);
        int price= 5;
        int result = 0;
        result = ShopController1.finalPrice(day, price);
        assertEquals(50,result);
    }
}