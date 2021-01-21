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

import zio.{Task, UIO}
import zio.test.Annotations

private[provider] case class DelegateAnnotationService(annotations: Annotations.Service) extends Service {

  override def report(result: AllureResult): UIO[Unit] =
    Task.effect(LifecycleStatics.writeResult(Converters.toTestResult(result))).orDie

  override def tag(tag: Tag): UIO[Unit] =
    annotations.annotate(allureAnnotation, List(tag))
}
