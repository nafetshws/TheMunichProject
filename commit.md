# Anleitung für git/github

## Unterschied zwischen dem lokalen Repository und dem remote Repository
Das lokale Repository ist nur auf eurem Computer gespeichert, weshalb ihr dieses beliebig verändern könnt. Das remote Repository wird auf Github gehostet und ist das Repsoitory, an dem das ganze Team gemeinsam arbeitet.

## Workflow
1. Ihr wollt am Projekt weiterarbeiten und müsst daher die letzten Änderungen herunterladen
``` shell
git pull origin master
```
2. Ihr habt ein neues feature implementiert, oder einn Bug behoben etc.
3. Alle Dateien speichern (strg + s)
4. Änderungen in eurem lokalen Repository speichern:
``` shell
# Datei(n) mit Änderungen die hinzugefügt werden sollen makieren
git add VeränderteKlasse.java
# Falls mehrere Dateien verändert wurden, könnt ihr einfach alle Dateien im Projekt auf einmal hinzufügen (empfohlen)
git add .

# Anschließend die Änderungen im lokalen Repository als eine neue Version speichern
git commit -m "Hier die Veränderungen reinschreiben, die mit der neuen Version des Projektes einhergehen"
```
5. Wenn ihr die neue Version hochladen wollt, damit die Anderen eure Veränderungen auch sehen können:
``` shell
git push origin master
```

## Branches (Äste)
Branches sind unabhängige Entwicklungslinien. Es gibt quasi einen Stamm, der die Hauptversion darstellt (Diese sollte immer funktionieren). Diesen branch nennt man auch "master". Wenn man ein neues experimentelles feature hinzufügen möchte, aber nicht will, dass dieses in der Hauptversion auftaucht, da das feature experimentell ist und nicht zwingend fehlerfrei ist, kann man einen neuen branch anlegen. Dieser branch ist ein exaktes Duplikat vom master branch, mit dem Unterschied, dass man diesen beliebig verändern und auch wieder hochladen kann, ohne den master branch zu beeinflussen. Wenn man dann feststellt, dass das feature bugfrei funktioniert, kann man die zwei branches mergen (zusammenführen).
``` shell
# Listet alle branches auf und zeigt an, in welchem man sich befindet
git branch --list
# Erstellt einen neuen branch (und wechselt in ihn)
git checkout -b neuerBranch
# Wechselt zwischen branches
git checkout master
# Zusammenführen von einem branch mit dem master branch
git checkout master
git merge neuerBranch
# Löschen eines branches
git branch -d neuerBranch
```
