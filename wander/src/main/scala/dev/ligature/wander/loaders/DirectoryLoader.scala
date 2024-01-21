/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package dev.ligature.wander.loaders

import dev.ligature.wander.FieldPath
import dev.ligature.wander.WanderValue
import java.nio.file.Path
import dev.ligature.wander.Environment
import dev.ligature.wander.WanderError
import scala.util.boundary
import scala.util.boundary.break
import java.nio.file.Files
import scala.jdk.CollectionConverters.IteratorHasAsScala
import dev.ligature.wander.Field
import dev.ligature.wander.TaggedField
import dev.ligature.wander.run
import scala.util.Success
import scala.util.Failure
import scala.io.Source
import scala.util.Using
import dev.ligature.wander.libraries.std
import dev.ligature.wander.Tag
import dev.ligature.wander.libraries.wmdn

/** Load Wander modules from the path provided using the environment provided as a base.
  */
def loadFromPath(path: Path, base: Environment = std()): Either[WanderError, Environment] =
  boundary:
    var resultEnvironment = base
    Files
      .walk(path)
      .iterator()
      .asScala
      .filter(Files.isRegularFile(_))
      .filter(f =>
        f.getFileName().toString().endsWith(".wander")
          &&
            !f.getFileName().toString().endsWith(".test.wander")
      )
      .foreach { file =>
        val modname = Field(file.toFile().getName().split('.').head)
        val module = scala.collection.mutable.HashMap[Field, WanderValue]()
        Using(Source.fromFile(file.toFile()))(_.mkString) match
          case Failure(exception) =>
            break(Left(WanderError(s"Error reading $file\n${exception.getMessage()}")))
          case Success(script) =>
            run(script, std()) match
              case Left(err) => break(Left(err))
              case Right((WanderValue.Module(values), _)) =>
                values.foreach((name, value) => module.put(name, value))
              case x => break(Left(WanderError("Unexpected value from load result. $x")))
        resultEnvironment = resultEnvironment.bindVariable(
          TaggedField(modname, Tag.Untagged),
          WanderValue.Module(module.toMap)
        ) match
          case Left(value)  => ???
          case Right(value) => value
      }
    Right(resultEnvironment)
