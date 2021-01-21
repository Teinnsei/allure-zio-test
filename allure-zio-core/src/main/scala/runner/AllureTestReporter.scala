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
package runner

import provider._
import tags._

import zio._
import zio.duration.Duration
import zio.test._

final private[allure] class AllureTestReporter[E](layer: ULayer[Allure]) extends TestReporter[E] {

  private def combineReporters(d: Duration, e: ExecutedSpec[E])(fs: TestReporter[E]*) =
    fs.map(f => f(d, e)).reduce[URIO[TestLogger, Unit]] { case (l, r) => l *> r }

  private def makeDefaultReporter: TestReporter[E] =
    DefaultTestReporter(TestAnnotationRenderer.default)

  private def makeAllureReporter(duration: Duration, executedSpec: ExecutedSpec[E]): URIO[TestLogger, Unit] = {
    def allure(executedSpec: ExecutedSpec[E], labels: List[String]): UIO[Unit] =
      executedSpec.caseValue match {
        case ExecutedSpec.SuiteCase(label, specs) =>
          ZIO.collectAll_(specs.map(s => allure(s, label :: labels)))
        case ExecutedSpec.TestCase(label, _, annotations) =>
          val tags         = annotations.get(allureAnnotation)
          val duration     = annotations.get(TestAnnotation.timing).toMillis
          val buildSummary = SummaryBuilder.buildSummary(executedSpec)
          val message = buildSummary match {
            case Summary(_, 0, 0, _)       => Type.Success
            case Summary(0, _, 0, summary) => Type.Failure(summary)
            case Summary(0, 0, _, _)       => Type.Ignored
            case _                         => Type.Broken

          }
          AllureResult(label, duration, message, labels, tags).flatMap(report).provideLayer(layer)
      }
    allure(executedSpec, List.empty)
  }

  override def apply(duration: Duration, executedSpec: ExecutedSpec[E]): URIO[TestLogger, Unit] =
    combineReporters(duration, executedSpec)(makeDefaultReporter, makeAllureReporter)
}
