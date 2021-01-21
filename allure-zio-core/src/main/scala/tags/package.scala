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

import zio.test._

package object tags {

  sealed trait Tag

  sealed trait Attachment extends Tag {
    def name: String
    def source: String
    def `type`: String
  }

  object Tag {

    final case class Plain(name: String, value: String)              extends Tag
    final case class Link(name: String, url: String, `type`: String) extends Tag

    object Attachment {
      final case class Custom(name: String, source: String, `type`: String) extends Attachment

      final case class Xml(name: String, source: String) extends Attachment {
        override def `type`: String = "text/xml"
      }

      final case class Json(name: String, source: String) extends Attachment {
        override def `type`: String = "text/json"
      }

      final case class Plain(name: String, source: String) extends Attachment {
        override def `type`: String = "text/plain"
      }

      final case class HttpRequest(
        name: String,
        url: String,
        method: String,
        body: String,
        headers: Map[String, String]
      ) extends Attachment {
        private lazy val rendered =
          LifecycleStatics.requestRender.render(Converters.toHttpRequest(this))
        override lazy val source: String = rendered.getContent
        override lazy val `type`: String = rendered.getContentType
      }

      final case class HttpResponse(
        name: String,
        url: String,
        code: Int,
        body: String,
        headers: Map[String, String]
      ) extends Attachment {
        private lazy val rendered =
          LifecycleStatics.responseRender.render(Converters.toHttpResponse(this))
        override lazy val source: String = rendered.getContent
        override lazy val `type`: String = rendered.getContentType
      }
    }
  }

  /**
   * A type of function that take value of tag like argument and return TestAspectPoly
   */
  type FMark = String => TestAspectPoly

  type LMark = (String, String) => TestAspectPoly

  /**
   * Used by Allure Enterprise to link test cases with related test methods.
   */
  val allureId: FMark = atag("AS_ID")

  /**
   * Tag that allows to attach a description for a test or for a step.
   */
  val description: FMark = atag("description")

  /**
   * Used to mark tests with epic label.
   */
  val epic: FMark = atag("epic")

  /**
   * Used to mark tests with feature label.
   */
  val feature: FMark = atag("feature")

  /**
   * Used to mark tests as flaky (unstable).
   */
  val flaky: FMark = atag("flaky")

  /**
   * This tag used to specify project leads for test case.
   */
  val lead: FMark = atag("lead")

  /**
   * This tag used to specify project leads for test case.
   */
  val muted: FMark = atag("muted")

  /**
   * This tag used to specify owner for test case.
   */
  val owner: FMark = atag("owner")

  /**
   * This tag used to specify name of class for test case.
   * It's internal api
   */
  val className: FMark = atag("className")

  /**
   * Alias className
   * It's internal api
   */
  val parentSuite: FMark = atag("parentSuite")

  /**
   * This tag used to specify package name of class for test case.
   * It's internal api
   */
  val packageName: FMark = atag("package")

  /**
   * Used to link tests with issues.
   */
  val issue: LMark = ltag("issue")

  /**
   * Used to link tests with test cases in external test management system.
   */
  val tms: LMark = ltag("tms")

  /**
   * Use this annotation to add some links to results
   */
  val custom: LMark = ltag("custom")

  /**
   * Annotation that declare special meta-information for Allure Framework
   */
  val allureAnnotation: TestAnnotation[List[Tag]] =
    TestAnnotation("allure_tags", List.empty, _ ++ _)

  object SeverityLevel extends Enumeration {
    type SeverityLevel = Value
    val BLOCKER, CRITICAL, NORMAL, MINOR, TRIVIAL = Value
  }

  /**
   * Used to set severity for tests.
   */
  val severity: SeverityLevel.SeverityLevel => TestAspectPoly =
    s => atag("Severity")(s.toString.toLowerCase())

  /**
   * This tag used to specify package name of class for test case.
   * It's internal api
   */
  val zioFramework: TestAspectPoly = atag("framework")("ZIO")

  private def atag(name: String)(value: String) =
    TestAspect.annotate(allureAnnotation, List(Tag.Plain(name, value)))

  private def ltag(`type`: String)(name: String, url: String) =
    TestAspect.annotate(allureAnnotation, List(Tag.Link(name, url, `type`)))
}
