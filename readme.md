# Подготовка среды

## Docker

- Скачать здесь [docker.com/products/docker-desktop/](https://www.docker.com/products/docker-desktop/)
- Проверить работоспособность

```bash
docker --version
docker compose version
```

- Из каталога с файлом [docker-compose.yml](docker-compose.yml) поднять контейнеры с Postgres, Zookeeper, Kafka.

```bash
docker desktop start
docker info
docker compose up -d
```

## Запуск

```bash
mvn clean install
mvn spring-boot:run -pl server
```

``` shell 
mvn clean install
mvn spring-boot:run -pl server
```

## Команды оперативные под руку

```
docker desktop start # запустит докер как услугу
docker desktop stop # потушит докер как услугу
docker desktop status # Проверить статус (работает или нет)
docker desktop restart 
docker system df # статистику по докер как услуге если запущен
docker info # покажет ошибку если докер не запущен
docker context show # покажет ошибку если докер не запущен
docker compose up -d # поднимет контейнеры если запущена рядом с докер-файлом
docker compose down # потушит контейнеры
docker compose down -v # потушит контейнеры
docker system prune -a --volumes # потушит контейнеры
docker rmi -f $(docker images -q) # удалит образы контейнеров с диска
docker rmi -f # # удалит образы контейнеров с диска
docker compose exec postgres psql -U postgres -d chatdb - подключиться к БД из консоли и позволит выполнить SQL запросы
\dt; # покажет все таблицы - что то типа TABLES SHOW
select * from users; 
\q; # выйдет из консольного режима SQL
```

## Команды чистки докер образов

| Команда	                           | Что удаляет                              |
|------------------------------------|------------------------------------------|
| docker system df                   | Посмотреть сколько места занимают образы |
| docker image prune                 | Висячие образы (без имени)               |
| docker image prune -a	             | Все неиспользуемые образы                |
| docker rmi $(docker images -q)	    | Все образы (если не используются)        |
| docker rmi -f $(docker images -q)	 | Все образы (принудительно)               |
| docker system prune -a	            | Образы + контейнеры + сети               |
| docker system prune -a --volumes	  | Всё, включая тома с данными              |
| docker compose down -v             | чтобы начать с чистой БД                 |

# Заметки

1. class="col-xl-4 col-lg-3 col-md-3 col-sm-5 col-xs iButton
2.     <!--https://stackoverflow.com/questions/40773248/how-to-change-pages-based-on-url
       https://stackoverflow.com/questions/10816073/how-to-do-paging-in-angularjs&ndash;&gt;
       &lt;<pagination
       ng-model="currentPage"
       total-items="todos.length"
       max-size="maxSize"
       boundary-links="true">
       </pagination>-->
3. heroku ps:scale web=1 - включить ПО на хероку
4. <div ng-include="'invoice/invoiceEdit.html'"></div>
5. sessionStorage.setItem("userID", response.data.userId);
   sessionStorage.setItem("userInfo", JSON.stringify(response.data));
   return JSON.parse(sessionStorage.getItem("userInfo"));
6.     {
           "id": 1554,
           "datacreate": "2020-11-20T11:03:45.065+00:00",
           "department": "1",
           "comment": "test",
           "ordernumber": "WL-REQ-111111111",
           "invoicenumber": null,
           "senttoapprove": null,
           "senttopurchase": null,
           "senttoprice": null,
           "totalprice": null,
           "resolveddate": null,
           "customer": {
               "id": 1
           },
           "purchases": [
               {
                   "id": 3827,
                   "nomenclature": {
                       "id": 444,
                       "comment": "test",
                       "price": 100,
                       "submitDate": "2020-06-01T00:00:00.000+00:00",
                       "nomenclature": "HP 24ea 23.8",
                       "manufacturer": "HP",
                       "code": "00000275978",
                       "expiredDate": null
                   },
                   "count": 2,
                   "approver": null,
                   "resolvingdate": null,
                   "comment": "test",
                   "buyingPrice": 100,
                   "commentnumenclature": null
               }
           ],
           "histories": [
               {
                   "id": 2,
                   "submitdate": "2020-11-24T00:00:00.000+00:00",
                   "customer": {
                       "id": 17
                   },
                   "step": {
                       "id": 2
                   },
                   "stepcomment": [
                       {
                           "id": 11,
                           "customer": {
                               "id": 17
                           },
                           "comment": "test",
                           "attachedfileid": null,
                           "submitdate": "2020-11-24T10:00:00.000+00:00"
                       }
                   ]
               }
           ]
       }

____
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>
не загрузились почему то
пока так
<script src="/js/sockjs.min.js"></script>
<script src="/js/stomp.min.js"></script>