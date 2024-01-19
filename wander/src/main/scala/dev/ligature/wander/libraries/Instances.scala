/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package dev.ligature.wander.libraries

import dev.ligature.wander.WanderValue
import scala.collection.mutable.ListBuffer
import dev.ligature.wander.*
import dev.ligature.wander.Environment
import java.nio.file.Path
import java.nio.file.Files
import scala.jdk.CollectionConverters.IteratorHasAsScala
import scala.util.Using
import scala.io.Source
import scala.util.Failure
import scala.util.Success
import scala.util.boundary
import scala.util.boundary.break
import jetbrains.exodus.entitystore.PersistentEntityStore

/** A named instance of an empty Environment used when parsing wmdn.
  */
val wmdn: Environment = Environment()

/** Create the "default" environment for working with Wander.
  */
def std(): Environment =
  Environment()
    .bindVariable(Field("Array"), arrayModule)
    .bindVariable(Field("Bool"), boolModule)
    .bindVariable(Field("Bytes"), bytesModel)
    .bindVariable(Field("Core"), coreModule)
    .bindVariable(Field("Http"), httpModule)
    .bindVariable(Field("Int"), intModule)
    .bindVariable(Field("Module"), moduleModule)
    .bindVariable(Field("Shape"), shapeModule)
    .bindVariable(Field("String"), stringModule)
    .bindVariable(Field("Test"), testingModule)
    .bindVariable(Field("import"), importFunction)

def stdWithStore(env: jetbrains.exodus.env.Environment): Environment =
  std().bindVariable(Field("Store"), createStoreModule(env))

/** Load Wander modules from the path provided using the environment provided as a base.
  */
def loadFromPath(path: Path, environment: Environment): Either[WanderError, Environment] =
  boundary:
    var resultEnvironment = environment
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
