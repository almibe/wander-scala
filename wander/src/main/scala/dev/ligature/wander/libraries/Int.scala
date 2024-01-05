/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package dev.ligature.wander.libraries

import javax.lang.model.`type`.ErrorType
import dev.ligature.wander.HostFunction
import dev.ligature.wander.TaggedField
import dev.ligature.wander.Tag
import dev.ligature.wander.WanderValue

val intModule = WanderValue.Module(
  Map(
  )
  // HostFunction(
  //   Name("Int.add"),
  //   "Add two Ints.",
  //   Seq(
  //     TaggedField(Name("left"), Tag.Single(Name("Core.Int"))),
  //     TaggedField(Name("right"), Tag.Single(Name("Core.Int")))
  //   ),
  //   Tag.Single(Name("Core.Int")),
  //   (args, environment) =>
  //     args match
  //       case Seq(WanderValue.Int(left), WanderValue.Int(right)) =>
  //         Right((WanderValue.Int(left + right), environment))
  //       case _ => ???
  // )
)
