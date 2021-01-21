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

import java.util.jar.Manifest

import _root_.sbt._
import _root_.sbt.Keys._

object AllureAutoPlugin extends AutoPlugin {

  private def version = {
    val cl         = getClass.getClassLoader
    val stream     = cl.getResourceAsStream("META-INF/MANIFEST.MF")
    val manifest   = new Manifest(stream)
    val attributes = manifest.getMainAttributes
    attributes.getValue("Implementation-Version")
  }

  override def projectSettings: Seq[Def.Setting[_]] = Seq(
    testFrameworks += new TestFramework("io.cronenbergworld.allure.sbt.ZTestAllureFramework"),
    testListeners += AllureTestListeners,
    libraryDependencies ++= Seq("io.cronenbergworld" %% "allure-zio-core" % "0.0.1-RC2" % Test)
  )
}
