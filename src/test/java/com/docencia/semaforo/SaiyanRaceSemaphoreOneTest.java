package com.docencia.semaforo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SaiyanRaceSemaphoreOneTest {

    @AfterEach
    void resetWinner() {
        SaiyanRaceSemaphoreOne.resetWinner();
    }

    @Test
    void testOnlyOneWinner() throws InterruptedException {
        SaiyanRaceSemaphoreOne gokuRunner = new SaiyanRaceSemaphoreOne("Goku");
        SaiyanRaceSemaphoreOne vegetaRunner = new SaiyanRaceSemaphoreOne("Vegeta");

        Thread goku = new Thread(gokuRunner);
        Thread vegeta = new Thread(vegetaRunner);

        goku.start();
        vegeta.start();

        goku.join();
        vegeta.join();

        boolean gokuWon = gokuRunner.getDistance() >= 100;
        boolean vegetaWon = vegetaRunner.getDistance() >= 100;

        assertTrue(gokuWon ^ vegetaWon,
                "Debe haber un único ganador (XOR).");
    }

    @Test
    void testThreadsFinish() throws InterruptedException {
        SaiyanRaceSemaphoreOne gokuRunner = new SaiyanRaceSemaphoreOne("Goku");
        SaiyanRaceSemaphoreOne vegetaRunner = new SaiyanRaceSemaphoreOne("Vegeta");

        Thread goku = new Thread(gokuRunner);
        Thread vegeta = new Thread(vegetaRunner);

        goku.start();
        vegeta.start();

        goku.join(5000);
        vegeta.join(5000);

        assertFalse(goku.isAlive(), "Goku no debe quedar bloqueado.");
        assertFalse(vegeta.isAlive(), "Vegeta no debe quedar bloqueado.");
    }
}
