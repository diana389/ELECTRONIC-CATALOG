# ELECTRONIC-CATALOG

Nume: Stefan Diana

Grupa: 322CC

Gradul de dificultate al temei: mediu

Timpul alocat rezolvării: o saptamana

MODUL DE IMPLEMENTARE: 

Mediul ales: Intellij IDEA

Pentru testare am creat un fisier “input.txt”, care contine informatii despre cursuri in urmatoarea ordine: numele cursului, numarul de puncte de credit, grupele (separate prin “*”) – asistentul grupei, studentii grupei, avand pe aceeasi linie, daca este cazul, numele parintilor si notele inca nevalidate. Aceste date din citite in main si adaugate in Catalog.

Am implementat urmatoarele design pattern-uri: Singleton, Factory, Builder, Observer (interfata Observer este implementata de clasa Parent si interfata Subject este implementata de clasa Catalog), Strategy, Visitor, Memento.

Interfata grafica consta intr-un meniu, in care utilizatorul trebuie sa introduca rolul sau (Student / Teacher / Assistant / Parent) si numele, dupa care este redirectionat in pagina corespunzatoare, cu posibilitatea revenirii.

Student Page contine o lista cu cursurile la care studentul este inscris si un panel in care sunt afisate informatii referitoare la cursul selectat.
Parent Page contine o lista cu notificarile primite.

Teacher / Assistant Page contine o lista cu cursurile la care utilizatorul preda, alaturi de cel mai un student al cursului respectiv si studentii promovati, o lista cu notele inca nevalidate, cu posibilitatea validarii lor, o lista cu notele trecute in catalog si butoane pentru Backup si Undo.
