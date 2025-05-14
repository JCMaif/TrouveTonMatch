package org.simplon.TrouveTonMatch.mapper;

import org.mapstruct.Mapper;
import org.simplon.TrouveTonMatch.dtos.MessageDto;
import org.simplon.TrouveTonMatch.model.Message;

@Mapper
public interface MessageMapper {

    Message toMessage(MessageDto messageDto);

    MessageDto toMessageDto(Message message);
}
