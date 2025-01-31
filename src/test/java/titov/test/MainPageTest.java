package titov.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.inject.Inject;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import titov.prof.extentions.UIExtention;
import titov.prof.pages.MainPage;

@ExtendWith(UIExtention.class)
public class MainPageTest {
  @Inject
  private MainPage mainPage;

  /**
   * Открыть главную страницу https://otus.ru
   * В заголовке страницы открыть меню "Обучение" и выбрать случайную категорию курсов
   * Проверить, что открыт каталог курсов верной категории
   */
  @ParameterizedTest
  @ValueSource(strings = {"Архитектура", "Безопасность", "Тестирование"})
  public void checkCourseCatalogTest(String text) {
    assertTrue(
        mainPage
        .open()
        .isCheckedFinedElement(text), "Элемент не был выбран!");
  }
}
