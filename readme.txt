FACTORY
Clasa RepositoryFactory citeste setarea repository.type din config.properties si creeaza repository-urile potrivite:

    pentru MEMORY face repository-uri direct in memorie;

    pentru FILE face repository-uri care citesc si scriu in fisiere text;

    pentru DATABASE creeaza doar niste clase de tip schelet. Cand o sa trecem la baze de date intr-un laborator viitor, va trebui doar sa implementam metodele cu JDBC in UserDbRepository si FriendshipDbRepository, pentru ca service-ul si UI-ul depind doar de interfata Repository, nu de implementare.

RepositoryFactory este un Simple Factory care foloseste un switch ca sa aleaga clasele. Daca vrem sa adaugam un tip nou de repository, trebuie sa modificam acest switch. O alta varianta ar fi fost Abstract Factory (cu cate o fabrica separata pentru fiecare tip), care e mai usor de extins fara un switch central, dar necesita mai multe clase.

FISIERE
Utilizatorii se salveaza in data/utilizatori.txt (cate unul pe linie, sub forma id;prenume;nume), iar prieteniile in data/prietenii.txt (sub forma idUtilizator1;idUtilizator2;data).

Daca un fisier nu exista la pornire, repository-ul pleaca gol, iar fisierul este creat automat la prima scriere. Daca apare o linie scrisa gresit, programul afiseaza numarul liniei si eroarea, o ignora si merge mai departe cu incarcarea liniilor corecte.

PRINCIPII SOLID

S — (Single Responsibility Principle): Fiecare clasă are un rol clar: repository-ul se ocupă de date, validatorul verifică dacă sunt corecte, iar UI-ul afișează mesajele.

O — (Open/Closed Principle): Putem adăuga sau schimba validatori și repository-uri fără să modificăm logica de bază din InMemoryRepository.

L — (Liskov Substitution Principle): Orice repository concret poate înlocui interfața Repository și poate fi folosit fără să strice funcționarea aplicației.

I —(Interface Segregation Principle): Interfața Repository ar trebui să conțină doar operațiile necesare repository-urilor, fără metode inutile pentru clasele care o implementează.

D —(Dependency Inversion Principle): Service-ul și UI-ul lucrează cu interfața Repository, nu direct cu clasele concrete.

Strategy: Validatorul este trimis ca parametru (injectat) in repository.
Template Method: AbstractFileRepository contine pasii generali de citire si scriere in fisier, iar clasele care o mostenesc se ocupa doar de transformarea liniilor de text in obiecte si invers.
Decorator: Am folosit BufferedReader si BufferedWriter peste fisiere pentru o citire si scriere mai eficienta.
Factory: RepositoryFactory decide ce tip de repository sa creeze in functie de fisierul de configurare.




LAB 5:
Am ales Optional<E> pentru ca rezultatul cautarii paote lipsi. Astfel findOne arata clar ca poate intoarece o entitate daca e gasita
fie un rezutlat gol, fara sa mai foloseasca null. Iar in service cand trebuie sa existe entitatea, tranformam rezultaul gol in aceeasi exceptie