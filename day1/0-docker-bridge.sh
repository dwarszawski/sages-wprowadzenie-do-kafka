# Checking if docker bridge network working correctly

## Create a bridge network
docker network create mynetwork

## Inpect the bridge network
docker network inspect mynetwork

## Extract the Gateway adress. Sample output below
[
    {
        "Name": "mynetwork",
        "Id": "6c37c7036e75577a311b3296f168f9a3bb85116f6ac8d9bbfac0486f03c1ec3f",
        "Created": "2022-05-25T12:32:04.256044191+02:00",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": {},
            "Config": [
                {
                    "Subnet": "172.27.0.0/16",
                    "Gateway": "172.27.0.1"
                }
            ]
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {},
        "Options": {},
        "Labels": {}
    }
]

