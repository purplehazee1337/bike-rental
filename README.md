# 🚲 Bike Rental – System Wypożyczalni Rowerów

Projekt **Bike Rental** to system wspierający zarządzanie wypożyczalnią rowerów. Aplikacja została stworzona w języku **Java 11**, z użyciem systemu budowania **Maven** i zawiera środowisko testowe oparte na **JUnit 5**.

## 📦 Struktura i Konfiguracja Maven

Projekt używa standardowego układu Maven i zawiera:

- **groupId**: `pl.wit.bikerental`
- **artifactId**: `bike-rental`
- **version**: `1.0-SNAPSHOT`
- Kompatybilność z Java 11
- Kodowanie znaków: `UTF-8`

## ✅ Budowanie projektu

Aby zbudować projekt Mavenowy, wykonaj następujące kroki:

1. Przejdź do katalogu projektu, w którym znajduje się plik `pom.xml`, np. `cd /ścieżka/do/projektu`.
2. Uruchom w terminalu polecenie:  
   `mvn clean install`  
   - `clean` — usuwa poprzednie wyniki kompilacji,  
   - `install` — kompiluje projekt, uruchamia testy i instaluje artefakt do lokalnego repozytorium Maven.
3. Po pomyślnym zbudowaniu, plik wynikowy (np. JAR lub WAR) znajdziesz w katalogu `target`.

Aby wygenerować dokumentacje, wykonaj następujące kroki:
1. Przejdź do katalogu projektu, w którym znajduje się plik `pom.xml`, np. `cd /ścieżka/do/projektu`.
2. Uruchom w terminalu polecenie:
   `mvn javadoc:javadoc`
3. Po pomyślnym zbudowaniu, plik do uruchomienia dokumentacji znajdziesz w katalogu `target/site/apidocs/index.html`.

Przydatne polecenia Maven:  
- `mvn compile` — kompiluje kod źródłowy,  
- `mvn test` — uruchamia testy jednostkowe,  
- `mvn package` — tworzy pakiet bez instalacji w lokalnym repozytorium.

### 📁 Zależności

- **JUnit Jupiter (5.8.0)** – biblioteka testowa wykorzystywana w testach jednostkowych.  
- **Log4j API (2.23.0)** – interfejs API dla logowania.  
- **Log4j Core (2.23.0)** – implementacja silnika logowania Log4j.  


## 1. Podstawowe założenia
- - [x] Projekt maven’owy z obsługą minimalnej liczby zależności zewnętrznych.
- - [x] Paczka wynikowa: JAR.
- - [x] Zgodność źródeł oraz klas wynikowych z Java 11.
- - [ ] Aplikacja z interfejsem okienkowym SWING, wielowątkowa z obsługą puli wątków.
- - [ ] Obsługa parametrów w wydzielonej klasie: liczba wątków w puli.
- - [ ] Kod obłożony testami jednostkowymi dostarczonymi w projekcie.
- - [ ] Kod zawiera komentarze do klas, metod i zmiennych składowych.
- - [x] Do projektu dołączony jest wygenerowany poprawny Javadoc zawierający opisy pakietów, klas, metod i zmiennych.

## 2. Opis merytoryczny zadania
Aplikacja okienkowa napisana w SWING do wsparcia obsługi procesu wypożyczania roweru. Program ma umożliwiać:

1. - [ ]  Ewidencję typów rowerów (lista) i wprowadzanie nowego typu roweru (nazwa, opis), edycję, usuwanie.
2. - [ ]  Ewidencję rowerów (lista) i wprowadzanie nowego roweru (typ, marka, model, rozmiar koła, opis), edycję, usuwanie.
3. - [ ]  Ewidencję korzystających z usługi wypożyczenia roweru (lista) i wprowadzanie nowego korzystającego (imię, nazwisko, nr dowodu, opis), edycję, usuwanie.
4. - [ ]  Ewidencję i obsługę wypożyczenia (przypisywanie dostępnego roweru do korzystającego w zadanym oknie czasowym — data i czas od i do, ustawienie statusu, uwagi), usuwanie przypisania.
5. - [ ]  Obsługę zwrotu roweru z wypożyczenia (wyszukanie wypożyczenia, wprowadzenie uwag, zmiana statusu).
6. - [ ]  Raportowanie aktualnie dostępnych rowerów (nie będących na wypożyczeniu).
7. - [ ]  Raportowanie niezwróconych rowerów na czas.
8. - [ ]  Raportowanie rowerów będących na wypożyczeniu.
9. - [ ]  Zapis do pliku całej zawartości wprowadzonych danych z użyciem `DataOutputStream`.
10. - [ ]  Odczyt danych z pliku z użyciem `DataInputStream`.
11. - [ ]  Wyszukiwanie i wyświetlanie danych o korzystających i rowerach.

Do programu należy dostarczyć testy sprawdzające poprawność działania poszczególnych funkcji programu.

---

### Zakończony projekt przesłany do oceny powinien zawierać:
1. - [ ]  Kod źródłowy projektu wraz z testami w strukturze projektu Maven.
2. - [x]  Poprawną dokumentację Javadoc.
3. - [ ]  Dokument opisu ról w projekcie realizowanych przez poszczególnych członków zespołu.
