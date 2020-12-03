#!/usr/bin/env bash
mkdir tmp
cd tmp

# Setup Certificate Authority by generating ca-cert, ca-key(priv-key)
openssl req -new -newkey rsa:4096 -days 365 -x509 -subj "/CN=kafka-ca" -keyout ca-key -out ca-cert -nodes
cat ca-cert
cat ca-key
keytool -printcert -v -file ca-cert

# show public certificates of facebook
openssl s_client -connect www.facebook.com:443 2>/dev/null |  openssl x509 -noout -text  -subject -nameopt multiline -issuer | less