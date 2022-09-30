package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
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
 //           "Selenide, Selenide - это фреймворк для автоматизированного тестирования",
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
