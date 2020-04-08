/* Licensed under Apache-2.0 */

package io.terrible.api.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Chris Turner (chris@forloop.space)
 */
@Slf4j
@Configuration
public class SearchConfig {

    @Bean
    public RestHighLevelClient client() {

      final RestHighLevelClient client = new RestHighLevelClient(
              RestClient.builder(new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9300, "http")));

      return client;
    }


}
