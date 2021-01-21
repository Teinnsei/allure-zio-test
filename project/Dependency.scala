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

import sbt._

object Dependency {

  val zioVersion    = "1.0.4"
  val allureVersion = "2.13.8"

  val dependencies: Seq[ModuleID] = Seq(
    "dev.zio"         %% "zio"                 % zioVersion,
    "dev.zio"         %% "zio-test"            % zioVersion,
    "dev.zio"         %% "zio-test-sbt"        % zioVersion,
    "io.qameta.allure" % "allure-java-commons" % allureVersion,
    "io.qameta.allure" % "allure-attachments"  % allureVersion
  )

  val pluginDependencies: Seq[ModuleID] = Seq(
    "io.qameta.allure" % "allure-commandline" % allureVersion,
    "io.qameta.allure" % "allure-generator"   % allureVersion,
    "io.qameta.allure" % "allure-plugin-api"  % allureVersion
  )

}
