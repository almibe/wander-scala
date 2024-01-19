/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package dev.ligature.wander.libraries

import dev.ligature.wander.WanderValue
import munit.FunSuite
import dev.ligature.wander.WanderSuiteCommonMode

class StoreSuite extends WanderSuiteCommonMode {

  test("store should start with no entities") {
    val store = openDefault()
    val script = "Store.count \"test\""
    val tokens = WanderValue.Int(0)
    check(script, tokens, stdWithStore(store))
    store.close()
  }
}
