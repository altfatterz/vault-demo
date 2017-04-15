1. Install vault

```bash
$ brew install vault
```

2. Start vault
```bash
$ vault server -dev-root-token-id='00000000-0000-0000-0000-000000000000' -dev
```

### Vault without SSL
 
Run Example in the `vault-without-ssl` module

### VaultPropertySource

```bash
$ vault write secret/myApp/config database.url=localhost database.username=foo database.password=bar
```
Run Example in the `vault-property-source` module


### Vault config 

vault server -config vault.conf

vault init (default is 5 keys with threshold 3)
vault init -key-shares=5 -key-threshold=2

vault unseal
vault auth

vault write secret/vault-demo mykey='Hello World'


Starting with consul secret backend:
The `secret backend` is untrusted and is used only to durably store encrypted data.
When the Vault server is started, it must be provided with a `secret backend` so that data is available across restarts.

```
consul agent -dev -node MyConsulServer
```

http://localhost:8500/


Once started, the Vault is in a `sealed` state. (in consul will be visible), it knows where is the secret backend but does not know how to decrypt it.


In the `secret backend` the data is encrypted. Vault needs the encryption key in order to decrypt the data.
The encryption key is also stored with the data, but encrypted with another encryption key known as the `master key`. The master key isn't stored anywhere.
Unsealing is the process of reconstructing this `master key`.

Instead of distributing this master key as a single key to an operator uses this algorithm:
https://en.wikipedia.org/wiki/Shamir%27s_Secret_Sharing


```
GPG:
brew install gnupg

gpg --list-keys

gpg --gen-key
gpg --export 6B7476E1 | base64 > hansolo.asc

gpg --gen-key
gpg --export 54361D6A | base64 > jacksparrow.asc

vault init -key-shares=3 -key-threshold=2 -pgp-keys="hansolo.asc,jacksparrow.asc,keybase:altfatterz"
```

When decrypting not that matters the order

for KeyBase:
echo "c1c0..." | xxd -r -p | keybase pgp decrypt

For gpg this worked:
echo "c1c0..." | xxd -r -p | gpg -d

```
$ keybase pgp select --only-import
#    Algo    Key Id             Created   UserId
=    ====    ======             =======   ======
1    2048R   971132E36B7476E1             Han Solo <han.solo@gmail.com>
2    2048R   60218A6454361D6A             Jack Sparrow <jack.sparrow@gmail.com>
```
