/* Licensed under Apache-2.0 */
package io.terrible.api.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** @author Chris Turner (chris@forloop.space) */
@Configuration
public class SearchConfig {

  @Bean
  public RestHighLevelClient client() {
    return new RestHighLevelClient(
        RestClient.builder(
            new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9300, "http")));
  }
}
