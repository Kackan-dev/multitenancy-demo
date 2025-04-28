To simplify I focused on multi-tenancy per schema approach so application uses in memory user details service and doesn't create own jwt if you want generate jwt please use https://jwt.io/ with secret key 'very_hard_and_very_secret_kackan_secret_key'. 
I recommend to use postman as a client with sub JOHNDOE or ANNADOE (these users exist and have own schema) as a payload
