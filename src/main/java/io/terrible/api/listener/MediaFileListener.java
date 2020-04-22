/* Licensed under Apache-2.0 */

package io.terrible.api.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.terrible.api.domain.MediaFile;
import io.terrible.api.services.MediaFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@Slf4j
@RequiredArgsConstructor
@EnableBinding(MessageBinding.class)
public class MediaFileListener {

    private final ObjectMapper objectMapper;

    private final MediaFileService mediaFileService;

    @StreamListener(target = MessageBinding.API_CHANNEL)
    public void processDirectoryMessage(final String message) {

        try {
            mediaFileService.save(objectMapper.readValue(message, MediaFile.class)).subscribe();
        } catch (final Exception e) {
            log.info("Unable save media file {}", e.getMessage());
        }
    }

}
