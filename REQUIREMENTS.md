##To do
1. REST API.
   1. bean validation v2
   2. exception handling v4
   3. pagination v3
   4. sorting
   5. filtering
2. Переезд с H2.
   1. Приложение стартует и коннектится к локальной PostgreSQL
   2. Приложение имеет компонентные тесты в которых коннектится к PostgreSQL test-containers
3. Контейнеризация.
4. Pipeline. Деплой на сервер.
5. Опытно-промышленная эксплуатация.
6. Сообщения по заявкам?

# Общие технические требования
1. Заявки могут создавать только люди, которые существуют только в БД, если запрос с неизвестной фамилией, то возвращаем 400.
2. Nullability. 
2.1 В базу данных некоторые поля могут сохраняться как NULL.
2.2. Для полей, которые не могут быть NULL возвращаем ошибку 400, если NULL.
3. Заявки могут создаваться только типов, которые существуют только в БД, если запрос с неизвестным типом, то возвращаем 400.

## V2 требования

*Bean validation*

1. Для полей, которые не могут быть NULL возвращаем ошибку 400, если NULL. Должно быть реализовано с использованием
[bean validation](https://reflectoring.io/bean-validation-with-spring-boot/) то есть запрос не должен дойти до контроллера, а упасть с ошибкой раньше
2. База данных не должна допускать сохранения в столбцы, которые не могут быть null значение типа null. См. https://www.baeldung.com/hibernate-notnull-vs-nullable
Это уровень базы данных, то есть отличная имплементация от пункта 1, можно проверить написанием теста на репозиторий, используя аннотацию `@DataJpaTest` над классом теста

## V3 требования

*Pagination*

1. На endpoint'е `/requests` сервис должен возвращать N сущностей разбитых на страницы. Смотри https://www.bezkoder.com/spring-boot-pagination-filter-jpa-pageable/
```shell
GET http://localhost:8080/requests?page=1&size=5
```
Возвращает
```shell
{
  "totalElements": 10,
  "totalPages:" 2,
  "currentPage": 1,
  "requests": [
     {
       ... request1 ...
     },
     {
       ... request1 ...
     }
  ]
}
```

## V4 требования. Exception handling + sorting

1. Exception handling, смотри `@ControllerAdvise`
* В случае ошибки сервис должен возвращать подходящий HTTP статус код, отличный от 200, или 500 по-умолчанию
* Тело ответа с ошибкой должно выглядит следующим образом:
```json
{
   "error": {
      "type": "NotFoundException", // имя класса сокращенное
      "detailedMessage": "Сущность не найдена", // #exception.getMessage()
      "status": 404
   }
}
```

2. Sorting, смотри https://www.bezkoder.com/spring-boot-pagination-sorting-example/
* Эндпоинт имеет возможность возвращать сортированный результат по полям: status, date и deadline. По-умолчанию (без переданных query параметров), он всегда сортирует по статусу
```shell
# пример контракта
GET http://localhost:8080/requests?sort=status,asc&sort=date,desc
```
* если `sort=status` не передан, но другие sort переданы, то сортировка только по тем полям, что запрашивает клиент
* в случае если sort передан следующим образом `sort=status`, то неявно добавляется `asc` или `desc` для всех запросов. Не важно какой, главное чтобы один и тотже
* в случае если sort передан следующим образом `sort=unexpected` с неожиданным значением, то эндпоинт возвращает 400 bad request
* эндпоинт ожидает N query параметроов sort


## V5 требования. PostgreSQL migration

1. Приложение коннектиться к локальной PostgreSQL при старте вместо h2 базы данных
* Репозиторий содержит инструкции: как развернуть postgresql
* В тестах приложение подключается к реальной базе данных PostgreSQL, которая стартует в контейнере, смотри https://github.com/Playtika/testcontainers-spring-boot
