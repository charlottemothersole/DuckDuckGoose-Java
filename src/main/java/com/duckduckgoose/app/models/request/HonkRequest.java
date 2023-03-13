package com.duckduckgoose.app.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class HonkRequest {

    @NotBlank(message = "Honks cannot be blank")
    @Size(max = 255, message = "Honks cannot be more than 255 characters long")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
