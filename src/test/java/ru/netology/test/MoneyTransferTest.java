package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    int amount = 100;
    int amountZero = 0;
    int amountLargeSum = 5000;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferFromTheFirstCardToTheSecond() {
        var dashboardPage = new DashboardPage();
        var balanceFirstBefore = dashboardPage.getCardBalance(0);
        var balanceSecondBefore = dashboardPage.getCardBalance(1);
        var balanceFirstAfter = balanceFirstBefore + amount;
        var balanceSecondAfter = balanceSecondBefore - amount;
        var verificationFirstCard = DataHelper.getFirstCardsInfo(amount);
        dashboardPage.personFirstCard().card(verificationFirstCard);

        assertEquals(balanceFirstAfter, dashboardPage.getCardBalance(0));
        assertEquals(balanceSecondAfter, dashboardPage.getCardBalance(1));
    }

    @Test
    void shouldTransferFromTheSecondCardToTheFirst() {
        var dashboardPage = new DashboardPage();
        var balanceFirstBefore = dashboardPage.getCardBalance(0);
        var balanceSecondBefore = dashboardPage.getCardBalance(1);
        var balanceFirstAfter = balanceFirstBefore - amount;
        var balanceSecondAfter = balanceSecondBefore + amount;
        var verificationSecondCard = DataHelper.getSecondCardsInfo(amount);
        dashboardPage.personSecondCard().card(verificationSecondCard);

        assertEquals(balanceFirstAfter, dashboardPage.getCardBalance(0));
        assertEquals(balanceSecondAfter, dashboardPage.getCardBalance(1));
    }

    @Test
    void shouldZeroTranslation() {
        var dashboardPage = new DashboardPage();
        var balanceFirstBefore = dashboardPage.getCardBalance(0);
        var balanceSecondBefore = dashboardPage.getCardBalance(1);
        var balanceFirstAfter = balanceFirstBefore + amountZero;
        var balanceSecondAfter = balanceSecondBefore - amountZero;
        var verificationFirstCard = DataHelper.getFirstCardsInfo(amountZero);
        dashboardPage.personFirstCard().card(verificationFirstCard);

        assertEquals(balanceFirstAfter, dashboardPage.getCardBalance(0));
        assertEquals(balanceSecondAfter, dashboardPage.getCardBalance(1));
    }

    @Test
    void shouldTransferWithANegativeBalance() {
        var dashboardPage = new DashboardPage();
        var balanceFirstBefore = dashboardPage.getCardBalance(0);
        var balanceSecondBefore = dashboardPage.getCardBalance(1);
        var balanceFirstAfter = balanceFirstBefore - amountLargeSum;
        var balanceSecondAfter = balanceSecondBefore + amountLargeSum;
        var verificationSecondCard = DataHelper.getSecondCardsInfo(amountLargeSum);
        dashboardPage.personSecondCard().card(verificationSecondCard);

        assertEquals(balanceFirstAfter, dashboardPage.getCardBalance(0));
        assertEquals(balanceSecondAfter, dashboardPage.getCardBalance(1));
    }
}