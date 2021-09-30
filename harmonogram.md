* Wprowadzenie do ekosystemu Kafka
	* Przykłady wdrożeń
	* Ewolucja modeli komunikacji
	* Zastosowania systemów zorientowanych na przetwarzanie komunikatów
	* Przegląd najważniejszych elementów platformy
* Konfiguracja w systemach rozproszonych
	* Rola koordynatora Apache Zookeeper w kontekście klastra Apache Kafka
	* Architektura klastra Apache Zookeeper
	* Budowanie zgodności w oparciu o kworum większości
	* Drzewiasta struktura metadanych
* Model danych
	* Podstawowe założenia i terminologia
	* Organizacja danych w ramach klastra Kafka
* Administracja klastra Apache Kafka
	* Architektura oraz możliwości konfiguracji klastra serwerów
	* Komunikacja klient-serwer
	* Konfiguracja parametrów serwera
	* Tolerencja awarii
* Producer/Consumer API
	* Producer API - semantyka dostarczenia komunikatów
	* Idempotentny Producent
	* Consumer API - semantyka odbierania komunikatów
	* Apache Kafka vs Rabbit MQ
* Aplikacje klienckie z wykorzystaniem biblioteki Kafka-Clients
	* Konfiguracja producentów i konsumentów
	* Kompresja komunikatów
* Aplikacje klienckie z wykorzystaniem Spring Framework
    * Konfiguracja producentów i konsumentów
    * Serializacja i deserializacja komunikatów
    * Filtrowanie komunikatów
    * Obsługa błędów i ponowień po stronie konsumenta
    * Strategia "Dead Letters"
* Schemat danych
    * Ograniczenia serializacji JSON
    * Wsparcie dla zmiennych schematów danych
    * Rejestr schematów
    * Reprezentacja danych w formacie AVRO (+ avro-tool)
    * Ewolucja schematów danych (kompatybilność wsteczna/w przód/pełna)
    * Konfiguracja producentów i konsumentów - wykorzystanie Avro i Schema Registry
* Przetwarzanie strumieniowe z wykorzystaniem biblioteki Kafka Streams
	* Batch vs Microbatch vs Stream
	* Topologia Kafka Streams
	* Przegląd operacji w ramach Kafka Streams DSL
	* Przetwarzanie stanowe
	* Semantyka "Exactly Once"
	* Przetwarzanie w określonych oknach czasowych
	* KStream vs KTable API
	
	
## O mnie

### BIO

Inżynier oprogramowania z ponad 10-letnim doświadczeniem w branży, od tradycyjncych aplikacji webowych do systemów rozproszonych.
Entuzjasta paradygmatu funkcyjnego i silnie typowanych języków programowania t.j. java, scala czy golang.
Kontrybutor projektów "open source" z zakresu zarządzania i wyszukiwania danych m. in. Apache Atlas, Apache Amundsen oraz autor dwóch otwartych connector-ów dla platformy Kafka Connect.
Prelegent na konferencjach Data Science Summit Warsaw, Big Data Technology Warsaw czy ING Data Engineering meetup.

### Co robię na codzień
VP Engineer w obszarze produktów inwestycyjncyh dla "Marcus by Goldman Sachs"


### Dlaczego lubię szkolić
Szkolenia to jedna z najlepszych form dzielenia się praktycznym doświadczeniem gdzie istnieje unikalna możliwość prowadzenia aktywnej interakcji z uczestnikami.
To nie tylko szansa na rozwój ale również konfrontacja różych punktów widzenia, merytoryczna dyskusja i praca z ludźmi na różnym etapie kariery. 
