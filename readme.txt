FACTORY
Clasa RepositoryFactory citeste setarea repository.type din config.properties si creeaza repository-urile potrivite:

    pentru MEMORY face repository-uri direct in memorie;

    pentru FILE face repository-uri care citesc si scriu in fisiere text;

    pentru DATABASE creeaza doar niste clase de tip schelet. Cand o sa trecem la baze de date intr-un laborator viitor, va trebui doar sa implementam metodele cu JDBC in UserDbRepository si FriendshipDbRepository, pentru ca service-ul si UI-ul depind doar de interfata Repository, nu de implementare.

RepositoryFactory este un Simple Factory care foloseste un switch ca sa aleaga clasele. Daca vrem sa adaugam un tip nou de repository, trebuie sa modificam acest switch. O alta varianta ar fi fost Abstract Factory (cu cate o fabrica separata pentru fiecare tip), care e mai usor de extins fara un switch central, dar necesita mai multe clase.

FISIERE
Utilizatorii se salveaza in data/utilizatori.txt (cate unul pe linie, sub forma id;prenume;nume), iar prieteniile in data/prietenii.txt (sub forma idUtilizator1;idUtilizator2;data).

Daca un fisier nu exista la pornire, repository-ul pleaca gol, iar fisierul este creat automat la prima scriere. Daca apare o linie scrisa gresit, programul afiseaza numarul liniei si eroarea, o ignora si merge mai departe cu incarcarea liniilor corecte.

PRINCIPII SOLID SI DESIGN PATTERNS
DIP: Service-ul si UI-ul lucreaza doar cu interfata Repository, nu direct cu clasele concrete.
OCP: Putem schimba sau extinde validatorii si repository-urile fara sa umblam la logica de baza din InMemoryRepository.
SRP: Fiecare clasa are un rol clar: repository-ul se ocupa de date, validatorul verifica daca sunt corecte, iar UI-ul afiseaza mesajele.
LSP: Orice repository concret poate inlocui interfata Repository fara probleme.

Strategy: Validatorul este trimis ca parametru (injectat) in repository.
Template Method: AbstractFileRepository contine pasii generali de citire si scriere in fisier, iar clasele care o mostenesc se ocupa doar de transformarea liniilor de text in obiecte si invers.
Decorator: Am folosit BufferedReader si BufferedWriter peste fisiere pentru o citire si scriere mai eficienta.
Factory: RepositoryFactory decide ce tip de repository sa creeze in functie de fisierul de configurare.