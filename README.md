##Aplikacja Pogodowa

Aplikacja Pogodowa jest prostą aplikacją mobilną napisaną w języku Kotlin, która umożliwia użytkownikom sprawdzenie bieżącej pogody dla wybranego miasta z wykorzystaniem publicznego interfejsu programistycznego (API) OpenWeatherMap.

##Funkcje

Sprawdzanie pogody: Użytkownicy mogą wprowadzić nazwę miasta i sprawdzić bieżącą pogodę w tym miejscu.
Wyświetlanie szczegółów pogody: Aplikacja wyświetla szczegółowe informacje o temperaturze, wilgotności, prędkości wiatru, wschodzie i zachodzie słońca oraz innych parametrach pogodowych.
Instrukcja użytkowania

Wprowadź nazwę miasta: Wprowadź nazwę miasta w pole tekstowe i naciśnij przycisk "Send", aby sprawdzić pogodę dla tego miasta.
Pobierz wyniki: Aplikacja pobierze bieżące informacje pogodowe za pomocą API OpenWeatherMap.
Wyświetl wyniki: Szczegółowe informacje o pogodzie zostaną wyświetlone na ekranie, obejmując temperaturę, wilgotność, prędkość wiatru itp.
Konfiguracja API OpenWeatherMap

Aby aplikacja działała poprawnie, wymagane jest posiadanie klucza API OpenWeatherMap. Możesz uzyskać klucz API, rejestrując się na stronie OpenWeatherMap.

Po uzyskaniu klucza API, wklej go do zmiennej apiKey w pliku MainActivity.kt:
