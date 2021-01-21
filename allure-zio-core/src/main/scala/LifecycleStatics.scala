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

import io.qameta.allure.attachment.FreemarkerAttachmentRenderer
import io.qameta.allure.model.TestResult
import io.qameta.allure.{Allure => JAllure, _}

import java.lang.invoke.MethodHandles

private[allure] object LifecycleStatics {

  private val lifecycle = JAllure.getLifecycle

  val requestRender  = new FreemarkerAttachmentRenderer("http-request.ftl")
  val responseRender = new FreemarkerAttachmentRenderer("http-response.ftl")

  private val writer: AllureResultsWriter = {
    val lookup =
      MethodHandles.privateLookupIn(classOf[AllureLifecycle], MethodHandles.lookup())
    val wvh =
      lookup.findVarHandle(classOf[AllureLifecycle], "writer", classOf[AllureResultsWriter])
    wvh.get(lifecycle)
  }

  def writeResult(testResult: TestResult): Unit = writer.write(testResult)
}
