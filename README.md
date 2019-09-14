# Project

## Wymagania

1. Użytkownik powinien móc dodawać zadanie z opisem i planowaną datą wykonania.
2. Użytkownik powinien móc ustatić status zadania:
   * rozpoczęte
   * w trakcie
   * zakończone
3. Użytkownik powinien mieć możliwość ustawienia zadania jako anulowane.
4. Użytkownik powienin mieć możliwość ustawienia kategorii zadania.
5. Użytkownik powinien mieć możliwość dodawania/usuwania kategorii. Kategoria, która
   jest przypisana do zadania powinna być niemożliwa do usunięcia.
   Nazwy kategorii powinny być unikatowe.
   
## Wskazówki

1. Jeżeli implementujesz warstwę trwałości przy pomocy Hibernate:

   1. Stwórz skrypt tworzący bazę danych i użytkownika, z którego będziesz korzystać.
   2. Stwórz plik `persistence.xml` oraz dodaj adnotacje dla klas `Task` oraz `Category`.
   3. Stwórz implementacje interfejsów `TaskRepository` oraz `CategoryRepository` 
      korzystające z *Hibernate* oraz przekaż je do `Application` zamiast `MemoryTaskRepository` oraz
      `MemoryCategoryRepository`.
   4. Stwórz schemat bazy danych. Możesz skorzystać z opcji *create-drop* aby stworzyć *DDL*.
   5. Pamiętaj, że z reguły aplikacja powinna mieć tylko jedną instancję `EntityManagerFactory`.
      Zastanów się czy przekażesz ją do repozytoriów, czy moze już stworzone instacje `EntityManager`?
      
2. Jeżeli implementujesz warstwę trwałości przy pomocy JDBC:

   1. Stwórz skrypt tworzący bazę danych i użytkownika, z którego będziesz korzystać.
   2. Rozważ użycie JdbcTemplate.
   3. Stwórz implementacje interfejsów `TaskRepository` oraz `CategoryRepository` 
      korzystające z *JDBC* oraz przekaż je do `Application` zamiast `MemoryTaskRepository` oraz
     `MemoryCategoryRepository`.
   4. Stwórz schemat bazy danych. Możesz skorzystać z opcji *create-drop* aby stworzyć *DDL*. 
   5. Zastanów się w jaki sposób przekażesz dane o połączeniu do repozytoriów?
      Rozważ użycie interfejsu `Datasource`, na przykład klasy `MysqlDataSource`.

3. Rozpocznij implementację od warstwy trwałości dla kategorii, następnie przejdź do zadań.
4. Zacznij od implementacji metod takich jak `getAll`, które tylko pobierają dane, następnie
   zaimplementuj metody dokonujące zmian na bazie danych.
5. Skonfiguruj połączenie z bazą danych, by móc łatwo sprawdzić stan bazy.
