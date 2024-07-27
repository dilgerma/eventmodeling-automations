## Modeling Automations

### Setup

Slices sind im de.nebulit Package (wie im Generator angegeben) als Packages definiert.

### Start der Applikation

Zum Start des Services kann die Klasse _ApplicationStarter_ verwendet werden in _src/test/kotlin_.

Warum in _test_?

Diese Klasse startet die komplette Umgebung (inkl. Postgres und ggf. Kafka über TestContainers)

### Package Struktur

Events sind im Package "events"

Aggregates liegen im Package "domain"

Slices haben jeweils ein isoliertes Package <sliceName>

Package "common" enthält einige Interfaces für die generelle Struktur.

Die Anwendung startet auf Port 8080.

[SWAGGER UI](http://localhost:8080/swagger-ui/index.html)

[Nebulit GmbH](https://www.nebulit.de)

[Miro Eventmodell](https://miro.com/app/board/uXjVKyw_cAI=/)

[Eventmodeling Tooling in Miro](https://eventmodelers.de/eventmodeling-tooling)
