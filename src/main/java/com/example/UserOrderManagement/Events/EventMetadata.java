package com.example.UserOrderManagement.Events;

import java.time.Instant;
import java.util.UUID;

public record EventMetadata(UUID eventId,
                            String eventType,
                            Instant occuredAt,
                            String producer,
                            int version) {
}
