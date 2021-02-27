package ru.otus.atm;

import org.junit.jupiter.api.*;
import ru.otus.atm.exceptions.*;
import ru.otus.atm.notes.*;

import java.util.Map;

public class AtmTest {
    private Atm atm;

    @BeforeEach
    public void setupAtm() {
        atm = new Atm();
        atm.addCartridge(new CashCartridge(Banknotes.FIFTY_DOLLARS, 50));
        atm.addCartridge(new CashCartridge(Banknotes.TEN_DOLLARS, 3));
        atm.addCartridge(new CashCartridge(Banknotes.ONE_HUNDRED_DOLLARS, 30));
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
                Map.entry(Banknotes.FIFTY_DOLLARS, 10),
                Map.entry(Banknotes.TEN_DOLLARS, 20)
        ));
        float newRemainder = atm.getVaultRemainder();

        Assertions.assertEquals(initialRemainder +
                Banknotes.FIFTY_DOLLARS.getValue() * 10 +
                Banknotes.TEN_DOLLARS.getValue() * 20, newRemainder);
    }

    @DisplayName("Вклад банкнот разных номиналов, но такой ячейки нет в банкомате")
    @Test
    public void depositVariousNotesTest2() {
        AtmException e = Assertions.assertThrows(AtmException.class,
                () -> atm.deposit(Map.ofEntries(Map.entry(Banknotes.ONE_DOLLAR, 20))));
        Assertions.assertEquals(e.getErrorCode(), ErrorCode.NO_SUCH_CARTRIDGE_FOR_NOTE);
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
        AtmException e = Assertions.assertThrows(AtmException.class, () -> atm.withdraw(155F));
        Assertions.assertEquals(e.getErrorCode(), ErrorCode.NOT_ENOUGH_NOTES_IN_CARTRIDGE);

        float newRemainder = atm.getVaultRemainder();
        Assertions.assertEquals(initialRemainder, newRemainder);
    }

    @DisplayName("Сумма не может быть выдана: превышает общую сумму в АТМ")
    @Test
    public void withdrawVariousNotesTest3()  {
        float initialRemainder = atm.getVaultRemainder();
        AtmException e = Assertions.assertThrows(AtmException.class,
                () -> atm.withdraw(155000F));
        Assertions.assertEquals(e.getErrorCode(), ErrorCode.WITHDRAWAL_EXCEEDED_VAULT_REMAINDER);

        float newRemainder = atm.getVaultRemainder();
        Assertions.assertEquals(initialRemainder, newRemainder);
    }

    @DisplayName("Сумма не может быть выдана: не удаётся составить нужную сумму из имеющихся в ячейках банкнот")
    @Test
    public void withdrawVariousNotesTest4()  {
        float initialRemainder = atm.getVaultRemainder();
        AtmException e = Assertions.assertThrows(AtmException.class,
                () -> atm.withdraw(190F));
        Assertions.assertEquals(e.getErrorCode(), ErrorCode.NOT_ENOUGH_NOTES_IN_CARTRIDGE);

        float newRemainder = atm.getVaultRemainder();
        Assertions.assertEquals(initialRemainder, newRemainder);
    }
}