/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package dev.ligature.wander

import dev.ligature.wander.WanderValue
import munit.FunSuite
import dev.ligature.wander.libraries.std

class InterpreterSuite extends FunSuite {
  def check(script: String, expected: Map[Name, WanderValue]) =
    load(script, std()) match
      case Left(err)    => throw RuntimeException(err.toString())
      case Right(value) => assertEquals(value, expected)

  test("load script with no exports") {
    val script = "x = false"
    check(script, Map())
  }
  test("load script with one exports") {
    val script = "export hello = 2"
    val tokens = Map(UName("hello").head -> WanderValue.Int(2))
    check(script, tokens)
  }
}
