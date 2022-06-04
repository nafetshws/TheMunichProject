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