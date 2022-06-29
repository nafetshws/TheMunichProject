# TheMunichProject

### Wie startet man das Spiel
Um das Spiel zu starten muss man zuerst die Javadatei: "src/multiplayer/Server.java" ausführen. Diese erstellt den Server und darf nicht geschlossen werden. Im Anschluss führt man "src/game/Main.java" aus.

#### Grundidee

**Abgewandeltes Jump & Run in 2D**
* 2 Teams mit jeweils 2 Spielern
* Eine Flagge als Ziel von beiden Gruppen
* Punkte sammeln durch Zeit und durch einsammeln von Diamanten (die jedes Spiel zufällig ausgelegt sind)
* Team hat Highscore gemeinsam
* Team muss zusammenarbeiten, um das Ziel zu erreichen

#### Aufbau
* Seitwärts in 2D mit schwebenden Böden
* Startseite, man kann Teamfarbe wählen
* Player A Steuerung durch awsd, Player B durch Pfeiltasten
* Zwei Computer benötigt insgesamt

#### Figuren
* Können rennen und springen
* Gruppenmitglieder können nicht durcheinander Durchlaufen -> Behinderung des anderen Teams möglich

#### Erlebnis
* Startseite mit direkter Regelerklärung eingeblendet
* Start drücken
* Teamfarbe wählen

#### Erweiterungsmöglichkeiten
* Booster einsammeln (z.B. Punchhand)
* Teamarbeit durch einzelne Aufgaben
* Bewegende Böden
* Ansprechende Einleitungsgeschichte
* Einrichten mehrerer Level
