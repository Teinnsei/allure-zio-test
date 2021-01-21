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

import tags._

import io.qameta.allure.model.{Attachment => JAttachment, _}
import io.qameta.allure.util.ResultsUtils.md5

import java.util.UUID

private[allure] object Converters {

  def toTestResult(allure: AllureResult): TestResult = {
    val rs = new TestResult()
      .setFullName(allure.testName)
      .setName(allure.testName)
      .setUuid(UUID.randomUUID().toString)
      .setTestCaseId(md5(allure.testName))
      .setHistoryId(md5(allure.testName))
      .setStart(0)
      .setStop(allure.duration)

    allure.`type` match {
      case Type.Failure(message) =>
        val status = new StatusDetails().setMessage("Failure invocation").setTrace(message)
        rs.setStatusDetails(status).setStatus(Status.FAILED)
      case Type.Success =>
        val status = new StatusDetails().setMessage("Success")
        rs.setStatusDetails(status).setStatus(Status.PASSED)
      case Type.Ignored =>
        val status = new StatusDetails().setMessage("Ignored")
        rs.setStatusDetails(status).setStatus(Status.SKIPPED)
      case Type.Broken =>
        val status = new StatusDetails().setMessage("Broken")
        rs.setStatusDetails(status).setStatus(Status.BROKEN)
    }

    allure.tags.foreach {
      case Tag.Plain(name, value) =>
        rs.getLabels.add(new Label().setName(name).setValue(value))
      case Tag.Link(name, url, t) =>
        rs.getLinks.add(new Link().setName(name).setType(t).setUrl(url))
      case attachment: Attachment =>
        val jAttachment = new JAttachment()
        jAttachment.setName(attachment.name)
        jAttachment.setSource(attachment.source)
        jAttachment.setType(attachment.`type`)
    }

    rs
  }
}
