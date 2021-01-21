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
package sbt

import _root_.sbt.testing._
import zio.test.sbt._

final class ZTestAllureFramework extends Framework {
  override val name: String = s"${Console.UNDERLINED}Allure ZIO Test${Console.RESET}"

  val fingerprints: Array[Fingerprint] = Array(RunnableSpecFingerprint)

  override def runner(args: Array[String], remoteArgs: Array[String], testClassLoader: ClassLoader): ZTestAllureRunner =
    new ZTestAllureRunner(args, remoteArgs, testClassLoader)
}
