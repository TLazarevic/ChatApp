# ChatApp
Zadatak iz predmeta Agentske tehnologije

Klijent-server aplikacija za chat koje predstavlja agentsko okruženje implemetirana upotrebom JEE platforme.

Frontend:
<br>
Tehnologija: Angular 6
<br>
Pokretanje:
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

Backend:
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
Pokretanje klastera može se izvršiti na jedan od sledeća dva načina:
<br> 
1. Statički: Iz connection.properties fajla željenog mastera izbrisati ip adresu. U properties fajl željenih čvorova upisati ip adresu mastera
<br>
2. Dinamički discovery: Otkomentarisati kod za discovery i zakomentarisati kod za connection.properties. Dosta, dosta sporije.
<br>
Za testiranje preDestroy dovoljno je uraditi fullPublish bez zaustavljanja projekta.
<br>
Za testiranje klastera potrebno je u standalone-ful-ha izmeniti adresu public interfejsa.
<br>

Autor:Tamara Lazarević
