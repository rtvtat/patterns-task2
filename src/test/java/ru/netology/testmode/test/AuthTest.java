package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        // TODO: добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
        //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
        //  пользователя registeredUser
        $("span[data-test-id='login'] .input__control").setValue(registeredUser.getLogin());
        $("span[data-test-id='password'] .input__control").setValue(registeredUser.getPassword());
        $("button[data-test-id='action-login']").click();
        $x("//div[contains(@id,'root')]")
                .shouldHave(Condition.text("Личный кабинет"), Duration.ofSeconds(5))
                .shouldBe(Condition.visible);
//        $x("//div[contains(@class, 'notification') and @data-test-id='error-notification']//div[contains(@class, 'notification__content')]")
//                .shouldHave(Condition.text("Неверно указан логин или пароль"))
//                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
        //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
        $("span[data-test-id='login'] .input__control").setValue(notRegisteredUser.getLogin());
        $("span[data-test-id='password'] .input__control").setValue(notRegisteredUser.getPassword());
        $("button[data-test-id='action-login']").click();
        $x("//div[contains(@class, 'notification') and @data-test-id='error-notification']//div[contains(@class, 'notification__content')]")
                .shouldHave(Condition.text("Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
        //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
        $("span[data-test-id='login'] .input__control").setValue(blockedUser.getLogin());
        $("span[data-test-id='password'] .input__control").setValue(blockedUser.getPassword());
        $("button[data-test-id='action-login']").click();
        $x("//div[contains(@class, 'notification') and @data-test-id='error-notification']//div[contains(@class, 'notification__content')]")
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
        //  "Пароль" - пользователя registeredUser
        $("span[data-test-id='login'] .input__control").setValue(wrongLogin);
        $("span[data-test-id='password'] .input__control").setValue(registeredUser.getPassword());
        $("button[data-test-id='action-login']").click();
        $x("//div[contains(@class, 'notification') and @data-test-id='error-notification']//div[contains(@class, 'notification__content')]")
                .shouldHave(Condition.text("Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
        //  "Пароль" - переменную wrongPassword
        $("span[data-test-id='login'] .input__control").setValue(registeredUser.getLogin());
        $("span[data-test-id='password'] .input__control").setValue(wrongPassword);
        $("button[data-test-id='action-login']").click();
        $x("//div[contains(@class, 'notification') and @data-test-id='error-notification']//div[contains(@class, 'notification__content')]")
                .shouldHave(Condition.text("Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }
}
