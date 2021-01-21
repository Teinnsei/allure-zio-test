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

import io.qameta.allure.CommandLine

import _root_.sbt._

object AllureTestListeners extends TestsListener {
  override def doInit(): Unit                                   = ()
  override def startGroup(name: String): Unit                   = ()
  override def testEvent(event: TestEvent): Unit                = ()
  override def endGroup(name: String, t: Throwable): Unit       = ()
  override def endGroup(name: String, result: TestResult): Unit = ()
  override def doComplete(finalResult: TestResult): Unit        = ()
}
