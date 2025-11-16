package com.docencia.semaforo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SaiyanRaceSemaphoreMejoradoTest {

    @AfterEach
    void resetWinner() {
        SaiyanRaceSemaphoreMejorda.resetWinner();
    }

    @Test
    void testOnlyOneWinner() throws InterruptedException {
        SaiyanRaceSemaphoreMejorda gokuRunner = new SaiyanRaceSemaphoreMejorda("Goku");
        SaiyanRaceSemaphoreMejorda vegetaRunner = new SaiyanRaceSemaphoreMejorda("Vegeta");

        Thread goku = new Thread(gokuRunner);
        Thread vegeta = new Thread(vegetaRunner);

        goku.start();
        vegeta.start();

        goku.join();
        vegeta.join();

        boolean gokuWon = gokuRunner.getDistance() >= 100;
        boolean vegetaWon = vegetaRunner.getDistance() >= 100;

        assertTrue(gokuWon ^ vegetaWon,
                "Debe haber un único ganador.");
    }

    @Test
    void testThreadsFinish() throws InterruptedException {
        SaiyanRaceSemaphoreMejorda gokuRunner = new SaiyanRaceSemaphoreMejorda("Goku");
        SaiyanRaceSemaphoreMejorda vegetaRunner = new SaiyanRaceSemaphoreMejorda("Vegeta");

        Thread goku = new Thread(gokuRunner);
        Thread vegeta = new Thread(vegetaRunner);

        goku.start();
        vegeta.start();

        goku.join(5000);
        vegeta.join(5000);

        assertFalse(goku.isAlive(), "Goku debe acabar, no bloquearse.");
        assertFalse(vegeta.isAlive(), "Vegeta debe acabar, no bloquearse.");
    }
}
