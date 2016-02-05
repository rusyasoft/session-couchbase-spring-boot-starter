package com.github.mkopylec.sessioncouchbase

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import spock.lang.Shared
import spock.lang.Specification

@WebIntegrationTest(randomPort = true)
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = TestApplication)
abstract class BasicSpec extends Specification {

    @Shared
    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private EmbeddedWebApplicationContext context

    private ThreadLocal<String> cookies = new ThreadLocal<>()

    private <T> ResponseEntity<T> post(String path, Class<T> responseType, Map<String, Object> parameters = [:]) {
        def url = "http://localhost:$port/$path"
        def params = new LinkedMultiValueMap<>()
        for (def parameter : parameters.entrySet()) {
            params.add(parameter.key, parameter.value)
        }
        HttpHeaders headers = new HttpHeaders()
        headers.set('Cookie', cookies.get())
        def request = new HttpEntity<>(params, headers)
        def response = restTemplate.postForEntity(url, request, responseType) as ResponseEntity<T>
        cookies.set(response.headers.get('Set-Cookie') as String)

        return response
    }

    protected int getPort() {
        return context.embeddedServletContainer.port
    }

    void cleanup() {
        cookies.remove()
    }
}
