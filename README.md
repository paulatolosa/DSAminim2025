# Paula Tolosa mínim 1 2025 tardor


# Fet a classe
fet la primera part menys el dos test que no funcionen i s'han de solucionar
posibles errors a dos metodes del impl

falta per fer:
- corregir metodes que fallen
- comprovar amb el test
- fer la segona part de rest api


# Fet a casa

PART 1
- Millora dels loggers de SistemaManagerImpl per entendre millor el funcionament del test
  (En comptes de posar al logger per exemple llibre, doncs posar llibre.getId())
- A la classe Munt s'ha afegit una funció getMida() que retorna la mida del munt. Funció implementada per tenir més informació als loggers i test
- Revisió del funcionament dels metodes implementats
- A la funció getPrestecPerLector del impl s'ha associat la exepció NoPrestecsRegistrats
- Al test he afegit més llibres per veure com s'omple un munt i com es crea un de nou
- Al test he afegit més prestecs a la funció TestAfegirPrestec per veure tots els possibles escenaris
- Correcció de la funció CatalogarLlibre, concretament:
  - llibre.setNumExemplars(1); --> Linea afegida 
  - llibresCatalogats.put(isbn, llibre);

    Sense la linea afegida, el test de testAddPrestec i el testGetPrestecPerLector, donaven error

--> Fins aqui la prim era part del mínim amb funcionament correcte i complet

PART 2
- constructors buits a cada classe pel funcionament del swagger
- A la interficie i al impl he afegit els metodes necessaris per facilitar la implementacio dels serveis res al BibliotecaService
- He implementat el servei REST per realitzar les operacions especificades a la part 1

--> Fins aqui la segona part del mínim amb funcionament correcte i complet

Nota: Totes les captures que es demanen estan adjuntes en aquest respositori
  