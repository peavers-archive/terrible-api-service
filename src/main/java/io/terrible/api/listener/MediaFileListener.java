/* Licensed under Apache-2.0 */

package io.terrible.api.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.terrible.api.domain.MediaFile;
import io.terrible.api.services.MediaFileService;
import io.terrible.api.utils.FileUtil;
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
            final MediaFile mediaFile = objectMapper.readValue(message, MediaFile.class);

            // Create directory for thumbnails and save the location for further use
            mediaFile.setThumbnailPath(FileUtil.getThumbnailDirectory(mediaFile));

            mediaFileService.save(mediaFile).subscribe();
        } catch (final Exception e) {
            log.info("Unable save media file {}", e.getMessage());
        }
    }

    @StreamListener(target = MessageBinding.API_CHANNEL_THUMBNAIL)
    public void processThumbnailMessage(final String message) {

        try {
            final MediaFile mediaFile = objectMapper.readValue(message, MediaFile.class);

            log.info("MediaFile {}", mediaFile);

        } catch (final Exception e) {
            log.info("Unable save media file {}", e.getMessage());
        }
    }

}
