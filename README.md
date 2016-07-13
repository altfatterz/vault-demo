vault server -config vault.conf

vault init (default is 5 keys with threshold 3)
vault init -key-shares=5 -key-threshold=2

vault unseal
vault auth

vault write secret/vault-demo mykey='Hello World'


Starting with consul secret backend:
consul agent -dev -node MyConsulServer


