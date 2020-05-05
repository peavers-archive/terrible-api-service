/* Licensed under Apache-2.0 */
package io.terrible.api.listener;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MessageBinding {

  String API_CHANNEL = "apiChannel";

  String API_CHANNEL_THUMBNAIL = "apiChannelThumbnail";

  @Input(API_CHANNEL)
  SubscribableChannel apiChannel();

  @Input(API_CHANNEL_THUMBNAIL)
  SubscribableChannel apiChannelThumbnail();

}
