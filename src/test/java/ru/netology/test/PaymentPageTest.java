package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.SQLHelper;
import ru.netology.page.MainPage;
import ru.netology.data.DataHelper;
import ru.netology.page.PaymentPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.CardInfo.*;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.getPaymentStatus;

public class PaymentPageTest {

    @BeforeAll
    static void setUpAll() {

        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {

        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {

        open("http://localhost:8080");
        SQLHelper.cleanTable();
    }


    // Запуск страницы оплаты
    @Test
    void shouldGetPaymentPage() {
        val mainPage = new MainPage();
        mainPage.payByCard();
    }


    // Успешная оплата картой - А001
    @Test
    void shouldPayByCardSuccessfully() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.successfullPayment();
        String dataSQLPayment = getPaymentStatus();
        assertEquals("APPROVED", dataSQLPayment);
    }

    // Оплата не должна пройти - А002
    @Test
    void shouldNotPayWithDeclinedCard() {
        val cardInfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.declinedPayment();
        String dataSQLPayment = SQLHelper.getPaymentStatus();
        assertEquals("DECLINED", dataSQLPayment);
    }

    // Номер карты не заполнен - А003
    @Test
    void shouldNotPayWithoutCard() {
        val cardInfo = new DataHelper.CardInfo(null, getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cardNumberErrorVisible();
    }

    // Короткий номер карты (10 цифр) - А004
    @Test
    void shouldNotPayByShortCard() {
        val cardInfo = new DataHelper.CardInfo(getShortCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cardNumberErrorVisible();
    }

    // Неизвестная карта - А005
    @Test
    void shouldNotPayByUnknownCard() {
        val cardInfo = new DataHelper.CardInfo(getUnknownCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.declinedPayment();
    }

    // Номер карты буквами - А006
    @Test
    void shouldNotPayByCardWithLettered() {
        val cardInfo = new DataHelper.CardInfo(getCardNumberWithLettered(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cardNumberErrorVisible();
    }

    // Номер карты символами - А007
    @Test
    void shouldNotPayByCardWithSymbols() {
        val cardInfo = new DataHelper.CardInfo(getCardNumberWithSymbols(), getValidMonth(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cardNumberErrorVisible();
    }

    // Месяц не заполнен - А008
    @Test
    void shouldNotPayWithoutMonth() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), null, getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.monthErrorVisible();
    }

    // Месяц со значением 00 - А009
    @Test
    void shouldNotPayWithMonthWithNulls() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getMonthWithNulls(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.monthErrorVisible();
    }

    // Месяц со значением больше 12 - А010
    @Test
    void shouldNotPayWithMonthOver12() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getMonthOver12(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.monthErrorVisible();
    }

    // Месяц буквами - А011
    @Test
    void shouldNotPayWithMonthWithLettered() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getMonthWithLettered(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.monthErrorVisible();
    }

    // Месяц символами - А012
    @Test
    void shouldNotPayWithMonthWithSymbols() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getMonthWithSymbols(), getValidYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.monthErrorVisible();
    }

    // Год не заполнен - А013
    @Test
    void shouldNotPayWithoutYear() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), null, getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.yearErrorVisible();
    }

    // Прошедший год - А014
    @Test
    void shouldNotPayWithPastYear() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getPastYear(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.yearErrorVisible();
    }

    // Год +5 лет - А015
    @Test
    void shouldNotBePaidForMoreThan5Years() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getMoreThan5Years(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.yearErrorVisible();
    }

    // Год одной цифрой - А016
    @Test
    void shouldNotPayWithYearWithOneDigit() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getYearWithOneDigit(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.yearErrorVisible();
    }

    // Год буквами - А017
    @Test
    void shouldNotPayWithYearWithLettered() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getYearWithLettered(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.yearErrorVisible();
    }

    // Год символами - А018
    @Test
    void shouldNotPayWithYearWithSymbols() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getYearWithSymbols(), getOwnerName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.yearErrorVisible();
    }

    // Владелец только имя - А019
    @Test
    void shouldNotPayWithFirstName() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerFirstName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    // Владелец на русском языке - А020
    @Test
    void shouldNotPayWithNameInRussian() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerNameInRussia(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    // Владелец с двойным именем - А021
    @Test
    void shouldPayWithDoubleName() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerNameWithDoubleName(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.successfullPayment();
    }

    // Владелец не заполнен - А022
    @Test
    void shouldNotPayWithoutName() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), null, getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    // Владелец символами - А023
    @Test
    void shouldNotPayWithNameWithSymbols() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerNameWithSymbols(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    // Владелец цифрами - А024
    @Test
    void shouldNotPayWithNameWithDigits() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerNameWithDigits(), getCVC());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.ownerErrorVisible();
    }

    // CVC/CVV две цифры - А025
    @Test
    void shouldNotPayWithCVCwithTwoDigit() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVCshort());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cvcErrorVisible();
    }

    // CVC/CVV не заполнен - А026
    @Test
    void shouldNotPayWithoutCVC() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), null);
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cvcErrorVisible();
    }

    // CVC/CVV символами - А027
    @Test
    void shouldNotPayWithCVCwithSymbols() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVCwithSymbols());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cvcErrorVisible();
    }

    // CVC/CVV буквами - А028
    @Test
    void shouldNotPayWithCVCwithLettered() {
        val cardInfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getOwnerName(), getCVCwithLettered());
        val mainPage = new MainPage();
        val paymentPage = mainPage.payByCard();
        paymentPage.fillForm(cardInfo);
        paymentPage.cvcErrorVisible();
    }

    // Незаполненная форма - А029
    @Test
    void shouldNotPayWithoutData() {
        val mainPage = new MainPage();
        mainPage.payByCard();
        val paymentPage = new PaymentPage();
        paymentPage.notFilledForm();
    }
}