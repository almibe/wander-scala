/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package dev.ligature.wander.libraries

import dev.ligature.wander.Environment
import dev.ligature.wander.HostFunction
import dev.ligature.wander.WanderValue
import dev.ligature.wander.TaggedField
import dev.ligature.wander.Tag
import dev.ligature.wander.WanderError
import dev.ligature.wander.HostProperty

val coreLibrary = Seq(
  // HostFunction(
  //   Name("Core.eq"),
  //   "Check if two values are equal.",
  //   Seq(
  //     TaggedField(Name("left"), Tag.Single(Name("Core.Any"))),
  //     TaggedField(Name("right"), Tag.Single(Name("Core.Any")))
  //   ),
  //   Tag.Single(Name("Core.Bool")),
  //   (args: Seq[WanderValue], environment: Environment) =>
  //     args match
  //       case Seq(first, second) =>
  //         val res = first.asInstanceOf[WanderValue] == second.asInstanceOf[WanderValue]
  //         Right((WanderValue.Bool(res), environment))
  //       case _ => ???
  // ),
  // HostFunction(
  //   Name("Core.Any"),
  //   "Checks if a value is an Any.",
  //   Seq(TaggedField(Name("value"), Tag.Single(Name("Core.Any")))),
  //   Tag.Single(Name("Core.Bool")),
  //   (args: Seq[WanderValue], environment: Environment) =>
  //     Right((WanderValue.Bool(true), environment))
  // ),
  // HostFunction(
  //   Name("Core.Int"),
  //   "Check if a value is an Int.",
  //   Seq(TaggedField(Name("value"), Tag.Single(Name("Core.Any")))),
  //   Tag.Single(Name("Core.Bool")),
  //   (args: Seq[WanderValue], environment: Environment) =>
  //     args match
  //       case Seq(WanderValue.Int(_)) => Right((WanderValue.Bool(true), environment))
  //       case Seq(_)                  => Right((WanderValue.Bool(false), environment))
  //       case _                       => ???
  // ),
  // HostFunction(
  //   Name("Core.Bool"),
  //   "Check if a value is a Bool.",
  //   Seq(TaggedField(Name("value"), Tag.Single(Name("Core.Any")))),
  //   Tag.Single(Name("Core.Bool")),
  //   (args: Seq[WanderValue], environment: Environment) =>
  //     args match
  //       case Seq(WanderValue.Bool(_)) => Right((WanderValue.Bool(true), environment))
  //       case Seq(_)                   => Right((WanderValue.Bool(false), environment))
  //       case _                        => ???
  // ),
  // HostFunction(
  //   Name("Core.Module"),
  //   "Check if a value is a Module.",
  //   Seq(TaggedField(Name("value"), Tag.Single(Name("Core.Any")))),
  //   Tag.Single(Name("Core.Bool")),
  //   (args: Seq[WanderValue], environment: Environment) =>
  //     args match
  //       case Seq(WanderValue.Module(_)) => Right((WanderValue.Bool(true), environment))
  //       case Seq(_)                     => Right((WanderValue.Bool(false), environment))
  //       case _                          => ???
  // ),
  // HostFunction(
  //   Name("Core.Array"),
  //   "Check if a value is an Array.",
  //   Seq(TaggedField(Name("value"), Tag.Single(Name("Core.Any")))),
  //   Tag.Single(Name("Core.Bool")),
  //   (args: Seq[WanderValue], environment: Environment) =>
  //     args match
  //       case Seq(WanderValue.Array(_)) => Right((WanderValue.Bool(true), environment))
  //       case Seq(_)                    => Right((WanderValue.Bool(false), environment))
  //       case _                         => ???
  // ),
  // HostFunction(
  //   Name("Core.String"),
  //   "Check if a value is a String.",
  //   Seq(TaggedField(Name("value"), Tag.Single(Name("Core.Any")))),
  //   Tag.Single(Name("Core.Bool")),
  //   (args: Seq[WanderValue], environment: Environment) =>
  //     args match
  //       case Seq(WanderValue.String(_)) => Right((WanderValue.Bool(true), environment))
  //       case Seq(_)                     => Right((WanderValue.Bool(false), environment))
  //       case _                          => ???
  // ),
  // HostFunction(
  //   Name("Core.Nothing"),
  //   "Check if a value is Nothing.",
  //   Seq(TaggedField(Name("value"), Tag.Single(Name("Core.Any")))),
  //   Tag.Single(Name("Core.Bool")),
  //   (args: Seq[WanderValue], environment: Environment) =>
  //     args match
  //       case Seq(WanderValue.Nothing) => Right((WanderValue.Bool(true), environment))
  //       case Seq(_)                   => Right((WanderValue.Bool(false), environment))
  //       case _                        => ???
  // ),
  // HostFunction(
  //   Name("Core.todo"),
  //   "Exit a script at this point with the given TODO message, useful during development.",
  //   Seq(TaggedField(Name("message"), Tag.Single(Name("Core.String")))),
  //   Tag.Single(Name("Core.Nothing")),
  //   (args: Seq[WanderValue], environment: Environment) =>
  //     args match
  //       case Seq(WanderValue.String(message)) => Left(WanderError(message))
  // )
)

val coreProperties = Seq(
  // HostProperty(
  //   Name("Core.environment"),
  //   "Read all Bindings in the current scope.",
  //   Tag.Single(Name("Core.Module")),
  //   (environment: Environment) =>
  //     // val names: List[WanderValue] = environment.functions.map(f => WanderValue.String(f.name.name))
  //     // ++
  //     // environment.properties.map(f => WanderValue.String(f.name.name))
  //     // Right((WanderValue.Array(names), environment))
  //     Right((WanderValue.Nothing, environment))
  // )
)

private def readProperties(evironment: Environment): WanderValue.Module =
  ???
