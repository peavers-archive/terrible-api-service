/* Licensed under Apache-2.0 */
package io.terrible.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/** @author Chris Turner (chris@forloop.space) */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Directory {

  @Id private String id;

  private String path;
}
