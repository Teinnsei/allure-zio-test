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
package provider

import tags.Tag

import zio.UIO

final case class AllureResult(
  testName: String,
  `type`: Type,
  duration: Long,
  steps: Seq[String],
  tags: Seq[Tag],
  uuid: String
)

object AllureResult {

  def apply(testName: String, duration: Long, `type`: Type, steps: Seq[String], tags: Seq[Tag]): UIO[AllureResult] =
    for {
      uuid <- Random.uuidStr
    } yield new AllureResult(testName, `type`, duration, steps, tags, uuid)
}
