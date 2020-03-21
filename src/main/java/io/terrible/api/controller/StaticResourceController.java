/* Licensed under Apache-2.0 */
package io.terrible.api.controller;

import java.io.File;
import java.net.MalformedURLException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** @author Chris Turner (chris@forloop.space) */
@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class StaticResourceController {

  @GetMapping(value = "static-resource/image", produces = MediaType.IMAGE_JPEG_VALUE)
  public Resource image(@RequestParam final String path) throws MalformedURLException {

    final File file = new File(path);

    return file.canRead()
        ? new FileSystemResource(file)
        : new UrlResource("https://via.placeholder.com/316x210");
  }

  @GetMapping(value = "static-resource/video", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public Resource video(@RequestParam final String path) {

    final File file = new File(path);

    return file.canRead() ? new FileSystemResource(file) : null;
  }
}
