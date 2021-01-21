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

import zio.test.environment.TestEnvironment
import zio._
import provider._

package object environment {

  type AllureEnvironment = TestEnvironment with Allure

  object AllureEnvironment {

    val any: URLayer[AllureEnvironment, AllureEnvironment] =
      ZLayer.requires[AllureEnvironment]

    val live: URLayer[ZEnv, AllureEnvironment] =
      TestEnvironment.live ++ (TestEnvironment.live >>> provider.live)

    val environment: Layer[Nothing, AllureEnvironment] =
      ZEnv.live >>> live
  }
}
