#Project

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
   
## Wsazówki

1. Zacznij od implementacji metod takich jak `getAll`, które tylko pobierają dane.
2. Rozpocznij implementację od widoku kategorii.

3. Jeżeli implementujesz warstwę trwałości przy pomocy Hibernate:

   1. Stwórz plik `persitance.xml`