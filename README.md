# homework-4 SELENOID
Автотест каталога курсов [https://otus.ru/catalog/courses](https://otus.ru/catalog/courses) со своими ожиданиями
## Инструкция по запуску:
1. Перейдите в корень проекта.
2. В терминале выполните команду:<br>
    mvn clean test
3. Для удаленного запуска в Selenoid у вас должен быть установлен Selenoid. Команда запуска тестов: 
   <br> mvn cleat test -Dremote.url="http://ip your Selenoid::4444/wd/hub" 
4. Enjoy!<br>
**PS.** У вас должен быть локально установлен Maven и JDK 17.<br>