## Что означают приглашения psql

### Оперативные команды

``` sql
-- отобразить все таблицы
\dt; 
-- Посмотреть структуру таблицы (узнать названия колонок)
\d users
```

``` sql
-- отобразить все таблицы
select * from users;  
```

``` sql
-- выйти из chatdb
\q;  
```

### Состояние

| Приглашение	 | Значение                                                             |
|--------------|----------------------------------------------------------------------|
| chatdb=#	    | Обычная команда, можно вводить SQL или \-команды                     |
| chatdb-#	    | Команда не завершена (нет точки с запятой или синтаксическая ошибка) |
| chatdb->	    | Многострочный запрос                                                 |

Чтобы выйти из режима ожидания (chatdb-#), нажмите Ctrl+C.

## Универсальный способ из папки где лежит [docker-compose.yml](docker-compose.yml)

``` bash
docker compose exec postgres psql -U postgres -d chatdb
```

**Разница:**

- `docker exec` — работает с конкретным контейнером (нужно знать имя)
- `docker compose exec` — работает с сервисом из Compose-файла (имя знает сам)

---

## Как найти правильное имя контейнера

### Способ 1: Посмотреть запущенные контейнеры

```bash
docker ps
```

Вы увидите что-то вроде:

```
CONTAINER ID   IMAGE          COMMAND                  NAMES
abc123def456   postgres:16    "docker-entrypoint.s…"   myproject_postgres_1
def456ghi789   confluentinc…  "/etc/confluent/dock…"   myproject_zookeeper_1
```

Имя контейнера PostgreSQL — **третий столбец** (например, `myproject_postgres_1`).

### Способ 2: Посмотреть все контейнеры (включая остановленные)

```bash
docker ps -a
```

### Способ 3: Спросить Docker Compose

```bash
docker compose ps
```

```
NAME                     IMAGE          COMMAND                  SERVICE    STATUS
myproject_postgres_1     postgres:16    "docker-entrypoint.s…"   postgres   running
myproject_zookeeper_1    confluentinc…  "/etc/confluent/dock…"   zookeeper  running
myproject_kafka_1        confluentinc…  "/etc/confluent/dock…"   kafka      running
```

---

## Правильная команда подключения

Допустим, имя контейнера `myproject_postgres_1`:

```bash
docker exec -it myproject_postgres_1 psql -U postgres -d chatdb
```

Где:

- `-it` — интерактивный режим + псевдо-TTY
- `psql` — команда внутри контейнера
- `-U postgres` — пользователь БД
- `-d chatdb` — база данных

---

## Что делать, если контейнер не запущен

```bash
# Проверить статус
docker compose ps

# Если статус не "running" — запустить
docker compose up -d

# Или перезапустить всё
docker compose down
docker compose up -d
```

---

## Живой пример (прямо сейчас)

Выполните эти команды и покажите вывод:

```bash
# 1. Посмотреть, что вообще запущено
docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Image}}"

# 2. Если не работает — попробуйте через Compose
docker compose ps
```