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
-registrovati i prijaviti se na proizvoljnog korisnika
-spisak korisnika je na početnoj stranici
-poruka se šalje iz dijaloga koji se otvara odabirom korisnika sa spiska
-poruka svim korisnicima šalje se iz toolbar trake (1. ikonica)
-pregled poruka za sada je na pocetnoj stranici. Vršiće se iz toolbar trake

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
Autor:Tamara Lazarević
