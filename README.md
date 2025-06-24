# 🚲 Bike Rental – System Wypożyczalni Rowerów

Projekt **Bike Rental** to system wspierający zarządzanie wypożyczalnią rowerów. Aplikacja została stworzona w języku **Java 11**, z użyciem systemu budowania **Maven** i zawiera środowisko testowe oparte na **JUnit 5**.

## 📦 Struktura i Konfiguracja Maven

Projekt używa standardowego układu Maven i zawiera:

- **groupId**: `pl.wit.bikerental`
- **artifactId**: `bike-rental`
- **version**: `1.0-SNAPSHOT`
- Kompatybilność z Java 11
- Kodowanie znaków: `UTF-8`

## 1. Podstawowe założenia
- - [x] Projekt maven’owy z obsługą minimalnej liczby zależności zewnętrznych.
- - [x] Paczka wynikowa: JAR.
- - [x] Zgodność źródeł oraz klas wynikowych z Java 11.
- - [x] Aplikacja z interfejsem okienkowym SWING, wielowątkowa z obsługą puli wątków.
- - [ ] Obsługa parametrów w wydzielonej klasie: liczba wątków w puli.
- - [x] Kod obłożony testami jednostkowymi dostarczonymi w projekcie.
- - [x] Kod zawiera komentarze do klas, metod i zmiennych składowych.
- - [x] Do projektu dołączony jest wygenerowany poprawny Javadoc zawierający opisy pakietów, klas, metod i zmiennych.

## 2. Opis merytoryczny zadania
Aplikacja okienkowa napisana w SWING do wsparcia obsługi procesu wypożyczania roweru. Program ma umożliwiać:

1. - [x]  Ewidencję typów rowerów (lista) i wprowadzanie nowego typu roweru (nazwa, opis), edycję, usuwanie.
2. - [x]  Ewidencję rowerów (lista) i wprowadzanie nowego roweru (typ, marka, model, rozmiar koła, opis), edycję, usuwanie.
3. - [x]  Ewidencję korzystających z usługi wypożyczenia roweru (lista) i wprowadzanie nowego korzystającego (imię, nazwisko, nr dowodu, opis), edycję, usuwanie.
4. - [x]  Ewidencję i obsługę wypożyczenia (przypisywanie dostępnego roweru do korzystającego w zadanym oknie czasowym — data i czas od i do, ustawienie statusu, uwagi), usuwanie przypisania.
5. - [x]  Obsługę zwrotu roweru z wypożyczenia (wyszukanie wypożyczenia, wprowadzenie uwag, zmiana statusu).
6. - [x]  Raportowanie aktualnie dostępnych rowerów (nie będących na wypożyczeniu).
7. - [x]  Raportowanie niezwróconych rowerów na czas.
8. - [x]  Raportowanie rowerów będących na wypożyczeniu.
9. - [x]  Zapis do pliku całej zawartości wprowadzonych danych z użyciem `DataOutputStream`.
10. - [x]  Odczyt danych z pliku z użyciem `DataInputStream`.
11. - [x]  Wyszukiwanie i wyświetlanie danych o korzystających i rowerach.

Do programu należy dostarczyć testy sprawdzające poprawność działania poszczególnych funkcji programu.

---

### Zakończony projekt przesłany do oceny powinien zawierać:
1. - [x]  Kod źródłowy projektu wraz z testami w strukturze projektu Maven.
2. - [x]  Poprawną dokumentację Javadoc.
3. - [x]  Dokument opisu ról w projekcie realizowanych przez poszczególnych członków zespołu.
