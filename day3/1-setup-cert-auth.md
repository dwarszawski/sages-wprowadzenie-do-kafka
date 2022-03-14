## Simulate CA authority

* create temporary workdir

```text

mkdir tmp
cd tmp

```

* Setup Certificate Authority


Generating a pair of public ca-cert and private ca-key
```text
 
openssl req -new -newkey rsa:4096 -days 365 -x509 -subj "/CN=kafka-ca" -keyout ca-key -out ca-cert -nodes
cat ca-cert
cat ca-key
keytool -printcert -v -file ca-cert

```

* Show public certificates of Facebook

```text

openssl s_client -connect www.facebook.com:443 2>/dev/null |  openssl x509 -noout -text  -subject -nameopt multiline -issuer | less

```
