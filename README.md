# 📋 Pastebin

Pastebin-клон, написанный на **Spring Boot 3** и **Java 17**, для хранения и обмена текстовыми фрагментами.  
Поддерживает работу с БД, файловым хранилищем и безопасностью.

## 🚀 Возможности
- Создание и хранение текстовых фрагментов
- Поддержка PostgreSQL
- Хранение файлов через MinIO или AWS S3
- Кэширование (Redis)
- JWT-аутентификация и Spring Security
- Валидация данных (Jakarta Validation)
- Централизованная обработка ошибок

## 🛠️ Технологии
- **Java 17**
- **Spring Boot 3.5.4**
  - spring-boot-starter-web
  - spring-boot-starter-data-jpa
  - spring-boot-starter-security
- **PostgreSQL**
- **MinIO / Amazon S3**
- **Redis
- **JWT (jjwt)**
- **Lombok**
- **Maven**

## 📦 Установка и запуск
```bash
git clone https://github.com/nifreebie/pastebin.git
cd pastebin
./mvnw spring-boot:run
