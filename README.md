### TODO:
- po rejestracji uzytkownik powinien być już zalogowany
- po dodaniu gry może by przenosić na stronę główną(???)
- formularz signup nie informuje, że hasło nie jest takie samo
- ~~model gry (jako TRADE_ITEM)~~
- ~~lista gier~~
- ~~usuń grę~~
- ~~model użytkownika~~
- ~~rejestracja~~
- ~~logowanie~~
- ~~dodawanie gry~~
- definicja etapu - encja TRADE_STATUS z enumem
- administrator (menu widoczne tylko dla administratora)
- zamknięcie listy
- STOP - strona preferencji
- generowanie listy wymian
- rozpoczęcie nowej listy
- STOP - wyszukiwarka na stronie preferencji
- moje gry

__GRA__:
- tytuł
- opis
- url zdjęcia
- BGG ID
- forTrade

__PANEL GRY__:
zdjęcie odsyła do strony bgg

- Lista gier
  * wyświetlanie listy na stronie głównej
    wszystkie gry są wyświetlane na stronie głównej
- panel gry [zdjęcie, tytuł, opis, akcje]
  * akcja - delete - dostępna dla administratora i dla właściciela gry
  * właściciel
  * opis - limit znaków?
  * stan - jako gwiazdki
- pagination
  * po 5/10 gier na początek
- szukaj gry z listy jako formularz do przeszukiwania
- admin menu
  * widoczne tylko dla admina
  * dummy list generuje listę list grup gier
  * trade - robi listę wymian
- rejestracja
  * wypełnij formularz -> mail z linkiem potw. -> zatwierdzenie //może później
- formularz logowania
- formularz rejestracji
  * potwierdzenie hasła dwa razy
  * email
  * username
- link dodaj grę na stronie głównej
- formularz dodawania gry na oddzielnej stronie
  * tytuł wymagany - z bazy bgg
  * podpowiedzi JS
  * opis
- strona "moje gry" z listą moich gier
- admin - zamknij listę
- "jestem zainteresowany"?
- po zamknięciu listy automatyczne generowanie grupy nazwanej
- kliknij = wklej id
- dodaj grę znika po zamknięciu listy
###workflow
__dodaj grę__
login -> kliknij dodaj grę -> wypełnij formularz -> zatwierdź
__usuń grę__
login -> moje gry -> usuń -> ew. zatwierdź
__admin -> zamknij listę__
login -> kliknij zamknij listę
__admin -> generuj listę wymian__
login -> generuj
__wpisywanie propozycji__
login -> lista preferencji -> wypełnij/edytuj -> zatwierdź


### DONE
- lista gier
    - ~~wyciąganie listy gier z bazy danych~~
    - ~~testy wyciągania~~
    - ~~paginacja z wyborem liczby gier na stronę~~
    - ~~testy paginacji~~
    - ~~wyświetlanie listy na stronie~~
    - ~~działanie paginacji na stronie~~
    - ~~test skryptu tworzącego pasek paginacji~~

- usuwanie gry
    - ~~usuwanie z bazy~~
    - ~~testy~~
    - ~~frontend~~
    - ~~popup z prośbą o potwierdzenie~~
    - ~~ajaxowe usuwanie postem~~
    - ~~wyświetlenie statusu usunięcia~~
    - ~~powiązanie gier z użytkownikiem~~
    - ~~TradeItemDTO zamiast TradeItem~~
    - ~~możliwość ich usunięcia tylko przez właściciela~~
    - ~~walidacja usuwania w sevice~~
    - ~~completne testy usuwania~~