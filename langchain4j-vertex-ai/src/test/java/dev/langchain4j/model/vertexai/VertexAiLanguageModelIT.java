package dev.langchain4j.model.vertexai;

import static org.assertj.core.api.Assertions.assertThat;

import dev.langchain4j.model.output.Response;
import dev.langchain4j.model.output.TokenUsage;
import org.junit.jupiter.api.Test;

class VertexAiLanguageModelIT {

    @Test
    void languageModel() {
        VertexAiLanguageModel vertexAiLanguageModel = VertexAiLanguageModel.builder()
                .endpoint(System.getenv("GCP_VERTEXAI_ENDPOINT"))
                .project(System.getenv("GCP_PROJECT_ID"))
                .location(System.getenv("GCP_LOCATION"))
                .publisher("google")
                .modelName("text-bison@001")
                .temperature(0.2)
                .maxOutputTokens(50)
                .topK(40)
                .topP(0.95)
                .maxRetries(2)
                .build();

        Response<String> response = vertexAiLanguageModel.generate("hi, what is java?");

        assertThat(response.content()).containsIgnoringCase("java");

        TokenUsage tokenUsage = response.tokenUsage();
        assertThat(tokenUsage.inputTokenCount()).isEqualTo(6);
        assertThat(tokenUsage.outputTokenCount()).isGreaterThan(1);
        assertThat(tokenUsage.totalTokenCount()).isGreaterThan(7);

        assertThat(response.finishReason()).isNull();
    }
}
