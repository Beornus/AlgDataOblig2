# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 

# Krav til innlevering

Se oblig-tekst for alle krav. Oppgaver som ikke oppfyller følgende vil ikke få godkjent:

* Git er brukt til å dokumentere arbeid (minst 2 commits per oppgave, beskrivende commit-meldinger)	
* Ingen debug-utskrifter
* Alle testene som kreves fungerer (også spesialtilfeller)
* Readme-filen her er fyllt ut som beskrevet

# Arbeidsfordeling

Oppgaven er levert av følgende studenter:
* Björn Pettersson, S344080, s344080@oslomet.no

Vi har brukt git til å dokumentere arbeidet vårt. Vi har 16 commits totalt, og hver logg-melding beskriver det vi har gjort av endringer.

I oppgaven har vi hatt følgende arbeidsfordeling:
* Per har hatt hovedansvar for oppgave 1, 3, og 5. 
* Else har hatt hovedansvar for oppgave 2, 4, og 6. 
* Fatima har hatt hovedansvar for oppgave 7 og 8. 
* Vi har i fellesskap løst oppgave 10. 

# Beskrivelse av oppgaveløsning (maks 5 linjer per oppgave)

* Oppgave 1: Denna löste jag genom att starta med tom() metoden. Då kunde jag gå vidare med antall()
genom att använda mig av tom() och sen räkna upp antalet. Till slut så skrev jag en konstruktör,
men på grund av felaktiga pekare i min första lösning så skrev jag om den senare när de andra uppgifterna
fick fel i testerna.

* Oppgave 2: toString() och omvendtString() löste jag genom att sätta upp en StringBuilder som sen la till
värden i listan genom .append. Här fick jag först en del fel med omvendtString() och det visade sig vara på grund
av min konstruktör.
I leggInn() så gjorde jag först en väldigt ineffektiv kod som loopade igenom hela listan istället för att
använda mig av halepekaren när jag la till en ny node. Ändrade detta lite senare.

* Oppgave 3: Gjorde metoden finnNode() genom att implementera forlökker som gick antingen framåt från hode eller
bakåt från hale. Fick en del problem med testing här, men det visade sig vara på grund av fel jag gjort tidigare
i obligen. Här hade jag tänkt rätt.
hent() och oppdater() fick jag ordnat ganska fort med indeksKontroll och genom att lagra gammalt värde i en node.

* Oppgave 4: indeksTil() fick jag gjort ganska fort genom att löpa genom listan och se om värdet i parametern
matchade ett värde i listan. inneholder() fick jag till genom att kolla om indeksTil returnerar -1 eller inte.

* Oppgave 5: Löste denna uppgiften genom att först ta reda på vart i listan indeksen var. För att på så sätt hitta
vart det nya värdet skulle sättas in. Här var utmaningen att få in en node mellan två existerande noder.
Genom att använda mig av av en temporär nodepekare så klarade jag att koppla ihop pekarna.

* Oppgave 6: här fick jag gå en del fram och tillbaka med vad jag var tvungen att göra med alla olika möjligheter.
I starten såg jag mig för blind på lite exempel från kompendiet, men klarade till slut få grepp om att det viktigaste
i denna uppgiften var att hitta alla cornercases och skriva rätt instruktioner till dem.

* Oppgave 8:

