Projeto demo da incubadora. Spring Security com OAuth2.

Usando:

* Spring Security
* OAuth2
* JDK 11
* Springboot 2.2.1
* IntelliJ
* Rest
* Lombok
* Devtools
* Gradle
* H2
 
 
Este é um projeto rest de CRUD de Usuário, usando Spring Security com OAuth2. O endpoint para criar um novo usuário
esta liberando para acesso:

URL para criação de usuário (Tipo POST):
http://localhost:8080/users

JSON
```
{
	"username":"novo_usuario",
	"password":"nova_senha"
}
```

Para fazer o login e receber um token de acesso, usar a URL:  
http://localhost:8080/oauth/token

Usando POST: 
Definir o corpo da requisição como "Form URL Encoded" e passar as variáveis com o seguintes valores:
```
grant_type = password
username = admin
password = admin
```

Usando GET:  
http://localhost:8080/oauth/token?grant_type=password&username=admin&password=admin

E para ambos (POST ou GET) definir a autenticação como "Basic Auth" passando os valores:
```
username = client-id
password = secret-id
```

Será retornado uma estrutura JSON semelhante abaixo:
```
{
  "access_token": "a7c3a1aa-5ec5-41d7-90b9-fdd60802c363",
  "token_type": "bearer",
  "refresh_token": "11add52f-cc4e-41c5-856a-d24f8c2e2dfa",
  "expires_in": 3599,
  "scope": "read write trust"
}
```

O endpoint para listar todos os usuário necessita autorização por token(access_token fornecido acima) para a URL:  
http://localhost:8080/users

Deve-se definir a autenticação como "Bearer" passando os valores: 
```
token = (access_token)
prefix = Bearer
```
Deve ser retornado a lista de usuário cadastrados.
