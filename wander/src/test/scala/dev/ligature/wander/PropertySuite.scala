/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package dev.ligature.wander

import java.io.File
import scala.io.Source
import dev.ligature.wander.libraries.std
import dev.ligature.wander.libraries.loadFromPath
import java.nio.file.Path

class PropertySuite extends WanderSuiteCommonMode {
    val properties = Seq(
      HostProperty(
        UName("prop"),
        "A test property.",
        Tag.Untagged,
        (environment) => Right((WanderValue.Int(5), environment))
      )
    )
    val env = std().addHostProperties(properties)
    test("Read basic property") {
        val script = "prop"
        val expected = WanderValue.Int(5)
        check(script, expected, env)
    }
}
