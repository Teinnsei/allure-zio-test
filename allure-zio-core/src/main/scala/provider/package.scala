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

import tags._

import zio.test.Annotations
import zio.{Tag => _, _}

import scala.language.implicitConversions

package object provider {

  type Allure = Has[Service]

  trait Service {
    def tag(tag: Tag): UIO[Unit]
    def report(result: AllureResult): UIO[Unit]
  }

  def report(result: AllureResult): URIO[Allure, Unit] = ZIO.accessM[Allure](_.get.report(result))
  def tag(attachment: Attachment): URIO[Allure, Unit]  = ZIO.accessM[Allure](_.get.tag(attachment))

  val any: URLayer[Allure, Allure]       = ZLayer.requires[Allure]
  val live: URLayer[Annotations, Allure] = ZLayer.fromService(DelegateAnnotationService)

}
