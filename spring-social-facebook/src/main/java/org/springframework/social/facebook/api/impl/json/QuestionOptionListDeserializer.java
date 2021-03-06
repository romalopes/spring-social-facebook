/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.facebook.api.impl.json;

import java.io.IOException;
import java.util.List;

import org.springframework.social.facebook.api.QuestionOption;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class QuestionOptionListDeserializer extends JsonDeserializer<List<QuestionOption>> {
	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionOption> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new FacebookModule());
		jp.setCodec(mapper);
		if (jp.hasCurrentToken()) {
			TreeNode dataNode = jp.readValueAs(JsonNode.class).get("data");
			if (dataNode != null) {
				// TODO: THIS PROBABLY ISN"T RIGHT
				return (List<QuestionOption>) mapper.reader(new TypeReference<List<QuestionOption>>() {}).readValue((JsonNode) dataNode);
			}
		}
		
		return null;
	}
}
