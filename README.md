# ChatApp
Zadatak iz predmeta Agentske tehnologije

Klijent-server aplikacija za chat koje predstavlja agentsko okruženje implemetirana upotrebom JEE platforme.

![bee img](https://github.com/TLazarevic/ChatApp/blob/master/Frontend/chatapp-frontend/src/assets/images/bee.png?raw=true)

  Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>

## Frontend:
<br>

### Tehnologija: Angular 6

<br>

### Pokretanje:

<br>
-npm start u terminalu projekta
<br>
-odlazak na localhost/4200 u pretraživaču
<br>
-registrovati i prijaviti se na proizvoljnog korisnika
<br>
-spisak korisnika je na početnoj stranici
<br>
-poruka se šalje iz dijaloga koji se otvara odabirom korisnika sa spiska
<br>
-poruka svim korisnicima šalje se iz toolbar trake (1. ikonica)
<br>
-pregled poruka je na početnoj stranici
<br>
-logout je poslednja ikonica toolbar trake
<br>

## Backend:
<br>
Ukoliko dodje do problema u pretraživaču, da bi zahtevi funkcionisali potrebno je izmeniti sledeće sekcije standalone fajla WildFly servera:

```
 <host name="default-host" alias="localhost">
            <location name="/" handler="welcome-content"/>
            <filter-ref name="server-header"/>
            <filter-ref name="x-powered-by-header"/>
            <filter-ref name="Access-Control-Allow-Origin"/>
            <filter-ref name="Access-Control-Allow-Methods"/>
            <filter-ref name="Access-Control-Allow-Headers"/>
            <filter-ref name="Access-Control-Allow-Credentials"/>
            <filter-ref name="Access-Control-Max-Age"/>
        </host>
         <filters>
        <response-header name="server-header" header-name="Server" header-value="WildFly/10"/>
        <response-header name="x-powered-by-header" header-name="X-Powered-By" header-value="Undertow/1"/>
        <response-header name="Access-Control-Allow-Origin" header-name="Access-Control-Allow-Origin" header-value="*"/>
        <response-header name="Access-Control-Allow-Methods" header-name="Access-Control-Allow-Methods" header-value="GET, POST, OPTIONS, PUT, DELETE"/>
        <response-header name="Access-Control-Allow-Headers" header-name="Access-Control-Allow-Headers" header-value="accept, authorization, content-type, x-requested-with"/>
        <response-header name="Access-Control-Allow-Credentials" header-name="Access-Control-Allow-Credentials" header-value="true"/>
        <response-header name="Access-Control-Max-Age" header-name="Access-Control-Max-Age" header-value="1"/>
    </filters>
```
<br> 

###  Pokretanje klastera može se izvršiti na jedan od sledeća dva načina:

<br> 
1. Statički: Iz connection.properties fajla željenog mastera izbrisati ip adresu. U properties fajl željenih čvorova upisati ip adresu mastera
<br>
2. Dinamički discovery: Otkomentarisati kod za discovery i zakomentarisati kod za connection.properties. Dosta, dosta sporije.
<br>
Za testiranje preDestroy dovoljno je uraditi fullPublish bez zaustavljanja projekta, ili ctrl+c u terminalu
<br>
Ip adresa se na frontendu postavlja u fajlu GlobalConstants.ts
<br>
Za testiranje handshake exceptiona dovoljno je otkomentarisati exception liniju u postNodes metodi Session Beana
<br>

## Resursi:

https://tutorialedge.net/typescript/angular/angular-websockets-tutorial/
https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-discovery-hosts-providers.html?fbclid=IwAR26ylBpFOvafvyKsCwIIvhWpLmcz2F4KBfj_NH8B1Fn18uy4icfHYgFdMY#built-in-hosts-providers
<br>
<br>
Autor:Tamara Lazarević
