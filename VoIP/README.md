#P2P System and Security Course Project

## VOIP Module

Summer Semester 2015

__Team Memebers__ - Ankit Bahuguna and Muhammad Zeeshan

### Module Information:

* voip : Main module which initiates the voip application.
* ui : User Interface for VOIP Client.
* messages : Messages to DHT and KX 
* crypto : Generating the pseudo-identities of the calle.
* logger : Setting up log.
* datacontrol : Audio Recive and Record functionalities.



### Instructions on how to genrate the HOST_KEY and the PEER_ID

1. Generates a Public/Private Key pair 4096 bit SHA encrypted. HOSTKEY = keypair.pem
```
$ openssl genrsa -out keypair.pem 4096
```
2. Generates a public key part of the public/private key and stores the same in a binary format
```
$ openssl rsa -in keypair.pem -pubout -inform PEM -outform DER -out pub.der
```
3. Use pub.der and extract the PEER_ID using the following:
```
$ openssl dgst -sha256 -binary pub.der #actual 32 bytes of the peer ID outputted to stdout
```
```
$ openssl dgst -sha256 -hex pub.der #output in hex format (useful for printing the peer ID while debugging) 
```

Result:

```
Ankits-MacBook-Pro:p2p ankit$ openssl dgst -sha256 -binary pub.der
f?=? 
```

Peer ID is f?=?

```
Ankits-MacBook-Pro:p2p ankopenssl dgst -sha256 -hex pub.der
SHA256(pub.der)= b04b31d1e6810d4f521117434900b1feea4cb7191281d83f147b7b0d66cf3dcd 
```
Use the hexadecimal value while debugging since it looks pretty.

### Peer Identity vs Pseudo Identity

**Central Idea of Working of Pseudo - Identities and difference between a Pseudo Identity and a PeerID**

Peer Id is generated using a hostkey (keypair.pem file containing the Public and Private Key pair of client peer).
PeerID_A = SHA-256 (PUBLIC KEY PART of keypair.pem FILE) {THIS IS UNIQUE}

The concept of a Pseudo Identity is based on a similar concept but with a key difference,
The pseudo identity is derived from pseudonymkeypair(i).pem file:

Thus, For a user 'A':

PseudoIdentity_A1 = SHA-256(PUBLIC KEY PART of pseudonymkeypair1.pem FILE)
PseudoIdentity_A2 = SHA-256(PUBLIC KEY PART of pseudonymkeypair2.pem FILE)
PseudoIdentity_A3 = SHA-256(PUBLIC KEY PART of pseudonymkeypair3.pem FILE)

Hence, USER 'A' has the following Pseudo-Identities:
{PseudoIdentity_A1, PseudoIdentity_A2, PseudoIdentity_A3}
But, Has only one peer id: PeerID_A 

Thus, when communicating with a peer B, the peer 'A' shares a certain public key associated with the respective pseudoidentity and his peerID which is unique.

