import Tags.Critical;
import Tags.RegistrationPage;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class TestExapleWithDataProvider {

    static Stream<Arguments> repositoryInProfileIsCorrect() {
        return Stream.of(
                Arguments.of("https://github.com/Kopibarius", List.of("qa_guru_HW2", "qa_guru_HW3", "qa_guru_HW3.3", "qa_guru_HW4", "qa_guru_HW5", "qa_guru_HW6")),
                Arguments.of("https://github.com/TellSamm", List.of("rApi-tellsamm", "PochtaPrimerEZP2", "Diplom-1", "Diplom_2", "Diplom_3", "TellSamm"))
        );
    }

    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
    }

    @Test
    @Disabled("Просто что бы был")
    @DisplayName("Ввод разных типов значений в поле ввода логина")
    void setDifferentTypeDataInLoginFild() {
        open("https://mail.google.com/");
        $("#identifierId").setValue("Привет");
        $("#identifierId").shouldHave(attributeMatching("data-initial-value", "Привет"));
    }

    @ValueSource(strings = {
            "Привет", "Hello", "12345"
    })
    @ParameterizedTest(name = "Ввод {0} в поле ввода логина")
    @DisplayName("Ввод разных типов значений в поле ввода логона")
    @RegistrationPage
    @Critical
    void setDifferentTypeDataInLoginFild2(String testData) {
        open("https://mail.google.com/");
        $("#identifierId").setValue(testData);
        $("#identifierId").shouldHave(attributeMatching("data-initial-value", testData));
    }

    @CsvSource(value = {
            "https://github.com/Kopibarius          | Egor Melnichyk",
            "https://github.com/TellSamm            | Samm"
    },
            delimiter = '|'
    )
    @ParameterizedTest(name = "Отображение имени {1} в профиле по ссылки {0}")

    @RegistrationPage
    @Critical
    void nameInProfileIsCorrect(String profile, String name) {
        open(profile);
        $("[itemprop='name']").shouldHave(text(name));
    }

    @MethodSource
    @ParameterizedTest(name = "Отображение в профиле по ссылки {0} репозиториев {1}")
    @RegistrationPage
    @Critical
    void repositoryInProfileIsCorrect(String profile, List<String> repository) {
        open(profile);
        actions().moveToElement($(byText("Contribution activity"))).build().perform();
        $$(".repo").filter(visible).shouldHave(texts(repository));
    }
}
