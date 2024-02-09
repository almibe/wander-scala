/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package dev.ligature.wander.modules

import dev.ligature.wander.libraries.HostLibrary
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
import dev.ligature.wander.libraries.ModuleLibrary

/** A named instance of an empty Environment used when parsing wmdn.
  */
val wmdn: Environment = Environment()

/** Create the "default" environment for working with Wander.
  */
def std(libraries: List[ModuleLibrary] = List()): Environment =
  Environment(Seq(HostLibrary(Map(
    "Array" -> arrayModule,
  //  "Bool" -> boolModule,
    "Bytes" -> bytesModule,
    "Core" -> coreModule,
    "Int" -> intModule,
    "Module" -> moduleModule,
    "String" -> stringModule,
    "Test" -> testingModule
  ))))
  .bindVariable(Field("Bool"), boolModule)
  .bindVariable(Field("import"), importFunction)

def stdWithKeylime(
    env: jetbrains.exodus.env.Environment,
    loaders: List[ModuleLibrary] = List()
): Environment = ???
// std(loaders).bindVariable(
//   Field("Keylime"),
//   createKeylimeModule(env)
// ) // this should add a loader instead of binding a varible
