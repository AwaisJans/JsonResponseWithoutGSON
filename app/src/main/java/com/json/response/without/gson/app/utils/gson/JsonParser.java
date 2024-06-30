/*
 * Copyright (C) 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.json.response.without.gson.app.utils.gson;

//import com.json.response.without.gson.app.utils.errorprone.annotations.InlineMe;
import com.json.response.without.gson.app.utils.gson.internal.Streams;
import com.json.response.without.gson.app.utils.gson.stream.JsonReader;
import com.json.response.without.gson.app.utils.gson.stream.JsonToken;
import com.json.response.without.gson.app.utils.gson.stream.MalformedJsonException;
import com.json.response.without.gson.app.utils.gson.JsonElement;
import com.json.response.without.gson.app.utils.gson.JsonIOException;
import com.json.response.without.gson.app.utils.gson.JsonParseException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * A parser to parse JSON into a parse tree of {@link JsonElement}s.
 *
 * <p>The JSON data is parsed in {@linkplain JsonReader#setStrictness(Strictness) lenient mode}.
 *
 * <p>Here's an example of parsing from a string:
 *
 * <pre>
 * String json = "{\"key\": \"value\"}";
 * JsonElement jsonElement = JsonParser.parseString(json);
 * JsonObject jsonObject = jsonElement.getAsJsonObject();
 * </pre>
 *
 * <p>It can also parse from a reader:
 *
 * <pre>
 * try (Reader reader = new FileReader("my-data.json", StandardCharsets.UTF_8)) {
 *   JsonElement jsonElement = JsonParser.parseReader(reader);
 *   JsonObject jsonObject = jsonElement.getAsJsonObject();
 * }
 * </pre>
 *
 * <p>If you want to parse from a {@link JsonReader} for more customized parsing requirements, the
 * following example demonstrates how to achieve it:
 *
 * <pre>
 * String json = "{\"skipObj\": {\"skipKey\": \"skipValue\"}, \"obj\": {\"key\": \"value\"}}";
 * try (JsonReader jsonReader = new JsonReader(new StringReader(json))) {
 *   jsonReader.beginObject();
 *   while (jsonReader.hasNext()) {
 *     String fieldName = jsonReader.nextName();
 *     if (fieldName.equals("skipObj")) {
 *       jsonReader.skipValue();
 *     } else {
 *       JsonElement jsonElement = JsonParser.parseReader(jsonReader);
 *       JsonObject jsonObject = jsonElement.getAsJsonObject();
 *     }
 *   }
 *   jsonReader.endObject();
 * }
 * </pre>
 *
 * @author Inderjeet Singh
 * @author Joel Leitch
 * @since 1.3
 */
public final class JsonParser {
  /**
   * @deprecated No need to instantiate this class, use the static methods instead.
   */
  @Deprecated
  public JsonParser() {}


  public static JsonElement parseString(String json) throws com.json.response.without.gson.app.utils.gson.JsonSyntaxException {
    return parseReader(new StringReader(json));
  }


  public static JsonElement parseReader(Reader reader) throws JsonIOException, com.json.response.without.gson.app.utils.gson.JsonSyntaxException {
    try {
      JsonReader jsonReader = new JsonReader(reader);
      JsonElement element = parseReader(jsonReader);
      if (!element.isJsonNull() && jsonReader.peek() != JsonToken.END_DOCUMENT) {
        throw new com.json.response.without.gson.app.utils.gson.JsonSyntaxException("Did not consume the entire document.");
      }
      return element;
    } catch (MalformedJsonException e) {
      throw new com.json.response.without.gson.app.utils.gson.JsonSyntaxException(e);
    } catch (IOException e) {
      throw new JsonIOException(e);
    } catch (NumberFormatException e) {
      throw new com.json.response.without.gson.app.utils.gson.JsonSyntaxException(e);
    }
  }

  /**
   * Returns the next value from the JSON stream as a parse tree. Unlike the other {@code parse}
   * methods, no exception is thrown if the JSON data has multiple top-level JSON elements, or if
   * there is trailing data.
   *
   * <p>If the {@linkplain JsonReader#getStrictness() strictness of the reader} is {@link
   * Strictness#STRICT}, that strictness will be used for parsing. Otherwise the strictness will be
   * temporarily changed to {@link Strictness#LENIENT} and will be restored once this method
   * returns.
   *
   * @throws JsonParseException if there is an IOException or if the specified text is not valid
   *     JSON
   * @since 2.8.6
   */
  public static JsonElement parseReader(JsonReader reader)
      throws JsonIOException, com.json.response.without.gson.app.utils.gson.JsonSyntaxException {
    com.json.response.without.gson.app.utils.gson.Strictness strictness = reader.getStrictness();
    if (strictness == com.json.response.without.gson.app.utils.gson.Strictness.LEGACY_STRICT) {
      // For backward compatibility change to LENIENT if reader has default strictness LEGACY_STRICT
      reader.setStrictness(com.json.response.without.gson.app.utils.gson.Strictness.LENIENT);
    }
    try {
      return Streams.parse(reader);
    } catch (StackOverflowError e) {
      throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", e);
    } catch (OutOfMemoryError e) {
      throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", e);
    } finally {
      reader.setStrictness(strictness);
    }
  }

  /**
   * @deprecated Use {@link JsonParser#parseString}
   */
  @Deprecated
//  @InlineMe(replacement = "JsonParser.parseString(json)", imports = "com.json.response.without.gson.app.utils.gson.JsonParser")
  public JsonElement parse(String json) throws com.json.response.without.gson.app.utils.gson.JsonSyntaxException {
    return parseString(json);
  }

  /**
   * @deprecated Use {@link JsonParser#parseReader(Reader)}
   */
  @Deprecated
//  @InlineMe(replacement = "JsonParser.parseReader(json)", imports = "com.json.response.without.gson.app.utils.gson.JsonParser")
  public JsonElement parse(Reader json) throws JsonIOException, com.json.response.without.gson.app.utils.gson.JsonSyntaxException {
    return parseReader(json);
  }

  /**
   * @deprecated Use {@link JsonParser#parseReader(JsonReader)}
   */
  @Deprecated
//  @InlineMe(replacement = "JsonParser.parseReader(json)", imports = "com.json.response.without.gson.app.utils.gson.JsonParser")
  public JsonElement parse(JsonReader json) throws JsonIOException, com.json.response.without.gson.app.utils.gson.JsonSyntaxException {
    return parseReader(json);
  }
}
