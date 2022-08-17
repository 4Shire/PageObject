package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CardPage {
    private SelenideElement amount = $("[data-test-id='amount'] input");
    private SelenideElement amountFrom = $("[data-test-id='from'] input");
    private SelenideElement buttonFrom = $("[data-test-id='action-transfer']");

    public CardPage() {
        amount.shouldBe(visible);
    }

    public DashboardPage card(DataHelper.CardsInfo cardsInfo) {
        amount.setValue(String.valueOf(cardsInfo.getSum()));
        amountFrom.setValue(cardsInfo.getNumCard());
        buttonFrom.click();
        return new DashboardPage();
    }
}
