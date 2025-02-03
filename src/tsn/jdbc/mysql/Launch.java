package tsn.jdbc.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class Launch {

    public static void main(String[] args) {
        // РАБОТА С БАЗОЙ ДАННЫХ MYSQL ЧЕРЕЗ JDBC
        // Адрес нашей базы данных "tsn_demo" на локальном компьютере (localhost)
        String url = "jdbc:mysql://localhost:3306/tsn_demo?serverTimezone=Asia/Almaty&useSSL=false";

        // Создание свойств соединения с базой данных
        Properties authorization = new Properties();
        authorization.put("user", "root"); // Зададим имя пользователя БД
        authorization.put("password", "PassW0Rd++"); // Зададим пароль доступа в БД

        // Используем try-with-resources для автоматического закрытия ресурсов
        try (Connection connection = DriverManager.getConnection(url, authorization);
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet table = statement.executeQuery("SELECT * FROM my_books")) {

            // Выводим имена полей
            System.out.println("Имена полей таблицы:");
            table.first(); // Переместимся на первую строку
            for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                System.out.print(table.getMetaData().getColumnName(j) + "\t\t");
            }
            System.out.println();

            // Выводим записи таблицы
            System.out.println("Записи таблицы:");
            table.beforeFirst(); // Переместимся перед первой строкой
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }

        } catch (Exception e) {
            System.err.println("Error accessing database!");
            e.printStackTrace();
        }
    }
}