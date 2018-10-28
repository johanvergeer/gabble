package com.redgyro


import brave.sampler.Sampler
import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.logging.Logger
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
class ZuulApiGatewayServerApplication {

    @Bean
    fun defaultSampler(): Sampler = Sampler.ALWAYS_SAMPLE
}


fun main(args: Array<String>) {
    SpringApplicationBuilder()
            .sources(ZuulApiGatewayServerApplication::class.java)
            .run(*args)
}

@Component
class ZuulLoggingFilter : ZuulFilter() {
    companion object {
        val logger = Logger.getLogger(ZuulLoggingFilter::class.qualifiedName)!!
    }

    override fun run(): Any? {
        val request = RequestContext.getCurrentContext().request
        logger.info("request -> $request; request uri -> ${request.requestURI}")
        return null
    }

    override fun shouldFilter() = true

    override fun filterType() = "pre" // Log everything before execution

    override fun filterOrder() = 1

}

@EnableWebSecurity    // Enable security config. This annotation denotes config for spring security.
class SecurityTokenConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    private val jwtConfig: JwtConfig? = null

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
                // make sure we use stateless session; session won't be used to store user's state.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // handle an authorized attempts
                .exceptionHandling().authenticationEntryPoint { _, rsp, _ -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED) }
                .and()
                // Add a filter to validate the tokens with every request
                .addFilterAfter(JwtTokenAuthenticationFilter(jwtConfig()), UsernamePasswordAuthenticationFilter::class.java)
                // authorization requests config
                .authorizeRequests()
                // allow all who are accessing "auth" service
                .antMatchers(HttpMethod.POST, jwtConfig().uri).permitAll()
                // must be an admin if trying to access admin area (authentication is also required here)
                .antMatchers("/gallery" + "/admin/**").hasRole("ADMIN")
                // Any other request must be authenticated
                .anyRequest().authenticated()
    }

    @Bean
    fun jwtConfig() = JwtConfig()
}

class JwtConfig {
    @Value("\${security.jwt.uri:/auth/**}")
    var uri: String = ""

    @Value("\${security.jwt.header:Authorization}")
    var header: String = ""

    @Value("\${security.jwt.prefix:Bearer }")
    var prefix: String = ""

    @Value("\${security.jwt.expiration:#{24*60*60}}")
    var expiration: Int = 0

    @Value("\${security.jwt.secret:JwtSecretKey}")
    var secret: String = ""
}

class JwtTokenAuthenticationFilter(private val jwtConfig: JwtConfig) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        // 1. get the authentication header. Tokens are supposed to be passed in the authentication header
        val header = request.getHeader(jwtConfig.header)

        // 2. validate the header and check the prefix
        if (header == null || !header.startsWith(jwtConfig.prefix)) {
            chain.doFilter(request, response) // If not valid, go to the next filter.
            return
        }

        // If there is no token provided and hence the user won't be authenticated.
        // It's Ok. Maybe the user accessing a public path or asking for a token.

        // All secured paths that needs a token are already defined and secured in config class.
        // And If user tried to access without access token, then he won't be authenticated and an exception will be thrown.

        // 3. Get the token
        val token = header.replace(jwtConfig.prefix, "")

        try {    // exceptions might be thrown in creating the claims if for example the token is expired

            // 4. Validate the token
            val claims = Jwts.parser()
                    .setSigningKey(jwtConfig.secret.toByteArray())
                    .parseClaimsJws(token)
                    .body

            val username = claims.subject
            if (username != null) {
                val authorities = claims["authorities"] as List<*>

                // 5. Create auth object
                // UsernamePasswordAuthenticationToken: A built-in object,
                // used by spring to represent the current authenticated / being authenticated user.
                // It needs a list of authorities, which has type of GrantedAuthority interface,
                // where SimpleGrantedAuthority is an implementation of that interface
                val auth = UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        authorities.asSequence()
                                .map { SimpleGrantedAuthority(it.toString()) }
                                .toList())

                // 6. Authenticate the user
                // Now, user is authenticated
                SecurityContextHolder.getContext().authentication = auth
            }

        } catch (e: Exception) {
            // In case of failure. Make sure it's clear; so guarantee user won't be authenticated
            SecurityContextHolder.clearContext()
        }

        // go to the next filter in the filter chain
        chain.doFilter(request, response)
    }
}