/*
 *
 * Copyright 2021 Alexander Galagutskiy and the Cronenbergs Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.cronenbergworld.allure
package tags

import io.qameta.allure.attachment.http._
import zio.{Tag => _}

import scala.jdk.CollectionConverters._
import scala.language.implicitConversions

private[tags] object Converters {

  def toHttpRequest(httpRequest: Tag.Attachment.HttpRequest): HttpRequestAttachment =
    HttpRequestAttachment.Builder
      .create(httpRequest.name, httpRequest.url)
      .setMethod(httpRequest.method)
      .setHeaders(httpRequest.headers.asJava)
      .setBody(httpRequest.body)
      .build()

  def toHttpResponse(httpResponse: Tag.Attachment.HttpResponse): HttpResponseAttachment =
    HttpResponseAttachment.Builder
      .create(httpResponse.name)
      .setUrl(httpResponse.url)
      .setResponseCode(httpResponse.code)
      .setHeaders(httpResponse.headers.asJava)
      .setBody(httpResponse.body)
      .build()

}
