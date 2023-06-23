package ru.netology.delivery.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        DataGenerator.UserInfo user = DataGenerator.Registration.generateUser();
        int daysToAddForFirstMeeting = 4;
        String firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        int daysToAddForSecondMeeting = 7;
        String secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        $("[data-test-id='city'] input").setValue(user.getCity());
        $("[data-test-id='date'] input").setValue(firstMeetingDate);
        $("input[name='name']").setValue(user.getName());
        $("input[name='phone']").setValue(user.getPhone());
        $("input[name='agreement']").parent().click();
        $(".button__text").shouldHave(text("Запланировать")).click();
        $("div[data-test-id='spinner']").should(disappear);
        $("div[data-test-id='success-notification']").shouldBe(visible);
        $("div[data-test-id='success-notification'] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на "));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String updatedSecondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting + 3);
        $("[data-test-id='date'] input").setValue(updatedSecondMeetingDate);
        $(".button__text").shouldHave(text("Запланировать")).click();
        $("div[data-test-id='replan-notification']").shouldBe(visible);
        $("div[data-test-id='replan-notification'] .notification__content")
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id='replan-notification'] button").click();
        $("div[data-test-id='success-notification'] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + updatedSecondMeetingDate));
    }
}