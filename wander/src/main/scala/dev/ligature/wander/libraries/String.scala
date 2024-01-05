/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package dev.ligature.wander.libraries

import dev.ligature.wander.HostFunction
import dev.ligature.wander.TaggedField
import dev.ligature.wander.Tag
import dev.ligature.wander.WanderValue

val stringLibrary =
  Seq(
    // HostFunction(
    //   Name("String.cat"),
    //   "Concat two Strings.",
    //   Seq(
    //     TaggedField(Name("left"), Tag.Single(Name("Core.String"))),
    //     TaggedField(Name("right"), Tag.Single(Name("Core.String")))
    //   ),
    //   Tag.Single(Name("Core.String")),
    //   (args, environment) =>
    //     args match
    //       case Seq(WanderValue.String(left), WanderValue.String(right)) =>
    //         Right((WanderValue.String(left + right), environment))
    // )
  )
