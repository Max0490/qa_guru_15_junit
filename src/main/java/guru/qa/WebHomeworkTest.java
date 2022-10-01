package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import guru.qa.data.Locale;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class WebHomeworkTest {
    // ТЕСТОВЫЕ ДАННЫЕ
    @ValueSource(strings = {"Компьютер", "Кресло"})
    @ParameterizedTest(name = "Проверка числа результатов поиска на сайте Авито для запроса {0}")
    void yandexSearchCommonTest(String testData) {
        open("https://www.avito.ru/");
        $("#text").setValue(testData);
        $("button[type='submit']").click();
        $$("iva-item-content")
                .shouldHave(CollectionCondition.size(50))
                .first()
                .shouldHave(text(testData));

    }

    @CsvSource(value = {
            "Компьютер, Брал пару месяцев назад, использовал только для работы",
            "Кресло, Компьютерное кресло новое"
    })
    @ParameterizedTest(name = "Проверка числа результатов поиска на сайте Авито для запроса {0}")
    void yandexSearchCommonTestWithDifferentExpectedText(String searchQuery, String expectedText) {
        open("https://ya.ru/");
        $("#text").setValue(searchQuery);
        $("button[type='submit']").click();
        $$("iva-item-content")
                .shouldHave(CollectionCondition.size(50))
                .first()
                .shouldHave(text(expectedText));
    }

    static Stream<Arguments>selenideSiteButtonsText() {
        return Stream.of(
                Arguments.of(Locale.EN, List.of("Home", "Download", "Donation", "My account", "Crew", "Forum", "Database", "Streams", "Wall of Glory")),
                Arguments.of(Locale.RU, List.of("Главная", "Скачать", "Донат", "Мой кабинет", "Команда", "Форум", "База знаний", "Стримы", "Стена Славы",))
        );
    }

    @MethodSource()
    @ParameterizedTest(name = "Проверка отображения названия кнопок для локали: {0}")
    void selenideSiteButtonsText(Locale locale, List<String> buttonsTexts){
        open("https://elmorelab.com/");
        $$("#lang-links a").find(text(locale.name())).click();
        $$(".navbar-nav a").filter(visible)
                .shouldHave(CollectionCondition.texts(buttonsTexts));
    }

    @EnumSource(Locale.class);
    @ParameterizedTest
    void checkLocaleTest(Locale locale){
    open("https://elmorelab.com/");
    $$("#lang-links a").find(text(locale.name())).shouldBe(visible);
    }
}