package ru.otus.atm;

import org.junit.jupiter.api.*;
import ru.otus.atm.exceptions.*;
import ru.otus.atm.notes.FiftyDollars;
import ru.otus.atm.notes.OneDollar;
import ru.otus.atm.notes.OneHundredDollars;
import ru.otus.atm.notes.TenDollars;

import java.util.Map;

public class AtmTest {
    private Atm atm;

    @BeforeEach
    public void setupAtm() {
        atm = new Atm();
        atm.addCartridge(new CashCartridge(new FiftyDollars(), 50));
        atm.addCartridge(new CashCartridge(new TenDollars(), 3));
        atm.addCartridge(new CashCartridge(new OneHundredDollars(), 30));
        System.out.println("АТМ настроен");
    }

    @AfterEach
    public void teardownAtm() {
        atm = null;
        System.out.println("АТМ расстроен");
    }

    @DisplayName("Выдача суммы остатка денежных средств")
    @Test
    public void getCashRemainderTest() {
        float remainder = atm.getVaultRemainder();
        Assertions.assertEquals(remainder, 5530);
    }

    @DisplayName("Вклад банкнот разных номиналов")
    @Test
    public void depositVariousNotesTest1() {
        float initialRemainder = atm.getVaultRemainder();
        atm.deposit(Map.ofEntries(
                Map.entry(new FiftyDollars(), 10),
                Map.entry(new TenDollars(), 20)
        ));
        float newRemainder = atm.getVaultRemainder();

        Assertions.assertEquals(initialRemainder +
                new FiftyDollars().getValue() * 10 +
                new TenDollars().getValue() * 20, newRemainder);
    }

    @DisplayName("Вклад банкнот разных номиналов, но такой ячейки нет в банкомате")
    @Test
    public void depositVariousNotesTest2() {
        Assertions.assertThrows(NoSuchCartridgeForNoteException.class,
                () -> atm.deposit(Map.ofEntries(Map.entry(new OneDollar(), 20))));
    }

    @DisplayName("Выдача запрошенной суммы разным кол-вом банкнот")
    @Test
    public void withdrawVariousNotesTest1() {
        float initialRemainder = atm.getVaultRemainder();
        atm.withdraw(150F);
        float newRemainder = atm.getVaultRemainder();

        Assertions.assertEquals(initialRemainder - 150F, newRemainder);
    }

    @DisplayName("Сумма не может быть выдана: не кратна доступным банктнотам в ячейках")
    @Test
    public void withdrawVariousNotesTest2() {
        float initialRemainder = atm.getVaultRemainder();
        Assertions.assertThrows(NotEnoughNotesInCartridgesException.class, () -> atm.withdraw(155F));
        float newRemainder = atm.getVaultRemainder();

        Assertions.assertEquals(initialRemainder, newRemainder);
    }

    @DisplayName("Сумма не может быть выдана: превышает общую сумму в АТМ")
    @Test
    public void withdrawVariousNotesTest3()  {
        float initialRemainder = atm.getVaultRemainder();
        //atm.withdraw(155000F);
        Assertions.assertThrows(WithdrawalExceededVaultRemainderException.class,
                () -> atm.withdraw(155000F));
        float newRemainder = atm.getVaultRemainder();

        Assertions.assertEquals(initialRemainder, newRemainder);
    }

    @DisplayName("Сумма не может быть выдана: не удаётся составить нужную сумму из имеющихся в ячейках банкнот")
    @Test
    public void withdrawVariousNotesTest4()  {
        float initialRemainder = atm.getVaultRemainder();
        //atm.withdraw(155000F);
        Assertions.assertThrows(NotEnoughNotesInCartridgesException.class,
                () -> atm.withdraw(190F));
        float newRemainder = atm.getVaultRemainder();

        Assertions.assertEquals(initialRemainder, newRemainder);
    }

}
