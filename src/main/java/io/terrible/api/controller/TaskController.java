/* Licensed under Apache-2.0 */
package io.terrible.api.controller;

import io.terrible.api.services.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;

  @GetMapping("/thumbnails")
  public Flux<?> thumbnails() {

    return taskService.createThumbnails();
  }

  @GetMapping("/directories")
  public Mono<?> directories(@RequestParam final String path) {

    return taskService.scanDirectory(path);
  }
}
