A feladat az Areus felvételi procedúrájának keretein belül készült.

Feladatleírás:
Készíts egy Spring boot alapú REST API-t, amely ügyfél adatok tárolására és kezelésére alkalmas.
- Az alkalmazás rendelkezzen adatbázissal, amelyet maga hoz létre és tölt fel tesztadatokkal;
- Az ügyfél entitás rendelkezzen a valós életben szokványos tulajdonságokkal;
- A REST API nyújtson CRUD metódusokat;
- Az adatok legyenek validálva;
- A REST API rendelkezzen authentikációval;
- Legyen egy végpont, amely visszaadja az ügyfelek átlagéletkorát. A számítást a JPA
  segítségével (query-vel) valósítsd meg;
- Legyen egy végpont, amely visszaad minden 18 és 40 év közötti ügyfelet. A számítást
  streaming segítségével valósítsd meg;
- Bizonyítsd az alkalmazás helyes működését!

Az alkalmazás készítése során a következő technológiákat használtam:
- Java 23
- Spring Boot 3.4.2
- Gradle 8.10
- H2 adatbázis
- Lombok

A Spring Boot verzió illetve az alkalmazás verzió a gradle.properties-ben állítható.
A funkciók teszteléséhez készítettem egy példa postman collectiont, ami a /postman mappában található.

Egyebek: 
 - Mivel nem szerettem volna túlbonyolítani a feladatot, egyes extra funkciók csak bizonyos esetekben lépnek életbe:
   - GlobalExceptionHandler, ami csak akkor lép életbe, ha update vagy create során egy vagy több hibát vétünk. Ekkor az összes hibát listázza.
   - Bizonyos validációkat jakarta annotációkkal oldottam meg (@Pattern, @NotBlank)
   - Készítettem egy saját validátort is -> AgeValidator (ami a születési időt is nézi), ehhez pedig egy @Age annotációt.
   - Az életkor kiszámításához egy formulát írtam, ami a születési időből számolja ki az életkort.
   - A SecurityProperties olvassa fel az application.yaml-ből a username/pass párost, illetve a cors URL-eket, egy "jövőbeli" frontendhez.
   - A security-t intéző AppWebSecurityConfiguration-ben is bizonyos session és cors beállításokat eszközölünk, amire jelenleg nincs szükség, csak egy "jövőbeli" frontendhez.
   - A tesztek több megoldást is használnak, a controllerhez WebMvcTest és Mock, a servicehez Mockito és reflection, az initializer teszteléséhez pedig DataJpaTest. 
   - A tesztek 100%-ban lefedik a controller, service és util packageket.
   - Igyekeztem alapos, angol nyelvű dokumentációt készíteni a kódhoz, ahol szükségesnek éreztem.
