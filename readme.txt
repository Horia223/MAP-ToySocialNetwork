# MAP Toy Social Network

Aplicație Java în linie de comandă pentru administrarea unei rețele sociale. Programul gestionează utilizatori și prieteniile dintre ei, validează datele și poate calcula comunitățile din rețea.

## Ce poate face programul

Din meniu poți:

1. adăuga un utilizator;
2. șterge un utilizator;
3. adăuga o prietenie între doi utilizatori;
4. șterge o prietenie;
5. afișa utilizatorii;
6. afișa prieteniile;
7. număra comunitățile;
8. afișa comunitatea cu diametrul cel mai mare;
9. afișa prietenii unui utilizator;
0. închide aplicația.

Utilizatorii sunt afișați în ordine alfabetică după numele de familie și prenume. Prieteniile sunt afișate de la cea mai recentă la cea mai veche.

## Cum este organizat proiectul

Programul separă responsabilitățile în mai multe pachete:

- `domain` conține obiectele principale: `User`, `Friendship`, `Tuple` și clasa de bază `Entity`.
- `repository` se ocupă de salvarea, căutarea, modificarea și ștergerea datelor.
- `validation` verifică utilizatorii și prieteniile înainte de salvare sau modificare.
- `service` conține logica rețelei sociale și calculele despre comunități.
- `ui` afișează meniul și preia comenzile de la utilizator.
- `exception` conține excepțiile folosite pentru erori de validare, stocare și căutare.
- `Run.java` pornește aplicația, citește configurația și construiește componentele necesare.

Fluxul general este:

```text
Run → RepositoryFactory → repository-uri → NetworkService → Main
```

`Main` primește repository-urile prin interfața `Repository`. Astfel, meniul și service-ul pot folosi aceeași interfață indiferent dacă datele sunt stocate în memorie, în fișiere sau în PostgreSQL.

## Domeniul aplicației

### Utilizatorii

Un `User` are un ID, un prenume și un nume de familie. Toate obiectele de domeniu moștenesc din `Entity<ID>`, care păstrează ID-ul și definește egalitatea între entități pe baza ID-ului.

`UserFactory` creează utilizatori și calculează un ID inițial mai mare decât ID-urile deja cunoscute. În modul `DATABASE`, `UserDbRepository` inserează numele fără ID, apoi folosește ID-ul generat de PostgreSQL și întoarce un obiect `User` cu acel ID.

### Prieteniile

O `Friendship` reprezintă o relație nedirecționată între doi utilizatori și păstrează data creării. Perechea ID-urilor este normalizată în ordine crescătoare. Astfel, prietenia dintre utilizatorii 3 și 8 este aceeași indiferent dacă este introdusă ca `(3, 8)` sau `(8, 3)`.

`Tuple<A, B>` ține împreună două valori și definește `equals` și `hashCode`; este folosită drept ID pentru prietenie.

## Validarea și căutarea

`UserDataValidator` verifică dacă utilizatorul există, are ID pozitiv și prenume și nume completate, care încep cu literă mare. În `Run.java`, acest validator este compus cu o regulă suplimentară care interzice cifrele în nume.

`FriendshipValidator` verifică ID-urile utilizatorilor și data prieteniei. Constructorul `Friendship` interzice prietenia unui utilizator cu el însuși.

`Validator<T>` este o interfață funcțională. Metoda `andThen` permite compunerea mai multor validatoare: toate regulile trebuie să treacă.

`Repository.findAll(Predicate<E>)` filtrează entitățile după o condiție. `UserCriteria` oferă condiții reutilizabile pentru căutarea utilizatorilor după prefixul prenumelui sau al numelui. `Run.java` conține un exemplu care caută prenume care încep cu `D` și nume care încep cu `T`.

## Repository-uri și stocare

Interfața `Repository<ID, E>` definește operațiile comune: `save`, `delete`, `findOne`, `update` și `getAll`. `findOne` întoarce un `Optional`, care reprezintă fie o entitate găsită, fie un rezultat gol.

Sunt disponibile trei moduri:

- `MEMORY`: `InMemoryRepository` păstrează entitățile într-un `HashMap`. Datele dispar când programul se închide.
- `FILE`: `UserFileRepository` și `FriendshipFileRepository` folosesc fișiere text. `AbstractFileRepository` conține logica comună de citire și scriere.
- `DATABASE`: `UserDbRepository` și `FriendshipDbRepository` folosesc JDBC pentru a comunica cu PostgreSQL.

În modul `FILE`, utilizatorii au formatul:

```text
id;prenume;nume
```

Prieteniile au formatul:

```text
idUtilizator1;idUtilizator2;data
```

La pornire, liniile invalide sunt raportate și ignorate. La salvare, modificare sau ștergere, repository-ul actualizează fișierul.

În modul `DATABASE`, repository-urile folosesc `Connection`, `PreparedStatement` și `ResultSet`. Valorile sunt trimise prin parametri `?`, iar conexiunile și instrucțiunile sunt închise automat cu `try-with-resources`. Erorile SQL sunt împachetate în `RepositoryException`, păstrând cauza inițială.

## Rețeaua și comunitățile

`NetworkService` coordonează repository-urile și aplică regulile aplicației. Înainte să creeze o prietenie, verifică dacă ambii utilizatori există. La ștergerea unui utilizator, service-ul șterge mai întâi prieteniile asociate și apoi utilizatorul.

`NetworkGraph` construiește o listă de adiacență: pentru fiecare utilizator păstrează setul de vecini. Prieteniile sunt nedirecționate, deci fiecare relație este adăugată în ambele sensuri.

- `countCommunities()` numără componentele conexe. Un utilizator fără prieteni formează propria comunitate.
- `mostSociableCommunity()` alege comunitatea cu diametrul cel mai mare. Diametrul este cea mai mare distanță minimă dintre doi utilizatori ai acelei comunități.
- Căutarea în adâncime (DFS) găsește comunitățile.
- Căutarea în lățime (BFS) calculează distanțele dintre utilizatori.

## Configurare

`Run.java` citește:

```text
src/resources/config.properties
```

Fișierul `config.example.properties` este modelul fără parola personală. Copiază-l ca `config.properties`, apoi completează datele locale:

```properties
repository.type=DATABASE
db.url=jdbc:postgresql://localhost:5432/socialnetwork
db.user=utilizatorul_tău
db.password=parola_ta
```

`repository.type` poate fi `MEMORY`, `FILE` sau `DATABASE`. Pentru modul `FILE`, sunt necesare și `user.file` și `friendship.file`.

Nu încărca parola personală în Git. Fișierul `src/resources/config.properties` este exclus din Git prin `.gitignore`.

### Schema PostgreSQL

Codul curent din `FriendshipDbRepository` folosește coloanele `id1`, `id2` și `friends_since`. Tabelul utilizatorilor folosește `id`, `first_name` și `last_name`.

**Atenție:** fișa laboratorului numește coloana datei prieteniei `friends_from`, dar codul actual folosește `friends_since`. Numele coloanei din baza de date și numele folosit în interogările Java trebuie să fie identice.

De asemenea, verifică dacă baza de date din `db.url` are același nume în `config.properties` și `config.example.properties`.

## Gradle și pornirea aplicației

Gradle construiește proiectul și gestionează bibliotecile externe. `build.gradle` adaugă driverul PostgreSQL JDBC, iar Gradle Wrapper fixează versiunea Gradle folosită de proiect.

Din rădăcina proiectului, poți construi proiectul pe Windows cu:

```powershell
.\gradlew.bat build
```

Pentru a porni aplicația din IntelliJ, rulează clasa `Run`. Clasa principală a proiectului este `Run.java`.

Înainte de folosirea modului `DATABASE`, pornește serverul PostgreSQL, creează baza și tabelele, apoi completează datele de conectare în `config.properties`. Repository-urile DB folosesc schema și numele de coloane descrise mai sus.
