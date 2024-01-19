/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package dev.ligature.wander.libraries

import dev.ligature.wander.Environment
import dev.ligature.wander.WanderValue
import dev.ligature.wander.Field
import dev.ligature.wander.HostFunction
import dev.ligature.wander.Tag
import dev.ligature.wander.TaggedField
import java.nio.file.Path
import jetbrains.exodus.env.Environments
import jetbrains.exodus.env.EnvironmentConfig
import jetbrains.exodus.entitystore.PersistentEntityStore
import jetbrains.exodus.entitystore.PersistentEntityStores
import java.nio.file.Paths

def openDefault(): jetbrains.exodus.env.Environment =
  val home = System.getProperty("user.home") + "/.wander"
  openStore(Paths.get(home))

def openStore(path: Path): jetbrains.exodus.env.Environment =
  val environment = Environments.newInstance(path.toFile(), EnvironmentConfig())
  environment

def createStoreModule(xe: jetbrains.exodus.env.Environment) = 
  val store = PersistentEntityStores.newInstance(xe, "default")
  WanderValue.Module(
  Map(
    Field("store") -> WanderValue.Function(
      HostFunction(
        "Store a Module in a given named store.",
        Seq(
          TaggedField(Field("storeName"), Tag.Untagged),
          TaggedField(Field("value"), Tag.Untagged)
        ),
        Tag.Untagged,
        (args, env) =>
          args match {
            case Seq(WanderValue.String(name), module: WanderValue.Module) =>
              store.executeInTransaction(tx =>
                val entity = tx.newEntity(name)
                entity.setProperty("test", "test")
                tx.commit()
              )
              Right((module, env))
            case _ => ???
          }
      )
    ),
    Field("count") -> WanderValue.Function(
      HostFunction(
        "Retrieve all values in this store.",
        Seq(
          TaggedField(Field("storeName"), Tag.Untagged),
        ),
        Tag.Untagged,
        (args, env) =>
          args match {
            case Seq(WanderValue.String(name)) =>
              val count = store.computeInReadonlyTransaction(tx =>
                tx.getAll(name).count()
              )
              Right((WanderValue.Int(count), env))
            case _ => ???
          }
      )
    )
  )
)
