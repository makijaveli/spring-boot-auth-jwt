# Getting Started

* @EnableWebSecurity: This allows us to enable security in our API.
* WebSecurityConfigurerAdapter: Allows us to override the default behavior of the security provided by Spring.
* CORS: We enable the default configuration that we can overwrite through the CorsFilter bean.
* SessionCreationPolicy: Defines the API as Stateless avoiding the creation of HTTPSession and we disable the use of cookies.
* CSRF: By configuring the API as stateless we do not need the use of cookies.
* Configure the API response in case of an authentication error.
* We declare as public the methods that allow the login and obtaining of JWT tokens. And we implement the security for the rest of the methods.
* We add the filter to check and validate if the request has a JWT token.