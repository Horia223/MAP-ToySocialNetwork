# Toy Social Network

Aplicație Java în linie de comandă pentru gestionarea utilizatorilor și a prieteniilor dintre ei. Programul poate stoca datele în memorie, în fișiere text sau într-o bază de date PostgreSQL.

## Funcționalități

- Adăugarea și ștergerea utilizatorilor.
- Crearea și ștergerea prieteniilor.
- Afișarea utilizatorilor și a prieteniilor.
- Afișarea prietenilor unui utilizator.
- Numărarea comunităților din rețea.
- Găsirea comunității cu diametrul cel mai mare.

O prietenie este nedirecționată: relația dintre utilizatorii 3 și 8 este aceeași cu relația dintre 8 și 3.

## Organizarea programului

- **`domain`** — obiectele aplicației: `User`, `Friendship`, `Tuple` și `Entity`.
- **`repository`** — salvarea și accesarea datelor, prin implementări pentru memorie, fișiere și PostgreSQL.
- **`service`** — regulile aplicației, căutarea prietenilor și calculul comunităților.
- **`validation`** — verificarea utilizatorilor și prieteniilor.
- **`ui`** — meniul interactiv din consolă.
- **`Run.java`** — citește configurația și pornește aplicația.

## Concepte aplicate

Proiectul folosește programare orientată pe obiecte, generice și interfețe. Service-ul lucrează cu interfața `Repository`, nu cu o metodă concretă de stocare. `RepositoryFactory` alege implementarea în funcție de configurație.

Validările folosesc interfețe funcționale și lambda-uri. Stream-urile ajută la filtrarea și transformarea colecțiilor, iar `HashMap` și `HashSet` sunt folosite pentru acces rapid și pentru reprezentarea rețelei.

`NetworkGraph` folosește căutarea în adâncime (DFS) pentru comunități și căutarea în lățime (BFS) pentru distanțele dintre utilizatori. Comunitatea „cea mai sociabilă” este cea cu diametrul cel mai mare.

Pentru PostgreSQL, repository-urile folosesc JDBC, `PreparedStatement` și `try-with-resources`. Gradle gestionează driverul PostgreSQL JDBC.

## Moduri de stocare

Setarea `repository.type` din `src/resources/config.properties` selectează modul:

- `MEMORY` — datele dispar când programul se închide.
- `FILE` — datele sunt păstrate în `data/utilizatori.txt` și `data/prietenii.txt`.
- `DATABASE` — datele sunt păstrate în PostgreSQL. ID-ul utilizatorului este generat de baza de date.

Copiază `src/resources/config.example.properties` în `src/resources/config.properties` și completează datele locale de conectare. Fișierul `config.properties` este exclus din Git; nu publica parola bazei de date.

Repository-ul pentru prietenii folosește în prezent coloana `friends_since`. Schema bazei de date trebuie să folosească același nume de coloană ca interogările din cod.

## Construire și pornire

Este necesar un JDK 17 sau mai nou. Din rădăcina proiectului, construiește aplicația pe Windows cu:

```powershell
.\gradlew.bat build
```

Pentru a porni meniul, rulează clasa `Run` din IntelliJ IDEA. Pentru modul `DATABASE`, pornește PostgreSQL, creează baza și tabelele necesare și completează `config.properties`.
