# ELECTRONIC-CATALOG

Nume: Stefan Diana

Grupa: 322CC

Gradul de dificultate al temei: mediu

Timpul alocat rezolvării: o saptamana

## MODUL DE IMPLEMENTARE: 

* Mediul ales: *Intellij IDEA*

* Pentru testare am creat un fisier `input.txt`, care contine informatii despre cursuri in urmatoarea ordine: numele cursului, numarul de puncte de credit, grupele (separate prin “*”) – asistentul grupei, studentii grupei, avand pe aceeasi linie, daca este cazul, numele parintilor si notele inca nevalidate. Aceste date din citite in main si adaugate in Catalog.

* Am implementat urmatoarele design pattern-uri: **Singleton**, **Factory**, **Builder**, **Observer** (interfata Observer este implementata de clasa Parent si interfata Subject este implementata de clasa Catalog), **Strategy**, **Visitor**, **Memento**.

* Interfata grafica consta intr-un meniu, in care utilizatorul trebuie sa introduca rolul sau (Student / Teacher / Assistant / Parent) si numele, dupa care este redirectionat in pagina corespunzatoare, cu posibilitatea revenirii.

* `Student Page` contine o lista cu cursurile la care studentul este inscris si un panel in care sunt afisate informatii referitoare la cursul selectat.

* `Parent Page` contine o lista cu notificarile primite.

* `Teacher / Assistant Page` contine o lista cu cursurile la care utilizatorul preda, alaturi de cel mai un student al cursului respectiv si studentii promovati, o lista cu notele inca nevalidate, cu posibilitatea validarii lor, o lista cu notele trecute in catalog si butoane pentru Backup si Undo.

![Screenshot 2024-02-23 205535](https://github.com/diana389/Electronic-Catalog/assets/94044563/e6474b6e-8818-40a8-b180-b1d34c29fc4b)
![Screenshot 2024-02-23 205605](https://github.com/diana389/Electronic-Catalog/assets/94044563/c2808830-413e-46d9-a983-571a899a15a0)
![Screenshot 2024-02-23 205755](https://github.com/diana389/Electronic-Catalog/assets/94044563/4f314173-aa2b-4d23-a05e-165fa2cca5cb)
![Screenshot 2024-02-23 205736](https://github.com/diana389/Electronic-Catalog/assets/94044563/81782423-ea81-4ad2-8e03-81302e1df74b)
![Screenshot 2024-02-23 205644](https://github.com/diana389/Electronic-Catalog/assets/94044563/b614bde1-fb5c-4fab-ad63-9ad1df71f8bf)
![Screenshot 2024-02-23 205703](https://github.com/diana389/Electronic-Catalog/assets/94044563/6627c203-4985-4395-bef4-2f3196e80d13)
