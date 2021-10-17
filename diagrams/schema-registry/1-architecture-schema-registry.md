```mermaid

flowchart LR
   a(Producer)
   e(Schema Registry)
   b(KAFKA)
   c(Consumer)
   a -- avro --> b -- avro --> c
   a --- e --- c
   
  
```