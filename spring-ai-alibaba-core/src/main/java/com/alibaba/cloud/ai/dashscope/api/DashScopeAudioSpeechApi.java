/*
 * Copyright 2024-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.cloud.ai.dashscope.api;

import com.alibaba.cloud.ai.dashscope.protocol.DashScopeWebSocketClient;
import com.alibaba.cloud.ai.dashscope.protocol.DashScopeWebSocketClientOptions;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Flux;

import java.nio.ByteBuffer;

import static com.alibaba.cloud.ai.dashscope.common.DashScopeApiConstants.DEFAULT_WEBSOCKET_URL;

public class DashScopeAudioSpeechApi {

	private final DashScopeWebSocketClient webSocketClient;

	public DashScopeAudioSpeechApi(String apiKey) {
		this(apiKey, null);
	}

	public DashScopeAudioSpeechApi(String apiKey, String workSpaceId) {
		this(apiKey, workSpaceId, DEFAULT_WEBSOCKET_URL);
	}

	public DashScopeAudioSpeechApi(String apiKey, String workSpaceId, String websocketUrl) {
		this.webSocketClient = new DashScopeWebSocketClient(DashScopeWebSocketClientOptions.builder()
			.withApiKey(apiKey)
			.withWorkSpaceId(workSpaceId)
			.withUrl(websocketUrl)
			.build());
	}

	// @formatter:off
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public record Request(
			@JsonProperty("header") RequestHeader header,
			@JsonProperty("payload") RequestPayload payload) {
		public record RequestHeader(
			@JsonProperty("action") String action,
			@JsonProperty("task_id") String taskId,
			@JsonProperty("streaming") String streaming
		) {}
		public record RequestPayload(
			@JsonProperty("model") String model,
			@JsonProperty("task_group") String taskGroup,
			@JsonProperty("task") String task,
			@JsonProperty("function") String function,
			@JsonProperty("input") RequestPayloadInput input,
			@JsonProperty("parameters") RequestPayloadParameters parameters) {
			public record RequestPayloadInput(
				@JsonProperty("text") String text
			) {}
			public record RequestPayloadParameters(
				@JsonProperty("volume") Integer volume,
				@JsonProperty("text_type") String textType,
				@JsonProperty("voice") String voice,
				@JsonProperty("sample_rate") Integer sampleRate,
				@JsonProperty("rate") Float rate,
				@JsonProperty("format") String format,
				@JsonProperty("pitch") Double pitch,
				@JsonProperty("phoneme_timestamp_enabled") Boolean phonemeTimestampEnabled,
				@JsonProperty("word_timestamp_enabled") Boolean wordTimestampEnabled
			) {}
		}
	}

	// @formatter:off
    public static class Response {
        ByteBuffer audio;

        public ByteBuffer getAudio() {
            return audio;
        }
    }
    // @formatter:on

	public Flux<ByteBuffer> streamOut(Request request) {
		try {
			String message = (new ObjectMapper()).writeValueAsString(request);
			return this.webSocketClient.streamBinaryOut(message);
		}
		catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * DashScope text to speech model list. More models:
	 * <a href="https://help.aliyun.com/zh/model-studio/sambert-java-sdk">Model Stt
	 * Models</a>
	 */
	public enum TTSModel {

		@JsonProperty("sambert-zhinan-v1")
		SAM_BERT_ZHINAN_V1("sambert-zhinan-v1"),

		@JsonProperty("sambert-zhiqi-v1")
		SAM_BERT_ZHIQI_V1("sambert-zhiqi-v1"),

		@JsonProperty("sambert-zhichu-v1")
		SAM_BERT_ZHICHU_V1("sambert-zhichu-v1"),

		@JsonProperty("sambert-zhide-v1")
		SAM_BERT_ZHIDE_V1("sambert-zhide-v1"),

		@JsonProperty("sambert-zhiwei-v1")
		SAM_BERT_ZHIWEI_V1("sambert-zhiwei-v1"),

		@JsonProperty("sambert-zhiting-v1")
		SAM_BERT_ZHITING_V1("sambert-zhiting-v1");

		private final String model;

		TTSModel(String model) {
			this.model = model;
		}

		public String getValue() {
			return this.model;
		}

	}

	/**
	 * DashScope Audio speech model list.
	 */
	public enum AudioSpeechModel {

		@JsonProperty("sambert-zhinan-v1")
		SAM_BERT_ZHINAN_V1("sambert-zhinan-v1"),

		@JsonProperty("sambert-zhiqi-v1")
		SAM_BERT_ZHIQI_V1("sambert-zhiqi-v1"),

		@JsonProperty("sambert-zhichu-v1")
		SAM_BERT_ZHICHU_V1("sambert-zhichu-v1"),

		@JsonProperty("sambert-zhide-v1")
		SAM_BERT_ZHIDE_V1("sambert-zhide-v1"),

		@JsonProperty("sambert-zhiwei-v1")
		SAM_BERT_ZHIWEI_V1("sambert-zhiwei-v1"),

		@JsonProperty("sambert-zhiting-v1")
		SAM_BERT_ZHITING_V1("sambert-zhiting-v1");

		public final String value;

		AudioSpeechModel(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

	}

	public enum RequestTextType {

		// @formatter:off
		@JsonProperty("plain_text") PLAIN_TEXT("PlainText"),
		@JsonProperty("ssml") SSML("SSML");
		// @formatter:on

		private final String value;

		RequestTextType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	}

	public enum ResponseFormat {

		// @formatter:off
		@JsonProperty("pcm") PCM("pcm"),
		@JsonProperty("wav") WAV("wav"),
		@JsonProperty("mp3") MP3("mp3");
		// @formatter:on

		public final String formatType;

		ResponseFormat(String value) {
			this.formatType = value;
		}

		public String getValue() {
			return this.formatType;
		}

	}

}
