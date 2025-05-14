package org.simplon.TrouveTonMatch.dtos;

public record MessageDto(
        Long senderId,
        Long receiverId,
        String content
) {
}
