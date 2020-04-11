/* Licensed under Apache-2.0 */
package io.terrible.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "media-files")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaFile {

  @Id private String id;

  private String name;

  private String absolutePath;

  private String mimeType;

  private long size;

  private long lastAccessTime;

  private long lastModifiedTime;

  private long importedTime;

  private ArrayList<String> thumbnails;
}
