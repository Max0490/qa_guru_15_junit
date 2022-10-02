package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import guru.qa.data.Locale;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class WebTest {
    // ТЕСТОВЫЕ ДАННЫЕ
    @ValueSource(strings = {"Selenide", "JUnit"})
    @ParameterizedTest(name = "Проверка числа результатов поиска в Яндексе для запроса {0}")
    void yandexSearchCommonTest(String testData) {
        open("https://ya.ru/");
        $("#text").setValue(testData);
        $("button[type='submit']").click();
        $$("li.serp-item")
                .shouldHave(CollectionCondition.size(10))
                .first()
                .shouldHave(text(testData));

    }

    @CsvSource(value = {
            "Selenide, Selenide - это фреймворк для автоматизированного тестирования",
            "JUnit, A programmer-oriented testing framework for Java"
    })
    @ParameterizedTest(name = "Проверка числа результатов поиска в Яндексе для запроса {0}")
    void yandexSearchCommonTestWithDifferentExpectedText(String searchQuery, String expectedText) {
        open("https://ya.ru/");
        $("#text").setValue(searchQuery);
        $("button[type='submit']").click();
        $$("li.serp-item")
                .shouldHave(CollectionCondition.size(10))
                .first()
                .shouldHave(text(expectedText));
    }

    static Stream<Arguments>selenideSiteButtonsText() {
        return Stream.of(
                Arguments.of(Locale.EN, List.of("Quick start", "Docs", "FAQ", "Blog", "Javadoc", "Users", "Quotes")),
                Arguments.of(Locale.RU, List.of("С чего начать?", "Док", "ЧАВО", "Блог", "Javadoc", "Пользователи", "Отзывы"))
        );
    }

    @MethodSource()
    @ParameterizedTest(name = "Проверка отображения названия кнопок для локали: {0}")
    void selenideSiteButtonsText(Locale locale, List<String> buttonsTexts){
        open("https://selenide.org/");
        $$("#languages a").find(text(locale.name())).click();
        $$(".main-menu-pages a").filter(visible)
                .shouldHave(CollectionCondition.texts(buttonsTexts));
    }

    @EnumSource(Locale locale);
    @ParameterizedTest
    void checkLocaleTest(Locale locale){
    open("https://selenide.org/");
    $$("#languages a").find(text(locale.name())).shouldBe(visible);
    }
}