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

Once started, the Vault is in a `sealed` state. (in consul will be visible), it knows where is the secret backend but does not know how to decrypt it.


In the `secret backend` the data is encrypted. Vault needs the encryption key in order to decrypt the data.
The encryption key is also stored with the data, but encrypted with another encryption key known as the `master key`. The master key isn't stored anywhere.
Unsealing is the process of reconstructing this `master key`.

Instead of distributing this master key as a single key to an operator uses this algorithm:
https://en.wikipedia.org/wiki/Shamir%27s_Secret_Sharing
