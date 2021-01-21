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

import tags._

import zio.clock.Clock
import zio.duration._
import zio.test._
import io.cronenbergworld.allure.environment.AllureEnvironment
import zio._

abstract class AllureRunnableSpec[R <: AllureEnvironment](layer: ULayer[R]) extends RunnableSpec[R, Any] {

  override def aspects: List[TestAspect[Nothing, R, Nothing, Any]] = {
    val `class`   = getClass.getSimpleName.split("\\$").last
    val `package` = getClass.getPackageName
    List(
      TestAspect.timeoutWarning(60.seconds),
      zioFramework,
      className(`class`),
      parentSuite(`class`),
      packageName(`package`),
      TestAspect.timed
    )
  }

  override def runSpec(spec: ZSpec[R, Failure]): URIO[TestLogger with Clock, ExecutedSpec[Failure]] =
    runner.run(aspects.foldLeft(spec)(_ @@ _))

  override def runner: TestRunner[R, Any] =
    TestRunner(TestExecutor.default(layer), reporter = new AllureTestReporter[Any](layer))
}
