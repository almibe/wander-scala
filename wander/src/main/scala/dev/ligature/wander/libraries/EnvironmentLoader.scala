/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package dev.ligature.wander.libraries

import dev.ligature.wander.Environment
import dev.ligature.wander.WanderError
import dev.ligature.wander.WanderValue
import dev.ligature.wander.FieldPath

final class EnvironmentLoader(environment: Environment) extends ModuleLibrary {
  override def lookup(path: FieldPath): Either[WanderError, Option[WanderValue]] =
    environment.read(path)
}
