# Requests
Application accounting system
Сервис приёма заявок

1. Сделать кнопку "Requests" на главной странице.
2. При нажатии на кнопку "Requests" на главной странице должна открываться страница с таблицей заявок "Waiting List" и кнопка "Requests".
3. При нажатии на кнопку "Requests" должна открываться форма со следующими полями:
- Date (автоматически заполняется текущая дата).
- Originator (выбор из выпадающего списка, есть список).
- Type (выбор из списка).
- Necessary mass, g
- Objective of use.
- Deadline (выпадающий календарь).
- Comments.
- Кнопка "Send request".
Все поля кроме Comments обязательны к исполнению.
4. Таблица Waiting List должна содержать следующие колонки:
- No
- Date
- Status (Generated, In Work, Done, Cancelled, Rejected)
- Originator
- Type
- Necessary mass, g
- Objective of use.
- Deadline (выпадающий календарь).
- Comments.
5. Таблица должна быть отсортирована таким образом, что выше всех должны находиться записи со статусом Generated, далее In Work, затем Done, Cancelled, Rejected.
6. Заявки должны иметь цвет согласно статусу:
Generated - Серый
In Work - Желтый
Done - Зеленый
Cancelled - Оранжевый
Rejected - Красный
7. Залогинившись под администратором вдобавок ко всему вышеизложенному должна появиться кнопка edit на каждой записи таблицы.
8. При нажатии на кнопку Edit должна открываться аналогичная форма с данными, которые там имеются. В Форме должна быть возможность изменить статус заявки.
10. Первая строка должна быть зафиксирована. Остальные строки должны иметь возможность прокручиваться.

----------------------------------
### How to run

1. Idea:

* Run RequestsApplication class as usual
* Build frontend-for-backend running from the root dir: `sh ./infrastructure/rebuild-frontend-for-backend.sh`
* Enjoy at http://localhost:8080

2. As standalone jar

* Build it
```shell
./mvnw clean package -Dmaven.test.skip=true

java -jar ./target/requests-0.0.1-SNAPSHOT.jar
```

* Enjoy at http://localhost:8080

3. With Docker

* Build jar

```shell
./mvnw clean package
```

* Build docker image of application with name "material-requests"
```shell
docker build . --tag=material-requests:1
```

* Build and run docker container with postgresql with name my_postgres
```shell
docker run -d --name my_postgres -v my_dbdata:/var/lib/postgresql/data -p 54320:5432 -e POSTGRES_PASSWORD=my_password postgres:13
```

* Create network with name "network_name"
```shell
docker network create --driver bridge network_name
```

* Run docker container with postgresql in network = "network_name"

```shell
docker run --name my_postgres --network=network_name -e POSTGRES_PASSWORD=my_password -d postgres:13
```

* Run docker image of application material-requests:1 on 8090 port in "network_name" network
```shell
docker run -p 8090:8080 --network=network_name -e SPRING_DATASOURCE_URL=jdbc:postgresql://my_postgres:5432/requests material-requests:1
```
* Enjoy at http://localhost:8090

