package com.openclassrooms.starterjwt.payload;

import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageResponseTest {

    @Test
    void testConstructorAndGetterSetter() {
        MessageResponse response = new MessageResponse("Initial message");

        // Test getter
        assertThat(response.getMessage()).isEqualTo("Initial message");

        // Test setter
        response.setMessage("Updated message");
        assertThat(response.getMessage()).isEqualTo("Updated message");
    }
}

