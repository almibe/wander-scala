/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package dev.ligature.wander

import dev.ligature.wander.Token
import dev.ligature.wander.WanderValue
import dev.ligature.wander.libraries.std

class LiteralsSuite extends munit.FunSuite {
  def check(script: String, expected: WanderValue) =
    assertEquals(
      run(script, std()).getOrElse(???)._1,
      expected
    )

  test("true boolean primitive") {
    val script = "true"
    val result = WanderValue.Bool(true)
    check(script, result)
  }
  test("false boolean primitive") {
    val script = "false"
    val result = WanderValue.Bool(false)
    check(script, result)
  }
  test("true boolean primitive with trailing whitespace") {
    val script = "true   "
    val result = WanderValue.Bool(true)
    check(script, result)
  }
  test("integer") {
    val script = "24601"
    val result = WanderValue.Int(24601)
    check(script, result)
  }
  test("negative integer") {
    val script = "-111"
    val result = WanderValue.Int(-111)
    check(script, result)
  }
  test("comment + nothing test") {
    val script = "--nothing   " + System.lineSeparator()
    val result = WanderValue.Nothing
    check(script, result)
  }
  test("string primitives") {
    val script = "\"hello world\" "
    val result = WanderValue.String("hello world")
    check(script, result)
  }
  test("nothing literal") {
    val script = "nothing"
    val result = WanderValue.Nothing
    check(script, result)
  }
  test("empty record literal") {
    val script = "{}"
    val result = WanderValue.Record(Map())
    check(script, result)
  }
  test("record literal with one value") {
    val script = "{x = 5}"
    val result = WanderValue.Record(Map((UName("x"), WanderValue.Int(5))))
    check(script, result)
  }
  test("record literal with multiple values") {
    val script = "{x = 5, notFalse = true}"
    val result = WanderValue.Record(
      Map((UName("x"), WanderValue.Int(5)), (UName("notFalse"), WanderValue.Bool(true)))
    )
    check(script, result)
  }
}
